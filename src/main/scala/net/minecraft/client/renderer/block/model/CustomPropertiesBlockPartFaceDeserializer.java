package net.minecraft.client.renderer.block.model;

import com.google.common.collect.ImmutableSet;
import com.google.gson.*;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public class CustomPropertiesBlockPartFaceDeserializer extends BlockPartFace.Deserializer {
    public static Gson create() {
        return (new GsonBuilder())
                .registerTypeAdapter(ModelBlock.class, new ModelBlock.Deserializer())
                .registerTypeAdapter(BlockPart.class, new BlockPart.Deserializer())
                .registerTypeAdapter(BlockPartFace.class, new CustomPropertiesBlockPartFaceDeserializer())
                .registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer())
                .registerTypeAdapter(ItemTransformVec3f.class, new ItemTransformVec3f.Deserializer())
                .registerTypeAdapter(ItemCameraTransforms.class, new ItemCameraTransforms.Deserializer())
                .registerTypeAdapter(ItemOverride.class, new ItemOverride.Deserializer()).create();
    }

    @Override
    public BlockPartFace deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
        BlockPartFace vanilla = super.deserialize(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
        Map<String, Object> customValues =
                p_deserialize_1_.getAsJsonObject().entrySet().stream()
                        .filter(this::isNotVanillaProperty)
                        .collect(toMap(Map.Entry::getKey, e -> refineValue(e.getValue())));
        return new CustomBlockPartFace(vanilla.cullFace, vanilla.tintIndex, vanilla.texture, vanilla.blockFaceUV, customValues);
    }

    private Object refineValue(JsonElement json) {
        if (json.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
            if (jsonPrimitive.isString())
                return jsonPrimitive.getAsString();

            else if (jsonPrimitive.isNumber())
                return jsonPrimitive.getAsNumber();

            else if (jsonPrimitive.isBoolean())
                return jsonPrimitive.getAsBoolean();

        } else if (json.isJsonArray()) {
            JsonArray jsonArray = json.getAsJsonArray();
            Object[] r = new Object[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                r[i] = refineValue(jsonArray.get(i));
            }
            return r;

        } else if (json.isJsonObject())
            return json.getAsJsonObject().entrySet().stream().collect(toMap(Map.Entry::getKey, e -> refineValue(e.getValue())));

        throw new IllegalArgumentException("Unsupported json value: " + json);
    }

    private Set<String> vanillaPropertyNames = ImmutableSet.of("cullface", "tintindex", "texture", "uv", "rotation");

    private boolean isNotVanillaProperty(Map.Entry<String, JsonElement> e) {
        return !vanillaPropertyNames.contains(e.getKey());
    }

    public static class CustomBlockPartFace extends BlockPartFace {

        public final Map<String, Object> customValues;

        public CustomBlockPartFace(@Nullable EnumFacing cullFaceIn, int tintIndexIn, String textureIn, BlockFaceUV blockFaceUVIn, Map<String, Object> customValues) {
            super(cullFaceIn, tintIndexIn, textureIn, blockFaceUVIn);
            this.customValues = customValues;
        }
    }
}
