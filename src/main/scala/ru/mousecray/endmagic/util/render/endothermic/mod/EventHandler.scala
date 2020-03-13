package ru.mousecray.endmagic.util.render.endothermic.mod

import ru.mousecray.endmagic.util.render.endothermic.mod.TestingStand.TileTestingStand
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemBlock}
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry

@Mod.EventBusSubscriber(modid = Title.modid)
object EventHandler {
  private val testing_stand = "testing_stand"

  @SubscribeEvent
  def onBlockRegistry(e: RegistryEvent.Register[Block]): Unit = {
    e.getRegistry.register(TestingStand.setRegistryName(testing_stand).setUnlocalizedName(testing_stand))
    GameRegistry.registerTileEntity(classOf[TileTestingStand], new ResourceLocation(Title.modid, classOf[TileTestingStand].getSimpleName))
  }

  @SubscribeEvent
  def onItemRegistry(e: RegistryEvent.Register[Item]): Unit = {
    e.getRegistry.register(new ItemBlock(TestingStand).setRegistryName(testing_stand).setUnlocalizedName(testing_stand).setCreativeTab(CreativeTabs.TOOLS))
  }

}
