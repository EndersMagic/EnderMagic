package ru.mousecray.endmagic.network;

import codechicken.lib.packet.PacketCustom;
import ru.mousecray.endmagic.EM;

public enum PacketTypes {
    UPDATE_COMPAS_TARGET,
    UPDATE_PHANROM_AVOIDINCAPABILITY,
    SYNC_RUNE_CAPABILITY,
    ADDED_RUNE_PART,
    REMOVE_RUNE_STATE,
    UPDATE_PLAYER_EM,
    SYNC_PLAYER_EM_CAPABILITY,
    ;

    public int id = ordinal() + 1;

    public static PacketTypes valueOf(int id) {
        return values()[id - 1];
    }

    public PacketCustom packet() {
        return new PacketCustom(EM.ID, id);
    }
}