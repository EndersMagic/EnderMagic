package ru.mousecray.endmagic.runes;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.EnumFaceDirection;

public class RuneState {
    public ImmutableMap<EnumFaceDirection, Rune> sides = ImmutableMap.of();
}
