package ru.mousecray.endmagic.api.world.gen;

import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.EndSoilType;

public class WorldGenMinableEnd extends WorldGenerator {

	private final IBlockState oreBlock;
	private int numberOfBlocks;
	private final Predicate<IBlockState> predicate;

	public WorldGenMinableEnd(IBlockState state, int blockCount) {
		this(state, blockCount, new EndstonePredicate());
	}

	public WorldGenMinableEnd(IBlockState state, int blockCount, Predicate<IBlockState> acceptedStates) {
		super();
		this.oreBlock = state;
		this.numberOfBlocks = blockCount;
		this.predicate = acceptedStates;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos1) {
		BlockPos position = pos1.down(calcHeight(world, rand, pos1));
		float f = rand.nextFloat() * (float) Math.PI;
		double d0 = (double) ((float) (position.getX() + 8) + MathHelper.sin(f) * (float) this.numberOfBlocks / 8.0F);
		double d1 = (double) ((float) (position.getX() + 8) - MathHelper.sin(f) * (float) this.numberOfBlocks / 8.0F);
		double d2 = (double) ((float) (position.getZ() + 8) + MathHelper.cos(f) * (float) this.numberOfBlocks / 8.0F);
		double d3 = (double) ((float) (position.getZ() + 8) - MathHelper.cos(f) * (float) this.numberOfBlocks / 8.0F);
		double d4 = (double) (position.getY() + rand.nextInt(3) - 2);
		double d5 = (double) (position.getY() + rand.nextInt(3) - 2);

		for (int i = 0; i < this.numberOfBlocks; ++i) {
			float f1 = (float) i / (float) this.numberOfBlocks;
			double d6 = d0 + (d1 - d0) * (double) f1;
			double d7 = d4 + (d5 - d4) * (double) f1;
			double d8 = d2 + (d3 - d2) * (double) f1;
			double d9 = rand.nextDouble() * (double) this.numberOfBlocks / 16.0D;
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

				if (d12 * d12 < 1.0D) {
					for (int i2 = k; i2 <= j1; ++i2) {
						double d13 = ((double) i2 + 0.5D - d7) / (d11 / 2.0D);

						if (d12 * d12 + d13 * d13 < 1.0D) {
							for (int j2 = l; j2 <= k1; ++j2) {
								double d14 = ((double) j2 + 0.5D - d8) / (d10 / 2.0D);

								if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
									BlockPos blockpos = new BlockPos(l1, i2, j2);

									IBlockState state = world.getBlockState(blockpos);
									if (state.getBlock().isReplaceableOreGen(state, world, blockpos, this.predicate)) {
										if (onSetOreBlock(world, blockpos, rand)) {
											setBlockAndNotifyAdequately(world, blockpos, this.oreBlock);
										}
									}
								}
							}
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

	public void setNumberOfBlocks(int numberOfBlocks) {
		this.numberOfBlocks = numberOfBlocks;
		if (this.numberOfBlocks > 10) this.numberOfBlocks = 10;
		else if (this.numberOfBlocks < 1) this.numberOfBlocks = 1;
	}

	public int getNumberOfBlocks() { return numberOfBlocks; }

	static class EndstonePredicate implements Predicate<IBlockState> {
		private EndstonePredicate() {}

		@Override
		public boolean apply(IBlockState state) {
			return state != null && EMUtils.isSoil(state, true, false, EndSoilType.STONE);
		}
	}
}