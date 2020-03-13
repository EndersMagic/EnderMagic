package ru.mousecray.endmagic.util.render.endothermic.ops

import ru.mousecray.endmagic.util.render.endothermic.BaseUnpackedQuad

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

  def rotate(O: Float, x: Float, y: Float, z: Float): Self = {
    val matrix = Seq(
      Seq(cos(O) + x * x * (1 - cos(O)), x * y * (1 - cos(O)) - z * sin(O), x * z * (1 - cos(O)) + y * sin(O)),
      Seq(y * x * (1 - cos(O)) + z * sin(O), cos(O) + y * y * (1 - cos(O)), y * z * (1 - cos(O)) - x * sin(O)),
      Seq(z * x * (1 - cos(O)) - y * sin(O), z * y * (1 - cos(O)) + x * sin(O), cos(O) + z * z * (1 - cos(O)))
    )

    def calcVertex(v: Seq[Float]) =
      matrix.map(line => line.zip(v).map { case (a, b) => a * b }.sum).map(_.toFloat)


    val nv1 = calcVertex(Seq(v1_x, v1_y, v1_z))
    val nv2 = calcVertex(Seq(v2_x, v2_y, v2_z))
    val nv3 = calcVertex(Seq(v3_x, v3_y, v3_z))
    val nv4 = calcVertex(Seq(v4_x, v4_y, v4_z))

    reconstruct(
      v1_x = nv1(0), v1_y = nv1(1), v1_z = nv1(2),
      v2_x = nv2(0), v2_y = nv2(1), v2_z = nv2(2),
      v3_x = nv3(0), v3_y = nv3(1), v3_z = nv3(2),
      v4_x = nv4(0), v4_y = nv4(1), v4_z = nv4(2)
    )
  }

  def slice(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float, x4: Float, y4: Float): Self = {
  def sliceRect(x1: Float, y1: Float, x3: Float, y3: Float): Self =
    slice(x1, y1, x3, y1, x3, y3, x3, y1)


    //Thx frobeniusfg for help with this
    def calcAttribute(x: Float, y: Float, v1: Float, v2: Float, v3: Float, v4: Float): Float = {
      val a = x * y
      a *v1 - a *v2 + a *v3 - a *v4 - v1 *x - v1 *y + v1 + v2 *x + v4 *y
    }

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
}
