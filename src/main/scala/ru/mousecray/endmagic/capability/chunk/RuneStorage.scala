package ru.mousecray.endmagic.capability.chunk

import net.minecraft.nbt.{NBTBase, NBTTagByte, NBTTagCompound}
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraftforge.common.capabilities.Capability
import ru.mousecray.endmagic.util.Java2Scala._
import ru.mousecray.endmagic.util.Vec2i

import scala.collection.JavaConverters._
import scala.collection.mutable

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
          instance.removeRuneState(string2pos(key))
          val state = instance.createRuneStateIfAbsent(string2pos(key))
          runeStateNbt.getKeySet.asScala.foreach {
            sideName =>
              val side = EnumFacing.byName(sideName)
              val runeNbt = runeStateNbt.getCompoundTag(sideName)
              val rune = state.getRuneAtSide(side)
              rune.averageCreatingTime = runeNbt.getLong("averageCreatingTime")
              rune.startingTime = runeNbt.getLong("startingTime")
              rune.runeEffect = nbtToRuneEffect(runeNbt.getTag("runeEffect"))
              rune.parts.clear()
              rune.parts ++= nbtToParts(runeNbt.getCompoundTag("parts"))
          }
        }
      case _ =>
    }


  def nbtToParts(compound: NBTTagCompound): mutable.HashMap[Vec2i, RunePart] = {
    val r = new mutable.HashMap[Vec2i, RunePart]()
    compound.getKeySet.asScala.foreach(key => r += string2vec2i(key) -> nbtToTunePart(compound.getTag(key)))
    r
  }


  def runeToNBT(rune: Rune): NBTBase = {
    val nbt = new NBTTagCompound

    nbt.setLong("averageCreatingTime", rune.averageCreatingTime)
    nbt.setLong("startingTime", rune.startingTime)
    nbt.setString("runeEffect", rune.runeEffect.toString)

    val partsNBT = new NBTTagCompound
    rune.parts.foreach { case (coord, part) => partsNBT.setTag(vec2i2string(coord), runePartToNBT(part)) }
    nbt.setTag("parts", partsNBT)

    nbt
  }

  def nbtToRuneEffect(base: NBTBase): RuneEffect = RuneEffect.EmptyEffect

  def runePartToNBT(part: RunePart): NBTBase = new NBTTagByte(0)

  def nbtToTunePart(base: NBTBase): RunePart = new RunePart

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
