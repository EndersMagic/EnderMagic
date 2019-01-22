package ru.mousecray.endmagic.client.render.rune

import java.util

import code.elix_x.excore.utils.client.render.model.UnpackedBakedQuad
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.{BakedQuad, IBakedModel}
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.Vec3i
import org.lwjgl.util.vector.Vector3f
import ru.mousecray.endmagic.client.render.model.baked.BakedModelDelegate

import scala.collection.generic.CanBuildFrom
import scala.collection.mutable

class RunModelWrapper(baseModel: IBakedModel) extends BakedModelDelegate(baseModel) {

  import collection.JavaConverters._

  scala.collection.convert.Wrappers

  def vectMask(vector3f: Vector3f, getDirectionVec: Vec3i): Float =
    vector3f.x * getDirectionVec.getX + vector3f.y * getDirectionVec.getY + vector3f.z * getDirectionVec.getZ

  def cbfZipMaps[K, V1, V2] = new CanBuildFrom[Map[K, V1], ((K, V1), (K, V2)), Map[K, (V1, V2)]] {
    override def apply(from: Map[K, V1]): mutable.Builder[((K, V1), (K, V2)), Map[K, (V1, V2)]] =
      apply()

    override def apply(): mutable.Builder[((K, V1), (K, V2)), Map[K, (V1, V2)]] =
      new mutable.Builder[((K, V1), (K, V2)), Map[K, (V1, V2)]] {
        private var map = Map[K, (V1, V2)]()

        override def +=(elem: ((K, V1), (K, V2))): this.type = {
          map += elem._1._1 -> (elem._1._2, elem._2._2)
          this
        }

        override def clear(): Unit = map = Map[K, (V1, V2)]()

        override def result(): Map[K, (V1, V2)] = map
      }
  }

  def findExtreme(facingToQuads: Map[EnumFacing, mutable.Buffer[BakedQuad]]): (Map[EnumFacing, BakedQuad], Map[EnumFacing, mutable.Buffer[BakedQuad]]) = {
    val uquad: Map[EnumFacing, BakedQuad] =
      facingToQuads.map(i =>
        i.copy(
          _2 = i._2
            .maxBy { q: BakedQuad =>
              val j = UnpackedBakedQuad.unpack(q)
              vectMask(j.getVertices.getVertices
                .asScala
                .map(_.getPos)
                .fold(j.getVertices.iterator().next().getPos)(
                  (a, b) => Vector3f.add(a, b, null).scale(0.5f).asInstanceOf[Vector3f]
                ), i._1.getDirectionVec)
            }))

    //[(EnumFacing, mutable.Buffer[BakedQuad]), (EnumFacing, UnpackedBakedQuad), Map[EnumFacing, (mutable.Buffer[BakedQuad], UnpackedBakedQuad)]]
    val tupleToTuple = facingToQuads.zip(uquad)(cbfZipMaps)
    uquad -> tupleToTuple.map(i => i.copy(_2 = i._2._1 - i._2._2))
  }


  override def getQuads(state: IBlockState, side: EnumFacing, rand: Long): util.List[BakedQuad] = {
    val facingToQuads: Map[EnumFacing, mutable.Buffer[BakedQuad]] = super.getQuads(state, side, rand)
      .asScala
      .groupBy(_.getFace)
      .filter(_._2.nonEmpty)

    val (sides, other) = findExtreme(facingToQuads)

    (sides.values.map(new FlatableBakedQuad(_)) ++ other.values.flatten)
      .toList
      .asJava
  }

  /*
  override def getOverrides: ItemOverrideList = new ItemOverrideList(Collections.emptyList()){
    override def handleItemState(originalModel: IBakedModel, stack: ItemStack, world: World, entity: EntityLivingBase): IBakedModel = {
      super.handleItemState(originalModel, stack, world, entity)
    }
  }*/

}
