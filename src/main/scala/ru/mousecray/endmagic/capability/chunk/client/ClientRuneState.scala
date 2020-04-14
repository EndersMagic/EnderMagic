package ru.mousecray.endmagic.capability.chunk.client

import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumFacing._
import ru.mousecray.endmagic.capability.chunk.{Rune, RuneState}

class ClientRuneState(runesOnSides: Array[Rune]) extends RuneState(runesOnSides) {

  val visibleAtFace: ((QuadData, EnumFacing)) => EnumFacing = {
    case (quad: BottomQuadData, runeSide) => runeSide
    case (quad: ElongateQuadData, runeSide) => runeSide
    case (quad: LeftSideQuadData, runeSide) =>
      runeSide match {
        case DOWN => EAST
        case UP => EAST
        case EAST => NORTH
        case NORTH => WEST
        case SOUTH => EAST
        case WEST => SOUTH
      }
    case (quad: RightSideQuadData, runeSide) =>
      runeSide match {
        case DOWN => WEST
        case UP => WEST
        case EAST => SOUTH
        case NORTH => EAST
        case SOUTH => WEST
        case WEST => NORTH
      }
    case (quad: DownSideQuadData, runeSide) =>
      runeSide match {
        case DOWN => SOUTH
        case UP => NORTH
        case EAST => UP
        case NORTH => UP
        case SOUTH => UP
        case WEST => UP
      }
    case (quad: UpSideQuadData, runeSide) =>
      runeSide match {
        case DOWN => NORTH
        case UP => SOUTH
        case EAST => DOWN
        case NORTH => DOWN
        case SOUTH => DOWN
        case WEST => DOWN
      }
  }

  val runeQuadsData: Map[EnumFacing, Set[(QuadData, EnumFacing)]] =
    EnumFacing.values()
      .map(side => side -> runesOnSides.apply(side.ordinal()))
      .flatMap { case (side, rune) =>
        rune.quadsData.map(_ -> side)
      }
      .toSet
      .groupBy(visibleAtFace)


}
