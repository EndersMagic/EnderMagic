package ru.mousecray.endmagic.gameobj.blocks.dimensional;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.api.blocks.IEndSoil;
import ru.mousecray.endmagic.api.metadata.BlockStateGenerator;
import ru.mousecray.endmagic.api.metadata.MetadataBlock;
import ru.mousecray.endmagic.api.metadata.PropertyFeature;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import java.util.Random;

public class EnderGrass extends MetadataBlock implements IEndSoil {

    public static final PropertyFeature<EnderBlockTypes.EnderGroundType> GROUND_TYPE = EnderBlockTypes.GROUND_TYPE;

    public EnderGrass() {
        super(Material.ROCK);
        setHarvestLevel("pickaxe", 1);
        setHardness(3.0F);
        setResistance(10.0F);
        setTickRandomly(true);
    }

    @Override
    protected BlockStateContainer createBlockStateContainer() {
        return BlockStateGenerator.create(this).addFeatures(GROUND_TYPE).buildContainer();
    }

    @Override
    public EndSoilType getSoilType() {
        return EndSoilType.GRASS;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(EMBlocks.blockEnderStone);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);
        // TODO: Custom mechanics
    }

    @Override
    public boolean growPlant(World world, BlockPos soilPos, IBlockState soilState, Random rand) {
        EnderBlockTypes.EnderGroundType type = soilState.getValue(GROUND_TYPE);
        int chance = rand.nextInt(100);
        BlockPos plantPos = soilPos.up();
        if (world.isAirBlock(plantPos)) {
            IBlockState plantState = Blocks.AIR.getDefaultState();
            if (chance > 60) plantState = EMBlocks.enderTallgrass.getDefaultState();
            if (type == EnderBlockTypes.EnderGroundType.LIVE && chance > 80) plantState = EMBlocks.enderOrchid.getDefaultState();
            else if (type == EnderBlockTypes.EnderGroundType.DEAD && chance > 95) plantState = EMBlocks.blockCurseBush.getDefaultState();
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