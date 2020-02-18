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
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.api.blocks.IEndSoil;
import ru.mousecray.endmagic.blocks.BlockTypeBase;
import ru.mousecray.endmagic.blocks.VariativeBlock;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.EnderBlockTypes.EnderGroundType;

public class BlockEnderGrass<GrassType extends Enum<GrassType> & IStringSerializable & BlockTypeBase> extends VariativeBlock<GrassType> implements IEndSoil {
	
	private final Function<GrassType, SoundType> soundFunc;

	public BlockEnderGrass(Class<GrassType> type, Function<GrassType, MapColor> mapColor,
			Function<GrassType, SoundType> soundFunc) {
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
		// TODO: Custom mechanics
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}

	@Override
	public IBlockState getBonemealCrops(Random rand, EntityPlayer player, IBlockState soil) {
		int chance = rand.nextInt(1000) + 1;
		IBlockState state = Blocks.AIR.getDefaultState();
		if(soil.getBlock() instanceof BlockEnderGrass) {
			GrassType type = soil.getValue(blockType);
			if (type == EnderGroundType.LIVE) {
				if (chance > 850) state = EMBlocks.enderTallgrass.getDefaultState();
				else if (chance > 600) state = EMBlocks.enderOrchid.getDefaultState();
			}
			else if(type == EnderGroundType.DEAD) {
				if (chance > 995) state = EMBlocks.enderTallgrass.getDefaultState();
				else if (chance > 980) state = EMBlocks.blockCurseBush.getDefaultState();
			}
			else if (type == EnderGroundType.FROZEN) {
				//TODO: Frozen plants
			}
		}
		else return IEndSoil.super.getBonemealCrops(rand, player, soil);
		return state;
	}

	@Override
	public boolean canUseBonemeal() {
		return true;
	}
}