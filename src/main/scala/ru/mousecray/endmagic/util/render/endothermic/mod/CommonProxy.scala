package ru.mousecray.endmagic.util.render.endothermic.mod

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.network.{IGuiHandler, NetworkRegistry}

class CommonProxy extends IGuiHandler{
  def preinit(event: FMLPreInitializationEvent): Unit = {
    NetworkRegistry.INSTANCE.registerGuiHandler(Title, this)
  }

  def init(event: FMLInitializationEvent): Unit = {}

  def postinit(event: FMLPostInitializationEvent): Unit = {
    TestingStand.reload()
  }

  override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = null

  override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = null
}
