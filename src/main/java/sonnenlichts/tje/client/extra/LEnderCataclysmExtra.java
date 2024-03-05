package sonnenlichts.tje.client.extra;

import com.github.L_Ender.cataclysm.items.*;
import net.minecraft.world.item.ItemStack;

public class LEnderCataclysmExtra {
    public static boolean isVoidAssaultShoulderWeapon(ItemStack stack) {
        return stack.getItem() instanceof Void_Assault_SHoulder_Weapon;
    }

    public static boolean isWitherAssaultShoulderWeapon(ItemStack stack) {
        return stack.getItem() instanceof Wither_Assault_SHoulder_Weapon;
    }

    public static boolean isCoralSpear(ItemStack stack) {
        return stack.getItem() instanceof Coral_Spear;
    }

    public static boolean isCoralBardiche(ItemStack stack) {
        return stack.getItem() instanceof Coral_Bardiche;
    }

    public static boolean isLaserGatling(ItemStack stack) {
        return stack.getItem() instanceof Laser_Gatling;
    }

    public static boolean isVoidScatterArrow(ItemStack stack) {
        return stack.getItem() instanceof ModItemArrow;
    }
}
