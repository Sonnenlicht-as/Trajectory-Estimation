package sonnenlichts.tje.client.extra;

import com.github.alexthe666.alexsmobs.item.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AlexsMobsExtra {
    public static boolean isItemPocketSand(ItemStack stack) {
        return stack.getItem() instanceof ItemPocketSand;
    }

    public static boolean isItemHemolymphBlaster(ItemStack stack) {
        return stack.getItem() instanceof ItemHemolymphBlaster;
    }

    public static boolean isItemBloodSprayer(ItemStack stack) {
        return stack.getItem() instanceof ItemBloodSprayer;
    }

    public static boolean isItemStinkRay(ItemStack stack) {
        return stack.getItem() instanceof ItemStinkRay;
    }

    public static boolean isItemVineLasso(ItemStack stack) {
        return stack.getItem() instanceof ItemVineLasso;
    }

    public static ItemStack findAmmoItemPocketSand(ItemStack stack, Player player){
        return ((ItemPocketSand)(stack.getItem())).findAmmo(player);
    }

    public static ItemStack findAmmoItemHemolymphBlaster(ItemStack stack, Player player){
        return ((ItemHemolymphBlaster)(stack.getItem())).findAmmo(player);
    }

    public static ItemStack findAmmoItemBloodSprayer(ItemStack stack, Player player){
        return ((ItemBloodSprayer)(stack.getItem())).findAmmo(player);
    }
}