package ru.mousecray.endmagic.rune;

import ru.mousecray.endmagic.capability.chunk.RuneEffect;
import ru.mousecray.endmagic.capability.chunk.RunePart;
import ru.mousecray.endmagic.util.Vec2i;

import java.util.Map;

import static ru.mousecray.endmagic.capability.chunk.RuneEffect.EmptyEffect;

public class RuneEffectRegistry {
    public static RuneEffect findEffect(Map<Vec2i, RunePart> parts) {
        return EmptyEffect;
    }
}
