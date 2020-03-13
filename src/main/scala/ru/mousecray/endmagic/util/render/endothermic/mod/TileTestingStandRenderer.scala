package ru.mousecray.endmagic.util.render.endothermic.mod

import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.{GlStateManager, OpenGlHelper, Tessellator}

class TileTestingStandRenderer extends TileEntitySpecialRenderer[TestingStand.TileTestingStand] {


  def textureManager = TestingStand.mc.getTextureManager

  override def render(te: TestingStand.TileTestingStand, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int, alpha: Float): Unit = {

    textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)
    textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false)
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F)
    GlStateManager.enableRescaleNormal()
    GlStateManager.alphaFunc(516, 0.1F)
    GlStateManager.enableBlend()
    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO)
    GlStateManager.pushMatrix()

    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0F, 240F)

    {
      GlStateManager.translate(x, y, z)
      val tessellator = Tessellator.getInstance
      val bufferbuilder = tessellator.getBuffer

      val maybeQuads = TestingStand.bechmarks.get(te.current._1)

      val maybeQuadToQuad = TestingStand.influence.get(te.current._2)

      bufferbuilder.begin(7, DefaultVertexFormats.ITEM)
      maybeQuads
        .foreach(_.foreach(q => net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(bufferbuilder, q, -1)))
      tessellator.draw()

      GlStateManager.translate(0, 1, 0)
      bufferbuilder.begin(7, DefaultVertexFormats.ITEM)
      maybeQuads.zip(maybeQuadToQuad)
        .foreach(i => i._1.map(i._2)
          .foreach(q => net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(bufferbuilder, q, -1)))
      tessellator.draw()
    }


    GlStateManager.cullFace(GlStateManager.CullFace.BACK)
    GlStateManager.popMatrix()
    GlStateManager.disableRescaleNormal()
    GlStateManager.disableBlend()
    textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)
    textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap()
  }

}
