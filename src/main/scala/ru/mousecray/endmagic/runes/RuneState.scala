package ru.mousecray.endmagic.runes

import com.google.common.collect.ImmutableMap
import net.minecraft.util.EnumFacing

case class RuneState(sides: Map[EnumFacing, Rune]) {
  def +(side: EnumFacing, x: Int, y: Int, part: RunePart, currentTime: Long): RuneState =
    RuneState(
      sides updated(side,
        sides.get(side).map(_ + (x, y, part, currentTime)).getOrElse(Rune(Map(x -> y -> part))))
    )
}

object RuneState {

  import collection.JavaConverters._

  object EmptyRuneState extends RuneState(Map())

  def apply(sides: ImmutableMap[EnumFacing, Rune]): RuneState = new RuneState(sides.asScala.toMap)

}

