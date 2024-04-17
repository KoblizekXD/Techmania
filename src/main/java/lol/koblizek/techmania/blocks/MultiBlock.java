package lol.koblizek.techmania.blocks;

import lol.koblizek.techmania.blocks.types.NonRenderingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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
                        world.addBlockEntity(new FillerBlockEntity(p, world.getBlockState(p)));
                    }
                }
            }
        }
    }
}
