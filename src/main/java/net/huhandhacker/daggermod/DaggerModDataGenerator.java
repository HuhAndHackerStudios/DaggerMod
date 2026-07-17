package net.huhandhacker.daggermod;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.huhandhacker.daggermod.datagen.ModBlockLootTableProvider;
import net.huhandhacker.daggermod.datagen.ModBlockTagsProvider;
import net.huhandhacker.daggermod.datagen.ModModelProvider;

public class DaggerModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		var pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModBlockTagsProvider::new);
		pack.addProvider(ModBlockLootTableProvider::new);
	}
}
