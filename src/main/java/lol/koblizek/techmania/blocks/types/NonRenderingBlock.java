package lol.koblizek.techmania.blocks.types;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class NonRenderingBlock extends Block implements BlockEntityProvider {

    private final BlockEntityProvider provider;

    public NonRenderingBlock(Settings settings, BlockEntityProvider provider) {
        super(settings.nonOpaque());
        this.provider = provider;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HorizontalFacingBlock.FACING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return provider.createBlockEntity(pos, state);
    }
}
