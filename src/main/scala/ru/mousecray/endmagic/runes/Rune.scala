package ru.mousecray.endmagic.runes

import scala.collection.mutable.ListBuffer

class Rune {
  private[this] var runeEffect: RuneEffect = EmptyEffect

  def isFinished: Boolean = runeEffect != EmptyEffect

  val parts = new ListBuffer[RunePart]

  def add(part: RunePart): Unit = {
    parts += part
    checkFinished()
  }

  private def checkFinished(): Unit =
    runeEffect = RuneRegistry.findRuneEffect(parts.groupBy((i: RunePart) => Tuple2.apply(i.x, i.y)).map(i => i._2.last).toSet)


}
