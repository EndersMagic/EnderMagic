package ru.mousecray.endmagic.util.baked.lens.immutable

import net.minecraft.client.renderer.vertex.{VertexFormat, VertexFormatElement}

import VertexLens._

trait VertexLens {


  protected implicit def elementMask[A](implicit size: ElementSize): ElementMask =
    ElementMask((256 << (8 * (size.v - 1))) - 1)

  protected implicit def elementSize[A](implicit `type`: VertexFormatElement.EnumType): ElementSize =
    ElementSize(`type`.getSize)

  protected implicit def elementType[A](implicit format: VertexFormat, e: ElementIndex): VertexFormatElement.EnumType =
    format.getElement(e.v).getType

  protected implicit def vertexStart[A](implicit vertex: Int, format: VertexFormat, e: ElementIndex): VertexStart =
    VertexStart(vertex * format.getNextOffset + format.getOffset(e.v))

  protected implicit def indexOfElement[A](implicit format: VertexFormat, element: VertexFormatElement): ElementIndex =
    ElementIndex(format.getElements.indexOf(element))

}

object VertexLens {

  case class ElementIndex(v: Int) extends AnyVal

  case class VertexStart(v: Int) extends AnyVal

  case class ElementSize(v: Int) extends AnyVal

  case class ElementMask(v: Int) extends AnyVal

}
