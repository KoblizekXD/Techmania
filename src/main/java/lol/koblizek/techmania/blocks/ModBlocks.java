package lol.koblizek.techmania.blocks;

import lol.koblizek.techmania.TechmaniaMod;
import lol.koblizek.techmania.blocks.multiblock.FillerBlock;
import lol.koblizek.techmania.blocks.multiblock.FillerBlockEntity;
import lol.koblizek.techmania.blocks.multiblock.MultiBlock;
import lol.koblizek.techmania.blocks.multiblock.MultiBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;

public final class ModBlocks {

    public static final Block FILLER = new FillerBlock(FabricBlockSettings.create());
    public static final Block MULTIBLOCK = new MultiBlock(new Vec3i(3, 3, 3), FabricBlockSettings.create());

    private ModBlocks() {}

    public static void init() {
        FillerBlockEntity.FILLER_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(TechmaniaMod.MOD_ID, "filler_entity"),
                FabricBlockEntityTypeBuilder.create(FillerBlockEntity::new, ModBlocks.FILLER).build()
        );
        MultiBlockEntity.MULTIBLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(TechmaniaMod.MOD_ID, "multiblock_entity"),
                FabricBlockEntityTypeBuilder.create(MultiBlockEntity::new, ModBlocks.MULTIBLOCK).build()
        );
        Registry.register(Registries.BLOCK, new Identifier(TechmaniaMod.MOD_ID, "multiblock"), MULTIBLOCK);
        Registry.register(Registries.BLOCK, new Identifier(TechmaniaMod.MOD_ID, "filler"), FILLER);
    }

    private static <E extends BlockEntity> BlockEntityType<E> blockEntity(String name, BlockEntityType.BlockEntityFactory<E> factory) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(TechmaniaMod.MOD_ID, name),
                BlockEntityType.Builder.create(factory).build(null)
        );
    }
}
