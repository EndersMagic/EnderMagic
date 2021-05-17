package ru.mousecray.endmagic.rune;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.capability.chunk.IRuneChunkCapability;
import ru.mousecray.endmagic.capability.chunk.Rune;
import ru.mousecray.endmagic.capability.chunk.RuneState;

import java.util.Collection;
import java.util.Map;

@Mod.EventBusSubscriber(modid = EM.ID)
public class RuneSupportHandler {

    @SubscribeEvent
    public static void onBlockChangeBreak(BlockEvent.NeighborNotifyEvent event) {
        World world = event.getWorld();
        BlockPos changedPos = event.getPos();

        Collection<IRuneChunkCapability> near = getRuneCapaAroundPos(world, changedPos);

        for (IRuneChunkCapability runeChunkCapability : near) {
            for (EnumFacing facing : EnumFacing.values()) {
                BlockPos runePos = changedPos.offset(facing);
                EnumFacing runeSide = facing.getOpposite();
                runeChunkCapability.getRuneState(runePos)
                        .ifPresent(runeState -> notifyRuneAboutNeighborChange(runeState, world, runePos, runeSide));
            }
        }
    }

    private static Collection<IRuneChunkCapability> getRuneCapaAroundPos(World world, BlockPos pos) {
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;

        ImmutableList.Builder<IRuneChunkCapability> r = ImmutableList.builder();
        r.add(RuneIndex.getCapability(world.getChunkFromChunkCoords(chunkX, chunkZ)));

        if (pos.getX() % 16 == 0)
            r.add(RuneIndex.getCapability(world.getChunkFromChunkCoords(chunkX - 1, chunkZ)));
        else if (pos.getX() % 16 == 15)
            r.add(RuneIndex.getCapability(world.getChunkFromChunkCoords(chunkX + 1, chunkZ)));


        if (pos.getZ() % 16 == 0)
            r.add(RuneIndex.getCapability(world.getChunkFromChunkCoords(chunkX, chunkZ - 1)));

        else if (pos.getZ() % 16 == 15)
            r.add(RuneIndex.getCapability(world.getChunkFromChunkCoords(chunkX, chunkZ + 1)));

        return r.build();
    }

    private static void notifyRuneAboutNeighborChange(RuneState runeState, World world, BlockPos runePos, EnumFacing runeSide) {
        Rune rune = runeState.getRuneAtSide(runeSide);
        rune.runeEffect().onNeighborChange(world, runePos, runeSide, RuneIndex.getRuneTarget(runeState, runePos, runeSide), rune.runePower());
    }

    @SubscribeEvent
    public static void onRuneUpdate(TickEvent.WorldTickEvent event) {
        if (event.world.getChunkProvider() instanceof ChunkProviderServer) {
            ChunkProviderServer chunkProvider = (ChunkProviderServer) event.world.getChunkProvider();
            for (Chunk loadedChunk : chunkProvider.getLoadedChunks()) {
                Map<BlockPos, RuneState> runes = RuneIndex.getCapability(loadedChunk).existingRunes();
                if (runes.size() > 0) {
                    for (Map.Entry<BlockPos, RuneState> entry : runes.entrySet()) {
                        RuneState runeState = entry.getValue();
                        BlockPos pos = entry.getKey();
                        for (EnumFacing facing : EnumFacing.values()) {
                            Rune runeAtSide = runeState.getRuneAtSide(facing);
                            if (runeAtSide.runeEffect() != RuneEffect.EmptyEffect)
                                runeAtSide.runeEffect().onUpdate(event.world, pos, facing, RuneIndex.getRuneTarget(runeState, pos, facing), runeAtSide.runePower());
                        }
                    }
                }
            }
        }
    }
}
