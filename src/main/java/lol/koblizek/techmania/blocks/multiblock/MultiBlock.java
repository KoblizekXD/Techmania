package lol.koblizek.techmania.blocks.multiblock;

import lol.koblizek.techmania.blocks.ModBlocks;
import lol.koblizek.techmania.blocks.types.NonRenderingBlock;
import lol.koblizek.techmania.model.BlockRenderer;
import lol.koblizek.techmania.model.WavefrontModel;
import lol.koblizek.techmania.util.Pos;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Vector3i;

import java.util.Optional;

public class MultiBlock extends NonRenderingBlock implements BlockRenderer<MultiBlockEntity> {
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
        int shift = 0;
        if (boxSize.getX() % 2 == 1) {
            shift = vec.getX() / 2;
            p = p.left(facing, shift);
        }
        for (int i = 0; i < vec.getY(); i++, p = p.oneUp()) {
            Pos pi = p;
            for (int j = -shift; j < vec.getX() - shift; j++) {
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

    public static WavefrontModel MODEL;

    @Override
    public WavefrontModel getModel() {
        return MODEL;
    }
}
