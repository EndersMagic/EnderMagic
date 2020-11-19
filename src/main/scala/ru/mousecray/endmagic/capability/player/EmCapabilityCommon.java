package ru.mousecray.endmagic.capability.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import ru.mousecray.endmagic.rune.RuneColor;

import java.util.HashMap;

import static ru.mousecray.endmagic.Configuration.healthPointPerEmPoint;

public class EmCapabilityCommon implements IEmCapability {
    protected final EntityPlayer player;
    private HashMap<String, Double> em;
    private HashMap<String, Double> maxEm;

    public EmCapabilityCommon(EntityPlayer player) {
        this.player = player;
        em = new HashMap<>();
        for (RuneColor color : RuneColor.values()) {
            double max = player.world.rand.nextInt(1000);
            maxEm.put(color.name(), max);
            em.put(color.name(), max);
        }

    }

    @Override
    public double getEm(RuneColor color) {
        return em.get(color.name());
    }

    @Override
    public void setEm(RuneColor color, double value) {
        em.put(color.name(), value);
    }

    private boolean consumeOneEm(RuneColor color, double amount) {
        double surplus = getEm(color) - amount;

        if (surplus >= 0) {
            setEm(color, surplus);
            return true;
        } else {
            float requiredHP = (float) (healthPointPerEmPoint * (-surplus));
            if (player.getHealth() > requiredHP) {
                setEm(color, 0);
                player.attackEntityFrom(DamageSource.MAGIC, requiredHP);
                return true;
            } else
                return false;
        }
    }

    @Override
    public boolean comsumeEm(RuneColor color, double amount, double efficiency) {
        boolean ok = consumeOneEm(color, amount);

        if (ok) {
            double other = (amount * (1 - efficiency)) / (RuneColor.values().length - 1);
            for (RuneColor runeColor : RuneColor.values())
                if (runeColor != color)
                    consumeOneEm(runeColor, other);
            return true;
        } else
            return false;
    }

    @Override
    public double getMaxEm(RuneColor color) {
        return maxEm.get(color.name());
    }
}
