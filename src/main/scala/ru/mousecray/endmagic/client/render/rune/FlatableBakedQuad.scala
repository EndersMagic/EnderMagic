package ru.mousecray.endmagic.client.render.rune

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraftforge.client.model.pipeline.{BlockInfoLense, IVertexConsumer, VertexLighterFlat}
import ru.mousecray.endmagic.client.render.model.baked.RichRectangleBakedQuad
import ru.mousecray.endmagic.runes.{Rune, RuneIndex}
import ru.mousecray.endmagic.teleport.Location
import ru.mousecray.endmagic.util.elix_x.ecomms.color.RGBA

import scala.util.Random

class FlatableBakedQuad(quad: BakedQuad) extends BakedQuad(
  quad.getVertexData, quad.getTintIndex, quad.getFace, quad.getSprite, quad.shouldApplyDiffuseLighting(), quad.getFormat
) {

  def makeRuneQuad(x: Float, y: Float, x2: Float, y2: Float, richQuad: RichRectangleBakedQuad, color: Int): RichRectangleBakedQuad = {
    val const = richQuad.const
    val nvu1 = (x, y, const)
    val nvu3 = (x2, y2, const)

    val nvu4 = (nvu3._1, nvu1._2, const)
    val nvu2 = (nvu1._1, nvu3._2, const)
    RichRectangleBakedQuad(richQuad.format,
      richQuad.prepareVertex(nvu1),
      richQuad.prepareVertex(nvu2),
      richQuad.prepareVertex(nvu3),
      richQuad.prepareVertex(nvu4),
      richQuad.tintIndexIn, richQuad.faceIn, atlasSpriteRune, richQuad.applyDiffuseLighting
    )
      .texture(atlasSpriteRune)
      .color(color)
  }

  val zero: Byte = 0

  override def pipe(consumer: IVertexConsumer): Unit = {
    consumer match {
      case consumer: VertexLighterFlat =>
        RuneIndex.getRuneAt(new Location(BlockInfoLense.get(consumer).getBlockPos, Minecraft.getMinecraft.world), BlockInfoLense.get(consumer).getState.getBlock)
          .flatMap(_.sides.get(quad.face))
          .map { rune: Rune =>
            val richQuad = RichRectangleBakedQuad(quad)
            richQuad.slicedArea(1f / 16)
              .eraseBy(r=>rune.parts.foreach(i => r(i.x)(i.y) = true))
              .grouped
              .overlay(richQuad)
              .map(_.toQuad)
          }.getOrElse(Seq(quad)).foreach(_.pipe(consumer))

      case _ => quad.pipe(consumer)
    }
  }
}

object FlatableBakedQuad {
  val random = new Random()

  def nextColor() = new RGBA(random.nextFloat(), random.nextFloat(), random.nextFloat())

}
