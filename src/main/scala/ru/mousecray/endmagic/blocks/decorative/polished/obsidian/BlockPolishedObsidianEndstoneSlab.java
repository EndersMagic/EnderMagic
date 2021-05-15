package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.mousecray.endmagic.blocks.base.TranslucentSlab;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.client.render.model.baked.SeparatedRenderLayersBakedModel;
import ru.mousecray.endmagic.client.render.model.baked.TranslucentPartsModel;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.render.RenderUtils;

import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.Utils.*;

public abstract class BlockPolishedObsidianEndstoneSlab extends TranslucentSlab implements IPolishedObsidian {

    public BlockPolishedObsidianEndstoneSlab() {
        super(Material.ICE, () -> EMBlocks.polishedObsidianEndstoneSlabSingle, () -> EMBlocks.polishedObsidianEndstoneSlabDouble);
        setSoundType(SoundType.STONE);
        setHardness(50);
        setResistance(2000);
        setLightOpacity(20);
    }

    public static class BlockPolishedObsidianEndstoneSlabDouble extends BlockPolishedObsidianEndstoneSlab {
        public boolean isDouble() {
            return true;
        }

        @Override
        public void registerModels(IModelRegistration modelRegistration) {
            modelRegistration.addBakedModelOverride(getRegistryName(),
                    __ -> new SeparatedRenderLayersBakedModel(ImmutableMap.of(
                            BlockRenderLayer.SOLID, RenderUtils.loadEMJsonModel("models/block/polished_obsidian/bricks/solid"),
                            BlockRenderLayer.TRANSLUCENT, new TranslucentPartsModel(RenderUtils.loadEMJsonModel("models/block/polished_obsidian/bricks/translucent"))
                    ), BlockRenderLayer.SOLID));
        }

        @Override
        public RenderSideParts2 getObsidianParts(IBlockState state) {
            return RenderSideParts2.allSidesIsFull;
        }

        @Override
        public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
            return getActualObsidianFullState(state, worldIn, pos);
        }
    }

    public static class BlockPolishedObsidianEndstoneSlabSingle extends BlockPolishedObsidianEndstoneSlab {
        public boolean isDouble() {
            return false;
        }

        @Override
        public void registerModels(IModelRegistration modelRegistration) {
            modelRegistration.addBakedModelOverride(new ModelResourceLocation(getRegistryName(), "half=bottom,render_side_parts=default,variant=default"),
                    __ -> new SeparatedRenderLayersBakedModel(ImmutableMap.of(
                            BlockRenderLayer.SOLID, RenderUtils.loadEMJsonModel("models/block/polished_obsidian/bricks/slab/bottom_solid"),
                            BlockRenderLayer.TRANSLUCENT, new TranslucentPartsModel(RenderUtils.loadEMJsonModel("models/block/polished_obsidian/bricks/slab/bottom_translucent"))
                    ), BlockRenderLayer.SOLID));

            modelRegistration.addBakedModelOverride(new ModelResourceLocation(getRegistryName(), "half=top,render_side_parts=default,variant=default"),
                    __ -> new SeparatedRenderLayersBakedModel(ImmutableMap.of(
                            BlockRenderLayer.SOLID, RenderUtils.loadEMJsonModel("models/block/polished_obsidian/bricks/slab/top_solid"),
                            BlockRenderLayer.TRANSLUCENT, new TranslucentPartsModel(RenderUtils.loadEMJsonModel("models/block/polished_obsidian/bricks/slab/top_translucent"))
                    ), BlockRenderLayer.SOLID));
        }

        @Override
        public RenderSideParts2 getObsidianParts(IBlockState state) {
            return getObsidianSlabParts(state);
        }

        @Override
        public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
            return getActualObsidianSlabState(state, worldIn, pos);
        }
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.SOLID;
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
