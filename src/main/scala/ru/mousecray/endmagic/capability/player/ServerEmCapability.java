package ru.mousecray.endmagic.capability.player;

import codechicken.lib.packet.PacketCustom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.network.PacketTypes;
import ru.mousecray.endmagic.rune.RuneColor;

@Mod.EventBusSubscriber(modid = EM.ID)
public class ServerEmCapability extends EmCapability {
    public ServerEmCapability(EntityPlayer player) {
        super(player);
    }

    @Override
    public void setEm(RuneColor color, int value) {
        super.setEm(color, value);
        PacketTypes.UPDATE_PLAYER_EM.packet().writeByte(color.ordinal()).writeInt(value).writeInt(getMaxEm(color)).sendToPlayer(player);
    }

    @Override
    public void setMaxEm(RuneColor color, int value) {
        super.setMaxEm(color, value);
        PacketTypes.UPDATE_PLAYER_EM.packet().writeByte(color.ordinal()).writeInt(getEm(color)).writeInt(value).sendToPlayer(player);
    }

    @SubscribeEvent
    public static void onPlayerEnter(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityPlayerMP) {
            EmCapability capa = EmCapabilityProvider.getCapa((EntityPlayerMP) event.getEntity());

            PacketCustom packet = PacketTypes.SYNC_PLAYER_EM_CAPABILITY.packet();

            for (RuneColor color : RuneColor.values())
                packet
                        .writeInt(capa.getEm(color))
                        .writeInt(capa.getMaxEm(color));

            packet.sendToPlayer((EntityPlayerMP) event.getEntity());
        }
    }
}
