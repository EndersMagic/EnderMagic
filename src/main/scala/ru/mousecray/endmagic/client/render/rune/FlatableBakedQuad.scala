package ru.mousecray.endmagic.client.render.rune

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraftforge.client.model.pipeline.{BlockInfoLense, IVertexConsumer, VertexLighterFlat}
import net.minecraftforge.fluids.FluidRegistry
import ru.mousecray.endmagic.client.render.model.baked.RichRectangleBakedQuad
import ru.mousecray.endmagic.client.render.rune.FlatableBakedQuad._
import ru.mousecray.endmagic.runes.RunePartEntryWrapper._
import ru.mousecray.endmagic.runes.{Rune, RuneIndex, RunePartEntryWrapper}
import ru.mousecray.endmagic.teleport.Location
import ru.mousecray.endmagic.util.baked.lens.immutable.VertexGetLens
import ru.mousecray.endmagic.util.elix_x.ecomms.color.RGBA

import scala.language.implicitConversions
import scala.util.Random

class FlatableBakedQuad(quad: BakedQuad) extends BakedQuad(
  quad.getVertexData, quad.getTintIndex, quad.getFace, atlasSpriteRune, quad.shouldApplyDiffuseLighting(), quad.getFormat
) {


  override def pipe(consumer: IVertexConsumer): Unit = {
    consumer match {
      case consumer: VertexLighterFlat =>
        RuneIndex.getRuneAt(new Location(BlockInfoLense.get(consumer).getBlockPos, Minecraft.getMinecraft.world), BlockInfoLense.get(consumer).getState.getBlock)
          .sides.get(quad.getFace)
          .collect { case rune: Rune =>


            println(VertexGetLens.getPos(quad.getVertexData, quad.getFormat,0, Tuple3.apply))

            val richQuad = RichRectangleBakedQuad(quad)

            def toQuad(textureAtlasSprite: TextureAtlasSprite)(elongateQuadData: ElongateQuadData): BakedQuad = {
              richQuad
                .texture(textureAtlasSprite)
                .slicePixeled(
                  elongateQuadData.x,
                  elongateQuadData.y1,
                  elongateQuadData.x,
                  elongateQuadData.y2
                ).toQuad
            }

            def makeRuneQuad(entry: RunePartEntryWrapper): Seq[BakedQuad] = {
              Seq(
                richQuad
                  .texture(atlasSpriteRune)
                  .slicePixeled(
                    entry.x, entry.y,
                    entry.x, entry.y
                  ).toQuad
              )
            }

            val data = (for {
              x <- 0 to 15
            } yield {
              val line = ElongateQuadData(x, 0, 15)

              val runePoints = rune.parts.filter(_.x == x).map(_.y).toSeq.sorted
              runePoints.foldLeft(line :: Nil) { case (last :: other, p) =>
                last.splitSecond(p) :: last.splitFirst(p) :: other
              } filter (i => i.y1 <= i.y2)
            }).flatten

            data.map(toQuad(quad.getSprite)) ++ rune.parts.flatMap(makeRuneQuad(_))
          }.getOrElse(Seq(quad)).foreach(_.pipe(consumer))

      case _ => quad.pipe(consumer)
    }
  }


}

object FlatableBakedQuad {

  case class ElongateQuadData(x: Int, y1: Int, y2: Int) {
    def splitFirst(y: Int) = ElongateQuadData(x, y1, y - 1)

    def splitSecond(y: Int) = ElongateQuadData(x, y + 1, y2)
  }


  val atlasSpriteRune: TextureAtlasSprite = Minecraft.getMinecraft.getTextureMapBlocks
    .getAtlasSprite(FluidRegistry.WATER.getStill().toString)

  val random = new Random()

  def nextColor() = new RGBA(random.nextFloat(), random.nextFloat(), random.nextFloat())

}
