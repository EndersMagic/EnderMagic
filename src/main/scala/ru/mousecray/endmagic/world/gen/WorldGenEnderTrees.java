package ru.mousecray.endmagic.world.gen;

import java.util.Random;

import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import ru.mousecray.endmagic.world.gen.trees.world.WorldGenDragonTreeWorld;
import ru.mousecray.endmagic.world.gen.trees.world.WorldGenNaturalTreeWorld;
import ru.mousecray.endmagic.world.gen.trees.world.WorldGenPhantomTreeWorld;

public class WorldGenEnderTrees implements IWorldGenerator {

    private WorldGenDragonTreeWorld dragonGenerator = new WorldGenDragonTreeWorld();
    private WorldGenNaturalTreeWorld naturalGenerator = new WorldGenNaturalTreeWorld();
    private WorldGenPhantomTreeWorld phantomGenerator = new WorldGenPhantomTreeWorld();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        //Transmit air block on gen coord
    	if (world.provider.getDimensionType() == DimensionType.THE_END) {
            dragonGenerator.generateWorld(random, chunkX, chunkZ, world);
            naturalGenerator.generateWorld(random, chunkX, chunkZ, world);
            phantomGenerator.generateWorld(random, chunkX, chunkZ, world);
        }
    }
}
