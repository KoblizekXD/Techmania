package lol.koblizek.techmania.blocks;

import lol.koblizek.techmania.blocks.types.NonRenderingNonEntityBlock;
import lol.koblizek.techmania.util.Pos;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3i;

public class FillerBlock extends NonRenderingNonEntityBlock implements BlockEntityProvider {
    public FillerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        FillerBlockEntity entity = world.getBlockEntity(pos, FillerBlockEntity.FILLER_BLOCK_ENTITY).get();
        Pos p = new Pos(pos);
        Vector3i off = entity.getMasterOffset();
        p = p.up(-off.y).right(entity.getDirection(), off.x).forward(entity.getDirection(), off.z);
        MultiBlockEntity master = world.getBlockEntity(p, MultiBlockEntity.MULTIBLOCK_ENTITY).get();
        master.removeAllFillers(master.getDirection());
        return super.onBreak(world, pos, state, player);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        // world.addBlockEntity(new FillerBlockEntity(p, world.getBlockState(p)));
        return null;
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        FillerBlockEntity entity = world.getBlockEntity(pos, FillerBlockEntity.FILLER_BLOCK_ENTITY).get();
        Vector3i off = entity.getMasterOffset();
        Pos p = new Pos(pos).up(-off.y).right(entity.getDirection(), off.x).forward(entity.getDirection(), off.z);
        return super.calcBlockBreakingDelta(world.getBlockState(p), player, world, p);
    }
}
