package ru.mousecray.endmagic.blocks.dimensional;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.api.blocks.IEndSoil;
import ru.mousecray.endmagic.blocks.base.AutoMetaBlock;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.EnderBlockTypes.EnderGroundType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static ru.mousecray.endmagic.util.EnderBlockTypes.GROUND_TYPE;

public class EnderGroundGrass extends AutoMetaBlock implements IEndSoil {

    @Override
    public List<IProperty> properties() {
        return ImmutableList.of(GROUND_TYPE);
    }

    public EnderGroundGrass() {
        super(Material.ROCK);
        setHarvestLevel("pickaxe", 1);
        setHardness(3.0F);
        setResistance(10.0F);
        setTickRandomly(true);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.getValue(GROUND_TYPE).getMapColor();
    }

    @Override
    public EndSoilType getSoilType() {
        return EndSoilType.GRASS;
    }

    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return state.getValue(GROUND_TYPE).getSound();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);
        // TODO: Custom mechanics
    }


    @Override
    public boolean growPlant(World world, BlockPos soilPos, IBlockState soilState, Random rand) {
        EnderGroundType type = soilState.getValue(GROUND_TYPE);
        int chance = rand.nextInt(100);
        BlockPos plantPos = soilPos.up();
        if (world.isAirBlock(plantPos)) {
            IBlockState plantState = Blocks.AIR.getDefaultState();
            if (chance > 60) plantState = EMBlocks.enderTallgrass.getDefaultState();
            if (type == EnderGroundType.LIVE && chance > 80) plantState = EMBlocks.enderOrchid.getDefaultState();
            else if (type == EnderGroundType.DEAD && chance > 95)
                plantState = EMBlocks.blockCurseBush.getDefaultState();
            //TODO: Frozen plants
            if (plantState.getBlock() != Blocks.AIR) {
                if (!world.isRemote) world.setBlockState(plantPos, plantState);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canUseBonemeal() {
        return true;
    }
}