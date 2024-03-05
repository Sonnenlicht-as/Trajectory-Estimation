package sonnenlichts.tje.client.extra;

import com.telepathicgrunt.the_bumblezone.items.CrystalCannon;
import com.telepathicgrunt.the_bumblezone.items.DirtPellet;
import com.telepathicgrunt.the_bumblezone.items.PollenPuff;
import com.telepathicgrunt.the_bumblezone.items.StingerSpearItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TheBumblezoneExtra {
    public static boolean isStingerSpear(ItemStack stack) {
        return stack.getItem() instanceof StingerSpearItem;
    }

    public static boolean isCrystalCannon(ItemStack stack) {
        return stack.getItem() instanceof CrystalCannon;
    }
    public static int getNumberOfCrystals(ItemStack crystalCannonItem) {
        return CrystalCannon.getNumberOfCrystals(crystalCannonItem);
    }
    public static boolean isPollenPuff(ItemStack stack) {
        return stack.getItem() instanceof PollenPuff;
    }
    public static boolean isDirtPellet(ItemStack stack) {
        return stack.getItem() instanceof DirtPellet;
    }
}
