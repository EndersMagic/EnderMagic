package ru.mousecray.endmagic.rune;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.capability.chunk.*;
import ru.mousecray.endmagic.network.PacketTypes;
import ru.mousecray.endmagic.util.Vec2i;

import javax.annotation.Nonnull;

import static ru.mousecray.endmagic.capability.chunk.RuneStateCapabilityProvider.*;

public class RuneIndex {
    public static Rune getRune(World world, BlockPos pos, @Nonnull EnumFacing side) {
        return getCapability(world, pos)
                .getRuneState(pos)
                .getRuneAtSide(side);
    }

    public static void removeRune(World world, BlockPos pos) {
        IRuneChunkCapability capability = getCapability(world, pos);
        capability.removeRuneState(pos);
        world.getChunkFromBlockCoords(pos).markDirty();
        sync(world, pos, capability);

    }

    public static void addRunePart(World world, BlockPos pos, @Nonnull EnumFacing side, Vec2i coord, RunePart part) {
        IRuneChunkCapability capability = getCapability(world, pos);
        RuneState runeState = capability.getRuneState(pos);
        capability.setRuneState(pos, runeState.withRune(side, runeState.getRuneAtSide(side).add(coord, part, System.currentTimeMillis())));
        world.getChunkFromBlockCoords(pos).markDirty();
        sync(world, pos, capability);

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
}
