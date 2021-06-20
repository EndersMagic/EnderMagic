package ru.mousecray.endmagic.rune.effects;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.rune.RuneEffect;
import ru.mousecray.endmagic.util.EnderBlockTypes;

public class FrozingEffect extends RuneEffect {
    @Override
    public boolean isTickable() {
        return true;
    }

    @Override
    public void onUpdate(World world, BlockPos runePos, EnumFacing side, BlockPos targetPos, double runePower) {
        if (world.rand.nextInt(Math.max(1, (int) (200 / runePower))) == 0) {
            Block targetBlock = world.getBlockState(targetPos).getBlock();
            if (targetBlock == Blocks.WATER)
                world.setBlockState(targetPos, Blocks.ICE.getDefaultState());

            if (targetBlock == Blocks.FLOWING_WATER)
                world.setBlockState(targetPos, Blocks.SNOW.getDefaultState());

            else if (targetBlock == Blocks.ICE && runePower > 1)
                world.setBlockState(targetPos, Blocks.PACKED_ICE.getDefaultState());

            else if (targetBlock == Blocks.LAVA && runePower > 5)
                world.setBlockState(targetPos, Blocks.OBSIDIAN.getDefaultState());

            else if (targetBlock == Blocks.FLOWING_LAVA && runePower > 5)
                world.setBlockState(targetPos, (isNether(world) ? Blocks.NETHERRACK : Blocks.COBBLESTONE).getDefaultState());

            else if (targetBlock == Blocks.END_STONE && runePower > 1)
                world.setBlockState(targetPos, EMBlocks.ENDER_GROUND_STONE.getDefaultState().withProperty(EnderBlockTypes.GROUND_TYPE, EnderBlockTypes.EnderGroundType.FROZEN));
        }
        world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(targetPos).grow(Math.min(runePower, 100)))
                .forEach(e -> e.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS)));
    }

    private boolean isNether(World world) {
        return world.provider.getDimensionType() == DimensionType.NETHER;
    }
}
