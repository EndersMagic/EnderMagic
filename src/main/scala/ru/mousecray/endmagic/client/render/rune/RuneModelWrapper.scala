package ru.mousecray.endmagic.client.render.rune

import java.util

import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.{BakedQuad, IBakedModel, ModelResourceLocation}
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.Vec3i
import ru.mousecray.endmagic.client.render.model.baked.BakedModelDelegate
import ru.mousecray.endmagic.util.render.endothermic.quad.immutable.LazyUnpackedQuad

import scala.collection.mutable

class RuneModelWrapper(baseModel: IBakedModel, resource: ModelResourceLocation) extends BakedModelDelegate(baseModel) {

  import collection.JavaConverters._

  scala.collection.convert.Wrappers

  def vectMask(x: Float, y: Float, z: Float, getDirectionVec: Vec3i): Float =
    x * getDirectionVec.getX + y * getDirectionVec.getY + z * getDirectionVec.getZ

  def findEdge(baseQuads: Seq[BakedQuad]): BakedQuad =
    baseQuads.maxBy { q: BakedQuad =>
      val j = LazyUnpackedQuad(q)
      vectMask(
        j.v1_x,
        j.v1_y,
        j.v1_z,
        j.face.getDirectionVec)
    }

  val cache = new mutable.OpenHashMap[(IBlockState, EnumFacing), util.List[BakedQuad]]()


  override def getQuads(state: IBlockState, side: EnumFacing, rand: Long): util.List[BakedQuad] = {
    if (true)
      super.getQuads(state, side, rand)
    else
      cache.getOrElseUpdate((state, side), {

        val originalGetQuads = super.getQuads _

        val allSideEdges: Map[Option[EnumFacing], BakedQuad] = (EnumFacing.values().map(Some(_)) :+ None)
          .map(v1 => v1 -> originalGetQuads(state, v1.orNull, 0).asScala)
          .filter(_._2.nonEmpty)
          .toMap
          .mapValues(findEdge)


        val baseQuadsJava = originalGetQuads(state, side, rand)
        val baseQuads = baseQuadsJava.asScala

        allSideEdges.get(Option(side))
          .map { edgeForCurrentSide =>
            (baseQuads.filter(_ != edgeForCurrentSide) :+ new VolumetricBakedQuad(side, allSideEdges))
              .toList
              .asJava
              .asInstanceOf[util.List[BakedQuad]]
          }.getOrElse(baseQuadsJava)
      })
  }


  private def withoutEdge(baseQuads: mutable.Buffer[BakedQuad]) = {
    if (baseQuads.nonEmpty)
      baseQuads.filter(_ != findEdge(baseQuads))
    else
      baseQuads
  }
}