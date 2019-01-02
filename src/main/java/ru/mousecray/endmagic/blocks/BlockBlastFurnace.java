package ru.mousecray.endmagic.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.tileentity.TileBlastFurnace;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BlockBlastFurnace extends BlockWithTile<TileBlastFurnace> {
    public void addRecipe(Item coal, Item iron, Item steel) {
        recipes.add(new BlastRecipe(coal, iron, steel));
    }

    private List<BlastRecipe> recipes;
    private Set<Item> coal;
    private Set<Item> iron;
    private Set<Item> steel;

    public BlockBlastFurnace() {
        super(Material.ROCK);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        playerIn.openGui(EM.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileBlastFurnace();
    }

    private Set<Item> getCollect(Function<BlastRecipe, Item> p) {
        return recipes.stream().map(p).collect(Collectors.toSet());
    }

    public Set<Item> coalSet() {
        if (coal == null) {
            coal = getCollect(i -> i.coal);
        }
        return coal;
    }

    public Set<Item> ironSet() {
        if (iron == null) {
            iron = getCollect(i -> i.iron);
        }
        return iron;
    }

    public Set<Item> steelSet() {
        if (steel == null) {
            steel = getCollect(i -> i.steel);
        }
        return steel;
    }

    private class BlastRecipe {
        public final Item coal, iron, steel;

        public BlastRecipe(Item coal, Item iron, Item steel) {
            this.coal = coal;
            this.iron = iron;
            this.steel = steel;
        }
    }
}
