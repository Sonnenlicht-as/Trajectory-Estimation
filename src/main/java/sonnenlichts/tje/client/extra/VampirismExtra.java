package sonnenlichts.tje.client.extra;

import de.teamlapen.vampirism.core.ModEnchantments;
import de.teamlapen.vampirism.items.HolyWaterSplashBottleItem;
import de.teamlapen.vampirism.items.crossbow.DoubleCrossbowItem;
import de.teamlapen.vampirism.items.crossbow.SingleCrossbowItem;
import de.teamlapen.vampirism.items.crossbow.TechCrossbowItem;
import de.teamlapen.vampirism.items.crossbow.VampirismCrossbowItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.List;

public class VampirismExtra {
    public static boolean isSingleCrossbowItem(ItemStack stack) {
        return stack.getItem() instanceof SingleCrossbowItem;
    }

    public static boolean isTechCrossbowItem(ItemStack stack) {
        return stack.getItem() instanceof TechCrossbowItem;
    }

    public static boolean isDoubleCrossbowItem(ItemStack stack) {
        return stack.getItem() instanceof DoubleCrossbowItem;
    }

    public static boolean isHolyWaterSplashBottleItem(ItemStack stack) {
        return stack.getItem() instanceof HolyWaterSplashBottleItem;
    }

    public static float getShootingPowerMod(ItemStack itemStack){
        return ((VampirismCrossbowItem)itemStack.getItem()).getShootingPowerMod(itemStack);
    }

    public static ItemStack getProjectile(LivingEntity entity, ItemStack crossbow, List<ItemStack> projectiles) {
        int frugal = isFrugal(crossbow);
        if (frugal > 0 && entity.getRandom().nextInt(Math.max(2, 4 - frugal)) == 0) {
            return projectiles.get(0).copy();
        }
        return projectiles.remove(0);
    }

    public static int isFrugal(ItemStack crossbow){
        return EnchantmentHelper.getItemEnchantmentLevel((Enchantment) ModEnchantments.CROSSBOWFRUGALITY.get(), crossbow);
    }

}