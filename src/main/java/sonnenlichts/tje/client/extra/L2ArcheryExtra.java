package sonnenlichts.tje.client.extra;

import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.init.registrate.ArcheryEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class L2ArcheryExtra {
    public static boolean isGenericBowItem(ItemStack stack) {
        return stack.getItem() instanceof GenericBowItem;
    }

    public static float getPowerForTime(ItemStack itemStack, LivingEntity entity, float time) {
        return ((GenericBowItem)itemStack.getItem()).getPullForTime(entity, time);
    }
}