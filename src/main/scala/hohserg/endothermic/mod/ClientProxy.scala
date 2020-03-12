package hohserg.endothermic.mod

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}

class ClientProxy extends CommonProxy {

  override def preinit(event: FMLPreInitializationEvent): Unit = {
    super.preinit(event)

  }

  override def init(event: FMLInitializationEvent): Unit = {
    super.init(event)
    ClientRegistry.bindTileEntitySpecialRenderer(classOf[TestingStand.TileTestingStand], new TileTestingStandRenderer)
  }

  override def postinit(event: FMLPostInitializationEvent): Unit = {
    super.postinit(event)
  }

  override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = new GuiSelectBechmark(world.getTileEntity(new BlockPos(x,y,z)).asInstanceOf[TestingStand.TileTestingStand])

}
