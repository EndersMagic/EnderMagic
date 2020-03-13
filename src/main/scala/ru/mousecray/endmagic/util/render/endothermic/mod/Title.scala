package ru.mousecray.endmagic.util.render.endothermic.mod

import net.minecraftforge.fml.common.event._
import net.minecraftforge.fml.common.{Mod, SidedProxy}

@Mod(modid = Title.modid, name="Endothermic",modLanguage = "scala")
object Title {
  final val modid = "endothermic"
  @SidedProxy(clientSide = "ru.mousecray.endmagic.util.render.endothermic.mod.ClientProxy", serverSide = "ru.mousecray.endmagic.util.render.endothermic.mod.CommonProxy")
  var proxy: CommonProxy = _

  @Mod.EventHandler def preinit(event: FMLPreInitializationEvent): Unit = proxy.preinit(event)

  @Mod.EventHandler def init(event: FMLInitializationEvent): Unit = proxy.init(event)

  @Mod.EventHandler def postinit(event: FMLPostInitializationEvent): Unit = proxy.postinit(event)

  @Mod.EventHandler def serverInit(event: FMLServerStartingEvent): Unit = event.registerServerCommand(new CommandReload)

}
