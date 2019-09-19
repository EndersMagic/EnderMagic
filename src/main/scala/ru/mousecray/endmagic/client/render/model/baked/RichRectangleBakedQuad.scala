package ru.mousecray.endmagic.client.render.model.baked

import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.VertexFormat
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumFacing._
import net.minecraft.util.math.Vec3i
import org.lwjgl.opengl.GL11
import org.lwjgl.util.vector.{Vector2f, Vector3f}
import ru.mousecray.endmagic.client.render.model.baked.RichRectangleBakedQuad._
import ru.mousecray.endmagic.util.elix_x.baked.UnpackedBakedQuad
import ru.mousecray.endmagic.util.elix_x.baked.vertex.{DefaultUnpackedVertex, DefaultUnpackedVertices}
import ru.mousecray.endmagic.util.elix_x.ecomms.color.RGBA

import scala.collection.JavaConverters._
import scala.language.implicitConversions
import scala.math._
import scala.reflect.ClassTag

case class RichRectangleBakedQuad(format: VertexFormat, v1: Vertex, v2: Vertex, v3: Vertex, v4: Vertex, tintIndexIn: Int, faceIn: EnumFacing, spriteIn: TextureAtlasSprite, applyDiffuseLighting: Boolean) {
  implicit val faceIn2 = faceIn

  def color(color: Int): RichRectangleBakedQuad =
    copy(
      v1 = v1.copy(_2 = RGBA.fromRGBA(color)),
      v2 = v2.copy(_2 = RGBA.fromRGBA(color)),
      v3 = v3.copy(_2 = RGBA.fromRGBA(color)),
      v4 = v4.copy(_2 = RGBA.fromRGBA(color))
    )

  def texture(atlas: TextureAtlasSprite): RichRectangleBakedQuad =
    copy(
      v1 = v1.copy(_3 = (atlas.getMinU, atlas.getMinV)),
      v2 = v2.copy(_3 = (atlas.getMinU, atlas.getMaxV)),
      v3 = v3.copy(_3 = (atlas.getMaxU, atlas.getMaxV)),
      v4 = v4.copy(_3 = (atlas.getMaxU, atlas.getMinV)),
      spriteIn = atlas
    )


  val minVertex: Vertex = Seq(v1, v2, v3, v4).minBy(_._1)
  val maxVertex: Vertex = Seq(v1, v2, v3, v4).maxBy(_._1)

  def slicedArea(by: Float): SlicedArea[Byte, List[Boolean]] =
    new SlicedArea(((floor(x2) - ceil(x)) / by).toInt, ((floor(y2) - ceil(y)) / by).toInt)

  //flat
  val VertexPos(x, y, const) = projectTo2d(minVertex._1)
  val VertexPos(x2, y2, _) = projectTo2d(maxVertex._1)

  implicit def toQuad: BakedQuad = {
    val v = Seq(
      v1,
      v2,
      v3,
      v4
    )
    new UnpackedBakedQuad(new DefaultUnpackedVertices(v.map(buildVertex): _*), tintIndexIn, faceIn, spriteIn, applyDiffuseLighting).pack(format)
  }

  /*
  def projectTo2d(v: VertexPos): VertexPos = {
    val x = v._1
    val y = v._2
    val z = v._3

    faceIn match {
      case DOWN => (x, z, y)
      case UP => (x, z, y)
      case NORTH => (x, y, z)
      case SOUTH => (x, y, z)
      case WEST => (z, y, x)
      case EAST => (z, y, x)
    }
  }

  val returnTo3dTupled = (returnTo3d _).tupled

  def returnTo3d(x: Float, y: Float, const: Float): VertexPos =
    faceIn match {
      case DOWN => (x, const, y)
      case UP => (x, const, y)
      case NORTH => (x, y, const)
      case SOUTH => (x, y, const)
      case WEST => (const, y, x)
      case EAST => (const, y, x)
    }*/

  def calculateBaricentric(a: (Float, Float), b: (Float, Float), c: (Float, Float), nvu: VertexPos): (Float, Float) = {
    val ab = new Vector3f(b._1 - a._1, b._2 - a._2, 0)
    val ac = new Vector3f(c._1 - a._1, c._2 - a._2, 0)

    val an = new Vector3f(nvu._1 - a._1, nvu._2 - a._2, 0)

    val s = Vector3f.cross(ab, ac, null).lengthSquared()
    val sabn = Vector3f.cross(ab, an, null).lengthSquared()
    val sacn = Vector3f.cross(an, ac, null).lengthSquared()
    (sqrt(sabn / s).toFloat, sqrt(sacn / s).toFloat)
  }

  def interpolateColor[A](nvu: VertexPos, by: Vertex => A)(implicit scalable: Scalable[A], blendable: Blendable[A]): A = {
    import blendable._
    import scalable._

    if (nvu._1 > (-(y - y2) * nvu._2 - (x * y2 - x2 * y)) / (x2 - x)) {
      val (w: Float, v: Float) = calculateBaricentric((x, y), (x2, y), (x2, y2), nvu)
      by(v1) * (1 - w - v) + by(v2) * v + by(v3) * w
    } else {
      val (w, v) = calculateBaricentric((x, y), (x2, y2), (x, y2), nvu)
      by(v1) * (1 - w - v) + by(v3) * v + by(v4) * w
    }
  }

  def calculateUV[A: Numeric](nvu: VertexPos, by: Vertex => A): (Float, Float) = ???

  def slice(_nx: Float, _ny: Float, _nx2: Float, _ny2: Float): RichRectangleBakedQuad = {
    val nx = min(_nx, _nx2)
    val nx2 = max(_nx, _nx2)
    val ny = min(_ny, _ny2)
    val ny2 = max(_ny, _ny2)

    val nvu1 = (max(x, nx), max(y, ny), const)
    val nvu3 = (min(x2, nx2), min(y2, ny2), const)

    val nvu2 = (nvu3._1, nvu1._2, const)
    val nvu4 = (nvu1._1, nvu3._2, const)

    RichRectangleBakedQuad(format,
      prepareVertex(nvu1),
      prepareVertex(nvu4),
      prepareVertex(nvu3),
      prepareVertex(nvu2),
      tintIndexIn, faceIn, spriteIn, applyDiffuseLighting)

  }

  def prepareVertex(nvu: VertexPos): Vertex = {
    Vertex(
      returnTo3dTupled(nvu),
      RGBA.fromRGBA(0xffffffff),
      //interpolateColor(nvu, _._2),
      interpolateColor(nvu, _._3),
      interpolateColor(nvu, _._4),
      v1._5
    )
  }

  def slice(nv1: VertexPos, nv2: VertexPos): RichRectangleBakedQuad = {
    val VertexPos(nx, ny, nz) = projectTo2d(nv1)
    val VertexPos(nx2, ny2, nz2) = projectTo2d(nv2)
    slice(nx, ny, nx2, ny2)
  }


}

