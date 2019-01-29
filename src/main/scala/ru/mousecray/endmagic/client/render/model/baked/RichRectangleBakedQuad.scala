package ru.mousecray.endmagic.client.render.model.baked

import math._
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.VertexFormat
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumFacing._
import net.minecraft.util.math.Vec3i
import org.lwjgl.opengl.GL11
import org.lwjgl.util.vector.{Vector2f, Vector3f}
import ru.mousecray.endmagic.client.render.model.baked.RichRectangleBakedQuad._
import ru.mousecray.endmagic.runes.Rune
import ru.mousecray.endmagic.util.elix_x.baked.UnpackedBakedQuad
import ru.mousecray.endmagic.util.elix_x.baked.vertex.{DefaultUnpackedVertex, DefaultUnpackedVertices}
import ru.mousecray.endmagic.util.elix_x.ecomms.color.RGBA

import scala.collection.JavaConverters._
import scala.language.implicitConversions

case class RichRectangleBakedQuad(format: VertexFormat, v1: Vertex, v2: Vertex, v3: Vertex, v4: Vertex, tintIndexIn: Int, faceIn: EnumFacing, spriteIn: TextureAtlasSprite, applyDiffuseLighting: Boolean) {
  def slicedArea(by: Float): SlicedArea[RichRectangleBakedQuad, List[Boolean]] =
    new SlicedArea(((floor(x2) - ceil(x)) / by).toInt, ((floor(y2) - ceil(y)) / by).toInt)

  //flat
  val (x, y, const) = projectTo2d(v1._1)
  val (x2, y2, _) = projectTo2d(v3._1)

  implicit def toQuad: BakedQuad = {
    val v = Seq(
      v1,
      v2,
      v3,
      v3
    )
    new UnpackedBakedQuad(new DefaultUnpackedVertices(v.map(buildVertex): _*), tintIndexIn, faceIn, spriteIn, applyDiffuseLighting).pack(format)
  }

  def projectTo2d(v: VertexPos): (Float, Float, Float) = {
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

  def returnTo3d(x: Float, y: Float, const: Float): (Float, Float, Float) =
    faceIn match {
      case DOWN => (x, const, y)
      case UP => (x, const, y)
      case NORTH => (x, y, const)
      case SOUTH => (x, y, const)
      case WEST => (const, y, x)
      case EAST => (const, y, x)
    }

  def interpolateColor[A: Blendable](nvu1: VertexPos, by: Vertex => A): A = ???

  def calculateUV[A: Numeric](nvu: (Float, Float, Float), by: Vertex => A): (Float, Float) = ???

  def slice(nv1: VertexPos, nv2: VertexPos): RichRectangleBakedQuad = {

    import math._
    //val x1 = v1._1._1
    //val y1 = v1._1._2
    //val z1 = v1._1._3

    val (nx, ny, nz) = projectTo2d(nv1)
    val (nx2, ny2, nz2) = projectTo2d(nv2)

    val nvu1 = returnTo3d(max(x, nx), max(y, ny), const)
    val nvu3 = returnTo3d(min(x2, nx2), min(y2, ny2), const)
    val nvu2 = returnTo3dTupled((nvu3._1, nv1._3, const))
    val nvu4 = returnTo3dTupled((nv1._1, nvu3._2, const))

    def prepareVertex(nvu: VertexPos): Vertex = {
      (
        nvu,
        interpolateColor(nvu, _._2),
        interpolateColor(nvu, _._3),
        interpolateColor(nvu, _._4),
        v1._5
      )
    }

    RichRectangleBakedQuad(format,
      prepareVertex(nvu1),
      prepareVertex(nvu2),
      prepareVertex(nvu3),
      prepareVertex(nvu4),
      tintIndexIn, faceIn, spriteIn, applyDiffuseLighting)
  }


}

object RichRectangleBakedQuad {

  implicit def apply(quad: BakedQuad): RichRectangleBakedQuad = {
    val vertices = UnpackedBakedQuad.unpack(quad).getVertices.getVertices.asScala.map(unpackVertex)
    new RichRectangleBakedQuad(quad.getFormat, vertices(0), vertices(1), vertices(2), vertices(3), quad.getTintIndex, quad.getFace, quad.getSprite, quad.shouldApplyDiffuseLighting())
  }

  trait Blendable[A] {
    def blend(a: A, b: A, ratio: Float): A

  }

  implicit val blendableRGBA: Blendable[RGBA] = new Blendable[RGBA] {
    override def blend(a: RGBA, b: RGBA, ratio: Float): RGBA =
      RGBA.fromRGBA((a.rgba() + (b.rgba() - a.rgba()) * ratio).toInt)
  }
  implicit val blendableUV: Blendable[(Float, Float)] = new Blendable[(Float, Float)] {
    override def blend(a: (Float, Float), b: (Float, Float), ratio: Float): (Float, Float) = {
      (a._1 + (b._1 - a._1) * ratio) ->
        (a._2 + (b._2 - a._2) * ratio)
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

  def unpackVertex(v1: DefaultUnpackedVertex): Vertex = (v1.getPos, v1.getColor, v1.getTexture, v1.getLightmap, v1.getNormal)

  def buildVertexData(format: VertexFormat, v1: Vertex*): Array[Int] =
    new net.minecraftforge.client.model.pipeline.UnpackedBakedQuad(
      new DefaultUnpackedVertices(v1.map(buildVertex): _*).pack(GL11.GL_QUADS, format).getData,
      0, null, null, false, format
    ).getVertexData

  type VertexPos = (Float, Float, Float)


  type Vertex = (VertexPos, RGBA, (Float, Float), (Int, Int, Int), (Int, Int, Int))


  @inline implicit private def tuple2VertexPos(t: (Float, Float, Float)): Vector3f =
    new Vector3f(t._1, t._2, t._3)

  @inline implicit private def vertexPos2Tuple(t: Vector3f): (Float, Float, Float) =
    (t.x, t.y, t.z)

  @inline implicit private def tuple2TexturePos(t: (Float, Float)): Vector2f =
    new Vector2f(t._1, t._2)

  @inline implicit private def texturePos2Tuple(t: Vector2f): (Float, Float) =
    (t.x, t.y)

  @inline implicit private def tuple2NormalAndLightmap(t: (Int, Int, Int)): Vec3i =
    new Vec3i(t._1, t._2, t._3)

  @inline implicit private def normalAndLightmap2Tuple(t: Vec3i): (Int, Int, Int) =
    (t.getX, t.getY, t.getZ)

}

class SlicedArea[A, B](w: Int, h: Int) {
  def overlay(richQuad: RichRectangleBakedQuad): Seq[RichRectangleBakedQuad] = ???

  def erase(rune: Rune): SlicedArea[A, B] = ???


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

  val matrix: Array[Array[A]] = new Array(w).map(_ => new Array[A](h))

  val edgesX: Array[Array[B]] = new Array(w).map(_ => new Array[B](h))
  val edgesY: Array[Array[B]] = new Array(h).map(_ => new Array[B](w))

}
