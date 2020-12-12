package ru.mousecray.endmagic.capability.player;

import net.minecraft.entity.player.EntityPlayer;
import ru.mousecray.endmagic.rune.RuneColor;

import java.util.EnumMap;
import java.util.Map;

public class ClientEmCapability extends EmCapability {

    private Map<RuneColor, Integer> prevEm = new EnumMap<>(RuneColor.class);
    private Map<RuneColor, Integer> prevMaxEm = new EnumMap<>(RuneColor.class);

    public ClientEmCapability(EntityPlayer player) {
        super(player);
    }

    @Override
    public void setEm(RuneColor color, int value) {
        prevEm.put(color, getEm(color));
        super.setEm(color, value);
    }

    @Override
    public void setMaxEm(RuneColor color, int value) {
        prevMaxEm.put(color, getMaxEm(color));
        super.setMaxEm(color, value);
    }

    public void dismissPrevs() {
        for (RuneColor color : RuneColor.values()) {
            prevEm.put(color, getEm(color));
            prevMaxEm.put(color, getMaxEm(color));
        }
    }
}
