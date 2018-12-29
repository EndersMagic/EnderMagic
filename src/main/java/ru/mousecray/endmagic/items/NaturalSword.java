package ru.mousecray.endmagic.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.EndMagicData;

public class NaturalSword extends ItemSword {
	
	public NaturalSword(ToolMaterial material) {
        super(material);
        setRegistryName("natural_sword");
        setUnlocalizedName("natural_sword");
		setCreativeTab(EndMagicData.EM_CREATIVE);
    }
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if(attacker.getEntityWorld().rand.nextInt(100) == 0) {	
			if(!attacker.getEntityWorld().isRemote) {
				BlockPos pos = target.getPosition();
				attacker.getEntityWorld().setBlockState(pos.up(), Blocks.WEB.getDefaultState());
			}
		}
		super.hitEntity(stack, target, attacker);
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		tooltip.add(I18n.format("tooltip.natural_sword", "1%"));
	}
}
