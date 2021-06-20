package ru.mousecray.endmagic.rune;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.capability.chunk.IRuneChunkCapability;
import ru.mousecray.endmagic.capability.chunk.Rune;
import ru.mousecray.endmagic.capability.chunk.RunePart;
import ru.mousecray.endmagic.capability.chunk.RuneState;
import ru.mousecray.endmagic.network.PacketTypes;
import ru.mousecray.endmagic.util.Vec2i;

import java.util.Optional;

import static ru.mousecray.endmagic.capability.chunk.RuneStateCapabilityProvider.runeStateCapability;

public class RuneIndex {
    public static Optional<Rune> getRune(World world, BlockPos pos, EnumFacing side) {
        return getCapability(world, pos)
                .getRuneState(pos)
                .map(rs -> rs.getRuneAtSide(side));
    }

    public static Optional<RuneState> getRuneState(World world, BlockPos pos) {
        return getCapability(world, pos)
                .getRuneState(pos);
    }

    public static void removeRune(World world, BlockPos pos) {
        IRuneChunkCapability capability = getCapability(world, pos);
        capability.removeRuneState(pos);
        EM.proxy.refreshChunk(world, pos);
        if (!world.isRemote)
            PacketTypes.REMOVE_RUNE_STATE.packet()
                    .writePos(pos)
                    .sendToDimension(world.provider.getDimension());

    }

    public static boolean canAddPart(World world, BlockPos pos, EnumFacing side, Vec2i coord, RunePart part) {
        if (coord.y >= 1 && coord.y <= 14 && coord.x >= 1 && coord.x <= 14) {
            IRuneChunkCapability capability = getCapability(world, pos);
            RuneState runeState = capability.createRuneStateIfAbsent(pos);
            return runeState.canAddPart(side, coord);
        } else
            return false;
    }

    public static void addRunePart(World world, BlockPos pos, EnumFacing side, Vec2i coord, RunePart part) {
        if (coord.y >= 1 && coord.y <= 14 && coord.x >= 1 && coord.x <= 14) {
            IRuneChunkCapability capability = getCapability(world, pos);
            RuneState runeState = capability.createRuneStateIfAbsent(pos);
            boolean isRuneFinished = runeState.addRunePart(side, coord, part, System.currentTimeMillis(), world, pos);
            if (isRuneFinished) {
                Rune runeAtSide = runeState.getRuneAtSide(side);
                double runePower = runeAtSide.runeEffect().calculateRunePower(runeAtSide.averageCreatingTime());
                runeAtSide.runePower_$eq(runePower);
                runeAtSide.runeEffect().onInscribed(world, getActualPos(runeState, pos, side), side, runePower);
            }
            EM.proxy.refreshChunk(world, pos);
            if (!world.isRemote)
                PacketTypes.ADDED_RUNE_PART.packet()
                        .writePos(pos)
                        .writeEnum(side)
                        .writeInt(coord.x)
                        .writeInt(coord.y)
                        .writeByte(part.color)
                        .sendToDimension(world.provider.getDimension());
        }
    }

    private static void sync(World world, BlockPos pos, IRuneChunkCapability capability) {
        if (!world.isRemote)
            PacketTypes.SYNC_RUNE_CAPABILITY.packet()
                    .writeInt(pos.getX() >> 4)
                    .writeInt(pos.getZ() >> 4)
                    .writeNBTTagCompound((NBTTagCompound) runeStateCapability.writeNBT(capability, null))
                    .sendToDimension(world.provider.getDimension());
    }

    public static IRuneChunkCapability getCapability(World world, BlockPos pos) {
        return getCapability(world.getChunkFromBlockCoords(pos));
    }

    public static IRuneChunkCapability getCapability(Chunk chunk) {
        return chunk.getCapability(runeStateCapability, EnumFacing.UP);
    }

    public static BlockPos getActualPos(RuneState runeState, BlockPos runePos, EnumFacing runeSide) {
        return runePos;
    }
}
