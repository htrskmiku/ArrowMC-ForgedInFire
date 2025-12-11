package top.arrowmc.forgedinfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import top.arrowmc.forgedinfire.ModRegister;
import top.arrowmc.forgedinfire.block.entity.DisplayStandBlockEntity;

public class DisplayStandBlock extends BaseEntityBlock {

    private static final VoxelShape BASE1 = Block.box(0.0D, 5.0D, 0.0D, 16.0D, 10.0D, 16.0D);
    private static final VoxelShape BASE2 = Block.box(2.0D, 10.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    private static final VoxelShape LEG1 = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 5.0D, 2.0D);
    private static final VoxelShape LEG2 = Block.box(0.0D, 0.0D, 14.0D, 2.0D, 5.0D, 16.0D);
    private static final VoxelShape LEG3 = Block.box(14.0D, 0.0D, 14.0D, 16.0D, 5.0D, 16.0D);
    private static final VoxelShape LEG4 = Block.box(14.0D, 0.0D, 0.0D, 16.0D, 5.0D, 2.0D);
    private static final VoxelShape AXIS_AABB = Shapes.or(BASE1, BASE2, LEG1, LEG2, LEG3, LEG4);



    public DisplayStandBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof DisplayStandBlockEntity displayStandBlockEntity) {
                displayStandBlockEntity.playerInteract(pPlayer,pLevel,pHand);
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }


    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide? null : createTickerHelper(pBlockEntityType, ModRegister.DISPLAY_STAND_BLOCK_ENTITY.get(),DisplayStandBlockEntity::tick);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DisplayStandBlockEntity(pPos,pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.getShape(pState, pLevel, pPos, pContext);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AXIS_AABB;
    }
}
