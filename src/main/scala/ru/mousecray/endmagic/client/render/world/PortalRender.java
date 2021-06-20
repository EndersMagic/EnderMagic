package ru.mousecray.endmagic.client.render.world;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityEndPortalRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.capability.chunk.portal.PortalCapabilityProvider;
import ru.mousecray.endmagic.util.render.RenderUtils;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = EM.ID, value = Side.CLIENT)
public class PortalRender {

    static Field chunkMapping;
    static TileEntityEndPortalRenderer vanilaRender;
    static TileEntityEndPortal teEndPortal;

    static {
        try {
            chunkMapping = ChunkProviderClient.class.getDeclaredField("chunkMapping");
            chunkMapping.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        vanilaRender = new TileEntityEndPortalRenderer();
        vanilaRender.setRendererDispatcher(TileEntityRendererDispatcher.instance);

        teEndPortal = new TileEntityEndPortal() {
            @Override
            public boolean shouldRenderFace(EnumFacing p_184313_1_) {
                return true;
            }
        };
    }

    @SubscribeEvent
    public static void onWorldRender(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        GlStateManager.pushMatrix();
        RenderUtils.translateToZeroCoord(mc.getRenderPartialTicks());

        try {
            ((Long2ObjectMap<Chunk>) chunkMapping.get(mc.world.getChunkProvider())).values().iterator().forEachRemaining(chunk -> {
                PortalCapabilityProvider.getPortalCapability(chunk).masterPosToHeight.forEach((pos, height) -> {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(pos.getX(), pos.getY() + 1, pos.getZ());
                    GlStateManager.scale(1, height, 1);
                    vanilaRender.render(teEndPortal, 0, 0, 0, mc.getRenderPartialTicks(), 0, 1);
                    GlStateManager.popMatrix();
                });
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        GlStateManager.popMatrix();
    }
}
