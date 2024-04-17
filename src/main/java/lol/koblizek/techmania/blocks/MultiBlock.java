package lol.koblizek.techmania.blocks;

import lol.koblizek.techmania.blocks.types.NonRenderingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
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
        Vec3i vec = world.getBlockEntity(pos, MultiBlockEntity.MULTIBLOCK_ENTITY).get()
                .getSlaveBox();
        for (int x = 0; x < vec.getX(); x++) {
            for (int y = 0; y < vec.getY(); y++) {
                for (int z = 0; z < vec.getZ(); z++) {
                    BlockPos p = pos.add(x, y, z);
                    if (!p.equals(pos)) {
                        ModBlocks.FILLER.getDefaultState();
                        world.setBlockState(p, ModBlocks.FILLER.getDefaultState());
                        FillerBlockEntity entity = new FillerBlockEntity(p, world.getBlockState(p));
                        entity.setMasterOffset(new Vector3i(x, y, z));
                        world.addBlockEntity(entity);
                    }
                }
            }
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        Optional<MultiBlockEntity> entOpt = world.getBlockEntity(pos,  MultiBlockEntity.MULTIBLOCK_ENTITY);
        Vec3i v = entOpt.get().getSlaveBox();
        for (int i = 0; i < v.getX(); i++) {
            for (int j = 0; j < v.getY(); j++) {
                for (int k = 0; k < v.getZ(); k++) {
                    BlockPos p = pos.add(i, j, k);
                    if (world.getBlockState(p).getBlock() == ModBlocks.FILLER);
                    world.removeBlock(p, false);
                }
            }
        }
        return super.onBreak(world, pos, state, player);
    }
}
