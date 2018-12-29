package ru.mousecray.endmagic.blocks;

import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.EndMagicData;
import ru.mousecray.endmagic.tileentity.TileTPBlockH;

public class TeleportBlockHeight extends BlockContainer {
	
	public TeleportBlockHeight() {
		super(Material.ROCK);
        setUnlocalizedName("tpblockh");
        setRegistryName("tpblockh");
        setHardness(1.5F);
        setResistance(30F);
        setHarvestLevel("pickaxe", 2);
		setCreativeTab(EndMagicData.EM_CREATIVE);
	}
	
	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity) {
		if(!world.isRemote)
		if(world.getTileEntity(pos) instanceof TileTPBlockH) {
			TileTPBlockH tile = (TileTPBlockH)world.getTileEntity(pos);
			if(world.getBlockState(pos.up(2)).getBlock() == Blocks.STONE_BUTTON) {
				if(world.getBlockState(pos.up(3)).getBlock() == ListBlock.GEN_N && ((Integer)world.getBlockState(pos.up(3)).getValue(GeneratorBase.layers)).intValue() > 1) {
					if(world.getBlockState(pos.down()) == Blocks.REDSTONE_BLOCK.getDefaultState()) {	
						if(world.getBlockState(pos.east()) == Blocks.STONEBRICK.getDefaultState() && world.getBlockState(pos.west()) == Blocks.STONEBRICK.getDefaultState()) {
							if(world.getBlockState(pos.east().up(3)) == Blocks.STONEBRICK.getDefaultState() && world.getBlockState(pos.west().up(3)) == Blocks.STONEBRICK.getDefaultState()) {
									boolean flag = false;
									for(int y = 1; y < 3; y++) {
										if(world.getBlockState(pos.east().up(y)) == Blocks.COBBLESTONE_WALL.getDefaultState()) {
											flag = true;
										}
										else {
											flag = false;
										}
										
										if(world.getBlockState(pos.west().up(y)) == Blocks.COBBLESTONE_WALL.getDefaultState()) {
											flag = true;
										}
										else {
											flag = false;
										}
									}
									if(flag) {
										if(world.getBlockState(pos.up(2)).getValue(BlockButton.POWERED) == Boolean.valueOf(true)) {
											if(tile.getToPos() != null) {	
												BlockPos toPos = tile.getToPos();
												if(world.getBlockState(toPos) == getDefaultState()) {
													int i = world.getBlockState(pos.up(3)).getValue(GeneratorBase.layers);
													world.setBlockState(pos.up(3), ListBlock.GEN_N.getDefaultState().withProperty(GeneratorBase.layers, i - 1));
													entity.setPositionAndUpdate(toPos.getX()+0.5, toPos.getY()+1, toPos.getZ()+0.5);
												}
												else {
													int i = world.getBlockState(pos.up(3)).getValue(GeneratorBase.layers);
													world.setBlockState(pos.up(3), ListBlock.GEN_N.getDefaultState().withProperty(GeneratorBase.layers, i - 1));
												}
											}
										}
									}
							}
						}
						else if(world.getBlockState(pos.north()) == Blocks.STONEBRICK.getDefaultState() && world.getBlockState(pos.south()) == Blocks.STONEBRICK.getDefaultState()) {
							if(world.getBlockState(pos.north().up(3)) == Blocks.STONEBRICK.getDefaultState() && world.getBlockState(pos.south().up(3)) == Blocks.STONEBRICK.getDefaultState()) {
								boolean flag = false;
								for(int y = 1; y < 3; y++) {
									if(world.getBlockState(pos.north().up(y)) == Blocks.COBBLESTONE_WALL.getDefaultState()) {
										flag = true;
									}
									else {
										flag = false;
									}
									
									if(world.getBlockState(pos.south().up(y)) == Blocks.COBBLESTONE_WALL.getDefaultState()) {
										flag = true;
									}
									else {
										flag = false;
									}
								}
								if(flag) {
									if(world.getBlockState(pos.up(2)).getValue(BlockButton.POWERED) == Boolean.valueOf(true)) {
										if(tile.getToPos() != null) {	
											BlockPos toPos = tile.getToPos();
											if(world.getBlockState(toPos) == getDefaultState()) {
												int i = world.getBlockState(pos.up(3)).getValue(GeneratorBase.layers);
												world.setBlockState(pos.up(3), ListBlock.GEN_N.getDefaultState().withProperty(GeneratorBase.layers, i - 1));
												entity.setPositionAndUpdate(toPos.getX()+0.5, toPos.getY()+1, toPos.getZ()+0.5);
											}
											else {
												int i = world.getBlockState(pos.up(3)).getValue(GeneratorBase.layers);
												world.setBlockState(pos.up(3), ListBlock.GEN_N.getDefaultState().withProperty(GeneratorBase.layers, i - 1));
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileTPBlockH();
	}
}