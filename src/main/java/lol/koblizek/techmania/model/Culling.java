package lol.koblizek.techmania.model;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;

public class Culling implements Instructable {
    private final String arg;

    public Culling(String arg) {
        this.arg = arg;
    }

    @Override
    public void render(WavefrontModel model, MatrixStack matrixStack) {
        if (arg.equals("on") || arg.equals("true") || arg.equals("1"))
            RenderSystem.enableCull();
        else if (arg.equals("off") || arg.equals("false") || arg.equals("0"))
            RenderSystem.disableCull();
        else WavefrontModel.LOGGER.warn("Unknown culling argument: {}", arg);
    }
}
