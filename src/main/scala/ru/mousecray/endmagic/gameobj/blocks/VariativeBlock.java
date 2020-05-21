package ru.mousecray.endmagic.gameobj.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.util.EMItemBlock;
import ru.mousecray.endmagic.util.registry.ITechnicalBlock;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Random;
import java.util.function.Function;

public abstract class VariativeBlock<BlockType extends Enum<BlockType> & IStringSerializable & BlockTypeBase> extends Block implements ITechnicalBlock {

    protected IProperty<BlockType> blockType;
    private final Class<BlockType> type;
    protected Function<Integer, BlockType> byIndex;
    private String suffix;
    private int metaCount;

    public VariativeBlock(Class<BlockType> type, Material material, String suffix) {
        super(material);
        this.type = type;
        blockType = PropertyEnum.create("type", type);
        this.suffix = suffix;

        //super
        Collection<IProperty<?>> baseProperties = createBlockState().getProperties();
        IProperty[] fullProperties = baseProperties.toArray(new IProperty[baseProperties.size() + 1]);
        fullProperties[baseProperties.size()] = blockType;
        blockState = new BlockStateContainer(this, fullProperties);
        //

        try {
            Method valuesField = type.getDeclaredMethod("values");
            @SuppressWarnings("unchecked") BlockType[] values = (BlockType[]) valuesField.invoke(null);
            metaCount = values.length;
            byIndex = i -> values[i];
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            System.out.println(getClass().getName() + " loaded with error");
            byIndex = null;
        }

        if (metaCount > 4) throw new IllegalArgumentException(String.format("The given EnumType %s contains "
                + metaCount + " metadata. The maximum number of 4.", type.getName()));

        setDefaultState(blockState.getBaseState().withProperty(blockType, byIndex.apply(0)));

        //Set tick randomly to true, if one of states hasTickRandomly
        setTickRandomly(this.hasTickRandomly());
    }

    public boolean hasTickRandomly() {
        for (BlockType type : blockType.getAllowedValues()) if (type.hasTickRandomly()) return true;
        return false;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        BlockType type = getBlockType(state);
        if (!type.hasTickRandomly()) return;
        type.updateTick(world, pos, state, rand);
    }

    @Override
    public ItemBlock getCustomItemBlock(Block block) {
        return new EMItemBlock(block);
    }

    @Nullable
    @Override
    public CreativeTabs getCustomCreativeTab() {
        return EM.EM_CREATIVE;
    }

    public VariativeBlock(Class<BlockType> type, Material material) {
        this(type, material, null);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return state.getValue(blockType).getRenderType(state);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return state.getValue(blockType).createTileEntity(world, state);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return state.getValue(blockType).hasTileEntity(state);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return blockType == null || state.getValue(blockType).isFullCube();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return blockType == null || state.getValue(blockType).isOpaqueCube();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(blockType, byIndex.apply(stack.getItemDamage())));
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

    @Nonnull
    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        MapColor color = state.getValue(blockType).getMapColor();
        return color == null ? super.getMapColor(state, world, pos) : color;
    }

    @Override
    protected abstract BlockStateContainer createBlockState();

    @Override
    public String getCustomName() {
        String rawName = NameAndTabUtils.getName(type);
        return suffix != null ? rawName.substring(0, rawName.lastIndexOf('_') + 1) + suffix : rawName.substring(0, rawName.lastIndexOf('_'));
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

    public BlockType getBlockType(IBlockState state) {
        return state.getValue(blockType);
    }

    public IBlockState stateWithBlockType(BlockType type1) {
        return getDefaultState().withProperty(blockType, type1);
    }

    public IProperty getVariantType() {
        return blockType;
    }
}
