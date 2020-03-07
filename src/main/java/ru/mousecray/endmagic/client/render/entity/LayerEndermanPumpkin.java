package ru.mousecray.endmagic.client.render.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.capability.entity.EndermanHeadBlockCapabilityProvider;

public class LayerEndermanPumpkin implements LayerRenderer<EntityEnderman> {
    private final RenderSnowMan snowManRenderer = (RenderSnowMan) Minecraft.getMinecraft().getRenderManager().<EntitySnowman>getEntityClassRenderObject(EntitySnowman.class);
    private final RenderEnderman renderEnderman;

    public LayerEndermanPumpkin(RenderEnderman renderEnderman) {
        this.renderEnderman = renderEnderman;
    }

    @Override
    public void doRenderLayer(EntityEnderman entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        IBlockState block = entitylivingbaseIn.getCapability(EndermanHeadBlockCapabilityProvider.endermanHeadBlockCapability, null).block;
        if (!entitylivingbaseIn.isInvisible() && block.getBlock() != Blocks.AIR) {
            //snowManRenderer.getMainModel().head.rotateAngleY = netHeadYaw * 0.017453292F;
            //snowManRenderer.getMainModel().head.rotateAngleX = headPitch * 0.017453292F;

            GlStateManager.pushMatrix();
            renderEnderman.getMainModel().bipedHead.postRender(0.0625F);


            snowManRenderer.getMainModel().head.postRender(0.125F);
            float f = 0.625F;
            GlStateManager.scale(0.625F, -0.625F, -0.625F);
            GlStateManager.rotate(180, 0, 1, 0);
            GlStateManager.translate(0, 1.41, 0);
            Minecraft.getMinecraft().getItemRenderer().renderItem(entitylivingbaseIn, new ItemStack(block.getBlock(), 1), ItemCameraTransforms.TransformType.HEAD);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}
