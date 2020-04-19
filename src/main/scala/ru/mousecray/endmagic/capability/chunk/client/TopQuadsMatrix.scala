package ru.mousecray.endmagic.capability.chunk.client

import ru.mousecray.endmagic.util.Vec2i

class TopQuadsMatrix(matrix: Array[Array[TopQuadData]]) {

  def get(coord: Vec2i): TopQuadData = matrix(coord.x)(coord.y)

  def replace(coord: Vec2i, by: Set[TopQuadData]): TopQuadsMatrix = {
    val newMatrix = matrix.clone()
    by.foreach(q => q.iterate.foreach(c => newMatrix(c.x)(c.y) = q))
    new TopQuadsMatrix(newMatrix)
  }

  def iterate: Seq[TopQuadData] = matrix.flatten.filter(_ != null)

}

object TopQuadsMatrix {

  object SingleArea extends TopQuadsMatrix(new Array[Array[TopQuadData]](16).map(_ => new Array[TopQuadData](16))) {
    override def get(coord: Vec2i): TopQuadData = TopQuadData.full

    override def iterate: Seq[TopQuadData] = Seq(TopQuadData.full)
  }

}
