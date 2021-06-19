package ru.mousecray.endmagic.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.util.math.Vec3d;
import ru.mousecray.endmagic.entity.EntityCustomEnderEye;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ru.mousecray.endmagic.util.render.RenderUtils.translateToZeroCoord;

public class RenderEntityCustomEnderEye extends RenderSnowball<EntityCustomEnderEye> {
    public RenderEntityCustomEnderEye(RenderManager renderManagerIn) {
        super(renderManagerIn, Items.ENDER_EYE, Minecraft.getMinecraft().getRenderItem());
    }

    @Override
    public void doRender(EntityCustomEnderEye entity, double x, double y, double z, float entityYaw, float partialTicks) {
        //debugDrawing(entity, partialTicks);

        GlStateManager.pushMatrix();
        translateToZeroCoord(partialTicks);

        double compositeTick = entity.clientOrientedTick + partialTicks;

        double function_t = compositeTick / entity.curveLen;
        Vec3d vec = entity.curve.apply(function_t);

        super.doRender(entity, vec.x, vec.y, vec.z, entityYaw, partialTicks);

        GlStateManager.popMatrix();
    }

    private void drawPolyChain(List<Vec3d> path, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);

        for (Vec3d vec3d : path)
            drawLine(vec3d, color, buffer);

        tessellator.draw();
    }

    private void drawLine(Vec3d first, Color color, BufferBuilder buffer) {
        buffer.pos(first.x, first.y, first.z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
    }

    private void debugDrawing(EntityCustomEnderEye entity, float partialTicks) {
        GlStateManager.pushMatrix();
        translateToZeroCoord(partialTicks);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.glLineWidth(2.0F);
        GlStateManager.disableTexture2D();

        drawPolyChain(entity.path, Color.green);
        drawPolyChain(IntStream.range(0, 100).mapToDouble(i -> ((double) i) / 100).mapToObj(entity.curve::apply).collect(Collectors.toList()), Color.red);

        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
}
