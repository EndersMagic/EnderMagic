package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.mousecray.endmagic.blocks.base.TranslucentBlock;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.client.render.model.baked.TranslucentPartsModel;
import ru.mousecray.endmagic.util.registry.IExtendedProperties;

import java.util.function.Function;

import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSideParts2.*;

public class BlockPolishedObsidian extends TranslucentBlock implements IPolishedObsidian, IExtendedProperties {

    public BlockPolishedObsidian() {
        super(Material.ICE, MapColor.PURPLE, IPolishedObsidian.class);
        setSoundType(SoundType.STONE);
        setHardness(50);
        setResistance(2000);
        setLightOpacity(20);
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type) {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PROPERTY);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
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

    @Override
    public RenderSideParts2 getObsidianParts(IBlockState state) {
        return RenderSideParts2.apply(VerticalFaceVisibility.visible_all, VerticalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all);
    }

    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        modelRegistration.addBakedModelOverride(getRegistryName(), TranslucentPartsModel::new);
    }
}
