package ru.mousecray.endmagic.util.render.endothermic.quad

import ru.mousecray.endmagic.util.render.endothermic.quad.BaseUnpackedQuad.defaultValue

//for java
class ReconstructBuilder[Self <: BaseUnpackedQuad] {

  private var quad: BaseUnpackedQuad = _

  private def init(quad: BaseUnpackedQuad): Unit = {
    this.quad = quad
    _v1_x = quad._v1_x
    _v1_y = quad._v1_y
    _v1_z = quad._v1_z
    _v1_r = quad._v1_r
    _v1_g = quad._v1_g
    _v1_b = quad._v1_b
    _v1_a = quad._v1_a
    _v1_u = quad._v1_u
    _v1_v = quad._v1_v
    _v1_lu = quad._v1_lu
    _v1_lv = quad._v1_lv
    _v1_nx = quad._v1_nx
    _v1_ny = quad._v1_ny
    _v1_nz = quad._v1_nz
    _v1_p = quad._v1_p

    _v2_x = quad._v2_x
    _v2_y = quad._v2_y
    _v2_z = quad._v2_z
    _v2_r = quad._v2_r
    _v2_g = quad._v2_g
    _v2_b = quad._v2_b
    _v2_a = quad._v2_a
    _v2_u = quad._v2_u
    _v2_v = quad._v2_v
    _v2_lu = quad._v2_lu
    _v2_lv = quad._v2_lv
    _v2_nx = quad._v2_nx
    _v2_ny = quad._v2_ny
    _v2_nz = quad._v2_nz
    _v2_p = quad._v2_p

    _v3_x = quad._v3_x
    _v3_y = quad._v3_y
    _v3_z = quad._v3_z
    _v3_r = quad._v3_r
    _v3_g = quad._v3_g
    _v3_b = quad._v3_b
    _v3_a = quad._v3_a
    _v3_u = quad._v3_u
    _v3_v = quad._v3_v
    _v3_lu = quad._v3_lu
    _v3_lv = quad._v3_lv
    _v3_nx = quad._v3_nx
    _v3_ny = quad._v3_ny
    _v3_nz = quad._v3_nz
    _v3_p = quad._v3_p

    _v4_x = quad._v4_x
    _v4_y = quad._v4_y
    _v4_z = quad._v4_z
    _v4_r = quad._v4_r
    _v4_g = quad._v4_g
    _v4_b = quad._v4_b
    _v4_a = quad._v4_a
    _v4_u = quad._v4_u
    _v4_v = quad._v4_v
    _v4_lu = quad._v4_lu
    _v4_lv = quad._v4_lv
    _v4_nx = quad._v4_nx
    _v4_ny = quad._v4_ny
    _v4_nz = quad._v4_nz
    _v4_p = quad._v4_p
  }

  private def clear(): Unit = {
    quad = null
  }

  private var _v1_x: Float = defaultValue

  def v1_x(v: Float): this.type = {
    _v1_x = v
    this
  }

  private var _v1_y: Float = defaultValue

  def v1_y(v: Float): this.type = {
    _v1_y = v
    this
  }

  private var _v1_z: Float = defaultValue

  def v1_z(v: Float): this.type = {
    _v1_z = v
    this
  }

  private var _v1_r: Float = defaultValue

  def v1_r(v: Float): this.type = {
    _v1_r = v
    this
  }

  private var _v1_g: Float = defaultValue

  def v1_g(v: Float): this.type = {
    _v1_g = v
    this
  }

  private var _v1_b: Float = defaultValue

  def v1_b(v: Float): this.type = {
    _v1_b = v
    this
  }

  private var _v1_a: Float = defaultValue

  def v1_a(v: Float): this.type = {
    _v1_a = v
    this
  }

  private var _v1_u: Float = defaultValue

  def v1_u(v: Float): this.type = {
    _v1_u = v
    this
  }

  private var _v1_v: Float = defaultValue

  def v1_v(v: Float): this.type = {
    _v1_v = v
    this
  }

  private var _v1_lu: Float = defaultValue

  def v1_lu(v: Float): this.type = {
    _v1_lu = v
    this
  }

  private var _v1_lv: Float = defaultValue

