package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class ClientRuneState extends RuneState {

    private Map<Pair<BakedQuad, EnumFacing>, Set<BakedQuad>> runeQuads = new HashMap<>();
    private Map<EnumFacing, Function<BakedQuad, Set<BakedQuad>>> runeQuadsMappers = new HashMap<>();

    public Set<BakedQuad> runeQuadsCached(BakedQuad quad, EnumFacing side) {
        return runeQuads.computeIfAbsent(Pair.of(quad, side), this::computeQuads);
    }

    private Set<BakedQuad> computeQuads(Pair<BakedQuad, EnumFacing> bakedQuadEnumFacingPair) {

    }

    protected ClientRuneState(Rune[] runesOnSides) {
        super(runesOnSides);
    }

    @Override
    public RuneState withRune(EnumFacing facing, Rune rune) {
        Rune[] newRunesOnSides = runesOnSides.clone();
        newRunesOnSides[facing.ordinal()] = rune;
        return new RuneState(newRunesOnSides);
    }
}
