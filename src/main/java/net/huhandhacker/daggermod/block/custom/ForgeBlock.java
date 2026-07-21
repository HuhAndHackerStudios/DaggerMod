package net.huhandhacker.daggermod.block.custom;

import com.mojang.serialization.MapCodec;
import net.huhandhacker.daggermod.blockentity.ForgeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jspecify.annotations.Nullable;


public class ForgeBlock extends BaseEntityBlock {

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final VoxelShape SHAPE_NORTH = Shapes.or(
            Block.box(0, 0, 0, 8, 10, 16),

            // Left rim
            Block.box(0, 10, 0, 1, 12, 16),

            // Right rim
            Block.box(7, 10, 0, 8, 12, 16),

            // Front rim
            Block.box(1, 10, 0, 7, 12, 1),

            // Back rim
            Block.box(1, 10, 15, 7, 12, 16),

            // Front top lip
            Block.box(1, 10, 1, 7, 11, 4),

            // Back top lip
            Block.box(1, 10, 12, 7, 11, 15),

            // Right attachment base
            Block.box(10, 9, 5, 14, 17, 11),

            // Right arm
            Block.box(12, 5, 7, 14, 9, 9),

            // Horizontal connector
            Block.box(8, 5, 7, 12, 7, 9),

            // End cap
            Block.box(14, 9, 7, 16, 10, 9),

            // Top cap
            Block.box(14, 16, 7, 16, 17, 9)
    );

    public ForgeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            double x = pos.getX() + 0.5;
            double y = pos.getY();
            double z = pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1) {
                level.playLocalSound(x, y, z, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = state.getValue(FACING);
            Direction.Axis axis = direction.getAxis();
            double r = 0.52;
            double ss = random.nextDouble() * 0.6 - 0.3;
            double dx = axis == Direction.Axis.X ? direction.getStepX() * 0.52 : ss;
            double dy = random.nextDouble() * 6.0 / 16.0;
            double dz = axis == Direction.Axis.Z ? direction.getStepZ() * 0.52 : ss;
            level.addParticle(ParticleTypes.SMOKE, x + dx, y + dy, z + dz, 0.0, 0.0, 0.0);
            level.addParticle(ParticleTypes.FLAME, x + dx, y + dy, z + dz, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {

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

    private static final VoxelShape NORTH = rotateShape(Direction.EAST, SHAPE_NORTH);
    private static final VoxelShape SOUTH = rotateShape(Direction.WEST, SHAPE_NORTH);
    private static final VoxelShape EAST = rotateShape(Direction.SOUTH, SHAPE_NORTH);
    private static final VoxelShape WEST = rotateShape(Direction.NORTH, SHAPE_NORTH);

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
        builder.add(LIT);
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