  def v1_lv(v: Float): this.type = {
    _v1_lv = v
    this
  }

  private var _v1_nx: Float = defaultValue

  def v1_nx(v: Float): this.type = {
    _v1_nx = v
    this
  }

  private var _v1_ny: Float = defaultValue

  def v1_ny(v: Float): this.type = {
    _v1_ny = v
    this
  }

  private var _v1_nz: Float = defaultValue

  def v1_nz(v: Float): this.type = {
    _v1_nz = v
    this
  }

  private var _v1_p: Float = defaultValue

  def v1_p(v: Float): this.type = {
    _v1_p = v
    this
  }

  private var _v2_x: Float = defaultValue

  def v2_x(v: Float): this.type = {
    _v2_x = v
    this
  }

  private var _v2_y: Float = defaultValue

  def v2_y(v: Float): this.type = {
    _v2_y = v
    this
  }

  private var _v2_z: Float = defaultValue

  def v2_z(v: Float): this.type = {
    _v2_z = v
    this
  }

  private var _v2_r: Float = defaultValue

  def v2_r(v: Float): this.type = {
    _v2_r = v
    this
  }

  private var _v2_g: Float = defaultValue

  def v2_g(v: Float): this.type = {
    _v2_g = v
    this
  }

  private var _v2_b: Float = defaultValue

  def v2_b(v: Float): this.type = {
    _v2_b = v
    this
  }

  private var _v2_a: Float = defaultValue

  def v2_a(v: Float): this.type = {
    _v2_a = v
    this
  }

  private var _v2_u: Float = defaultValue

  def v2_u(v: Float): this.type = {
    _v2_u = v
    this
  }

  private var _v2_v: Float = defaultValue

  def v2_v(v: Float): this.type = {
    _v2_v = v
    this
  }

  private var _v2_lu: Float = defaultValue

  def v2_lu(v: Float): this.type = {
    _v2_lu = v
    this
  }

  private var _v2_lv: Float = defaultValue

  def v2_lv(v: Float): this.type = {
    _v2_lv = v
    this
  }

  private var _v2_nx: Float = defaultValue

  def v2_nx(v: Float): this.type = {
    _v2_nx = v
    this
  }

  private var _v2_ny: Float = defaultValue

  def v2_ny(v: Float): this.type = {
    _v2_ny = v
    this
  }

  private var _v2_nz: Float = defaultValue

  def v2_nz(v: Float): this.type = {
    _v2_nz = v
    this
  }

  private var _v2_p: Float = defaultValue

  def v2_p(v: Float): this.type = {
    _v2_p = v
    this
  }

  private var _v3_x: Float = defaultValue

  def v3_x(v: Float): this.type = {
    _v3_x = v
    this
  }

  private var _v3_y: Float = defaultValue

  def v3_y(v: Float): this.type = {
    _v3_y = v
    this
  }

  private var _v3_z: Float = defaultValue

  def v3_z(v: Float): this.type = {
    _v3_z = v
    this
  }

  private var _v3_r: Float = defaultValue

  def v3_r(v: Float): this.type = {
    _v3_r = v
    this
  }

  private var _v3_g: Float = defaultValue

  def v3_g(v: Float): this.type = {
    _v3_g = v
    this
  }

  private var _v3_b: Float = defaultValue

  def v3_b(v: Float): this.type = {
    _v3_b = v
    this
  }

  private var _v3_a: Float = defaultValue

  def v3_a(v: Float): this.type = {
    _v3_a = v
    this
  }

  private var _v3_u: Float = defaultValue

  def v3_u(v: Float): this.type = {
    _v3_u = v
    this
  }

  private var _v3_v: Float = defaultValue

  def v3_v(v: Float): this.type = {
    _v3_v = v
    this
  }

  private var _v3_lu: Float = defaultValue

  def v3_lu(v: Float): this.type = {
    _v3_lu = v
    this
  }

  private var _v3_lv: Float = defaultValue

  def v3_lv(v: Float): this.type = {
    _v3_lv = v
    this
  }

  private var _v3_nx: Float = defaultValue

  def v3_nx(v: Float): this.type = {
    _v3_nx = v
    this
  }

  private var _v3_ny: Float = defaultValue

