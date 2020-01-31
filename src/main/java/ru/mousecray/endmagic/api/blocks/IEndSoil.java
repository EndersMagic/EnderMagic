package ru.mousecray.endmagic.api.blocks;

public interface IEndSoil {
	default EndSoilType getSoilType() {
		return EndSoilType.STONE;
	}
}