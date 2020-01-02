package endothermic.format

import hohserg.endothermic.format.AttributeRepresentation._
import net.minecraft.client.renderer.vertex.{VertexFormat, VertexFormatElement}

import scala.collection.mutable

object UnpackEvaluations {

  private type FirstValue = mutable.OpenHashMap[VertexAttribute, DependentOnFormatAttribute]

  private val map: mutable.OpenHashMap[VertexFormat, FirstValue] =
    new mutable.OpenHashMap[VertexFormat, FirstValue]()

  case class DependentOnFormatAttribute(
                                         elementSize: Int, elementMask: Int, elementEnumType: VertexFormatElement.EnumType,
                                         dependentOnVertex: Map[Vertex, DependentOnFormatAttributeVertex]
                                       )

  object DependentOnFormatAttribute {
    def apply(format: VertexFormat, attribute: VertexAttribute): DependentOnFormatAttribute = {
      val ei = elementIndex(format, attribute)
      val `type` = elementType(format, ei)
      val es = elementSize(`type`)
      val mask = elementMask(es)
      new DependentOnFormatAttribute(es, mask, `type`,
        Seq(_1, _2, _3, _4).map(v => v -> DependentOnFormatAttributeVertex(vertexStart(v, format, ei))).toMap
      )
    }
  }

  case class DependentOnFormatAttributeVertex(vertexStart: Int) extends AnyVal


  def pack[A <: VertexAttribute](value: Float, to: Array[Int], i: Int)(implicit format: VertexFormat, v: Vertex, a: A): Unit = {
    val DependentOnFormatAttribute(elementSize, elementMask, elementEnumType, dependentOnVertex) = getAttributeProperties(format, a)
    //todo: remove tuple allocation
    val DependentOnFormatAttributeVertex(vertexStart) = dependentOnVertex(v)

    val pos = vertexStart + elementSize * i
    val index = pos >> 2
    val offset = pos & 3
    var bits = 0
    if (elementEnumType eq VertexFormatElement.EnumType.FLOAT)
      bits = java.lang.Float.floatToRawIntBits(value)
    else if ((elementEnumType eq VertexFormatElement.EnumType.UBYTE) || (elementEnumType eq VertexFormatElement.EnumType.USHORT) || (elementEnumType eq VertexFormatElement.EnumType.UINT))
      bits = (value * elementMask).round
    else
      bits = (value * (elementMask >> 1)).round

    to(index) &= ~elementMask << (offset * 8)
    to(index) |= (bits & elementMask) << (offset * 8)
    // TODO handle overflow into to[index + 1]
  }

  def unpack[A <: VertexAttribute](quadData: Array[Int], i: Int)(implicit format: VertexFormat, v: Vertex, a: A, parser: AttributeParser[A]): Float = {

    val DependentOnFormatAttribute(elementSize, elementMask, _, dependentOnVertex) = getAttributeProperties(format, a)
    //todo: remove tuple allocation
    val DependentOnFormatAttributeVertex(vertexStart) = dependentOnVertex(v)


    val pos = vertexStart + elementSize * i
    val index = pos >> 2
    val offset = pos & 3
    var bits: Int = quadData(index)
    bits = bits >>> (offset * 8)
    if ((pos + elementSize - 1) / 4 != index) bits |= quadData(index + 1) << ((4 - offset) * 8)
    bits &= elementMask

    //valuePacker(
    parser.parse(bits, elementMask)
    //)
  }


  private def getAttributeProperties[A <: VertexAttribute, V <: Vertex](format: VertexFormat, a: A): DependentOnFormatAttribute = {
    val dependentOnFormat: FirstValue = map.getOrElseUpdate(format, new mutable.OpenHashMap[VertexAttribute, DependentOnFormatAttribute])
    dependentOnFormat.getOrElseUpdate(a, DependentOnFormatAttribute(format, a))
  }


  def elementMask(elementSize: Int): Int =
    (256 << (8 * (elementSize - 1))) - 1

  def elementSize(`type`: VertexFormatElement.EnumType): Int =
    `type`.getSize

  def elementType(format: VertexFormat, elementIndex: Int): VertexFormatElement.EnumType =
    format.getElement(elementIndex).getType

  def vertexStart[V <: Vertex, A <: VertexAttribute](vertex: V, format: VertexFormat, elementIndex: Int): Int =
    vertex.index * format.getNextOffset + format.getOffset(elementIndex)

  def elementIndex[A <: VertexAttribute](format: VertexFormat, attribute: A): Int =
    format.getElements.indexOf(attribute.element)
}
