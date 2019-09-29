package ru.mousecray.endmagic.runes

import ru.mousecray.endmagic.runes.effects.{EmptyEffect, RuneEffect}

case class Rune(parts: Map[(Int, Int), RunePart], runeEffect: RuneEffect = EmptyEffect, averageCreatingTime: Long = 0, lastTime: Long = 0) {

  val isFinished: Boolean = runeEffect != EmptyEffect

  def +(x: Int, y: Int, part: RunePart, currentTime: Long): Rune = {
    if (parts.get((x, y)).isDefined)
      this
    else {
      val newParts = parts + (x -> y -> part)
      Rune(newParts, RuneRegistry.findRuneEffect(newParts), (averageCreatingTime + (currentTime - lastTime)) / 2, currentTime)
    }
  }
}
