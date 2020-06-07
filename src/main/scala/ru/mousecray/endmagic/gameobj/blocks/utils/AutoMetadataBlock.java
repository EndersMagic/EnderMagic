package ru.mousecray.endmagic.gameobj.blocks.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
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
import org.apache.commons.lang3.tuple.Pair;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.registry.ITechnicalBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class AutoMetadataBlock extends Block implements ITechnicalBlock {

    public List<IBlockState> metaToState;
    public Map<ImmutableMap<IProperty<?>, Comparable<?>>, Integer> stateToMeta;
    public Map<FeatureTypes, PropertyFeature> features;

    public AutoMetadataBlock(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    public AutoMetadataBlock(Material materialIn) {
        super(materialIn);
    }

    public abstract List<IProperty<?>> properties();

    /*----Magic methods which you must implement in IMetadataBlock child classes----*/
    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        getFeatureListFromState(state, FeatureTypes.updateTick).updateTick(world, pos, state, rand);
    }

    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return getFeatureListFromState(state, FeatureTypes.createTileEntity).createTileEntity(world);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return getFeatureListFromState(state, FeatureTypes.hasTileEntity).hasTileEntity();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getFeatureListFromState(state, FeatureTypes.isSubtypeSource).getDamage();
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (IFeaturesList allowedValue : getPropertyForFeature(FeatureTypes.isSubtypeSource).getAllowedValues())
            items.add(new ItemStack(Item.getItemFromBlock(this), 1, allowedValue.getDamage()));
    }

    @Override
    public ItemBlock getCustomItemBlock(Block block) {
        return new AutoMetaItemBlock(this, getPropertyForFeature(FeatureTypes.isSubtypeSource) != propertyFeatureItself);
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return getFeatureListFromState(state, FeatureTypes.quantityDropped).quantityDropped(fortune, random);
    }

    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return getFeatureListFromState(state, FeatureTypes.getSoundType).getSoundType(world, pos, entity);
    }
    /*----Magic methods which you must implement in IMetadataBlock child classes----*/

    private IFeaturesList getFeatureListFromState(IBlockState state, FeatureTypes featureType) {
        return state.getValue(getPropertyForFeature(featureType));
    }

    public PropertyFeature<?> getPropertyForFeature(FeatureTypes isSubtypeSource) {
        return features.get(isSubtypeSource);
    }

    public class AutoMetaItemBlock extends ItemBlock {

        public AutoMetaItemBlock(Block block, boolean hasSubtypes) {
            super(block);
            setHasSubtypes(hasSubtypes);
        }

        @Override
        public String getUnlocalizedName(ItemStack stack) {
            String name = super.getUnlocalizedName();
            if (getHasSubtypes())
                name = name + metaToState.get(stack.getMetadata()).getValue(getPropertyForFeature(FeatureTypes.isSubtypeSource)).getName();
            return name;
        }

        @Override
        public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
            return getFeatureListFromState(newState, FeatureTypes.canPlaceBlockAt).canPlaceBlockAt(world, pos) && super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
        }

        @Override
        public int getMetadata(int damage) {
            return getHasSubtypes() ? damage : 0;
        }
    }

    private ItselfFeature itself = new ItselfFeature();

    private PropertyFeature propertyFeatureItself = new PropertyFeature("blockItself", ItselfFeature.class, ImmutableSet.of(itself));

    private class ItselfFeature implements IFeaturesList {
        @Override
        public String name() {
            return "blockItself";
        }

        @Override
        public int ordinal() {
            return 0;
        }

        public void neighbourChanged(World world, BlockPos pos, Block block, BlockPos fromPos) {
        }

        public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        }

        @Nullable
        public TileEntity createTileEntity(World world) {
            return null;
        }

        public boolean hasTileEntity() {
            return false;
        }

        public boolean hasTickRandomly() {
            return false;
        }

        public Material getMaterial() {
            return blockMaterial;
        }

        public MapColor getMapColor(IBlockAccess world, BlockPos pos) {
            return blockMapColor;
        }

        public float getHardness(World world, BlockPos pos) {
            return blockHardness;
        }

        public boolean isOpaqueCube() {
            return true;
        }

        public boolean isFullCube() {
            return true;
        }

        public int getLightValue(IBlockAccess world, BlockPos pos) {
            return lightValue;
        }

        public int getLightOpacity(IBlockAccess world, BlockPos pos) {
            return lightOpacity;
        }

        @Nullable
        public EnumBlockRenderType getRenderType() {
            return EnumBlockRenderType.MODEL;
        }

        public boolean canProvidePower() {
            return false;
        }

        public boolean canEntitySpawn(Entity entity) {
            return true;
        }

        public SoundType getSoundType(World world, BlockPos pos, Entity entity) {
            return blockSoundType;
        }

        public boolean isTopSolid() {
            return blockMaterial.isSolid();
        }

        public int quantityDropped(int fortune, Random rand) {
            return 1;
        }

        public BlockFaceShape getFaceShape(IBlockAccess world, BlockPos pos, EnumFacing facing) {
            return BlockFaceShape.SOLID;
        }

        @Nullable
        public AxisAlignedBB getCollisionAABB(IBlockAccess world, BlockPos pos) {
            return Block.FULL_BLOCK_AABB;
        }

        @Nullable
        public AxisAlignedBB getSelectedAABB(IBlockAccess world, BlockPos pos) {
            return Block.FULL_BLOCK_AABB;
        }

        public AxisAlignedBB getBoundingBox(IBlockAccess world, BlockPos pos) {
            return Block.FULL_BLOCK_AABB;
        }

        public int getWeakPower(IBlockAccess world, BlockPos pos, EnumFacing side) {
            return 0;
        }

        public int getStrongPower(IBlockAccess world, BlockPos pos, EnumFacing side) {
            return 0;
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        Pair<List<IBlockState>, Map<ImmutableMap<IProperty<?>, Comparable<?>>, Integer>> listMapPair = BlockStateUtils.buildStateIsomorphism(properties());
        metaToState = listMapPair.getLeft();
        stateToMeta = listMapPair.getRight();
        features = BlockStateUtils.prepareFeatures(propertyFeatureItself, properties().stream().filter(p -> p instanceof PropertyFeature).map(p -> (PropertyFeature) p).collect(Collectors.toList()));
        IProperty[] properties = properties().toArray(new IProperty[properties().size() + 1]);
        properties[properties.length - 1] = propertyFeatureItself;
        return new BlockStateContainer(this, properties);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if (stateToMeta.containsKey(state.getProperties()))
            return stateToMeta.get(state.getProperties());
        else
            throw new IllegalArgumentException("state is " + state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (0 <= meta && meta < metaToState.size())
            return metaToState.get(meta);
        else
            throw new IllegalArgumentException("meta is " + meta);
    }

    @Nullable
    @Override
    public CreativeTabs getCustomCreativeTab() {
        return EM.EM_CREATIVE;
    }
}
