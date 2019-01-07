package ru.mousecray.endmagic.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.mousecray.endmagic.init.EMItems;

import java.util.Random;

public class EnderOre extends BlockNamed {

    public EnderOre(String name) {
        super(name);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return EMItems.rawEnderit;
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        return MathHelper.getInt(rand, 0, 2);
    }
}
