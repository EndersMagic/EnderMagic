package ru.mousecray.endmagic.gameobj.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.EnderBlockTypes;
import ru.mousecray.endmagic.util.registry.IExtendedProperties;
import ru.mousecray.endmagic.util.registry.ITechnicalBlock;

import javax.annotation.Nullable;

public abstract class EMSlab extends BlockSlab implements ITechnicalBlock, IExtendedProperties {
    public static class EMSlabDouble extends EMSlab {
        @Override
        public boolean isDouble() {
            return true;
        }

        @Nullable
        @Override
        public String getCustomName() {
            return "ender_tree_slab_double";
        }

        @Nullable
        @Override
        public CreativeTabs getCustomCreativeTab() {
            return null;
        }
    }

    public static class EMSlabSingle extends EMSlab {
        @Override
        public boolean isDouble() {
            return false;
        }

        @Nullable
        @Override
        public String getCustomName() {
            return "ender_tree_slab";
        }

        @Nullable
        @Override
        public CreativeTabs getCustomCreativeTab() {
            return EM.EM_CREATIVE;
        }

        @Override
        public void registerModels(IModelRegistration modelRegistration) {
            for (EnderBlockTypes.EnderTreeType treeType : EnderBlockTypes.EnderTreeType.values()) {
                int i = treeType.ordinal();
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(getRegistryName(), i == 0 ? "inventory" : "inventory,meta=" + i));
            }
        }

        public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
            for (EnderBlockTypes.EnderTreeType treeType : EnderBlockTypes.EnderTreeType.values())
                items.add(new ItemStack(this, 1, treeType.ordinal()));
        }
    }

    @Override
    public ItemBlock getCustomItemBlock() {
        return new ItemSlab(EMBlocks.enderWoodenSlabSingle, EMBlocks.enderWoodenSlabSingle, EMBlocks.enderWoodenSlabDouble);
    }

    public static final PropertyEnum<EnderBlockTypes.EnderTreeType> VARIANT = PropertyEnum.create("type", EnderBlockTypes.EnderTreeType.class);

    public EMSlab() {
        super(Material.WOOD);
        setSoundType(SoundType.WOOD);
        setHardness(2.0F);
        setResistance(5.0F);
        IBlockState iblockstate = blockState.getBaseState();

        if (!isDouble()) {
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        }

        setDefaultState(iblockstate.withProperty(VARIANT, EnderBlockTypes.EnderTreeType.DRAGON));
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return "tile.ender_tree_slab." + getStateFromMeta(meta).getValue(VARIANT).getName().toLowerCase();
    }

    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.getValue(VARIANT).getMapColor(state,worldIn,pos);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(EMBlocks.enderWoodenSlabSingle, 1, state.getValue(VARIANT).ordinal());
    }

    public IProperty<?> getVariantProperty() {
        return VARIANT;
    }

    public Comparable<?> getTypeForItem(ItemStack stack) {
        return EnderBlockTypes.EnderTreeType.values()[stack.getItemDamage() & 7];
    }

    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = getDefaultState().withProperty(VARIANT, EnderBlockTypes.EnderTreeType.values()[meta & 7]);

        if (!isDouble()) {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }

        return iblockstate;
    }

    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | state.getValue(VARIANT).ordinal();

        if (!isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
            i |= 8;

        return i;
    }

    protected BlockStateContainer createBlockState() {
        return isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
    }

    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }
}