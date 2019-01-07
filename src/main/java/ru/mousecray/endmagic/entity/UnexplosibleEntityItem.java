package ru.mousecray.endmagic.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class UnexplosibleEntityItem extends EntityItem {
    public UnexplosibleEntityItem(World worldIn) {
        super(worldIn);
    }

    public UnexplosibleEntityItem(World world, double x, double y, double z, ItemStack itemStack) {
        super(world, x, y, z, itemStack);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        return !source.isExplosion() && super.attackEntityFrom(source, amount);
    }
}
