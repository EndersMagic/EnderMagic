package ru.mousecray.endmagic.network;

public enum PacketTypes {
    UPDATE_COMPAS_TARGET,
    UPDATE_PHANROM_AVOIDINCAPABILITY;
    
	public int id = ordinal() + 1;
}