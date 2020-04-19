package ru.mousecray.endmagic.capability.chunk.client

import ru.mousecray.endmagic.util.Vec2i

case class TopQuadData(x1: Int, y1: Int, x2: Int, y2: Int) extends QuadData {
  def nonEmpty: Boolean = x1 <= x2 && y1 <= y2

  def splitBy(vec2i: Vec2i): Set[TopQuadData] = Set(
    TopQuadData(x1, y1, vec2i.x - 1, y2),
    TopQuadData(vec2i.x, y1, vec2i.x, vec2i.y - 1),
    TopQuadData(vec2i.x, vec2i.y + 1, vec2i.x, y2),
    TopQuadData(vec2i.x + 1, y1, x2, y2)
  ).filter(_.nonEmpty)

  lazy val iterate: Seq[Vec2i] = for {
    x <- x1 to x2
    y <- y1 to y2
  } yield new Vec2i(x, y)

}

object TopQuadData {
  val full = TopQuadData(0, 0, 15, 15)
}