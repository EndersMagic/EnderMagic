package ru.mousecray.endmagic.client.render.rune

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.pipeline.{BlockInfoLense, IVertexConsumer, VertexLighterFlat}
import ru.mousecray.endmagic.EM
import ru.mousecray.endmagic.capability.chunk.RunePart
import ru.mousecray.endmagic.client.render.rune.VolumetricBakedQuad._
import ru.mousecray.endmagic.rune.RuneIndex
import ru.mousecray.endmagic.util.Vec2i
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA
import ru.mousecray.endmagic.util.render.endothermic.immutable.UnpackedQuad
import ru.mousecray.endmagic.util.render.endothermic.utils._

import scala.language.implicitConversions
import scala.util.Random

class VolumetricBakedQuad(quad: BakedQuad) extends BakedQuad(
  quad.getVertexData, quad.getTintIndex, quad.getFace, atlasSpriteRune, quad.shouldApplyDiffuseLighting(), quad.getFormat
) {


  override def pipe(consumer: IVertexConsumer): Unit = {
    consumer match {
      case consumer: VertexLighterFlat =>
        val blockInfo = BlockInfoLense.get(consumer)
        val pos = blockInfo.getBlockPos
        val side = quad.getFace
        val capability = RuneIndex.getCapability(Minecraft.getMinecraft.world, pos)

        Option(capability.getRuneState(pos)
          .getRuneAtSide(quad.getFace))
          .filter(rune => rune.parts.size > 0)
          .map[Seq[BakedQuad]](rune => {

          val richQuad = UnpackedQuad(quad)

          def toQuad(textureAtlasSprite: TextureAtlasSprite)(elongateQuadData: ElongateQuadData): BakedQuad = {

            richQuad
              .updated(atlas = textureAtlasSprite)
              .trivialSliceRect(
                elongateQuadData.x.toFloat / 16,
                elongateQuadData.y1.toFloat / 16,

                (elongateQuadData.x + 1).toFloat / 16,
                (elongateQuadData.y2 + 1).toFloat / 16
              ).toBakedQuad
          }

          def makeRuneQuad(x: Int, y: Int, entry: RunePart): Seq[BakedQuad] = {
            val center1 = richQuad
              .trivialSliceRect(
                x.toFloat / 16, y.toFloat / 16,
                (x + 1).toFloat / 16, (y + 1).toFloat / 16
              )
            val centerTop = center1
              .updated(atlas = atlasSpriteRune)
              .reconstruct(
                v1_a = 128,
                v2_a = 128,
                v3_a = 128,
                v4_a = 128
              )
            val centerBottom = center1.translate(standard_pixel * (-face.getDirectionVec.getX), standard_pixel * (-face.getDirectionVec.getY), standard_pixel * (-face.getDirectionVec.getZ))

            val borts = Seq(
              new Vec2i(x - 1, y) -> richQuad
                .trivialSliceRect(
                  (x - 1).toFloat / 16, y.toFloat / 16,
                  x.toFloat / 16, (y + 1).toFloat / 16
                ).reconstruct(
                v1_x = centerBottom.v1_x,
                v4_x = centerBottom.v4_x,

                v1_y = centerBottom.v1_y,
                v4_y = centerBottom.v4_y,

                v1_z = centerBottom.v1_z,
                v4_z = centerBottom.v4_z
              ).reverse
                .recalculateNormals
                .toBakedQuad,
              new Vec2i(x + 1, y) -> richQuad
                .trivialSliceRect(
                  (x + 1).toFloat / 16, y.toFloat / 16,
                  (x + 2).toFloat / 16, (y + 1).toFloat / 16
                ).reconstruct(
                v2_x = centerBottom.v2_x,
                v3_x = centerBottom.v3_x,

                v2_y = centerBottom.v2_y,
                v3_y = centerBottom.v3_y,

                v2_z = centerBottom.v2_z,
                v3_z = centerBottom.v3_z
              ).reverse
                .recalculateNormals
                .toBakedQuad,
              new Vec2i(x, y - 1) -> richQuad
                .trivialSliceRect(
                  x.toFloat / 16, (y - 1).toFloat / 16,
                  (x + 1).toFloat / 16, y.toFloat / 16
                ).reconstruct(
                v1_x = centerBottom.v1_x,
                v2_x = centerBottom.v2_x,

                v1_y = centerBottom.v1_y,
                v2_y = centerBottom.v2_y,

                v1_z = centerBottom.v1_z,
                v2_z = centerBottom.v2_z

              ).reverse
                .recalculateNormals
                .toBakedQuad,
              new Vec2i(x, y + 1) -> richQuad
                .trivialSliceRect(
                  x.toFloat / 16, (y + 1).toFloat / 16,
                  (x + 1).toFloat / 16, (y + 2).toFloat / 16
                ).reconstruct(
                v3_x = centerBottom.v3_x,
                v4_x = centerBottom.v4_x,

                v3_y = centerBottom.v3_y,
                v4_y = centerBottom.v4_y,

                v3_z = centerBottom.v3_z,
                v4_z = centerBottom.v4_z
              ).reverse
                .recalculateNormals
                .toBakedQuad
            ).filter(i => !rune.parts.contains(i._1)).map(_._2)


            borts :+ centerBottom.toBakedQuad
          }


          val rune_parts: Map[Vec2i, RunePart] = rune.parts

          val data =
            ((0 to 15).map(_ -> Map()).toMap ++ rune_parts.groupBy(_._1.x)).flatMap {
              case (x, parts) if parts.nonEmpty =>
                val line = ElongateQuadData(x, 0, 15)
                val runePoints = parts.map(_._1.y).toSeq.sorted
                runePoints.foldLeft(line :: Nil) { case (last :: other, p) =>
                  last.splitSecond(p) :: last.splitFirst(p) :: other
                } filter (i => i.y1 <= i.y2)
              case (x, _) =>
                Seq(ElongateQuadData(x, 0, 15))
            }.toSeq

          val back = data.map(toQuad(quad.getSprite))
          val runeQuads = rune_parts.flatMap(i => makeRuneQuad(i._1.x, i._1.y, i._2))
          val result = back ++ runeQuads
          result
        }).getOrElse(Seq(quad)).foreach(_.pipe(consumer))
      case _ =>
        quad.pipe(consumer)
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