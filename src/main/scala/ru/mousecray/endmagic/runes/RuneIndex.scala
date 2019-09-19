package ru.mousecray.endmagic.runes

import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.relauncher.{Side, SideOnly}
import ru.mousecray.endmagic.runes.RuneState.EmptyRuneState
import ru.mousecray.endmagic.teleport.Location

import scala.collection.mutable

object RuneIndex {
  private val index = new mutable.OpenHashMap[(Block, Location), RuneState]().withDefaultValue(EmptyRuneState)

  def getRuneAt(pos: Location, block: Block): RuneState = index((block, pos))

  object client {
    @SideOnly(Side.CLIENT)
    def refreshChunk(pos: BlockPos): Unit =
      client.mc.renderGlobal.notifyBlockUpdate(
        client.world, pos, client.world.getBlockState(pos), client.world.getBlockState(pos), 2)

    @SideOnly(Side.CLIENT)
    private def mc: Minecraft = Minecraft.getMinecraft

    @SideOnly(Side.CLIENT)
    private def world: WorldClient = client.mc.world
  }


  def addRunePart(pos: Location, block: Block, face: EnumFacing, x: Int, y: Int, part: RunePart): Unit = {
    setRuneAt(pos, block,
      getRuneAt(pos, block) + (face, x, y, part, System.currentTimeMillis())
    )

  }


  def setRuneAt(pos: Location, block: Block, state: RuneState): Unit = {
    index.put((block, pos), state)
    if (FMLCommonHandler.instance().getEffectiveSide == Side.CLIENT)
      client.refreshChunk(pos.toPos)
  }


}
