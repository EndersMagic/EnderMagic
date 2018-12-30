package ru.mousecray.endmagic.blocks;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.init.EMItems;
import ru.mousecray.endmagic.render.IModelRegistration;
import ru.mousecray.endmagic.render.model.baked.BakedModelFullbright;
import ru.mousecray.endmagic.util.CreativeTabProvider;
import ru.mousecray.endmagic.util.IEMModel;

public class EnderCrops extends BlockCrops implements IEMModel, CreativeTabProvider {

    private static final AxisAlignedBB[] ENDER_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.4375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D)};

    public EnderCrops() {
		setLightLevel(0.3F);
    }
    
	@Override
	public CreativeTabs creativeTab() {
		return null;
	}
    
	@Override
	public void registerModels(IModelRegistration modelRegistration) {
		modelRegistration.addBakedModelOverride(this.getRegistryName(), base -> new BakedModelFullbright(base, 
				EM.ID + ":blocks/ender_crops0",
				EM.ID + ":blocks/ender_crops1",
				EM.ID + ":blocks/ender_crops2",
				EM.ID + ":blocks/ender_crops3",
				EM.ID + ":blocks/ender_crops4",
				EM.ID + ":blocks/ender_crops5",
				EM.ID + ":blocks/ender_crops6",
				EM.ID + ":blocks/ender_crops7"));	
	}
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock().isReplaceable(world, pos) && world.getBlockState(pos.down()).getBlock() == Blocks.END_STONE;
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        return world.getBlockState(pos.down()).getBlock() == Blocks.END_STONE;
    }
    
    @Override
    protected Item getSeed() {
        return EMItems.ENDER_SEEDS;
    }

    @Override
    protected Item getCrop() {
        return Items.APPLE;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return ENDER_AABB[((Integer)state.getValue(this.getAgeProperty())).intValue()];
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);
        if (this.isMaxAge(state) && RANDOM.nextInt(30) < 15)
            drops.add(new ItemStack(Items.DYE, 1, 4));
    }
}