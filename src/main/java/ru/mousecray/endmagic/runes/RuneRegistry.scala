package ru.mousecray.endmagic.runes

import scala.collection.mutable

object RuneRegistry {

  def findRuneEffect(parts: Set[RunePart]): RuneEffect =
    map.getOrElse(nailToCenter(parts), EmptyEffect)

  def addRuneEffect(parts: Set[RunePart], effect: RuneEffect): Unit =
    map += nailToCenter(parts) -> effect

  private def nailToCenter(parts: Set[RunePart]): Set[RunePart] =
    nailY(nailX(parts, findLeft(parts).x), findBottom(parts).y)

  private val map = new mutable.OpenHashMap[Set[RunePart], RuneEffect]()

  private def findLeft(parts: Set[RunePart]) = parts.minBy(_.x)

  private def findBottom(parts: Set[RunePart]) = parts.minBy(_.y)

  private def nailX(parts: Set[RunePart], xl: Int) = parts.map(p => p.copy(x = p.x - xl))

  private def nailY(parts: Set[RunePart], yl: Int) = parts.map(p => p.copy(y = p.y - yl))

}
