package jiraiyah.stripblock.block.entity;

import jiraiyah.stripblock.StripBlock;
import jiraiyah.stripblock.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities
{
    public static final BlockEntityType<StripperBlockEntity> STRIPPER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(StripBlock.ModID, "stripper_be"),
                    FabricBlockEntityTypeBuilder.create(StripperBlockEntity::new, ModBlocks.STRIPPER_BLOCK).build());

    public static void register()
    {
        StripBlock.LOGGER.info(">>> Registering Block Entities for : " + StripBlock.ModID);
    }
}