package sonnenlichts.tje.client.extra;

import com.legacy.blue_skies.items.tools.weapons.SpearItem;
import com.legacy.blue_skies.items.tools.weapons.VenomSacItem;
import net.minecraft.world.item.ItemStack;

public class BlueSkiesExtra {

    public static boolean isSpear(ItemStack stack) {
        return stack.getItem() instanceof SpearItem;
    }

    public static boolean isVenomSac(ItemStack stack) {
        return stack.getItem() instanceof VenomSacItem;
    }
}
