package ru.mousecray.endmagic.gameobj.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import ru.mousecray.endmagic.api.blocks.EMBlockBush;
import ru.mousecray.endmagic.api.blocks.EnderPlantType;
import ru.mousecray.endmagic.init.EMBlocks;

public class ChrysofillumFruit extends EMBlockBush {

	public ChrysofillumFruit() {
		super(Material.PLANTS, MapColor.PURPLE);
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == EMBlocks.chrysVine;
	}
	
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnderPlantType.em_hang;
    }
}