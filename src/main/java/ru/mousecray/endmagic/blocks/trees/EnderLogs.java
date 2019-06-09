package ru.mousecray.endmagic.blocks.trees;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import ru.mousecray.endmagic.blocks.trees.EnderPlanks.EnumType;

public class EnderLogs extends BlockLog {

    public static final PropertyEnum TYPE = PropertyEnum.create("type", EnumType.class);

    public EnderLogs() {
        setResistance(10F);
        setHarvestLevel("axe", 2);
        setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumType.DRAGON).withProperty(LOG_AXIS, EnumAxis.Y));
    }

    @Override
    public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items) {
        for (int i = 0; i < EnumType.values().length; i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = getDefaultState().withProperty(TYPE, EnumType.values()[(meta & 3) % 4]);

        switch (meta & 12) {
            case 0:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Y);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.X);
                break;
            case 8:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Z);
                break;
            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.NONE);
        }

        return iblockstate;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumType) state.getValue(TYPE)).getID();

        switch ((EnumAxis) state.getValue(LOG_AXIS)) {
            case X:
                i |= 4;
                break;
            case Z:
                i |= 8;
                break;
            case NONE:
                i |= 12;
        }
        return i;
    }

    @Override
    public int damageDropped(IBlockState state) {
        EnumType type = (EnumType) state.getValue(TYPE);
        return type.getID();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{TYPE, LOG_AXIS});
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(this), 1, ((EnumType) state.getValue(TYPE)).getID());
    }
}