  def v3_ny(v: Float): this.type = {
    _v3_ny = v
    this
  }

  private var _v3_nz: Float = defaultValue

  def v3_nz(v: Float): this.type = {
    _v3_nz = v
    this
  }

  private var _v3_p: Float = defaultValue

  def v3_p(v: Float): this.type = {
    _v3_p = v
    this
  }

  private var _v4_x: Float = defaultValue

  def v4_x(v: Float): this.type = {
    _v4_x = v
    this
  }

  private var _v4_y: Float = defaultValue

  def v4_y(v: Float): this.type = {
    _v4_y = v
    this
  }

  private var _v4_z: Float = defaultValue

  def v4_z(v: Float): this.type = {
    _v4_z = v
    this
  }

  private var _v4_r: Float = defaultValue

  def v4_r(v: Float): this.type = {
    _v4_r = v
    this
  }

  private var _v4_g: Float = defaultValue

  def v4_g(v: Float): this.type = {
    _v4_g = v
    this
  }

  private var _v4_b: Float = defaultValue

  def v4_b(v: Float): this.type = {
    _v4_b = v
    this
  }

  private var _v4_a: Float = defaultValue

  def v4_a(v: Float): this.type = {
    _v4_a = v
    this
  }

  private var _v4_u: Float = defaultValue

  def v4_u(v: Float): this.type = {
    _v4_u = v
    this
  }

  private var _v4_v: Float = defaultValue

  def v4_v(v: Float): this.type = {
    _v4_v = v
    this
  }

  private var _v4_lu: Float = defaultValue

  def v4_lu(v: Float): this.type = {
    _v4_lu = v
    this
  }

  private var _v4_lv: Float = defaultValue

  def v4_lv(v: Float): this.type = {
    _v4_lv = v
    this
  }

  private var _v4_nx: Float = defaultValue

  def v4_nx(v: Float): this.type = {
    _v4_nx = v
    this
  }

  private var _v4_ny: Float = defaultValue

  def v4_ny(v: Float): this.type = {
    _v4_ny = v
    this
  }

  private var _v4_nz: Float = defaultValue

  def v4_nz(v: Float): this.type = {
    _v4_nz = v
    this
  }

  private var _v4_p: Float = defaultValue

  def v4_p(v: Float): this.type = {
    _v4_p = v
    this
  }

  def build: Self = {
    val r = quad.reconstruct(
      _v1_x, _v1_y, _v1_z, _v1_r, _v1_g, _v1_b, _v1_a, _v1_u, _v1_v, _v1_lu, _v1_lv, _v1_nx, _v1_ny, _v1_nz, _v1_p,
      _v2_x, _v2_y, _v2_z, _v2_r, _v2_g, _v2_b, _v2_a, _v2_u, _v2_v, _v2_lu, _v2_lv, _v2_nx, _v2_ny, _v2_nz, _v2_p,
      _v3_x, _v3_y, _v3_z, _v3_r, _v3_g, _v3_b, _v3_a, _v3_u, _v3_v, _v3_lu, _v3_lv, _v3_nx, _v3_ny, _v3_nz, _v3_p,
      _v4_x, _v4_y, _v4_z, _v4_r, _v4_g, _v4_b, _v4_a, _v4_u, _v4_v, _v4_lu, _v4_lv, _v4_nx, _v4_ny, _v4_nz, _v4_p
    ).asInstanceOf[Self]
    ReconstructBuilder.returnToPool(this)
    r
  }

}

object ReconstructBuilder {
  private val free = new collection.mutable.HashSet[ReconstructBuilder[_]]()

  expandPool(50)

  private def returnToPool(value: ReconstructBuilder[_]) = {
    value.clear()
    free += value
  }

  def getPooledFor[Self <: BaseUnpackedQuad](quad: Self): ReconstructBuilder[Self] = {
    val builder =
      if (free.nonEmpty) {
        val builder = free.head
        free -= builder
        builder.asInstanceOf[ReconstructBuilder[Self]]
      } else
        new ReconstructBuilder[Self]

    builder.init(quad)
    builder
  }

  def expandPool(count: Int): Unit = {
    free ++= (1 to count map (_ => new ReconstructBuilder))
  }

}
