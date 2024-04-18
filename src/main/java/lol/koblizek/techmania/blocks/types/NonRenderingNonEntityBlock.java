package lol.koblizek.techmania.blocks.types;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;

public class NonRenderingNonEntityBlock extends Block {
    public NonRenderingNonEntityBlock(Settings settings) {
        super(settings.nonOpaque());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }
}
