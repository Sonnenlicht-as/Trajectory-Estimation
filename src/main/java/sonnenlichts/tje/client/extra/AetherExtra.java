package sonnenlichts.tje.client.extra;

import com.aetherteam.aether.item.combat.DartShooterItem;
import com.aetherteam.aether.item.combat.loot.HammerOfKingbdogzItem;
import com.aetherteam.aether.item.combat.loot.LightningKnifeItem;
import com.aetherteam.aether.item.combat.loot.PhoenixBowItem;
import net.minecraft.world.item.ItemStack;

public class AetherExtra {
    public static boolean isPhoenixBowItem(ItemStack stack) {
        return stack.getItem() instanceof PhoenixBowItem;
    }

    public static boolean isHammerOfKingbdogzItem(ItemStack stack) {
        return stack.getItem() instanceof HammerOfKingbdogzItem;
    }

    public static boolean isLightningKnifeItem(ItemStack stack) {
        return stack.getItem() instanceof LightningKnifeItem;
    }

    public static boolean isDartShooterItem(ItemStack stack) {
        return stack.getItem() instanceof DartShooterItem;
    }
}