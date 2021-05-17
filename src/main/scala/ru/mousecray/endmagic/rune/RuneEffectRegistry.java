package ru.mousecray.endmagic.rune;

import ru.mousecray.endmagic.capability.chunk.RunePart;
import ru.mousecray.endmagic.rune.effects.*;
import ru.mousecray.endmagic.util.Vec2i;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static ru.mousecray.endmagic.rune.RuneEffect.EmptyEffect;

public enum RuneEffectRegistry {
    instance;

    RuneEffectRegistry() {
        //addEffect(new HashMap<>(), new LightRuneEffect());
        //addEffect(new HashMap<>(), new HeatCatalystEffect());
    }

    private Map<Map<Vec2i, RunePart>, RuneEffect> effects = new HashMap<>();

    public RuneEffect findEffect(Map<Vec2i, RunePart> parts) {
        return effects.getOrDefault(nailToCenter(parts), EmptyEffect);
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

    public void addEffect(Map<Vec2i, RunePart> parts, RuneEffect effect) {
        effects.put(nailToCenter(parts), effect);
        effects.put(nailToCenter(rotate(parts)), effect);
        effects.put(nailToCenter(rotate(rotate(parts))), effect);
        effects.put(nailToCenter(rotate(rotate(rotate(parts)))), effect);
    }

    private Map<Vec2i, RunePart> rotate(Map<Vec2i, RunePart> parts) {
        //+y -> +x
        //+x - > -y
        //-y -> -x
        //-x -> +y
        return parts.entrySet().stream().collect(toImmutableMap(p -> new Vec2i(p.getKey().y, -p.getKey().x), Map.Entry::getValue));
    }
}
