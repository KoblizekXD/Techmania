package lol.koblizek.techmania.blocks;

import lol.koblizek.techmania.TechmaniaMod;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModBlocks {

    private ModBlocks() {}

    public static void init() {

    }

    private static <E extends BlockEntity> BlockEntityType<E> blockEntity(String name, BlockEntityType.BlockEntityFactory<E> factory) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(TechmaniaMod.MOD_ID, name),
                BlockEntityType.Builder.create(factory).build(null)
        );
    }
}
