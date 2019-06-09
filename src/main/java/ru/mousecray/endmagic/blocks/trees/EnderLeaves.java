package ru.mousecray.endmagic.blocks.trees;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.trees.EnderPlanks.EnumType;
import ru.mousecray.endmagic.init.EMItems;

import javax.annotation.Nullable;

public class EnderLeaves extends AbstractEnderLeaves {

    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

    public EnderLeaves() {
        super("ender_leaves");
        setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumType.DRAGON).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
    }

    @Override
    public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items) {
        for (int i = 0; i < EnumType.values().length; i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    protected void dropApple(World world, BlockPos pos, IBlockState state, int chance) {
        if (state.getValue(TYPE) == EnumType.DRAGON && world.rand.nextInt(chance) == 0) {
            spawnAsEntity(world, pos, new ItemStack(EMItems.enderApple));
        }
    }

    @Override
    protected int getSaplingDropChance(IBlockState state) {
        return state.getValue(TYPE) == EnumType.NATURAL ? 10 : (state.getValue(TYPE) == EnumType.IMMORTAL ? 5 : super.getSaplingDropChance(state));
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(this), 1, ((EnumType) state.getValue(TYPE)).getID());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, getWoodTypes(meta)).withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumType) state.getValue(TYPE)).getID();
        if (!((Boolean) state.getValue(DECAYABLE)).booleanValue()) i |= 4;
        if (((Boolean) state.getValue(CHECK_DECAY)).booleanValue()) i |= 8;
        return i;
    }

    @Override
    public EnumType getWoodTypes(int meta) {
        return EnumType.values()[(meta & 3) % 4];
    }

    @Override
    public int damageDropped(IBlockState state) {
        return ((EnumType) state.getValue(TYPE)).getID();
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (!world.isRemote && stack.getItem() == Items.SHEARS) player.addStat(StatList.getBlockStats(this));
        else super.harvestBlock(world, player, pos, state, te, stack);
    }

    @Override
    public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this, 1, world.getBlockState(pos).getValue(TYPE).getID()));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{TYPE, CHECK_DECAY, DECAYABLE});
    }
}