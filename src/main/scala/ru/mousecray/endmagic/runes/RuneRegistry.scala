package ru.mousecray.endmagic.runes

import scala.language.implicitConversions
import RunePartEntryWrapper._

import scala.collection.mutable

object RuneRegistry {

  val colorById = new mutable.OpenHashMap[Byte, Int]()

  def getColor(id: Byte): Int = colorById.getOrElse(id, 0xffffff)

  def registerColor(id: Byte, color: Int): Unit =
    colorById += ((id, color))

  registerColor(1, 0xff00ff)


  def findRuneEffect(parts: Map[(Int, Int), RunePart]): RuneEffect =
    map.getOrElse(nailToCenter(parts), EmptyEffect)

  def addRuneEffect(parts: Map[(Int, Int), RunePart], effect: RuneEffect): Unit =
    map += nailToCenter(parts) -> effect

  private def nailToCenter(parts: Map[(Int, Int), RunePart]): Map[(Int, Int), RunePart] =
    nailY(nailX(parts, findLeft(parts).x), findBottom(parts).y)

  private val map = new mutable.OpenHashMap[Map[(Int, Int), RunePart], RuneEffect]()

  private def findLeft(parts: Map[(Int, Int), RunePart]) = parts.minBy(_.x)

  private def findBottom(parts: Map[(Int, Int), RunePart]) = parts.minBy(_.y)

  private def nailX(parts: Map[(Int, Int), RunePart], xl: Int) = parts.map(p => (p.x - xl) -> p.y -> p._2)

  private def nailY(parts: Map[(Int, Int), RunePart], yl: Int) = parts.map(p => p.x -> (p.y - yl) -> p._2)

}
