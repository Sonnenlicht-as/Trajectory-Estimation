package sonnenlichts.tje.client.util;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import sonnenlichts.tje.client.config.TjeModConfig;
import sonnenlichts.tje.client.extra.*;

import java.util.ArrayList;
import java.util.List;

public class ModUtils {
    private static final int FULL_LIGHT = 15728880;

    public static boolean CGMLoaded() {
        return (ModList.get().isLoaded("cgm"));
    }

    public static boolean CataclysmLoaded() {
        return (ModList.get().isLoaded("cataclysm"));
    }

    public static boolean IceAndFireLoaded() {
        return (ModList.get().isLoaded("iceandfire"));
    }

    public static boolean BlueSkiesLoaded() {
        return (ModList.get().isLoaded("blue_skies"));
    }

    public static boolean TwilightForestLoaded() {
        return (ModList.get().isLoaded("twilightforest"));
    }

    public static boolean ImmersiveEngineeringLoaded() {
        return (ModList.get().isLoaded("immersiveengineering"));
    }

    public static boolean AlexCavesLoaded() {
        return (ModList.get().isLoaded("alexscaves"));
    }

    public static boolean TheBumblezoneLoaded() {
        return (ModList.get().isLoaded("the_bumblezone"));
    }

    public static boolean AetherLoaded() {
        return (ModList.get().isLoaded("aether"));
    }

    public static boolean AlexsMobsLoaded() {
        return (ModList.get().isLoaded("alexsmobs"));
    }

    public static boolean ArchBowsLoaded() {
        return (ModList.get().isLoaded("archbows"));
    }

    public static boolean VampirismLoaded() {
        return (ModList.get().isLoaded("vampirism"));
    }

    public static boolean L2WeaponryLoaded() {
        return (ModList.get().isLoaded("l2weaponry"));
    }

    public static boolean L2ArcheryLoaded() {
        return (ModList.get().isLoaded("l2archery"));
    }

    public static boolean AdventOfAscension3Loaded() {
        return (ModList.get().isLoaded("aoa3"));
    }

    public static boolean isVanillaItemsSound(ItemStack stack) {
        return (ModUtils.isClassOrSuperClass(stack.getItem(), BowItem.class) && TjeModConfig.targetSoundBow)
                || (ModUtils.isClassOrSuperClass(stack.getItem(), TridentItem.class) && TjeModConfig.targetSoundTrident)
                || (ModUtils.isClassOrSuperClass(stack.getItem(), CrossbowItem.class) && TjeModConfig.targetSoundCrossbow)
                || (stack.getItem() instanceof SnowballItem && TjeModConfig.targetSoundSnowball)
                || (stack.getItem() instanceof EggItem && TjeModConfig.targetSoundEgg)
                || (stack.getItem() instanceof ExperienceBottleItem && TjeModConfig.targetSoundExperienceBottle)
                || (stack.getItem() instanceof ThrowablePotionItem && TjeModConfig.targetSoundSplashBottle)
                ||(stack.getItem() instanceof EnderpearlItem && TjeModConfig.targetSoundEnderpearl);
    }

    public static boolean isVanillaItems(ItemStack stack) {
        return (stack.getItem() instanceof BowItem && TjeModConfig.renderBow) || (stack.getItem() instanceof TridentItem && TjeModConfig.renderTrident) || (stack.getItem() instanceof CrossbowItem && TjeModConfig.renderCrossbow) ||
                (stack.getItem() instanceof SnowballItem && TjeModConfig.renderSnowball) || (stack.getItem() instanceof EggItem && TjeModConfig.renderEgg) ||
                (stack.getItem() instanceof ExperienceBottleItem && TjeModConfig.renderExperienceBottle) || (stack.getItem() instanceof ThrowablePotionItem && TjeModConfig.renderSplashBottle) ||
                (stack.getItem() instanceof EnderpearlItem && TjeModConfig.renderEnderpearl);
    }

    public static boolean isCGMItemsSound(ItemStack stack) {
        if (CGMLoaded())
            return (MrcrayfishGunsExtra.isGun(stack) && TjeModConfig.targetSoundCGMGun) || (MrcrayfishGunsExtra.isGrenade(stack) && TjeModConfig.targetSoundCGMGrenade) || (MrcrayfishGunsExtra.isStunGrenade(stack) && TjeModConfig.targetSoundCGMStunGrenade);
        return false;
    }

    public static boolean isCGMItems(ItemStack stack) {
        if (CGMLoaded())
            return (MrcrayfishGunsExtra.isGun(stack) && TjeModConfig.renderCGMGun) || (MrcrayfishGunsExtra.isGrenade(stack) && TjeModConfig.renderCGMGrenade) || (MrcrayfishGunsExtra.isStunGrenade(stack) && TjeModConfig.renderCGMStunGrenade);
        return false;
    }

