package ru.mousecray.endmagic.client.render.model.baked.rune

import java.util.function

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraftforge.client.model.pipeline.{BlockInfoLense, IVertexConsumer, VertexLighterFlat}
import net.minecraftforge.fluids.FluidRegistry
import ru.mousecray.endmagic.capability.chunk.{Rune, RunePart, RuneState, RuneStateCapabilityProvider}
import ru.mousecray.endmagic.client.render.model.baked.rune.VolumetricBakedQuad._
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA
import ru.mousecray.endmagic.util.render.endothermic.immutable.UnpackedQuad

import scala.collection.JavaConverters._
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
        val capability = Minecraft.getMinecraft.world.getChunkFromBlockCoords(pos).getCapability(RuneStateCapabilityProvider.runeStateCapability, null)

        capability.getRuneState(pos)
          .map[Rune]((runeState: RuneState) => runeState.getRuneAtSide(quad.getFace))
          .filter((rune: Rune) => rune.parts.size() > 0)
          .map[Seq[BakedQuad]]((rune: Rune) => {

          val richQuad = UnpackedQuad(quad)

          def toQuad(textureAtlasSprite: TextureAtlasSprite)(elongateQuadData: ElongateQuadData): BakedQuad = {

            richQuad
              .updated(atlas = textureAtlasSprite)
              .sliceRect(
                elongateQuadData.x.toFloat / 16,
                elongateQuadData.y1.toFloat / 16,

                (elongateQuadData.x + 1).toFloat / 16,
                (elongateQuadData.y2 + 1).toFloat / 16
              ).toBakedQuad
          }

          def makeRuneQuad(x: Int, y: Int, entry: RunePart): Seq[BakedQuad] = {
            Seq(
              richQuad
                .updated(atlas = atlasSpriteRune)
                .sliceRect(
                  x.toFloat / 16, y.toFloat / 16,
                  (x + 1).toFloat / 16, (y + 1).toFloat / 16
                )
                .toBakedQuad
            )
          }

          val rune_parts = rune.parts.asScala

          val data = (for {
            x <- 0 to 15
          } yield {
            val line = ElongateQuadData(x, 0, 15)
            //todo: optimise by replace filtering by multiple lines

            val runePoints = rune_parts.filter(_._1.x == x).map(_._1.y).toSeq.sorted
            runePoints.foldLeft(line :: Nil) { case (last :: other, p) =>
              last.splitSecond(p) :: last.splitFirst(p) :: other
            } filter (i => i.y1 <= i.y2)
          }).flatten

          val back = data.map(toQuad(quad.getSprite))
          val runeQuads = rune_parts.flatMap(i => makeRuneQuad(i._1.x, i._1.y, i._2))
          val result = back ++ runeQuads
          result
        }).orElse(Seq(quad)).foreach(_.pipe(consumer))
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
    .getAtlasSprite(FluidRegistry.WATER.getStill().toString)

  val random = new Random()

  def nextColor() = new RGBA(random.nextFloat(), random.nextFloat(), random.nextFloat())

  implicit def function2Java[A, B](f: A => B): function.Function[A, B] = new function.Function[A, B] {
    override def apply(t: A): B = f(t)
  }

  implicit def predicate2Java[A](f: A => Boolean): function.Predicate[A] = new function.Predicate[A] {
    override def test(t: A): Boolean = f(t)
  }

}