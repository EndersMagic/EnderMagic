package ru.mousecray.endmagic.capability.chunk

import ru.mousecray.endmagic.capability.chunk.Rune.Recess
import ru.mousecray.endmagic.capability.chunk.RuneEffect.EmptyEffect
import ru.mousecray.endmagic.capability.chunk.client._
import ru.mousecray.endmagic.util.Vec2i

import scala.collection.mutable

class Rune {
  val parts = new mutable.HashMap[Vec2i, RunePart]
  var runeEffect: RuneEffect = EmptyEffect
  var averageCreatingTime: Long = Long.MaxValue
  var startingTime: Long = -1
  val recessQuadsMatrix: Array[Array[Recess]] = new Array[Array[Recess]](16).map(_ => new Array[Recess](16))
  val topQuadMatrix: Array[Array[TopQuadData]] = new Array[Array[TopQuadData]](16).map(_ => new Array[TopQuadData](16))
  val topQuadData = new mutable.HashSet[TopQuadData]()

  {
    val primalCount = 4
    val primalPieceWidth = 16 / primalCount
    for (i <- 0 until primalCount) {
      val data = TopQuadData(i * primalPieceWidth, 0, i * primalPieceWidth + primalPieceWidth - 1, 15)
      topQuadData += data
      for {
        x <- data.x1 to data.x2
        y <- data.y1 to data.y2
      } topQuadMatrix(x)(y) = data
    }
  }
}

object Rune {

  class Recess {
    var left: LeftSideQuadData = _
    var right: RightSideQuadData = _
    var down: DownSideQuadData = _
    var up: UpSideQuadData = _
    var bottom: BottomQuadData = _
  }

}
