package lol.koblizek.techmania.blocks.multiblock;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.joml.Vector3i;

public class FillerBlockEntity extends BlockEntity {

    public static BlockEntityType<FillerBlockEntity> FILLER_BLOCK_ENTITY;

    private Vector3i masterOffset;
    private Direction direction;

    public void setMasterOffset(Vector3i masterOffset) {
        this.masterOffset = masterOffset;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public FillerBlockEntity(BlockPos pos, BlockState state) {
        super(FILLER_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putIntArray("master_offset", new int[] {masterOffset.x, masterOffset.y, masterOffset.z});
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        int[] masterOffsetArray = nbt.getIntArray("master_offset");
        masterOffset = new Vector3i(masterOffsetArray[0], masterOffsetArray[1], masterOffsetArray[2]);
        super.readNbt(nbt);
    }

    public Vector3i getMasterOffset() {
        return masterOffset;
    }
}
