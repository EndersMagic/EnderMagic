package ru.mousecray.endmagic.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.tileentity.TileEntityImmortalHeart;
import ru.mousecray.endmagic.util.worldgen.ImmortalTree;

import javax.annotation.Nullable;

public class BlockImmortalHeart extends BlockWithTile<TileEntityImmortalHeart>
{
    public BlockImmortalHeart()
    {
        super(Material.WOOD);
        setUnlocalizedName("immortal_heart");
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ImmortalTree.generate(worldIn, pos.up(),  new BlockPos(5, 20, 8), 50);
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i)
    {
        return new TileEntityImmortalHeart();
    }
}
