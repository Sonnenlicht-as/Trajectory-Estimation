package sonnenlichts.tje.client.util;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import static sonnenlichts.tje.TrajectoryEstimation.MOD_ID;

public class StringHelper {
    public static boolean isNullOrEmpty(@Nullable String targetStr) {
        return targetStr == null || targetStr.isEmpty();
    }

    public static ResourceLocation create(String target) {
        return new ResourceLocation(MOD_ID, target);
    }
}
