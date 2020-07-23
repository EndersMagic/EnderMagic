package ru.mousecray.endmagic.blocks.base;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;

import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;

/**
 * Improved AutoMetaBlock, Useful for blocks with subtypes
 * First property in determine block name and subtypes
 */
public abstract class AutoMetaSubtypedBlock extends AutoMetaBlock {

    public AutoMetaSubtypedBlock(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    public AutoMetaSubtypedBlock(Material materialIn) {
        super(materialIn);
    }

    @Override
    void init() {
        super.init();

        List<IProperty> properties = properties();

        if (properties.size() > 0) {
            firstProperty = properties.get(0);
            firstPropertyOrderedValues = ((IProperty<?>) firstProperty).getAllowedValues().stream().sorted(Comparable::compareTo).collect(toImmutableList());
        }
    }

    private IProperty firstProperty;
    private List<Comparable> firstPropertyOrderedValues;

    public String getUnlocalizedName(ItemStack itemStack) {
        return getUnlocalizedName() + (firstPropertyOrderedValues != null ?
                "." + firstProperty.getName(firstPropertyOrderedValues.get(itemStack.getItemDamage())) :
                "");
    }

    public IBlockState getStateByItemStackDamage(int damage) {
        return firstPropertyOrderedValues != null ?
                getDefaultState().withProperty(firstProperty, firstPropertyOrderedValues.get(damage)) :
                getDefaultState();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        if (firstPropertyOrderedValues != null)
            for (int i = 0; i < firstPropertyOrderedValues.size(); i++)
                items.add(new ItemStack(this, 1, i));
        else
            super.getSubBlocks(itemIn, items);
    }

    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        for (int i = 0; i < (firstPropertyOrderedValues != null ? firstPropertyOrderedValues.size() : 1); i++)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i,
                    new ModelResourceLocation(getRegistryName(), i == 0 ? "inventory" : "inventory,meta=" + i));
    }
}
