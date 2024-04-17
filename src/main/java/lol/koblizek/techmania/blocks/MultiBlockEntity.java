package lol.koblizek.techmania.blocks;

import lol.koblizek.techmania.TechmaniaMod;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class MultiBlockEntity extends BlockEntity {

    public static BlockEntityType<MultiBlockEntity> MULTIBLOCK_ENTITY;

    private Vec3i slaveBox;

    public MultiBlockEntity(BlockPos pos, BlockState state) {
        super(MULTIBLOCK_ENTITY, pos, state);
    }

    public void setSlaves(Vec3i slaves) {
        this.slaveBox = slaves;
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

    public void removeAllFillers() {
        for (BlockPos p : BlockPos.iterate(pos, pos.add(slaveBox))) {
            world.removeBlock(p, false);
        }
        slaveBox = new Vec3i(0, 0, 0);
    }
}
