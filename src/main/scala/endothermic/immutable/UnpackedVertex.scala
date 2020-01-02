package endothermic.immutable

import hohserg.endothermic.BaseUnpackedVertex
import hohserg.endothermic.format.AttributeRepresentation.Vertex
import net.minecraft.client.renderer.vertex.VertexFormat

import scala.language.dynamics

class UnpackedVertex(private val vertexP: Vertex)(implicit protected val quadData: Array[Int], protected val format: VertexFormat) extends BaseUnpackedVertex[UnpackedVertex] {

  override def toImmutable: UnpackedVertex = this

  override def getUpdateDestination: UnpackedVertex = new UnpackedVertex(vertex)(quadData, format)

  override private[endothermic] implicit def vertex: Vertex = vertexP
}
