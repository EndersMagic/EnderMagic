package ru.mousecray.endmagic.capability.chunk.client

case class ElongateQuadData(x: Int, y1: Int, y2: Int) extends QuadData {
  def splitFirst(y: Int) = ElongateQuadData(x, y1, y - 1)

  def splitSecond(y: Int) = ElongateQuadData(x, y + 1, y2)
}