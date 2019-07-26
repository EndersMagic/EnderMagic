package ru.mousecray.endmagic.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.entity.EntityCurseBush;

public class BlockCurseBush extends BlockBush {
	
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	public BlockCurseBush() {
		setDefaultState(blockState.getBaseState().withProperty(ACTIVE, Boolean.valueOf(false)));
		setHardness(0.0F);
		setSoundType(SoundType.PLANT);
	}
	
	@Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(ACTIVE, Boolean.valueOf((meta & 1) > 0));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((Boolean)state.getValue(ACTIVE)).booleanValue() ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {ACTIVE});
    }

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
		if(!world.isRemote) {
			switch (getPlayer(world, pos)) {
			case 1:	
				summonBush(world, pos);
				break;
			case 2:	
				if(!existEffect(world, pos, 2)) {
					if(state == getDefaultState().withProperty(ACTIVE, true)) {
						world.setBlockState(pos, getDefaultState().withProperty(ACTIVE, false));
					}
					else if(rand.nextInt(100) < 25) {
						world.spawnEntity(getAreaEffect(world, pos));
					}
				}
				break;
			}
		}
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if(!world.isRemote && entity instanceof EntityLivingBase) {
			world.setBlockToAir(pos);
			EntityCurseBush bush = new EntityCurseBush(world, pos);
			world.spawnEntity(bush);
		}
	}
	
	private void summonBush(World world, BlockPos pos) {
		world.setBlockToAir(pos);
		EntityCurseBush bush = new EntityCurseBush(world, pos);
		world.spawnEntity(bush);
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
		for(Entity entity : list) //EM: Don't indent below
			if(entity instanceof EntityAreaEffectCloud) return true;
		return false;
	}
	
	private int getPlayer(World world, BlockPos pos) {
		List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)).grow(3D));
		for(Entity entity : list) {
			if(entity instanceof EntityPlayer) {
				if(entity.getDistanceSq(pos) < 1.5D) return 1;
				else if(entity.getDistanceSq(pos) < 2.0D) {
					if(entity.isSneaking()) return 2;
					else return 1;
				}
				else {
					if(entity.isSneaking()) return 0;
					else return 2;
				}
			}
		}
		return 0;
	}
	
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.AIR;
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock().isReplaceable(world, pos) && world.getBlockState(pos.down()).getBlock() == Blocks.END_STONE;
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        return world.getBlockState(pos.down()).getBlock() == Blocks.END_STONE;
    }
}