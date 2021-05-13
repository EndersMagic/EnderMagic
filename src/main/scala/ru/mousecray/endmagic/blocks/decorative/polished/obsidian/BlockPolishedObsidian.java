package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.mousecray.endmagic.blocks.base.TranslucentBlock;

public class BlockPolishedObsidian extends TranslucentBlock implements IPolishedObsidian {
    public BlockPolishedObsidian() {
        super(Material.ICE, MapColor.PURPLE, IPolishedObsidian.class);
        setSoundType(SoundType.STONE);
        setHardness(50);
        setResistance(2000);
        setLightOpacity(20);
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type) {
        return true;
    }
}
