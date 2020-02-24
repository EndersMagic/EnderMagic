package ru.mousecray.endmagic.worldgen;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldServer;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.IChunkGenerator;
import ru.mousecray.endmagic.worldgen.biomes.BiomeProviderEMEnd;

public class WorldProviderEMEnd extends WorldProviderEnd {

	@Override
	public void init() {
		biomeProvider = new BiomeProviderEMEnd();
        NBTTagCompound nbttagcompound = world.getWorldInfo().getDimensionData(world.provider.getDimension());
        dragonFightManager = world instanceof WorldServer ? new DragonFightManager((WorldServer) world, nbttagcompound.getCompoundTag("DragonFight")) : null;
	}
	
    @Override
	public IChunkGenerator createChunkGenerator() {
        return (IChunkGenerator)new ChunkGeneratorEMEnd(this.world, this.world.getWorldInfo().isMapFeaturesEnabled(), world.getSeed(), getSpawnCoordinate());
    }
}