object RichRectangleBakedQuad {

  def projectTo2d(v: VertexPos)(implicit faceIn: EnumFacing): VertexPos = {
    val x = v._1
    val y = v._2
    val z = v._3

    faceIn match {
      case DOWN => (x, z, y)
      case UP => (x, z, y)
      case NORTH => (x, y, z)
      case SOUTH => (x, y, z)
      case WEST => (z, y, x)
      case EAST => (z, y, x)
    }
  }

  def returnTo3dTupled(i: VertexPos)(implicit faceIn: EnumFacing): VertexPos = returnTo3d(i._1, i._2, i._3)(faceIn)

  def returnTo3d(x: Float, y: Float, const: Float)(implicit faceIn: EnumFacing): VertexPos =
    faceIn match {
      case DOWN => (x, const, y)
      case UP => (x, const, y)
      case NORTH => (x, y, const)
      case SOUTH => (x, y, const)
      case WEST => (const, y, x)
      case EAST => (const, y, x)
    }

  implicit def apply(quad: BakedQuad): RichRectangleBakedQuad = {
    val vertices = UnpackedBakedQuad.unpack(quad).getVertices.getVertices.asScala.map(unpackVertex)
    new RichRectangleBakedQuad(quad.getFormat, vertices(0), vertices(1), vertices(2), vertices(3), quad.getTintIndex, quad.getFace, quad.getSprite, quad.shouldApplyDiffuseLighting())
  }

  trait Scalable[A] {
    def scale(a: A, b: Float): A

    implicit class Ops(a: A) {
      def *(b: Float): A = scale(a, b)
    }

  }

  implicit val scalableRGBA: Scalable[RGBA] = new Scalable[RGBA] {
    override def scale(a: RGBA, b: Float): RGBA = new RGBA(a.getRF * b, a.getGF * b, a.getBF * b)
  }
  implicit val scalableUV: Scalable[(Float, Float)] = new Scalable[(Float, Float)] {
    override def scale(a: (Float, Float), b: Float): (Float, Float) = (a._1 * b, a._2 * b)
  }
  implicit val scalableLightmap: Scalable[(Int, Int, Int)] = new Scalable[(Int, Int, Int)] {
    override def scale(a: (Int, Int, Int), b: Float): (Int, Int, Int) = ((a._1 * b).toInt, (a._2 * b).toInt, (a._3 * b).toInt)
  }
  implicit val orderingOfVertexPos: Ordering[VertexPos] = Ordering.by(VertexPos.unapply)


  trait Blendable[A] {
    def blend(a: A, b: A, ratio: Float): A

    implicit class Ops1(a: A) {
      def +(b: A): A = blend(a, b, 0.5f)
    }

  }

