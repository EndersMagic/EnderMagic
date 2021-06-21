package ru.mousecray.endmagic.capability.chunk

import net.minecraft.nbt.{NBTBase, NBTTagByte, NBTTagCompound}
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.relauncher.Side
import ru.mousecray.endmagic.rune.RuneEffectRegistry
import ru.mousecray.endmagic.util.Java2Scala._
import ru.mousecray.endmagic.util.Vec2i

import scala.collection.JavaConverters._

class RuneStorage extends Capability.IStorage[IRuneChunkCapability] {

  override def writeNBT(capability: Capability[IRuneChunkCapability], instance: IRuneChunkCapability, side: EnumFacing): NBTBase =
    if (instance.existingRunes.size > 0) {
      val instanceNBT = new NBTTagCompound
      instance.existingRunes.forEach((pos: BlockPos, runeState: RuneState) => instanceNBT.setTag(pos2string(pos), runeStateToNBT(runeState)))
      instanceNBT
    } else
      new NBTTagCompound

  override def readNBT(capability: Capability[IRuneChunkCapability], instance: IRuneChunkCapability, side: EnumFacing, nbt: NBTBase): Unit =
    nbt match {
      case instanceNBT: NBTTagCompound =>
        instance.existingRunes().clear()
        instanceNBT.getKeySet.forEach { key: String =>
          val runeStateNbt = instanceNBT.getCompoundTag(key)
          val newState = instance.createRuneStateIfAbsent(string2pos(key))
          runeStateNbt.getKeySet.asScala.foreach {
            sideName =>
              val side = EnumFacing.byName(sideName)
              val runeNbt = runeStateNbt.getCompoundTag(sideName)
              val newRune = newState.getRuneAtSide(side)
              val nbtParts = runeNbt.getCompoundTag("parts")
              nbtParts.getKeySet.asScala.foreach { key =>
                val coord = string2vec2i(key)
                val runePart = nbtToTunePart(nbtParts.getByte(key))
                newRune.parts += coord -> runePart
                if (FMLCommonHandler.instance().getEffectiveSide == Side.CLIENT)
                  newState.incrementQuadsData(newRune, coord, runePart)
              }
              newRune.averageCreatingTime = runeNbt.getLong("averageCreatingTime")
              newRune.startingTime = runeNbt.getLong("startingTime")
              newRune.runeEffect = RuneEffectRegistry.instance.getByName(runeNbt.getString("runeEffect")).getRight
              newRune.runePower = runeNbt.getDouble("runePower")
          }
        }
      case _ =>
    }


  def runeToNBT(rune: Rune): NBTBase = {
    val nbt = new NBTTagCompound

    nbt.setLong("averageCreatingTime", rune.averageCreatingTime)
    nbt.setLong("startingTime", rune.startingTime)
    nbt.setString("runeEffect", rune.runeEffect.getName)
    nbt.setDouble("runePower", rune.runePower)

    val partsNBT = new NBTTagCompound
    rune.parts.foreach { case (coord, part) => partsNBT.setTag(vec2i2string(coord), runePartToNBT(part)) }
    nbt.setTag("parts", partsNBT)

    nbt
  }

  def runePartToNBT(part: RunePart): NBTBase = new NBTTagByte(part.color)

  def nbtToTunePart(color: Byte): RunePart = new RunePart(color)

  def runeStateToNBT(runeState: RuneState): NBTTagCompound =
    EnumFacing.values().foldLeft(new NBTTagCompound) {
      case (nbt, side) =>
        nbt.setTag(side.getName, runeToNBT(runeState.getRuneAtSide(side)))
        nbt
    }


  private def vec2i2string(coord: Vec2i) = "" + coord.x + "|" + coord.y

  private def string2vec2i(coord: String) = {
    val split = coord.split('|')
    new Vec2i(split(0).toInt, split(1).toInt)
  }

  private def pos2string(pos: BlockPos) = "" + pos.getX + "|" + pos.getY + "|" + pos.getZ

  private def string2pos(pos: String) = {
    val split = pos.split('|')
    new BlockPos(split(0).toInt, split(1).toInt, split(2).toInt)
  }

}
