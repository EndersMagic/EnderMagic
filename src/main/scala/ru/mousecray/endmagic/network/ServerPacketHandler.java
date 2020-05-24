package ru.mousecray.endmagic.network;

import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.INetHandlerPlayServer;
import ru.mousecray.endmagic.init.EMItems;

public class ServerPacketHandler implements ICustomPacketHandler.IServerPacketHandler {

    @Override
    public void handlePacket(PacketCustom packetCustom, EntityPlayerMP entityPlayerMP, INetHandlerPlayServer iNetHandlerPlayServer) {
        switch (PacketTypes.valueOf(packetCustom.getType())) {
            case CHANDE_DEMENSION:
                if (entityPlayerMP.capabilities.isCreativeMode || entityPlayerMP.inventory.hasItemStack(testItemStack())) {
                    int targetDim = packetCustom.readInt();
                    entityPlayerMP.changeDimension(targetDim);
                }
                break;
            default:
                break;
            case STRUCTURE_FIND:
                /*//to structure find todo
                World world2 = entityPlayerMP.getServerWorld();
                BlockPos pos = world2.findNearestStructure(packetCustom.readString(), entityPlayerMP.getPosition(), true);
                entityPlayerMP.attemptTeleport(pos.getX(), world2.getHeight(pos.getX(), pos.getZ()), pos.getZ());*/
                break;
        }
    }

    private ItemStack testItemStack;

    private ItemStack testItemStack() {
        if (testItemStack == null)
            testItemStack = new ItemStack(EMItems.test);
        return testItemStack;
    }
}
