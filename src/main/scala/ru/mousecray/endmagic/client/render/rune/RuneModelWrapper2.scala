package ru.mousecray.endmagic.client.render.rune

import java.util

import com.google.common.collect.ImmutableList
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.{BakedQuad, IBakedModel}
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.Vec3i
import ru.mousecray.endmagic.client.render.model.baked.BakedModelDelegate
import ru.mousecray.endmagic.util.render.endothermic.quad.immutable.LazyUnpackedQuad

import scala.collection.JavaConverters._
import scala.collection.mutable

class RuneModelWrapper2(baseModel: IBakedModel) extends BakedModelDelegate(baseModel) {

  val cache = new mutable.OpenHashMap[IBlockState, Map[Option[EnumFacing], util.List[BakedQuad]]]()

  override def getQuads(state: IBlockState, side: EnumFacing, rand: Long): util.List[BakedQuad] =
    cache.getOrElseUpdate(state, {
      val originalGetQuads = super.getQuads _

      val allCullFaces: List[Option[EnumFacing]] = EnumFacing.values().toList.map(Option[EnumFacing]) :+ None

      val allCulledQuads: Map[Option[EnumFacing], List[BakedQuad]] = allCullFaces.map(cullFace => cullFace -> originalGetQuads(state, cullFace.orNull, rand).asScala.toList).toMap

      val allQuads: Map[EnumFacing, List[(Option[EnumFacing], BakedQuad)]] =
        allCulledQuads.toList.flatMap { case (cullFace, list) => list.map(cullFace -> _) }.groupBy(_._2.getFace)

      val allEdges: Map[EnumFacing, (Option[EnumFacing], BakedQuad)] = allQuads.mapValues(list => list.maxBy { case (_, q) =>
        val j = LazyUnpackedQuad(q)
        vectMask(
          j.v1_x,
          j.v1_y,
          j.v1_z,
          j.face.getDirectionVec)
      })

      val allEdgesInverted: Map[Option[EnumFacing], Set[BakedQuad]] = allEdges.toSet.groupBy((i: (EnumFacing, (Option[EnumFacing], BakedQuad))) => i._2._1).mapValues(_.map(i => i._2._2))

      val edgesSet = allEdges.values.map(_._2).toSet

      val filteredCulledQuads = allCulledQuads.map {
        case p@(cullFace, list) =>
          allEdgesInverted.get(cullFace)
            .map(edgesForCullFace => cullFace -> (list.filter(!edgesForCullFace.contains(_)) ++ edgesForCullFace.map(e=>new VolumetricBakedQuad2(e.getFace,allEdges))))
            .getOrElse(p)
      }

      val finallyCulledQuads = filteredCulledQuads.mapValues(_.asJava)

      finallyCulledQuads
    }).getOrElse(Option(side), ImmutableList.of())


  def vectMask(x: Float, y: Float, z: Float, getDirectionVec: Vec3i): Float =
    x * getDirectionVec.getX + y * getDirectionVec.getY + z * getDirectionVec.getZ

}

object RuneModelWrapper2 {

}
