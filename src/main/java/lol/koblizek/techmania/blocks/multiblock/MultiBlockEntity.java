package lol.koblizek.techmania.blocks.multiblock;

import lol.koblizek.techmania.util.Pos;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

public class MultiBlockEntity extends BlockEntity {

    public static BlockEntityType<MultiBlockEntity> MULTIBLOCK_ENTITY;

    private Vec3i slaveBox;
    private Direction direction;

    public MultiBlockEntity(BlockPos pos, BlockState state) {
        super(MULTIBLOCK_ENTITY, pos, state);
    }

    public void setSlaves(Vec3i slaves) {
        this.slaveBox = slaves;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public Vec3i getSlaveBox() {
        return slaveBox;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        int[] arr = nbt.getIntArray("slave_box");
        this.slaveBox = new Vec3i(arr[0], arr[1], arr[2]);
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putIntArray("slave_box", new int[] {slaveBox.getX(), slaveBox.getY(), slaveBox.getZ()});
        super.writeNbt(nbt);
    }

    public void removeAllFillers(Direction facing) {
        Vec3i vec = getSlaveBox();
        int shift = 0;
        Pos p = new Pos(pos);
        if (vec.getX() % 2 == 1) {
            shift = vec.getX() / 2;
            p = p.left(facing, shift);
        }
        for (int i = 0; i < vec.getY(); i++, p = p.oneUp()) {
            Pos pi = p;
            for (int j = -shift; j < vec.getX() - shift; j++) {
                Pos pj = pi;
                for (int k = 0; k < vec.getZ(); k++) {
                    world.removeBlock(pj, false);
                    pj = pj.oneForward(facing);
                }
                pi = pi.oneRight(facing);
            }
        }
    }
}
