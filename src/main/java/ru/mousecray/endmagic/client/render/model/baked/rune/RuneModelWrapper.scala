package ru.mousecray.endmagic.client.render.model.baked.rune

import java.util

import hohserg.endothermic.immutable.UnpackedQuad
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.{BakedQuad, IBakedModel}
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.Vec3i
import ru.mousecray.endmagic.client.render.model.baked.BakedModelDelegate

import scala.collection.mutable

class RuneModelWrapper(baseModel: IBakedModel) extends BakedModelDelegate(baseModel) {

  import collection.JavaConverters._

  scala.collection.convert.Wrappers

  def vectMask(x: Float, y: Float, z: Float, getDirectionVec: Vec3i): Float =
    x * getDirectionVec.getX + y * getDirectionVec.getY + z * getDirectionVec.getZ

  def findEdge(facingToQuads: Map[EnumFacing, mutable.Buffer[BakedQuad]]): (Map[EnumFacing, BakedQuad], Map[EnumFacing, mutable.Buffer[BakedQuad]]) = {
    val uquad: Map[EnumFacing, BakedQuad] =
      facingToQuads.map(i =>
        i.copy(
          _2 = i._2
            .maxBy { q: BakedQuad =>
              val j = UnpackedQuad(q)
              vectMask(
                j.v1_x,
                j.v1_y,
                j.v1_z,
                i._1.getDirectionVec)
            }))

    uquad -> facingToQuads.mapValues(b => b --= uquad.values)
  }

  val cache = new mutable.OpenHashMap[(IBlockState, EnumFacing), util.List[BakedQuad]]()


  override def getQuads(state: IBlockState, side: EnumFacing, rand: Long): util.List[BakedQuad] = {
    cache.getOrElseUpdate((state, side), {
      val facingToQuads: Map[EnumFacing, mutable.Buffer[BakedQuad]] = super.getQuads(state, side, rand)
        .asScala
        .groupBy(_.getFace)
        .filter(_._2.nonEmpty)

      val (sides, other) = findEdge(facingToQuads)

      (sides.values.map(new VolumetricBakedQuad(_)) ++ other.values.flatten)
        //facingToQuads.values.flatten.map(new VolumetricBakedQuad(_))
        .toList
        .asJava
        .asInstanceOf[util.List[BakedQuad]]
    })
  }

}
