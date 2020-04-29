package ru.mousecray.endmagic.network;

import codechicken.lib.packet.PacketCustom;
import ru.mousecray.endmagic.EM;

public enum PacketTypes {
    UPDATE_COMPAS_TARGET,
    UPDATE_PHANROM_AVOIDINCAPABILITY,
    CHANDE_DEMENSION,
    STRUCTURE_FIND;//to structure find todo
    public int id = ordinal() + 1;

    public static PacketTypes valueOf(int id) {
        return values()[id - 1];
    }

    public PacketCustom packet() {
        return new PacketCustom(EM.ID, id);
    }
}