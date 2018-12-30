package ru.mousecray.endmagic.util;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.client.FMLClientHandler;

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
}