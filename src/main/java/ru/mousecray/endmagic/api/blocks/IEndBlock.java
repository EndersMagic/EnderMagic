package ru.mousecray.endmagic.api.blocks;

public interface IEndBlock {
	default EndBlockType getBlockType() {
		return EndBlockType.STONE;
	}
}