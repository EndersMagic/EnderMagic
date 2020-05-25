package ru.mousecray.endmagic.api.metadata;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.IUnlistedProperty;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MetadataContainer extends BlockStateContainer {

    private final ImmutableList<ExtendedStateImpl> metaToState;
    private final ImmutableMap<IBlockState, Integer> stateToMeta;
    private final int itemBlockCount;
    private MetaItemBlock itemBlock;

    public MetadataContainer(Block block, @Nullable PropertyFeature<?> featureWithItemBlock, List<PropertyFeature<?>> features, List<IProperty<?>> properties, List<IProperty<?>> excludedProp) {
        super(block, union(properties, excludedProp, features, featureWithItemBlock).toArray(new IProperty[0]));
        Map<Comparable<?>, ExtendedStateImpl> tempStates = new HashMap<>();
        excludedProp.forEach(property -> getValidStates()
                .forEach(state -> tempStates.putIfAbsent(state.getValue(property), (ExtendedStateImpl) state)));

        metaToState = ImmutableList.copyOf(!tempStates.isEmpty() ? tempStates.values() :
                getValidStates().stream().map(val -> (ExtendedStateImpl) val).collect(Collectors.toList()));
        stateToMeta = ImmutableMap.copyOf(metaToState.stream().collect(Collectors.toMap(key -> key, metaToState::indexOf)));

        itemBlockCount = featureWithItemBlock != null ? featureWithItemBlock.getAllowedValues().size() : 0;
    }

    private static Set<IProperty<?>> union(List<IProperty<?>> firstList, List<IProperty<?>> secondList, List<PropertyFeature<?>> thirdList, @Nullable PropertyFeature element) {
        Set<IProperty<?>> returnSet = new HashSet<>();
        returnSet.addAll(firstList);
        returnSet.addAll(secondList);
        returnSet.addAll(thirdList);
        if (element != null) returnSet.add(element);
        return returnSet;
    }

    @Override
    protected StateImplementation createState(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties, @Nullable ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties) {
        Collection<IFeaturesList> list = properties.values().stream()
                .filter(val -> val instanceof IFeaturesList)
                .map(val -> (IFeaturesList) val)
                .sorted(Comparator.comparing(IFeaturesList::getPriority))
                .collect(Collectors.toList());
        return list.isEmpty() ? new ExtendedStateImpl(block, properties) : new ExtendedStateImpl(block, properties, list);
    }

    public boolean hasBlockTickRandomly() {
        return metaToState.stream().anyMatch(ExtendedStateImpl::hasTickRandomly);
    }

    public int getMetaFromState(IBlockState state) {
        return stateToMeta.get(state);
    }

    public ExtendedStateImpl getStateFromMeta(int meta) {
        return metaToState.get(meta);
    }

    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> destItems, Block block) {
        if (itemBlockCount == 0) {
            destItems.add(new ItemStack(block));
            return;
        }
        for (int i = 0; i < itemBlockCount; ++i)
            destItems.add(new ItemStack(block, 1, i));
    }

    public ItemBlock createMetaItem(Block block) {
        return itemBlock == null ? (itemBlock = new MetaItemBlock(block, itemBlockCount > 0)) : itemBlock;
    }

    public void registerItemModels(Block block) {
        for (int i = 0; i < itemBlockCount; ++i)
            //TODO: Test for 4+ ItemBlocks
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i,
                    new ModelResourceLocation(block.getRegistryName(), "inventory,meta=" + i));
    }

    public class MetaItemBlock extends ItemBlock {

        public MetaItemBlock(Block block, boolean hasSubtypes) {
            super(block);
            setHasSubtypes(hasSubtypes);
        }

        @Override
        public String getUnlocalizedName(ItemStack stack) {
            String name = super.getUnlocalizedName();
            if (getHasSubtypes()) name = name + getStateFromMeta(stack.getMetadata()).getName();
            return name;
        }

        @Override
        public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
            if (((ExtendedStateImpl) newState).canPlaceBlockAt(world, pos)) return false;
            return super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
        }

        @Override
        public int getMetadata(int damage) {
            return getHasSubtypes() ? damage : 0;
        }
    }

    public static class ExtendedStateImpl extends StateImplementation {

        private final List<IFeaturesList> features = new ArrayList<>();

        protected ExtendedStateImpl(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties) {
            this(block, properties, Collections.singleton(IFeaturesList.EMPTY("")));
        }

        public ExtendedStateImpl(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties, Collection<IFeaturesList> features) {
            super(block, properties);
            this.features.addAll(features);
        }

        @Nullable
        private <R> R iterateFeatures(@Nullable R defaultReturn, Function<IFeaturesList, R> function) {
            for (IFeaturesList feature : features)
                try { return function.apply(feature); } catch (UnsupportedOperationException ignore) {}
            return defaultReturn;
        }

        private void iterateVoidFeatures(Consumer<IFeaturesList> function) {
            for (IFeaturesList feature : features)
                try { function.accept(feature); } catch (UnsupportedOperationException ignore) {}
        }

        public ImmutableList<IFeaturesList> getFeatures() {
            return ImmutableList.copyOf(features);
        }

        /*-------------------------New methods-------------------------*/
        public int getDamage() {
            //Excessively, because IFeaturesList#getDamage don't create exception, but used for iteration of features list
            return iterateFeatures(0, IFeaturesList::getDamage);
        }

        public TileEntity createTileEntity(World world) {
            //Can't access to Block#createTileEntity, because of recursion
            return iterateFeatures(null, feature -> feature.createTileEntity(world));
        }

        public boolean hasTileEntity() {
            //Can't access to Block#hasTileEntity, because of recursion
            return iterateFeatures(false, IFeaturesList::hasTileEntity);
        }

        public void updateTick(World world, BlockPos pos, Random rand) {
            //Void method
            iterateVoidFeatures(feature -> feature.updateTick(world, pos, this, rand));
        }

        public boolean hasTickRandomly() {
            //Don't create recursion, because MetadataBlock don't overriding Block#getTickRandomly, and set it in constructor
            return iterateFeatures(getBlock().getTickRandomly(), IFeaturesList::hasTickRandomly);
        }

        public int quantityDropped(int fortune, Random random) {
            //Access to Block#quantityDropped with one parameter, because of recursion
            return iterateFeatures(getBlock().quantityDropped(random), feature -> feature.quantityDropped(fortune, random));
        }

        public String getName() {
            //Excessively, because IFeaturesList#getName don't create exception, but used for iteration of features list
            return iterateFeatures("", IFeaturesList::getName);
        }

        public SoundType getSoundType(World world, BlockPos pos, Entity entity) {
            //Access to Block#getSoundType without parameters, because of recursion
            return iterateFeatures(getBlock().getSoundType(), feature -> feature.getSoundType(world, pos, entity));
        }

        public boolean canPlaceBlockAt(World world, BlockPos pos) {
            boolean canPlace = true;
            if (getBlock() instanceof IStayBlock) {
                IStayBlock block = (IStayBlock) getBlock();
                canPlace = block.canPlaceBlockAt(this, world, pos);
            }
            return iterateFeatures(canPlace, feature -> feature.canPlaceBlockAt(world, pos));
        }
        /*-------------------------New methods-------------------------*/

        /*-------------------------Overriding methods-------------------------*/
        @Override
        public void neighborChanged(World world, BlockPos pos, Block block, BlockPos fromPos) {
            iterateVoidFeatures(feature -> feature.neighbourChanged(world, pos, block, fromPos));
        }

        @Override
        public Material getMaterial() {
            return iterateFeatures(getBlock().getMaterial(this), IFeaturesList::getMaterial);
        }

        @Override
        public MapColor getMapColor(IBlockAccess world, BlockPos pos) {
            return iterateFeatures(getBlock().getMapColor(this, world, pos), feature -> feature.getMapColor(world, pos));
        }

        @Override
        public float getBlockHardness(World world, BlockPos pos) {
            return iterateFeatures(getBlock().getBlockHardness(this, world, pos), feature -> feature.getHardness(world, pos));
        }

        @Override
        public boolean isOpaqueCube() {
            return iterateFeatures(getBlock().isOpaqueCube(this), IFeaturesList::isOpaqueCube);
        }

        @Override
        public boolean isFullCube() {
            return iterateFeatures(getBlock().isFullCube(this), IFeaturesList::isFullCube);
        }

        @Override
        public int getLightValue(IBlockAccess world, BlockPos pos) {
            return iterateFeatures(getBlock().getLightValue(this, world, pos), feature -> feature.getLightValue(world, pos));
        }

        @Override
        public int getLightOpacity(IBlockAccess world, BlockPos pos) {
            return iterateFeatures(getBlock().getLightOpacity(this, world, pos), feature -> feature.getLightOpacity(world, pos));
        }

        @Override
        public EnumBlockRenderType getRenderType() {
            return iterateFeatures(getBlock().getRenderType(this), IFeaturesList::getRenderType);
        }

        @Override
        public BlockFaceShape getBlockFaceShape(IBlockAccess world, BlockPos pos, EnumFacing facing) {
            return iterateFeatures(getBlock().getBlockFaceShape(world, this, pos, facing), feature -> feature.getFaceShape(world, pos, facing));
        }

        @Override
        public boolean isTopSolid() {
            return iterateFeatures(getBlock().isTopSolid(this), IFeaturesList::isTopSolid);
        }

        @Override
        public boolean canProvidePower() {
            return iterateFeatures(getBlock().canProvidePower(this), IFeaturesList::canProvidePower);
        }

        @Override
        public boolean canEntitySpawn(Entity entity) {
            return iterateFeatures(getBlock().canEntitySpawn(this, entity), feature -> feature.canEntitySpawn(entity));
        }

        @Override
        public AxisAlignedBB getBoundingBox(IBlockAccess world, BlockPos pos) {
            return iterateFeatures(getBlock().getBoundingBox(this, world, pos), feature -> feature.getBoundingBox(world, pos));
        }

        @Nullable
        @Override
        public AxisAlignedBB getCollisionBoundingBox(IBlockAccess world, BlockPos pos) {
            return iterateFeatures(getBlock().getCollisionBoundingBox(this, world, pos), feature -> feature.getCollisionAABB(world, pos));
        }

        @Override
        public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos) {
            return iterateFeatures(getBlock().getSelectedBoundingBox(this, world, pos), feature -> feature.getSelectedAABB(world, pos));
        }

        @Override
        public int getStrongPower(IBlockAccess world, BlockPos pos, EnumFacing side) {
            return iterateFeatures(getBlock().getStrongPower(this, world, pos, side), feature -> feature.getStrongPower(world, pos, side));
        }

        @Override
        public int getWeakPower(IBlockAccess world, BlockPos pos, EnumFacing side) {
            return iterateFeatures(getBlock().getWeakPower(this, world, pos, side), feature -> feature.getWeakPower(world, pos, side));
        }
        /*-------------------------Overriding methods-------------------------*/
    }
}