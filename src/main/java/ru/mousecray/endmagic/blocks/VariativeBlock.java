package ru.mousecray.endmagic.blocks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.function.Function;

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

public abstract class VariativeBlock<BlockType extends Enum<BlockType> & IStringSerializable> extends Block implements NameProvider, IEMModel {

    protected final IProperty<BlockType> blockType;
    protected Function<Integer, BlockType> byIndex;
    private final Class<BlockType> type;
    private String suffix;
    private int metaCount;

    public VariativeBlock(Class<BlockType> type, Material material, String suffix) {
        super(material);
        this.type = type;
        blockType = PropertyEnum.create("tree_type", type);
        this.suffix = suffix;

        //super
        Collection<IProperty<?>> baseProperties = createBlockState().getProperties();
        IProperty[] fullProperties = baseProperties.toArray(new IProperty[baseProperties.size() + 1]);
        fullProperties[baseProperties.size()] = blockType;
        blockState = new BlockStateContainer(this, fullProperties);
        //
        
        try {
            Method valuesField = type.getDeclaredMethod("values");
            BlockType[] values = (BlockType[]) valuesField.invoke(null);
            this.metaCount = values.length;
            this.byIndex = i -> values[i];
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            System.out.println(this.getClass().getName() + " loaded with error");
            this.byIndex = null;
        }
        
        if (metaCount > 4) throw new IllegalArgumentException(String.format("The given EnumType %s contains " 
        + metaCount + " metadata. The maximum number of 4.", type.getName()));

        setDefaultState(blockState.getBaseState().withProperty(blockType, byIndex.apply(0)));
    }
    
    public VariativeBlock(Class<BlockType> type, Material material) {
    	this(type, material, null);
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(blockType, byIndex.apply(stack.getItemDamage())));
    }
    
    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(blockType).ordinal();
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
        return suffix != null ? rawName.substring(0, rawName.lastIndexOf('_')) + suffix : rawName;
    }

    public String getNameForStack(ItemStack stack) {
        return byIndex.apply(stack.getMetadata()).getName();
    }

    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        for (int i = 0; i < metaCount; i++)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i,
                    new ModelResourceLocation(getRegistryName(), i == 0 ? "inventory" : "inventory,meta=" + i));
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int i = 0; i < metaCount; i++)
            items.add(new ItemStack(this, 1, i));
    }
}
