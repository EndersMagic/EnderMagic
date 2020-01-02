package endothermic.mutable

import hohserg.endothermic.format.AttributeRepresentation.Vertex
import hohserg.endothermic.{BaseUnpackedVertex, immutable}
import net.minecraft.client.renderer.vertex.VertexFormat

class UnpackedVertex(private var vertexP: Vertex)(implicit protected val quadData: Array[Int], protected val format: VertexFormat) extends BaseUnpackedVertex[UnpackedVertex] {

  override private[endothermic] implicit def vertex: Vertex = vertexP

  override def getUpdateDestination: UnpackedVertex = this

  /**
    * Setters
    */

  def x_=(v: Float): Unit = {
    initFlag |= (1 << 0)
    changeFlag |= (1 << 0)
    _x = v
  }

  def y_=(v: Float): Unit = {
    initFlag |= (1 << 1)
    changeFlag |= (1 << 1)
    _y = v
  }

  def z_=(v: Float): Unit = {
    initFlag |= (1 << 2)
    changeFlag |= (1 << 2)
    _z = v
  }

  def u_=(v: Float): Unit = {
    initFlag |= (1 << 3)
    changeFlag |= (1 << 3)
    _u = v
  }

  def v_=(v: Float): Unit = {
    initFlag |= (1 << 4)
    changeFlag |= (1 << 4)
    _v = v
  }

  def r_=(v: Float): Unit = {
    initFlag |= (1 << 5)
    changeFlag |= (1 << 5)
    _r = v
  }

  def g_=(v: Float): Unit = {
    initFlag |= (1 << 6)
    changeFlag |= (1 << 6)
    _g = v
  }

  def b_=(v: Float): Unit = {
    initFlag |= (1 << 7)
    changeFlag |= (1 << 7)
    _b = v
  }

  def a_=(v: Float): Unit = {
    initFlag |= (1 << 8)
    changeFlag |= (1 << 8)
    _a = v
  }

  def lx_=(v: Float): Unit = {
    initFlag |= (1 << 9)
    changeFlag |= (1 << 9)
    _lx = v
  }

  def ly_=(v: Float): Unit = {
    initFlag |= (1 << 10)
    changeFlag |= (1 << 10)
    _ly = v
  }

  def nx_=(v: Float): Unit = {
    initFlag |= (1 << 11)
    changeFlag |= (1 << 11)
    _nx = v
  }

  def ny_=(v: Float): Unit = {
    initFlag |= (1 << 12)
    changeFlag |= (1 << 12)
    _ny = v
  }

  def nz_=(v: Float): Unit = {
    initFlag |= (1 << 13)
    changeFlag |= (1 << 13)
    _nz = v
  }

  def padding_=(v: Float): Unit = {
    initFlag |= (1 << 14)
    changeFlag |= (1 << 14)
    _padding = v
  }

  /**
    * Getters - only for scala 2.11
    */

  override def x: Float = super.x

  override def y: Float = super.y

  override def z: Float = super.z

  override def u: Float = super.u

  override def v: Float = super.v

  override def r: Float = super.r

  override def g: Float = super.g

  override def b: Float = super.b

  override def a: Float = super.a

  override def lx: Float = super.lx

  override def ly: Float = super.ly

  override def nx: Float = super.nx

  override def ny: Float = super.ny

  override def nz: Float = super.nz

  override def padding: Float = super.padding

  override def toImmutable: immutable.UnpackedVertex = {
    val result = new immutable.UnpackedVertex(vertex)(quadData, format)

    result._x = _x
    result._y = _y
    result._z = _z
    result._u = _u
    result._v = _v
    result._r = _r
    result._g = _g
    result._b = _b
    result._a = _a
    result._lx = _lx
    result._ly = _ly
    result._nx = _nx
    result._ny = _ny
    result._nz = _nz
    result._padding = _padding
    result.initFlag = initFlag
    result.changeFlag = changeFlag

    result
  }
}
