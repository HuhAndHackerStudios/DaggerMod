package net.huhandhacker.daggermod.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.huhandhacker.daggermod.block.ModBlocks;
import net.huhandhacker.daggermod.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createTrivialCube(ModBlocks.TEMP_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModItems.SHARKTOOTH, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.IRON_HANDLE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ROPE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.BTSDUT, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.IRON_GRAPPLE_HEAD, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MALLEABLE_IRON_INGOT, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GRAPPLE_HOOK, ModelTemplates.FLAT_HANDHELD_ITEM);
    }
}
