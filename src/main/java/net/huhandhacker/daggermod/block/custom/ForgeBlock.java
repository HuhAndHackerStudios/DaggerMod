package net.huhandhacker.daggermod.block.custom;

import com.mojang.serialization.MapCodec;
import net.huhandhacker.daggermod.blockentity.ForgeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jspecify.annotations.Nullable;


public class ForgeBlock extends BaseEntityBlock {

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE_NORTH = Shapes.or(
            // Bottom plate
            Block.box(0, 3, 0, 16, 5, 16),

            // Main side walls
            Block.box(0, 5, 0, 16, 8, 1),
            Block.box(0, 5, 15, 16, 8, 16),
            Block.box(0, 5, 1, 1, 8, 16),
            Block.box(15, 5, 1, 16, 8, 16),

            // Upper platform
            Block.box(0, 8, 0, 16, 10, 16),

            // Middle forge block
            Block.box(4, 10, 4, 12, 12, 12),

            // Chimney / raised section
            Block.box(12, 10, 4, 15, 13, 12),

            // Small top supports
            Block.box(6, 12, 6, 10, 14, 10),

            // Feet
            Block.box(0, 0, 0, 2, 3, 2),
            Block.box(14, 0, 0, 16, 3, 2),
            Block.box(14, 0, 14, 16, 3, 16),
            Block.box(0, 0, 14, 2, 3, 16)
    );

    public ForgeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
        );
    }



    @Override
    protected InteractionResult useWithoutItem(BlockState state,
                                               Level level,
                                               BlockPos pos,
                                               Player player,
                                               BlockHitResult hit) {

        if (!(level.getBlockEntity(pos) instanceof ForgeBlockEntity forgeBlockEntity)) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            player.openMenu(forgeBlockEntity);
        }

        if (level.isClientSide()) {
            player.sendOverlayMessage(Component.literal("Opening The Forge"));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(ForgeBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new ForgeBlockEntity(worldPosition, blockState);
    }

    private static final VoxelShape NORTH = rotateShape(Direction.SOUTH, SHAPE_NORTH);
    private static final VoxelShape SOUTH = rotateShape(Direction.NORTH, SHAPE_NORTH);
    private static final VoxelShape EAST = rotateShape(Direction.WEST, SHAPE_NORTH);
    private static final VoxelShape WEST = rotateShape(Direction.EAST, SHAPE_NORTH);

    private static VoxelShape rotateShape(Direction direction, VoxelShape shape) {
        VoxelShape[] buffer = {shape, Shapes.empty()};

        int rotations = switch (direction) {
            case SOUTH -> 2;
            case WEST -> 3;
            case EAST -> 1;
            default -> 0;
        };

        for (int i = 0; i < rotations; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                buffer[1] = Shapes.or(
                        buffer[1],
                        Shapes.box(
                                1 - maxZ,
                                minY,
                                minX,
                                1 - minZ,
                                maxY,
                                maxX
                        )
                );
            });

            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {

        return switch (state.getValue(FACING)) {
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
            default -> NORTH;
        };
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(
                        FACING,
                        context.getHorizontalDirection().getOpposite()
                );
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        System.out.println(state.getValue(FACING));
    }
}