  implicit val blendableRGBA: Blendable[RGBA] = new Blendable[RGBA] {
    override def blend(a: RGBA, b: RGBA, ratio: Float): RGBA =
      RGBA.fromRGBA((a.rgba() + (b.rgba() - a.rgba()) * ratio).toInt)
  }
  implicit val blendableUV: Blendable[(Float, Float)] = new Blendable[(Float, Float)] {
    override def blend(a: (Float, Float), b: (Float, Float), ratio: Float): (Float, Float) = {
      (a._1 + b._1, a._2 + b._2)
      //(a._1 + (b._1 - a._1) * ratio) ->
      //  (a._2 + (b._2 - a._2) * ratio)
    }
  }
  implicit val blendableLightmap: Blendable[(Int, Int, Int)] = new Blendable[(Int, Int, Int)] {
    override def blend(a: (Int, Int, Int), b: (Int, Int, Int), ratio: Float): (Int, Int, Int) = {
      ((a._1 + (b._1 - a._1) * ratio).toInt,
        (a._2 + (b._2 - a._2) * ratio).toInt,
        (a._3 + (b._3 - a._3) * ratio).toInt)
    }
  }

  //val test = RichRectangleBakedQuad(null, ((0, 0, 0), (0, 20, 15), 0, EAST, null, false)
  //println(test.toQuad())
  //println(test.slice((-1, 0, -1), (0, 20, 16)))

  //test for projections
  //EnumFacing.values().foreach { f =>
  //  val quad = RichRectangleBakedQuad(null, null, null, 0, f, null, false)

  //println((quad.returnTo3d _).tupled(quad.projectTo2d((1, 2, 3))) == (1, 2, 3))
  // }

  def buildVertex(v1: Vertex): DefaultUnpackedVertex = new DefaultUnpackedVertex(v1._1, v1._2, v1._3, v1._4, v1._5)

  def unpackVertex(v1: DefaultUnpackedVertex): Vertex = {
    Vertex(v1.getPos, v1.getColor, v1.getTexture, if (v1.getLightmap == null) new Vec3i(1, 1, 0) else v1.getLightmap, v1.getNormal)
  }

  def buildVertexData(format: VertexFormat, v1: Vertex*): Array[Int] =
    new net.minecraftforge.client.model.pipeline.UnpackedBakedQuad(
      new DefaultUnpackedVertices(v1.map(buildVertex): _*).pack(GL11.GL_QUADS, format).getData,
      0, null, null, false, format
    ).getVertexData

  case class VertexPos(_1: Float, _2: Float, _3: Float)

  case class Vertex(_1: VertexPos, _2: RGBA, _3: (Float, Float), _4: (Int, Int, Int), _5: (Int, Int, Int))

  implicit private def tupple2VertexPos(t: (Float, Float, Float)): VertexPos = VertexPos.tupled(t)


  @inline implicit private def tuple2Vector3f(t: VertexPos): Vector3f =
    new Vector3f(t._1, t._2, t._3)

  @inline implicit private def vertexPos2Tuple(t: Vector3f): VertexPos =
    VertexPos(t.x, t.y, t.z)

  @inline implicit private def tuple2TexturePos(t: (Float, Float)): Vector2f =
    new Vector2f(t._1, t._2)

  @inline implicit private def texturePos2Tuple(t: Vector2f): (Float, Float) =
    (t.x, t.y)

  @inline implicit private def tuple2NormalAndLightmap(t: (Int, Int, Int)): Vec3i =
    new Vec3i(t._1, t._2, t._3)

  @inline implicit private def normalAndLightmap2Tuple(t: Vec3i): (Int, Int, Int) =
    (t.getX, t.getY, t.getZ)

}

class SlicedArea[A: ClassTag, B: ClassTag](w: Int, h: Int) {

  def grouped: SlicedArea[A, B] = this

  def eraseBy(function: Array[Array[A]] => Unit): SlicedArea[A, B] = {
    function(matrix)
    this
  }

  def overlay[C](mapping: PartialFunction[(Int, Int, A), C]): Seq[C] = {
    val mapping2: ((Int, Int, A)) => Option[C] = mapping.lift
    (for {
      i <- matrix.indices
      col = matrix(i)
      j <- col.indices
    } yield mapping2((i, j, col(j)))).flatten
  }


  import math._

  def apply(x: Int, y: Int): A = matrix(x)(y)

  def update(x: Int, y: Int, v: A): Unit = matrix(x)(y) = v

  def getEdge(p1: (Int, Int), p2: (Int, Int)): Option[B] = {
    if (p1._1 == p2._1) Some(edgesX(p1._1)(min(p1._2, p2._2)))
    else if (p1._2 == p2._2) Some(edgesY(p1._2)(min(p1._1, p2._1)))
    else None
  }

  def setEdge(p1: (Int, Int), p2: (Int, Int), v: B): Unit = {
    if (p1._1 == p2._1) edgesX(p1._1)(min(p1._2, p2._2)) = v
    else if (p1._2 == p2._2) edgesY(p1._2)(min(p1._1, p2._1)) = v
  }

  val matrix: Array[Array[A]] = new Array[Array[A]](w).map(_ => new Array[A](h))

  val edgesX: Array[Array[B]] = new Array[Array[B]](w).map(_ => new Array[B](h))
  val edgesY: Array[Array[B]] = new Array[Array[B]](h).map(_ => new Array[B](w))

}
