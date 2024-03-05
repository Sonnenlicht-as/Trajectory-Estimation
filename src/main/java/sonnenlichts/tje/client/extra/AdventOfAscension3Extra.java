package sonnenlichts.tje.client.extra;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.tslat.aoa3.content.entity.projectile.cannon.BalloonBombEntity;
import net.tslat.aoa3.content.entity.projectile.gun.ShoeShotEntity;
import net.tslat.aoa3.content.entity.projectile.thrown.SliceStarEntity;
import net.tslat.aoa3.content.item.EnergyProjectileWeapon;
import net.tslat.aoa3.content.item.weapon.blaster.BaseBlaster;
import net.tslat.aoa3.content.item.weapon.bow.BaseBow;
import net.tslat.aoa3.content.item.weapon.cannon.*;
import net.tslat.aoa3.content.item.weapon.crossbow.BaseCrossbow;
import net.tslat.aoa3.content.item.weapon.gun.*;
import net.tslat.aoa3.content.item.weapon.shotgun.BaseShotgun;
import net.tslat.aoa3.content.item.weapon.sniper.BaseSniper;
import net.tslat.aoa3.content.item.weapon.staff.BaseStaff;
import net.tslat.aoa3.content.item.weapon.staff.FireflyStaff;
import net.tslat.aoa3.content.item.weapon.staff.NoxiousStaff;
import net.tslat.aoa3.content.item.weapon.staff.ShyreStaff;
import net.tslat.aoa3.content.item.weapon.thrown.*;

public class AdventOfAscension3Extra {
    public static boolean isBaseBow(ItemStack stack) {
        return stack.getItem() instanceof BaseBow;
    }

    public static float getDrawSpeedMultiplier(ItemStack stack) {
        return ((BaseBow) stack.getItem()).getDrawSpeedMultiplier();
    }

    public static boolean isBaseCrossbow(ItemStack stack) {
        return stack.getItem() instanceof BaseCrossbow;
    }

    public static boolean isBaseStaff(ItemStack stack) {
        return stack.getItem() instanceof BaseStaff;
    }

    public static boolean isBaseGun(ItemStack stack) {
        return stack.getItem() instanceof BaseGun;
    }

    public static Vec3 getNewOriginPos(ItemStack itemStack, Vec3 old) {
        if (itemStack.getItem() instanceof Clownershot || itemStack.getItem() instanceof Overshot)
            return new Vec3(old.x(), old.y() + 0.10000000149011612D, old.z());
        return old;
    }

    public static float getNewGravity(ItemStack itemStack, float old) {
        if (itemStack.getItem() instanceof ShyreStaff) return 0.085F;
        if (itemStack.getItem() instanceof Baronator
                || itemStack.getItem() instanceof Dustometer
                || itemStack.getItem() instanceof AncientBomber
                || itemStack.getItem() instanceof BoomCannon
                || itemStack.getItem() instanceof MissileMaker
                || itemStack.getItem() instanceof Hellfire
                || itemStack.getItem() instanceof Grenade
        ) return 0.075F;
        if (itemStack.getItem() instanceof ShoeFlinger) return 0.13F;
        if (itemStack.getItem() instanceof AquaCannon
                || itemStack.getItem() instanceof BalloonBomber
                || itemStack.getItem() instanceof WitherCannon
                || itemStack.getItem() instanceof WaterBalloonBomber
                || itemStack.getItem() instanceof SmileBlaster
                || itemStack.getItem() instanceof IroCannon
                || itemStack.getItem() instanceof PredatorianBlaster
                || itemStack.getItem() instanceof MechaCannon
                || itemStack.getItem() instanceof HiveHowitzer
                || itemStack.getItem() instanceof HiveBlaster
                || itemStack.getItem() instanceof GhastBlaster
                || itemStack.getItem() instanceof FungalCannon
                || itemStack.getItem() instanceof FlowerCannon
                || itemStack.getItem() instanceof CoralCannon
                || itemStack.getItem() instanceof ClownCannon
                || itemStack.getItem() instanceof CarrotCannon
                || itemStack.getItem() instanceof BulbCannon
                || itemStack.getItem() instanceof BozoBlaster
                || itemStack.getItem() instanceof HardenedParapiranha
                || itemStack.getItem() instanceof RunicBomb
        ) return 0.1F;
        if (itemStack.getItem() instanceof ShoeFlinger
                || itemStack.getItem() instanceof GolderBomber
                || itemStack.getItem() instanceof GhoulCannon
                || itemStack.getItem() instanceof BoulderBomber
                || itemStack.getItem() instanceof BoomBoom
                || itemStack.getItem() instanceof Chakram
                || itemStack.getItem() instanceof SliceStar
                || itemStack.getItem() instanceof Vulkram
                || itemStack.getItem() instanceof GooBall
                || itemStack.getItem() instanceof FireflyStaff
                || itemStack.getItem() instanceof NoxiousStaff
        )
            return 0.05F;
        if (itemStack.getItem() instanceof JackRocker
                || itemStack.getItem() instanceof JackFunger)
            return 0.06F;
        if (itemStack.getItem() instanceof SelyanStickler
                || itemStack.getItem() instanceof PlutonStickler
                || itemStack.getItem() instanceof LuxonStickler
                || itemStack.getItem() instanceof ErebonStickler
        )
            return 0.015F;
        return old;
    }

