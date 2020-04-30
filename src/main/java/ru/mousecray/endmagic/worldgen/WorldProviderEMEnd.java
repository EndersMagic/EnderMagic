package ru.mousecray.endmagic.worldgen;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldServer;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.IChunkGenerator;
import ru.mousecray.endmagic.worldgen.biomes.BiomeProviderEMEnd;

import javax.annotation.Nonnull;

public class WorldProviderEMEnd extends WorldProviderEnd {

	@Override
	public void init() {
		biomeProvider = new BiomeProviderEMEnd();
        NBTTagCompound nbttagcompound = world.getWorldInfo().getDimensionData(world.provider.getDimension());
        dragonFightManager = world instanceof WorldServer ? new DragonFightManager((WorldServer) world, nbttagcompound.getCompoundTag("DragonFight")) : null;
	}
	
    @Nonnull
    @Override
	public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorEMEnd(this.world, this.world.getWorldInfo().isMapFeaturesEnabled(), world.getSeed(), getSpawnCoordinate());
    }
}