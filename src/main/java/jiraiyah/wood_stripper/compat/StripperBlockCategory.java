/*
package jiraiyah.wood_stripper.compat;

import jiraiyah.wood_stripper.blockentity.StripperBlockEntity;
import jiraiyah.wood_stripper.registry.ModBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

import static jiraiyah.wood_stripper.Main.ModID;
import static jiraiyah.wood_stripper.Main.REFERENCE;

// Done with the help :
// https://github.com/TeamGalacticraft/Galacticraft/tree/main (MIT License)
public class StripperBlockCategory implements DisplayCategory<BasicDisplay>
{
    public static final Identifier TEXTURE =
            REFERENCE.identifier("textures/gui/stripper_gui.png");

    public static final CategoryIdentifier<StripperBlockDisplay> WOOD_STRIPPING =
            CategoryIdentifier.of(ModID, "wood_stripping");

    @Override
    public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier()
    {
        return WOOD_STRIPPING;
    }

    @Override
    public Text getTitle()
    {
        return StripperBlockEntity.TITLE;
    }

    @Override
    public Renderer getIcon()
    {
        return EntryStacks.of(ModBlocks.STRIPPER_BLOCK.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(BasicDisplay display, Rectangle bounds)
    {
        final Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 175, 82)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 80, startPoint.y + 11))
                .entries(display.getInputEntries().getFirst()));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 80, startPoint.y + 59))
                .markOutput().entries(display.getOutputEntries().getFirst()));


        return widgets;
    }

    @Override
    public int getDisplayHeight()
    {
        return 90;
    }
}*/