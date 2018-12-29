package ru.mousecray.endmagic.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import ru.mousecray.endmagic.blocks.ListBlock;

public abstract class AbstractEnderTreeGen extends WorldGenerator {
	
    public final IBlockState metaWood;
    public final IBlockState metaLeaves;

    private final boolean doBlockNotify;
    
    public AbstractEnderTreeGen(boolean notify, IBlockState metaWood, IBlockState metaLevaes) {
    	super(notify);
        doBlockNotify = notify;
        this.metaWood = metaWood;
        this.metaLeaves = metaLevaes;
    }
    
    protected boolean canGrowInto(Block blockType) {
        Material material = blockType.getDefaultState().getMaterial();
        return material == Material.AIR || material == Material.LEAVES || blockType == Blocks.END_STONE || blockType == Blocks.OBSIDIAN || blockType == ListBlock.ENDER_LOGS || blockType == ListBlock.ENDER_SAPLING;
    }
	
    public void generateSaplings(World world, Random random, BlockPos pos) {}

    public boolean isReplaceable(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getBlock().isWood(world, pos) || canGrowInto(state.getBlock());
    }
    
    protected void setBlockN(World world, BlockPos pos, IBlockState state) {
        if (doBlockNotify) {
            world.setBlockState(pos, state, 3);
        }
        else {
            world.setBlockState(pos, state, 18);
        }
    }
}
