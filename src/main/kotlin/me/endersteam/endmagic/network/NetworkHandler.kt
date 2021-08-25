@file:Mod.EventBusSubscriber
@file:JvmName("NetworkHandler")
package me.endersteam.endmagic.network

import me.endersteam.endmagic.modId
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.MinecraftForge.EVENT_BUS
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.network.NetworkRegistry
import net.minecraftforge.fml.network.simple.SimpleChannel
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

val defaultChannel : SimpleChannel = NetworkRegistry.newSimpleChannel(ResourceLocation(modId, "network"), { "2.0" }, { true }, { true })

@SubscribeEvent
fun initPacketSystem(event : FMLCommonSetupEvent)
{
    val packets : MutableSet<Packet.Serializer<out Packet>> = TreeSet()
    EVENT_BUS.post(PacketRegisterEvent(packets))

    val id = AtomicInteger(0)

    for (handler in packets)
        handler.register(defaultChannel, id)
}