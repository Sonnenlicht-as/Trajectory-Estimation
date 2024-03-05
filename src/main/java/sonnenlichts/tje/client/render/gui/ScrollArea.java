package sonnenlichts.tje.client.render.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class ScrollArea extends ScrollWidget {
    public final List<Renderable> renderables = Lists.newArrayList();
    private final List<GuiEventListener> children = Lists.newArrayList();
    private final Screen screenIn;
    @Nullable
    private GuiEventListener focused;
    private boolean isDragging;



    public ScrollArea(Screen screenIn, int pX, int pY, int pWidth, int pHeight) {
        super(pX, pY, pWidth, pHeight);
        this.screenIn = screenIn;
    }

    public <T extends GuiEventListener & Renderable & NarratableEntry> void addRenderableWidget(T child) {
        this.renderables.add(child);
        this.children.add(child);
    }

    public <T extends Renderable> void addRenderableWidgetOnly(T child) {
        this.renderables.add(child);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (super.mouseClicked(pMouseX, pMouseY, pButton)) {
            screenIn.setFocused(this);
            return true;
        } else if (this.inContentArea(pMouseX, pMouseY)) {
            screenIn.setFocused(this);
            for(GuiEventListener childIn : this.children) {
                if (childIn.mouseClicked(pMouseX, pMouseY+this.scrollAmount, pButton)) {
                    screenIn.setFocused(childIn);
                    if (pButton == InputConstants.MOUSE_BUTTON_LEFT) {
                        screenIn.setDragging(true);
                    }
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseBtn) {
        return this.getChildAt(mouseX, mouseY).filter((childAt) -> childAt.mouseReleased(mouseX, mouseY+this.scrollAmount, mouseBtn)).isPresent();
    }

    public Optional<GuiEventListener> getChildAt(double mouseX, double mouseY) {
        for(GuiEventListener child : this.children) {
            if (child.isMouseOver(mouseX, mouseY+this.scrollAmount)) {
                return Optional.of(child);
            }
        }
        return Optional.empty();
    }


    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY)) {
            return true;
        } else if (this.inContentArea(pMouseX, pMouseY)) {
            // Test
            return screenIn.getFocused() != null && screenIn.isDragging() && pButton == InputConstants.MOUSE_BUTTON_LEFT && screenIn.getFocused().mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
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
            return this.getChildAt(pMouseX, pMouseY).filter((listener) -> listener.mouseScrolled(pMouseX, pMouseY+this.scrollAmount, pDelta)).isPresent();
        }
    }


    @Override
    public void setFocused(boolean f) {
        this.isFocused = f;
    }

    @Override
    public boolean isFocused() {
        return isFocused;
    }

    @Override
    public int getInnerHeight() {
        return this.renderables.size() * (20) + 10;
    }

    @Override
    protected boolean scrollbarVisible() {
        return true;
    }

    @Override
    protected double scrollRate() {
        return 20;
    }


    @Override
    protected void renderContents(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        for (Renderable renderable : this.renderables) {
            renderable.render(graphics, pMouseX, pMouseY, pPartialTick);
        }
    }


    @Override
    public @NotNull NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {
    }
}