    public static boolean isCataclysmItemsSound(ItemStack stack) {
        if (CataclysmLoaded())
            return (LEnderCataclysmExtra.isCoralBardiche(stack) && TjeModConfig.targetSoundCataclysmCoralBardiche)
                    || (LEnderCataclysmExtra.isCoralSpear(stack) && TjeModConfig.targetSoundCataclysmCoralSpear)
                    || (LEnderCataclysmExtra.isLaserGatling(stack) && TjeModConfig.targetSoundCataclysmLaserGatling)
                    || (LEnderCataclysmExtra.isVoidAssaultShoulderWeapon(stack) && TjeModConfig.targetSoundCataclysmVoidAssaultShoulder)
                    || (LEnderCataclysmExtra.isWitherAssaultShoulderWeapon(stack) && TjeModConfig.targetSoundCataclysmWitherAssaultShoulder);
        return false;
    }

    public static boolean isCataclysmItems(ItemStack stack) {
        if (CataclysmLoaded())
            return (LEnderCataclysmExtra.isCoralBardiche(stack) && TjeModConfig.renderCataclysmCoralBardiche)
                    || (LEnderCataclysmExtra.isCoralSpear(stack) && TjeModConfig.renderCataclysmCoralSpear)
                    || (LEnderCataclysmExtra.isLaserGatling(stack) && TjeModConfig.renderCataclysmLaserGatling)
                    || (LEnderCataclysmExtra.isVoidAssaultShoulderWeapon(stack) && TjeModConfig.renderCataclysmVoidAssaultShoulder)
                    || (LEnderCataclysmExtra.isWitherAssaultShoulderWeapon(stack) && TjeModConfig.renderCataclysmWitherAssaultShoulder);
        return false;
    }

    public static boolean isBlueSkiesItemsSound(ItemStack stack) {
        if (BlueSkiesLoaded())
            return (BlueSkiesExtra.isSpear(stack) && TjeModConfig.targetSoundBlueSkiesSpearItem)
                    || (BlueSkiesExtra.isVenomSac(stack) && TjeModConfig.targetSoundBlueSkiesVenomSacItem);
        return false;
    }

    public static boolean isBlueSkiesItems(ItemStack stack) {
        if (BlueSkiesLoaded())
            return (BlueSkiesExtra.isSpear(stack) && TjeModConfig.renderBlueSkiesSpearItem)
                    || (BlueSkiesExtra.isVenomSac(stack) && TjeModConfig.renderBlueSkiesVenomSacItem);
        return false;
    }

    public static boolean isIceAndFireItemsSound(ItemStack stack) {
        if (IceAndFireLoaded())
            return (IceAndFireExtra.isTideTrident(stack) && TjeModConfig.targetSoundIceAndFireTideTrident)
                    || (IceAndFireExtra.isLichStaff(stack) && TjeModConfig.targetSoundIceAndFireLichStaff)
                    || (IceAndFireExtra.isDragonBow(stack) && TjeModConfig.targetSoundIceAndFireDragonBow);
        return false;
    }

    public static boolean isIceAndFireItems(ItemStack stack) {
        if (IceAndFireLoaded())
            return (IceAndFireExtra.isTideTrident(stack) && TjeModConfig.renderIceAndFireTideTrident)
                    || (IceAndFireExtra.isLichStaff(stack) && TjeModConfig.renderIceAndFireLichStaff)
                    || (IceAndFireExtra.isDragonBow(stack) && TjeModConfig.renderIceAndFireDragonBow);
        return false;
    }

    public static boolean isTwilightForestItemsSound(ItemStack stack) {
        if (TwilightForestLoaded())
            return (TwilightForestExtra.isEnderBowItem(stack) && TjeModConfig.targetSoundTwilightForestEnderBowItem)
                    || (TwilightForestExtra.isIceBombItem(stack) && TjeModConfig.targetSoundTwilightForestIceBombItem)
                    || (TwilightForestExtra.isIceBow(stack) && TjeModConfig.targetSoundTwilightForestIceBowItem)
                    || (TwilightForestExtra.isSeekerBowItem(stack) && TjeModConfig.targetSoundTwilightForestSeekerBowItem)
                    || (TwilightForestExtra.isTripleBowItem(stack) && TjeModConfig.targetSoundTwilightForestTripleBowItem)
                    || (TwilightForestExtra.isTwilightWandItem(stack) && TjeModConfig.targetSoundTwilightForestTwilightWandItem);
        return false;
    }

