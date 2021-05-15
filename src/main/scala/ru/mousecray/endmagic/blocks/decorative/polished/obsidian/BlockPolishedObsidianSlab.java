package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPurpurSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSideParts2.HorizontalFaceVisibility;
import ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSideParts2.VerticalFaceVisibility;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.client.render.model.baked.SeparatedRenderLayersBakedModel;
import ru.mousecray.endmagic.client.render.model.baked.TranslucentPartsModel;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.registry.ITechnicalBlock;
import ru.mousecray.endmagic.util.render.RenderUtils;

import javax.annotation.Nullable;
import java.util.Random;

import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSideParts2.HorizontalFaceVisibility.invisible_bottom;
import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSideParts2.HorizontalFaceVisibility.invisible_top;
import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSideParts2.PROPERTY;

public abstract class BlockPolishedObsidianSlab extends BlockPurpurSlab implements ITechnicalBlock, IPolishedObsidian {
    public BlockPolishedObsidianSlab() {
        super();
        try {
            EnumHelper.setFailsafeFieldValue(Block.class.getDeclaredField("blockMaterial"), this, Material.ICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSoundType(SoundType.STONE);
        setHardness(50);
        setResistance(2000);
        setLightOpacity(20);
    }

    public static class BlockPolishedObsidianSlabDouble extends BlockPolishedObsidianSlab {
        public boolean isDouble() {
            return true;
        }

        @Override
        public RenderSideParts2 getObsidianParts(IBlockState state) {
            return RenderSideParts2.apply(VerticalFaceVisibility.visible_all, VerticalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all);
        }

        @Override
        public void registerModels(IModelRegistration modelRegistration) {
            modelRegistration.addBakedModelOverride(getRegistryName(), TranslucentPartsModel::new);
        }

        @Override
        public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
            IBlockState up = worldIn.getBlockState(pos.offset(EnumFacing.UP));
            IBlockState down = worldIn.getBlockState(pos.offset(EnumFacing.DOWN));
            IBlockState east = worldIn.getBlockState(pos.offset(EnumFacing.EAST));
            IBlockState west = worldIn.getBlockState(pos.offset(EnumFacing.WEST));
            IBlockState south = worldIn.getBlockState(pos.offset(EnumFacing.SOUTH));
            IBlockState north = worldIn.getBlockState(pos.offset(EnumFacing.NORTH));

            VerticalFaceVisibility upR = getActualVisibility(up, i -> i.down, VerticalFaceVisibility.visible_all);
            VerticalFaceVisibility downR = getActualVisibility(down, i -> i.up, VerticalFaceVisibility.visible_all);

            HorizontalFaceVisibility eastR = getActualVisibility(east, i -> i.west, HorizontalFaceVisibility.visible_all);
            HorizontalFaceVisibility westR = getActualVisibility(west, i -> i.east, HorizontalFaceVisibility.visible_all);
            HorizontalFaceVisibility southR = getActualVisibility(south, i -> i.north, HorizontalFaceVisibility.visible_all);
            HorizontalFaceVisibility northR = getActualVisibility(north, i -> i.south, HorizontalFaceVisibility.visible_all);

            return state.withProperty(PROPERTY, RenderSideParts2.apply(upR, downR, eastR, southR, westR, northR));
        }
    }

    public static class BlockPolishedObsidianSlabSingle extends BlockPolishedObsidianSlab {
        public boolean isDouble() {
            return false;
        }

        @Nullable
        @Override
        public CreativeTabs getCustomCreativeTab() {
            return EM.EM_CREATIVE;
        }

        @Override
        public ItemBlock getCustomItemBlock(Block block) {
            return new ItemSlab(EMBlocks.polishedObsidianSlabSingle, EMBlocks.polishedObsidianSlabSingle, EMBlocks.polishedObsidianSlabDouble);
        }

        @Override
        public void registerModels(IModelRegistration modelRegistration) {
            modelRegistration.addBakedModelOverride(new ModelResourceLocation(getRegistryName(), "half=bottom,render_side_parts=default,variant=default"),
                    TranslucentPartsModel::new);

            modelRegistration.addBakedModelOverride(new ModelResourceLocation(getRegistryName(), "half=top,render_side_parts=default,variant=default"),
                    TranslucentPartsModel::new);
        }

        @Override
        public RenderSideParts2 getObsidianParts(IBlockState state) {
            switch (state.getValue(HALF)) {
                case TOP:
                    return RenderSideParts2.apply(VerticalFaceVisibility.visible_all, VerticalFaceVisibility.invisible_all, invisible_bottom, invisible_bottom, invisible_bottom, invisible_bottom);
                case BOTTOM:
                    return RenderSideParts2.apply(VerticalFaceVisibility.invisible_all, VerticalFaceVisibility.visible_all, invisible_top, invisible_top, invisible_top, invisible_top);
                default:
                    throw new IllegalArgumentException("Unsupported value of HALF property");
            }
        }

        @Override
        public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
            IBlockState up = worldIn.getBlockState(pos.offset(EnumFacing.UP));
            IBlockState down = worldIn.getBlockState(pos.offset(EnumFacing.DOWN));
            IBlockState east = worldIn.getBlockState(pos.offset(EnumFacing.EAST));
            IBlockState west = worldIn.getBlockState(pos.offset(EnumFacing.WEST));
            IBlockState south = worldIn.getBlockState(pos.offset(EnumFacing.SOUTH));
            IBlockState north = worldIn.getBlockState(pos.offset(EnumFacing.NORTH));

            switch (state.getValue(HALF)) {
                case TOP: {

                    VerticalFaceVisibility upR = getActualVisibility(up, i -> i.down, VerticalFaceVisibility.visible_all);

                    HorizontalFaceVisibility eastR = getActualVisibility(east, i -> i.west, HorizontalFaceVisibility.visible_all);
                    HorizontalFaceVisibility westR = getActualVisibility(west, i -> i.east, HorizontalFaceVisibility.visible_all);
                    HorizontalFaceVisibility southR = getActualVisibility(south, i -> i.north, HorizontalFaceVisibility.visible_all);
                    HorizontalFaceVisibility northR = getActualVisibility(north, i -> i.south, HorizontalFaceVisibility.visible_all);

                    return state.withProperty(PROPERTY, RenderSideParts2.apply(upR, VerticalFaceVisibility.visible_all, eastR, southR, westR, northR));
                }

                case BOTTOM: {

                    VerticalFaceVisibility downR = getActualVisibility(down, i -> i.up, VerticalFaceVisibility.visible_all);

                    HorizontalFaceVisibility eastR = getActualVisibility(east, i -> i.west, HorizontalFaceVisibility.visible_all);
                    HorizontalFaceVisibility westR = getActualVisibility(west, i -> i.east, HorizontalFaceVisibility.visible_all);
                    HorizontalFaceVisibility southR = getActualVisibility(south, i -> i.north, HorizontalFaceVisibility.visible_all);
                    HorizontalFaceVisibility northR = getActualVisibility(north, i -> i.south, HorizontalFaceVisibility.visible_all);

                    return state.withProperty(PROPERTY, RenderSideParts2.apply(VerticalFaceVisibility.visible_all, downR, eastR, southR, westR, northR));
                }

                default:
                    throw new IllegalArgumentException("Unsupported value of HALF property");
            }
        }
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(EMBlocks.polishedObsidianSlabSingle);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(EMBlocks.polishedObsidianSlabSingle);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.PURPLE;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return isDouble() ? new BlockStateContainer(this, VARIANT, RenderSideParts2.PROPERTY) : new BlockStateContainer(this, HALF, VARIANT, RenderSideParts2.PROPERTY);
    }
}
