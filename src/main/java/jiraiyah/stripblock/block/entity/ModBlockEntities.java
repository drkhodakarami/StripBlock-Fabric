package jiraiyah.stripblock.block.entity;

import jiraiyah.stripblock.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static jiraiyah.stripblock.Reference.*;

public class ModBlockEntities
{
    public static final BlockEntityType<StripperBlockEntity> STRIPPER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, identifier("stripper_be"),
                     BlockEntityType.Builder.create(StripperBlockEntity::new,
                                                            ModBlocks.STRIPPER_BLOCK).build(null));

    public static void register()
    {
        logN(">>> Registering Block Entities for : " + ModID);
    }
}