    public static boolean isTwilightForestItems(ItemStack stack) {
        if (TwilightForestLoaded())
            return (TwilightForestExtra.isEnderBowItem(stack) && TjeModConfig.renderTwilightForestEnderBowItem)
                    || (TwilightForestExtra.isIceBombItem(stack) && TjeModConfig.renderTwilightForestIceBombItem)
                    || (TwilightForestExtra.isIceBow(stack) && TjeModConfig.renderTwilightForestIceBowItem)
                    || (TwilightForestExtra.isSeekerBowItem(stack) && TjeModConfig.renderTwilightForestSeekerBowItem)
                    || (TwilightForestExtra.isTripleBowItem(stack) && TjeModConfig.renderTwilightForestTripleBowItem)
                    || (TwilightForestExtra.isTwilightWandItem(stack) && TjeModConfig.renderTwilightForestTwilightWandItem);
        return false;
    }

    public static boolean isImmersiveEngineeringItemsSound(ItemStack stack) {
        if (ImmersiveEngineeringLoaded())
            return (ImmersiveEngineeringExtra.isChemthrowerItem(stack) && TjeModConfig.targetSoundImmersiveEngineeringChemthrowerItem)
                    || (ImmersiveEngineeringExtra.isRailgunItem(stack) && TjeModConfig.targetSoundImmersiveEngineeringRailgunItem)
                    || (ImmersiveEngineeringExtra.isRevolverItem(stack) && TjeModConfig.targetSoundImmersiveEngineeringRevolverItem);
        return false;
    }

    public static boolean isImmersiveEngineeringItems(ItemStack stack) {
        if (ImmersiveEngineeringLoaded())
            return (ImmersiveEngineeringExtra.isChemthrowerItem(stack) && TjeModConfig.renderImmersiveEngineeringChemthrowerItem)
                    || (ImmersiveEngineeringExtra.isRailgunItem(stack) && TjeModConfig.renderImmersiveEngineeringRailgunItem)
                    || (ImmersiveEngineeringExtra.isRevolverItem(stack) && TjeModConfig.renderImmersiveEngineeringRevolverItem);
        return false;
    }

    public static boolean isAlexCavesItemsSound(ItemStack stack) {
        if (AlexCavesLoaded())
            return (AlexCavesExtra.isLimestoneSpear(stack) && TjeModConfig.targetSoundAlexCavesLimestoneSpearItem)
                    || (AlexCavesExtra.isExtinctionSpear(stack) && TjeModConfig.targetSoundAlexCavesExtinctionSpearItem)
                    || (AlexCavesExtra.isDreadbow(stack) && TjeModConfig.targetSoundAlexCavesDreadbowItem)
                    || (AlexCavesExtra.isSeaStaffItem(stack) && TjeModConfig.targetSoundAlexCavesSeaStaffItem);
        return false;
    }

    public static boolean isAlexCavesItems(ItemStack stack) {
        if (AlexCavesLoaded())
            return (AlexCavesExtra.isLimestoneSpear(stack) && TjeModConfig.renderAlexCavesLimestoneSpearItem)
                    || (AlexCavesExtra.isExtinctionSpear(stack) && TjeModConfig.renderAlexCavesExtinctionSpearItem)
                    || (AlexCavesExtra.isDreadbow(stack) && TjeModConfig.renderAlexCavesDreadbowItem)
                    || (AlexCavesExtra.isSeaStaffItem(stack) && TjeModConfig.renderAlexCavesSeaStaffItem);
        return false;
    }

    public static boolean isTheBumblezoneItemsSound(ItemStack stack) {
        if (TheBumblezoneLoaded())
            return (TheBumblezoneExtra.isStingerSpear(stack) && TjeModConfig.targetSoundTheBumblezoneStingerSpearItem)
                    || (TheBumblezoneExtra.isCrystalCannon(stack) && TjeModConfig.targetSoundTheBumblezoneCrystalCannonItem)
                    || (TheBumblezoneExtra.isPollenPuff(stack) && TjeModConfig.targetSoundTheBumblezonePollenPuff)
                    || (TheBumblezoneExtra.isDirtPellet(stack) && TjeModConfig.targetSoundTheBumblezoneDirtPellet);
        return false;
    }

    public static boolean isTheBumblezoneItems(ItemStack stack) {
        if (TheBumblezoneLoaded())
            return (TheBumblezoneExtra.isStingerSpear(stack) && TjeModConfig.renderTheBumblezoneStingerSpearItem)
                    || (TheBumblezoneExtra.isCrystalCannon(stack) && TjeModConfig.renderTheBumblezoneCrystalCannonItem)
                    || (TheBumblezoneExtra.isPollenPuff(stack) && TjeModConfig.renderTheBumblezonePollenPuff)
                    || (TheBumblezoneExtra.isDirtPellet(stack) && TjeModConfig.renderTheBumblezoneDirtPellet);
        return false;
    }

