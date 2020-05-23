package ru.mousecray.endmagic.client.render.tileentity;

import com.google.common.primitives.Ints;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.gameobj.tileentity.TilePhantomAvoidingBlockBase;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import java.util.List;

import static net.minecraft.block.BlockLog.LOG_AXIS;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glTranslated;

@SideOnly(Side.CLIENT)
public class TilePhantomAvoidingBlockRenderer extends TileEntitySpecialRenderer<TilePhantomAvoidingBlockBase> {
    private static BlockPos.MutableBlockPos tempPos = new BlockPos.MutableBlockPos();

    Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void render(TilePhantomAvoidingBlockBase te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        glPushMatrix();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0F, 240F);

        IBlockState blockState = te.getWorld().getBlockState(te.getPos());
        Block block = blockState.getBlock();
        if (blockState.getPropertyKeys().contains(LOG_AXIS)) {
            BlockLog.EnumAxis axis = blockState.getValue(LOG_AXIS);
            if (axis == BlockLog.EnumAxis.X) {
                glTranslated(x + 28f / 16, y, z);
                glScaled(4, 4, 4);
                GlStateManager.rotate(90, 0, 0, 1);
            } else if (axis == BlockLog.EnumAxis.Z) {
                glTranslated(x, y + 1, z - 12f / 16);
                glScaled(4, 4, 4);
                GlStateManager.rotate(90, 1, 0, 0);
            } else if (axis == BlockLog.EnumAxis.Y || axis == BlockLog.EnumAxis.NONE) {
                glTranslated(x, y - 12f / 16, z);
                glScaled(4, 4, 4);
            }
        } else {
            glTranslated(x, y - 12f / 16, z);
            glScaled(4, 4, 4);
        }
        double alpha1 = (double) (TilePhantomAvoidingBlockBase.maxAvoidTicks - (te.avoidTicks + partialTicks * te.increment)) /
                TilePhantomAvoidingBlockBase.maxAvoidTicks;
        //System.out.println(alpha1);
        renderBlockWithColor(new ItemStack(block, 1, EnderBlockTypes.EnderTreeType.PHANTOM.ordinal()), alpha1);

        glPopMatrix();
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
    }

    private void renderBlockWithColor(ItemStack stack, double alpha) {
        IBakedModel bakedmodel = mc.getRenderItem().getItemModelWithOverrides(stack, mc.world, mc.player);
        mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableRescaleNormal();
        //GlStateManager.disableAlpha();
        GlStateManager.alphaFunc(516, 0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.pushMatrix();
        bakedmodel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(bakedmodel, ItemCameraTransforms.TransformType.GROUND, false);


        GlStateManager.pushMatrix();


        {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.ITEM);

            int argb = Ints.constrainToRange((int) (alpha * 255), 0, 255) << 24 | 255 << 16 | 255 << 8 | 255;
            for (EnumFacing enumfacing : EnumFacing.values())
                renderQuads(bufferbuilder, bakedmodel.getQuads(null, enumfacing, 0L), argb, stack);

            renderQuads(bufferbuilder, bakedmodel.getQuads(null, null, 0L), argb, stack);
            tessellator.draw();
        }

        GlStateManager.popMatrix();


        GlStateManager.cullFace(GlStateManager.CullFace.BACK);
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
    }


    private void renderQuads(BufferBuilder renderer, List<BakedQuad> quads, int color, ItemStack stack) {
        for (BakedQuad bakedquad : quads)
            net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(renderer, bakedquad, color);
    }

}
