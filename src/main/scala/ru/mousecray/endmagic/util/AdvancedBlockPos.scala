package ru.mousecray.endmagic.util

import net.minecraft.client.renderer.Vector3d
import net.minecraft.entity.Entity
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.{BlockPos, Vec3i}

class AdvancedBlockPos(private var xD : Double, private var yD : Double, private var zD : Double) extends BlockPos.MutableBlockPos
{
  def this(pos : BlockPos) = this(pos.getX, pos.getY, pos.getZ)

  override def getX: Int = xD.toInt

  override def getY: Int = yD.toInt

  override def getZ: Int = zD.toInt

  override def setPos(xIn: Int, yIn: Int, zIn: Int) : BlockPos.MutableBlockPos = {
    xD = xIn
    yD = yIn
    zD = zIn
    this
  }

  override def setPos(xIn: Double, yIn: Double, zIn: Double) : BlockPos.MutableBlockPos = {
    xD = xIn
    yD = yIn
    zD = zIn
    this
  }

  override def setPos(entityIn: Entity): BlockPos.MutableBlockPos = this.setPos(entityIn.posX, entityIn.posY, entityIn.posZ)

  override def setPos(vec: Vec3i): BlockPos.MutableBlockPos = this.setPos(vec.getX, vec.getY, vec.getZ)

  override def move(facing: EnumFacing): BlockPos.MutableBlockPos = this.move(facing, 1)

  override def move(facing: EnumFacing, n: Int): BlockPos.MutableBlockPos = this.setPos(this.xD + facing.getFrontOffsetX * n, this.yD + facing.getFrontOffsetY * n, this.zD + facing.getFrontOffsetZ * n)

  override def setY(yIn: Int): Unit = yD = yIn

  def move(x: Double, y: Double, z: Double): Unit = {
    this.xD += x
    this.yD += y
    this.zD += z
  }

  def move(vac : Vector3d): Unit = this.move(vac.x, vac.y, vac.z)

  def setPos(blockPos: BlockPos): Unit =
  {
      this.xD = blockPos.getX
      this.yD = blockPos.getY
      this.zD = blockPos.getZ
  }

  override def toString: String = "AdvancedBlockPos{" + xD + " " + yD + " " + zD + "}"
}
