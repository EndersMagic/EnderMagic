package ru.mousecray.endmagic.gameobj.blocks.dimensional;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.blocks.IEndSoil;
import ru.mousecray.endmagic.api.metadata.BlockStateGenerator;
import ru.mousecray.endmagic.api.metadata.MetadataBlock;
import ru.mousecray.endmagic.api.metadata.PropertyFeature;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnderStone extends MetadataBlock implements IEndSoil {

    public static final PropertyFeature<EnderBlockTypes.EnderGroundType> GROUND_TYPE = EnderBlockTypes.GROUND_TYPE;

    public EnderStone() {
        super(Material.ROCK);
        setHarvestLevel("pickaxe", 1);
        setHardness(3.0F);
        setResistance(15.0F);
    }

    @Override
    protected BlockStateContainer createBlockStateContainer() {
        return BlockStateGenerator.create(this).addFeatures(GROUND_TYPE).buildContainer();
    }

    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return SoundType.STONE;
    }

    @Nonnull
    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return MapColor.SAND;
    }

    @Override
    public boolean canUseBonemeal() {
        return false;
    }
}