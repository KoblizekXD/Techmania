package lol.koblizek.techmania.model;

import net.minecraft.client.util.math.MatrixStack;

public interface Instructable {
    void render(WavefrontModel model, MatrixStack matrixStack);
}
