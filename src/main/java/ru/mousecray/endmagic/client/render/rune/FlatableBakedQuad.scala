package ru.mousecray.endmagic.client.render.rune

import code.elix_x.excomms.color.RGBA
import code.elix_x.excore.utils.client.render.model.UnpackedBakedQuad
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraftforge.client.model.pipeline.IVertexConsumer

class FlatableBakedQuad(quad: BakedQuad) extends BakedQuad(
  quad.getVertexData, quad.getTintIndex, quad.getFace, quad.getSprite, quad.shouldApplyDiffuseLighting(), quad.getFormat
) {
  //if (quad.getFace == EnumFacing.UP)
  //println(quads.size)
  //def this(quad: BakedQuad) = this(new ::(quad, Nil))

  /*
  def colorize(quad: BakedQuad, cyan: Color): UnpackedBakedQuad = {
    val builder = new UnpackedBakedQuad.Builder(RenderUtils.getFormatWithLightMap(quad.getFormat))
    val trans = new VertexLighterFlat(Minecraft.getMinecraft.getBlockColors) {
      override protected def updateLightmap(normal: Array[Float], lightmap: Array[Float], x: Float, y: Float, z: Float): Unit = {
        lightmap(0) = 0.007F
        lightmap(1) = 0.007F
      }

      override def updateColor(normal: Array[Float], color: Array[Float], x: Float, y: Float, z: Float, tint: Float, multiplier: Int): Unit = {
        println(color)
        color(0) = cyan.getRed / 255f
        color(1) = cyan.getGreen / 255f
        color(2) = cyan.getBlue / 255f
        println(color)
      }

      override def setQuadTint(tint: Int): Unit = {
      }
    }

    trans.setParent(builder)

    quad.pipe(trans)

    builder.setQuadTint(quad.getTintIndex)
    builder.setQuadOrientation(quad.getFace)
    builder.setTexture(quad.getSprite)
    builder.setApplyDiffuseLighting(false)

    builder.build
  }
  */

  override def pipe(consumer: IVertexConsumer): Unit = {
    quad.pipe(consumer)
    //BakedModelFullbright.transformQuad(quad, 0.1F).pipe(consumer)

    val quad1 = UnpackedBakedQuad.unpack(quad)

    import scala.collection.JavaConversions._
    for (v <- quad1.getVertices.getVertices)
      v.setColor(new RGBA(0f, 1f, 1f))

    quad1.pack(DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL).pipe(consumer)


  }

}
