package ru.mousecray.endmagic.rune.effects;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.rune.RuneEffect;
import ru.mousecray.endmagic.rune.RuneIndex;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = EM.ID)
public class StabilityEffect extends RuneEffect {
    public static final StabilityEffect instance = new StabilityEffect();

    @SubscribeEvent
    public static void onDigBlock(PlayerEvent.BreakSpeed event) {
        double breakDeceleration =
                RuneIndex.getRuneState(event.getEntityPlayer().world, event.getPos())
                        .map(rs ->
                                Arrays.stream(EnumFacing.values()).map(rs::getRuneAtSide)
                                        .filter(r -> r.runeEffect() == instance)
                                        .reduce(0d, (acc, r) -> acc + r.runePower(), (a1, a2) -> a1 + a2))
                        .orElse(0d) + 1;
        event.setNewSpeed((float) (event.getOriginalSpeed()/breakDeceleration));
    }
}
