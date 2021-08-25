@file:JvmName("GameUtils")
package me.endersteam.endmagic.other

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.player.ClientPlayerEntity
import net.minecraft.client.world.ClientWorld
import net.minecraft.server.MinecraftServer
import net.minecraftforge.fml.server.ServerLifecycleHooks

@get:JvmName("getClientWorld")
val clientWorld : ClientWorld?         get() = try { Minecraft.getInstance().world  } catch (e : Exception) { null }

@get:JvmName("getClientPlayer")
val clientPlayer : ClientPlayerEntity? get() = try { Minecraft.getInstance().player } catch (e : Exception) { null }

@get:JvmName("getClient")
val client : Minecraft?                get() = try { Minecraft.getInstance()        } catch (e : Exception) { null }

@get:JvmName("getServer")
val server : MinecraftServer?          get() = client?.integratedServer ?: ServerLifecycleHooks.getCurrentServer()