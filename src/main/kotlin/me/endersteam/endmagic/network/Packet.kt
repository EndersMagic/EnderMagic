package me.endersteam.endmagic.network

import io.netty.buffer.ByteBuf
import me.endersteam.endmagic.other.clientPlayer
import net.minecraft.client.entity.player.ClientPlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.network.PacketBuffer
import net.minecraft.util.RegistryKey
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.LogicalSide
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.fml.network.PacketDistributor
import net.minecraftforge.fml.network.simple.SimpleChannel
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Supplier
import kotlin.reflect.KClass

abstract class Packet
{
    @OnlyIn(Dist.CLIENT)
    open fun processClient(player: ClientPlayerEntity?) {}

    @OnlyIn(Dist.DEDICATED_SERVER)
    open fun processServer(player: ServerPlayerEntity?) {}

    abstract class Serializer<PacketType : Packet>(protected val clazz : KClass<PacketType>)
    {
        abstract fun encode(packet: PacketType, buf: PacketBuffer): ByteBuf

        abstract fun decode(buf: PacketBuffer): PacketType

        @Suppress("INACCESSIBLE_TYPE")
        internal open fun register(channel : SimpleChannel, id : AtomicInteger)
        {
            channel.registerMessage(id.get(), clazz.java, ::encode, ::decode)
            { packet: Packet, context: Supplier<NetworkEvent.Context> ->
                val ctx: NetworkEvent.Context = context.get()
                if (ctx.direction.receptionSide == LogicalSide.CLIENT)
                    packet.processClient(clientPlayer)
                else
                    packet.processServer(ctx.sender)
                ctx.packetHandled = true
            }
            id.incrementAndGet()
        }
    }

    /**
     * Посылает пакет с сервера ВСЕМ игрокам на сервере.
     */
    open fun sendToAllPlayers() = defaultChannel.send(PacketDistributor.ALL.noArg(), this)

    /**
     * Посылает пакет с клиента на сервер.
     */
    open fun sendToServer() = defaultChannel.send(PacketDistributor.SERVER.noArg(), this)

    /**
     * Посылкает пакет с сервера на клиент всем игрокам в измерении.
     */
    open fun sendToWorld(type : RegistryKey<World>) = defaultChannel.send(PacketDistributor.DIMENSION.with { type }, this)

    /**
     * Посылкает пакет с сервера на клиент конкретному игроку.
     */
    open fun sendToPlayer(player : ServerPlayerEntity) = defaultChannel.send(PacketDistributor.PLAYER.with { player }, this)
}