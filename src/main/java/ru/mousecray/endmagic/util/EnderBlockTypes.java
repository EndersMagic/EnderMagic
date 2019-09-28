package ru.mousecray.endmagic.util;

import java.util.Arrays;

import net.minecraft.block.SoundType;
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
	    NATURAL("natural", MapColor.BROWN),
	    IMMORTAL("immortal", MapColor.EMERALD),
	    PHANTOM("phantom", MapColor.SILVER);

	    private final String name;
	    private final MapColor mapColor;

	    EnderTreeType(String name, MapColor mapColor) {
	        this.name = name;
	        this.mapColor = mapColor;
	    }
	    
	    public MapColor getMapColor() {
			return mapColor;
		}
	    
	    @Override
        public String toString() {
            return name;
        }

	    @Override
	    public String getName() {
	        return name;
	    }
	}

	public static enum EnderGroundType implements IStringSerializable {
	    LIVE("live", MapColor.BLUE, SoundType.GROUND),
	    DEAD("dead", MapColor.GRAY, SoundType.SAND),
	    FROZEN("frozen", MapColor.DIAMOND, SoundType.SNOW);

	    private final String name;
	    private final MapColor mapColor;
	    private final SoundType sound;

	    EnderGroundType(String name, MapColor mapColor, SoundType sound) {
	        this.name = name;
	        this.mapColor = mapColor;
	        this.sound = sound;
	    }
	    
	    public MapColor getMapColor() {
			return mapColor;
		}
	    
	    public SoundType getSound() {
			return sound;
		}
	    
	    @Override
        public String toString() {
            return name;
        }

	    @Override
	    public String getName() {
	        return name;
	    }
	}
	public static enum EMBlockHalf implements IStringSerializable {
        TOP("top"),
        BOTTOM("bottom"),
        DOUBLE("double");

        private final String name;

        private EMBlockHalf(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public String getName() {
            return name;
        }
	}
}