    public static boolean isAetherItemsSound(ItemStack stack) {
        if (AetherLoaded()) return (AetherExtra.isPhoenixBowItem(stack) && TjeModConfig.targetSoundAetherPhoenixBowItem) ||
                (AetherExtra.isHammerOfKingbdogzItem(stack) && TjeModConfig.targetSoundAetherHammerOfKingbdogzItem) ||
                (AetherExtra.isLightningKnifeItem(stack) && TjeModConfig.targetSoundAetherLightningKnifeItem) ||
                (AetherExtra.isDartShooterItem(stack) && TjeModConfig.targetSoundAetherDartShooterItem);
        return false;
    }

    public static boolean isAetherItems(ItemStack stack) {
        if (AetherLoaded()) return (AetherExtra.isPhoenixBowItem(stack) && TjeModConfig.renderAetherPhoenixBowItem) ||
                (AetherExtra.isHammerOfKingbdogzItem(stack) && TjeModConfig.renderAetherHammerOfKingbdogzItem) ||
                (AetherExtra.isLightningKnifeItem(stack) && TjeModConfig.renderAetherLightningKnifeItem) ||
                (AetherExtra.isDartShooterItem(stack) && TjeModConfig.renderAetherDartShooterItem);
        return false;
    }

    public static boolean isAlexsMobsItemsSound(ItemStack stack) {
        if (AlexsMobsLoaded())
            return (AlexsMobsExtra.isItemPocketSand(stack) && TjeModConfig.targetSoundAlexsMobsItemPocketSand) ||
                    (AlexsMobsExtra.isItemHemolymphBlaster(stack) && TjeModConfig.targetSoundAlexsMobsItemHemolymphBlaster) ||
                    (AlexsMobsExtra.isItemBloodSprayer(stack) && TjeModConfig.targetSoundAlexsMobsItemBloodSprayer) ||
                    (AlexsMobsExtra.isItemStinkRay(stack) && TjeModConfig.targetSoundAlexsMobsItemStinkRay) ||
                    (AlexsMobsExtra.isItemVineLasso(stack) && TjeModConfig.targetSoundAlexsMobsItemVineLasso);

        return false;
    }

    public static boolean isAlexsMobsItems(ItemStack stack) {
        if (AlexsMobsLoaded())
            return (AlexsMobsExtra.isItemPocketSand(stack) && TjeModConfig.renderAlexsMobsItemPocketSand) ||
                    (AlexsMobsExtra.isItemHemolymphBlaster(stack) && TjeModConfig.renderAlexsMobsItemHemolymphBlaster) ||
                    (AlexsMobsExtra.isItemBloodSprayer(stack) && TjeModConfig.renderAlexsMobsItemBloodSprayer) ||
                    (AlexsMobsExtra.isItemStinkRay(stack) && TjeModConfig.renderAlexsMobsItemStinkRay) ||
                    (AlexsMobsExtra.isItemVineLasso(stack) && TjeModConfig.renderAlexsMobsItemVineLasso);
        return false;
    }

    public static boolean isArchBowsItemsSound(ItemStack stack) {
        if (ArchBowsLoaded()) return (ArchBowsExtra.isFlatBowItem(stack) && TjeModConfig.targetSoundArchBowsFlatBowItem) ||
                (ArchBowsExtra.isLongBowItem(stack) && TjeModConfig.targetSoundArchBowsLongBowItem) ||
                (ArchBowsExtra.isRecurveBowItem(stack) && TjeModConfig.targetSoundArchBowsRecurveBowItem) ||
                (ArchBowsExtra.isShortBowItem(stack) && TjeModConfig.targetSoundArchBowsShortBowItem) ||
                (ArchBowsExtra.isArbalestItem(stack) && TjeModConfig.targetSoundArchBowsArbalestItem) ||
                (ArchBowsExtra.isHeavyCrossbowItem(stack) && TjeModConfig.targetSoundArchBowsHeavyCrossbowItem) ||
                (ArchBowsExtra.isPistolCrossbowItem(stack) && TjeModConfig.targetSoundArchBowsPistolCrossbowItem);
        return false;
    }

    public static boolean isArchBowsItems(ItemStack stack) {
        if (ArchBowsLoaded()) return (ArchBowsExtra.isFlatBowItem(stack) && TjeModConfig.renderArchBowsFlatBowItem) ||
                (ArchBowsExtra.isLongBowItem(stack) && TjeModConfig.renderArchBowsLongBowItem) ||
                (ArchBowsExtra.isRecurveBowItem(stack) && TjeModConfig.renderArchBowsRecurveBowItem) ||
                (ArchBowsExtra.isShortBowItem(stack) && TjeModConfig.renderArchBowsShortBowItem) ||
                (ArchBowsExtra.isArbalestItem(stack) && TjeModConfig.renderArchBowsArbalestItem) ||
                (ArchBowsExtra.isHeavyCrossbowItem(stack) && TjeModConfig.renderArchBowsHeavyCrossbowItem) ||
                (ArchBowsExtra.isPistolCrossbowItem(stack) && TjeModConfig.renderArchBowsPistolCrossbowItem);

        return false;
    }

