package net.huhandhacker.daggermod.menu;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.RecipeType;

public class ForgeMenu extends AbstractForgeMenu{
    public ForgeMenu(final int containerId, final Inventory inventory) {
        super(ModMenuType.FORGE, RecipeType.SMELTING, RecipePropertySet.FURNACE_INPUT, RecipeBookType.FURNACE, containerId, inventory);
    }

    public ForgeMenu(final int containerId, final Inventory inventory, final Container container, final ContainerData data) {
        super(ModMenuType.FORGE, RecipeType.SMELTING, RecipePropertySet.FURNACE_INPUT, RecipeBookType.FURNACE, containerId, inventory, container, data);
    }

    //TODO Custom Recipe Book types
}
