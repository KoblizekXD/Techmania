package lol.koblizek.techmania.blocks.types;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class NonRenderingBlock extends Block implements BlockEntityProvider {

    private final BlockEntityProvider provider;

    public NonRenderingBlock(Settings settings, BlockEntityProvider provider) {
        super(settings);
        this.provider = provider;
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
