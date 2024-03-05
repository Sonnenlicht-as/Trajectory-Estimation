package sonnenlichts.tje.client.render.gui;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class ScrollWidget implements Renderable, GuiEventListener, NarratableEntry {

    protected int width;
    protected int height;

    protected int x;
    protected int y;

    protected boolean isFocused;
    protected boolean isHovered;
    protected boolean active = true;
    protected boolean visible = true;

    private static final int INNER_PADDING = 4;
    private static final int SCROLL_BAR_WIDTH = 8;

    private static final int BORDER_COLOR_FOCUSED = -1;
    private static final int BORDER_COLOR = -6250336;
    private final Color backgroundColor = new Color(0x59000000, true);

    private final Color scrollBarColor = new Color(192, 192, 192, 255);
    private final Color scrollBarBorderColor = new Color(128, 128, 128, 255);


    protected double scrollAmount;
    private boolean scrolling;


    public ScrollWidget(int pX, int pY, int pWidth, int pHeight) {
        this.x = pX;
        this.y = pY;
        this.width = pWidth;
        this.height = pHeight;
    }


    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (!this.visible) {
            return false;
        } else {
            boolean inContentArea = this.inContentArea(pMouseX, pMouseY);
            boolean isInWidget = this.scrollbarVisible() &&
                    pMouseX >= (double) (this.x + this.width) && pMouseX <= (double) (this.x + this.width + SCROLL_BAR_WIDTH) &&
                    pMouseY >= (double) this.y && pMouseY < (double) (this.y + this.height);
            this.isFocused = inContentArea || isInWidget;
            if (isInWidget && pButton == InputConstants.MOUSE_BUTTON_LEFT) {
                this.scrolling = true;
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (pButton == InputConstants.MOUSE_BUTTON_LEFT) {
            this.scrolling = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (this.visible && this.isFocused && this.scrolling) {
            if (pMouseY < (double) this.y) {
                this.setScrollAmount(0.0D);
            } else if (pMouseY > (double) (this.y + this.height)) {
                this.setScrollAmount(this.getMaxScrollAmount());
            } else {
                int scrollBarHeight = this.getScrollBarHeight();
                double scrollProgress = Math.max(1, this.getMaxScrollAmount() / (this.height - scrollBarHeight));
                this.setScrollAmount(this.scrollAmount + pDragY * scrollProgress);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (!this.visible) {
            return false;
        } else {
            this.setScrollAmount(this.scrollAmount - pDelta * this.scrollRate());
            return true;
        }
    }

    @Override
    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return this.active && this.visible && pMouseX >= (double) this.x && pMouseY >= (double) this.y && pMouseX < (double) (this.x + this.width) && pMouseY < (double) (this.y + this.height);
    }


    @Override
    public void render(@NotNull GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.visible) {
            this.isHovered = pMouseX >= this.x && pMouseY >= this.y && pMouseX < this.x + this.width && pMouseY < this.y + this.height;
            graphics.fill(this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1, backgroundColor.getRGB());
            enableScissor(this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1);
            graphics.pose().pushPose();
            graphics.pose().translate(0.0D, -this.scrollAmount, 0.0D);
            this.renderContents(graphics, pMouseX, pMouseY, pPartialTick);
            graphics.pose().popPose();
            disableScissor();
            this.renderDecorations(graphics.pose());
        }
    }


    protected abstract void renderContents(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick);

    protected void renderDecorations(PoseStack pPoseStack) {
        if (this.scrollbarVisible()) {
            this.renderScrollBar();
        }
    }

    private void renderScrollBar() {
        int barHeight = this.getScrollBarHeight();
        int xStart = this.x + this.width;
        int xEnd = this.x + this.width + SCROLL_BAR_WIDTH;
        int yStart = Math.max(this.y, (int) this.scrollAmount * (this.height - barHeight) / this.getMaxScrollAmount() + this.y);
        int yEnd = yStart + barHeight;
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.vertex(xStart, yEnd, 0.0D).color(scrollBarBorderColor.getRed(), scrollBarBorderColor.getGreen(), scrollBarBorderColor.getBlue(), scrollBarBorderColor.getAlpha()).endVertex();
        bufferbuilder.vertex(xEnd, yEnd, 0.0D).color(scrollBarBorderColor.getRed(), scrollBarBorderColor.getGreen(), scrollBarBorderColor.getBlue(), scrollBarBorderColor.getAlpha()).endVertex();
        bufferbuilder.vertex(xEnd, yStart, 0.0D).color(scrollBarBorderColor.getRed(), scrollBarBorderColor.getGreen(), scrollBarBorderColor.getBlue(), scrollBarBorderColor.getAlpha()).endVertex();
        bufferbuilder.vertex(xStart, yStart, 0.0D).color(scrollBarBorderColor.getRed(), scrollBarBorderColor.getGreen(), scrollBarBorderColor.getBlue(), scrollBarBorderColor.getAlpha()).endVertex();
        bufferbuilder.vertex(xStart, yEnd - 1, 0.0D).color(scrollBarColor.getRed(), scrollBarColor.getGreen(), scrollBarColor.getBlue(), scrollBarColor.getAlpha()).endVertex();
        bufferbuilder.vertex(xEnd - 1, yEnd - 1, 0.0D).color(scrollBarColor.getRed(), scrollBarColor.getGreen(), scrollBarColor.getBlue(), scrollBarColor.getAlpha()).endVertex();
        bufferbuilder.vertex(xEnd - 1, yStart, 0.0D).color(scrollBarColor.getRed(), scrollBarColor.getGreen(), scrollBarColor.getBlue(), scrollBarColor.getAlpha()).endVertex();
        bufferbuilder.vertex(xStart, yStart, 0.0D).color(scrollBarColor.getRed(), scrollBarColor.getGreen(), scrollBarColor.getBlue(), scrollBarColor.getAlpha()).endVertex();
        tesselator.end();
    }

    private void renderBorder(GuiGraphics graphics) {
        int borderClr = this.isFocused ? BORDER_COLOR_FOCUSED : BORDER_COLOR;
        graphics.fill(this.x, this.y, this.x + this.width, this.y + this.height, borderClr);
    }




    private static void enableScissor(int xStart, int yStart, int xEnd, int yEnd) {
        Window window = Minecraft.getInstance().getWindow();
        int i = window.getHeight();
        double scaleRatio = window.getGuiScale();
        double xLeft = (double)xStart * scaleRatio;
        double yTop = (double)i - (double)yEnd * scaleRatio;
        double width = (double)(xEnd - xStart) * scaleRatio;
        double height = (double)(yEnd - yStart) * scaleRatio;
        RenderSystem.enableScissor((int)xLeft, (int)yTop, Math.max(0, (int)width), Math.max(0, (int)height));
    }


    private static void disableScissor() {
        RenderSystem.disableScissor();
    }




    protected double scrollAmount() {
        return this.scrollAmount;
    }

    protected void setScrollAmount(double pScrollAmount) {
        this.scrollAmount = Mth.clamp(pScrollAmount, 0.0D, this.getMaxScrollAmount());
    }

    protected int getMaxScrollAmount() {
        return Math.max(0, this.getContentHeight() - (this.height - INNER_PADDING));
    }

    protected abstract boolean scrollbarVisible();

    protected abstract double scrollRate();

    private int getScrollBarHeight() {
        return Mth.clamp((int) ((float) (this.height * this.height) / (float) this.getContentHeight()), 1, this.height);
    }

    protected int innerPadding() {
        return INNER_PADDING;
    }

    protected int totalInnerPadding() {
        return this.innerPadding() * 2;
    }

    protected boolean inContentAreaByTopBottom(int pTop, int pBottom) {
        return (double) pBottom - this.scrollAmount >= (double) this.y && (double) pTop - this.scrollAmount <= (double) (this.y + this.height);
    }

    protected boolean inContentArea(double mouseX, double mouseY) {
        return mouseX >= (double) this.x && mouseX < (double) (this.x + this.width) && mouseY >= (double) this.y && mouseY < (double) (this.y + this.height);
    }

    protected abstract int getInnerHeight();

    private int getContentHeight() {
        return this.getInnerHeight() + INNER_PADDING;
    }

    @Override
    public @NotNull NarrationPriority narrationPriority() {
        if (this.isFocused) {
            return NarrationPriority.FOCUSED;
        } else {
            return this.isHovered ? NarrationPriority.HOVERED : NarrationPriority.NONE;
        }
    }

    @Override
    public abstract void updateNarration(@NotNull NarrationElementOutput pNarrationElementOutput);


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
