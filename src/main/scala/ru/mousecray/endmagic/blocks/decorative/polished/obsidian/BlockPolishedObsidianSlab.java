package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.EnumHelper;
import ru.mousecray.endmagic.blocks.base.TranslucentSlab;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.client.render.model.baked.TranslucentPartsModel;
import ru.mousecray.endmagic.init.EMBlocks;

import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSideParts2.HorizontalFaceVisibility.visible_all;
import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.Utils.*;

public abstract class BlockPolishedObsidianSlab extends TranslucentSlab implements IPolishedObsidian {
    public BlockPolishedObsidianSlab() {
        super(Material.ICE, () -> EMBlocks.polishedObsidianSlabSingle, () -> EMBlocks.polishedObsidianSlabDouble);
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
            return RenderSideParts2.allSidesIsFull;
        }

        @Override
        public void registerModels(IModelRegistration modelRegistration) {
            modelRegistration.addBakedModelOverride(getRegistryName(), TranslucentPartsModel::new);
        }

        @Override
        public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
            return getActualObsidianFullState(state, worldIn, pos);
        }
    }

    public static class BlockPolishedObsidianSlabSingle extends BlockPolishedObsidianSlab {
        public boolean isDouble() {
            return false;
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
            return getObsidianSlabParts(state);
        }

        @Override
        public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
            return getActualObsidianSlabState(state, worldIn, pos);
        }
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
