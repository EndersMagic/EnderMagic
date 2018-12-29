package ru.mousecray.endmagic.items;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.EndMagicData;

public class NaturalPickaxe extends ItemPickaxe {
	
    public NaturalPickaxe(ToolMaterial material) {
        super(material);
        setRegistryName("natural_pickaxe");
        setUnlocalizedName("natural_pickaxe");
		setCreativeTab(EndMagicData.EM_CREATIVE);
    }
    
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
    	if(world.rand.nextInt(100) == 0) {
    		if(!world.isRemote) {
    			state.getBlock().dropBlockAsItem(world, pos, state, world.rand.nextInt(5));
    		}
    	}  	
    	super.onBlockDestroyed(stack, world, state, pos, entityLiving);
    	return true;
    }
    
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		tooltip.add(I18n.format("tooltip.natural_pickaxe", "1%"));
	}
}
