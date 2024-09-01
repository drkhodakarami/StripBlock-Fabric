package jiraiyah.stripblock.item;

import jiraiyah.stripblock.StripBlock;
import jiraiyah.stripblock.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static jiraiyah.stripblock.Reference.logN;

public class ModItemGroups
{
    /*public static final ItemGroup RUBY_Group = Registry.register(Registries.ITEM_GROUP,
            new Identifier(StripBlock.ModID, "stripperblockgroup"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.stripperblockgroup"))
                    .icon(() -> new ItemStack(ModBlocks.STRIPPER_BLOCK)).entries((displayContext, entries) -> {

                        entries.add(ModBlocks.STRIPPER_BLOCK);

                    }).build());*/

    public static void register()
    {
        logN(">>> Registering Item Groups");
    }
}