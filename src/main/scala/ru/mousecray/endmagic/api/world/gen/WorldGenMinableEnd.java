package ru.mousecray.endmagic.api.world.gen;

import com.google.common.base.Predicate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.api.utils.EMUtils;

import javax.annotation.Nonnull;
import java.util.Random;

public class WorldGenMinableEnd extends WorldGenerator {

    private final IBlockState oreBlock;
    private final Predicate<IBlockState> predicate;
    private int numberOfBlocks;

    public WorldGenMinableEnd(IBlockState state, int blockCount) {
        this(state, blockCount, new EndstonePredicate());
    }

    private WorldGenMinableEnd(IBlockState state, int blockCount, Predicate<IBlockState> acceptedStates) {
        super();
        oreBlock = state;
        numberOfBlocks = blockCount;
        predicate = acceptedStates;
    }

    @Override
    public boolean generate(@Nonnull World world, Random rand, BlockPos pos1) {
        BlockPos position = pos1.down(calcHeight(world, rand, pos1));
        float f = rand.nextFloat() * (float) Math.PI;
        double d0 = (double) ((float) (position.getX() + 8) + MathHelper.sin(f) * (float) numberOfBlocks / 8.0F);
        double d1 = (double) ((float) (position.getX() + 8) - MathHelper.sin(f) * (float) numberOfBlocks / 8.0F);
        double d2 = (double) ((float) (position.getZ() + 8) + MathHelper.cos(f) * (float) numberOfBlocks / 8.0F);
        double d3 = (double) ((float) (position.getZ() + 8) - MathHelper.cos(f) * (float) numberOfBlocks / 8.0F);
        double d4 = (double) (position.getY() + rand.nextInt(3) - 2);
        double d5 = (double) (position.getY() + rand.nextInt(3) - 2);

        for (int i = 0; i < numberOfBlocks; ++i) {
            float f1 = (float) i / (float) numberOfBlocks;
            double d6 = d0 + (d1 - d0) * (double) f1;
            double d7 = d4 + (d5 - d4) * (double) f1;
            double d8 = d2 + (d3 - d2) * (double) f1;
            double d9 = rand.nextDouble() * (double) numberOfBlocks / 16.0D;
            double d10 = (double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
            double d11 = (double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
            int j = MathHelper.floor(d6 - d10 / 2.0D);
            int k = MathHelper.floor(d7 - d11 / 2.0D);
            int l = MathHelper.floor(d8 - d10 / 2.0D);
            int i1 = MathHelper.floor(d6 + d10 / 2.0D);
            int j1 = MathHelper.floor(d7 + d11 / 2.0D);
            int k1 = MathHelper.floor(d8 + d10 / 2.0D);

            for (int l1 = j; l1 <= i1; ++l1) {
                double d12 = ((double) l1 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D) for (int i2 = k; i2 <= j1; ++i2) {
                    double d13 = ((double) i2 + 0.5D - d7) / (d11 / 2.0D);

                    if (d12 * d12 + d13 * d13 < 1.0D) for (int j2 = l; j2 <= k1; ++j2) {
                        double d14 = ((double) j2 + 0.5D - d8) / (d10 / 2.0D);

                        if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
                            BlockPos blockpos = new BlockPos(l1, i2, j2);

                            IBlockState state = world.getBlockState(blockpos);
                            if (state.getBlock().isReplaceableOreGen(state, world, blockpos, predicate))
                                if (onSetOreBlock(world, blockpos, rand))
                                    setBlockAndNotifyAdequately(world, blockpos, oreBlock);
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * Calculate height of generation
     */
    public int calcHeight(World world, Random rand, BlockPos pos1) {
        return 0;
    }

    /**
     * Called on block is ready to state; Return false to cancel this.
     */
    public boolean onSetOreBlock(World world, BlockPos pos, Random rand) {
        return true;
    }

    public int getNumberOfBlocks() { return numberOfBlocks; }

    public void setNumberOfBlocks(int numberOfBlocks) {
        this.numberOfBlocks = numberOfBlocks;
        if (this.numberOfBlocks > 10) this.numberOfBlocks = 10;
        else if (this.numberOfBlocks < 1) this.numberOfBlocks = 1;
    }

    static class EndstonePredicate implements Predicate<IBlockState> {
        private EndstonePredicate() {}

        @Override
        public boolean apply(IBlockState state) {
            return state != null && EMUtils.isSoil(state, EndSoilType.STONE);
        }
    }
}