package net.huhandhacker.daggermod;

import net.fabricmc.api.ModInitializer;
import net.huhandhacker.daggermod.blockentity.ModBlockEntityTypes;
import net.huhandhacker.daggermod.block.ModBlocks;
import net.huhandhacker.daggermod.creativemodetab.ModCreativeModeTabs;
import net.huhandhacker.daggermod.item.ModItems;
import net.huhandhacker.daggermod.stat.ModStats;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaggerMod implements ModInitializer {
	public static final String MOD_ID = "daggermod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		LOGGER.info("Your World Has Loaded Properly and there are no issues " +
				"that will impede the function of the game at this time");

		ModCreativeModeTabs.registerModCreativeModeTabs();
		ModItems.registerItems();
		ModBlocks.registerModBlocks();
		ModStats.registerStats();
		ModBlockEntityTypes.initialize();

	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

}
