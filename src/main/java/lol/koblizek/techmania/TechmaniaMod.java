package lol.koblizek.techmania;

import lol.koblizek.techmania.blocks.ModBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TechmaniaMod implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("Techmania");
    public static final String MOD_ID = "techmania";

    @Override
    public void onInitialize() {
        ModBlocks.init();
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "multiblock"), new BlockItem(ModBlocks.MULTIBLOCK, new FabricItemSettings()));
    }
}
