package ru.mousecray.endmagic.items;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.util.registry.NameProvider;

public class EMSeeds extends Item implements IPlantable, NameProvider, ItemOneWhiteEMTextured {

    private final Supplier<Block> crops;
    private final EndSoilType[] soilBlockID;
    @Nullable
    private final String textTooltip;
    private final String name;
	private boolean isEndStone;

    public EMSeeds(Supplier<Block> crops, String name, @Nullable String text, boolean isEndStone, EndSoilType... soil) {
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
            
            if(EMUtils.isSoil(state, isEndStone, false, soilBlockID)) {
            	if(!world.isRemote) world.setBlockState(pos.up(), getPlant(world, pos));

	            if (player instanceof EntityPlayerMP) CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos.up(), itemstack);
	
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
        return crops.get().getDefaultState();
    }

    @Override
    public String texture() {
        return "ender_seeds";
    }
}