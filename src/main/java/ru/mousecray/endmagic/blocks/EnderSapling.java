package ru.mousecray.endmagic.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import ru.mousecray.endmagic.EndMagicData;
import ru.mousecray.endmagic.blocks.EnderPlanks.EnumType;
import ru.mousecray.endmagic.blocks.item.IMetaBlockName;
import ru.mousecray.endmagic.world.gen.feature.DragonTreeGen;
import ru.mousecray.endmagic.world.gen.feature.NaturalTreeGen;

public class EnderSapling extends BlockBush implements IGrowable, IMetaBlockName {
	
    public static final PropertyEnum TYPE = PropertyEnum.create("type", EnumType.class);
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    protected EnderSapling() {
		setUnlocalizedName("ender_sapling");
		setRegistryName("ender_sapling");
		setCreativeTab(EndMagicData.EM_CREATIVE);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumType.DRAGON).withProperty(STAGE, Integer.valueOf(0)));
    }
    
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
    	tooltip.add(I18n.format("tooltip.ender_seeds"));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {return SAPLING_AABB;}
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock().isReplaceable(world, pos) && world.getBlockState(pos.down()).getBlock() == Blocks.END_STONE;
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        return world.getBlockState(pos.down()).getBlock() == Blocks.END_STONE;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!world.isRemote) {
            super.updateTick(world, pos, state, rand);

            if (world.getLightFromNeighbors(pos.up()) >= 3 && rand.nextInt(7) == 0) {
                this.grow(world, rand, pos, state);
            }
        }
    }

    public void generateTree(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, pos)) return;
        WorldGenerator worldgenerator = new DragonTreeGen(true);
        int i = 0;
        int j = 0;
        boolean flag = false;

        switch ((EnumType)state.getValue(TYPE)) {
            case DRAGON:
            	flag = true;
                break;    
            case NATURAL:
                worldgenerator = new NaturalTreeGen(true);
                flag = true;
                break;
            case IMMORTAL:
                worldgenerator = new WorldGenTrees(true, 3, ListBlock.ENDER_LOGS.getDefaultState().withProperty(EnderLogs.TYPE, EnderPlanks.EnumType.NATURAL), ListBlock.ENDER_LEAVES.getDefaultState().withProperty(EnderLeaves.TYPE, EnderPlanks.EnumType.NATURAL), false);
                flag = true;
                break;
            case VANISHING:
                worldgenerator = new WorldGenTrees(true, 3, ListBlock.ENDER_LOGS.getDefaultState().withProperty(EnderLogs.TYPE, EnderPlanks.EnumType.VANISHING), ListBlock.ENDER_LEAVES.getDefaultState().withProperty(EnderLeaves.TYPE, EnderPlanks.EnumType.VANISHING), false);
                flag = true;
                break;
        }
        
        IBlockState iblockstate2 = Blocks.AIR.getDefaultState();

        if (flag) {
            world.setBlockState(pos.add(i, 0, j), iblockstate2, 4);
            world.setBlockState(pos.add(i + 1, 0, j), iblockstate2, 4);
            world.setBlockState(pos.add(i, 0, j + 1), iblockstate2, 4);
            world.setBlockState(pos.add(i + 1, 0, j + 1), iblockstate2, 4);
        }
        else {
            world.setBlockState(pos, iblockstate2, 4);
        }

        if (!worldgenerator.generate(world, rand, pos.add(i, 0, j))) {
            if (flag) {
                world.setBlockState(pos.add(i, 0, j), state, 4);
                world.setBlockState(pos.add(i + 1, 0, j), state, 4);
                world.setBlockState(pos.add(i, 0, j + 1), state, 4);
                world.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
            }
            else {
                world.setBlockState(pos, state, 4);
            }
        }
    }

    @Override
    public int damageDropped(IBlockState state) {return ((EnumType)state.getValue(TYPE)).getID();}

	@Override
	public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items) {
		for(int i = 0; i < EnumType.values().length; i++) items.add(new ItemStack(this, 1, i));
	}

	@Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {return true;}

	@Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return (double)world.rand.nextFloat() < 0.45D;
    }

	@Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		if(world.provider.getDimension() == 1) {
			if (((Integer)state.getValue(STAGE)).intValue() == 0) world.setBlockState(pos, state.cycleProperty(STAGE), 4);
			else this.generateTree(world, pos, state, rand);
		}
    }
	
	@Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, EnumType.values()[meta & 7]).withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumType)state.getValue(TYPE)).getID();
        i = i | ((Integer)state.getValue(STAGE)).intValue() << 3;
        return i;
    }

	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE, STAGE});
    }

	@Override
	public String getSpecialName(int damage) {return EnumType.values()[damage].getName();}

	@Override
	public String getSpecialName(ItemStack stack) {return EnumType.values()[stack.getItemDamage()].getName();}

	@Override
	public int getMetaCount() {return EnumType.values().length;}
}