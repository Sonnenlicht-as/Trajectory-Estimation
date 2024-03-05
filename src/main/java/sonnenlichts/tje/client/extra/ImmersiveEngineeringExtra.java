package sonnenlichts.tje.client.extra;

import blusunrize.immersiveengineering.api.tool.BulletHandler;
import blusunrize.immersiveengineering.common.entities.RevolvershotEntity;
import blusunrize.immersiveengineering.common.items.BulletItem;
import blusunrize.immersiveengineering.common.items.ChemthrowerItem;
import blusunrize.immersiveengineering.common.items.RailgunItem;
import blusunrize.immersiveengineering.common.items.RevolverItem;
import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

public class ImmersiveEngineeringExtra {
    public static boolean isRailgunItem(ItemStack stack) {
        return stack.getItem() instanceof RailgunItem;
    }

    public static boolean isRevolverItem(ItemStack stack) {
        return stack.getItem() instanceof RevolverItem;
    }

    public static int getChargeTime(ItemStack stack) {
        return RailgunItem.getChargeTime(stack);
    }

    public static boolean isChemthrowerItem(ItemStack stack) {
        return stack.getItem() instanceof ChemthrowerItem;
    }

    public static FluidStack getFluid(ItemStack stack) {
        if (stack.getItem() instanceof ChemthrowerItem item) {
            return item.getFluid(stack);
        }
        return FluidStack.EMPTY;
    }

    public static double getFluidGravity(FluidStack stack) {
        if (stack.isEmpty()) {
            return 0.05F;
        } else {
            boolean isGas = stack.getFluid().is(Tags.Fluids.GASEOUS);
            return (isGas ? 0.025F : 0.05F) * (float)(stack.getFluid().getFluidType().getDensity(stack) < 0 ? -1 : 1);
        }
    }

    public static boolean hasUpgradesFocus(ItemStack stack) {
        if (stack.getItem() instanceof ChemthrowerItem item) {
            return item.getUpgrades(stack).getBoolean("focus");
        }
        return false;
    }

    public static boolean hasKey(ItemStack stack, String s){
        return ItemNBTHelper.hasKey(stack, s);
    }
    public static boolean isBull(Item stack){
        return stack instanceof BulletItem;
    }
    public static BulletHandler.IBullet getType(Item item){
       return ((BulletItem) item).getType();
    }
    public static boolean isTypeNotNull(Item item){
        return getType(item) != null;
    }

    public static double getGravity(ItemStack item, Player player, ItemStack bulletStack, Vec3 vecDir){
        boolean et =  ImmersiveEngineeringExtra.getUpgradesStatic(item).getBoolean("electro");
        return ((RevolvershotEntity)getType(item.getItem()).getProjectile(player, bulletStack, getBullet(player, vecDir, getType(item.getItem()),et ),et)).getGravity();
    }
    public static NonNullList<ItemStack> getBullets(ItemStack stack){
        return ((RevolverItem)stack.getItem()).getBullets(stack);
    }

    private static RevolvershotEntity getBullet(LivingEntity living, Vec3 vecDir, BulletHandler.IBullet type, boolean electro) {
        RevolvershotEntity bullet = new RevolvershotEntity(living.level(), living, vecDir.x * 1.5D, vecDir.y * 1.5D, vecDir.z * 1.5D, type);
        bullet.setDeltaMovement(vecDir.scale(2.0D));
        bullet.bulletElectro = electro;
        return bullet;
    }

    public static CompoundTag getUpgradesStatic(ItemStack stack) {
        return ItemNBTHelper.getTagCompound(stack, "upgrades");
    }

    public static int getProjectileCount(Item item, Player player){
        return getType(item).getProjectileCount(player);
    }
}
