package ru.mousecray.endmagic.util.baked.lens.immutable

import net.minecraft.client.renderer.vertex.{DefaultVertexFormats, VertexFormat, VertexFormatElement}
import ru.mousecray.endmagic.util.baked.lens.immutable.VertexLens._
import ru.mousecray.endmagic.util.elix_x.ecomms.color.RGBA

object VertexSetLens extends VertexLens {

  case class ElementIndex(v: Int) extends AnyVal

  def withCopyOfArray(quadData: Array[Int])(actions: Array[Int] => Unit*): Array[Int] = {
    val r = quadData.clone()
    actions.foreach(_ (r))
    r
  }

  def setPos(quadData: Array[Int], x: Int, y: Int, z: Int)(implicit format: VertexFormat, vertex: Int): Array[Int] = {
    implicit val element: VertexFormatElement = DefaultVertexFormats.POSITION_3F

    withCopyOfArray(quadData)(
      pack(x, _, 0),
      pack(y, _, 1),
      pack(z, _, 2)
    )
  }

  def setColor(quadData: Array[Int], color: RGBA)(implicit format: VertexFormat, vertex: Int): Array[Int] = {
    implicit val element: VertexFormatElement = DefaultVertexFormats.COLOR_4UB

    withCopyOfArray(quadData)(
      pack(color.getRF, _, 0),
      pack(color.getGF, _, 1),
      pack(color.getBF, _, 2),
      pack(color.getAF, _, 3)
    )
  }

  def setUV(quadData: Array[Int], u: Float, v: Float)(implicit format: VertexFormat, vertex: Int): Array[Int] = {
    implicit val element: VertexFormatElement = DefaultVertexFormats.TEX_2F

    withCopyOfArray(quadData)(
      pack(u, _, 0),
      pack(v, _, 1)
    )
  }

  def setNormal(quadData: Array[Int], x: Byte, y: Byte, z: Byte)(implicit format: VertexFormat, vertex: Int): Array[Int] = {
    implicit val element: VertexFormatElement = DefaultVertexFormats.NORMAL_3B

    withCopyOfArray(quadData)(
      pack(x, _, 0),
      pack(y, _, 1),
      pack(z, _, 2)
    )
  }

  def setLightmap(quadData: Array[Int], x: Short, y: Short)(implicit format: VertexFormat, vertex: Int): Array[Int] = {
    implicit val element: VertexFormatElement = DefaultVertexFormats.NORMAL_3B

    withCopyOfArray(quadData)(
      pack(x, _, 0),
      pack(y, _, 1)
    )
  }

  def pack(value: Float, to: Array[Int], i: Int)(implicit element: VertexFormatElement, vertexStart: VertexStart, `type`: VertexFormatElement.EnumType, size: ElementSize, mask: ElementMask): Unit = {
    val pos = vertexStart.v + size.v * i
    val index = pos >> 2
    val offset = pos & 3
    var bits = 0
    val f = value
    if (`type` eq VertexFormatElement.EnumType.FLOAT)
      bits = java.lang.Float.floatToRawIntBits(f)
    else if ((`type` eq VertexFormatElement.EnumType.UBYTE) || (`type` eq VertexFormatElement.EnumType.USHORT) || (`type` eq VertexFormatElement.EnumType.UINT))
      bits = (f * mask.v).round
    else
      bits = (f * (mask.v >> 1)).round

    to(index) &= ~mask.v << (offset * 8)
    to(index) |= (bits & mask.v) << (offset * 8)
    // TODO handle overflow into to[index + 1]
  }

}
