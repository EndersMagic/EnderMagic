package ru.mousecray.endmagic.client.render.rune

import java.util.{LinkedHashMap, Map, function}

import net.minecraft.client.renderer.block.model.BakedQuad
import ru.mousecray.endmagic.capability.chunk.client._
import ru.mousecray.endmagic.util.Java2Scala.function2Java
import ru.mousecray.endmagic.util.render.endothermic.immutable.UnpackedQuad
import ru.mousecray.endmagic.util.render.endothermic.utils._

object QuadDataCache {
  private val cache = makeCache[(QuadData, BakedQuad), BakedQuad](255 * 6)


  def makeCache[K, V](capacity: Int): Map[K, V] = new LinkedHashMap[K, V](capacity, 0.7F, true) {
    private val cacheCapacity = capacity

    override def removeEldestEntry(entry: Map.Entry[K, V]): Boolean = {
      this.size() > this.cacheCapacity
    }
  }

  val computeQuad: function.Function[(QuadData, BakedQuad), BakedQuad] = function2Java({
    case (data: QuadData, baseQuad: BakedQuad) =>
      val runeFace = baseQuad.getFace
      val richQuad = UnpackedQuad(baseQuad)
      val directionVec = runeFace.getDirectionVec
      val deepX = -standard_pixel * directionVec.getX
      val deepY = -standard_pixel * directionVec.getY
      val deepZ = -standard_pixel * directionVec.getZ
      data match {
        case BottomQuadData(x, y) =>
          val center1 = richQuad
            .trivialSliceRect(
              x.toFloat / 16, y.toFloat / 16,
              (x + 1).toFloat / 16, (y + 1).toFloat / 16
            )
          val centerBottom = center1.translate(standard_pixel * (-directionVec.getX), standard_pixel * (-directionVec.getY), standard_pixel * (-directionVec.getZ))
          centerBottom.toBakedQuad
        case DownSideQuadData(x, y) =>
          val temp = richQuad
            .trivialSliceRect(
              x.toFloat / 16, (y + 1).toFloat / 16,
              (x + 1).toFloat / 16, (y + 2).toFloat / 16
            )
          temp.reconstruct(
            v3_x = temp.v2_x + deepX,
            v4_x = temp.v1_x + deepX,

            v3_y = temp.v2_y + deepY,
            v4_y = temp.v1_y + deepY,

            v3_z = temp.v2_z + deepZ,
            v4_z = temp.v1_z + deepZ
          ).reverse
            .recalculateNormals
            .toBakedQuad
        case LeftSideQuadData(x, y) =>
          val temp = richQuad
            .trivialSliceRect(
              (x - 1).toFloat / 16, y.toFloat / 16,
              x.toFloat / 16, (y + 1).toFloat / 16
            )
          temp.reconstruct(
            v1_x = temp.v2_x + deepX,
            v4_x = temp.v3_x + deepX,

            v1_y = temp.v2_y + deepY,
            v4_y = temp.v3_y + deepY,

            v1_z = temp.v2_z + deepZ,
            v4_z = temp.v3_z + deepZ
          ).reverse
            .recalculateNormals
            .toBakedQuad
        case RightSideQuadData(x, y) =>
          val temp = richQuad
            .trivialSliceRect(
              (x + 1).toFloat / 16, y.toFloat / 16,
              (x + 2).toFloat / 16, (y + 1).toFloat / 16
            )
          temp.reconstruct(
            v2_x = temp.v1_x + deepX,
            v3_x = temp.v4_x + deepX,

            v2_y = temp.v1_y + deepY,
            v3_y = temp.v4_y + deepY,

            v2_z = temp.v1_z + deepZ,
            v3_z = temp.v4_z + deepZ
          ).reverse
            .recalculateNormals
            .toBakedQuad
        case UpSideQuadData(x, y) =>
          val temp = richQuad
            .trivialSliceRect(
              x.toFloat / 16, (y - 1).toFloat / 16,
              (x + 1).toFloat / 16, y.toFloat / 16
            )
          temp.reconstruct(
            v1_x = temp.v4_x + deepX,
            v2_x = temp.v3_x + deepX,

            v1_y = temp.v4_y + deepY,
            v2_y = temp.v3_y + deepY,

            v1_z = temp.v4_z + deepZ,
            v2_z = temp.v3_z + deepZ

          ).reverse
            .recalculateNormals
            .toBakedQuad
      }
  })

  def getQuadFor(data: QuadData, baseQuad: BakedQuad): BakedQuad =
    cache.computeIfAbsent((data, baseQuad), computeQuad)

  /*

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
        })
   */

}
