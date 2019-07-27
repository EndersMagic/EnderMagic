package ru.mousecray.endmagic.worldgen;

import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import ru.mousecray.endmagic.worldgen.monad.WorldIO;

import java.util.Random;

import static ru.mousecray.endmagic.worldgen.monad.WorldIO.onlyWorld;
import static ru.mousecray.endmagic.worldgen.monad.WorldIO.whenEffect;

public class WorldGenEnderTrees implements IWorldGenerator {

    private WorldGenDragonTreeWorld dragonGenerator = new WorldGenDragonTreeWorld();

    private WorldIO<Void> dragonTreeGenerator =
            whenEffect(onlyWorld(w -> w.provider.getDimensionType() == DimensionType.THE_END), WorldGenDragonTreeWorld.generateWorld);

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

        dragonTreeGenerator.performIO(world, chunkZ, chunkX);

        /*if (world.provider.getDimensionType() == DimensionType.THE_END) {
            dragonGenerator.generateWorld(random, chunkX, chunkZ, world);
        }*/

        /**
         * a.flatMap(a1->b.flatMap(b1->c.andThen(d))) == a.flatMap(a1->b).flatMap(b1->c).andThen(d)
         *
         *  a.flatMap(a1->b.flatMap(b1->lift(a1+b1).andThen(d)))
         *
         *
         *  a.flatMap(a1->
         *  b.flatMap(b1->
         *  lift(a1+b1)
         *  .andThen(d)
         *  ))
         *
         * {
         * a1 <- a
         * b1 <- b
         * c
         * d
         * }
         *
         * List(a,a1->b,(a1,b1)->c, d)
         * ->
         *
         *
         */
    }
}
