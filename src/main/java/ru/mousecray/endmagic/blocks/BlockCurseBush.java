package ru.mousecray.endmagic.blocks;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCurseBush extends BlockBush {
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
		if(!world.isRemote && world.checkNoEntityCollision(BUSH_AABB) && rand.nextInt(100) < 10) {
			EntityAreaEffectCloud entity = new EntityAreaEffectCloud(world);
			entity.addEffect(new PotionEffect(Potion.getPotionById(15)));
			entity.setDuration(10);
			world.spawnEntity(entity);
		}
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if(!world.isRemote && entity instanceof EntityPlayer) {
			//TODO: Spawn entity
		}
	}
}