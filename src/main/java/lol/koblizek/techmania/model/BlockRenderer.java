package lol.koblizek.techmania.model;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public interface BlockRenderer<T extends BlockEntity> extends BlockEntityRenderer<T> {
    default MatrixStack pre(MatrixStack matrices) {
        return matrices;
    }

    WavefrontModel getModel();

    @Override
    default void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        getModel().render(pre(matrices));
    }
}
