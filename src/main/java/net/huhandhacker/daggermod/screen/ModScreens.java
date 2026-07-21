package net.huhandhacker.daggermod.screen;

import net.fabricmc.api.ClientModInitializer;
import net.huhandhacker.daggermod.menu.ModMenuType;
import net.minecraft.client.gui.screens.MenuScreens;

public class ModScreens implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenuType.FORGE, ForgeScreen::new);
        System.out.println("FORGE SCREEN REGISTERED");
    }
}
