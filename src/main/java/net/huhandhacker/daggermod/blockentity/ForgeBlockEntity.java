package net.huhandhacker.daggermod.blockentity;


import net.huhandhacker.daggermod.menu.ForgeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;


public class ForgeBlockEntity extends AbstractForgeBlockEntity {


    private static final Component DEFAULT_NAME = Component.translatable("Forge");

    public ForgeBlockEntity(final BlockPos worldPosition, final BlockState blockState) {
        super(ModBlockEntityTypes.FORGE_BLOCK_ENTITY, worldPosition, blockState, RecipeType.SMELTING);
    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    protected AbstractContainerMenu createMenu(final int containerId, final Inventory inventory) {
        return new ForgeMenu(containerId, inventory, this, this.dataAccess);
    }
}
