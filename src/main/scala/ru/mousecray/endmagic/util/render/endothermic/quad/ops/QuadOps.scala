package ru.mousecray.endmagic.util.render.endothermic.quad.ops

import ru.mousecray.endmagic.util.render.endothermic.quad.BaseUnpackedQuad

import scala.math._

trait QuadOps {
  this: BaseUnpackedQuad =>

  def translate(x: Float, y: Float, z: Float): Self =
    reconstruct(
      v1_x = v1_x + x, v1_y = v1_y + y, v1_z = v1_z + z,
      v2_x = v2_x + x, v2_y = v2_y + y, v2_z = v2_z + z,
      v3_x = v3_x + x, v3_y = v3_y + y, v3_z = v3_z + z,
      v4_x = v4_x + x, v4_y = v4_y + y, v4_z = v4_z + z
    )

  //todo
  def scale(factor: Float): Self =
    reconstruct(
      v1_x = v1_x * factor, v1_y = v1_y * factor, v1_z = v1_z * factor,
      v2_x = v2_x * factor, v2_y = v2_y * factor, v2_z = v2_z * factor,
      v3_x = v3_x * factor, v3_y = v3_y * factor, v3_z = v3_z * factor,
      v4_x = v4_x * factor, v4_y = v4_y * factor, v4_z = v4_z * factor
    )

  def rotate(angle: Float, x: Float, y: Float, z: Float): Self = {
    val m11 = cos(angle) + x * x * (1 - cos(angle))
    val m12 = x * y * (1 - cos(angle)) - z * sin(angle)
    val m13 = x * z * (1 - cos(angle)) + y * sin(angle)

    val m21 = y * x * (1 - cos(angle)) + z * sin(angle)
    val m22 = cos(angle) + y * y * (1 - cos(angle))
    val m23 = y * z * (1 - cos(angle)) - x * sin(angle)

    val m31 = z * x * (1 - cos(angle)) - y * sin(angle)
    val m32 = z * y * (1 - cos(angle)) + x * sin(angle)
    val m33 = cos(angle) + z * z * (1 - cos(angle))

    @inline def mul_sum(v1_1: Double, v1_2: Double, v1_3: Double, v2_1: Float, v2_2: Float, v2_3: Float) =
      (v1_1 * v2_1 + v1_2 * v2_2 + v1_3 * v2_3).toFloat


    val nv1_x = mul_sum(m11, m12, m13, v1_x, v1_y, v1_z)
    val nv1_y = mul_sum(m21, m22, m23, v1_x, v1_y, v1_z)
    val nv1_z = mul_sum(m31, m32, m33, v1_x, v1_y, v1_z)

    val nv2_x = mul_sum(m11, m12, m13, v2_x, v2_y, v2_z)
    val nv2_y = mul_sum(m21, m22, m23, v2_x, v2_y, v2_z)
    val nv2_z = mul_sum(m31, m32, m33, v2_x, v2_y, v2_z)

    val nv3_x = mul_sum(m11, m12, m13, v3_x, v3_y, v3_z)
    val nv3_y = mul_sum(m21, m22, m23, v3_x, v3_y, v3_z)
    val nv3_z = mul_sum(m31, m32, m33, v3_x, v3_y, v3_z)

    val nv4_x = mul_sum(m11, m12, m13, v4_x, v4_y, v4_z)
    val nv4_y = mul_sum(m21, m22, m23, v4_x, v4_y, v4_z)
    val nv4_z = mul_sum(m31, m32, m33, v4_x, v4_y, v4_z)

    reconstruct(
      v1_x = nv1_x, v1_y = nv1_y, v1_z = nv1_z,
      v2_x = nv2_x, v2_y = nv2_y, v2_z = nv2_z,
      v3_x = nv3_x, v3_y = nv3_y, v3_z = nv3_z,
      v4_x = nv4_x, v4_y = nv4_y, v4_z = nv4_z
    )
  }

