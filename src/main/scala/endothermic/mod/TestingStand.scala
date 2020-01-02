package endothermic.mod

import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ITickable
import net.minecraft.world.World

object TestingStand extends BlockContainer(Material.GLASS) {
  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = new TileTestingStand

  class TileTestingStand extends TileEntity with ITickable {
    override def update(): Unit = {}
  }

}
