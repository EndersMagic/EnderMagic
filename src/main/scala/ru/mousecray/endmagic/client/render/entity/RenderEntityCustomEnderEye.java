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

public class RenderEntityCustomEnderEye extends RenderSnowball<EntityCustomEnderEye> {
    public RenderEntityCustomEnderEye(RenderManager renderManagerIn) {
        super(renderManagerIn, Items.ENDER_EYE, Minecraft.getMinecraft().getRenderItem());
    }

    @Override
    public void doRender(EntityCustomEnderEye entity, double x, double y, double z, float entityYaw, float partialTicks) {
        debugDrawing(entity, partialTicks);


        GlStateManager.pushMatrix();
        translateToZeroCoord(partialTicks);

        float t = entity.clientOrientedT + partialTicks;
        System.out.println(t);

        double tt = 1 - ((double) t) / entity.curveLen;
        Vec3d vec = entity.curve.apply(tt);

        super.doRender(entity, vec.x, vec.y, vec.z, entityYaw, partialTicks);

        GlStateManager.popMatrix();
        /*
        if (clientOrientedT < entity.curveLen) {
            Vec3d currentPos = entity.curveCache.get(clientOrientedT);
            Vec3d nextPos = clientOrientedT + 1 < entity.curveCache.size() ? entity.curveCache.get(clientOrientedT + 1) : currentPos;
            Vec3d partialTickAddition = nextPos.subtract(currentPos).scale(partialTicks);
            super.doRender(entity, x + partialTickAddition.x, y + partialTickAddition.y, z + partialTickAddition.z, entityYaw, partialTicks);
        }*/
    }

    private void drawPolyChain(List<Vec3d> path, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);

        for (Vec3d vec3d : path)
            drawLine(vec3d, color, buffer);


        tessellator.draw();
    }

    private void translateToZeroCoord(float partialTicks) {
        Entity player = Minecraft.getMinecraft().getRenderViewEntity();
        double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
        GlStateManager.translate(-x, -y, -z);
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
        //drawPolyChain(entity.rotatedPath, Color.blue);
        List<Vec3d> collect = IntStream.range(0, 100).mapToDouble(i -> ((double) i) / 100).mapToObj(entity.curve::apply).collect(Collectors.toList());
        drawPolyChain(collect, Color.red);

        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
}
