package ru.mousecray.endmagic.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.client.render.model.baked.BakedModelFullbright;
import ru.mousecray.endmagic.init.EMItems;
import ru.mousecray.endmagic.util.registry.IEMModel;

public class EnderTallgrass extends BlockBush implements IShearable, IEMModel {
	
    protected static final AxisAlignedBB END_GRASS_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    public EnderTallgrass() {
        super(Material.VINE);
        setHardness(0.0F);
        setResistance(0.0F);
        setSoundType(SoundType.PLANT);
		setLightLevel(0.1F);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerModels(IModelRegistration modelRegistration) {
        modelRegistration.addBakedModelOverride(this.getRegistryName(), base -> new BakedModelFullbright(base, EM.ID + ":blocks/ender_tallgrass"));
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return END_GRASS_AABB;
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock().isReplaceable(world, pos) && EMUtils.isSoil(world, pos.down(), true, EndSoilType.GRASS);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        return EMUtils.isSoil(world, pos.down(), true, EndSoilType.GRASS);
    }
    
    @Override
    public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
        return true;
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return EMItems.enderSeeds;
    }
    
    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return 1 + random.nextInt(fortune * 2 + 1);
    }

    @Override
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XYZ;
    }

    @Override 
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) { 
    	return true; 
    }
    
    @Override
    public NonNullList onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this));
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (!world.isRemote && stack.getItem() == Items.SHEARS) player.addStat(StatList.getBlockStats(this));
        else super.harvestBlock(world, player, pos, state, te, stack);
    }
    
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	if (RANDOM.nextInt(60) != 0) return;
        ItemStack seed = new ItemStack(EMItems.enderSeeds, fortune);
        if (!seed.isEmpty()) drops.add(seed);
    }
}