package sonnenlichts.tje.client.render.gui;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class TitleWidget implements Renderable, NarratableEntry {
    private final Font font;
    private final Component text;
    private int x;
    private int y;
    private int color;

    public TitleWidget(Font font, Component text, int x, int y, int color) {
        this.font = font;
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int x, int y, float tick) {
        graphics.drawString(font, text, this.x, this.y, color);
    }

    @Override
    public @NotNull NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput output) {

    }
}
