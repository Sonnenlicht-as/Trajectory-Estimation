package sonnenlichts.tje.client.extra;

import com.github.alexthe666.iceandfire.item.ItemDragonBow;
import com.github.alexthe666.iceandfire.item.ItemLichStaff;
import com.github.alexthe666.iceandfire.item.ItemTideTrident;
import net.minecraft.world.item.ItemStack;

public class IceAndFireExtra {
    public static boolean isDragonBow(ItemStack stack) {
        return stack.getItem() instanceof ItemDragonBow;
    }

    public static boolean isTideTrident(ItemStack stack) {
        return stack.getItem() instanceof ItemTideTrident;
    }

    public static boolean isLichStaff(ItemStack stack) {
        return stack.getItem() instanceof ItemLichStaff;
    }
}
