package endothermic.mod

import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.{Mod, SidedProxy}

@Mod(modid = Title.modid,modLanguage = "scala")
object Title {
  final val modid = "endothermic"
  @SidedProxy(clientSide = "hohserg.endothermic.mod.ClientProxy", serverSide = "hohserg.endothermic.mod.CommonProxy")
  var proxy: CommonProxy = _

  @Mod.EventHandler def preinit(event: FMLPreInitializationEvent): Unit = proxy.preinit(event)

  @Mod.EventHandler def init(event: FMLInitializationEvent): Unit = proxy.init(event)

  @Mod.EventHandler def postinit(event: FMLPostInitializationEvent): Unit = proxy.postinit(event)

}