  def reverse: Self = {
    reconstruct(
      v4_x, v4_y, v4_z, v4_r, v4_g, v4_b, v4_a, v4_u, v4_v, v4_lu, v4_lv, v4_nx, v4_ny, v4_nz, v4_p,
      v3_x, v3_y, v3_z, v3_r, v3_g, v3_b, v3_a, v3_u, v3_v, v3_lu, v3_lv, v3_nx, v3_ny, v3_nz, v3_p,
      v2_x, v2_y, v2_z, v2_r, v2_g, v2_b, v2_a, v2_u, v2_v, v2_lu, v2_lv, v2_nx, v2_ny, v2_nz, v2_p,
      v1_x, v1_y, v1_z, v1_r, v1_g, v1_b, v1_a, v1_u, v1_v, v1_lu, v1_lv, v1_nx, v1_ny, v1_nz, v1_p
    )
  }

  def recalculateNormals: Self = {
    val (ax, ay, az) = (v4_x - v1_x, v4_y - v1_y, v4_z - v1_z)
    val (bx, by, bz) = (v2_x - v1_x, v2_y - v1_y, v2_z - v1_z)

    val (tnx, tny, tnz) = (ay * bz - az * by, az * bx - ax * bz, ax * by - ay * bx)
    val norm = (1.0 / sqrt(tnx * tnx + tny * tny + tnz * tnz)).toFloat
    val (nx, ny, nz) = (tnx * norm, tny * norm, tnz * norm)
    reconstruct(
      v1_nx = nx, v1_ny = ny, v1_nz = nz,
      v2_nx = nx, v2_ny = ny, v2_nz = nz,
      v3_nx = nx, v3_ny = ny, v3_nz = nz,
      v4_nx = nx, v4_ny = ny, v4_nz = nz
    )
  }

  def sliceRect(x1: Float, y1: Float, x2: Float, y2: Float): Self =
    slice(
      x1, y1,
      x2, y1,
      x2, y2,
      x1, y2)


  private def cyclicNormalize2(v: Double): Double =
    if (v < 0) 1 + (v - v.toInt)
    else if (v > 1) v - v.toInt
    else v

  def slice(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double, x4: Double, y4: Double): Self =
    slice1(cyclicNormalize2(x1), cyclicNormalize2(y1), cyclicNormalize2(x2), cyclicNormalize2(y2), cyclicNormalize2(x3), cyclicNormalize2(y3), cyclicNormalize2(x4), cyclicNormalize2(y4))

