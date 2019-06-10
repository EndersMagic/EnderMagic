package ru.mousecray.endmagic.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.entity.EntityCurseBush;

public class BlockCurseBush extends BlockBush {
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
		if(!world.isRemote && rand.nextInt(100) < 25 && !existEffect(world, pos, 2)) {
			world.spawnEntity(getAreaEffect(world, pos));
		}
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if(!world.isRemote && entity instanceof EntityPlayer) {
			if(entity.getHeldEquipment().iterator().next().getItem() instanceof ItemSword) {
				//TODO: matadata
				world.spawnEntity(getAreaEffect(world, pos));
			}
			else {
				world.setBlockToAir(pos);
				EntityCurseBush bush = new EntityCurseBush(world, pos);
				world.spawnEntity(bush);
			}
		}
	}
	
	public EntityAreaEffectCloud getAreaEffect(World world, BlockPos pos) {
		EntityAreaEffectCloud entity = new EntityAreaEffectCloud(world, pos.getX(), pos.getY(), pos.getZ());
		entity.setRadius(3.0F);
        entity.setRadiusOnUse(-0.5F);
        entity.setWaitTime(5);
        entity.setRadiusPerTick(-entity.getRadius() / (float)entity.getDuration());
        entity.setPotion(PotionTypes.EMPTY);
        entity.addEffect(new PotionEffect(MobEffects.POISON, 150, 1));
		entity.addEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 1));
		entity.setDuration(100);
		return entity;
	}
	
	private boolean existEffect(World world, BlockPos pos, int radius) {
		List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)).grow(radius));
		Boolean flag = false;
		for(Entity entity : list) flag = entity instanceof EntityAreaEffectCloud;
		return flag;
	}
}