package sonnenlichts.tje.client.extra;

import com.melvinbur.archbows.common.weapons.bow.*;
import com.melvinbur.archbows.common.weapons.crossbow.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ArchBowsExtra {
    public static boolean isFlatBowItem(ItemStack stack) {
        return stack.getItem() instanceof FlatBowItem;
    }

    public static boolean isLongBowItem(ItemStack stack) {
        return stack.getItem() instanceof LongBowItem;
    }

    public static boolean isRecurveBowItem(ItemStack stack) {
        return stack.getItem() instanceof RecurveBowItem;
    }

    public static boolean isShortBowItem(ItemStack stack) {
        return stack.getItem() instanceof ShortBowItem;
    }

    public static boolean isArbalestItem(ItemStack stack) {
        return stack.getItem() instanceof ArbalestItem;
    }

    public static boolean isHeavyCrossbowItem(ItemStack stack) {
        return stack.getItem() instanceof HeavyCrossbowItem;
    }

    public static boolean isPistolCrossbowItem(ItemStack stack) {
        return stack.getItem() instanceof PistolCrossbowItem;
    }

    public static float getVelocity(ItemStack stack) {
        if (isFlatBowItem(stack)) {
            return FlatBowItem.maxVelocity;
        } else if (isLongBowItem(stack)) {
            return LongBowItem.maxVelocity;
        } else if (isRecurveBowItem(stack)) {
            return RecurveBowItem.maxVelocity;
        } else if (isShortBowItem(stack)) {
            return ShortBowItem.maxVelocity;
        }
        return 0.0F;
    }

    @SuppressWarnings("all")
    public static float getVelocityCrossbow(ItemStack stack) {
        if (stack.getItem() instanceof ArbalestItem arbalestItem) {
            return ArbalestItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.6F : ArbalestItem.maxVelocity;
        } else if (stack.getItem() instanceof HeavyCrossbowItem heavyCrossbowItem) {
            return HeavyCrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.6F : HeavyCrossbowItem.maxVelocity;
        } else if (stack.getItem() instanceof PistolCrossbowItem pistolCrossbowItem) {
            return PistolCrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.6F : PistolCrossbowItem.maxVelocity;
        }
        return 0.0F;
    }

    public static float getPowerForTime(ItemStack stack, int duration) {
        float drawSpeed = 1.0F;
        if (isFlatBowItem(stack)) {
            drawSpeed = FlatBowItem.drawSpeed;
        } else if (isLongBowItem(stack)) {
            drawSpeed = LongBowItem.drawSpeed;
        } else if (isRecurveBowItem(stack)) {
            drawSpeed = RecurveBowItem.drawSpeed;
        } else if (isShortBowItem(stack)) {
            drawSpeed = ShortBowItem.drawSpeed;
        }
        float f = (float) duration / drawSpeed;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return Math.min(f, 1.0F);
    }
}