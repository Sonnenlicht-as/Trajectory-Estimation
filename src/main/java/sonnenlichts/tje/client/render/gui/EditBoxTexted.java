package sonnenlichts.tje.client.render.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class EditBoxTexted extends EditBox {
    private final Component text;
    private final Color textClr;
    private final int pad;

    public EditBoxTexted(Font font, int x, int y, int width, int height, Component empty, Component text, Color textClr, int pad) {
        super(font, x, y, width, height, empty);
        this.text = text;
        this.textClr = textClr;
        this.pad = pad;
        this.setX(this.getX() + pad);
    }

    public void renderWidget(@NotNull GuiGraphics graphics, int x, int y, float tick) {
        int xo = this.getX();
        int yo = this.getY();
        this.setY(yo + 2);
        this.setX(xo - pad);
        if (this.isVisible()) {
            TitleWidget titleWidget = new TitleWidget(Minecraft.getInstance().font, this.text, this.getX(), this.getY(), this.textClr.getRGB());
            titleWidget.render(graphics, x, y, tick);
        }
        this.setX(xo);
        this.setY(yo);
        super.renderWidget(graphics, x, y, tick);
    }
}