package ru.mousecray.endmagic.runes

import com.google.common.collect.ImmutableMap
import net.minecraft.util.EnumFacing
import ru.mousecray.endmagic.runes.Rune.EmptyRune

case class RuneState(sides: Map[EnumFacing, Rune] = EnumFacing.values().map((_, EmptyRune)).toMap) {
  def +(side: EnumFacing, part: RunePart): RuneState =
    RuneState(sides updated(side, sides(side) + part))
}

object RuneState {

  import collection.JavaConverters._

  object EmptyRuneState extends RuneState

  def apply(sides: ImmutableMap[EnumFacing, Rune]): RuneState = new RuneState(EmptyRuneState.sides ++ sides.asScala.toMap)

}

