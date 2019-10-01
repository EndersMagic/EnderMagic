package ru.mousecray.endmagic.blocks.dimensional;

import java.util.Random;
import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.api.blocks.IEndSoil;
import ru.mousecray.endmagic.blocks.VariativeBlock;
import ru.mousecray.endmagic.init.EMBlocks;

public class BlockEnderGrass<GrassType extends Enum<GrassType> & IStringSerializable> extends VariativeBlock<GrassType> implements IEndSoil {
	
	private final Function<GrassType, SoundType> soundFunc;
	
	public BlockEnderGrass(Class<GrassType> type, Function<GrassType, MapColor> mapColor, Function<GrassType, SoundType> soundFunc) {
		super(type, Material.ROCK, "grass", mapColor);
		
		this.soundFunc = soundFunc;
		setHarvestLevel("pickaxe", 1);	
		setHardness(3.0F);
		setResistance(10.0F);
		setTickRandomly(true);
	}
	
	@Override
	public EndSoilType getSoilType() {
		return EndSoilType.GRASS;
	}
	
	@Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
		return soundFunc.apply(state.getValue(blockType));
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(EMBlocks.blockEnderStone);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
		//TODO:Custom mechanics
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}

	@Override
	public boolean onUseBonemeal(World world, BlockPos pos, Random rand, EntityPlayer player) {
		for (int x = -1; x < 2; ++x) {
			for (int z = -1; z < 2; ++z) {
				if (world.isAirBlock(pos.add(x, 1, z))) {
					int chance = rand.nextInt(10);
					if (chance > 6) world.setBlockState(pos.add(x, 1, z), EMBlocks.enderOrchid.getDefaultState());
					else if(chance > 4) world.setBlockState(pos.add(x, 1, z), EMBlocks.enderTallgrass.getDefaultState());
				}
			}
		}
		for (int i = 0; i < 32; ++i) world.spawnParticle(EnumParticleTypes.PORTAL, pos.up().getX(), pos.up().getY() + rand.nextDouble() * 2.0D, pos.up().getZ(), rand.nextGaussian(), 0.0D, rand.nextGaussian());
		return true;
	}
}