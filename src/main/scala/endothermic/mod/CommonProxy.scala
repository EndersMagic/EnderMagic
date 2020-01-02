package endothermic.mod

import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}

class CommonProxy {
  def preinit(event: FMLPreInitializationEvent): Unit = {}

  def init(event: FMLInitializationEvent): Unit = {}

  def postinit(event: FMLPostInitializationEvent): Unit = {}

}