    public static boolean isVampirismItemsSound(ItemStack stack) {
        if (VampirismLoaded())
            return (VampirismExtra.isSingleCrossbowItem(stack) && TjeModConfig.targetSoundVampirismSingleCrossbowItem) ||
                    (VampirismExtra.isTechCrossbowItem(stack) && TjeModConfig.targetSoundVampirismTechCrossbowItem) ||
                    (VampirismExtra.isDoubleCrossbowItem(stack) && TjeModConfig.targetSoundVampirismDoubleCrossbowItem) ||
                    (VampirismExtra.isHolyWaterSplashBottleItem(stack) && TjeModConfig.targetSoundVampirismHolyWaterSplashBottleItem);
        return false;
    }

    public static boolean isVampirismItems(ItemStack stack) {
        if (VampirismLoaded())
            return (VampirismExtra.isSingleCrossbowItem(stack) && TjeModConfig.renderVampirismSingleCrossbowItem) ||
                    (VampirismExtra.isTechCrossbowItem(stack) && TjeModConfig.renderVampirismTechCrossbowItem) ||
                    (VampirismExtra.isDoubleCrossbowItem(stack) && TjeModConfig.renderVampirismDoubleCrossbowItem) ||
                    (VampirismExtra.isHolyWaterSplashBottleItem(stack) && TjeModConfig.renderVampirismHolyWaterSplashBottleItem);
        return false;
    }

    public static boolean isL2WeaponryItemsSound(ItemStack stack) {
        if (L2WeaponryLoaded())
            return (L2WeaponryExtra.isJavelinItem(stack) && TjeModConfig.targetSoundL2WeaponryJavelinItem);
        return false;
    }

    public static boolean isL2WeaponryItems(ItemStack stack) {
        if (L2WeaponryLoaded())
            return (L2WeaponryExtra.isJavelinItem(stack) && TjeModConfig.renderL2WeaponryJavelinItem);
        return false;
    }

    public static boolean isL2ArcheryItemsSound(ItemStack stack) {
        if (L2ArcheryLoaded())
            return (L2ArcheryExtra.isGenericBowItem(stack) && TjeModConfig.targetSoundL2ArcheryGenericBowItem);
        return false;
    }

    public static boolean isL2ArcheryItems(ItemStack stack) {
        if (L2ArcheryLoaded())
            return (L2ArcheryExtra.isGenericBowItem(stack) && TjeModConfig.renderL2ArcheryGenericBowItem);
        return false;
    }

    public static boolean isAdventOfAscension3ItemsSound(ItemStack stack) {
        if (AdventOfAscension3Loaded())
            return (AdventOfAscension3Extra.isBaseBow(stack) && TjeModConfig.targetSoundAdventOfAscension3BaseBow) ||
                    (AdventOfAscension3Extra.isBaseCrossbow(stack) && TjeModConfig.targetSoundAdventOfAscension3BaseCrossbow) ||
                    (AdventOfAscension3Extra.isBaseStaff(stack) && TjeModConfig.targetSoundAdventOfAscension3BaseStaff) ||
                    (AdventOfAscension3Extra.isBaseGun(stack) && !AdventOfAscension3Extra.isBaseSniper(stack)  && !AdventOfAscension3Extra.isBaseShotgun(stack) && !AdventOfAscension3Extra.isBaseCannon(stack)&& !AdventOfAscension3Extra.isBaseThrownWeapon(stack)&& TjeModConfig.targetSoundAdventOfAscension3BaseGun) ||
                    (AdventOfAscension3Extra.isBaseBlaster(stack) && TjeModConfig.targetSoundAdventOfAscension3BaseBlaster) ||
                    (AdventOfAscension3Extra.isBaseSniper(stack) && TjeModConfig.targetSoundAdventOfAscension3BaseSniper) ||
                    (AdventOfAscension3Extra.isBaseShotgun(stack) && TjeModConfig.targetSoundAdventOfAscension3BaseShotgun) ||
                    (AdventOfAscension3Extra.isBaseCannon(stack) && TjeModConfig.targetSoundAdventOfAscension3BaseCannon) ||
                    (AdventOfAscension3Extra.isBaseThrownWeapon(stack) && TjeModConfig.targetSoundAdventOfAscension3BaseThrownWeapon);
        return false;
    }

