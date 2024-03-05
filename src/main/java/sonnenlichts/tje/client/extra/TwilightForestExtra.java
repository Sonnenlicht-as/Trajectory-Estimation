package sonnenlichts.tje.client.extra;

import net.minecraft.world.item.ItemStack;
import twilightforest.item.*;

public class TwilightForestExtra {

    public static boolean isIceBow(ItemStack stack) {
        return stack.getItem() instanceof IceBowItem;
    }

    public static boolean isTripleBowItem(ItemStack stack) {
        return stack.getItem() instanceof TripleBowItem;
    }

    public static boolean isEnderBowItem(ItemStack stack) {
        return stack.getItem() instanceof EnderBowItem;
    }

    public static boolean isSeekerBowItem(ItemStack stack) {
        return stack.getItem() instanceof SeekerBowItem;
    }

    public static boolean isTwilightWandItem(ItemStack stack) {
        return stack.getItem() instanceof TwilightWandItem;
    }

    public static boolean isIceBombItem(ItemStack stack) {
        return stack.getItem() instanceof IceBombItem;
    }
}
