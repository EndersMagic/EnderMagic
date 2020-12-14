package ru.mousecray.endmagic.util.render.endothermic.format

import net.minecraft.client.renderer.vertex.VertexFormatElement

import scala.runtime.Statics

case class AttributeId(_1: VertexFormatElement, _2: Int, _3: VertexRepr) {
  override lazy val hashCode: Int =  {
    var i = -889275714
    i = Statics.mix(i, Statics.anyHash(_1))
    i = Statics.mix(i, _2)
    i = Statics.mix(i, Statics.anyHash(_3))
    Statics.finalizeHash(i, 3)
  }
}
