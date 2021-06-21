package ru.mousecray.endmagic.rune;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import ru.mousecray.endmagic.capability.chunk.RunePart;
import ru.mousecray.endmagic.rune.effects.*;
import ru.mousecray.endmagic.util.Vec2i;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static ru.mousecray.endmagic.rune.RuneColor.Void;
import static ru.mousecray.endmagic.rune.RuneColor.*;
import static ru.mousecray.endmagic.rune.RuneEffect.EmptyEffect;

public enum RuneEffectRegistry {
    instance;

    RuneEffectRegistry() {
        addEffect(new HeatCatalystEffect(),
                ImmutableMap.<Vec2i, RunePart>builder()
                        .put(new Vec2i(2, 9), new RunePart(Fire))
                        .put(new Vec2i(11, 8), new RunePart(Fire))
                        .put(new Vec2i(3, 8), new RunePart(Fire))
                        .put(new Vec2i(4, 7), new RunePart(Fire))
                        .put(new Vec2i(5, 6), new RunePart(Fire))
                        .put(new Vec2i(9, 7), new RunePart(Fire))
                        .put(new Vec2i(8, 8), new RunePart(Fire))
                        .put(new Vec2i(8, 11), new RunePart(Fire))
                        .put(new Vec2i(14, 7), new RunePart(Fire))
                        .put(new Vec2i(8, 5), new RunePart(Fire))
                        .put(new Vec2i(11, 4), new RunePart(Fire))
                        .put(new Vec2i(1, 9), new RunePart(Fire))
                        .put(new Vec2i(13, 5), new RunePart(Fire))
                        .put(new Vec2i(6, 9), new RunePart(Fire))
                        .put(new Vec2i(7, 5), new RunePart(Fire))
                        .put(new Vec2i(14, 6), new RunePart(Fire))
                        .put(new Vec2i(6, 6), new RunePart(Fire))
                        .put(new Vec2i(11, 12), new RunePart(Fire))
                        .put(new Vec2i(10, 4), new RunePart(Fire))
                        .put(new Vec2i(12, 11), new RunePart(Fire))
                        .put(new Vec2i(13, 10), new RunePart(Fire))
                        .put(new Vec2i(10, 7), new RunePart(Fire))
                        .put(new Vec2i(10, 10), new RunePart(Fire))
                        .put(new Vec2i(9, 11), new RunePart(Fire))
                        .put(new Vec2i(9, 5), new RunePart(Fire))
                        .put(new Vec2i(5, 9), new RunePart(Fire))
                        .put(new Vec2i(12, 4), new RunePart(Fire))
                        .put(new Vec2i(7, 10), new RunePart(Fire))
                        .build());

        addEffect(new FrozingEffect(),
                ImmutableMap.<Vec2i, RunePart>builder()
                        .put(new Vec2i(5, 6), new RunePart(Cold))
                        .put(new Vec2i(9, 4), new RunePart(Cold))
                        .put(new Vec2i(9, 13), new RunePart(Cold))
                        .put(new Vec2i(10, 6), new RunePart(Cold))
                        .put(new Vec2i(9, 7), new RunePart(Cold))
                        .put(new Vec2i(3, 5), new RunePart(Cold))
                        .put(new Vec2i(11, 5), new RunePart(Cold))
                        .put(new Vec2i(8, 8), new RunePart(Cold))
                        .put(new Vec2i(8, 2), new RunePart(Cold))
                        .put(new Vec2i(8, 11), new RunePart(Cold))
                        .put(new Vec2i(8, 14), new RunePart(Cold))
                        .put(new Vec2i(8, 5), new RunePart(Cold))
                        .put(new Vec2i(6, 7), new RunePart(Cold))
                        .put(new Vec2i(13, 8), new RunePart(Cold))
                        .put(new Vec2i(14, 10), new RunePart(Cold))
                        .put(new Vec2i(10, 8), new RunePart(Cold))
                        .put(new Vec2i(3, 7), new RunePart(Cold))
                        .put(new Vec2i(4, 6), new RunePart(Cold))
                        .put(new Vec2i(13, 5), new RunePart(Cold))
                        .put(new Vec2i(8, 4), new RunePart(Cold))
                        .put(new Vec2i(12, 9), new RunePart(Cold))
                        .put(new Vec2i(3, 10), new RunePart(Cold))
                        .put(new Vec2i(8, 13), new RunePart(Cold))
                        .put(new Vec2i(2, 5), new RunePart(Cold))
                        .put(new Vec2i(7, 8), new RunePart(Cold))
                        .put(new Vec2i(8, 7), new RunePart(Cold))
                        .put(new Vec2i(6, 9), new RunePart(Cold))
                        .put(new Vec2i(8, 1), new RunePart(Cold))
                        .put(new Vec2i(8, 10), new RunePart(Cold))
                        .put(new Vec2i(7, 2), new RunePart(Cold))
                        .put(new Vec2i(7, 11), new RunePart(Cold))
                        .put(new Vec2i(3, 12), new RunePart(Cold))
                        .put(new Vec2i(5, 10), new RunePart(Cold))
                        .put(new Vec2i(4, 11), new RunePart(Cold))
                        .put(new Vec2i(13, 10), new RunePart(Cold))
                        .put(new Vec2i(12, 11), new RunePart(Cold))
                        .put(new Vec2i(11, 3), new RunePart(Cold))
                        .put(new Vec2i(8, 9), new RunePart(Cold))
                        .put(new Vec2i(9, 8), new RunePart(Cold))
                        .put(new Vec2i(8, 3), new RunePart(Cold))
                        .put(new Vec2i(9, 2), new RunePart(Cold))
                        .put(new Vec2i(11, 9), new RunePart(Cold))
                        .put(new Vec2i(8, 12), new RunePart(Cold))
                        .put(new Vec2i(7, 13), new RunePart(Cold))
                        .put(new Vec2i(7, 4), new RunePart(Cold))
                        .put(new Vec2i(9, 11), new RunePart(Cold))
                        .put(new Vec2i(8, 6), new RunePart(Cold))
                        .put(new Vec2i(7, 7), new RunePart(Cold))
                        .put(new Vec2i(4, 4), new RunePart(Cold))
                        .put(new Vec2i(13, 3), new RunePart(Cold))
                        .put(new Vec2i(12, 4), new RunePart(Cold))
                        .put(new Vec2i(5, 12), new RunePart(Cold))
                        .build());
        addEffect(new LightRuneEffect(),
                ImmutableMap.<Vec2i, RunePart>builder()
                        .put(new Vec2i(11, 8), new RunePart(Air))
                        .put(new Vec2i(9, 7), new RunePart(Fire))
                        .put(new Vec2i(10, 6), new RunePart(Fire))
                        .put(new Vec2i(11, 7), new RunePart(Fire))
                        .put(new Vec2i(10, 8), new RunePart(Fire))
                        .put(new Vec2i(9, 6), new RunePart(Air))
                        .put(new Vec2i(11, 6), new RunePart(Air))
                        .put(new Vec2i(9, 8), new RunePart(Air))
                        .put(new Vec2i(10, 7), new RunePart(Air))
                        .build());

        addEffect(new RedTransposEffect(),
                ImmutableMap.<Vec2i, RunePart>builder()
                        .put(new Vec2i(10, 6), new RunePart(Void))
                        .put(new Vec2i(9, 7), new RunePart(Void))
                        .put(new Vec2i(11, 5), new RunePart(Void))
                        .put(new Vec2i(8, 8), new RunePart(Void))
                        .put(new Vec2i(7, 9), new RunePart(Void))
                        .put(new Vec2i(12, 3), new RunePart(Fire))
                        .put(new Vec2i(9, 9), new RunePart(Fire))
                        .put(new Vec2i(11, 7), new RunePart(Fire))
                        .put(new Vec2i(12, 6), new RunePart(Fire))
                        .put(new Vec2i(13, 5), new RunePart(Fire))
                        .put(new Vec2i(13, 4), new RunePart(Fire))
                        .put(new Vec2i(8, 10), new RunePart(Fire))
                        .put(new Vec2i(7, 11), new RunePart(Fire))
                        .put(new Vec2i(10, 4), new RunePart(Fire))
                        .put(new Vec2i(5, 10), new RunePart(Fire))
                        .put(new Vec2i(11, 3), new RunePart(Fire))
                        .put(new Vec2i(9, 5), new RunePart(Fire))
                        .put(new Vec2i(5, 9), new RunePart(Fire))
                        .put(new Vec2i(6, 8), new RunePart(Fire))
                        .put(new Vec2i(7, 7), new RunePart(Fire))
                        .put(new Vec2i(6, 11), new RunePart(Fire))
                        .build());

        addEffect(new RuneTransposEffect(),
                ImmutableMap.<Vec2i, RunePart>builder()
                        .put(new Vec2i(11, 2), new RunePart(Fire))
                        .put(new Vec2i(9, 4), new RunePart(Earth))
                        .put(new Vec2i(10, 3), new RunePart(Earth))
                        .put(new Vec2i(5, 8), new RunePart(Fire))
                        .put(new Vec2i(6, 7), new RunePart(Earth))
                        .put(new Vec2i(7, 6), new RunePart(Earth))
                        .put(new Vec2i(6, 10), new RunePart(Fire))
                        .put(new Vec2i(11, 4), new RunePart(Void))
                        .put(new Vec2i(10, 5), new RunePart(Void))
                        .put(new Vec2i(9, 6), new RunePart(Void))
                        .put(new Vec2i(8, 7), new RunePart(Void))
                        .put(new Vec2i(7, 8), new RunePart(Void))
                        .put(new Vec2i(13, 4), new RunePart(Fire))
                        .put(new Vec2i(12, 2), new RunePart(Fire))
                        .put(new Vec2i(8, 9), new RunePart(Earth))
                        .put(new Vec2i(9, 8), new RunePart(Earth))
                        .put(new Vec2i(11, 6), new RunePart(Earth))
                        .put(new Vec2i(12, 5), new RunePart(Earth))
                        .put(new Vec2i(5, 9), new RunePart(Fire))
                        .put(new Vec2i(13, 3), new RunePart(Fire))
                        .put(new Vec2i(7, 10), new RunePart(Fire))
                        .build());

        addEffect(new StabilityEffect(),
                ImmutableMap.<Vec2i, RunePart>builder()
                        .put(new Vec2i(2, 9), new RunePart(Cold))
                        .put(new Vec2i(10, 9), new RunePart(Cold))
                        .put(new Vec2i(5, 6), new RunePart(Cold))
                        .put(new Vec2i(3, 8), new RunePart(Cold))
                        .put(new Vec2i(4, 7), new RunePart(Cold))
                        .put(new Vec2i(6, 14), new RunePart(Earth))
                        .put(new Vec2i(6, 5), new RunePart(Cold))
                        .put(new Vec2i(10, 12), new RunePart(Cold))
                        .put(new Vec2i(9, 13), new RunePart(Cold))
                        .put(new Vec2i(11, 11), new RunePart(Cold))
                        .put(new Vec2i(9, 4), new RunePart(Earth))
                        .put(new Vec2i(12, 10), new RunePart(Cold))
                        .put(new Vec2i(13, 9), new RunePart(Cold))
                        .put(new Vec2i(10, 6), new RunePart(Cold))
                        .put(new Vec2i(6, 13), new RunePart(Cold))
                        .put(new Vec2i(9, 10), new RunePart(Cold))
                        .put(new Vec2i(8, 11), new RunePart(Cold))
                        .put(new Vec2i(7, 12), new RunePart(Cold))
                        .put(new Vec2i(6, 4), new RunePart(Earth))
                        .put(new Vec2i(8, 5), new RunePart(Earth))
                        .put(new Vec2i(13, 8), new RunePart(Earth))
                        .put(new Vec2i(7, 6), new RunePart(Cold))
                        .put(new Vec2i(1, 9), new RunePart(Earth))
                        .put(new Vec2i(2, 8), new RunePart(Earth))
                        .put(new Vec2i(9, 9), new RunePart(Earth))
                        .put(new Vec2i(10, 8), new RunePart(Earth))
                        .put(new Vec2i(11, 7), new RunePart(Cold))
                        .put(new Vec2i(5, 5), new RunePart(Earth))
                        .put(new Vec2i(9, 12), new RunePart(Earth))
                        .put(new Vec2i(12, 9), new RunePart(Earth))
                        .put(new Vec2i(3, 10), new RunePart(Cold))
                        .put(new Vec2i(8, 13), new RunePart(Earth))
                        .put(new Vec2i(10, 5), new RunePart(Earth))
                        .put(new Vec2i(8, 7), new RunePart(Cold))
                        .put(new Vec2i(9, 6), new RunePart(Earth))
                        .put(new Vec2i(6, 12), new RunePart(Earth))
                        .put(new Vec2i(5, 13), new RunePart(Earth))
                        .put(new Vec2i(7, 5), new RunePart(Earth))
                        .put(new Vec2i(3, 9), new RunePart(Earth))
                        .put(new Vec2i(12, 8), new RunePart(Cold))
                        .put(new Vec2i(6, 6), new RunePart(Earth))
                        .put(new Vec2i(10, 13), new RunePart(Earth))
                        .put(new Vec2i(4, 11), new RunePart(Cold))
                        .put(new Vec2i(13, 10), new RunePart(Earth))
                        .put(new Vec2i(14, 9), new RunePart(Earth))
                        .put(new Vec2i(9, 8), new RunePart(Cold))
                        .put(new Vec2i(10, 10), new RunePart(Earth))
                        .put(new Vec2i(11, 9), new RunePart(Earth))
                        .put(new Vec2i(2, 10), new RunePart(Earth))
                        .put(new Vec2i(7, 13), new RunePart(Earth))
                        .put(new Vec2i(9, 14), new RunePart(Earth))
                        .put(new Vec2i(9, 5), new RunePart(Cold))
                        .put(new Vec2i(5, 12), new RunePart(Cold))
                        .build());


        addEffect(new LookseekEffect(),
                ImmutableMap.<Vec2i, RunePart>builder()
                        .put(new Vec2i(10, 9), new RunePart(Air))
                        .put(new Vec2i(11, 8), new RunePart(Air))
                        .put(new Vec2i(4, 7), new RunePart(Fire))
                        .put(new Vec2i(12, 7), new RunePart(Void))
                        .put(new Vec2i(11, 2), new RunePart(Fire))
                        .put(new Vec2i(4, 10), new RunePart(Fire))
                        .put(new Vec2i(11, 14), new RunePart(Fire))
                        .put(new Vec2i(10, 6), new RunePart(Void))
                        .put(new Vec2i(9, 7), new RunePart(Air))
                        .put(new Vec2i(8, 8), new RunePart(Void))
                        .put(new Vec2i(7, 9), new RunePart(Air))
                        .put(new Vec2i(6, 13), new RunePart(Fire))
                        .put(new Vec2i(9, 10), new RunePart(Void))
                        .put(new Vec2i(8, 2), new RunePart(Fire))
                        .put(new Vec2i(8, 14), new RunePart(Fire))
                        .put(new Vec2i(6, 7), new RunePart(Void))
                        .put(new Vec2i(14, 7), new RunePart(Fire))
                        .put(new Vec2i(5, 8), new RunePart(Void))
                        .put(new Vec2i(4, 9), new RunePart(Fire))
                        .put(new Vec2i(13, 8), new RunePart(Void))
                        .put(new Vec2i(7, 6), new RunePart(Void))
                        .put(new Vec2i(14, 10), new RunePart(Fire))
                        .put(new Vec2i(5, 11), new RunePart(Fire))
                        .put(new Vec2i(13, 11), new RunePart(Fire))
                        .put(new Vec2i(12, 3), new RunePart(Fire))
                        .put(new Vec2i(10, 8), new RunePart(Void))
                        .put(new Vec2i(9, 9), new RunePart(Air))
                        .put(new Vec2i(11, 7), new RunePart(Air))
                        .put(new Vec2i(4, 6), new RunePart(Fire))
                        .put(new Vec2i(5, 5), new RunePart(Fire))
                        .put(new Vec2i(13, 5), new RunePart(Fire))
                        .put(new Vec2i(11, 10), new RunePart(Void))
                        .put(new Vec2i(10, 2), new RunePart(Fire))
                        .put(new Vec2i(12, 9), new RunePart(Void))
                        .put(new Vec2i(10, 14), new RunePart(Fire))
                        .put(new Vec2i(7, 8), new RunePart(Air))
                        .put(new Vec2i(6, 9), new RunePart(Void))
                        .put(new Vec2i(8, 7), new RunePart(Air))
                        .put(new Vec2i(9, 6), new RunePart(Void))
                        .put(new Vec2i(13, 4), new RunePart(Fire))
                        .put(new Vec2i(6, 3), new RunePart(Fire))
                        .put(new Vec2i(8, 10), new RunePart(Void))
                        .put(new Vec2i(5, 4), new RunePart(Fire))
                        .put(new Vec2i(7, 2), new RunePart(Fire))
                        .put(new Vec2i(4, 8), new RunePart(Fire))
                        .put(new Vec2i(12, 8), new RunePart(Air))
                        .put(new Vec2i(14, 6), new RunePart(Fire))
                        .put(new Vec2i(7, 14), new RunePart(Fire))
                        .put(new Vec2i(14, 9), new RunePart(Fire))
                        .put(new Vec2i(8, 9), new RunePart(Air))
                        .put(new Vec2i(9, 8), new RunePart(Void))
                        .put(new Vec2i(11, 6), new RunePart(Void))
                        .put(new Vec2i(10, 7), new RunePart(Air))
                        .put(new Vec2i(10, 10), new RunePart(Void))
                        .put(new Vec2i(9, 2), new RunePart(Fire))
                        .put(new Vec2i(11, 9), new RunePart(Air))
                        .put(new Vec2i(9, 14), new RunePart(Fire))
                        .put(new Vec2i(6, 8), new RunePart(Air))
                        .put(new Vec2i(8, 6), new RunePart(Void))
                        .put(new Vec2i(7, 7), new RunePart(Air))
                        .put(new Vec2i(14, 8), new RunePart(Fire))
                        .put(new Vec2i(13, 12), new RunePart(Fire))
                        .put(new Vec2i(12, 13), new RunePart(Fire))
                        .put(new Vec2i(5, 12), new RunePart(Fire))
                        .put(new Vec2i(7, 10), new RunePart(Void))
                        .build()

        );

        /*
        Explosion like

        ImmutableMap.<Vec2i, RunePart>builder()
.put(new Vec2i(12, 7), new RunePart(Cold))
.put(new Vec2i(11, 11), new RunePart(Cold))
.put(new Vec2i(9, 4), new RunePart(Cold))
.put(new Vec2i(10, 12), new RunePart(Cold))
.put(new Vec2i(9, 13), new RunePart(Cold))
.put(new Vec2i(11, 2), new RunePart(Air))
.put(new Vec2i(12, 10), new RunePart(Cold))
.put(new Vec2i(1, 7), new RunePart(Air))
.put(new Vec2i(9, 1), new RunePart(Air))
.put(new Vec2i(6, 4), new RunePart(Cold))
.put(new Vec2i(14, 7), new RunePart(Air))
.put(new Vec2i(5, 2), new RunePart(Air))
.put(new Vec2i(12, 3), new RunePart(Air))
.put(new Vec2i(6, 1), new RunePart(Air))
.put(new Vec2i(9, 9), new RunePart(Earth))
.put(new Vec2i(1, 9), new RunePart(Air))
.put(new Vec2i(5, 5), new RunePart(Cold))
.put(new Vec2i(13, 5), new RunePart(Air))
.put(new Vec2i(8, 4), new RunePart(Cold))
.put(new Vec2i(12, 9), new RunePart(Cold))
.put(new Vec2i(10, 2), new RunePart(Air))
.put(new Vec2i(8, 13), new RunePart(Cold))
.put(new Vec2i(10, 5), new RunePart(Cold))
.put(new Vec2i(2, 5), new RunePart(Air))
.put(new Vec2i(6, 9), new RunePart(Earth))
.put(new Vec2i(8, 7), new RunePart(Earth))
.put(new Vec2i(1, 6), new RunePart(Air))
.put(new Vec2i(13, 4), new RunePart(Air))
.put(new Vec2i(8, 1), new RunePart(Air))
.put(new Vec2i(8, 10), new RunePart(Earth))
.put(new Vec2i(12, 8), new RunePart(Cold))
.put(new Vec2i(14, 6), new RunePart(Air))
.put(new Vec2i(2, 4), new RunePart(Air))
.put(new Vec2i(4, 2), new RunePart(Air))
.put(new Vec2i(3, 3), new RunePart(Air))
.put(new Vec2i(9, 8), new RunePart(Earth))
.put(new Vec2i(11, 6), new RunePart(Cold))
.put(new Vec2i(1, 8), new RunePart(Air))
.put(new Vec2i(7, 13), new RunePart(Cold))
.put(new Vec2i(7, 4), new RunePart(Cold))
.put(new Vec2i(14, 8), new RunePart(Air))
.put(new Vec2i(7, 1), new RunePart(Air))
.put(new Vec2i(7, 10), new RunePart(Earth))
.build()

         */
    }

