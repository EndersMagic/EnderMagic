package ru.mousecray.endmagic.blocks;

import java.util.Arrays;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.trees.EMSapling;

public class EnderBlockTypes {

	public static enum EnderTreeType implements IStringSerializable, EMSapling.SaplingThings {
	    DRAGON("dragon", MapColor.PURPLE) {
	        public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
	            return Arrays.stream(EnumFacing.HORIZONTALS)
	                    .map(pos::offset)
	                    .map(worldIn::getBlockState)
	                    .map(IBlockState::getBlock)
	                    .anyMatch(Blocks.END_STONE::equals);
	        }
	    },
	    NATURAL("natural", MapColor.BLUE),
	    IMMORTAL("immortal", MapColor.EMERALD),
	    PHANTOM("phantom", MapColor.SILVER);

	    private final String name;
	    private final MapColor mapColor;

	    EnderTreeType(String name, MapColor mapColor) {
	        this.name = name;
	        this.mapColor = mapColor;
	    }

	    @Override
	    public String getName() {
	        return name;
	    }
	}
	
	public static enum EndeGrassType implements IStringSerializable {
	    LIVE("live", MapColor.BLUE),
	    DEAD("dead", MapColor.GRAY),
	    FROZEN("frozen", MapColor.DIAMOND);

	    private final String name;
	    private final MapColor mapColor;

	    EndeGrassType(String name, MapColor mapColor) {
	        this.name = name;
	        this.mapColor = mapColor;
	    }

	    @Override
	    public String getName() {
	        return name;
	    }
	}
}