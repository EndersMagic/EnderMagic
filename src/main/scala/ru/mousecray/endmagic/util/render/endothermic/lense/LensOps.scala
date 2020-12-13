package ru.mousecray.endmagic.util.render.endothermic.lense

import ru.mousecray.endmagic.util.render.endothermic.format.AttributeRepresentation._

import scala.math.{cos, sin}

object LensOps {

  val allX = Seq(x_1, x_2, x_3, x_4)
  val allY = Seq(y_1, y_2, y_3, y_4)
  val allZ = Seq(z_1, z_2, z_3, z_4)

  def translate(lense: QuadAttributeLens, vertexData: Array[Int], x: Float, y: Float, z: Float): Unit = {
    import lense._
    allX.foreach(a => set(a, vertexData, get(a, vertexData) + x))
    allY.foreach(a => set(a, vertexData, get(a, vertexData) + y))
    allZ.foreach(a => set(a, vertexData, get(a, vertexData) + z))
  }

  def scale(lense: QuadAttributeLens, vertexData: Array[Int], x: Float, y: Float, z: Float): Unit = {
    import lense._
    allX.foreach(a => set(a, vertexData, get(a, vertexData) * x))
    allY.foreach(a => set(a, vertexData, get(a, vertexData) * y))
    allZ.foreach(a => set(a, vertexData, get(a, vertexData) * z))
  }

  def scale(lense: QuadAttributeLens, vertexData: Array[Int], angle: Float, x: Float, y: Float, z: Float): Unit = {
    import lense._
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


    val nv1_x = mul_sum(m11, m12, m13, get(x_1, vertexData), get(y_1, vertexData), get(z_1, vertexData))
    val nv1_y = mul_sum(m21, m22, m23, get(x_1, vertexData), get(y_1, vertexData), get(z_1, vertexData))
    val nv1_z = mul_sum(m31, m32, m33, get(x_1, vertexData), get(y_1, vertexData), get(z_1, vertexData))

    val nv2_x = mul_sum(m11, m12, m13, get(x_2, vertexData), get(y_2, vertexData), get(z_2, vertexData))
    val nv2_y = mul_sum(m21, m22, m23, get(x_2, vertexData), get(y_2, vertexData), get(z_2, vertexData))
    val nv2_z = mul_sum(m31, m32, m33, get(x_2, vertexData), get(y_2, vertexData), get(z_2, vertexData))

    val nv3_x = mul_sum(m11, m12, m13, get(x_3, vertexData), get(y_3, vertexData), get(z_3, vertexData))
    val nv3_y = mul_sum(m21, m22, m23, get(x_3, vertexData), get(y_3, vertexData), get(z_3, vertexData))
    val nv3_z = mul_sum(m31, m32, m33, get(x_3, vertexData), get(y_3, vertexData), get(z_3, vertexData))

    val nv4_x = mul_sum(m11, m12, m13, get(x_4, vertexData), get(y_4, vertexData), get(z_4, vertexData))
    val nv4_y = mul_sum(m21, m22, m23, get(x_4, vertexData), get(y_4, vertexData), get(z_4, vertexData))
    val nv4_z = mul_sum(m31, m32, m33, get(x_4, vertexData), get(y_4, vertexData), get(z_4, vertexData))

    set(x_1, vertexData, nv1_x)
    set(y_1, vertexData, nv1_y)
    set(z_1, vertexData, nv1_z)
    set(x_2, vertexData, nv2_x)
    set(y_2, vertexData, nv2_y)
    set(z_2, vertexData, nv2_z)
    set(x_3, vertexData, nv3_x)
    set(y_3, vertexData, nv3_y)
    set(z_3, vertexData, nv3_z)
    set(x_4, vertexData, nv4_x)
    set(y_4, vertexData, nv4_y)
    set(z_4, vertexData, nv4_z)
  }

}
