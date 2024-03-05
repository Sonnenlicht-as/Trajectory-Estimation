package sonnenlichts.tje.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.mojang.math.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.slf4j.Logger;
import sonnenlichts.tje.client.config.TjeModConfig;
import sonnenlichts.tje.client.extra.*;
import sonnenlichts.tje.client.render.ModRenderType;
import sonnenlichts.tje.client.util.ModUtils;
import sonnenlichts.tje.client.util.StringHelper;

import java.util.List;

import static net.minecraft.world.item.CrossbowItem.containsChargedProjectile;
import static sonnenlichts.tje.TrajectoryEstimation.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientRenderHandler {
    private static final RenderType BUFFS = ModRenderType.cube(StringHelper.create("textures/point/0.png"));
    private int soundPlayCount = 0;
    public static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public void rendersWorldEvent(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        Level pLevel = mc.level;
        if (player == null) return;
        if (pLevel == null) return;
        ItemStack itemStackUsing = player.getUseItem();
        ItemStack itemStack = ModUtils.getCorrectItem(player);
        PoseStack matrix = event.getPoseStack();

        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
        VertexConsumer builder = buffer.getBuffer(BUFFS);

        Vec3 viewPos = mc.getEntityRenderDispatcher().camera.getPosition();
        Vec3 originPos = new Vec3(player.getX(), player.getEyeY() - (double) 0.1F, player.getZ());


        if (ModUtils.isClassOrSuperClass(itemStackUsing.getItem(), BowItem.class)) {
            int remainTick = player.getUseItemRemainingTicks();
            int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
            float power = ModUtils.getPowerForTime(i);
            float pVelocity = power * 3.0F;
            float pInaccuracy = 0.0F;
            float gravity = 0.05F;
            if (ModUtils.CataclysmLoaded() && LEnderCataclysmExtra.isVoidScatterArrow(player.getProjectile(itemStackUsing)))
                originPos = new Vec3(player.getX(), player.getEyeY() - 0.10000000149011612D, player.getZ());
            Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
            this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
        }
        if (ModUtils.isClassOrSuperClass(itemStack.getItem(), CrossbowItem.class)) {
            if (!CrossbowItem.isCharged(itemStack)) return;
            float pProjectileAngle = 0;
            float pVelocity = containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
            float pInaccuracy = 0.0F;
            float gravity = 0.05F;
            Vec3 vec31 = player.getUpVector(1.0F);
            Vec3 vec32 = player.getViewVector(1.0F);
            List<ItemStack> list = ModUtils.getCrossbowProjectileInfo(itemStack);
            for (int j = 0; j < list.size(); ++j) {
                ItemStack itemstack = list.get(j);
                if (!itemstack.isEmpty()) {
                    if (itemstack.is(Items.FIREWORK_ROCKET)) {
                        originPos = new Vec3(player.getX(), player.getEyeY() - (double) 0.15F, player.getZ());
                        gravity = 0;
                    }
                    switch (j) {
                        case 0 -> pProjectileAngle = 0.0F;
                        case 1 -> pProjectileAngle = -10.0F;
                        case 2 -> pProjectileAngle = 10.0F;
                    }
                    Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(pProjectileAngle * Mth.DEG_TO_RAD, vec31.x, vec31.y, vec31.z);
                    Vector3f vector3f = vec32.toVector3f().rotate(quaternionf);
                    Vec3 vec3 = ModUtils.calculateVec(player, pVelocity, pInaccuracy, new Vec3(vector3f.x(), vector3f.y(), vector3f.z()));
                    this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
                }
            }
        }
        if (ModUtils.isClassOrSuperClass(itemStack.getItem(), TridentItem.class)) {
            int remainTick = player.getUseItemRemainingTicks();
            int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
            if (i < 10) return;
            int j = EnchantmentHelper.getRiptide(itemStackUsing);
            if (!(j <= 0 || player.isInWaterOrRain())) return;
            if (j != 0) return;

            float pVelocity = 2.5F + (float) j * 0.5F;
            float pInaccuracy = 0.0F;
            float gravity = 0.05F;
            Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
            this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
        }
        if (itemStack.getItem() instanceof SnowballItem) {
            float pVelocity = 1.5F;
            float pInaccuracy = 0.0F;
            float gravity = 0.03F;
            Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
            this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
        }
        if (itemStack.getItem() instanceof EggItem) {
            float pVelocity = 1.5F;
            float pInaccuracy = 0.0F;
            float gravity = 0.03F;
            Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
            this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
        }
        if (itemStack.getItem() instanceof ExperienceBottleItem) {
            float pVelocity = 0.7F;
            float pInaccuracy = 0.0F;
            float gravity = 0.07F;
            Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy, -20F);
            this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
        }
        if (itemStack.getItem() instanceof ThrowablePotionItem) {
            float pVelocity = 0.5F;
            float pInaccuracy = 0.0F;
            float gravity = 0.03F;
            Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
            this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
        }
        if (itemStack.getItem() instanceof EnderpearlItem) {
            float pVelocity = 1.5F;
            float pInaccuracy = 0.0F;
            float gravity = 0.03F;
            Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
            this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
        }

        if (ModUtils.CGMLoaded()) {
            if (MrcrayfishGunsExtra.isStunGrenade(itemStackUsing) || MrcrayfishGunsExtra.isGrenade(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                if (i < 10) return;
                float pVelocity = Math.min(1.0F, i / 20F);
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (MrcrayfishGunsExtra.isGun(itemStack)) {
                float gravity = MrcrayfishGunsExtra.getGunProjGravity(itemStack, player);
                originPos = MrcrayfishGunsExtra.getNewOriginPos(itemStack, player);
                Vec3 vec3 = MrcrayfishGunsExtra.getGunsVec3(itemStack, player);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, -gravity, itemStack);
            }
        }


        if (ModUtils.CataclysmLoaded()) {
            if (LEnderCataclysmExtra.isVoidAssaultShoulderWeapon(itemStackUsing) && !player.getCooldowns().isOnCooldown(itemStackUsing.getItem())) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                float pVelocity = ModUtils.getPowerForTime(i);
                if (pVelocity < 0.5F) return;
                float pInaccuracy = 0.0F;
                float gravity = 0.03F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (LEnderCataclysmExtra.isWitherAssaultShoulderWeapon(itemStackUsing) && !player.getCooldowns().isOnCooldown(itemStackUsing.getItem())) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                float pVelocity = ModUtils.getPowerForTime(i);
                if (pVelocity < 0.5F) return;
                if (!player.isCrouching()) {
                    float d1 = -Mth.sin(player.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(player.getXRot() * Mth.DEG_TO_RAD);
                    float d2 = -Mth.sin(player.getXRot() * Mth.DEG_TO_RAD);
                    float d3 = Mth.cos(player.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(player.getXRot() * Mth.DEG_TO_RAD);
                    originPos = new Vec3(player.getX() + Math.cos(player.getYRot() * Mth.DEG_TO_RAD + 1), player.getEyeY(), player.getZ() + Math.sin(player.getYRot() * Mth.DEG_TO_RAD + 1));
                    double d0 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                    double xPower = 0, yPower = 0, zPower = 0;
                    if (d0 != 0.0D) {
                        xPower = d1 / d0 * 0.1D;
                        yPower = d2 / d0 * 0.1D;
                        zPower = d2 / d0 * 0.1D;
                    }
                    Vec3 vec3 = new Vec3(xPower, yPower, zPower).scale(1);
                    this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, 0, itemStackUsing);
                } else {
                    float pInaccuracy = 0.0F;
                    float gravity = 0.03F;
                    Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                    this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
                }
            }
            if (LEnderCataclysmExtra.isCoralSpear(itemStackUsing) || LEnderCataclysmExtra.isCoralBardiche(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                if (i < 10) return;
                int j = EnchantmentHelper.getRiptide(itemStackUsing);
                if (!(j <= 0 || player.isInWaterOrRain())) return;
                if (j != 0) return;
                float pVelocity = LEnderCataclysmExtra.isCoralBardiche(itemStackUsing) ? (2.5F + (float) j * 0.5F) : (2.5F + (float) j * 0.5F) * 1.25F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (LEnderCataclysmExtra.isLaserGatling(itemStack) && !player.getCooldowns().isOnCooldown(itemStack.getItem())) {
                if (!(itemStack.getDamageValue() < itemStack.getMaxDamage() - 1)) return;
                Vec3 vec32 = player.getViewVector(1.0F);
                originPos = new Vec3(player.getX(), player.getEyeY(), player.getZ());
                float pVelocity = 1F;
                float pInaccuracy = 0.0F;
                float gravity = 0F;
                Vec3 vec3 = ModUtils.calculateVec(player, pVelocity, pInaccuracy, vec32);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
        }


        if (ModUtils.BlueSkiesLoaded()) {
            if (BlueSkiesExtra.isSpear(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                if (i < 10) return;
                float pVelocity = 2.5F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (BlueSkiesExtra.isVenomSac(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                if (i < 20) return;
                float pVelocity = 1.5F;
                float pInaccuracy = 0.0F;
                float gravity = 0F;
                originPos = new Vec3(player.getX(), player.getY() + player.getEyeY(), player.getZ());
                float x = -Mth.sin(player.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(player.getXRot() * Mth.DEG_TO_RAD);
                float y = -Mth.sin((player.getXRot() + 2.0F) * Mth.DEG_TO_RAD);
                float z = Mth.cos(player.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(player.getXRot() * Mth.DEG_TO_RAD);
                Vec3 vec3d = (new Vec3(x, y, z)).normalize().add(player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy, player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy, player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy).scale(pVelocity);
                Vec3 vector3d = player.getDeltaMovement();
                vec3d = vec3d.add(vector3d.x, player.onGround() ? 0.0D : vector3d.y, vector3d.z);
                this.renderTracePoint(vec3d, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
        }


        if (ModUtils.IceAndFireLoaded()) {
            if (IceAndFireExtra.isDragonBow(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                float power = ModUtils.getPowerForTime(i);
                float pVelocity = power * 3.0F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                if (ModUtils.CataclysmLoaded() && LEnderCataclysmExtra.isVoidScatterArrow(player.getProjectile(itemStackUsing)))
                    originPos = new Vec3(player.getX(), player.getEyeY() - 0.10000000149011612D, player.getZ());
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (IceAndFireExtra.isTideTrident(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                if (i < 10) return;
                int j = EnchantmentHelper.getRiptide(itemStackUsing);
                if (!(j <= 0 || player.isInWaterOrRain())) return;
                if (j != 0) return;
                float pVelocity = 2.5F + (float) j * 0.5F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (IceAndFireExtra.isLichStaff(itemStack) && !player.getCooldowns().isOnCooldown(itemStack.getItem())) {
                float pVelocity = 7.0F;
                float pInaccuracy = 0.0F;
                Vec3 vec3 = ModUtils.calculateVec(player, pVelocity, pInaccuracy, new Vec3(player.getXRot(), player.getYRot(), 0));
                originPos = new Vec3(player.getX(), player.getY() + 1.0D, player.getZ());
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, 0, itemStack);
            }
        }


        if (ModUtils.TwilightForestLoaded()) {
            if (TwilightForestExtra.isIceBow(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                float power = ModUtils.getPowerForTime(i);
                float pVelocity = power * 3.0F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (TwilightForestExtra.isTripleBowItem(itemStackUsing) || TwilightForestExtra.isEnderBowItem(itemStackUsing) || TwilightForestExtra.isSeekerBowItem(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                float power = ModUtils.getPowerForTime(i);
                float pVelocity;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                if (power < 0.1D) return;
                for (int j = -1; j < 2; j++) {
                    pVelocity = power * (3.0F - Math.abs(j));
                    if (j != 0) originPos = originPos.add(0.0D, 0.0075 * 20F * j, 0.0D);
                    Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                    this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
                }
            }
            if (TwilightForestExtra.isTripleBowItem(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                float power = ModUtils.getPowerForTime(i);
                float pVelocity;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                if (power < 0.1D) return;
                for (int j = -1; j < 2; j++) {
                    pVelocity = power * (3.0F - Math.abs(j));
                    if (j != 0) originPos = originPos.add(0.0D, 0.0075 * 20F * j, 0.0D);
                    Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                    this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
                }
            }
            if (TwilightForestExtra.isTwilightWandItem(itemStack)) {
                float pVelocity = 1.5F;
                float pInaccuracy = 0.0F;
                float gravity = 0.003F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
            if (TwilightForestExtra.isIceBombItem(itemStack)) {
                float pVelocity = 0.75F;
                float pInaccuracy = 0.0F;
                float gravity = 0.025F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy, -20F);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
        }


        if (ModUtils.ImmersiveEngineeringLoaded()) {
            if (ImmersiveEngineeringExtra.isRailgunItem(itemStack)) {
                int remainTick = player.getUseItemRemainingTicks();
                float pVelocity = 20;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
            if (ImmersiveEngineeringExtra.isRevolverItem(itemStack)) {
                if (ImmersiveEngineeringExtra.hasKey(itemStack, "reload")) return;
                NonNullList<ItemStack> bullets = ImmersiveEngineeringExtra.getBullets(itemStack);
                ItemStack bulletStack = bullets.get(0);
                Item bullet0 = bulletStack.getItem();
                if (!ImmersiveEngineeringExtra.isBull(bullet0)) return;
                if (!ImmersiveEngineeringExtra.isTypeNotNull(bullet0)) return;
                Vec3 vec = player.getLookAngle();
                int count = ImmersiveEngineeringExtra.getProjectileCount(bullet0, player);
                if (count == 1) {
                    float pVelocity = 3;
                    float pInaccuracy = 0.0F;
                    double gravity = ImmersiveEngineeringExtra.getGravity(itemStack, player, bulletStack, vec);
                    originPos = new Vec3(player.getX() + vec.x * 1.5D, player.getY() + (double) player.getEyeHeight() + vec.y * 1.5D, player.getZ() + vec.z * 1.5D);
                    Vec3 vec3 = ModUtils.calculateVec(player, pVelocity, pInaccuracy, new Vec3(vec.x * 1.5D, vec.y * 1.5D, vec.z * 1.5D)).scale(2.0D);
                    this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, (float) gravity, itemStack);
                } else {
                    for (int i = 0; i < count; ++i) {
                        Vec3 vecDir = vec.add(player.getRandom().nextGaussian() * 0.1D, player.getRandom().nextGaussian() * 0.1D, player.getRandom().nextGaussian() * 0.1D);
                        float pVelocity = 3;
                        float pInaccuracy = 0.0F;
                        double gravity = ImmersiveEngineeringExtra.getGravity(itemStack, player, bulletStack, vecDir);
                        originPos = new Vec3(player.getX() + vecDir.x * 1.5D, player.getY() + (double) player.getEyeHeight() + vecDir.y * 1.5D, player.getZ() + vecDir.z * 1.5D);
                        Vec3 vec3 = ModUtils.calculateVec(player, pVelocity, pInaccuracy, new Vec3(vecDir.x * 1.5D, vecDir.y * 1.5D, vecDir.z * 1.5D)).scale(2.0D);
                        this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, (float) gravity, itemStack);
                    }
                }
            }
            if (ImmersiveEngineeringExtra.isChemthrowerItem(itemStack)) {
                FluidStack fs = ImmersiveEngineeringExtra.getFluid(itemStack);
                if (fs.isEmpty()) return;
                boolean isGas = fs.getFluid().is(Tags.Fluids.GASEOUS);
                float scatter = isGas ? 0.25F : 0.15F;
                float range = isGas ? 0.5F : 1.0F;
                if (ImmersiveEngineeringExtra.hasUpgradesFocus(itemStack)) {
                    range += 0.25F;
                    scatter = 0.025F;
                }
                float pVelocity = 3.0F;
                float pInaccuracy = 0.0F;
                float gravity = (float) ImmersiveEngineeringExtra.getFluidGravity(fs);
                Vec3 vecDir = player.getLookAngle().add(player.getRandom().nextGaussian() * (double) scatter, player.getRandom().nextGaussian() * (double) scatter, player.getRandom().nextGaussian() * (double) scatter);
                originPos = new Vec3(player.getX(), player.getY() + (double) player.getEyeHeight(), player.getZ());
                Vec3 base = new Vec3(vecDir.x * 0.25D, vecDir.y * 0.25D, vecDir.z * 0.25D);
                Vec3 second = ModUtils.calculateVec(player, pVelocity, pInaccuracy, base);
                Vec3 vec3 = new Vec3(second.add(vecDir.scale(range)).toVector3f());
                if (!player.onGround())
                    vec3 = new Vec3(second.subtract(vecDir.scale(0.0025D * (double) range)).toVector3f());
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
        }


        if (ModUtils.AlexCavesLoaded()) {
            if (AlexCavesExtra.isDreadbow(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                float power = AlexCavesExtra.getPowerForTime(i, itemStackUsing);
                float gravity = 0F;
                float maxDist = 128.0F * power;
                HitResult realHitResult = ProjectileUtil.getHitResultOnViewVector(player, Entity::canBeHitByProjectile, maxDist);
                if (realHitResult.getType() == HitResult.Type.MISS) {
                    realHitResult = ProjectileUtil.getHitResultOnViewVector(player, Entity::canBeHitByProjectile, power * 42.0F);
                }
                Vec3 mutableSkyPos = new Vec3(realHitResult.getLocation().x, realHitResult.getLocation().y, realHitResult.getLocation().z);
                if (ModUtils.CataclysmLoaded() && LEnderCataclysmExtra.isVoidScatterArrow(player.getProjectile(itemStackUsing)))
                    originPos = new Vec3(player.getX(), player.getEyeY() - 0.10000000149011612D, player.getZ());
                Vec3 vec3 = mutableSkyPos.subtract(originPos).normalize();
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (AlexCavesExtra.isExtinctionSpear(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                float power = ModUtils.getPowerForTime(i);
                if ((double) power <= 0.1D) return;
                float pVelocity = power * 3.5F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (AlexCavesExtra.isLimestoneSpear(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                float power = ModUtils.getPowerForTime(i);
                if ((double) power <= 0.1D) return;
                float pVelocity = power * 2.5F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (AlexCavesExtra.isSeaStaffItem(itemStack)) {
                double dist = 128;
                float gravity = 0.0F;
                Entity closestValid = null;
                Vec3 playerEyes = player.getEyePosition(1.0F);
                HitResult hitresult = pLevel.clip(new ClipContext(playerEyes, playerEyes.add(player.getLookAngle().scale(dist)), ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, player));
                if (hitresult.getType() == HitResult.Type.ENTITY) {
                    EntityHitResult result = (EntityHitResult) hitresult;
                    Entity entity = result.getEntity();
                    if (!entity.equals(player) && !player.isAlliedTo(entity) && !entity.isAlliedTo(player) && entity instanceof Mob && player.hasLineOfSight(entity)) {
                        closestValid = entity;
                    }
                } else {
                    Vec3 at = hitresult.getLocation();
                    AABB around = new AABB(at.add(-0.5F, -0.5F, -0.5F), at.add(0.5F, 0.5F, 0.5F)).inflate(15);
                    for (Entity entity : pLevel.getEntitiesOfClass(LivingEntity.class, around.inflate(dist))) {
                        if (!entity.equals(player) && !player.isAlliedTo(entity) && !entity.isAlliedTo(player) && entity instanceof Mob && player.hasLineOfSight(entity)) {
                            if (closestValid == null || entity.distanceToSqr(at) < closestValid.distanceToSqr(at)) {
                                closestValid = entity;
                            }
                        }
                    }
                }
                if (closestValid != null) {
                    Vec3 endVec = new Vec3(closestValid.getX(), closestValid.getY(), closestValid.getZ());
                    Vec3 vec3 = endVec.subtract(originPos).normalize();
                    System.out.println("!??");
                    this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
                }
            }
        }


        if (ModUtils.TheBumblezoneLoaded()) {
            if (TheBumblezoneExtra.isCrystalCannon(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int remainingDuration = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                originPos = new Vec3(player.getX(), player.getEyeY() - 0.25D, player.getZ());
                Vec3 upVector = player.getUpVector(1.0F);
                Vec3 viewVector = player.getViewVector(1.0F);
                Vector3f shootVector = viewVector.toVector3f();
                float pVelocity = 1.9F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                if (remainingDuration < 20) return;
                int crystalsToSpawn = TheBumblezoneExtra.getNumberOfCrystals(itemStackUsing);
                for (int i = 0; i < crystalsToSpawn; ++i) {
                    float offset = 0.0F;
                    if (i == 1) {
                        offset = player.getRandom().nextFloat() * 5.0F + 3.5F;
                    } else if (i == 2) {
                        offset = player.getRandom().nextFloat() * 5.0F - 11.5F;
                    } else if (i != 0) {
                        offset = player.getRandom().nextFloat() * 10.0F - 5.0F;
                    }
                    if (i != 0) {
                        Quaternionfc quaternion1 = new Quaternionf(upVector.x(), upVector.y(), upVector.z(), offset);
                        shootVector.rotate(quaternion1);
                    }
                    Vec3 base = new Vec3(shootVector.x(), shootVector.y() + 0.01F, shootVector.z());
                    Vec3 vec3 = ModUtils.calculateVec(player, pVelocity, pInaccuracy, base);
                    this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
                }
            }
            if (TheBumblezoneExtra.isStingerSpear(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                if (i < 10) return;
                float pVelocity = 3.0F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (TheBumblezoneExtra.isPollenPuff(itemStack) || TheBumblezoneExtra.isDirtPellet(itemStack)) {
                float pVelocity = 1.5F;
                float pInaccuracy = 0.0F;
                float gravity = 0.03F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
        }


        if (ModUtils.AetherLoaded()) {
            if (AetherExtra.isPhoenixBowItem(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                float power = ModUtils.getPowerForTime(i);
                float pVelocity = power * 3.0F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                if (ModUtils.CataclysmLoaded() && LEnderCataclysmExtra.isVoidScatterArrow(player.getProjectile(itemStackUsing)))
                    originPos = new Vec3(player.getX(), player.getEyeY() - 0.10000000149011612D, player.getZ());
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (AetherExtra.isLightningKnifeItem(itemStack)) {
                float pVelocity = 0.8F;
                float pInaccuracy = 0.0F;
                float gravity = 0.03F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
            if (AetherExtra.isHammerOfKingbdogzItem(itemStack)) {
                float pVelocity = 3.0F;
                float pInaccuracy = 0.0F;
                float gravity = 0.0F;
                float rotationPitch = player.getXRot();
                float rotationYaw = player.getYRot();
                float x = -Mth.sin(rotationYaw * 0.017453292F) * Mth.cos(rotationPitch * 0.017453292F);
                float y = -Mth.sin(rotationPitch * 0.017453292F);
                float z = Mth.cos(rotationYaw * 0.017453292F) * Mth.cos(rotationPitch * 0.017453292F);
                Vec3 vec3 = ModUtils.calculateVec(player, pVelocity, pInaccuracy, new Vec3(x, y, z));
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
            if (AetherExtra.isDartShooterItem(itemStackUsing)) {
                float pVelocity = 3.1F;
                float pInaccuracy = 0.0F;
                float gravity = 0.0F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
        }


        if (ModUtils.AlexsMobsLoaded()) {
            if (AlexsMobsExtra.isItemPocketSand(itemStack)) {
                ItemStack ammo = AlexsMobsExtra.findAmmoItemPocketSand(itemStack, player);
                if (ammo.isEmpty() && !player.isCreative())
                    return;
                float pVelocity = 1.2F;
                float pInaccuracy = 0.0F;//11
                float gravity = 0.029999999329447F;
                Vec3 base = player.getViewVector(1.0F);
                double f = base.x;
                double f1 = base.y;
                double f2 = base.z;
                boolean left = player.getUsedItemHand() == InteractionHand.OFF_HAND && player.getMainArm() == HumanoidArm.RIGHT || player.getUsedItemHand() == InteractionHand.MAIN_HAND && player.getMainArm() == HumanoidArm.LEFT;
                float rot = player.yHeadRot + (float) (!left ? 60 : -60);
                originPos = new Vec3(player.getX() - (double) player.getBbWidth() * 0.5D * (double) Mth.sin(rot * 0.017453292F), player.getEyeY() - 0.20000000298023224D, player.getZ() + (double) player.getBbWidth() * 0.5D * (double) Mth.cos(rot * 0.017453292F));
                Vec3 vec3 = (new Vec3(f, f1, f2)).normalize().add(
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy,
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy,
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy).scale((double) pVelocity);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
            if (AlexsMobsExtra.isItemHemolymphBlaster(itemStack)) {
                ItemStack ammo = AlexsMobsExtra.findAmmoItemHemolymphBlaster(itemStack, player);
                if (ammo.isEmpty() && !player.isCreative() || !player.isCreative() && !(itemStack.getDamageValue() < itemStack.getMaxDamage() - 1))
                    return;
                Vec3 base = player.getViewVector(1.0F);
                float pVelocity = 3.0F;
                float pInaccuracy = 0.0F;//3
                float gravity = 0.019999999552965F;
                double f = base.x;
                double f1 = base.y;
                double f2 = base.z;
                boolean left = player.getUsedItemHand() == InteractionHand.OFF_HAND && player.getMainArm() == HumanoidArm.RIGHT || player.getUsedItemHand() == InteractionHand.MAIN_HAND && player.getMainArm() == HumanoidArm.LEFT;
                float rot = player.yHeadRot + (float) (!left ? 60 : -60);
                originPos = new Vec3(player.getX() - (double) player.getBbWidth() * 0.5D * (double) Mth.sin(rot * 0.017453292F), player.getEyeY() - 0.20000000298023224D, player.getZ() + (double) player.getBbWidth() * 0.5D * (double) Mth.cos(rot * 0.017453292F));
                Vec3 vec3 = (new Vec3(f, f1, f2)).normalize().add(
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy,
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy,
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy).scale((double) pVelocity);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
            if (AlexsMobsExtra.isItemBloodSprayer(itemStack)) {
                ItemStack ammo = AlexsMobsExtra.findAmmoItemBloodSprayer(itemStack, player);
                if (ammo.isEmpty() && !player.isCreative() || !player.isCreative() && !(itemStack.getDamageValue() < itemStack.getMaxDamage() - 1))
                    return;
                Vec3 base = player.getViewVector(1.0F);
                float pVelocity = 1.0F;
                float gravity = 0.0599999986589F;
                float pInaccuracy = 0.0F;//3
                double f = base.x;
                double f1 = base.y;
                double f2 = base.z;
                boolean left = player.getUsedItemHand() == InteractionHand.OFF_HAND && player.getMainArm() == HumanoidArm.RIGHT || player.getUsedItemHand() == InteractionHand.MAIN_HAND && player.getMainArm() == HumanoidArm.LEFT;
                float rot = player.yHeadRot + (float) (!left ? 60 : -60);
                originPos = new Vec3(player.getX() - (double) player.getBbWidth() * 0.5D * (double) Mth.sin(rot * 0.017453292F), player.getEyeY() - 0.20000000298023224D, player.getZ() + (double) player.getBbWidth() * 0.5D * (double) Mth.cos(rot * 0.017453292F));
                Vec3 vec3 = (new Vec3(f, f1, f2)).normalize().add(
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy,
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy,
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy).scale((double) pVelocity);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
            if (AlexsMobsExtra.isItemStinkRay(itemStackUsing)) {
                if (!(itemStackUsing.getDamageValue() < itemStackUsing.getMaxDamage() - 1)) return;
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                if (i < 10) return;
                float pVelocity = 0.2F + ModUtils.getPowerForTime(i) * 0.4F;
                float gravity = 0.0F;
                float pInaccuracy = 0.0F;
                Vec3 base = player.getViewVector(1.0F);
                double f = base.x;
                double f1 = base.y;
                double f2 = base.z;
                boolean left = player.getUsedItemHand() == InteractionHand.OFF_HAND && player.getMainArm() == HumanoidArm.RIGHT || player.getUsedItemHand() == InteractionHand.MAIN_HAND && player.getMainArm() == HumanoidArm.LEFT;
                float rot = player.yHeadRot + (float) (!left ? 60 : -60);
                originPos = new Vec3(player.getX() - (double) player.getBbWidth() * 0.5D * (double) Mth.sin(rot * 0.017453292F), player.getEyeY() - 0.20000000298023224D, player.getZ() + (double) player.getBbWidth() * 0.5D * (double) Mth.cos(rot * 0.017453292F));
                Vec3 vec3 = (new Vec3(f, f1, f2)).normalize().add(
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy,
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy,
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy).scale(pVelocity);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (AlexsMobsExtra.isItemVineLasso(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                Vec3 base = player.getViewVector(1.0F);
                float pInaccuracy = 0.0F;//1
                float pVelocity = ModUtils.getPowerForTime(i);
                float gravity = 0.0199999995529652F;
                double f = base.x;
                double f1 = base.y;
                double f2 = base.z;
                originPos = new Vec3(player.getX(), player.getEyeY() + 0.15000000596046448D, player.getZ());
                Vec3 vec3 = (new Vec3(f, f1, f2)).normalize().add(
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy,
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy,
                        player.getRandom().nextGaussian() * 0.007499999832361937D * (double) pInaccuracy).scale(pVelocity);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
        }


        if (ModUtils.ArchBowsLoaded()) {
            if (ArchBowsExtra.isFlatBowItem(itemStackUsing) || ArchBowsExtra.isLongBowItem(itemStackUsing) || ArchBowsExtra.isRecurveBowItem(itemStackUsing) || ArchBowsExtra.isShortBowItem(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                float power = ArchBowsExtra.getPowerForTime(itemStackUsing, i);
                float pVelocity = power * ArchBowsExtra.getVelocity(itemStackUsing);
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (ArchBowsExtra.isPistolCrossbowItem(itemStack) || ArchBowsExtra.isArbalestItem(itemStack) || ArchBowsExtra.isHeavyCrossbowItem(itemStack)) {
                if (!CrossbowItem.isCharged(itemStack)) return;
                float pProjectileAngle = 0;
                float pVelocity = ArchBowsExtra.getVelocityCrossbow(itemStack);
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                Vec3 vec31 = player.getUpVector(1.0F);
                Vec3 vec32 = player.getViewVector(1.0F);
                List<ItemStack> list = ModUtils.getCrossbowProjectileInfo(itemStack);
                for (int j = 0; j < list.size(); ++j) {
                    ItemStack itemstack = list.get(j);
                    if (!itemstack.isEmpty()) {
                        if (itemstack.is(Items.FIREWORK_ROCKET)) {
                            originPos = new Vec3(player.getX(), player.getEyeY() - (double) 0.15F, player.getZ());
                            gravity = 0;
                        }
                        switch (j) {
                            case 0 -> pProjectileAngle = 0.0F;
                            case 1 -> pProjectileAngle = -10.0F;
                            case 2 -> pProjectileAngle = 10.0F;
                        }
                        Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(pProjectileAngle * Mth.DEG_TO_RAD, vec31.x, vec31.y, vec31.z);
                        Vector3f vector3f = vec32.toVector3f().rotate(quaternionf);
                        Vec3 vec3 = ModUtils.calculateVec(player, pVelocity, pInaccuracy, new Vec3(vector3f.x(), vector3f.y(), vector3f.z()));
                        this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
                    }
                }
            }
        }


        if (ModUtils.VampirismLoaded()) {
            if (VampirismExtra.isSingleCrossbowItem(itemStack) || VampirismExtra.isDoubleCrossbowItem(itemStack)) {
                if (!CrossbowItem.isCharged(itemStack)) return;
                float pProjectileAngle = 0;
                float pVelocity = VampirismExtra.getShootingPowerMod(itemStack);
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                Vec3 vec31 = player.getUpVector(1.0F);
                Vec3 vec32 = player.getViewVector(1.0F);
                List<ItemStack> list = ModUtils.getCrossbowProjectileInfo(itemStack);
                for (int j = 0; VampirismExtra.isDoubleCrossbowItem(itemStack) ? (j < 2 && j < list.size()) : (j < list.size()); ++j) {
                    ItemStack itemstack = list.get(j);
                    if (!itemstack.isEmpty()) {
                        if (itemstack.is(Items.FIREWORK_ROCKET)) {
                            originPos = new Vec3(player.getX(), player.getEyeY() - (double) 0.15F, player.getZ());
                            gravity = 0;
                        }
                        Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(pProjectileAngle * Mth.DEG_TO_RAD, vec31.x, vec31.y, vec31.z);
                        Vector3f vector3f = vec32.toVector3f().rotate(quaternionf);
                        Vec3 vec3 = ModUtils.calculateVec(player, pVelocity, pInaccuracy, new Vec3(vector3f.x(), vector3f.y(), vector3f.z()));
                        this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
                    }
                }
            }
            if (VampirismExtra.isTechCrossbowItem(itemStack)) {
                if (!CrossbowItem.isCharged(itemStack)) return;
                float pProjectileAngle = 0;
                float pVelocity = VampirismExtra.getShootingPowerMod(itemStack);
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                Vec3 vec31 = player.getUpVector(1.0F);
                Vec3 vec32 = player.getViewVector(1.0F);
                List<ItemStack> list = ModUtils.getCrossbowProjectileInfo(itemStack);
                ItemStack itemstack = VampirismExtra.getProjectile(player, itemStack, list);
                if (!itemstack.isEmpty()) {
                    if (itemstack.is(Items.FIREWORK_ROCKET)) {
                        originPos = new Vec3(player.getX(), player.getEyeY() - (double) 0.15F, player.getZ());
                        gravity = 0;
                    }
                    Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(pProjectileAngle * Mth.DEG_TO_RAD, vec31.x, vec31.y, vec31.z);
                    Vector3f vector3f = vec32.toVector3f().rotate(quaternionf);
                    Vec3 vec3 = ModUtils.calculateVec(player, pVelocity, pInaccuracy, new Vec3(vector3f.x(), vector3f.y(), vector3f.z()));
                    this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
                }
            }
            if (VampirismExtra.isHolyWaterSplashBottleItem(itemStack)) {
                float pVelocity = 0.5F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy, -20F);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
        }


        if (ModUtils.L2WeaponryLoaded()) {
            if (L2WeaponryExtra.isJavelinItem(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                if (i < 10) return;
                float pVelocity = 2.5F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                if (ModUtils.CataclysmLoaded() && LEnderCataclysmExtra.isVoidScatterArrow(player.getProjectile(itemStackUsing)))
                    originPos = new Vec3(player.getX(), player.getEyeY() - 0.10000000149011612D, player.getZ());
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
        }


        if (ModUtils.L2ArcheryLoaded()) {
            if (L2ArcheryExtra.isGenericBowItem(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                float power = L2ArcheryExtra.getPowerForTime(itemStackUsing, player, i);
                float pVelocity = power * 3.0F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                if (ModUtils.CataclysmLoaded() && LEnderCataclysmExtra.isVoidScatterArrow(player.getProjectile(itemStackUsing)))
                    originPos = new Vec3(player.getX(), player.getEyeY() - 0.10000000149011612D, player.getZ());
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
        }


        if (ModUtils.AdventOfAscension3Loaded()) {
            if (AdventOfAscension3Extra.isBaseBow(itemStackUsing)) {
                int remainTick = player.getUseItemRemainingTicks();
                int i = itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick;
                int charge = (int) ((float) (itemStackUsing.getItem().getUseDuration(itemStackUsing) - remainTick) * AdventOfAscension3Extra.getDrawSpeedMultiplier(itemStackUsing));
                if (charge < 0) return;
                float power = ModUtils.getPowerForTime(i);
                if ((double) power < 0.1D) return;
                float pVelocity = power * 3.0F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                originPos = new Vec3(player.getX(), player.getEyeY() - 0.10000000149011612D, player.getZ());
                if (ModUtils.CataclysmLoaded() && LEnderCataclysmExtra.isVoidScatterArrow(player.getProjectile(itemStackUsing)))
                    originPos = new Vec3(player.getX(), player.getEyeY() - 0.10000000149011612D, player.getZ());
                Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (AdventOfAscension3Extra.isBaseCrossbow(itemStack)) {
                if (!CrossbowItem.isCharged(itemStack)) return;
                float pVelocity = containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
                float pInaccuracy = 0.0F;
                float gravity = 0.05F;
                float projectileAngle = -10.0F;
                List<ItemStack> list = ModUtils.getCrossbowProjectileInfo(itemStack);
                ItemStack itemstack0 = list.get(0);
                if (!itemstack0.isEmpty()) {
                    if (itemstack0.is(Items.FIREWORK_ROCKET)) {
                        originPos = new Vec3(player.getX(), player.getEyeY() - (double) 0.15F, player.getZ());
                        gravity = 0;
                    }
                    Vec3 vecUp = player.getUpVector(1);
                    Quaternionf angle = new Quaternionf().setAngleAxis(0, vecUp.x, vecUp.y, vecUp.z);
                    Vector3f lookVec = player.getViewVector(1).toVector3f().rotate(angle);
                    Vec3 vec3 = ModUtils.calculateVec(player, pVelocity, pInaccuracy, new Vec3(lookVec.x(), lookVec.y(), lookVec.z()));
                    this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
                }
                for (int i = 1; i < list.size(); ++i) {
                    ItemStack itemstack = list.get(i);
                    if (!itemstack.isEmpty()) {
                        if (itemstack.is(Items.FIREWORK_ROCKET)) {
                            originPos = new Vec3(player.getX(), player.getEyeY() - (double) 0.15F, player.getZ());
                            gravity = 0;
                        }
                        Vec3 vecUp = player.getUpVector(1);
                        Quaternionf angle = new Quaternionf().setAngleAxis(projectileAngle * Constants.DEG_TO_RAD, vecUp.x, vecUp.y, vecUp.z);
                        Vector3f lookVec = player.getViewVector(1).toVector3f().rotate(angle);
                        Vec3 vec3 = ModUtils.calculateVec(player, pVelocity, pInaccuracy, new Vec3(lookVec.x(), lookVec.y(), lookVec.z()));
                        this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
                        projectileAngle = projectileAngle < 0.0F ? projectileAngle * -1.0F : projectileAngle / -2.0F;
                    }
                }
            }
            if (AdventOfAscension3Extra.isBaseStaff(itemStack)) {
                float pVelocity = 3.0F;
                float pInaccuracy = 0.0F;
                float gravity = 0.0F;
                gravity = AdventOfAscension3Extra.getNewGravity(itemStack, gravity);
                originPos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
                Vec3 base = new Vec3(
                        -Mth.sin(player.getYRot() / 180.0F * (float) Math.PI) * Mth.cos(player.getXRot() / 180.0F * (float) Math.PI),
                        -Mth.sin(player.getXRot() / 180.0F * (float) Math.PI),
                        Mth.cos(player.getYRot() / 180.0F * (float) Math.PI) * Mth.cos(player.getXRot() / 180.0F * (float) Math.PI));
                Vec3 motionVec = (new Vec3(base.x, base.y, base.z)).normalize().add(
                        player.getRandom().nextGaussian() * 0.0075F * pInaccuracy,
                        player.getRandom().nextGaussian() * 0.0075F * pInaccuracy,
                        player.getRandom().nextGaussian() * (double) 0.0075F * pInaccuracy).scale(pVelocity);
                this.renderTracePoint(motionVec, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
            if (AdventOfAscension3Extra.isBaseShotgun(itemStack) || AdventOfAscension3Extra.isBaseCannon(itemStack) || AdventOfAscension3Extra.isBaseGun(itemStack) && !AdventOfAscension3Extra.isBaseSniper(itemStack) && !AdventOfAscension3Extra.isBaseCannon(itemStack) && !AdventOfAscension3Extra.isBaseThrownWeapon(itemStack) && !AdventOfAscension3Extra.isBaseShotgun(itemStack)) {
                float pVelocity = 3.0F;
                float pInaccuracy = 0.0F;
                float gravity = 0.0F;
                gravity = AdventOfAscension3Extra.getNewGravity(itemStack, gravity);
                originPos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
                Vec3 base = new Vec3(
                        -Mth.sin(player.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(player.getXRot() * Mth.DEG_TO_RAD),
                        (-Mth.sin(player.getXRot() * Mth.DEG_TO_RAD)),
                        (Mth.cos(player.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(player.getXRot() * Mth.DEG_TO_RAD))
                );
                base = AdventOfAscension3Extra.getNewVec(itemStack, player, base);
                Vec3 motionVec = (new Vec3(base.x, base.y, base.z)).normalize().add(
                        player.getRandom().nextGaussian() * 0.0075F * pInaccuracy,
                        player.getRandom().nextGaussian() * 0.0075F * pInaccuracy,
                        player.getRandom().nextGaussian() * (double) 0.0075F * pInaccuracy).scale(pVelocity);
                originPos = AdventOfAscension3Extra.getNewOriginPos(itemStack, originPos);
                this.renderTracePoint(motionVec, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
            if (AdventOfAscension3Extra.isBaseSniper(itemStack)) {
                float pVelocity = 20.0F;
                float pInaccuracy = 0.0F;
                float gravity = 0.0F;
                originPos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
                if (!player.onGround() || !player.isShiftKeyDown()) {
                    Vec3 vec3 = ModUtils.calculateShootVec(player, pVelocity, pInaccuracy);
                    this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStack);
                } else {
                    Vec3 base = AdventOfAscension3Extra.getSniperVec(player);
                    Vec3 motionVec = (new Vec3(base.x, base.y, base.z)).normalize().add(
                            player.getRandom().nextGaussian() * 0.0075F * pInaccuracy,
                            player.getRandom().nextGaussian() * 0.0075F * pInaccuracy,
                            player.getRandom().nextGaussian() * (double) 0.0075F * pInaccuracy).scale(pVelocity);
                    this.renderTracePoint(motionVec, originPos, viewPos, matrix, player, builder, gravity, itemStack);
                }
            }
            if (AdventOfAscension3Extra.isBaseBlaster(itemStack)) {
                float gravity = 0.0F;
                originPos = AdventOfAscension3Extra.getBlasterStartVec(itemStack, player);
                Vec3 end = AdventOfAscension3Extra.getBlasterEndVec(itemStack, player);
                Vec3 vec3 = end.subtract(originPos).normalize();
                this.renderTracePoint(vec3, originPos, viewPos, matrix, player, builder, gravity, itemStackUsing);
            }
            if (AdventOfAscension3Extra.isBaseThrownWeapon(itemStack)) {
                float pVelocity = 3.0F;
                pVelocity = AdventOfAscension3Extra.getNewSpeed(itemStack, pVelocity);
                float pInaccuracy = 0.0F;
                float gravity = 0.0F;
                gravity = AdventOfAscension3Extra.getNewGravity(itemStack, gravity);
                originPos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
                Vec3 base = new Vec3(
                        -Mth.sin(player.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(player.getXRot() * Mth.DEG_TO_RAD),
                        (-Mth.sin(player.getXRot() * Mth.DEG_TO_RAD)),
                        (Mth.cos(player.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(player.getXRot() * Mth.DEG_TO_RAD))
                );
                base = AdventOfAscension3Extra.getNewVec(itemStack, player, base);
                Vec3 motionVec = (new Vec3(base.x, base.y, base.z)).normalize().add(
                        player.getRandom().nextGaussian() * 0.0075F * pInaccuracy,
                        player.getRandom().nextGaussian() * 0.0075F * pInaccuracy,
                        player.getRandom().nextGaussian() * (double) 0.0075F * pInaccuracy).scale(pVelocity);
                originPos = AdventOfAscension3Extra.getNewOriginPos(itemStack, originPos);
                this.renderTracePoint(motionVec, originPos, viewPos, matrix, player, builder, gravity, itemStack);
            }
        }


        buffer.endBatch(BUFFS);
    }

    private void renderTracePoint(Vec3 vec3, Vec3 origin, Vec3 view, PoseStack matrix, Player player, VertexConsumer builder, float gravity, ItemStack stack) {
        boolean renderPlane = true;
        float step = 0.7F, begin = 1, end = 500;
        if (ModUtils.CGMLoaded()) {
            if (MrcrayfishGunsExtra.isGun(stack)) {
                step = 0.6F;
                begin = 0.4F;
            }
        }

        double xo = 0, yo = 0, zo = 0;
        int count = 0, stp = 4;
        if (TjeModConfig.renderLine) {
            xo = origin.x + vec3.x * begin;
            yo = origin.y + vec3.y * begin - 0.5 * gravity * Math.pow(begin, 2);
            zo = origin.z + vec3.z * begin;
        }

        for (float t = begin; t < end; t += step) {
            int r = TjeModConfig.cubeRed, g = TjeModConfig.cubeGreen, b = TjeModConfig.cubeBlue, a = TjeModConfig.cubeAlpha;
            float hw = (float) TjeModConfig.cubeSize, h = (float) TjeModConfig.cubeSize;

            int lr = TjeModConfig.lineRed, lg = TjeModConfig.lineGreen, lb = TjeModConfig.lineBlue, la = TjeModConfig.lineAlpha;
            float lw = (float) TjeModConfig.lineWidth;

            double x = origin.x + vec3.x * t;
            double y = origin.y + vec3.y * t - 0.5 * gravity * Math.pow(t, 2);
            double z = origin.z + vec3.z * t;

            Vec3 pos = new Vec3(x, y, z);
            if (TjeModConfig.renderPoint) {
                if (ModUtils.isVanillaItems(stack) || ModUtils.isCGMItems(stack) || ModUtils.isCataclysmItems(stack)
                        || ModUtils.isBlueSkiesItems(stack) || ModUtils.isIceAndFireItems(stack)
                        || ModUtils.isTwilightForestItems(stack) || ModUtils.isImmersiveEngineeringItems(stack)
                        || ModUtils.isAlexCavesItems(stack)
                        || ModUtils.isTheBumblezoneItems(stack)
                        || ModUtils.isAetherItems(stack)
                        || ModUtils.isAlexsMobsItems(stack)
                        || ModUtils.isArchBowsItems(stack)
                        || ModUtils.isVampirismItems(stack)
                        || ModUtils.isL2WeaponryItems(stack)
                        || ModUtils.isL2ArcheryItems(stack)
                        || ModUtils.isAdventOfAscension3Items(stack)
                ) {
                    matrix.pushPose();
                    matrix.translate(-view.x, -view.y, -view.z);
                    float minU = 0, maxU = 1, minV = 0, maxV = 1;
                    matrix.translate(x, y, z);
                    if (TjeModConfig.renderLine) {
                        ModUtils.drawLineFullLight(matrix, player, xo, yo, zo, x, y, z, count, stp, lr, lg, lb, la, lw);
                    }
                    if (TjeModConfig.renderCube) {
                        ModUtils.drawCubeFullLight(builder, matrix, 0, 0, 0, hw, h, minU, maxU, minV, maxV, r, g, b, a);
                    }
                    matrix.popPose();
                    if (renderPlane) {
                        if (!(player.level().getBlockState(ModUtils.getCorrectPos(player.level(), pos)).getBlock() instanceof AirBlock || player.level().getBlockState(ModUtils.getCorrectPos(player.level(), pos)).getBlock() instanceof LiquidBlock)) {
                            matrix.pushPose();
                            h = Mth.clamp(h * 0.8F, 0.01F, 10F);
                            hw = Mth.clamp(hw * 4F, 0.01F, 20F);
                            r = 255;
                            g = 0;
                            b = 0;
                            a = 60;
                            y += 0.3F;
                            matrix.translate(-view.x, -view.y, -view.z);
                            matrix.translate(x, y, z);
                            ModUtils.drawCubeFullLight(builder, matrix, 0, 0, 0, hw, h, minU, maxU, minV, maxV, r, g, b, a);
                            matrix.popPose();
                            renderPlane = false;
                        }
                    }

                    if (TjeModConfig.renderLine) {
                        if (count % stp == 0) {
                            xo = x;
                            yo = y;
                            zo = z;
                        }
                        count++;
                    }
                }
            }
            this.playHitSound(pos, player, stack);
        }
    }

    private void playHitSound(Vec3 pos, Player player, ItemStack stack) {
        if (!TjeModConfig.targetSound) return;
        if (ModUtils.isVanillaItemsSound(stack) || ModUtils.isCGMItemsSound(stack) || ModUtils.isCataclysmItemsSound(stack)
                || ModUtils.isBlueSkiesItemsSound(stack) || ModUtils.isIceAndFireItemsSound(stack)
                || ModUtils.isTwilightForestItemsSound(stack) || ModUtils.isImmersiveEngineeringItemsSound(stack)
                || ModUtils.isAlexCavesItemsSound(stack)
                || ModUtils.isTheBumblezoneItemsSound(stack)
                || ModUtils.isAetherItemsSound(stack)
                || ModUtils.isAlexsMobsItemsSound(stack)
                || ModUtils.isArchBowsItemsSound(stack)
                || ModUtils.isVampirismItemsSound(stack)
                || ModUtils.isL2WeaponryItemsSound(stack)
                || ModUtils.isL2ArcheryItemsSound(stack)
                || ModUtils.isAdventOfAscension3ItemsSound(stack)
        ) {
            List<LivingEntity> targets = ModUtils.checkEntityOnBlock(new BlockPos(new Vec3i(Mth.floor(pos.x()), Mth.floor(pos.y()), Mth.floor(pos.z()))), player.level(), 0, LivingEntity.class);
            targets.remove(player);
            for (Entity entity : targets) {
                if (entity instanceof LivingEntity entity2 && entity2 != Minecraft.getInstance().cameraEntity) {
                    if (this.soundPlayCount % 60 == 0) player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0F, 2F);
                }
            }
            if (this.soundPlayCount % 60 == 0) this.soundPlayCount = 0;
            this.soundPlayCount++;
        }
    }
}
