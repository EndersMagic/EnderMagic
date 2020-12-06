package ru.mousecray.endmagic.capability.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.FMLCommonHandler;
import ru.mousecray.endmagic.rune.RuneColor;

import java.util.EnumMap;
import java.util.Map;

import static ru.mousecray.endmagic.Configuration.*;

public class EmCapability {
    protected final EntityPlayer player;

    public EmCapability(EntityPlayer player) {
        this.player = player;

        em = new EnumMap<>(RuneColor.class);
        maxEm = new EnumMap<>(RuneColor.class);
        for (RuneColor runeColor : RuneColor.values()) {
            int randomMaxValue = player.world.rand.nextInt(maxStartingEm - minStartingEm) + minStartingEm;
            em.put(runeColor, randomMaxValue);
            maxEm.put(runeColor, randomMaxValue);
        }
    }

    private Map<RuneColor, Integer> em;
    private Map<RuneColor, Integer> maxEm;

    public int getEm(RuneColor color) {
        return em.get(color);
    }

    public int getMaxEm(RuneColor color) {
        return maxEm.get(color);
    }

    public void setEm(RuneColor color, int value) {
        em.put(color, value);
    }

    public void setMaxEm(RuneColor color, int value) {
        maxEm.put(color, value);
    }

    public boolean consumeEm(RuneColor color, int amount, boolean soft) {
        int current = em.get(color);
        int surplus = current - amount;
        System.out.println(FMLCommonHandler.instance().getEffectiveSide() + " " + color + " " + surplus);
        if (surplus >= 0) {
            em.put(color, surplus);
            return true;
        } else if (!soft && consumeHPInsteadOfEm(-surplus)) {
            em.put(color, 0);
            return true;
        } else
            return false;
    }

    private boolean consumeHPInsteadOfEm(int amount) {
        float hpPrice = (float) (healthPointPerEmPoint * (amount));
        if (player.getHealth() - hpPrice > 1) {
            player.attackEntityFrom(DamageSource.GENERIC, hpPrice);
            return true;
        } else
            return false;
    }

    public boolean consumeEm(RuneColor color, int amount, double efficiency, boolean soft) {
        boolean ok = consumeEm(color, amount, soft);

        if (ok) {
            int other = (int) ((amount * (1 - efficiency)) / (RuneColor.values().length - 1));

            for (RuneColor otherColor : RuneColor.values())
                if (otherColor != color)
                    consumeEm(otherColor, other, true);

            return true;
        } else
            return false;
    }
}