    private Map<Map<Vec2i, RunePart>, RuneEffect> effects = new HashMap<>();
    private Map<String, Pair<Map<Vec2i, RunePart>, RuneEffect>> effectByName = new HashMap<>();

    public RuneEffect findEffect(Map<Vec2i, RunePart> parts) {
        return effects.getOrDefault(nailToCenter(parts), EmptyEffect);
    }

    public Pair<Map<Vec2i, RunePart>, RuneEffect> getByName(String name) {
        return effectByName.getOrDefault(name, Pair.of(null, EmptyEffect));
    }

    private Map<Vec2i, RunePart> nailToCenter(Map<Vec2i, RunePart> parts) {
        return nail(parts, findLeft(parts).x, findBottom(parts).y);
    }

    private Vec2i findLeft(Map<Vec2i, RunePart> parts) {
        return Collections.min(parts.keySet(), Comparator.comparingInt(o -> o.x));
    }

    private Vec2i findBottom(Map<Vec2i, RunePart> parts) {
        return Collections.min(parts.keySet(), Comparator.comparingInt(o -> o.y));
    }

    private Map<Vec2i, RunePart> nail(Map<Vec2i, RunePart> parts, int xl, int yl) {
        return parts.entrySet().stream().collect(toImmutableMap(p -> new Vec2i(p.getKey().x - xl, p.getKey().y - yl), Map.Entry::getValue));
    }

    public void addEffect(RuneEffect effect, Map<Vec2i, RunePart> parts) {
        if (parts.size() < 2)
            throw new IllegalArgumentException("Parts of rune must have at least of two part");
        effects.put(nailToCenter(parts), effect);
        effects.put(nailToCenter(rotate(parts)), effect);
        effects.put(nailToCenter(rotate(rotate(parts))), effect);
        effects.put(nailToCenter(rotate(rotate(rotate(parts)))), effect);
        effectByName.put(effect.getName(), Pair.of(parts, effect));
    }

    private Map<Vec2i, RunePart> rotate(Map<Vec2i, RunePart> parts) {
        //+y -> +x
        //+x - > -y
        //-y -> -x
        //-x -> +y
        return parts.entrySet().stream().collect(toImmutableMap(p -> new Vec2i(p.getKey().y, -p.getKey().x), Map.Entry::getValue));
    }
}
