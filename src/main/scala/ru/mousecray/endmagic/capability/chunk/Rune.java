package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.util.math.BlockPos;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.rune.RuneEffectRegistry;
import ru.mousecray.endmagic.util.Vec2i;

import java.util.HashMap;
import java.util.Map;

import static ru.mousecray.endmagic.capability.chunk.RuneEffect.EmptyEffect;

public class Rune {
    private final BlockPos pos;

    public Map<Vec2i, RunePart> parts = new HashMap<>();
    public RuneEffect runeEffect = EmptyEffect;
    public long averageCreatingTime = Long.MAX_VALUE;
    public long lastTime = -1;

    public Rune(BlockPos pos) {
        this.pos = pos;
    }

    public void add(Vec2i coord, RunePart runePart) {
        if (!parts.containsKey(coord)) {
            parts.put(coord, runePart);
            runeEffect = RuneEffectRegistry.findEffect(parts);

            long currentTimeMillis = System.currentTimeMillis();

            if (parts.size() == 1)
                lastTime = currentTimeMillis;
            else if (runeEffect != EmptyEffect) {
                long fullCreatingTime = currentTimeMillis - lastTime;
                averageCreatingTime = fullCreatingTime / parts.size();
            }
            
            EM.proxy.refreshChunk(pos);
        }
    }
}