    public static boolean isAdventOfAscension3Items(ItemStack stack) {
        if (AdventOfAscension3Loaded())
            return (AdventOfAscension3Extra.isBaseBow(stack) && TjeModConfig.renderAdventOfAscension3BaseBow) ||
                    (AdventOfAscension3Extra.isBaseCrossbow(stack) && TjeModConfig.renderAdventOfAscension3BaseCrossbow) ||
                    (AdventOfAscension3Extra.isBaseStaff(stack) && TjeModConfig.renderAdventOfAscension3BaseStaff) ||
                    (AdventOfAscension3Extra.isBaseGun(stack) && !AdventOfAscension3Extra.isBaseSniper(stack)  && !AdventOfAscension3Extra.isBaseShotgun(stack) && !AdventOfAscension3Extra.isBaseCannon(stack)&& !AdventOfAscension3Extra.isBaseThrownWeapon(stack) && TjeModConfig.renderAdventOfAscension3BaseGun) ||
                    (AdventOfAscension3Extra.isBaseBlaster(stack) && TjeModConfig.renderAdventOfAscension3BaseBlaster) ||
                    (AdventOfAscension3Extra.isBaseSniper(stack) && TjeModConfig.renderAdventOfAscension3BaseSniper) ||
                    (AdventOfAscension3Extra.isBaseShotgun(stack) && TjeModConfig.renderAdventOfAscension3BaseShotgun) ||
                    (AdventOfAscension3Extra.isBaseCannon(stack) && TjeModConfig.renderAdventOfAscension3BaseCannon) ||
                    (AdventOfAscension3Extra.isBaseThrownWeapon(stack) && TjeModConfig.renderAdventOfAscension3BaseThrownWeapon);
        return false;
    }


    public static ItemStack getCorrectItem(Player player) {
        InteractionHand hand = player.getUsedItemHand();
        ItemStack mainStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offStack = player.getItemInHand(InteractionHand.OFF_HAND);
        if (mainStack.getItem() instanceof ThrowablePotionItem
                || mainStack.getItem().getUseAnimation(mainStack) != UseAnim.NONE
                || mainStack.getItem() instanceof EggItem
                || mainStack.getItem() instanceof SnowballItem
                || mainStack.getItem() instanceof ExperienceBottleItem
                || mainStack.getItem() instanceof EnderpearlItem
                || (ModUtils.CGMLoaded() && MrcrayfishGunsExtra.isGun(mainStack))
                || (ModUtils.CataclysmLoaded() && LEnderCataclysmExtra.isLaserGatling(mainStack))
                || (ModUtils.IceAndFireLoaded() && IceAndFireExtra.isLichStaff(mainStack))
                || (ModUtils.TwilightForestLoaded() && (TwilightForestExtra.isTwilightWandItem(mainStack) || TwilightForestExtra.isIceBombItem(mainStack)))
                || (ModUtils.ImmersiveEngineeringLoaded() && (ImmersiveEngineeringExtra.isRailgunItem(mainStack) || ImmersiveEngineeringExtra.isChemthrowerItem(mainStack)))
                || (ModUtils.AetherLoaded() && (AetherExtra.isLightningKnifeItem(mainStack) || AetherExtra.isHammerOfKingbdogzItem(mainStack)))
                || (ModUtils.AlexsMobsLoaded() && AlexsMobsExtra.isItemPocketSand(mainStack))
                || (ModUtils.AlexCavesLoaded() && AlexCavesExtra.isSeaStaffItem(mainStack))
                || (ModUtils.AdventOfAscension3Loaded() && (AdventOfAscension3Extra.isBaseBow(mainStack) || AdventOfAscension3Extra.isBaseCrossbow(mainStack) || AdventOfAscension3Extra.isBaseGun(mainStack) || AdventOfAscension3Extra.isBaseStaff(mainStack) || AdventOfAscension3Extra.isBaseBlaster(mainStack)))
        ) {
            return mainStack;
        } else {
            if (offStack.getItem() instanceof ThrowablePotionItem
                    || offStack.getItem().getUseAnimation(offStack) != UseAnim.NONE
                    || offStack.getItem() instanceof EggItem
                    || offStack.getItem() instanceof SnowballItem
                    || offStack.getItem() instanceof ExperienceBottleItem
                    || offStack.getItem() instanceof EnderpearlItem
                    || (ModUtils.CataclysmLoaded() && LEnderCataclysmExtra.isLaserGatling(offStack))
                    || (ModUtils.IceAndFireLoaded() && IceAndFireExtra.isLichStaff(offStack))
                    || (ModUtils.TwilightForestLoaded() && (TwilightForestExtra.isTwilightWandItem(offStack) || TwilightForestExtra.isIceBombItem(offStack)))
                    || (ModUtils.ImmersiveEngineeringLoaded() && (ImmersiveEngineeringExtra.isRailgunItem(offStack) || ImmersiveEngineeringExtra.isChemthrowerItem(offStack)))
                    || (ModUtils.AetherLoaded() && (AetherExtra.isLightningKnifeItem(offStack) || AetherExtra.isHammerOfKingbdogzItem(offStack)))
                    || (ModUtils.AlexsMobsLoaded() && AlexsMobsExtra.isItemPocketSand(offStack))
                    || (ModUtils.AlexCavesLoaded() && AlexCavesExtra.isSeaStaffItem(offStack))
                    || (ModUtils.AdventOfAscension3Loaded() && (AdventOfAscension3Extra.isBaseBow(offStack) || AdventOfAscension3Extra.isBaseCrossbow(offStack)))
            ) {
                return offStack;
            }
        }
        return player.getItemInHand(hand);
    }

