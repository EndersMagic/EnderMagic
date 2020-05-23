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
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MetadataContainer extends BlockStateContainer {

    private final ImmutableList<ExtendedStateImpl> metaToState;
    private final ImmutableMap<IBlockState, Integer> stateToMeta;
    private final PropertyFeature feature;

    public MetadataContainer(Block block, PropertyFeature feature, List<IProperty<?>> properties, List<IProperty<?>> excludedProp) {
        super(block, union(properties, excludedProp, feature).toArray(new IProperty[0]));
        Map<Comparable<?>, ExtendedStateImpl> tempStates = new HashMap<>();
        excludedProp.forEach(
                property -> getValidStates().forEach(state -> tempStates.putIfAbsent(state.getValue(property), (ExtendedStateImpl) state)));

        metaToState = ImmutableList.copyOf(tempStates.values());
        stateToMeta = ImmutableMap.copyOf(metaToState.stream().collect(Collectors.toMap(key -> key, metaToState::indexOf)));
        this.feature = feature;
    }

    private static Set<IProperty<?>> union(List<IProperty<?>> firstList, List<IProperty<?>> secondList, IProperty<?> element) {
        Set<IProperty<?>> returnSet = new HashSet<>();
        returnSet.addAll(firstList);
        returnSet.addAll(secondList);
        if (element != null) returnSet.add(element);
        return returnSet;
    }

    @Override
    protected StateImplementation createState(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties, @Nullable ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties) {
        return properties.values().stream().filter(val -> val instanceof IFeaturesList).findFirst()
                .map(comparable -> new ExtendedStateImpl(block, properties, (IFeaturesList) comparable))
                .orElseGet(() -> new ExtendedStateImpl(block, properties));
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

    public int getItemBlockCount() {
        return feature == null ? 0 : feature.getAllowedValues().size();
    }

    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> destItems, Block block) {
        if (getItemBlockCount() == 0) {
            destItems.add(new ItemStack(block));
            return;
        }
        for (int i = 0; i < getItemBlockCount(); ++i)
            destItems.add(new ItemStack(block, 1, i));
    }

    public ItemBlock createMetaItem(Block block) {
        return getItemBlockCount() == 0 ? new ItemBlock(block) : new MetaItemBlock(block);
    }

    public void registerItemModels(Block block) {
        for (int i = 0; i < getItemBlockCount(); ++i)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i,
                    new ModelResourceLocation(block.getRegistryName(), "inventory,meta=" + i));
    }

    public class MetaItemBlock extends ItemBlock {

        public MetaItemBlock(Block block) {
            super(block);
            setHasSubtypes(true);
            setMaxDamage(0);
        }

        @Override
        public String getUnlocalizedName(ItemStack stack) {
            return super.getUnlocalizedName() + "." + getStateFromMeta(stack.getMetadata()).getName();
        }

        @Override
        public int getMetadata(int damage) {
            return damage;
        }
    }

    public static class ExtendedStateImpl extends StateImplementation {

        private IFeaturesList features;

        protected ExtendedStateImpl(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties) {
            this(block, properties, IFeaturesList.EMPTY(block.getUnlocalizedName()));
        }

        public ExtendedStateImpl(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties, IFeaturesList features) {
            super(block, properties);
            this.features = features;
        }

        public IFeaturesList getFeatures() {
            return features;
        }

        public int getDamage() {
            return features.getDamage();
        }

        public TileEntity createTileEntity() {
            return features.createTileEntity();
        }

        public boolean hasTileEntity() {
            return features.hasTileEntity(this);
        }

        public void updateTick(World world, BlockPos pos, Random rand) {
            features.updateTick(world, pos, this, rand);
        }

        public boolean hasTickRandomly() {
            return features.hasTickRandomly();
        }

        public String getName() {
            return features.getName();
        }

        @Override
        public void neighborChanged(World world, BlockPos pos, Block block, BlockPos fromPos) {
            features.neighbourChanged(world, pos, block, fromPos);
        }

        public SoundType getSoundType() {
            return features.getSoundType(this);
        }

        @Override
        public Material getMaterial() {
            return features.getMaterial(this);
        }

        @Override
        public MapColor getMapColor(IBlockAccess access, BlockPos pos) {
            return features.getMapColor(this, access, pos);
        }

        @Override
        public float getBlockHardness(World world, BlockPos pos) {
            return features.getHardness(this, world, pos);
        }

        @Override
        public boolean isOpaqueCube() {
            return features.isOpaqueCube(this);
        }

        @Override
        public boolean isFullCube() {
            return features.isFullCube(this);
        }

        @Override
        public int getLightValue(IBlockAccess world, BlockPos pos) {
            return features.getLightValue(this, world, pos);
        }

        @Override
        public int getLightOpacity(IBlockAccess world, BlockPos pos) {
            return features.getLightOpacity(this, world, pos);
        }

        @Override
        public EnumBlockRenderType getRenderType() {
            return features.getRenderType(this);
        }

        @Override
        public BlockFaceShape getBlockFaceShape(IBlockAccess world, BlockPos pos, EnumFacing facing) {
            return features.getFaceShape(this, world, pos, facing);
        }

        public int quantityDropped(int fortune, Random random) {
            return features.quantityDropped(this, fortune, random);
        }

        @Override
        public boolean isTopSolid() {
            return features.isTopSolid(this);
        }

        @Override
        public boolean canProvidePower() {
            return features.canProvidePower(this);
        }

        @Override
        public boolean canEntitySpawn(Entity entity) {
            return features.canEntitySpawn(this, entity);
        }

        @Override
        public AxisAlignedBB getBoundingBox(IBlockAccess blockAccess, BlockPos pos) {
            return features.getBoundingBox(this, blockAccess, pos);
        }

        @Nullable
        @Override
        public AxisAlignedBB getCollisionBoundingBox(IBlockAccess world, BlockPos pos) {
            return features.getCollisionAABB(this, world, pos);
        }

        @Override
        public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos) {
            return features.getSelectedAABB(this, world, pos);
        }

        @Override
        public int getStrongPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
            return features.getStrongPower(this, blockAccess, pos, side);
        }

        @Override
        public int getWeakPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
            return features.getWeakPower(this, blockAccess, pos, side);
        }
    }
}