  private def slice1(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double, x4: Double, y4: Double): Self = {


    reconstruct(
      v1_x = calcAttribute(x1, y1,
        v1_x, v2_x, v3_x, v4_x),
      v1_y = calcAttribute(x1, y1,
        v1_y, v2_y, v3_y, v4_y),
      v1_z = calcAttribute(x1, y1,
        v1_z, v2_z, v3_z, v4_z),
      v1_r = calcAttribute(x1, y1,
        v1_r, v2_r, v3_r, v4_r),
      v1_g = calcAttribute(x1, y1,
        v1_g, v2_g, v3_g, v4_g),
      v1_b = calcAttribute(x1, y1,
        v1_b, v2_b, v3_b, v4_b),
      v1_a = calcAttribute(x1, y1,
        v1_a, v2_a, v3_a, v4_a),
      v1_u = calcAttribute(x1, y1,
        v1_u, v2_u, v3_u, v4_u),
      v1_v = calcAttribute(x1, y1,
        v1_v, v2_v, v3_v, v4_v),
      v1_lu = calcAttribute(x1, y1,
        v1_lu, v2_lu, v3_lu, v4_lu),
      v1_lv = calcAttribute(x1, y1,
        v1_lv, v2_lv, v3_lv, v4_lv),
      v1_nx = calcAttribute(x1, y1,
        v1_nx, v2_nx, v3_nx, v4_nx),
      v1_ny = calcAttribute(x1, y1,
        v1_ny, v2_ny, v3_ny, v4_ny),
      v1_nz = calcAttribute(x1, y1,
        v1_nz, v2_nz, v3_nz, v4_nz),
      v1_p = calcAttribute(x1, y1,
        v1_p, v2_p, v3_p, v4_p),

      v2_x = calcAttribute(x2, y2,
        v1_x, v2_x, v3_x, v4_x),
      v2_y = calcAttribute(x2, y2,
        v1_y, v2_y, v3_y, v4_y),
      v2_z = calcAttribute(x2, y2,
        v1_z, v2_z, v3_z, v4_z),
      v2_r = calcAttribute(x2, y2,
        v1_r, v2_r, v3_r, v4_r),
      v2_g = calcAttribute(x2, y2,
        v1_g, v2_g, v3_g, v4_g),
      v2_b = calcAttribute(x2, y2,
        v1_b, v2_b, v3_b, v4_b),
      v2_a = calcAttribute(x2, y2,
        v1_a, v2_a, v3_a, v4_a),
      v2_u = calcAttribute(x2, y2,
        v1_u, v2_u, v3_u, v4_u),
      v2_v = calcAttribute(x2, y2,
        v1_v, v2_v, v3_v, v4_v),
      v2_lu = calcAttribute(x2, y2,
        v1_lu, v2_lu, v3_lu, v4_lu),
      v2_lv = calcAttribute(x2, y2,
        v1_lv, v2_lv, v3_lv, v4_lv),
      v2_nx = calcAttribute(x2, y2,
        v1_nx, v2_nx, v3_nx, v4_nx),
      v2_ny = calcAttribute(x2, y2,
        v1_ny, v2_ny, v3_ny, v4_ny),
      v2_nz = calcAttribute(x2, y2,
        v1_nz, v2_nz, v3_nz, v4_nz),
      v2_p = calcAttribute(x2, y2,
        v1_p, v2_p, v3_p, v4_p),

      v3_x = calcAttribute(x3, y3,
        v1_x, v2_x, v3_x, v4_x),
      v3_y = calcAttribute(x3, y3,
        v1_y, v2_y, v3_y, v4_y),
      v3_z = calcAttribute(x3, y3,
        v1_z, v2_z, v3_z, v4_z),
      v3_r = calcAttribute(x3, y3,
        v1_r, v2_r, v3_r, v4_r),
      v3_g = calcAttribute(x3, y3,
        v1_g, v2_g, v3_g, v4_g),
      v3_b = calcAttribute(x3, y3,
        v1_b, v2_b, v3_b, v4_b),
      v3_a = calcAttribute(x3, y3,
        v1_a, v2_a, v3_a, v4_a),
      v3_u = calcAttribute(x3, y3,
        v1_u, v2_u, v3_u, v4_u),
      v3_v = calcAttribute(x3, y3,
        v1_v, v2_v, v3_v, v4_v),
      v3_lu = calcAttribute(x3, y3,
        v1_lu, v2_lu, v3_lu, v4_lu),
      v3_lv = calcAttribute(x3, y3,
        v1_lv, v2_lv, v3_lv, v4_lv),
      v3_nx = calcAttribute(x3, y3,
        v1_nx, v2_nx, v3_nx, v4_nx),
      v3_ny = calcAttribute(x3, y3,
        v1_ny, v2_ny, v3_ny, v4_ny),
      v3_nz = calcAttribute(x3, y3,
        v1_nz, v2_nz, v3_nz, v4_nz),
      v3_p = calcAttribute(x3, y3,
        v1_p, v2_p, v3_p, v4_p),

      v4_x = calcAttribute(x4, y4,
        v1_x, v2_x, v3_x, v4_x),
      v4_y = calcAttribute(x4, y4,
        v1_y, v2_y, v3_y, v4_y),
      v4_z = calcAttribute(x4, y4,
        v1_z, v2_z, v3_z, v4_z),
      v4_r = calcAttribute(x4, y4,
        v1_r, v2_r, v3_r, v4_r),
      v4_g = calcAttribute(x4, y4,
        v1_g, v2_g, v3_g, v4_g),
      v4_b = calcAttribute(x4, y4,
        v1_b, v2_b, v3_b, v4_b),
      v4_a = calcAttribute(x4, y4,
        v1_a, v2_a, v3_a, v4_a),
      v4_u = calcAttribute(x4, y4,
        v1_u, v2_u, v3_u, v4_u),
      v4_v = calcAttribute(x4, y4,
        v1_v, v2_v, v3_v, v4_v),
      v4_lu = calcAttribute(x4, y4,
        v1_lu, v2_lu, v3_lu, v4_lu),
      v4_lv = calcAttribute(x4, y4,
        v1_lv, v2_lv, v3_lv, v4_lv),
      v4_nx = calcAttribute(x4, y4,
        v1_nx, v2_nx, v3_nx, v4_nx),
      v4_ny = calcAttribute(x4, y4,
        v1_ny, v2_ny, v3_ny, v4_ny),
      v4_nz = calcAttribute(x4, y4,
        v1_nz, v2_nz, v3_nz, v4_nz),
      v4_p = calcAttribute(x4, y4,
        v1_p, v2_p, v3_p, v4_p)


    )
  }

