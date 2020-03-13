package ru.mousecray.endmagic.util.render.endothermic.mod

import net.minecraft.command.{CommandBase, ICommandSender}
import net.minecraft.server.MinecraftServer

class CommandReload extends CommandBase {
  override def getName: String = "reload"

  override def getUsage(sender: ICommandSender): String = "/reload"

  override def execute(server: MinecraftServer, sender: ICommandSender, args: Array[String]): Unit =
    TestingStand.reload()
}
