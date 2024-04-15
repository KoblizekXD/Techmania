package lol.koblizek.techmania.model;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class WavefrontModel {

    public static final Logger LOGGER = LoggerFactory.getLogger("Techmania/Model Loader");

    private final Identifier modelId;
    private List<DefinitionEntry> entries;
    private List<ModelObject> objects;
    List<Vector3f> vertices;
    List<Vector3f> normals;
    List<Vector2f> textures;

    public WavefrontModel(Identifier modelId) {
        this.modelId = modelId;
        this.reload();
    }

    public void reload() {
        Optional<Resource> optRes = MinecraftClient.getInstance().getResourceManager().getResource(modelId);
        if (optRes.isPresent()) {
            LOGGER.info("Reloading {}", modelId);
            this.objects = new ArrayList<>();
            this.normals = new ArrayList<>();
            this.textures = new ArrayList<>();
            this.vertices = new ArrayList<>();
            try (BufferedReader reader = optRes.get().getReader()) {
                entries = parseLines(reader.lines());
                ModelObject currentObject = null;
                for (DefinitionEntry entry : entries) {
                    switch (entry.def) {
                        case O -> {
                            if (currentObject != null) {
                                objects.add(currentObject);
                            }
                            currentObject = new ModelObject(entry.args[0]);
                        }
                        case V -> vertices.add(new Vector3f(Float.parseFloat(entry.args[0]),
                                Float.parseFloat(entry.args[1]),
                                Float.parseFloat(entry.args[2])));
                        case VN -> normals.add(new Vector3f(Float.parseFloat(entry.args[0]),
                                Float.parseFloat(entry.args[1]),
                                Float.parseFloat(entry.args[2])));
                        case VT -> textures.add(new Vector2f(Float.parseFloat(entry.args[0]),
                                entry.args.length == 1 ? 0f : Float.parseFloat(entry.args[1])));
                        default -> {
                            if (currentObject != null)
                                currentObject.add(entry);
                        }
                    }
                }
                if (currentObject != null) {
                    objects.add(currentObject);
                }
            } catch (IOException e) {
                LOGGER.error("Failed to load model data for " + modelId, e);
            }
        } else {
            LOGGER.error("Failed to load model data for {}(resource is missing)", modelId);
        }
    }

    private List<DefinitionEntry> parseLines(Stream<String> lines) {
        return lines.map(DefinitionEntry::from).filter(Optional::isPresent).map(Optional::get).toList();
    }

    public record DefinitionEntry(Definition def, String[] args) {
        public static Optional<DefinitionEntry> from(String line) {
            String[] args = line.split(" ");
            if (args.length == 0 || args[0].startsWith("#") || args[0].isBlank()) {
                return Optional.empty();
            }
            String[] args2 = new String[args.length - 1];
            System.arraycopy(args, 1, args2, 0, args2.length);
            return Optional.of(new DefinitionEntry(Definition.from(args[0]), args2));
        }
    }

    public List<DefinitionEntry> getEntries() {
        return entries;
    }

    public List<ModelObject> getObjects() {
        return objects;
    }

    public void render() {
        render(null);
    }

    public void render(MatrixStack stack) {
        for (ModelObject object : this.getObjects()) {
            for (Instructable instructable : object.instructables) {
                instructable.render(this, stack);
            }
        }
    }

    public static class ModelObject {

        private final String name;
        private final List<Instructable> instructables;

        public ModelObject(String name) {
            this.name = name;
            this.instructables = new ArrayList<>();
        }

        public ModelObject add(DefinitionEntry entry) {
            switch (entry.def) {
                case F -> instructables.add(Face.fromArgs(entry.args));
                case S -> instructables.add(new Culling(entry.args[0]));
                default -> LOGGER.warn("Unknown or unsupported definition: {}", entry.def);
            }
            return this;
        }

        public String getName() {
            return name;
        }

        public List<Instructable> getInstructables() {
            return instructables;
        }
    }
}
