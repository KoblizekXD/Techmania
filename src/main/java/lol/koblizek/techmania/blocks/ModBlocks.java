package lol.koblizek.techmania.blocks;

import lol.koblizek.techmania.TechmaniaMod;
import lol.koblizek.techmania.blocks.multiblock.FillerBlock;
import lol.koblizek.techmania.blocks.multiblock.FillerBlockEntity;
import lol.koblizek.techmania.blocks.multiblock.MultiBlock;
import lol.koblizek.techmania.blocks.multiblock.MultiBlockEntity;
import lol.koblizek.techmania.model.BlockRenderer;
import lol.koblizek.techmania.model.WavefrontModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;

public final class ModBlocks {

    public static final Block FILLER = new FillerBlock(FabricBlockSettings.create());
    public static final MultiBlock MULTIBLOCK = new MultiBlock(new Vec3i(3, 3, 3), FabricBlockSettings.create());

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

    @Environment(EnvType.CLIENT)
    public static void initRenderers() {
        MultiBlock.MODEL = new WavefrontModel(new Identifier("techmania", "models/block/gadget.obj"),
                new Identifier("techmania", "textures/block/gadget.png"));
        newRenderer(MultiBlockEntity.MULTIBLOCK_ENTITY, MULTIBLOCK);
    }

    private static <T extends BlockEntity> void newRenderer(BlockEntityType<T> t, BlockRenderer<T> renderer) {
        BlockEntityRendererFactories.register(t, c -> renderer);
    }

    private static <E extends BlockEntity> BlockEntityType<E> blockEntity(String name, BlockEntityType.BlockEntityFactory<E> factory) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(TechmaniaMod.MOD_ID, name),
                BlockEntityType.Builder.create(factory).build(null)
        );
    }
}
