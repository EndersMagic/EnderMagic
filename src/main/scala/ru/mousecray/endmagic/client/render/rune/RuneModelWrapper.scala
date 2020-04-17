package ru.mousecray.endmagic.client.render.rune

import java.util

import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.{BakedQuad, IBakedModel}
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.Vec3i
import ru.mousecray.endmagic.client.render.model.baked.BakedModelDelegate
import ru.mousecray.endmagic.util.render.endothermic.immutable.UnpackedQuad

import scala.collection.mutable

class RuneModelWrapper(baseModel: IBakedModel) extends BakedModelDelegate(baseModel) {

  import collection.JavaConverters._

  scala.collection.convert.Wrappers

  def vectMask(x: Float, y: Float, z: Float, getDirectionVec: Vec3i): Float =
    x * getDirectionVec.getX + y * getDirectionVec.getY + z * getDirectionVec.getZ

  def findEdge(baseQuads: Seq[BakedQuad]): BakedQuad =
    baseQuads.maxBy { q: BakedQuad =>
      val j = UnpackedQuad(q)
      vectMask(
        j.v1_x,
        j.v1_y,
        j.v1_z,
        j.face.getDirectionVec)
    }

  val cache = new mutable.OpenHashMap[(IBlockState, EnumFacing), util.List[BakedQuad]]()


  override def getQuads(state: IBlockState, side: EnumFacing, rand: Long): util.List[BakedQuad] = {
    cache.getOrElseUpdate((state, side), {

      val originalGetQuads = super.getQuads _

      val allSideQuads = EnumFacing.values()
        .map(v1 => v1 -> originalGetQuads(state, v1, 0).asScala)
        .filter(_._2.nonEmpty)
        .toMap
        .mapValues(findEdge)


      val baseQuads = super.getQuads(state, side, rand).asScala

      ((if (baseQuads.nonEmpty) {
        val edge = findEdge(baseQuads)
        baseQuads.filter(_ != edge)
      } else
        baseQuads) :+ new VolumetricBakedQuad(side, allSideQuads))
        .toList
        .asJava
        .asInstanceOf[util.List[BakedQuad]]
    })
  }

}
