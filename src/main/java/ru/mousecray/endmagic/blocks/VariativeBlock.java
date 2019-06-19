package ru.mousecray.endmagic.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.util.IEMModel;
import ru.mousecray.endmagic.util.NameAndTabUtils;
import ru.mousecray.endmagic.util.NameProvider;

import java.util.Collection;
import java.util.function.Function;

public abstract class VariativeBlock<BlockType extends Enum<BlockType> & IStringSerializable> extends Block implements NameProvider, IEMModel {

    protected final IProperty<BlockType> blockType;
    protected final Function<Integer, BlockType> byIndex;
    private final Class<BlockType> type;
    private String suffix;

    public VariativeBlock(Class<BlockType> type, Function<Integer, BlockType> byIndex, Material material, String suffix) {
        super(material);
        this.type = type;
        blockType = PropertyEnum.create("tree_type", type);
        this.byIndex = byIndex;
        this.suffix = suffix;

        //super
        Collection<IProperty<?>> baseProperties = createBlockState().getProperties();
        IProperty[] fullProperties = baseProperties.toArray(new IProperty[baseProperties.size() + 1]);
        fullProperties[baseProperties.size()] = blockType;
        blockState = new BlockStateContainer(this, fullProperties);
        //

        setDefaultState(blockState.getBaseState()
                .withProperty(blockType, byIndex.apply(0)));
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(blockType, byIndex.apply(stack.getItemDamage())));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        int type = meta & 3;
        return getDefaultState()
                .withProperty(blockType, byIndex.apply(type));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(blockType).ordinal();
    }

    @Override
    protected abstract BlockStateContainer createBlockState();

    @Override
    public String name() {
        String rawName = NameAndTabUtils.getName(type);
        return rawName.substring(0, rawName.lastIndexOf('_')) + suffix;
    }

    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getRegistryName(), "inventory"));
        for (int i = 1; i < 4; i++)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i,
                    new ModelResourceLocation(getRegistryName(), "inventory,meta=" + i));
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int i = 0; i < 4; i++)
            items.add(new ItemStack(this, 1, i));
    }
}
