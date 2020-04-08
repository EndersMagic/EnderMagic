package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class RuneState {
    public static RuneState empty = new RuneState(
            Stream.generate(Rune::empty)
                    .limit(EnumFacing.values().length)
                    .toArray(Rune[]::new)
    );

    protected final Rune[] runesOnSides;

    protected RuneState(Rune[] runesOnSides) {
        this.runesOnSides = runesOnSides;
        assert (runesOnSides.length == EnumFacing.values().length);
    }

    public Rune getRuneAtSide(EnumFacing facing) {
        return facing == null ? Rune.empty() : runesOnSides[facing.ordinal()];
    }

    public RuneState withRune(EnumFacing facing, Rune rune) {
        Rune[] newRunesOnSides = runesOnSides.clone();
        newRunesOnSides[facing.ordinal()] = rune;
        return new RuneState(newRunesOnSides);
    }

    public RuneState withRune(EnumFacing facing, Function<Rune, Rune> runeMapping) {
        return withRune(facing, runeMapping.apply(getRuneAtSide(facing)));
    }
}
