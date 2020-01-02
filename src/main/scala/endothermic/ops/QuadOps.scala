package endothermic.ops

import hohserg.endothermic.format.AttributeRepresentation._

import scala.math._

trait QuadOps {
  this: ReconstructOpsQuad =>

  def translate(x: Float, y: Float, z: Float): Self =
    reconstruct(
      v1.reconstruct(v1.x + x, v1.y + y, v1.z + z),
      v2.reconstruct(v2.x + x, v2.y + y, v2.z + z),
      v3.reconstruct(v3.x + x, v3.y + y, v3.z + z),
      v4.reconstruct(v4.x + x, v4.y + y, v4.z + z)
    )

  def scale(factor: Float): Self =
    foreachVertex(v => v.reconstruct(v.x * factor, v.y * factor, v.z * factor))

  def rotate(O: Float, x: Float, y: Float, z: Float): Self = {
    val matrix = Seq(
      Seq(cos(O) + x * x * (1 - cos(O)), x * y * (1 - cos(O)) - z * sin(O), x * z * (1 - cos(O)) + y * sin(O)),
      Seq(y * x * (1 - cos(O)) + z * sin(O), cos(O) + y * y * (1 - cos(O)), y * z * (1 - cos(O)) - x * sin(O)),
      Seq(z * x * (1 - cos(O)) - y * sin(O), z * y * (1 - cos(O)) + x * sin(O), cos(O) + z * z * (1 - cos(O)))
    )

    def calcVertex(v: Seq[Float]) =
      matrix.map(line => line.zip(v).map { case (a, b) => a * b }.sum)


    foreachVertex(v => {
      val nv = calcVertex(Seq(v.x, v.y, v.z))
      v.reconstruct(x = nv(0).toFloat, y = nv(1).toFloat, z = nv(2).toFloat)
    })
  }

  def slice(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float, x4: Float, y4: Float): Self = {
    val v1i = v1.toImmutable
    val v2i = v2.toImmutable
    val v3i = v3.toImmutable
    val v4i = v4.toImmutable

    def calcVertex[V <: Vertex](x: Float, y: Float): VertexType = {
      val a: Float = x * y
      v1 * (-a) + v2i * a + v3i * (-a) + v4i * a + v1i * x + v1i * y - v1 - v2i * x - v4i * y
    }

    reconstruct(
      calcVertex(x1, y1),
      calcVertex(x2, y2),
      calcVertex(x3, y3),
      calcVertex(x4, y4)
    )
  }


}
