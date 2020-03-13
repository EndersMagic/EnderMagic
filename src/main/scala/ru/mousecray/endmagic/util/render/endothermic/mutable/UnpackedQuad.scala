package ru.mousecray.endmagic.util.render.endothermic.mutable

import ru.mousecray.endmagic.util.render.endothermic.BaseUnpackedQuad
import ru.mousecray.endmagic.util.render.endothermic.BaseUnpackedQuad._
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.EnumFacing


class UnpackedQuad(
                    private[endothermic] val quad: BakedQuad,
                    var face: EnumFacing,
                    var atlas: TextureAtlasSprite,
                    var tint: Int,
                    var applyDiffuseLighting: Boolean
                  ) extends BaseUnpackedQuad with Cloneable {

  def toBakedQuad: BakedQuad =
    new BakedQuad(toRawArray, tint, face, atlas, applyDiffuseLighting, format)

  override type Self = UnpackedQuad

  private[endothermic] override def reconstructResult(): UnpackedQuad = this

  def copy: Self = this.clone().asInstanceOf[Self]

  def v1_x_=(v: Float): Unit = {
    initFlag1 |= (1 << x_1)
    changeFlag1 |= (1 << x_1)
    _v1_x = v
  }

  def v1_y_=(v: Float): Unit = {
    initFlag1 |= (1 << y_1)
    changeFlag1 |= (1 << y_1)
    _v1_y = v
  }

  def v1_z_=(v: Float): Unit = {
    initFlag1 |= (1 << z_1)
    changeFlag1 |= (1 << z_1)
    _v1_z = v
  }

  def v1_r_=(v: Float): Unit = {
    initFlag1 |= (1 << r_1)
    changeFlag1 |= (1 << r_1)
    _v1_r = v
  }

  def v1_g_=(v: Float): Unit = {
    initFlag1 |= (1 << g_1)
    changeFlag1 |= (1 << g_1)
    _v1_g = v
  }

  def v1_b_=(v: Float): Unit = {
    initFlag1 |= (1 << b_1)
    changeFlag1 |= (1 << b_1)
    _v1_b = v
  }

  def v1_a_=(v: Float): Unit = {
    initFlag1 |= (1 << a_1)
    changeFlag1 |= (1 << a_1)
    _v1_a = v
  }

  def v1_u_=(v: Float): Unit = {
    initFlag1 |= (1 << u_1)
    changeFlag1 |= (1 << u_1)
    _v1_u = v
  }

  def v1_v_=(v: Float): Unit = {
    initFlag1 |= (1 << v_1)
    changeFlag1 |= (1 << v_1)
    _v1_v = v
  }

  def v1_lu_=(v: Float): Unit = {
    initFlag1 |= (1 << lu_1)
    changeFlag1 |= (1 << lu_1)
    _v1_lu = v
  }

  def v1_lv_=(v: Float): Unit = {
    initFlag1 |= (1 << lv_1)
    changeFlag1 |= (1 << lv_1)
    _v1_lv = v
  }

  def v1_nx_=(v: Float): Unit = {
    initFlag1 |= (1 << nx_1)
    changeFlag1 |= (1 << nx_1)
    _v1_nx = v
  }

  def v1_ny_=(v: Float): Unit = {
    initFlag1 |= (1 << ny_1)
    changeFlag1 |= (1 << ny_1)
    _v1_ny = v
  }

  def v1_nz_=(v: Float): Unit = {
    initFlag1 |= (1 << nz_1)
    changeFlag1 |= (1 << nz_1)
    _v1_nz = v
  }

  def v1_p_=(v: Float): Unit = {
    initFlag1 |= (1 << p_1)
    changeFlag1 |= (1 << p_1)
    _v1_p = v
  }

  def v2_x_=(v: Float): Unit = {
    initFlag2 |= (1 << x_1)
    changeFlag2 |= (1 << x_1)
    _v2_x = v
  }

  def v2_y_=(v: Float): Unit = {
    initFlag2 |= (1 << y_1)
    changeFlag2 |= (1 << y_1)
    _v2_y = v
  }

  def v2_z_=(v: Float): Unit = {
    initFlag2 |= (1 << z_1)
    changeFlag2 |= (1 << z_1)
    _v2_z = v
  }

  def v2_r_=(v: Float): Unit = {
    initFlag2 |= (1 << r_1)
    changeFlag2 |= (1 << r_1)
    _v2_r = v
  }

  def v2_g_=(v: Float): Unit = {
    initFlag2 |= (1 << g_1)
    changeFlag2 |= (1 << g_1)
    _v2_g = v
  }

  def v2_b_=(v: Float): Unit = {
    initFlag2 |= (1 << b_1)
    changeFlag2 |= (1 << b_1)
    _v2_b = v
  }

  def v2_a_=(v: Float): Unit = {
    initFlag2 |= (1 << a_1)
    changeFlag2 |= (1 << a_1)
    _v2_a = v
  }

  def v2_u_=(v: Float): Unit = {
    initFlag2 |= (1 << u_1)
    changeFlag2 |= (1 << u_1)
    _v2_u = v
  }

  def v2_v_=(v: Float): Unit = {
    initFlag2 |= (1 << v_1)
    changeFlag2 |= (1 << v_1)
    _v2_v = v
  }

  def v2_lu_=(v: Float): Unit = {
    initFlag2 |= (1 << lu_1)
    changeFlag2 |= (1 << lu_1)
    _v2_lu = v
  }

  def v2_lv_=(v: Float): Unit = {
    initFlag2 |= (1 << lv_1)
    changeFlag2 |= (1 << lv_1)
    _v2_lv = v
  }

  def v2_nx_=(v: Float): Unit = {
    initFlag2 |= (1 << nx_1)
    changeFlag2 |= (1 << nx_1)
    _v2_nx = v
  }

  def v2_ny_=(v: Float): Unit = {
    initFlag2 |= (1 << ny_1)
    changeFlag2 |= (1 << ny_1)
    _v2_ny = v
  }

  def v2_nz_=(v: Float): Unit = {
    initFlag2 |= (1 << nz_1)
    changeFlag2 |= (1 << nz_1)
    _v2_nz = v
  }

  def v2_p_=(v: Float): Unit = {
    initFlag2 |= (1 << p_1)
    changeFlag2 |= (1 << p_1)
    _v2_p = v
  }

  def v3_x_=(v: Float): Unit = {
    initFlag3 |= (1 << x_1)
    changeFlag3 |= (1 << x_1)
    _v3_x = v
  }

  def v3_y_=(v: Float): Unit = {
    initFlag3 |= (1 << y_1)
    changeFlag3 |= (1 << y_1)
    _v3_y = v
  }

  def v3_z_=(v: Float): Unit = {
    initFlag3 |= (1 << z_1)
    changeFlag3 |= (1 << z_1)
    _v3_z = v
  }

  def v3_r_=(v: Float): Unit = {
    initFlag3 |= (1 << r_1)
    changeFlag3 |= (1 << r_1)
    _v3_r = v
  }

  def v3_g_=(v: Float): Unit = {
    initFlag3 |= (1 << g_1)
    changeFlag3 |= (1 << g_1)
    _v3_g = v
  }

  def v3_b_=(v: Float): Unit = {
    initFlag3 |= (1 << b_1)
    changeFlag3 |= (1 << b_1)
    _v3_b = v
  }

  def v3_a_=(v: Float): Unit = {
    initFlag3 |= (1 << a_1)
    changeFlag3 |= (1 << a_1)
    _v3_a = v
  }

  def v3_u_=(v: Float): Unit = {
    initFlag3 |= (1 << u_1)
    changeFlag3 |= (1 << u_1)
    _v3_u = v
  }

  def v3_v_=(v: Float): Unit = {
    initFlag3 |= (1 << v_1)
    changeFlag3 |= (1 << v_1)
    _v3_v = v
  }

  def v3_lu_=(v: Float): Unit = {
    initFlag3 |= (1 << lu_1)
    changeFlag3 |= (1 << lu_1)
    _v3_lu = v
  }

  def v3_lv_=(v: Float): Unit = {
    initFlag3 |= (1 << lv_1)
    changeFlag3 |= (1 << lv_1)
    _v3_lv = v
  }

  def v3_nx_=(v: Float): Unit = {
    initFlag3 |= (1 << nx_1)
    changeFlag3 |= (1 << nx_1)
    _v3_nx = v
  }

  def v3_ny_=(v: Float): Unit = {
    initFlag3 |= (1 << ny_1)
    changeFlag3 |= (1 << ny_1)
    _v3_ny = v
  }

  def v3_nz_=(v: Float): Unit = {
    initFlag3 |= (1 << nz_1)
    changeFlag3 |= (1 << nz_1)
    _v3_nz = v
  }

  def v3_p_=(v: Float): Unit = {
    initFlag3 |= (1 << p_1)
    changeFlag3 |= (1 << p_1)
    _v3_p = v
  }

  def v4_x_=(v: Float): Unit = {
    initFlag4 |= (1 << x_1)
    changeFlag4 |= (1 << x_1)
    _v4_x = v
  }

  def v4_y_=(v: Float): Unit = {
    initFlag4 |= (1 << y_1)
    changeFlag4 |= (1 << y_1)
    _v4_y = v
  }

  def v4_z_=(v: Float): Unit = {
    initFlag4 |= (1 << z_1)
    changeFlag4 |= (1 << z_1)
    _v4_z = v
  }

  def v4_r_=(v: Float): Unit = {
    initFlag4 |= (1 << r_1)
    changeFlag4 |= (1 << r_1)
    _v4_r = v
  }

  def v4_g_=(v: Float): Unit = {
    initFlag4 |= (1 << g_1)
    changeFlag4 |= (1 << g_1)
    _v4_g = v
  }

  def v4_b_=(v: Float): Unit = {
    initFlag4 |= (1 << b_1)
    changeFlag4 |= (1 << b_1)
    _v4_b = v
  }

  def v4_a_=(v: Float): Unit = {
    initFlag4 |= (1 << a_1)
    changeFlag4 |= (1 << a_1)
    _v4_a = v
  }

  def v4_u_=(v: Float): Unit = {
    initFlag4 |= (1 << u_1)
    changeFlag4 |= (1 << u_1)
    _v4_u = v
  }

  def v4_v_=(v: Float): Unit = {
    initFlag4 |= (1 << v_1)
    changeFlag4 |= (1 << v_1)
    _v4_v = v
  }

  def v4_lu_=(v: Float): Unit = {
    initFlag4 |= (1 << lu_1)
    changeFlag4 |= (1 << lu_1)
    _v4_lu = v
  }

  def v4_lv_=(v: Float): Unit = {
    initFlag4 |= (1 << lv_1)
    changeFlag4 |= (1 << lv_1)
    _v4_lv = v
  }

  def v4_nx_=(v: Float): Unit = {
    initFlag4 |= (1 << nx_1)
    changeFlag4 |= (1 << nx_1)
    _v4_nx = v
  }

  def v4_ny_=(v: Float): Unit = {
    initFlag4 |= (1 << ny_1)
    changeFlag4 |= (1 << ny_1)
    _v4_ny = v
  }

  def v4_nz_=(v: Float): Unit = {
    initFlag4 |= (1 << nz_1)
    changeFlag4 |= (1 << nz_1)
    _v4_nz = v
  }

  def v4_p_=(v: Float): Unit = {
    initFlag4 |= (1 << p_1)
    changeFlag4 |= (1 << p_1)
    _v4_p = v
  }

}

object UnpackedQuad {

  def apply(quad: BakedQuad): UnpackedQuad =
    new UnpackedQuad(quad, quad.getFace, quad.getSprite, quad.getTintIndex, quad.shouldApplyDiffuseLighting())


  private val unpackedQuad = UnpackedQuad(???)
  //unpackedQuad.v2_x += 1
  unpackedQuad.toRawArray
}
