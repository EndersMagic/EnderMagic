package ru.mousecray.endmagic.capability.chunk

import java.util.function.Function

import net.minecraft.util.EnumFacing
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
    case (_: LeftSideQuadData, _) => null
    case (_: RightSideQuadData, _) => null
    case (_: DownSideQuadData, _) => null
    case (_: UpSideQuadData, _) => null
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
