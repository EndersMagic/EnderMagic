package ru.mousecray.endmagic.util.render.endothermic.builder

import ru.mousecray.endmagic.util.render.endothermic.format.AttributeId
import ru.mousecray.endmagic.util.render.endothermic.format.UnpackEvaluations
import ru.mousecray.endmagic.util.render.endothermic.lense.QuadAttributeLens
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.VertexFormat
import net.minecraft.util.EnumFacing

class BakedQuadBuilder(format: VertexFormat) {
  private val lens = QuadAttributeLens.getForFormat(format)

  private val vertexData = UnpackEvaluations.defaultVertexData(format)
  private var _tintIndex: Int = -1
  private var _face: EnumFacing = _
  private var _atlas: TextureAtlasSprite = _
  private var _applyDiffuseLighting: Boolean = false


  def tintIndex(value: Int): this.type = {
    _tintIndex = value
    this
  }

  def face(value: EnumFacing): this.type = {
    _face = value
    this
  }

  def applyDiffuseLighting(value: Boolean): this.type = {
    _applyDiffuseLighting = value
    this
  }

  def atlas(value: TextureAtlasSprite): this.type = {
    _atlas = value
    this
  }

  def withAttribute(attributeId: AttributeId, value: Float): this.type = {
    lens.set(attributeId, vertexData, value)
    this
  }

  def toBakedQuad: BakedQuad = {
    if (_face == null)
      throw new IllegalArgumentException("face don't been set")
    if (_atlas == null)
      throw new IllegalArgumentException("atlas don't been set")

    new BakedQuad(vertexData, _tintIndex, _face, _atlas, _applyDiffuseLighting, format)
  }

}