    public static Vec3 getNewVec(ItemStack itemStack, Player player, Vec3 old) {
        if (itemStack.getItem() instanceof Cyclone)
            return new Vec3(-Mth.sin(player.getYRot() * 3.1415927F / 180.0F) * Mth.cos(player.getXRot() * 3.1415927F / 180.0F) + 0F, -Mth.sin(player.getXRot() * 3.1415927F / 180.0F) + 0.05F, Mth.cos(player.getYRot() * 3.1415927F / 180.0F) * Mth.cos(player.getXRot() * 3.1415927F / 180.0F) + 0F);
        if (itemStack.getItem() instanceof BoulderBomber)
            return new Vec3(-Mth.sin(player.getYRot() * 3.1415927F / 180.0F) * Mth.cos(player.getXRot() * 3.1415927F / 180.0F) + 0F, -Mth.sin(player.getXRot() * 3.1415927F / 180.0F) + 0.325F, Mth.cos(player.getYRot() * 3.1415927F / 180.0F) * Mth.cos(player.getXRot() * 3.1415927F / 180.0F) + 0F);
        return old;
    }

    public static boolean isBaseBlaster(ItemStack stack) {
        return stack.getItem() instanceof BaseBlaster;
    }

    public static Vec3 getBlasterStartVec(ItemStack stack, Player player) {
        float beamDistance = ((BaseBlaster) stack.getItem()).getBeamDistance(stack, player);
        EnergyProjectileWeapon.ShotInfo shotInfo = ((BaseBlaster) stack.getItem()).getPosAndRotForShot(((BaseBlaster) stack.getItem()), player, 1.0F, beamDistance);
        return shotInfo.position();
    }

    public static Vec3 getBlasterEndVec(ItemStack stack, Player player) {
        float beamDistance = ((BaseBlaster) stack.getItem()).getBeamDistance(stack, player);
        EnergyProjectileWeapon.ShotInfo shotInfo = ((BaseBlaster) stack.getItem()).getPosAndRotForShot(((BaseBlaster) stack.getItem()), player, 1.0F, beamDistance);
        return shotInfo.position().add(shotInfo.angle().scale(beamDistance));
    }

    public static boolean isBaseSniper(ItemStack stack) {
        return stack.getItem() instanceof BaseSniper;
    }

    public static Vec3 getSniperVec(Player entity) {
        float velocityMod = 1.0F;
        return new Vec3(-Mth.sin(entity.getYRot() * 3.1415927F / 180.0F) * Mth.cos(entity.getXRot() * 3.1415927F / 180.0F) * velocityMod, (double) (-Mth.sin(entity.getXRot() * 3.1415927F / 180.0F) * velocityMod), (double) (Mth.cos(entity.getYRot() * 3.1415927F / 180.0F) * Mth.cos(entity.getXRot() * 3.1415927F / 180.0F) * velocityMod));
    }

    public static boolean isBaseShotgun(ItemStack stack) {
        return stack.getItem() instanceof BaseShotgun;
    }

    public static boolean isBaseCannon(ItemStack stack) {
        return stack.getItem() instanceof BaseCannon;
    }

    public static boolean isBaseThrownWeapon(ItemStack stack) {
        return stack.getItem() instanceof BaseThrownWeapon;
    }

    public static float getNewSpeed(ItemStack stack, float old) {
        if (stack.getItem() instanceof HardenedParapiranha
                || stack.getItem() instanceof Hellfire
                || stack.getItem() instanceof RunicBomb
                || stack.getItem() instanceof Grenade
        ) return 1.5F;
        return old;
    }
}