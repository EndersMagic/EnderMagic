package ru.mousecray.endmagic.util.render;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.VanillaLoaderLens;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.util.Optional;

public final class RenderUtils {

	private static final VertexFormat ITEM_FORMAT_WITH_LIGHTMAP = new VertexFormat(DefaultVertexFormats.ITEM).addElement(DefaultVertexFormats.TEX_2S);

    public static boolean isLightMapDisabled() {
        return FMLClientHandler.instance().hasOptifine() || !ForgeModContainer.forgeLightPipelineEnabled;
    }
    
    public static VertexFormat getFormatWithLightMap(VertexFormat format) {
        if (isLightMapDisabled()) return format;

        if (format == DefaultVertexFormats.BLOCK) {
            return DefaultVertexFormats.BLOCK;          
        } else if (format == DefaultVertexFormats.ITEM) {
            return ITEM_FORMAT_WITH_LIGHTMAP;    
        } else if (!format.hasUvOffset(1)) {
            VertexFormat result = new VertexFormat(format);
            result.addElement(DefaultVertexFormats.TEX_2S);
            return result;
        }
        return format;
    }

    public static IBakedModel loadJsonModel(ResourceLocation modelLocation) {
        try {
            return VanillaLoaderLens.get().loadModel(modelLocation)
                    .bake(___ -> Optional.empty(), DefaultVertexFormats.BLOCK, ModelLoader.defaultTextureGetter());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}