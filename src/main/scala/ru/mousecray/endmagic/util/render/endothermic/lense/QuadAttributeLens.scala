package ru.mousecray.endmagic.util.render.endothermic.lense

import ru.mousecray.endmagic.util.render.endothermic.format.AttributeId
import ru.mousecray.endmagic.util.render.endothermic.format.UnpackEvaluations.{AttributeIsomorphism, _}
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.vertex.VertexFormat

class QuadAttributeLens(rules: Map[AttributeId, AttributeIsomorphism]) {

  def get(attributeId: AttributeId, quad: BakedQuad): Float = {
    val option = rules.get(attributeId)
    if (option.isDefined)
      option.get.unpack(quad.getVertexData)
    else
      0
  }

  def set(attributeId: AttributeId, quad: BakedQuad, value: Float): Unit =
    rules.get(attributeId).foreach(_.pack(value, quad.getVertexData))

  def get(attributeId: AttributeId, vertexData: Array[Int]): Float = {
    val option = rules.get(attributeId)
    if (option.isDefined)
      option.get.unpack(vertexData)
    else
      0
  }

  def set(attributeId: AttributeId, vertexData: Array[Int], value: Float): Unit =
    rules.get(attributeId).foreach(_.pack(value, vertexData))

}

object QuadAttributeLens {

  val getForFormat: VertexFormat => QuadAttributeLens = memoize(format => new QuadAttributeLens(getFormatParseRule(format)))

}
