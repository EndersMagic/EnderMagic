package ru.mousecray.endmagic.util.render.endothermic.builder

import ru.mousecray.endmagic.util.render.endothermic.format.UnpackEvaluations
import ru.mousecray.endmagic.util.render.endothermic.quad.mutable.LazyUnpackedQuad
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.VertexFormat
import net.minecraft.util.EnumFacing

class UnpackedQuadBuilder(format: VertexFormat) {

  val quad = LazyUnpackedQuad(new BakedQuad(UnpackEvaluations.defaultVertexData(format), -1, null, null, false, format))

  def tintIndex(value: Int): this.type = {
    quad.tint = value
    this
  }

  def face(value: EnumFacing): this.type = {
    quad.face = value
    this
  }

  def applyDiffuseLighting(value: Boolean): this.type = {
    quad.applyDiffuseLighting = value
    this
  }

  def atlas(value: TextureAtlasSprite): this.type = {
    quad.atlas = value
    this
  }

  def toUnpackerQuad: LazyUnpackedQuad = quad

}
