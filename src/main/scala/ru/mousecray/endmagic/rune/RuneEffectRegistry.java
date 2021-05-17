package ru.mousecray.endmagic.rune;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.capability.chunk.RunePart;
import ru.mousecray.endmagic.rune.effects.LightRuneEffect;
import ru.mousecray.endmagic.util.Vec2i;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static ru.mousecray.endmagic.rune.RuneColor.Fire;
import static ru.mousecray.endmagic.rune.RuneEffect.EmptyEffect;

public enum RuneEffectRegistry {
    instance;

    RuneEffectRegistry() {
        //addEffect(new HashMap<>(), new LightRuneEffect());
        //addEffect(new HashMap<>(), new HeatCatalystEffect());
    }

    private Map<Map<Vec2i, RunePart>, RuneEffect> effects = new HashMap<>();
    private Map<String, RuneEffect> effectByName = new HashMap<>();

    public RuneEffect findEffect(Map<Vec2i, RunePart> parts) {
        return effects.getOrDefault(nailToCenter(parts), EmptyEffect);
    }

    public RuneEffect getByName(String name) {
        return effectByName.getOrDefault(name, EmptyEffect);
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
        if (parts.isEmpty())
            throw new IllegalArgumentException("Parts of rune must have at least of one part");
        effects.put(nailToCenter(parts), effect);
        effects.put(nailToCenter(rotate(parts)), effect);
        effects.put(nailToCenter(rotate(rotate(parts))), effect);
        effects.put(nailToCenter(rotate(rotate(rotate(parts)))), effect);
        effectByName.put(effect.getName(), effect);
    }

    private Map<Vec2i, RunePart> rotate(Map<Vec2i, RunePart> parts) {
        //+y -> +x
        //+x - > -y
        //-y -> -x
        //-x -> +y
        return parts.entrySet().stream().collect(toImmutableMap(p -> new Vec2i(p.getKey().y, -p.getKey().x), Map.Entry::getValue));
    }
}
