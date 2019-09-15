package ru.mousecray.endmagic.client.render.rune

import java.util

import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.{BakedQuad, IBakedModel}
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.Vec3i
import ru.mousecray.endmagic.client.render.model.baked.BakedModelDelegate
import ru.mousecray.endmagic.runes.Utils._
import ru.mousecray.endmagic.util.baked.`lazy`.LazyUnpackedQuad

import scala.collection.mutable

class RunModelWrapper(baseModel: IBakedModel) extends BakedModelDelegate(baseModel) {

  import collection.JavaConverters._

  scala.collection.convert.Wrappers

  def vectMask(vector3f: (Float, Float, Float), getDirectionVec: Vec3i): Float =
    vector3f._1 * getDirectionVec.getX + vector3f._3 * getDirectionVec.getY + vector3f._2 * getDirectionVec.getZ

  def findEdge(facingToQuads: Map[EnumFacing, mutable.Buffer[BakedQuad]]): (Map[EnumFacing, BakedQuad], Map[EnumFacing, mutable.Buffer[BakedQuad]]) = {
    val uquad: Map[EnumFacing, BakedQuad] =
      facingToQuads.map(i =>
        i.copy(
          _2 = i._2
            .maxBy { q: BakedQuad =>
              val j = LazyUnpackedQuad.lazyUnpack(q)
              vectMask(
                j.vertices.head.pos, //j.vertices.map(_.pos).fold(j.vertices.head.pos) { case ((x1, y1, z1), (x2, y2, z2)) => (x1 + x2, y1 + y2, z1 + z2) },
                i._1.getDirectionVec)
            }))

    //[(EnumFacing, mutable.Buffer[BakedQuad]), (EnumFacing, UnpackedBakedQuad), Map[EnumFacing, (mutable.Buffer[BakedQuad], UnpackedBakedQuad)]]
    val tupleToTuple = facingToQuads.zip(uquad)(cbfZipMaps)
    uquad -> tupleToTuple.map(i => i.copy(_2 = i._2._1 - i._2._2))
  }

  val cache = new mutable.OpenHashMap[(IBlockState, EnumFacing), util.List[BakedQuad]]()


  override def getQuads(state: IBlockState, side: EnumFacing, rand: Long): util.List[BakedQuad] = {
    cache.getOrElseUpdate((state, side), {
      val facingToQuads: Map[EnumFacing, mutable.Buffer[BakedQuad]] = super.getQuads(state, side, rand)
        .asScala
        .groupBy(_.getFace)
        .filter(_._2.nonEmpty)

      val (sides, other) = findEdge(facingToQuads)

      (sides.values.map(new FlatableBakedQuad(_)) ++ other.values.flatten)
        //facingToQuads.values.flatten.map(new FlatableBakedQuad(_))
        .toList
        .asJava
        .asInstanceOf[util.List[BakedQuad]]
    })
  }

}

