package jiraiyah.wood_stripper.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static jiraiyah.wood_stripper.Main.REFERENCE;

public class StripperBlockScreenRenderer extends HandledScreen<StripperBlockScreenHandler>
{
    private static final Identifier TEXTURE = REFERENCE.identifier("textures/gui/stripper_gui.png");

    public StripperBlockScreenRenderer(StripperBlockScreenHandler handler, PlayerInventory inventory, Text title)
    {
        super(handler, inventory, title);
    }

    @Override
    protected void init()
    {
        super.init();
        this.backgroundWidth = 176;
        this.backgroundHeight = 133;
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY)
    {
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE,
                            this.x, this.y, 0 , 0,
                            backgroundWidth, backgroundHeight,
                            256, 256);

        renderProgressArrow(context, this.x, this.y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y)
    {
        if(handler.isCrafting())
        {
            // 26 ==> This is the width in pixels of your arrow
            context.drawTexture(RenderLayer::getGuiTextured, TEXTURE,
                                x + 85, y + 30, 176, 0,
                                handler.getScaledProgress(26), 8,
                                256, 256);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}