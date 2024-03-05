package sonnenlichts.tje.client.extra;

import dev.xkmc.l2weaponry.content.item.types.JavelinItem;
import net.minecraft.world.item.ItemStack;

public class L2WeaponryExtra {
    public static boolean isJavelinItem(ItemStack stack) {
        return stack.getItem() instanceof JavelinItem;
    }
}