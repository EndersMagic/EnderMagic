package ru.mousecray.endmagic.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.entity.EntityDungeonSlime;

import javax.annotation.Nullable;

import static org.lwjgl.opengl.GL11.*;

public class RenderDungeonSlime extends Render<EntityDungeonSlime> {
    protected RenderDungeonSlime(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityDungeonSlime entity, double x, double y, double z, float entityYaw, float partialTicks) {
        glPushMatrix();
        {
            glTranslated(x, y - 12f / 16, z);
            renderCube(entity.getW(), entity.getH(), entity.getD());
        }
        glPopMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    private void renderCube(double w, double h, double d) {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0F, 240F);
        glScaled(4, 4, 4);
        Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(Blocks.SLIME_BLOCK), ItemCameraTransforms.TransformType.GROUND);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityDungeonSlime entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
