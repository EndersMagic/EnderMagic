package ru.mousecray.endmagic.capability.player;

import net.minecraft.entity.player.EntityPlayer;
import ru.mousecray.endmagic.network.PacketTypes;

public class EmCapabilityServer extends EmCapabilityCommon {
    public EmCapabilityServer(EntityPlayer player) {
        super(player);
    }

    @Override
    public void setEm(int value) {
        super.setEm(value);
    }
}