    @SafeVarargs
    public static <T extends Entity> List<T> checkEntityOnBlock(BlockPos pos, Level level, double inflate, Class<? extends T>... entities) {
        List<T> list = new ArrayList<>();
        for (Class<? extends T> classes : entities) {
            list.addAll(level.getEntitiesOfClass(classes, new AABB(pos).inflate(inflate, inflate, inflate), EntitySelector.NO_SPECTATORS));
        }
        return list;
    }

    public static BlockPos getCorrectPos(Level level, Vec3 vec) {
        int i = Mth.floor(vec.x);
        int j = Mth.floor(vec.y - (double) (1.0E-5F));
        int k = Mth.floor(vec.z);
        BlockPos blockpos = new BlockPos(i, j, k);
        if (level.isEmptyBlock(blockpos)) {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = level.getBlockState(blockpos1);
            if (blockstate.collisionExtendsVertically(level, blockpos1, null)) {
                return blockpos1;
            }
        }
        return blockpos;
    }

    public static void drawLineFullLight(PoseStack matrix, Player player, double xo, double yo, double zo, double x, double y, double z, int count, int stp, int lr, int lg, int lb, int la, float lw) {
        PoseStack.Pose entry = matrix.last();
        float changeX = (float) (xo - x);
        float changeY = (float) (yo - y);
        float changeZ = (float) (zo - z);
        if (!(changeX == 0 && changeY == 0 && changeZ == 0) && count % stp == 0) {
            float factor = 2F;
            float amplitude = Mth.clamp((factor - player.tickCount - Minecraft.getInstance().getPartialTick()) / factor, 0F, 1F);
            RenderSystem.depthMask(false);
            RenderSystem.disableCull();
            RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
            Tesselator tesselator = RenderSystem.renderThreadTesselator();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            RenderSystem.lineWidth(lw);
            bufferbuilder.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
            float rd = 2F;
            for (int p = 0; p < rd; ++p)
                drawLineVertex(changeX, changeY, changeZ, bufferbuilder, entry, p / rd, (p + 1) / rd, amplitude, lr, lg, lb, la);
            tesselator.end();
            RenderSystem.lineWidth(1.0F);
            RenderSystem.enableCull();
            RenderSystem.depthMask(true);
        }
    }

