package ru.mousecray.endmagic.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.api.blocks.IEndSoil;
import ru.mousecray.endmagic.util.registry.NameProvider;

public class EMSeeds extends Item implements IPlantable, NameProvider, ItemOneWhiteEMTextured {

    private final Block crops;
    private final EndSoilType[] soilBlockID;
    @Nullable
    private final String textTooltip;
    private final String name;
	private boolean isEndStone;

    public EMSeeds(Block crops, String name, @Nullable String text, boolean isEndStone, EndSoilType... soil) {
        this.crops = crops;
        this.name = name;
        this.soilBlockID = soil;
        this.textTooltip = text;
        this.isEndStone = isEndStone;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && world.isAirBlock(pos.up())) {
            
        	boolean isTrue = false;
        	
            for (int i = 0; i < soilBlockID.length; ++i) {
            	if (state.getBlock() instanceof IEndSoil && ((IEndSoil)state.getBlock()).getSoilType() == soilBlockID[i]) {
            		isTrue = true;
            		break;
            	}
            }
            System.out.println("test0");
            System.out.println(isTrue);
            System.out.println(crops.toString());
            
            if(isTrue || (isEndStone && state.getBlock() == Blocks.END_STONE)) {
            	System.out.println("test1");
                System.out.println(isTrue);
                System.out.println(crops.toString());
            	if(!world.isRemote) {
                	System.out.println("test2");
                    System.out.println(isTrue);
                    System.out.println(crops.toString());
            		world.setBlockState(pos.up(), getPlant(world, pos));
            	}
	
            	System.out.println("test3");
                System.out.println(isTrue);
                System.out.println(crops.toString());
	            if (player instanceof EntityPlayerMP) {
	                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos.up(), itemstack);
	            }
	
	            itemstack.shrink(1);
	            return EnumActionResult.SUCCESS;
            }
            else return EnumActionResult.FAIL;
            
        } else return EnumActionResult.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        if (textTooltip != null) tooltip.add(I18n.format(textTooltip));
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return crops.getDefaultState();
    }

    @Override
    public String texture() {
        return "ender_seeds";
    }
}