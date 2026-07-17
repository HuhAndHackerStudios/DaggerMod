package net.huhandhacker.daggermod.creativemodetab;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.huhandhacker.daggermod.DaggerMod;
import net.huhandhacker.daggermod.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {

    public static final CreativeModeTab DAGGER_MOD = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(DaggerMod.MOD_ID, "dagger_mod"),
            FabricCreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SHARKTOOTH))
                    .title(Component.translatable("creativemodetab.daggermod.dagger_mod"))
                    .displayItems((parameters, item) -> {
                        item.accept(ModItems.SHARKTOOTH);
                        item.accept(ModItems.BTSDUT);
                        item.accept(ModItems.MALLEABLEIRONINGOT);
                        item.accept(ModItems.IRONGRAPPLEHEAD);
                        item.accept(ModItems.IRON_HANDLE);
                        item.accept(ModItems.ROPE);
                        item.accept(ModItems.GRAPPLEHOOK);
                    }).build());


    public static void registerModCreativeModeTabs(){
        DaggerMod.LOGGER.info("Registering Creative Mode Tabs for" + DaggerMod.MOD_ID);
    }
}
