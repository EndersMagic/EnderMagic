package ru.mousecray.endmagic.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class LayerEndermanPumpkin implements LayerRenderer<EntityEnderman> {
    private final RenderSnowMan snowManRenderer = (RenderSnowMan) Minecraft.getMinecraft().getRenderManager().<EntitySnowman>getEntityClassRenderObject(EntitySnowman.class);

    @Override
    public void doRenderLayer(EntityEnderman entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entitylivingbaseIn.isInvisible() /*&& entitylivingbaseIn.isPumpkinEquipped()*/) {
            //snowManRenderer.getMainModel().head.rotateAngleY = netHeadYaw * 0.017453292F;
            //snowManRenderer.getMainModel().head.rotateAngleX = headPitch * 0.017453292F;

            GlStateManager.pushMatrix();
            snowManRenderer.getMainModel().head.postRender(0.125F);
            float f = 0.625F;
            GlStateManager.color(1, 1, 1, 0.5f);
            GlStateManager.translate(0.0F, -1.55, 0.0F);
            GlStateManager.rotate(headPitch * 0.017453292F, 1.0F, 0, 0);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(netHeadYaw * 0.017453292F, 0, 1.0F, 0.0F);
            GlStateManager.scale(0.625F, -0.625F, -0.625F);
            Minecraft.getMinecraft().getItemRenderer().renderItem(entitylivingbaseIn, new ItemStack(Blocks.PUMPKIN, 1), ItemCameraTransforms.TransformType.HEAD);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}
