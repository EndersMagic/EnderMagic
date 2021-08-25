package me.endersteam.endmagic.network

import net.minecraftforge.eventbus.api.Event

/**
 *  Событие для регистрации пакетов. Вызывается после FMLCommonSetupEvent. Для регистрации пакета необходимо вызвать [PacketRegisterEvent.register] от сериализатора пакета.
 */
class PacketRegisterEvent(private val packets : MutableSet<Packet.Serializer<out Packet>>) : Event()
{
    inline fun<reified T : Packet> register(serializer: Packet.Serializer<T>)
    {
        add(serializer)
    }

    fun add(value : Packet.Serializer<out Packet>) = packets.add(value)
}