    private static void drawLineVertex(float x, float y, float z, VertexConsumer buffer, PoseStack.Pose normal, float t, float t1, float amplitude, int r, int g, int b, int a) {
        float px = (float) (x * t + amplitude * Math.sin(t * 2F * Math.PI));
        float py = y * t + 0.25F;
        float pz = z * t;
        float nx = (float) (x * t1 + amplitude * Math.sin(t1 * 2F * Math.PI)) - px;
        float ny = y * t1 + 0.25F - py;
        float nz = z * t1 - pz;
        float s = Mth.sqrt(nx * nx + ny * ny + nz * nz);
        nx /= s;
        ny /= s;
        nz /= s;
        buffer.vertex(normal.pose(), px, py, pz).color(r, g, b, a).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normal.normal(), nx, ny, nz).endVertex();
    }

    public static void drawCubeFullLight(VertexConsumer builder, PoseStack matrix, double x, double y, double z, float hw, float h, float minU, float maxU, float minV, float maxV, int r, int g, int b, int a) {
        drawCubeFlat(builder, matrix, -hw + (float) x, h + (float) y, hw + (float) z, hw + (float) x, h + (float) y, -hw + (float) z, minU, minV, maxU, maxV, r, g, b, a, FULL_LIGHT);
        drawCubeFlat(builder, matrix, -hw + (float) x, -h + (float) y, -hw + (float) z, hw + (float) x, -h + (float) y, hw + (float) z, minU, minV, maxU, maxV, r, g, b, a, FULL_LIGHT);
        drawCubeFace(builder, matrix, hw + (float) x, -h + (float) y, -hw + (float) z, -hw + (float) x, h + (float) y, -hw + (float) z, minU, minV, maxU, maxV, r, g, b, a, FULL_LIGHT);
        drawCubeFace(builder, matrix, -hw + (float) x, -h + (float) y, -hw + (float) z, -hw + (float) x, h + (float) y, hw + (float) z, minU, minV, maxU, maxV, r, g, b, a, FULL_LIGHT);
        drawCubeFace(builder, matrix, -hw + (float) x, -h + (float) y, hw + (float) z, hw + (float) x, h + (float) y, hw + (float) z, minU, minV, maxU, maxV, r, g, b, a, FULL_LIGHT);
        drawCubeFace(builder, matrix, hw + (float) x, -h + (float) y, hw + (float) z, hw + (float) x, h + (float) y, -hw + (float) z, minU, minV, maxU, maxV, r, g, b, a, FULL_LIGHT);
    }

    public static void drawCubeFlat(VertexConsumer builder, PoseStack matrixStackIn, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int r, int g, int b, int a, int packedLight) {
        buildVertex(builder, matrixStackIn, x0, y0, z0, u0, v1, r, g, b, a, packedLight);
        buildVertex(builder, matrixStackIn, x1, y0, z0, u1, v1, r, g, b, a, packedLight);
        buildVertex(builder, matrixStackIn, x1, y1, z1, u1, v0, r, g, b, a, packedLight);
        buildVertex(builder, matrixStackIn, x0, y1, z1, u0, v0, r, g, b, a, packedLight);
    }

    public static void drawCubeFace(VertexConsumer builder, PoseStack matrixStackIn, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int r, int g, int b, int a, int packedLight) {
        buildVertex(builder, matrixStackIn, x0, y0, z0, u0, v1, r, g, b, a, packedLight);
        buildVertex(builder, matrixStackIn, x1, y0, z1, u1, v1, r, g, b, a, packedLight);
        buildVertex(builder, matrixStackIn, x1, y1, z1, u1, v0, r, g, b, a, packedLight);
        buildVertex(builder, matrixStackIn, x0, y1, z0, u0, v0, r, g, b, a, packedLight);
    }

    public static void buildVertex(VertexConsumer builder, PoseStack matrixStackIn, float x, float y, float z, float u, float v, int r, int g, int b, int a, int packedLight) {
        builder.vertex(matrixStackIn.last().pose(), x, y, z).color(r, g, b, a).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrixStackIn.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();
    }

    public static Vec3 calculateShootVec(Player player, double pVelocity, double pInaccuracy) {
        float f = -Mth.sin(player.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(player.getXRot() * Mth.DEG_TO_RAD);
        float f1 = -Mth.sin((player.getXRot()) * Mth.DEG_TO_RAD);
        float f2 = Mth.cos(player.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(player.getXRot() * Mth.DEG_TO_RAD);
        return calculateVec(player, pVelocity, pInaccuracy, new Vec3(f, f1, f2));
    }

    public static Vec3 calculateShootVec(Player player, double pVelocity, double pInaccuracy, float offset) {
        float f = -Mth.sin(player.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(player.getXRot() * Mth.DEG_TO_RAD);
        float f1 = -Mth.sin((player.getXRot() + offset) * Mth.DEG_TO_RAD);
        float f2 = Mth.cos(player.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(player.getXRot() * Mth.DEG_TO_RAD);
        return calculateVec(player, pVelocity, pInaccuracy, new Vec3(f, f1, f2));
    }

    public static Vec3 calculateVec(Player player, double pVelocity, double pInaccuracy, Vec3 base) {
        double f = base.x;
        double f1 = base.y;
        double f2 = base.z;
        return (new Vec3(f, f1, f2)).normalize().add(
                player.getRandom().triangle(0.0D, 0.0172275D * pInaccuracy),
                player.getRandom().triangle(0.0D, 0.0172275D * pInaccuracy),
                player.getRandom().triangle(0.0D, 0.0172275D * pInaccuracy)).scale(pVelocity);
    }

    public static List<ItemStack> getCrossbowProjectileInfo(ItemStack pCrossbowStack) {
        List<ItemStack> list = Lists.newArrayList();
        CompoundTag compoundtag = pCrossbowStack.getTag();
        if (compoundtag != null && compoundtag.contains("ChargedProjectiles", 9)) {
            ListTag listtag = compoundtag.getList("ChargedProjectiles", 10);
            for (int i = 0; i < listtag.size(); ++i) {
                list.add(ItemStack.of(listtag.getCompound(i)));
            }
        }
        return list;
    }

    public static float getPowerForTime(int releasingTime) {
        float f = (float) releasingTime / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    public static <T, S> boolean isClassOrSuperClass(T instance, Class<S> target) {
        return instance.getClass().isAssignableFrom(target);
    }
}
