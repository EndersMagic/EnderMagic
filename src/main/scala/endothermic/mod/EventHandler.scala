package endothermic.mod

import hohserg.endothermic.mod.TestingStand.TileTestingStand
import net.minecraft.block.Block
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry

@Mod.EventBusSubscriber(modid = Title.modid)
object EventHandler {
  @SubscribeEvent
  def onBlockRegistry(e: RegistryEvent.Register[Block]): Unit = {
    TestingStand.setRegistryName("testing_stand").setUnlocalizedName("testing_stand")
    e.getRegistry.register(TestingStand)
    GameRegistry.registerTileEntity(classOf[TileTestingStand], new ResourceLocation(Title.modid, classOf[TileTestingStand].getSimpleName))
  }

}
