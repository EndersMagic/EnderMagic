package hohserg.endothermic.immutable

import hohserg.endothermic.BaseUnpackedQuad
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.EnumFacing


class UnpackedQuad(
                    private[endothermic] val quad: BakedQuad,
                    private var _face: EnumFacing,
                    private var _atlas: TextureAtlasSprite,
                    private var _tint: Int,
                    private var _applyDiffuseLighting: Boolean
                  ) extends BaseUnpackedQuad with Cloneable {

  def face: EnumFacing = _face

  def atlas: TextureAtlasSprite = _atlas

  def tint: Int = _tint

  def applyDiffuseLighting: Boolean = _applyDiffuseLighting

  override type Self = UnpackedQuad

  lazy val toBakedQuad: BakedQuad =
    new BakedQuad(toRawArray, _tint, _face, _atlas, _applyDiffuseLighting, format)

  private[endothermic] override def reconstructResult(): UnpackedQuad =
    this.clone().asInstanceOf[UnpackedQuad]

  def updated(face: EnumFacing = this._face,
              atlas: TextureAtlasSprite = this._atlas,
              tint: Int = this._tint,
              applyDiffuseLighting: Boolean = this._applyDiffuseLighting): UnpackedQuad = {
    val r = reconstructResult()

    r._face = face
    r._atlas = atlas
    r._tint = tint
    r._applyDiffuseLighting = applyDiffuseLighting

    r
  }

}

object UnpackedQuad {

  def apply(quad: BakedQuad): UnpackedQuad =
    new UnpackedQuad(quad, quad.getFace, quad.getSprite, quad.getTintIndex, quad.shouldApplyDiffuseLighting())


  //UnpackedQuad(???).updated(v2f = v => v.reconstruct(x = v.x + 1)).toRawArray
}
