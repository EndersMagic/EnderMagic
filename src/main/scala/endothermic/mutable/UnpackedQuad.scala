package endothermic.mutable

import hohserg.endothermic.BaseUnpackedQuad
import hohserg.endothermic.format.AttributeRepresentation.{_1, _2, _3, _4}
import net.minecraft.client.renderer.vertex.VertexFormat


class UnpackedQuad(
                    quadData: Array[Int], val format: VertexFormat,
                    val v1: UnpackedVertex,
                    val v2: UnpackedVertex,
                    val v3: UnpackedVertex,
                    val v4: UnpackedVertex
                  ) extends BaseUnpackedQuad {
  lazy val toRawArray: Array[Int] = {
    val r = quadData.clone()

    v1.toRawArray(r)
    v2.toRawArray(r)
    v3.toRawArray(r)
    v4.toRawArray(r)

    r
  }

  override type Self = UnpackedQuad

  override type VertexType = UnpackedVertex

  override def reconstruct(v1: VertexType, v2: VertexType, v3: VertexType, v4: VertexType): Self = this
}

object UnpackedQuad {


  def apply(implicit quadData: Array[Int], format: VertexFormat): UnpackedQuad = {
    new UnpackedQuad(
      quadData, format,
      new UnpackedVertex(_1),
      new UnpackedVertex(_2),
      new UnpackedVertex(_3),
      new UnpackedVertex(_4)
    )
  }

  private val unpackedQuad = UnpackedQuad(???, ???)
  unpackedQuad.v2.x += 1
  unpackedQuad.toRawArray
}
