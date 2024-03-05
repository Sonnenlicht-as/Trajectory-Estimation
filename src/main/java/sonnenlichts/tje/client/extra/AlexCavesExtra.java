package sonnenlichts.tje.client.extra;

import com.github.alexmodguy.alexscaves.server.item.*;
import net.minecraft.world.item.ItemStack;

public class AlexCavesExtra {
    public static boolean isLimestoneSpear(ItemStack stack) {
        return stack.getItem() instanceof LimestoneSpearItem;
    }

    public static boolean isExtinctionSpear(ItemStack stack) {
        return stack.getItem() instanceof ExtinctionSpearItem;
    }

    public static boolean isDreadbow(ItemStack stack) {
        return stack.getItem() instanceof DreadbowItem;
    }

    public static boolean isSeaStaffItem(ItemStack stack) {
        return stack.getItem() instanceof SeaStaffItem;
    }

    public static float getPowerForTime(int i, ItemStack itemStack) {
        return DreadbowItem.getPowerForTime(i, itemStack);
    }
}
