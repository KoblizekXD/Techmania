package lol.koblizek.techmania.blocks;

import lol.koblizek.techmania.blocks.types.NonRenderingBlock;
import lol.koblizek.techmania.util.Pos;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3i;

import java.util.Optional;

public class MultiBlock extends NonRenderingBlock {
    private final Vec3i boxSize;

    public MultiBlock(Vec3i boxSize, Settings settings) {
        super(settings, MultiBlockEntity::new);
        this.boxSize = boxSize;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        MultiBlockEntity multiBlockEntity = MultiBlockEntity.MULTIBLOCK_ENTITY.instantiate(pos, state);
        multiBlockEntity.setSlaves(boxSize);
        return multiBlockEntity;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        MultiBlockEntity multiBlockEntity = world.getBlockEntity(pos, MultiBlockEntity.MULTIBLOCK_ENTITY).get();
        Vec3i vec = multiBlockEntity
                .getSlaveBox();
        Direction facing = placer.getHorizontalFacing();
        multiBlockEntity.setDirection(facing);
        Pos p = new Pos(pos);
        for (int i = 0; i < vec.getY(); i++, p = p.oneUp()) {
            Pos pi = p;
            for (int j = 0; j < vec.getX(); j++) {
                Pos pj = pi;
                for (int k = 0; k < vec.getZ(); k++) {
                    if (!pj.equals(pos)) {
                        BlockState s = ModBlocks.FILLER.getDefaultState();
                        FillerBlockEntity e = new FillerBlockEntity(pj, s);
                        e.setDirection(facing.getOpposite());
                        e.setMasterOffset(new Vector3i(j, i, k));
                        world.setBlockState(pj, s);
                        world.addBlockEntity(e);
                    }
                    pj = pj.oneForward(facing);
                }
                pi = pi.oneRight(facing);
            }
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        Optional<MultiBlockEntity> entOpt = world.getBlockEntity(pos,  MultiBlockEntity.MULTIBLOCK_ENTITY);
        entOpt.get().removeAllFillers(entOpt.get().getDirection());
        return super.onBreak(world, pos, state, player);
    }
}
