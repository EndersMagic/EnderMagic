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
  override def pipe(consumer: IVertexConsumer): Unit = {
    consumer match {
      case consumer: VertexLighterFlat =>
        RuneIndex.getRuneAt(new Location(BlockInfoLense.get(consumer).getBlockPos, Minecraft.getMinecraft.world), BlockInfoLense.get(consumer).getState.getBlock)
          .flatMap(_.sides.get(quad.face))
          .map { rune: Rune =>
            val richQuad = RichRectangleBakedQuad(quad)
            richQuad.slicedArea(1f / 16)
              .erase(rune)
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
