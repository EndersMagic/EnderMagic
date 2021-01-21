package ru.mousecray.endmagic.rune;

import ru.mousecray.endmagic.capability.chunk.RuneEffect;
import ru.mousecray.endmagic.capability.chunk.RunePart;
import ru.mousecray.endmagic.util.Vec2i;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static ru.mousecray.endmagic.capability.chunk.RuneEffect.EmptyEffect;

public class RuneEffectRegistry {
    private static Map<Map<Vec2i, RunePart>, RuneEffect> effects = new HashMap<>();

    public static RuneEffect findEffect(Map<Vec2i, RunePart> parts) {
        return effects.getOrDefault(nailToCenter(parts), EmptyEffect);
    }

    private static Map<Vec2i, RunePart> nailToCenter(Map<Vec2i, RunePart> parts) {
        return nail(parts, findLeft(parts).x, findBottom(parts).y);
    }

    private static Vec2i findLeft(Map<Vec2i, RunePart> parts) {
        return Collections.min(parts.keySet(), Comparator.comparingInt(o -> o.x));
    }

    private static Vec2i findBottom(Map<Vec2i, RunePart> parts) {
        return Collections.min(parts.keySet(), Comparator.comparingInt(o -> o.y));
    }

    private static Map<Vec2i, RunePart> nail(Map<Vec2i, RunePart> parts, int xl, int yl) {
        return parts.entrySet().stream().collect(toImmutableMap(p -> new Vec2i(p.getKey().x - xl, p.getKey().y - yl), Map.Entry::getValue));
    }

    public static void addEffect(Map<Vec2i, RunePart> parts, RuneEffect effect) {
        effects.put(nailToCenter(parts), effect);
        effects.put(nailToCenter(rotate(parts)), effect);
        effects.put(nailToCenter(rotate(rotate(parts))), effect);
        effects.put(nailToCenter(rotate(rotate(rotate(parts)))), effect);
    }

    private static Map<Vec2i, RunePart> rotate(Map<Vec2i, RunePart> parts) {
        //+y -> +x
        //+x - > -y
        //-y -> -x
        //-x -> +y
        return parts.entrySet().stream().collect(toImmutableMap(p -> new Vec2i(p.getKey().y, -p.getKey().x), Map.Entry::getValue));
    }
}