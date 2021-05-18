package ru.mousecray.endmagic.capability.chunk

import net.minecraft.util.EnumFacing
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.relauncher.Side
import ru.mousecray.endmagic.capability.chunk.Rune.Recess
import ru.mousecray.endmagic.capability.chunk.client._
import ru.mousecray.endmagic.rune.{RuneEffect, RuneEffectRegistry}
import ru.mousecray.endmagic.util.Vec2i

import scala.collection.JavaConverters._
import scala.collection.mutable

class RuneState {

  def getRuneAtSide(side: EnumFacing): Rune = runes(side)

  private val runes = new mutable.HashMap[EnumFacing, Rune]()
  EnumFacing.values().foreach(runes += _ -> new Rune)

  def canAddPart(side: EnumFacing, coord: Vec2i): Boolean =
    !runes(side).parts.contains(coord)


  /**
    * return true if rune finished
    */
  def addRunePart(side: EnumFacing, coord: Vec2i, runePart: RunePart, currentTimeMillis: Long): Boolean = {
    val rune = runes(side)
    if (!rune.parts.contains(coord)) {
      rune.parts += (coord -> runePart)
      val foundEffect = RuneEffectRegistry.instance.findEffect(rune.parts.asJava)
      //if(foundEffect.isValidTarget())
      rune.runeEffect = foundEffect

      if (FMLCommonHandler.instance().getEffectiveSide == Side.CLIENT)
        incrementQuadsData(rune, coord, runePart)

      if (rune.parts.size == 1)
        rune.startingTime = currentTimeMillis
      else if (rune.runeEffect != RuneEffect.EmptyEffect) {
        rune.averageCreatingTime = (currentTimeMillis - rune.startingTime) / rune.parts.size
        if (FMLCommonHandler.instance().getEffectiveSide == Side.CLIENT)
          rune.splashAnimation = 0
        return true
      }
    }
    false
  }

  def incrementQuadsData(rune: Rune, coord: Vec2i, runePart: RunePart): Unit = {
    incrementTopQuadsData(rune, coord, runePart)
    incrementRecessQuadsData(rune, coord, runePart)
  }


  private def incrementTopQuadsData(rune: Rune, vec2i: Vec2i, part: RunePart): Unit = {
    val prevTopPiece = rune.topQuadMatrix(vec2i.x)(vec2i.y)
    val newTopPieces = prevTopPiece.splitBy(vec2i)
    newTopPieces.foreach(piece =>
      for {
        x <- piece.x1 to piece.x2
        y <- piece.y1 to piece.y2
      } rune.topQuadMatrix(x)(y) = piece
    )
    rune.topQuadData -= prevTopPiece
    rune.topQuadData ++= newTopPieces
  }

  private def incrementRecessQuadsData(rune: Rune, vec2i: Vec2i, part: RunePart): Unit = {
    val (x, y) = (vec2i.x, vec2i.y)

    val targetRecess = new Recess
    rune.recessQuadsMatrixUpdate(x, y, targetRecess)

    targetRecess.bottom = BottomQuadData(x, y)

    val leftRecess = rune.recessQuadsMatrix(x - 1, y)
    if (leftRecess != null)
      leftRecess.right = null
    else
      targetRecess.left = LeftSideQuadData(x, y)

    val rightRecess = rune.recessQuadsMatrix(x + 1, y)
    if (rightRecess != null)
      rightRecess.left = null
    else
      targetRecess.right = RightSideQuadData(x, y)

    val downRecess = rune.recessQuadsMatrix(x, y + 1)
    if (downRecess != null)
      downRecess.up = null
    else
      targetRecess.down = DownSideQuadData(x, y)

    val upRecess = rune.recessQuadsMatrix(x, y - 1)
    if (upRecess != null)
      upRecess.down = null
    else
      targetRecess.up = UpSideQuadData(x, y)
  }

  def foreachRuneQuadsData(side: EnumFacing, f: (QuadData, EnumFacing) => Unit): Unit = {
    if (side == null) {
    } else
      runes.get(side).foreach { rune =>
        rune.topQuadData.foreach(f(_, side))
      }
  }

  def foreachRuneQuadsDataRecess(f: (QuadData, EnumFacing) => Unit): Unit = {
    runes.foreach { case (sourceSide, rune) =>
      rune.parts.keys.foreach { c =>
        val recess = rune.recessQuadsMatrix(c.x, c.y)
        if (recess.left != null)
          f(recess.left, sourceSide)
        if (recess.right != null)
          f(recess.right, sourceSide)
        if (recess.down != null)
          f(recess.down, sourceSide)
        if (recess.up != null)
          f(recess.up, sourceSide)
        if (recess.bottom != null)
          f(recess.bottom, sourceSide)
      }
    }
  }

}
