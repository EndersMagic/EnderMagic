package ru.mousecray.endmagic.runes

case class Rune(parts: Set[RunePart], runeEffect: RuneEffect = EmptyEffect) {

  val isFinished: Boolean = runeEffect != EmptyEffect

  def +(part: RunePart): Rune = {
    val newParts = parts + part
    copy(newParts, RuneRegistry.findRuneEffect(newParts))
  }

  def ++(rune: Rune): Rune = {
    val newParts = parts ++ rune.parts
    copy(newParts, RuneRegistry.findRuneEffect(newParts))
  }
}

object Rune {
  object EmptyRune extends Rune(Set())

}
