package lol.koblizek.techmania.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

public class FabricBlockEntity extends BlockEntity implements BlockEntityRenderer<FabricBlockEntity> {

    public FabricBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public final void render(FabricBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        this.render(tickDelta, matrices, vertexConsumers, light, overlay);
    }

    public void render(float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {}
}
