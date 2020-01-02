package endothermic.immutable

import hohserg.endothermic.BaseUnpackedQuad
import hohserg.endothermic.format.AttributeRepresentation._
import net.minecraft.client.renderer.vertex.VertexFormat


case class UnpackedQuad(
                         quadData: Array[Int],
                         v1: UnpackedVertex,
                         v2: UnpackedVertex,
                         v3: UnpackedVertex,
                         v4: UnpackedVertex
                       ) extends BaseUnpackedQuad {

  override type Self = UnpackedQuad

  override def reconstruct(v1: VertexType, v2: VertexType, v3: VertexType, v4: VertexType): Self =
    UnpackedQuad(quadData, v1, v2, v3, v4)

  def updated(
               v1f: VertexType => VertexType = identity,
               v2f: VertexType => VertexType = identity,
               v3f: VertexType => VertexType = identity,
               v4f: VertexType => VertexType = identity
             ): UnpackedQuad = {
    UnpackedQuad(quadData,
      v1f(v1),
      v2f(v2),
      v3f(v3),
      v4f(v4)
    )

  }

  lazy val toRawArray: Array[Int] = {
    val r = quadData.clone()

    v1.toRawArray(r)
    v2.toRawArray(r)
    v3.toRawArray(r)
    v4.toRawArray(r)

    r
  }

  override type VertexType = UnpackedVertex
}

object UnpackedQuad {


  def apply(implicit quadData: Array[Int], format: VertexFormat): UnpackedQuad = {
    UnpackedQuad(
      quadData,
      new UnpackedVertex(_1),
      new UnpackedVertex(_2),
      new UnpackedVertex(_3),
      new UnpackedVertex(_4)
    )
  }

  UnpackedQuad(???, ???).updated(v2f = v => v.reconstruct(x = v.x + 1)).toRawArray
}
