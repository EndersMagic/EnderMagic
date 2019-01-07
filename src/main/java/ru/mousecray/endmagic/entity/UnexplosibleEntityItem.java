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

    public UnexplosibleEntityItem(World world, int x, int y, int z, ItemStack itemStack) {
        super(world, x, y, z, itemStack);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (world.isRemote || isDead) return false; //Forge: Fixes MC-53850
        if (isEntityInvulnerable(source)) {
            return false;
        } else if (source.isExplosion()) {
            return false;
        } else {
            markVelocityChanged();
            health = (int) ((float) health - amount);

            if (health <= 0) {
                setDead();
            }

            return false;
        }
    }
}
