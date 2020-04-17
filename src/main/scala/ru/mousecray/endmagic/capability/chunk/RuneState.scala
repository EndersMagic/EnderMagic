package ru.mousecray.endmagic.capability.chunk

import java.util.function.Function

import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumFacing._
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.relauncher.Side
import ru.mousecray.endmagic.capability.chunk.client._

class RuneState private(protected val runesOnSides: Array[Rune]) {

  def getRuneAtSide(facing: EnumFacing): Rune =
    if (facing == null) Rune.empty
    else runesOnSides(facing.ordinal)

  def withRune(facing: EnumFacing, rune: Rune): RuneState = {
    val newRunesOnSides = runesOnSides.clone()
    newRunesOnSides(facing.ordinal) = rune
    new RuneState(newRunesOnSides)
  }

  def withRune(facing: EnumFacing, runeMapping: Function[Rune, Rune]): RuneState =
    withRune(facing, runeMapping.apply(getRuneAtSide(facing)))


  val visibleAtFace: ((QuadData, EnumFacing)) => EnumFacing = {
    case (_: BottomQuadData, runeSide) => runeSide
    case (_: ElongateQuadData, runeSide) => runeSide
    case (_: LeftSideQuadData, runeSide) =>
      runeSide match {
        case DOWN => EAST
        case UP => EAST
        case EAST => NORTH
        case NORTH => WEST
        case SOUTH => EAST
        case WEST => SOUTH
      }
    case (_: RightSideQuadData, runeSide) =>
      runeSide match {
        case DOWN => WEST
        case UP => WEST
        case EAST => SOUTH
        case NORTH => EAST
        case SOUTH => WEST
        case WEST => NORTH
      }
    case (_: DownSideQuadData, runeSide) =>
      runeSide match {
        case DOWN => SOUTH
        case UP => NORTH
        case EAST => UP
        case NORTH => UP
        case SOUTH => UP
        case WEST => UP
      }
    case (_: UpSideQuadData, runeSide) =>
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
    if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
      EnumFacing.values()
        .map(side => side -> runesOnSides.apply(side.ordinal()))
        .flatMap { case (side, rune) =>
          rune.quadsData.map(_ -> side)
        }
        .toSet
        .groupBy(visibleAtFace)
    else
      Map.empty

}

object RuneState {
  var empty = new RuneState(new Array[Rune](EnumFacing.values.length).map(_ => Rune.empty))
}
