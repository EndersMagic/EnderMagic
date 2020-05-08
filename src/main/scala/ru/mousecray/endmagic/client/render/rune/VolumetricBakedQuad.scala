package ru.mousecray.endmagic.client.render.rune

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.{EnumFacing, ResourceLocation}
import net.minecraftforge.client.model.pipeline.{BlockInfoLense, IVertexConsumer, VertexLighterFlat}
import ru.mousecray.endmagic.EM
import ru.mousecray.endmagic.client.render.rune.VolumetricBakedQuad._
import ru.mousecray.endmagic.rune.RuneIndex
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA

import scala.language.implicitConversions
import scala.util.Random

class VolumetricBakedQuad(face: EnumFacing, sides: Map[EnumFacing, BakedQuad]) extends BakedQuad(
  Array(), 0, face, atlasSpriteRune, sides.headOption.exists(_._2.shouldApplyDiffuseLighting()), sides.headOption.map(_._2.getFormat).getOrElse(DefaultVertexFormats.BLOCK)
) {


  override def pipe(consumer: IVertexConsumer): Unit = {
    consumer match {
      case consumer: VertexLighterFlat =>
        val blockInfo = BlockInfoLense.get(consumer)
        val pos = blockInfo.getBlockPos
        val side = face
        val capability = RuneIndex.getCapability(Minecraft.getMinecraft.world, pos)

        val maybeState = capability.getRuneState(pos)
        if (maybeState.isPresent)
          maybeState.get().foreachRuneQuadsData(face, { case (data, sourceSide) =>
            QuadDataCache.getQuadFor(data, sides(sourceSide)).pipe(consumer)
          })
        else
          sides.get(face).foreach(_.pipe(consumer))
      case _ =>
        sides.get(face).foreach(_.pipe(consumer))
    }
  }
}

object VolumetricBakedQuad {

  case class ElongateQuadData(x: Int, y1: Int, y2: Int) {
    def splitFirst(y: Int) = ElongateQuadData(x, y1, y - 1)

    def splitSecond(y: Int) = ElongateQuadData(x, y + 1, y2)
  }


  val atlasSpriteRune: TextureAtlasSprite = Minecraft.getMinecraft.getTextureMapBlocks
    .getAtlasSprite(new ResourceLocation(EM.ID, "blocks/rune").toString)

  val random = new Random()

  def nextColor() = new RGBA(random.nextFloat(), random.nextFloat(), random.nextFloat())


}