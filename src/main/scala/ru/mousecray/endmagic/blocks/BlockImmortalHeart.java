package ru.mousecray.endmagic.blocks;

import com.sun.istack.internal.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.tileentity.TileEntityImmortalHeart;
import ru.mousecray.endmagic.util.worldgen.ImmortalTree;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BlockImmortalHeart extends BlockWithTile<TileEntityImmortalHeart>
{
    public BlockImmortalHeart()
    {
        super(Material.WOOD);
        setUnlocalizedName("immortal_heart");
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(!worldIn.isRemote)
            ImmortalTree.generate(worldIn, pos, 60, 12);
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i)
    {
        return new TileEntityImmortalHeart();
    }
}
