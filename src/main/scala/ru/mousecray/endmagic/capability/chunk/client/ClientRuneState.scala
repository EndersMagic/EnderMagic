package ru.mousecray.endmagic.capability.chunk.client

import net.minecraft.util.EnumFacing
import ru.mousecray.endmagic.capability.chunk.{Rune, RuneState}

class ClientRuneState(runesOnSides: Array[Rune]) extends RuneState(runesOnSides) {

  val runeQuadsData: Map[EnumFacing, Set[QuadData]] = {
    val tuples: Array[(EnumFacing, Rune)] = EnumFacing.values().map(side => side -> runesOnSides.apply(side.ordinal()))
    tuples.map { case (side, rune) =>
    }

  }

}