  /*
  Only for standard minecraft quads
   */
  def trivialSlice(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double, x4: Double, y4: Double): Self = {
    reconstruct(
      v1_x = calcAttribute(x1, y1,
        v1_x, v2_x, v3_x, v4_x),
      v1_y = calcAttribute(x1, y1,
        v1_y, v2_y, v3_y, v4_y),
      v1_z = calcAttribute(x1, y1,
        v1_z, v2_z, v3_z, v4_z),
      v1_r = calcAttribute(x1, y1,
        v1_r, v2_r, v3_r, v4_r),
      v1_g = calcAttribute(x1, y1,
        v1_g, v2_g, v3_g, v4_g),
      v1_b = calcAttribute(x1, y1,
        v1_b, v2_b, v3_b, v4_b),
      v1_a = calcAttribute(x1, y1,
        v1_a, v2_a, v3_a, v4_a),
      v1_u = calcU(x1, y1,
        v1_u, v2_u, v3_u, v4_u),
      v1_v = calcV(x1, y1,
        v1_v, v2_v, v3_v, v4_v),
      v1_lu = calcAttribute(x1, y1,
        v1_lu, v2_lu, v3_lu, v4_lu),
      v1_lv = calcAttribute(x1, y1,
        v1_lv, v2_lv, v3_lv, v4_lv),
      v1_nx = calcAttribute(x1, y1,
        v1_nx, v2_nx, v3_nx, v4_nx),
      v1_ny = calcAttribute(x1, y1,
        v1_ny, v2_ny, v3_ny, v4_ny),
      v1_nz = calcAttribute(x1, y1,
        v1_nz, v2_nz, v3_nz, v4_nz),
      v1_p = calcAttribute(x1, y1,
        v1_p, v2_p, v3_p, v4_p),

      v2_x = calcAttribute(x2, y2,
        v1_x, v2_x, v3_x, v4_x),
      v2_y = calcAttribute(x2, y2,
        v1_y, v2_y, v3_y, v4_y),
      v2_z = calcAttribute(x2, y2,
        v1_z, v2_z, v3_z, v4_z),
      v2_r = calcAttribute(x2, y2,
        v1_r, v2_r, v3_r, v4_r),
      v2_g = calcAttribute(x2, y2,
        v1_g, v2_g, v3_g, v4_g),
      v2_b = calcAttribute(x2, y2,
        v1_b, v2_b, v3_b, v4_b),
      v2_a = calcAttribute(x2, y2,
        v1_a, v2_a, v3_a, v4_a),
      v2_u = calcU(x2, y2,
        v1_u, v2_u, v3_u, v4_u),
      v2_v = calcV(x2, y2,
        v1_v, v2_v, v3_v, v4_v),
      v2_lu = calcAttribute(x2, y2,
        v1_lu, v2_lu, v3_lu, v4_lu),
      v2_lv = calcAttribute(x2, y2,
        v1_lv, v2_lv, v3_lv, v4_lv),
      v2_nx = calcAttribute(x2, y2,
        v1_nx, v2_nx, v3_nx, v4_nx),
      v2_ny = calcAttribute(x2, y2,
        v1_ny, v2_ny, v3_ny, v4_ny),
      v2_nz = calcAttribute(x2, y2,
        v1_nz, v2_nz, v3_nz, v4_nz),
      v2_p = calcAttribute(x2, y2,
        v1_p, v2_p, v3_p, v4_p),

      v3_x = calcAttribute(x3, y3,
        v1_x, v2_x, v3_x, v4_x),
      v3_y = calcAttribute(x3, y3,
        v1_y, v2_y, v3_y, v4_y),
      v3_z = calcAttribute(x3, y3,
        v1_z, v2_z, v3_z, v4_z),
      v3_r = calcAttribute(x3, y3,
        v1_r, v2_r, v3_r, v4_r),
      v3_g = calcAttribute(x3, y3,
        v1_g, v2_g, v3_g, v4_g),
      v3_b = calcAttribute(x3, y3,
        v1_b, v2_b, v3_b, v4_b),
      v3_a = calcAttribute(x3, y3,
        v1_a, v2_a, v3_a, v4_a),
      v3_u = calcU(x3, y3,
        v1_u, v2_u, v3_u, v4_u),
      v3_v = calcV(x3, y3,
        v1_v, v2_v, v3_v, v4_v),
      v3_lu = calcAttribute(x3, y3,
        v1_lu, v2_lu, v3_lu, v4_lu),
      v3_lv = calcAttribute(x3, y3,
        v1_lv, v2_lv, v3_lv, v4_lv),
      v3_nx = calcAttribute(x3, y3,
        v1_nx, v2_nx, v3_nx, v4_nx),
      v3_ny = calcAttribute(x3, y3,
        v1_ny, v2_ny, v3_ny, v4_ny),
      v3_nz = calcAttribute(x3, y3,
        v1_nz, v2_nz, v3_nz, v4_nz),
      v3_p = calcAttribute(x3, y3,
        v1_p, v2_p, v3_p, v4_p),

      v4_x = calcAttribute(x4, y4,
        v1_x, v2_x, v3_x, v4_x),
      v4_y = calcAttribute(x4, y4,
        v1_y, v2_y, v3_y, v4_y),
      v4_z = calcAttribute(x4, y4,
        v1_z, v2_z, v3_z, v4_z),
      v4_r = calcAttribute(x4, y4,
        v1_r, v2_r, v3_r, v4_r),
      v4_g = calcAttribute(x4, y4,
        v1_g, v2_g, v3_g, v4_g),
      v4_b = calcAttribute(x4, y4,
        v1_b, v2_b, v3_b, v4_b),
      v4_a = calcAttribute(x4, y4,
        v1_a, v2_a, v3_a, v4_a),
      v4_u = calcU(x4, y4,
        v1_u, v2_u, v3_u, v4_u),
      v4_v = calcV(x4, y4,
        v1_v, v2_v, v3_v, v4_v),
      v4_lu = calcAttribute(x4, y4,
        v1_lu, v2_lu, v3_lu, v4_lu),
      v4_lv = calcAttribute(x4, y4,
        v1_lv, v2_lv, v3_lv, v4_lv),
      v4_nx = calcAttribute(x4, y4,
        v1_nx, v2_nx, v3_nx, v4_nx),
      v4_ny = calcAttribute(x4, y4,
        v1_ny, v2_ny, v3_ny, v4_ny),
      v4_nz = calcAttribute(x4, y4,
        v1_nz, v2_nz, v3_nz, v4_nz),
      v4_p = calcAttribute(x4, y4,
        v1_p, v2_p, v3_p, v4_p)


    )
  }

  def trivialSliceRect(x1: Float, y1: Float, x2: Float, y2: Float): Self =
    trivialSlice(
      x1, y1,
      x2, y1,
      x2, y2,
      x1, y2)


  //Thx frobeniusfg for help with this
  private def calcAttribute(x: Double, y: Double, v1: Double, v2: Double, v3: Double, v4: Double): Float = {
    val a = x * y
    a * v1 - a * v2 + a * v3 - a * v4 - v1 * x - v1 * y + v1 + v2 * x + v4 * y
  }.toFloat


  private def calcU(x: Double, y: Double, u1: Float, u2: Float, u3: Float, u4: Float): Float = {
    val fl = y.toFloat * (quad.getSprite.getMaxU - quad.getSprite.getMinU) + quad.getSprite.getMinU
    fl
  }

  private def calcV(x: Double, y: Double, v1: Float, v2: Float, v3: Float, v4: Float): Float = {
    val fl = x.toFloat * (quad.getSprite.getMaxV - quad.getSprite.getMinV) + quad.getSprite.getMinV
    fl
  }
}
