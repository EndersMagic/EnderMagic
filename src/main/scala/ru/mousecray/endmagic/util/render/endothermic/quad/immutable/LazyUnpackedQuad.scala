package ru.mousecray.endmagic.util.render.endothermic.quad.immutable

import ru.mousecray.endmagic.util.render.endothermic.quad.BaseUnpackedQuad
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.EnumFacing


class LazyUnpackedQuad private[endothermic](
                    private[quad] val quad: BakedQuad,
                    private var _face: EnumFacing,
                    private var _atlas: TextureAtlasSprite,
                    private var _tint: Int,
                    private var _applyDiffuseLighting: Boolean
                  ) extends BaseUnpackedQuad with Cloneable {

  def face: EnumFacing = _face

  def atlas: TextureAtlasSprite = _atlas

  def tint: Int = _tint

  def applyDiffuseLighting: Boolean = _applyDiffuseLighting

  override type Self = LazyUnpackedQuad

  lazy val toBakedQuad: BakedQuad =
    new BakedQuad(toRawArray, _tint, _face, _atlas, _applyDiffuseLighting, format)

  private[quad] override def reconstructResult(): LazyUnpackedQuad =
    this.clone().asInstanceOf[LazyUnpackedQuad]

  def updated(face: EnumFacing = this._face,
              atlas: TextureAtlasSprite = this._atlas,
              tint: Int = this._tint,
              applyDiffuseLighting: Boolean = this._applyDiffuseLighting): LazyUnpackedQuad = {
    val r = reconstructResult()

    r._face = face
    r._atlas = atlas
    r._tint = tint
    r._applyDiffuseLighting = applyDiffuseLighting

    if (atlas != this._atlas) {
      def transposeUVToNewAtlas(v: Float, atlasMin: Float, atlasWidth: Float, newAtlasMin: Float, newAtlasWidth: Float): Float = {
        ((v - atlasMin) / atlasWidth) * newAtlasWidth + newAtlasMin
      }

      val newAtlasWidthU = atlas.getMaxU - atlas.getMinU
      val atlasWidthU = _atlas.getMaxU - _atlas.getMinU
      val newAtlasWidthV = atlas.getMaxV - atlas.getMinV
      val atlasWidthV = _atlas.getMaxV - _atlas.getMinV

      r._v1_u = transposeUVToNewAtlas(r.v1_u, _atlas.getMinU, atlasWidthU, atlas.getMinU, newAtlasWidthU)
      r._v2_u = transposeUVToNewAtlas(r.v2_u, _atlas.getMinU, atlasWidthU, atlas.getMinU, newAtlasWidthU)
      r._v3_u = transposeUVToNewAtlas(r.v3_u, _atlas.getMinU, atlasWidthU, atlas.getMinU, newAtlasWidthU)
      r._v4_u = transposeUVToNewAtlas(r.v4_u, _atlas.getMinU, atlasWidthU, atlas.getMinU, newAtlasWidthU)

      r._v1_v = transposeUVToNewAtlas(r.v1_v, _atlas.getMinV, atlasWidthV, atlas.getMinV, newAtlasWidthV)
      r._v2_v = transposeUVToNewAtlas(r.v2_v, _atlas.getMinV, atlasWidthV, atlas.getMinV, newAtlasWidthV)
      r._v3_v = transposeUVToNewAtlas(r.v3_v, _atlas.getMinV, atlasWidthV, atlas.getMinV, newAtlasWidthV)
      r._v4_v = transposeUVToNewAtlas(r.v4_v, _atlas.getMinV, atlasWidthV, atlas.getMinV, newAtlasWidthV)
    }

    r
  }

}

object LazyUnpackedQuad {

  def apply(quad: BakedQuad): LazyUnpackedQuad =
    new LazyUnpackedQuad(quad, quad.getFace, quad.getSprite, quad.getTintIndex, quad.shouldApplyDiffuseLighting())


  //LazyUnpackedQuad(???).updated(v2f = v => v.reconstruct(x = v.x + 1)).toRawArray
}
