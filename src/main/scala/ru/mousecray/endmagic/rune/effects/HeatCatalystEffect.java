package ru.mousecray.endmagic.rune.effects;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.rune.RuneEffect;
import ru.mousecray.endmagic.tileentity.TileBlastFurnace;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class HeatCatalystEffect extends RuneEffect {


    @Override
    public boolean isValidTarget(IBlockState state, World world, BlockPos pos) {
        return state.getBlock() == Blocks.FURNACE || state.getBlock() == Blocks.LIT_FURNACE || state.getBlock() == EMBlocks.blockBlastFurnace;
    }

    @Override
    public boolean isTickable() {
        return true;
    }

    @Override
    public void onUpdate(World world, BlockPos runePos, EnumFacing side, double runePower) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(runePos);
            if (tileEntity instanceof TileEntityFurnace) {
                TileEntityFurnace tile = (TileEntityFurnace) tileEntity;
                if (isFurnaceStartSmelting(tile)) {
                    int newTotalCookTime = max(1, (int) (getTotalCookTime(tile) / (runePower * 200 + 1)));
                    setTotalCookTime(tile, newTotalCookTime);
                    setCookTime(tile, min(getCookTime(tile), newTotalCookTime - 1));
                }
            } else if (tileEntity instanceof TileBlastFurnace) {
                TileBlastFurnace tile = (TileBlastFurnace) tileEntity;
                if (tile.isBurning())
                    tile.boost();
            }
        }
    }

    public boolean isFurnaceStartSmelting(TileEntityFurnace tile) {
        return tile.isBurning() && canSmelt(tile) && getCookTime(tile) == 1;
    }

    private void setCookTime(TileEntityFurnace tile, int cookTime) {
        tile.setField(2, cookTime);
    }

    private int getCookTime(TileEntityFurnace tile) {
        return tile.getField(2);
    }

    private void setTotalCookTime(TileEntityFurnace tile, int totalCookTime) {
        tile.setField(3, totalCookTime);
    }

    private int getTotalCookTime(TileEntityFurnace tile) {
        return tile.getField(3);
    }

    private Method canSmelt = ReflectionHelper.findMethod(TileEntityFurnace.class, "canSmelt", "canSmelt");

    {
        canSmelt.setAccessible(true);
    }

    private boolean canSmelt(TileEntityFurnace tile) {
        try {
            return (boolean) canSmelt.invoke(tile);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }
}
