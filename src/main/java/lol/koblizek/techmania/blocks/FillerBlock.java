package lol.koblizek.techmania.blocks;

import lol.koblizek.techmania.blocks.types.NonRenderingBlock;
import lol.koblizek.techmania.blocks.types.NonRenderingNonEntityBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3i;

public class FillerBlock extends NonRenderingNonEntityBlock implements BlockEntityProvider {
    public FillerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        FillerBlockEntity entity = world.getBlockEntity(pos, FillerBlockEntity.FILLER_BLOCK_ENTITY).get();
        Vector3i off = entity.getMasterOffset();
        MultiBlockEntity master = world.getBlockEntity(pos.add(off.x, off.y, off.z), MultiBlockEntity.MULTIBLOCK_ENTITY).get();
        master.removeAllFillers();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        // world.addBlockEntity(new FillerBlockEntity(p, world.getBlockState(p)));
        return new FillerBlockEntity(pos, state);
    }
}
