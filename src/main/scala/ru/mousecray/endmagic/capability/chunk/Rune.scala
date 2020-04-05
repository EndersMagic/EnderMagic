package ru.mousecray.endmagic.capability.chunk

import ru.mousecray.endmagic.capability.chunk.RuneEffect.EmptyEffect
import ru.mousecray.endmagic.rune.RuneEffectRegistry
import ru.mousecray.endmagic.util.Vec2i

case class Rune(parts: Map[Vec2i, RunePart], runeEffect: RuneEffect = EmptyEffect, averageCreatingTime: Long = Long.MaxValue, startingTime: Long = -1) {
  def add(coord: Vec2i, runePart: RunePart, currentTimeMillis: Long): Rune = {
    if (parts.contains(coord))
      this
    else {
      val newParts = parts + (coord -> runePart)
      val newEffect = RuneEffectRegistry.findEffect(newParts)

      if (parts.size == 1)
        Rune(newParts, newEffect, Long.MaxValue, currentTimeMillis)
      else if (runeEffect != EmptyEffect)
        Rune(newParts, newEffect, (currentTimeMillis - startingTime) / parts.size)
      else
        Rune(newParts, newEffect)
    }
  }

}

object Rune {
  val empty = new Rune(Map())

}
