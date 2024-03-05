package sonnenlichts.tje.client.render.gui;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.math.NumberUtils;
import sonnenlichts.tje.client.config.ConfigHolder;
import sonnenlichts.tje.client.config.TjeModConfig;

import java.awt.*;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class TjeModConfigScreen extends Screen {
    public static final CubeMap CUBE_MAP = new CubeMap(new ResourceLocation("textures/gui/title/background/panorama"));
    private PanoramaRenderer panorama = new PanoramaRenderer(CUBE_MAP);
    private final Screen previousScreen;
    private ScrollArea contextArea;
    private Checkbox renderPoint, targetSound, renderCube, renderLine, renderBow, renderCrossbow, renderTrident, renderSplashBottle, renderExperienceBottle, renderEgg, renderSnowball, renderEnderpearl, targetSoundBow, targetSoundCrossbow, targetSoundTrident, targetSoundSplashBottle, targetSoundExperienceBottle, targetSoundEgg, targetSoundSnowball, targetSoundEnderpearl, renderCGMGrenade, renderCGMStunGrenade, renderCGMGun,
            targetSoundCGMGrenade,
            targetSoundCGMStunGrenade,
            targetSoundCGMGun,
            renderCataclysmVoidAssaultShoulder,
            renderCataclysmWitherAssaultShoulder,
            renderCataclysmCoralSpear,
            renderCataclysmCoralBardiche,
            renderCataclysmLaserGatling,
            targetSoundCataclysmVoidAssaultShoulder,
            targetSoundCataclysmWitherAssaultShoulder,
            targetSoundCataclysmCoralSpear,
            targetSoundCataclysmCoralBardiche,
            targetSoundCataclysmLaserGatling,
            renderIceAndFireTideTrident,
            renderIceAndFireLichStaff,
            renderIceAndFireDragonBow,
            targetSoundIceAndFireTideTrident,
            targetSoundIceAndFireLichStaff,
            targetSoundIceAndFireDragonBow,
            renderBlueSkiesSpearItem,
            renderBlueSkiesVenomSacItem,
            targetSoundBlueSkiesSpearItem,
            targetSoundBlueSkiesVenomSacItem,
            renderTwilightForestIceBowItem,
            renderTwilightForestTripleBowItem,
            renderTwilightForestEnderBowItem,
            renderTwilightForestSeekerBowItem,
            renderTwilightForestTwilightWandItem,
            renderTwilightForestIceBombItem,
            targetSoundTwilightForestIceBowItem,
            targetSoundTwilightForestTripleBowItem,
            targetSoundTwilightForestEnderBowItem,
            targetSoundTwilightForestSeekerBowItem,
            targetSoundTwilightForestTwilightWandItem,
            targetSoundTwilightForestIceBombItem,
            renderImmersiveEngineeringRailgunItem,
            renderImmersiveEngineeringRevolverItem,
            renderImmersiveEngineeringChemthrowerItem,
            targetSoundImmersiveEngineeringRailgunItem,
            targetSoundImmersiveEngineeringRevolverItem,
            targetSoundImmersiveEngineeringChemthrowerItem,
            renderAlexCavesLimestoneSpearItem,
            renderAlexCavesExtinctionSpearItem,
            renderAlexCavesDreadbowItem,
            renderAlexCavesSeaStaffItem,
            targetSoundAlexCavesLimestoneSpearItem,
            targetSoundAlexCavesExtinctionSpearItem,
            targetSoundAlexCavesDreadbowItem,
            targetSoundAlexCavesSeaStaffItem,
            renderTheBumblezoneStingerSpearItem,
            renderTheBumblezoneCrystalCannonItem,
            renderTheBumblezonePollenPuff,
            renderTheBumblezoneDirtPellet,
            targetSoundTheBumblezoneStingerSpearItem,
            targetSoundTheBumblezoneCrystalCannonItem,
            targetSoundTheBumblezonePollenPuff,
            targetSoundTheBumblezoneDirtPellet,
            renderAetherPhoenixBowItem,
            renderAetherHammerOfKingbdogzItem,
            renderAetherLightningKnifeItem,
            renderAetherDartShooterItem,
            targetSoundAetherPhoenixBowItem,
            targetSoundAetherHammerOfKingbdogzItem,
            targetSoundAetherLightningKnifeItem,
            targetSoundAetherDartShooterItem,
            renderAlexsMobsItemPocketSand,
            renderAlexsMobsItemHemolymphBlaster,
            renderAlexsMobsItemBloodSprayer,
            renderAlexsMobsItemStinkRay,
            renderAlexsMobsItemVineLasso,
            targetSoundAlexsMobsItemPocketSand,
            targetSoundAlexsMobsItemHemolymphBlaster,
            targetSoundAlexsMobsItemBloodSprayer,
            targetSoundAlexsMobsItemStinkRay,
            targetSoundAlexsMobsItemVineLasso,
            renderVampirismSingleCrossbowItem,
            renderVampirismTechCrossbowItem,
            renderVampirismDoubleCrossbowItem,
            renderVampirismHolyWaterSplashBottleItem,
            targetSoundVampirismSingleCrossbowItem,
            targetSoundVampirismTechCrossbowItem,
            targetSoundVampirismDoubleCrossbowItem,
            targetSoundVampirismHolyWaterSplashBottleItem,
            renderL2WeaponryJavelinItem,
            targetSoundL2WeaponryJavelinItem,
            renderL2ArcheryGenericBowItem,
            targetSoundL2ArcheryGenericBowItem,
            renderAdventOfAscension3BaseBow,
            renderAdventOfAscension3BaseCrossbow,
            renderAdventOfAscension3BaseStaff,
            renderAdventOfAscension3BaseGun,
            renderAdventOfAscension3BaseBlaster,
            renderAdventOfAscension3BaseSniper,
            renderAdventOfAscension3BaseShotgun,
            renderAdventOfAscension3BaseCannon,
            renderAdventOfAscension3BaseThrownWeapon,
            targetSoundAdventOfAscension3BaseBow,
            targetSoundAdventOfAscension3BaseCrossbow,
            targetSoundAdventOfAscension3BaseStaff,
            targetSoundAdventOfAscension3BaseGun,
            targetSoundAdventOfAscension3BaseBlaster,
            targetSoundAdventOfAscension3BaseSniper,
            targetSoundAdventOfAscension3BaseShotgun,
            targetSoundAdventOfAscension3BaseCannon,
            targetSoundAdventOfAscension3BaseThrownWeapon,
            renderArchBowsFlatBowItem,
            renderArchBowsLongBowItem,
            renderArchBowsRecurveBowItem,
            renderArchBowsShortBowItem,
            renderArchBowsArbalestItem,
            renderArchBowsHeavyCrossbowItem,
            renderArchBowsPistolCrossbowItem,
            targetSoundArchBowsFlatBowItem,
            targetSoundArchBowsLongBowItem,
            targetSoundArchBowsRecurveBowItem,
            targetSoundArchBowsShortBowItem,
            targetSoundArchBowsArbalestItem,
            targetSoundArchBowsHeavyCrossbowItem,
            targetSoundArchBowsPistolCrossbowItem;

    private Checkbox[] checkboxes;
    private long fade;
    private EditBoxTexted cubeRed, cubeGreen, cubeBlue, cubeAlpha, cubeSize, lineRed, lineGreen, lineBlue, lineAlpha, lineWidth;
    private EditBoxTexted[] editboxes;

    private final Predicate<String> rgbaCheck = (val) -> {
        if (val.matches("^(0|[1-9][0-9]*)$")) return Integer.parseInt(val) >= 0 && Integer.parseInt(val) <= 255;
        return false;
    };
    private final Predicate<String> sizeCheck = (val) -> {
        if (NumberUtils.isCreatable(val)) return Double.parseDouble(val) >= 0.0 && Double.parseDouble(val) <= 10.0;
        return false;
    };
    private final Predicate<String> widthCheck = (val) -> {
        if (NumberUtils.isCreatable(val)) return Double.parseDouble(val) >= 1.0 && Double.parseDouble(val) <= 10.0;
        return false;
    };

    public TjeModConfigScreen(Screen previousScreen) {
        super(Component.translatable("tje.config.gui.title"));
        this.previousScreen = previousScreen;
        this.panorama = new PanoramaRenderer(CUBE_MAP);
    }

    public TjeModConfigScreen() {
        super(Component.translatable("tje.config.gui.title"));
        this.previousScreen = null;
    }

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        float f = (float) (Util.getMillis() - this.fade);
        this.panorama.render(pPartialTick, Mth.clamp(f, 0.0F, 1.0F));
        graphics.drawCenteredString(font, Component.translatable("tje.config.gui.title"), width / 2, 10, 0xffffff);
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    protected void init() {
        final int contextBtm = 10 + 10;
        this.contextArea = new ScrollArea(this, 10, contextBtm, this.width - 20, this.height - contextBtm - 40);

        this.initBox();
        this.addRenderableWidget(this.contextArea);

        Button cancelButton = Button.builder(Component.translatable("tje.config.gui.cancel"), (button -> {
            onClose();
        })).bounds(this.width / 2 - 200, this.height - 30, 100, 20).build();

        Button applyButton = Button.builder(Component.translatable("tje.config.gui.apply"), (button -> {
            this.apply();
        })).bounds(this.width / 2 + 100, this.height - 30, 100, 20).build();

        this.addRenderableWidget(cancelButton);
        this.addRenderableWidget(applyButton);
        super.init();
    }


    private void initBox() {
        int offset = -1;
        final int yTopBase = this.contextArea.y + 10;
        final int xLeftBase = this.contextArea.x + 230;

        TitleWidget label = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_1").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + 5, Color.WHITE.getRGB());
        ++offset;

        this.contextArea.addRenderableWidgetOnly(label);

        renderPoint = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.general.rt"), ConfigHolder.CLIENT.renderPoint.get());
        targetSound = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.general.ts"), ConfigHolder.CLIENT.targetSound.get());

        renderCube = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.general.rc"), ConfigHolder.CLIENT.renderCube.get());
        renderLine = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.general.rl"), ConfigHolder.CLIENT.renderLine.get());


        cubeRed = new EditBoxTexted(this.font, xLeftBase - 200, yTopBase + ((++offset) * 20) + 2, 40, 12, Component.translatable("tje.config.general.cbr"), Component.translatable("tje.config.general.cbr"), Color.WHITE, 100);
        cubeRed.setFilter(rgbaCheck);
        cubeRed.setValue(String.valueOf(ConfigHolder.CLIENT.cubeRed.get()));
        cubeRed.setHint(Component.nullToEmpty("Red: [0,255]"));

        cubeGreen = new EditBoxTexted(this.font, xLeftBase - 200, yTopBase + ((++offset) * 20) + 2, 40, 12, Component.translatable("tje.config.general.cbg"), Component.translatable("tje.config.general.cbg"), Color.WHITE, 100);
        cubeGreen.setFilter(rgbaCheck);
        cubeGreen.setValue(String.valueOf(ConfigHolder.CLIENT.cubeGreen.get()));
        cubeGreen.setHint(Component.nullToEmpty("Green: [0,255]"));

        cubeBlue = new EditBoxTexted(this.font, xLeftBase - 200, yTopBase + ((++offset) * 20) + 2, 40, 12, Component.translatable("tje.config.general.cbb"), Component.translatable("tje.config.general.cbb"), Color.WHITE, 100);
        cubeBlue.setFilter(rgbaCheck);
        cubeBlue.setValue(String.valueOf(ConfigHolder.CLIENT.cubeBlue.get()));
        cubeBlue.setHint(Component.nullToEmpty("Blue: [0,255]"));

        cubeAlpha = new EditBoxTexted(this.font, xLeftBase - 200, yTopBase + ((++offset) * 20) + 2, 40, 12, Component.translatable("tje.config.general.cba"), Component.translatable("tje.config.general.cba"), Color.WHITE, 100);
        cubeAlpha.setFilter(rgbaCheck);
        cubeAlpha.setValue(String.valueOf(ConfigHolder.CLIENT.cubeAlpha.get()));
        cubeAlpha.setHint(Component.nullToEmpty("Alpha: [0,255]"));

        cubeSize = new EditBoxTexted(this.font, xLeftBase - 200, yTopBase + ((++offset) * 20) + 2, 40, 12, Component.translatable("tje.config.general.cbs"), Component.translatable("tje.config.general.cbs"), Color.WHITE, 100);
        cubeSize.setFilter(sizeCheck);
        cubeSize.setValue(String.valueOf(ConfigHolder.CLIENT.cubeSize.get().doubleValue()));
        cubeSize.setHint(Component.nullToEmpty("Size: [0.0,10.0]"));

        lineRed = new EditBoxTexted(this.font, xLeftBase - 200, yTopBase + ((++offset) * 20) + 2, 40, 12, Component.translatable("tje.config.general.lr"), Component.translatable("tje.config.general.lr"), Color.WHITE, 100);
        lineRed.setFilter(rgbaCheck);
        lineRed.setValue(String.valueOf(ConfigHolder.CLIENT.lineRed.get()));
        lineRed.setHint(Component.nullToEmpty("Red: [0,255]"));

        lineGreen = new EditBoxTexted(this.font, xLeftBase - 200, yTopBase + ((++offset) * 20) + 2, 40, 12, Component.translatable("tje.config.general.lg"), Component.translatable("tje.config.general.lg"), Color.WHITE, 100);
        lineGreen.setFilter(rgbaCheck);
        lineGreen.setValue(String.valueOf(ConfigHolder.CLIENT.lineGreen.get()));
        lineGreen.setHint(Component.nullToEmpty("Green: [0,255]"));

        lineBlue = new EditBoxTexted(this.font, xLeftBase - 200, yTopBase + ((++offset) * 20) + 2, 40, 12, Component.translatable("tje.config.general.lb"), Component.translatable("tje.config.general.lb"), Color.WHITE, 100);
        lineBlue.setFilter(rgbaCheck);
        lineBlue.setValue(String.valueOf(ConfigHolder.CLIENT.lineBlue.get()));
        lineBlue.setHint(Component.nullToEmpty("Blue: [0,255]"));

        lineAlpha = new EditBoxTexted(this.font, xLeftBase - 200, yTopBase + ((++offset) * 20) + 2, 40, 12, Component.translatable("tje.config.general.la"), Component.translatable("tje.config.general.la"), Color.WHITE, 100);
        lineAlpha.setFilter(rgbaCheck);
        lineAlpha.setValue(String.valueOf(ConfigHolder.CLIENT.lineAlpha.get()));
        lineAlpha.setHint(Component.nullToEmpty("Alpha: [0,255]"));

        lineWidth = new EditBoxTexted(this.font, xLeftBase - 200, yTopBase + ((++offset) * 20) + 3, 40, 12, Component.translatable("tje.config.general.lw"), Component.translatable("tje.config.general.lw"), Color.WHITE, 100);
        lineWidth.setFilter(widthCheck);
        lineWidth.setValue(String.valueOf(ConfigHolder.CLIENT.lineWidth.get().doubleValue()));
        lineWidth.setHint(Component.nullToEmpty("Size: [1.0,10.0]"));

        TitleWidget labelVanilla = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_2").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelVanilla);
        renderBow = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.rb"), ConfigHolder.CLIENT.renderBow.get());
        renderCrossbow = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.rc"), ConfigHolder.CLIENT.renderCrossbow.get());
        renderTrident = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.rt"), ConfigHolder.CLIENT.renderTrident.get());
        renderSplashBottle = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.rs"), ConfigHolder.CLIENT.renderSplashBottle.get());
        renderExperienceBottle = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.reb"), ConfigHolder.CLIENT.renderExperienceBottle.get());
        renderEgg = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.re"), ConfigHolder.CLIENT.renderEgg.get());
        renderSnowball = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.rsb"), ConfigHolder.CLIENT.renderSnowball.get());
        renderEnderpearl = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.rep"), ConfigHolder.CLIENT.renderEnderpearl.get());
        targetSoundBow = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.tsb"), ConfigHolder.CLIENT.targetSoundBow.get());
        targetSoundCrossbow = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.tsc"), ConfigHolder.CLIENT.targetSoundCrossbow.get());
        targetSoundTrident = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.tst"), ConfigHolder.CLIENT.targetSoundTrident.get());
        targetSoundSplashBottle = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.tss"), ConfigHolder.CLIENT.targetSoundSplashBottle.get());
        targetSoundExperienceBottle = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.tseb"), ConfigHolder.CLIENT.targetSoundExperienceBottle.get());
        targetSoundEgg = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.tse"), ConfigHolder.CLIENT.targetSoundEgg.get());
        targetSoundSnowball = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.tssb"), ConfigHolder.CLIENT.targetSoundSnowball.get());
        targetSoundEnderpearl = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vanilla.tsep"), ConfigHolder.CLIENT.targetSoundEnderpearl.get());

        TitleWidget labelCgm = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_3").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelCgm);
        renderCGMGrenade = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cgm.rg"), ConfigHolder.CLIENT.renderCGMGrenade.get());
        renderCGMStunGrenade = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cgm.rsg"), ConfigHolder.CLIENT.renderCGMStunGrenade.get());
        renderCGMGun = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cgm.rgu"), ConfigHolder.CLIENT.renderCGMGun.get());
        targetSoundCGMGrenade = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cgm.tg"), ConfigHolder.CLIENT.targetSoundCGMGrenade.get());
        targetSoundCGMStunGrenade = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cgm.tsg"), ConfigHolder.CLIENT.targetSoundCGMStunGrenade.get());
        targetSoundCGMGun = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cgm.tgu"), ConfigHolder.CLIENT.targetSoundCGMGun.get());

        TitleWidget labelCataclysm = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_4").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelCataclysm);
        renderCataclysmVoidAssaultShoulder = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cataclysm.rvas"), ConfigHolder.CLIENT.renderCataclysmVoidAssaultShoulder.get());
        renderCataclysmWitherAssaultShoulder = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cataclysm.rwas"), ConfigHolder.CLIENT.renderCataclysmWitherAssaultShoulder.get());
        renderCataclysmCoralSpear = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cataclysm.rcs"), ConfigHolder.CLIENT.renderCataclysmCoralSpear.get());
        renderCataclysmCoralBardiche = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cataclysm.rcb"), ConfigHolder.CLIENT.renderCataclysmCoralBardiche.get());
        renderCataclysmLaserGatling = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cataclysm.rlg"), ConfigHolder.CLIENT.renderCataclysmLaserGatling.get());
        targetSoundCataclysmVoidAssaultShoulder = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cataclysm.tvas"), ConfigHolder.CLIENT.targetSoundCataclysmVoidAssaultShoulder.get());
        targetSoundCataclysmWitherAssaultShoulder = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cataclysm.twas"), ConfigHolder.CLIENT.targetSoundCataclysmWitherAssaultShoulder.get());
        targetSoundCataclysmCoralSpear = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cataclysm.tcs"), ConfigHolder.CLIENT.targetSoundCataclysmCoralSpear.get());
        targetSoundCataclysmCoralBardiche = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cataclysm.tcb"), ConfigHolder.CLIENT.targetSoundCataclysmCoralBardiche.get());
        targetSoundCataclysmLaserGatling = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.cataclysm.tlg"), ConfigHolder.CLIENT.targetSoundCataclysmLaserGatling.get());

        TitleWidget labelIAF = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_5").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelIAF);
        renderIceAndFireTideTrident = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.iceandfire.rtt"), ConfigHolder.CLIENT.renderIceAndFireTideTrident.get());
        renderIceAndFireLichStaff = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.iceandfire.rls"), ConfigHolder.CLIENT.renderIceAndFireLichStaff.get());
        renderIceAndFireDragonBow = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.iceandfire.rdb"), ConfigHolder.CLIENT.renderIceAndFireDragonBow.get());
        targetSoundIceAndFireTideTrident = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.iceandfire.ttt"), ConfigHolder.CLIENT.targetSoundIceAndFireTideTrident.get());
        targetSoundIceAndFireLichStaff = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.iceandfire.tls"), ConfigHolder.CLIENT.targetSoundIceAndFireLichStaff.get());
        targetSoundIceAndFireDragonBow = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.iceandfire.tdb"), ConfigHolder.CLIENT.targetSoundIceAndFireDragonBow.get());

        TitleWidget labelBlueS = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_6").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelBlueS);
        renderBlueSkiesSpearItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.blue_skies.rs"), ConfigHolder.CLIENT.renderBlueSkiesSpearItem.get());
        renderBlueSkiesVenomSacItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.blue_skies.rvs"), ConfigHolder.CLIENT.renderBlueSkiesVenomSacItem.get());
        targetSoundBlueSkiesSpearItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.blue_skies.ts"), ConfigHolder.CLIENT.targetSoundBlueSkiesSpearItem.get());
        targetSoundBlueSkiesVenomSacItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.blue_skies.tvs"), ConfigHolder.CLIENT.targetSoundBlueSkiesVenomSacItem.get());

        TitleWidget labelTwilight = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_7").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelTwilight);
        renderTwilightForestIceBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.twilightforest.rib"), ConfigHolder.CLIENT.renderTwilightForestIceBowItem.get());
        renderTwilightForestTripleBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.twilightforest.rtb"), ConfigHolder.CLIENT.renderTwilightForestTripleBowItem.get());
        renderTwilightForestEnderBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.twilightforest.reb"), ConfigHolder.CLIENT.renderTwilightForestEnderBowItem.get());
        renderTwilightForestSeekerBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.twilightforest.rsb"), ConfigHolder.CLIENT.renderTwilightForestSeekerBowItem.get());
        renderTwilightForestTwilightWandItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.twilightforest.rtw"), ConfigHolder.CLIENT.renderTwilightForestTwilightWandItem.get());
        renderTwilightForestIceBombItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.twilightforest.ribo"), ConfigHolder.CLIENT.renderTwilightForestIceBombItem.get());
        targetSoundTwilightForestIceBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.twilightforest.tib"), ConfigHolder.CLIENT.targetSoundTwilightForestIceBowItem.get());
        targetSoundTwilightForestTripleBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.twilightforest.ttb"), ConfigHolder.CLIENT.targetSoundTwilightForestTripleBowItem.get());
        targetSoundTwilightForestEnderBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.twilightforest.teb"), ConfigHolder.CLIENT.targetSoundTwilightForestEnderBowItem.get());
        targetSoundTwilightForestSeekerBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.twilightforest.tsb"), ConfigHolder.CLIENT.targetSoundTwilightForestSeekerBowItem.get());
        targetSoundTwilightForestTwilightWandItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.twilightforest.ttw"), ConfigHolder.CLIENT.targetSoundTwilightForestTwilightWandItem.get());
        targetSoundTwilightForestIceBombItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.twilightforest.tibo"), ConfigHolder.CLIENT.targetSoundTwilightForestIceBombItem.get());

        TitleWidget labelIE = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_8").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelIE);
        renderImmersiveEngineeringRailgunItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.immersiveengineering.rra"), ConfigHolder.CLIENT.renderImmersiveEngineeringRailgunItem.get());
        renderImmersiveEngineeringRevolverItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.immersiveengineering.rre"), ConfigHolder.CLIENT.renderImmersiveEngineeringRevolverItem.get());
        renderImmersiveEngineeringChemthrowerItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.immersiveengineering.rc"), ConfigHolder.CLIENT.renderImmersiveEngineeringChemthrowerItem.get());
        targetSoundImmersiveEngineeringRailgunItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.immersiveengineering.tra"), ConfigHolder.CLIENT.targetSoundImmersiveEngineeringRailgunItem.get());
        targetSoundImmersiveEngineeringRevolverItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.immersiveengineering.tre"), ConfigHolder.CLIENT.targetSoundImmersiveEngineeringRevolverItem.get());
        targetSoundImmersiveEngineeringChemthrowerItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.immersiveengineering.tc"), ConfigHolder.CLIENT.targetSoundImmersiveEngineeringChemthrowerItem.get());

        TitleWidget labelAC = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_9").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelAC);
        renderAlexCavesDreadbowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexscaves.rls"), ConfigHolder.CLIENT.renderAlexCavesDreadbowItem.get());
        renderAlexCavesExtinctionSpearItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexscaves.res"), ConfigHolder.CLIENT.renderAlexCavesExtinctionSpearItem.get());
        renderAlexCavesLimestoneSpearItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexscaves.rd"), ConfigHolder.CLIENT.renderAlexCavesLimestoneSpearItem.get());
        renderAlexCavesSeaStaffItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexscaves.rss"), ConfigHolder.CLIENT.renderAlexCavesSeaStaffItem.get());
        targetSoundAlexCavesDreadbowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexscaves.tls"), ConfigHolder.CLIENT.targetSoundAlexCavesDreadbowItem.get());
        targetSoundAlexCavesExtinctionSpearItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexscaves.tes"), ConfigHolder.CLIENT.targetSoundAlexCavesExtinctionSpearItem.get());
        targetSoundAlexCavesLimestoneSpearItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexscaves.td"), ConfigHolder.CLIENT.targetSoundAlexCavesLimestoneSpearItem.get());
        targetSoundAlexCavesSeaStaffItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexscaves.tss"), ConfigHolder.CLIENT.targetSoundAlexCavesSeaStaffItem.get());

        TitleWidget labelTB = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_10").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelTB);
        renderTheBumblezoneCrystalCannonItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.the_bumblezone.rss"), ConfigHolder.CLIENT.renderTheBumblezoneCrystalCannonItem.get());
        renderTheBumblezoneStingerSpearItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.the_bumblezone.rcc"), ConfigHolder.CLIENT.renderTheBumblezoneStingerSpearItem.get());
        renderTheBumblezonePollenPuff = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.the_bumblezone.rpp"), ConfigHolder.CLIENT.renderTheBumblezonePollenPuff.get());
        renderTheBumblezoneDirtPellet = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.the_bumblezone.rdp"), ConfigHolder.CLIENT.renderTheBumblezoneDirtPellet.get());
        targetSoundTheBumblezoneCrystalCannonItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.the_bumblezone.tss"), ConfigHolder.CLIENT.targetSoundTheBumblezoneCrystalCannonItem.get());
        targetSoundTheBumblezoneStingerSpearItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.the_bumblezone.tcc"), ConfigHolder.CLIENT.targetSoundTheBumblezoneStingerSpearItem.get());
        targetSoundTheBumblezonePollenPuff = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.the_bumblezone.tpp"), ConfigHolder.CLIENT.targetSoundTheBumblezonePollenPuff.get());
        targetSoundTheBumblezoneDirtPellet = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.the_bumblezone.tdp"), ConfigHolder.CLIENT.targetSoundTheBumblezoneDirtPellet.get());


        TitleWidget labelA = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_11").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelA);
        renderAetherPhoenixBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aether.rpbi"), ConfigHolder.CLIENT.renderAetherPhoenixBowItem.get());
        renderAetherHammerOfKingbdogzItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aether.rhoki"), ConfigHolder.CLIENT.renderAetherHammerOfKingbdogzItem.get());
        renderAetherLightningKnifeItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aether.rlki"), ConfigHolder.CLIENT.renderAetherLightningKnifeItem.get());
        renderAetherDartShooterItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aether.rdsi"), ConfigHolder.CLIENT.renderAetherDartShooterItem.get());
        targetSoundAetherPhoenixBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aether.tpbi"), ConfigHolder.CLIENT.targetSoundAetherPhoenixBowItem.get());
        targetSoundAetherHammerOfKingbdogzItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aether.thoki"), ConfigHolder.CLIENT.targetSoundAetherHammerOfKingbdogzItem.get());
        targetSoundAetherLightningKnifeItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aether.tlki"), ConfigHolder.CLIENT.targetSoundAetherLightningKnifeItem.get());
        targetSoundAetherDartShooterItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aether.tdsi"), ConfigHolder.CLIENT.targetSoundAetherDartShooterItem.get());

        TitleWidget labelAM = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_13").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelAM);
        renderAlexsMobsItemPocketSand = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexsmobs.rips"), ConfigHolder.CLIENT.renderAlexsMobsItemPocketSand.get());
        renderAlexsMobsItemHemolymphBlaster = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexsmobs.rihb"), ConfigHolder.CLIENT.renderAlexsMobsItemHemolymphBlaster.get());
        renderAlexsMobsItemBloodSprayer = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexsmobs.ribs"), ConfigHolder.CLIENT.renderAlexsMobsItemBloodSprayer.get());
        renderAlexsMobsItemStinkRay = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexsmobs.risr"), ConfigHolder.CLIENT.renderAlexsMobsItemStinkRay.get());
        renderAlexsMobsItemVineLasso = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexsmobs.rivl"), ConfigHolder.CLIENT.renderAlexsMobsItemVineLasso.get());
        targetSoundAlexsMobsItemPocketSand = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexsmobs.tips"), ConfigHolder.CLIENT.targetSoundAlexsMobsItemPocketSand.get());
        targetSoundAlexsMobsItemHemolymphBlaster = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexsmobs.tihb"), ConfigHolder.CLIENT.targetSoundAlexsMobsItemHemolymphBlaster.get());
        targetSoundAlexsMobsItemBloodSprayer = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexsmobs.tibs"), ConfigHolder.CLIENT.targetSoundAlexsMobsItemBloodSprayer.get());
        targetSoundAlexsMobsItemStinkRay = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexsmobs.tisr"), ConfigHolder.CLIENT.targetSoundAlexsMobsItemStinkRay.get());
        targetSoundAlexsMobsItemVineLasso = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.alexsmobs.tivl"), ConfigHolder.CLIENT.targetSoundAlexsMobsItemVineLasso.get());

        TitleWidget labelV = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_14").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelV);
        renderVampirismSingleCrossbowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vampirism.rsci"), ConfigHolder.CLIENT.renderVampirismSingleCrossbowItem.get());
        renderVampirismTechCrossbowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vampirism.rtci"), ConfigHolder.CLIENT.renderVampirismTechCrossbowItem.get());
        renderVampirismDoubleCrossbowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vampirism.rdci"), ConfigHolder.CLIENT.renderVampirismDoubleCrossbowItem.get());
        renderVampirismHolyWaterSplashBottleItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vampirism.rhwsbi"), ConfigHolder.CLIENT.renderVampirismHolyWaterSplashBottleItem.get());
        targetSoundVampirismSingleCrossbowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vampirism.tsci"), ConfigHolder.CLIENT.targetSoundVampirismSingleCrossbowItem.get());
        targetSoundVampirismTechCrossbowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vampirism.ttci"), ConfigHolder.CLIENT.targetSoundVampirismTechCrossbowItem.get());
        targetSoundVampirismDoubleCrossbowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vampirism.tdci"), ConfigHolder.CLIENT.targetSoundVampirismDoubleCrossbowItem.get());
        targetSoundVampirismHolyWaterSplashBottleItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.vampirism.thwsbi"), ConfigHolder.CLIENT.targetSoundVampirismHolyWaterSplashBottleItem.get());

        TitleWidget labelLW = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_15").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelLW);
        renderL2WeaponryJavelinItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.l2weaponry.rji"), ConfigHolder.CLIENT.renderL2WeaponryJavelinItem.get());
        targetSoundL2WeaponryJavelinItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.l2weaponry.tji"), ConfigHolder.CLIENT.targetSoundL2WeaponryJavelinItem.get());

        TitleWidget labelLA = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_16").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelLA);
        renderL2ArcheryGenericBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.l2archery.rgbi"), ConfigHolder.CLIENT.renderL2ArcheryGenericBowItem.get());
        targetSoundL2ArcheryGenericBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.l2archery.tgbi"), ConfigHolder.CLIENT.targetSoundL2ArcheryGenericBowItem.get());

        TitleWidget labelAOA = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_17").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelAOA);
        renderAdventOfAscension3BaseBow = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.rbb"), ConfigHolder.CLIENT.renderAdventOfAscension3BaseBow.get());
        renderAdventOfAscension3BaseCrossbow = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.rbc"), ConfigHolder.CLIENT.renderAdventOfAscension3BaseCrossbow.get());
        renderAdventOfAscension3BaseStaff = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.rbs"), ConfigHolder.CLIENT.renderAdventOfAscension3BaseStaff.get());
        renderAdventOfAscension3BaseGun = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.rbg"), ConfigHolder.CLIENT.renderAdventOfAscension3BaseGun.get());
        renderAdventOfAscension3BaseBlaster = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.rbb2"), ConfigHolder.CLIENT.renderAdventOfAscension3BaseBlaster.get());
        renderAdventOfAscension3BaseSniper = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.rbs2"), ConfigHolder.CLIENT.renderAdventOfAscension3BaseSniper.get());
        renderAdventOfAscension3BaseShotgun = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.rbs3"), ConfigHolder.CLIENT.renderAdventOfAscension3BaseShotgun.get());
        renderAdventOfAscension3BaseCannon = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.rbc2"), ConfigHolder.CLIENT.renderAdventOfAscension3BaseCannon.get());
        renderAdventOfAscension3BaseThrownWeapon = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.rbtw"), ConfigHolder.CLIENT.renderAdventOfAscension3BaseThrownWeapon.get());
        targetSoundAdventOfAscension3BaseBow = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.tbb"), ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseBow.get());
        targetSoundAdventOfAscension3BaseCrossbow = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.tbc"), ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseCrossbow.get());
        targetSoundAdventOfAscension3BaseStaff = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.tbs"), ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseStaff.get());
        targetSoundAdventOfAscension3BaseGun = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.tbg"), ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseGun.get());
        targetSoundAdventOfAscension3BaseBlaster = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.tbb2"), ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseBlaster.get());
        targetSoundAdventOfAscension3BaseSniper = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.tbs2"), ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseSniper.get());
        targetSoundAdventOfAscension3BaseShotgun = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.tbs3"), ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseShotgun.get());
        targetSoundAdventOfAscension3BaseCannon = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.tbc2"), ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseCannon.get());
        targetSoundAdventOfAscension3BaseThrownWeapon = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.aoa3.tbtw"), ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseThrownWeapon.get());

        TitleWidget labelAB = new TitleWidget(this.font, Component.translatable("tje.config.gui.label_19").withStyle(ChatFormatting.BOLD), xLeftBase - 200, yTopBase + ((++offset) * 20) + 5, Color.WHITE.getRGB());
        this.contextArea.addRenderableWidgetOnly(labelAB);
        renderArchBowsFlatBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.archbows.rfbi"), ConfigHolder.CLIENT.renderArchBowsFlatBowItem.get());
        renderArchBowsLongBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.archbows.rlbi"), ConfigHolder.CLIENT.renderArchBowsLongBowItem.get());
        renderArchBowsRecurveBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.archbows.rrbi"), ConfigHolder.CLIENT.renderArchBowsRecurveBowItem.get());
        renderArchBowsShortBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.archbows.rsbi"), ConfigHolder.CLIENT.renderArchBowsShortBowItem.get());
        renderArchBowsArbalestItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.archbows.rai"), ConfigHolder.CLIENT.renderArchBowsArbalestItem.get());
        renderArchBowsHeavyCrossbowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.archbows.rhci"), ConfigHolder.CLIENT.renderArchBowsHeavyCrossbowItem.get());
        renderArchBowsPistolCrossbowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.archbows.rpci"), ConfigHolder.CLIENT.renderArchBowsPistolCrossbowItem.get());
        targetSoundArchBowsFlatBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.archbows.tfbi"), ConfigHolder.CLIENT.targetSoundArchBowsFlatBowItem.get());
        targetSoundArchBowsLongBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.archbows.tlbi"), ConfigHolder.CLIENT.targetSoundArchBowsLongBowItem.get());
        targetSoundArchBowsRecurveBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.archbows.trbi"), ConfigHolder.CLIENT.targetSoundArchBowsRecurveBowItem.get());
        targetSoundArchBowsShortBowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.archbows.tsbi"), ConfigHolder.CLIENT.targetSoundArchBowsShortBowItem.get());
        targetSoundArchBowsArbalestItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.archbows.tai"), ConfigHolder.CLIENT.targetSoundArchBowsArbalestItem.get());
        targetSoundArchBowsHeavyCrossbowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.archbows.thci"), ConfigHolder.CLIENT.targetSoundArchBowsHeavyCrossbowItem.get());
        targetSoundArchBowsPistolCrossbowItem = new Checkbox(xLeftBase - 200, yTopBase + ((++offset) * 20), 20, 20, Component.translatable("tje.config.archbows.tpci"), ConfigHolder.CLIENT.targetSoundArchBowsPistolCrossbowItem.get());

        checkboxes = new Checkbox[]{
                renderPoint, targetSound, renderCube, renderLine, renderBow, renderCrossbow, renderTrident, renderSplashBottle, renderExperienceBottle, renderEgg, renderSnowball, renderEnderpearl, targetSoundBow, targetSoundCrossbow, targetSoundTrident, targetSoundSplashBottle, targetSoundExperienceBottle, targetSoundEgg, targetSoundSnowball, targetSoundEnderpearl, renderCGMGrenade, renderCGMStunGrenade, renderCGMGun,
                targetSoundCGMGrenade,
                targetSoundCGMStunGrenade,
                targetSoundCGMGun,
                renderCataclysmVoidAssaultShoulder,
                renderCataclysmWitherAssaultShoulder,
                renderCataclysmCoralSpear,
                renderCataclysmCoralBardiche,
                renderCataclysmLaserGatling,
                targetSoundCataclysmVoidAssaultShoulder,
                targetSoundCataclysmWitherAssaultShoulder,
                targetSoundCataclysmCoralSpear,
                targetSoundCataclysmCoralBardiche,
                targetSoundCataclysmLaserGatling,
                renderIceAndFireTideTrident,
                renderIceAndFireLichStaff,
                renderIceAndFireDragonBow,
                targetSoundIceAndFireTideTrident,
                targetSoundIceAndFireLichStaff,
                targetSoundIceAndFireDragonBow,
                renderBlueSkiesSpearItem,
                renderBlueSkiesVenomSacItem,
                targetSoundBlueSkiesSpearItem,
                targetSoundBlueSkiesVenomSacItem,
                renderTwilightForestIceBowItem,
                renderTwilightForestTripleBowItem,
                renderTwilightForestEnderBowItem,
                renderTwilightForestSeekerBowItem,
                renderTwilightForestTwilightWandItem,
                renderTwilightForestIceBombItem,
                targetSoundTwilightForestIceBowItem,
                targetSoundTwilightForestTripleBowItem,
                targetSoundTwilightForestEnderBowItem,
                targetSoundTwilightForestSeekerBowItem,
                targetSoundTwilightForestTwilightWandItem,
                targetSoundTwilightForestIceBombItem,
                renderImmersiveEngineeringRailgunItem,
                renderImmersiveEngineeringRevolverItem,
                renderImmersiveEngineeringChemthrowerItem,
                targetSoundImmersiveEngineeringRailgunItem,
                targetSoundImmersiveEngineeringRevolverItem,
                targetSoundImmersiveEngineeringChemthrowerItem,

                renderAlexCavesLimestoneSpearItem,
                renderAlexCavesExtinctionSpearItem,
                renderAlexCavesDreadbowItem,
                renderAlexCavesSeaStaffItem,
                targetSoundAlexCavesLimestoneSpearItem,
                targetSoundAlexCavesExtinctionSpearItem,
                targetSoundAlexCavesDreadbowItem,
                targetSoundAlexCavesSeaStaffItem,

                renderTheBumblezoneStingerSpearItem,
                renderTheBumblezoneCrystalCannonItem,
                renderTheBumblezonePollenPuff,
                renderTheBumblezoneDirtPellet,
                targetSoundTheBumblezoneStingerSpearItem,
                targetSoundTheBumblezoneCrystalCannonItem,
                targetSoundTheBumblezonePollenPuff,
                targetSoundTheBumblezoneDirtPellet,

                renderAetherPhoenixBowItem,
                renderAetherHammerOfKingbdogzItem,
                renderAetherLightningKnifeItem,
                renderAetherDartShooterItem,
                targetSoundAetherPhoenixBowItem,
                targetSoundAetherHammerOfKingbdogzItem,
                targetSoundAetherLightningKnifeItem,
                targetSoundAetherDartShooterItem,

                renderAlexsMobsItemPocketSand,
                renderAlexsMobsItemHemolymphBlaster,
                renderAlexsMobsItemBloodSprayer,
                renderAlexsMobsItemStinkRay,
                renderAlexsMobsItemVineLasso,
                targetSoundAlexsMobsItemPocketSand,
                targetSoundAlexsMobsItemHemolymphBlaster,
                targetSoundAlexsMobsItemBloodSprayer,
                targetSoundAlexsMobsItemStinkRay,
                targetSoundAlexsMobsItemVineLasso,

                renderVampirismSingleCrossbowItem,
                renderVampirismTechCrossbowItem,
                renderVampirismDoubleCrossbowItem,
                renderVampirismHolyWaterSplashBottleItem,
                targetSoundVampirismSingleCrossbowItem,
                targetSoundVampirismTechCrossbowItem,
                targetSoundVampirismDoubleCrossbowItem,
                targetSoundVampirismHolyWaterSplashBottleItem,

                renderL2WeaponryJavelinItem,
                targetSoundL2WeaponryJavelinItem,

                renderL2ArcheryGenericBowItem,
                targetSoundL2ArcheryGenericBowItem,

                renderAdventOfAscension3BaseBow,
                renderAdventOfAscension3BaseCrossbow,
                renderAdventOfAscension3BaseStaff,
                renderAdventOfAscension3BaseGun,
                renderAdventOfAscension3BaseBlaster,
                renderAdventOfAscension3BaseSniper,
                renderAdventOfAscension3BaseShotgun,
                renderAdventOfAscension3BaseCannon,
                renderAdventOfAscension3BaseThrownWeapon,
                targetSoundAdventOfAscension3BaseBow,
                targetSoundAdventOfAscension3BaseCrossbow,
                targetSoundAdventOfAscension3BaseStaff,
                targetSoundAdventOfAscension3BaseGun,
                targetSoundAdventOfAscension3BaseBlaster,
                targetSoundAdventOfAscension3BaseSniper,
                targetSoundAdventOfAscension3BaseShotgun,
                targetSoundAdventOfAscension3BaseCannon,
                targetSoundAdventOfAscension3BaseThrownWeapon,

                renderArchBowsFlatBowItem,
                renderArchBowsLongBowItem,
                renderArchBowsRecurveBowItem,
                renderArchBowsShortBowItem,
                renderArchBowsArbalestItem,
                renderArchBowsHeavyCrossbowItem,
                renderArchBowsPistolCrossbowItem,
                targetSoundArchBowsFlatBowItem,
                targetSoundArchBowsLongBowItem,
                targetSoundArchBowsRecurveBowItem,
                targetSoundArchBowsShortBowItem,
                targetSoundArchBowsArbalestItem,
                targetSoundArchBowsHeavyCrossbowItem,
                targetSoundArchBowsPistolCrossbowItem
        };
        editboxes = new EditBoxTexted[]{
                cubeRed, cubeGreen, cubeBlue, cubeAlpha, cubeSize, lineRed, lineGreen, lineBlue, lineAlpha, lineWidth,
        };
        for (Checkbox checkbox : checkboxes) {
            this.contextArea.addRenderableWidget(checkbox);
        }
        for (EditBoxTexted editBox : editboxes) {
            this.contextArea.addRenderableWidget(editBox);
        }
    }

    @Override
    public void tick() {
        if (editboxes != null && editboxes.length != 0) {
            for (EditBoxTexted editBox : editboxes) {
                if (editBox != null)
                    editBox.tick();
            }
        }
    }


    private void apply() {
        ConfigHolder.CLIENT.renderPoint.set(renderPoint.selected());
        ConfigHolder.CLIENT.targetSound.set(targetSound.selected());
        ConfigHolder.CLIENT.renderCube.set(renderCube.selected());
        ConfigHolder.CLIENT.renderLine.set(renderLine.selected());
        ConfigHolder.CLIENT.cubeRed.set(Integer.parseInt(cubeRed.getValue()));
        ConfigHolder.CLIENT.cubeGreen.set(Integer.parseInt(cubeGreen.getValue()));
        ConfigHolder.CLIENT.cubeBlue.set(Integer.parseInt(cubeBlue.getValue()));
        ConfigHolder.CLIENT.cubeAlpha.set(Integer.parseInt(cubeAlpha.getValue()));
        ConfigHolder.CLIENT.cubeSize.set(Double.parseDouble(cubeSize.getValue()));
        ConfigHolder.CLIENT.lineRed.set(Integer.parseInt(lineRed.getValue()));
        ConfigHolder.CLIENT.lineGreen.set(Integer.parseInt(lineGreen.getValue()));
        ConfigHolder.CLIENT.lineBlue.set(Integer.parseInt(lineBlue.getValue()));
        ConfigHolder.CLIENT.lineAlpha.set(Integer.parseInt(lineAlpha.getValue()));
        ConfigHolder.CLIENT.lineWidth.set(Double.parseDouble(lineWidth.getValue()));
        ConfigHolder.CLIENT.renderBow.set(renderBow.selected());
        ConfigHolder.CLIENT.renderCrossbow.set(renderCrossbow.selected());
        ConfigHolder.CLIENT.renderTrident.set(renderTrident.selected());
        ConfigHolder.CLIENT.renderSplashBottle.set(renderSplashBottle.selected());
        ConfigHolder.CLIENT.renderExperienceBottle.set(renderExperienceBottle.selected());
        ConfigHolder.CLIENT.renderEgg.set(renderEgg.selected());
        ConfigHolder.CLIENT.renderSnowball.set(renderSnowball.selected());
        ConfigHolder.CLIENT.renderEnderpearl.set(renderEnderpearl.selected());
        ConfigHolder.CLIENT.targetSoundBow.set(targetSoundBow.selected());
        ConfigHolder.CLIENT.targetSoundCrossbow.set(targetSoundCrossbow.selected());
        ConfigHolder.CLIENT.targetSoundTrident.set(targetSoundTrident.selected());
        ConfigHolder.CLIENT.targetSoundSplashBottle.set(targetSoundSplashBottle.selected());
        ConfigHolder.CLIENT.targetSoundExperienceBottle.set(targetSoundExperienceBottle.selected());
        ConfigHolder.CLIENT.targetSoundEgg.set(targetSoundEgg.selected());
        ConfigHolder.CLIENT.targetSoundSnowball.set(targetSoundSnowball.selected());
        ConfigHolder.CLIENT.targetSoundEnderpearl.set(targetSoundEnderpearl.selected());
        ConfigHolder.CLIENT.renderCGMGrenade.set(renderCGMGrenade.selected());
        ConfigHolder.CLIENT.renderCGMStunGrenade.set(renderCGMStunGrenade.selected());
        ConfigHolder.CLIENT.renderCGMGun.set(renderCGMGun.selected());
        ConfigHolder.CLIENT.targetSoundCGMGrenade.set(targetSoundCGMGrenade.selected());
        ConfigHolder.CLIENT.targetSoundCGMStunGrenade.set(targetSoundCGMStunGrenade.selected());
        ConfigHolder.CLIENT.targetSoundCGMGun.set(targetSoundCGMGun.selected());
        ConfigHolder.CLIENT.renderCataclysmVoidAssaultShoulder.set(renderCataclysmVoidAssaultShoulder.selected());
        ConfigHolder.CLIENT.renderCataclysmWitherAssaultShoulder.set(renderCataclysmWitherAssaultShoulder.selected());
        ConfigHolder.CLIENT.renderCataclysmCoralSpear.set(renderCataclysmCoralSpear.selected());
        ConfigHolder.CLIENT.renderCataclysmCoralBardiche.set(renderCataclysmCoralBardiche.selected());
        ConfigHolder.CLIENT.renderCataclysmLaserGatling.set(renderCataclysmLaserGatling.selected());
        ConfigHolder.CLIENT.targetSoundCataclysmVoidAssaultShoulder.set(targetSoundCataclysmVoidAssaultShoulder.selected());
        ConfigHolder.CLIENT.targetSoundCataclysmWitherAssaultShoulder.set(targetSoundCataclysmWitherAssaultShoulder.selected());
        ConfigHolder.CLIENT.targetSoundCataclysmCoralSpear.set(targetSoundCataclysmCoralSpear.selected());
        ConfigHolder.CLIENT.targetSoundCataclysmCoralBardiche.set(targetSoundCataclysmCoralBardiche.selected());
        ConfigHolder.CLIENT.targetSoundCataclysmLaserGatling.set(targetSoundCataclysmLaserGatling.selected());
        ConfigHolder.CLIENT.renderIceAndFireTideTrident.set(renderIceAndFireTideTrident.selected());
        ConfigHolder.CLIENT.renderIceAndFireLichStaff.set(renderIceAndFireLichStaff.selected());
        ConfigHolder.CLIENT.renderIceAndFireDragonBow.set(renderIceAndFireDragonBow.selected());
        ConfigHolder.CLIENT.targetSoundIceAndFireTideTrident.set(targetSoundIceAndFireTideTrident.selected());
        ConfigHolder.CLIENT.targetSoundIceAndFireLichStaff.set(targetSoundIceAndFireLichStaff.selected());
        ConfigHolder.CLIENT.targetSoundIceAndFireDragonBow.set(targetSoundIceAndFireDragonBow.selected());
        ConfigHolder.CLIENT.renderBlueSkiesSpearItem.set(renderBlueSkiesSpearItem.selected());
        ConfigHolder.CLIENT.renderBlueSkiesVenomSacItem.set(renderBlueSkiesVenomSacItem.selected());
        ConfigHolder.CLIENT.targetSoundBlueSkiesSpearItem.set(targetSoundBlueSkiesSpearItem.selected());
        ConfigHolder.CLIENT.targetSoundBlueSkiesVenomSacItem.set(targetSoundBlueSkiesVenomSacItem.selected());
        ConfigHolder.CLIENT.renderTwilightForestIceBowItem.set(renderTwilightForestIceBowItem.selected());
        ConfigHolder.CLIENT.renderTwilightForestTripleBowItem.set(renderTwilightForestTripleBowItem.selected());
        ConfigHolder.CLIENT.renderTwilightForestEnderBowItem.set(renderTwilightForestEnderBowItem.selected());
        ConfigHolder.CLIENT.renderTwilightForestSeekerBowItem.set(renderTwilightForestSeekerBowItem.selected());
        ConfigHolder.CLIENT.renderTwilightForestTwilightWandItem.set(renderTwilightForestTwilightWandItem.selected());
        ConfigHolder.CLIENT.renderTwilightForestIceBombItem.set(renderTwilightForestIceBombItem.selected());
        ConfigHolder.CLIENT.targetSoundTwilightForestIceBowItem.set(targetSoundTwilightForestIceBowItem.selected());
        ConfigHolder.CLIENT.targetSoundTwilightForestTripleBowItem.set(targetSoundTwilightForestTripleBowItem.selected());
        ConfigHolder.CLIENT.targetSoundTwilightForestEnderBowItem.set(targetSoundTwilightForestEnderBowItem.selected());
        ConfigHolder.CLIENT.targetSoundTwilightForestSeekerBowItem.set(targetSoundTwilightForestSeekerBowItem.selected());
        ConfigHolder.CLIENT.targetSoundTwilightForestTwilightWandItem.set(targetSoundTwilightForestTwilightWandItem.selected());
        ConfigHolder.CLIENT.targetSoundTwilightForestIceBombItem.set(targetSoundTwilightForestIceBombItem.selected());
        ConfigHolder.CLIENT.renderImmersiveEngineeringRailgunItem.set(renderImmersiveEngineeringRailgunItem.selected());
        ConfigHolder.CLIENT.renderImmersiveEngineeringRevolverItem.set(renderImmersiveEngineeringRevolverItem.selected());
        ConfigHolder.CLIENT.renderImmersiveEngineeringChemthrowerItem.set(renderImmersiveEngineeringChemthrowerItem.selected());
        ConfigHolder.CLIENT.targetSoundImmersiveEngineeringRailgunItem.set(targetSoundImmersiveEngineeringRailgunItem.selected());
        ConfigHolder.CLIENT.targetSoundImmersiveEngineeringRevolverItem.set(targetSoundImmersiveEngineeringRevolverItem.selected());
        ConfigHolder.CLIENT.targetSoundImmersiveEngineeringChemthrowerItem.set(targetSoundImmersiveEngineeringChemthrowerItem.selected());

        ConfigHolder.CLIENT.renderAlexCavesDreadbowItem.set(renderAlexCavesDreadbowItem.selected());
        ConfigHolder.CLIENT.renderAlexCavesExtinctionSpearItem.set(renderAlexCavesExtinctionSpearItem.selected());
        ConfigHolder.CLIENT.renderAlexCavesLimestoneSpearItem.set(renderAlexCavesLimestoneSpearItem.selected());
        ConfigHolder.CLIENT.renderAlexCavesSeaStaffItem.set(renderAlexCavesSeaStaffItem.selected());
        ConfigHolder.CLIENT.targetSoundAlexCavesDreadbowItem.set(targetSoundAlexCavesDreadbowItem.selected());
        ConfigHolder.CLIENT.targetSoundAlexCavesExtinctionSpearItem.set(targetSoundAlexCavesExtinctionSpearItem.selected());
        ConfigHolder.CLIENT.targetSoundAlexCavesLimestoneSpearItem.set(targetSoundAlexCavesLimestoneSpearItem.selected());
        ConfigHolder.CLIENT.targetSoundAlexCavesSeaStaffItem.set(targetSoundAlexCavesSeaStaffItem.selected());

        ConfigHolder.CLIENT.renderTheBumblezoneCrystalCannonItem.set(renderTheBumblezoneCrystalCannonItem.selected());
        ConfigHolder.CLIENT.renderTheBumblezoneStingerSpearItem.set(renderTheBumblezoneStingerSpearItem.selected());
        ConfigHolder.CLIENT.renderTheBumblezonePollenPuff.set(renderTheBumblezonePollenPuff.selected());
        ConfigHolder.CLIENT.renderTheBumblezoneDirtPellet.set(renderTheBumblezoneDirtPellet.selected());
        ConfigHolder.CLIENT.targetSoundTheBumblezoneCrystalCannonItem.set(targetSoundTheBumblezoneCrystalCannonItem.selected());
        ConfigHolder.CLIENT.targetSoundTheBumblezoneStingerSpearItem.set(targetSoundTheBumblezoneStingerSpearItem.selected());
        ConfigHolder.CLIENT.targetSoundTheBumblezonePollenPuff.set(targetSoundTheBumblezonePollenPuff.selected());
        ConfigHolder.CLIENT.targetSoundTheBumblezoneDirtPellet.set(targetSoundTheBumblezoneDirtPellet.selected());

        ConfigHolder.CLIENT.renderAetherPhoenixBowItem.set(renderAetherPhoenixBowItem.selected());
        ConfigHolder.CLIENT.renderAetherHammerOfKingbdogzItem.set(renderAetherHammerOfKingbdogzItem.selected());
        ConfigHolder.CLIENT.renderAetherLightningKnifeItem.set(renderAetherLightningKnifeItem.selected());
        ConfigHolder.CLIENT.renderAetherDartShooterItem.set(renderAetherDartShooterItem.selected());
        ConfigHolder.CLIENT.targetSoundAetherPhoenixBowItem.set(targetSoundAetherPhoenixBowItem.selected());
        ConfigHolder.CLIENT.targetSoundAetherHammerOfKingbdogzItem.set(targetSoundAetherHammerOfKingbdogzItem.selected());
        ConfigHolder.CLIENT.targetSoundAetherLightningKnifeItem.set(targetSoundAetherLightningKnifeItem.selected());
        ConfigHolder.CLIENT.targetSoundAetherDartShooterItem.set(targetSoundAetherDartShooterItem.selected());

        ConfigHolder.CLIENT.renderAlexsMobsItemPocketSand.set(renderAlexsMobsItemPocketSand.selected());
        ConfigHolder.CLIENT.renderAlexsMobsItemHemolymphBlaster.set(renderAlexsMobsItemHemolymphBlaster.selected());
        ConfigHolder.CLIENT.renderAlexsMobsItemBloodSprayer.set(renderAlexsMobsItemBloodSprayer.selected());
        ConfigHolder.CLIENT.renderAlexsMobsItemStinkRay.set(renderAlexsMobsItemStinkRay.selected());
        ConfigHolder.CLIENT.renderAlexsMobsItemVineLasso.set(renderAlexsMobsItemVineLasso.selected());
        ConfigHolder.CLIENT.targetSoundAlexsMobsItemPocketSand.set(targetSoundAlexsMobsItemPocketSand.selected());
        ConfigHolder.CLIENT.targetSoundAlexsMobsItemHemolymphBlaster.set(targetSoundAlexsMobsItemHemolymphBlaster.selected());
        ConfigHolder.CLIENT.targetSoundAlexsMobsItemBloodSprayer.set(targetSoundAlexsMobsItemBloodSprayer.selected());
        ConfigHolder.CLIENT.targetSoundAlexsMobsItemStinkRay.set(targetSoundAlexsMobsItemStinkRay.selected());
        ConfigHolder.CLIENT.targetSoundAlexsMobsItemVineLasso.set(targetSoundAlexsMobsItemVineLasso.selected());

        ConfigHolder.CLIENT.renderVampirismSingleCrossbowItem.set(renderVampirismSingleCrossbowItem.selected());
        ConfigHolder.CLIENT.renderVampirismTechCrossbowItem.set(renderVampirismTechCrossbowItem.selected());
        ConfigHolder.CLIENT.renderVampirismDoubleCrossbowItem.set(renderVampirismDoubleCrossbowItem.selected());
        ConfigHolder.CLIENT.renderVampirismHolyWaterSplashBottleItem.set(renderVampirismHolyWaterSplashBottleItem.selected());
        ConfigHolder.CLIENT.targetSoundVampirismSingleCrossbowItem.set(targetSoundVampirismSingleCrossbowItem.selected());
        ConfigHolder.CLIENT.targetSoundVampirismTechCrossbowItem.set(targetSoundVampirismTechCrossbowItem.selected());
        ConfigHolder.CLIENT.targetSoundVampirismDoubleCrossbowItem.set(targetSoundVampirismDoubleCrossbowItem.selected());
        ConfigHolder.CLIENT.targetSoundVampirismHolyWaterSplashBottleItem.set(targetSoundVampirismHolyWaterSplashBottleItem.selected());

        ConfigHolder.CLIENT.renderL2WeaponryJavelinItem.set(renderL2WeaponryJavelinItem.selected());
        ConfigHolder.CLIENT.targetSoundL2WeaponryJavelinItem.set(targetSoundL2WeaponryJavelinItem.selected());

        ConfigHolder.CLIENT.renderL2ArcheryGenericBowItem.set(renderL2ArcheryGenericBowItem.selected());
        ConfigHolder.CLIENT.targetSoundL2ArcheryGenericBowItem.set(targetSoundL2ArcheryGenericBowItem.selected());

        ConfigHolder.CLIENT.renderAdventOfAscension3BaseBow.set(renderAdventOfAscension3BaseBow.selected());
        ConfigHolder.CLIENT.renderAdventOfAscension3BaseCrossbow.set(renderAdventOfAscension3BaseCrossbow.selected());
        ConfigHolder.CLIENT.renderAdventOfAscension3BaseStaff.set(renderAdventOfAscension3BaseStaff.selected());
        ConfigHolder.CLIENT.renderAdventOfAscension3BaseGun.set(renderAdventOfAscension3BaseGun.selected());
        ConfigHolder.CLIENT.renderAdventOfAscension3BaseBlaster.set(renderAdventOfAscension3BaseBlaster.selected());
        ConfigHolder.CLIENT.renderAdventOfAscension3BaseSniper.set(renderAdventOfAscension3BaseSniper.selected());
        ConfigHolder.CLIENT.renderAdventOfAscension3BaseShotgun.set(renderAdventOfAscension3BaseShotgun.selected());
        ConfigHolder.CLIENT.renderAdventOfAscension3BaseCannon.set(renderAdventOfAscension3BaseCannon.selected());
        ConfigHolder.CLIENT.renderAdventOfAscension3BaseThrownWeapon.set(renderAdventOfAscension3BaseThrownWeapon.selected());
        ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseBow.set(targetSoundAdventOfAscension3BaseBow.selected());
        ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseCrossbow.set(targetSoundAdventOfAscension3BaseCrossbow.selected());
        ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseStaff.set(targetSoundAdventOfAscension3BaseStaff.selected());
        ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseGun.set(targetSoundAdventOfAscension3BaseGun.selected());
        ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseBlaster.set(targetSoundAdventOfAscension3BaseBlaster.selected());
        ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseSniper.set(targetSoundAdventOfAscension3BaseSniper.selected());
        ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseShotgun.set(targetSoundAdventOfAscension3BaseShotgun.selected());
        ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseCannon.set(targetSoundAdventOfAscension3BaseCannon.selected());
        ConfigHolder.CLIENT.targetSoundAdventOfAscension3BaseThrownWeapon.set(targetSoundAdventOfAscension3BaseThrownWeapon.selected());

        ConfigHolder.CLIENT.renderArchBowsFlatBowItem.set(renderArchBowsFlatBowItem.selected());
        ConfigHolder.CLIENT.renderArchBowsLongBowItem.set(renderArchBowsLongBowItem.selected());
        ConfigHolder.CLIENT.renderArchBowsRecurveBowItem.set(renderArchBowsRecurveBowItem.selected());
        ConfigHolder.CLIENT.renderArchBowsShortBowItem.set(renderArchBowsShortBowItem.selected());
        ConfigHolder.CLIENT.renderArchBowsArbalestItem.set(renderArchBowsArbalestItem.selected());
        ConfigHolder.CLIENT.renderArchBowsHeavyCrossbowItem.set(renderArchBowsHeavyCrossbowItem.selected());
        ConfigHolder.CLIENT.renderArchBowsPistolCrossbowItem.set(renderArchBowsPistolCrossbowItem.selected());
        ConfigHolder.CLIENT.targetSoundArchBowsFlatBowItem.set(targetSoundArchBowsFlatBowItem.selected());
        ConfigHolder.CLIENT.targetSoundArchBowsLongBowItem.set(targetSoundArchBowsLongBowItem.selected());
        ConfigHolder.CLIENT.targetSoundArchBowsRecurveBowItem.set(targetSoundArchBowsRecurveBowItem.selected());
        ConfigHolder.CLIENT.targetSoundArchBowsShortBowItem.set(targetSoundArchBowsShortBowItem.selected());
        ConfigHolder.CLIENT.targetSoundArchBowsArbalestItem.set(targetSoundArchBowsArbalestItem.selected());
        ConfigHolder.CLIENT.targetSoundArchBowsHeavyCrossbowItem.set(targetSoundArchBowsHeavyCrossbowItem.selected());
        ConfigHolder.CLIENT.targetSoundArchBowsPistolCrossbowItem.set(targetSoundArchBowsPistolCrossbowItem.selected());

        TjeModConfig.initClient();
        this.onClose();
    }

    @Override
    public void onClose() {
        if (previousScreen != null) {
            if (minecraft != null) {
                minecraft.setScreen(previousScreen);
            }
        }
        super.onClose();
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseBtn) {
        for (GuiEventListener child : this.children()) {

            if (child.mouseClicked(mouseX, mouseY, mouseBtn)) {
                if (!(child instanceof ScrollArea)) {
                    this.setFocused(child);
                }
                if (mouseBtn == InputConstants.MOUSE_BUTTON_LEFT) {
                    this.setDragging(true);
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseBtn) {
        this.setDragging(false);
        return this.getChildAt(mouseX, mouseY).filter((childAt) -> childAt.mouseReleased(mouseX, mouseY, mouseBtn)).isPresent();
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int mouseBtn, double pDragX, double pDragY) {
        return this.getFocused() != null && this.isDragging() && mouseBtn == InputConstants.MOUSE_BUTTON_LEFT && this.getFocused().mouseDragged(mouseX, mouseY, mouseBtn, pDragX, pDragY);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        return this.getChildAt(pMouseX, pMouseY).filter((listener) -> listener.mouseScrolled(pMouseX, pMouseY, pDelta)).isPresent();
    }

}