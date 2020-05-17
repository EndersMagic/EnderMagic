package ru.mousecray.endmagic.capability.chunk

import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.relauncher.Side
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
  private val recessQuadsMatrix: Array[Array[Recess]] = new Array[Array[Recess]](16).map(_ => new Array[Recess](16))

  var splashAnimation = 0

  def recessQuadsMatrix(x: Int, y: Int): Recess =
    if (x <= 15 && x >= 0 && y <= 15 && y >= 0)
      recessQuadsMatrix(x)(y)
    else
      null

  def recessQuadsMatrixUpdate(x: Int, y: Int, v: Recess): Unit =
    if (x <= 15 && x >= 0 && y <= 15 && y >= 0)
      recessQuadsMatrix(x)(y) = v


  val topQuadMatrix: Array[Array[TopQuadData]] = new Array[Array[TopQuadData]](16).map(_ => new Array[TopQuadData](16))
  val topQuadData = new mutable.HashSet[TopQuadData]()

  if (FMLCommonHandler.instance().getEffectiveSide == Side.CLIENT) {
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

  val splashAnimationMax = 100

  class Recess {
    var left: LeftSideQuadData = _
    var right: RightSideQuadData = _
    var down: DownSideQuadData = _
    var up: UpSideQuadData = _
    var bottom: BottomQuadData = _
  }

}
