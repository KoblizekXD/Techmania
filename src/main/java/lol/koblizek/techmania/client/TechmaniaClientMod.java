package lol.koblizek.techmania.client;

import lol.koblizek.techmania.blocks.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class TechmaniaClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            ModBlocks.initRenderers();
        });
    }
}
