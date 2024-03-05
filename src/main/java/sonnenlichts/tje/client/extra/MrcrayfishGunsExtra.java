package sonnenlichts.tje.client.extra;

import com.mrcrayfish.guns.common.Gun;
import com.mrcrayfish.guns.common.SpreadTracker;
import com.mrcrayfish.guns.init.ModSyncedDataKeys;
import com.mrcrayfish.guns.item.GrenadeItem;
import com.mrcrayfish.guns.item.GunItem;
import com.mrcrayfish.guns.item.StunGrenadeItem;
import com.mrcrayfish.guns.util.GunEnchantmentHelper;
import com.mrcrayfish.guns.util.GunModifierHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class MrcrayfishGunsExtra {

    public static Vec3 getGunsVec3(ItemStack itemStack, Player player) {
        if (itemStack.getItem() instanceof GunItem gun) {
            if (!(Gun.hasAmmo(itemStack) || player.isCreative())) return Vec3.ZERO;
            Gun modifiedGun = gun.getModifiedGun(itemStack);
            Gun.Projectile projectile = modifiedGun.getProjectile();
            double speed_p = projectile.getSpeed();
            Vec3 dir = getDirection(player, itemStack, gun, modifiedGun);
            double speedModifier = GunEnchantmentHelper.getProjectileSpeedModifier(itemStack);
            double speed = GunModifierHelper.getModifiedProjectileSpeed(itemStack, speed_p * speedModifier);
            return new Vec3(dir.x * speed, dir.y * speed, dir.z * speed);
        }
        return Vec3.ZERO;
    }

    public static Vec3 getNewOriginPos(ItemStack itemStack, Player player) {
        if (itemStack.getItem() instanceof GunItem) {
            if (!(Gun.hasAmmo(itemStack) || player.isCreative())) return Vec3.ZERO;
            double posX = player.xOld + (player.getX() - player.xOld) / 2.0;
            double posY = player.yOld + (player.getY() - player.yOld) / 2.0 + player.getEyeHeight();
            double posZ = player.zOld + (player.getZ() - player.zOld) / 2.0;
            return new Vec3(posX, posY, posZ);
        }
        return Vec3.ZERO;
    }

    public static float getGunProjGravity(ItemStack itemStack, Player player) {
        if (itemStack.getItem() instanceof GunItem gun) {
            if (!(Gun.hasAmmo(itemStack) || player.isCreative())) return 0;
            Gun modifiedGun = gun.getModifiedGun(itemStack);
            Gun.Projectile projectile = modifiedGun.getProjectile();
            boolean isGravity = projectile.isGravity();
            return isGravity ? (float) GunModifierHelper.getModifiedProjectileGravity(itemStack, -0.04) : 0.0f;
        }
        return 0;
    }

    private static Vec3 getDirection(LivingEntity shooter, ItemStack weapon, GunItem item, Gun modifiedGun) {
        float gunSpread = GunModifierHelper.getModifiedSpread(weapon, modifiedGun.getGeneral().getSpread());
        if (gunSpread == 0.0F) {
            return getVectorFromRotation(shooter.getXRot(), shooter.getYRot());
        } else {
            if (shooter instanceof Player) {
                if (!modifiedGun.getGeneral().isAlwaysSpread()) {
                    gunSpread *= SpreadTracker.get((Player) shooter).getSpread(item);
                }

                if ((Boolean) ModSyncedDataKeys.AIMING.getValue((Player) shooter)) {
                    gunSpread *= 0.5F;
                }
            }
            return getVectorFromRotation(shooter.getXRot() - (gunSpread / 2.0F) + 0.3F * gunSpread, shooter.getYHeadRot() - (gunSpread / 2.0F) + 0.3F * gunSpread);
        }
    }

    private static Vec3 getVectorFromRotation(float pitch, float yaw) {
        float f = Mth.cos(-yaw * 0.017453292F - 3.1415927F);
        float f1 = Mth.sin(-yaw * 0.017453292F - 3.1415927F);
        float f2 = -Mth.cos(-pitch * 0.017453292F);
        float f3 = Mth.sin(-pitch * 0.017453292F);
        return new Vec3((f1 * f2), f3, (f * f2));
    }

    public static boolean isGun(ItemStack stack) {
        return stack.getItem() instanceof GunItem;
    }

    public static boolean isGrenade(ItemStack stack) {
        return stack.getItem() instanceof GrenadeItem;
    }

    public static boolean isStunGrenade(ItemStack stack) {
        return stack.getItem() instanceof StunGrenadeItem;
    }

    public static boolean judge(ItemStack stack) {
        return isGun(stack) || isGrenade(stack) || isStunGrenade(stack);
    }
}
