package ru.mousecray.endmagic.blocks.dimensional;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.mousecray.endmagic.api.blocks.IEndSoil;
import ru.mousecray.endmagic.blocks.base.AutoMetaBlock;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import java.util.List;

public class EnderGroundStone extends AutoMetaBlock implements IEndSoil {

    @Override
    public List<IProperty> properties() {
        return ImmutableList.of(EnderBlockTypes.GROUND_TYPE);
    }

    public EnderGroundStone() {
        super(Material.ROCK);

        setHarvestLevel("pickaxe", 1);
        setHardness(3.0F);
        setResistance(15.0F);
        setSoundType(SoundType.STONE);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return MapColor.SAND;
    }


    @Override
    public boolean canUseBonemeal() {
        return false;
    }
}