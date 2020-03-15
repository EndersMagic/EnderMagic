package ru.mousecray.endmagic.blocks.vanilla;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.blocks.IEndSoil;

public class BlockVanillaEndstone extends Block implements IEndSoil {

    public BlockVanillaEndstone() {
        super(Material.ROCK, MapColor.SAND);
        setHardness(3F);
        setResistance(15F);
        setSoundType(SoundType.STONE);
        setUnlocalizedName("whiteStone");
        setRegistryName("minecraft", "end_stone");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    //TODO: Remove debug
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) playerIn.sendMessage(new TextComponentString("Hey"));
        return true;
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
    }

    @Override
    public boolean canUseBonemeal() {
        return true;
    }
}
