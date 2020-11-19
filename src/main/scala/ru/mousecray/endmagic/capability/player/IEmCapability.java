package ru.mousecray.endmagic.capability.player;

import ru.mousecray.endmagic.rune.RuneColor;

public interface IEmCapability {
    double getEm(RuneColor color);

    void setEm(RuneColor color, double value);

    boolean comsumeEm(RuneColor color, double amount, double efficiency);

    double getMaxEm(RuneColor color);
}
