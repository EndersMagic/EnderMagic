package ru.mousecray.endmagic.blocks.trees;

import static net.minecraft.block.BlockLog.LOG_AXIS;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;
import ru.mousecray.endmagic.blocks.BlockTypeBase;
import ru.mousecray.endmagic.blocks.VariativeBlock;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.EnderBlockTypes;

public class EMLog<TreeType extends Enum<TreeType> & IStringSerializable & BlockTypeBase> extends VariativeBlock<TreeType> {

    public EMLog(Class<TreeType> type, Function<TreeType, MapColor> mapFunc) {
        super(type, Material.WOOD, "log", mapFunc);

        setHardness(2.5F);
        setResistance(4.0F);
        setSoundType(SoundType.WOOD);
        setTickRandomly(true);
        setHarvestLevel("axe", 2, getDefaultState().withProperty(blockType, byIndex.apply(0)));
        setHarvestLevel("axe", 2, getDefaultState().withProperty(blockType, byIndex.apply(1)));
        setHarvestLevel("axe", 3, getDefaultState().withProperty(blockType, byIndex.apply(2)));
        setHarvestLevel("axe", 1, getDefaultState().withProperty(blockType, byIndex.apply(3)));

        setDefaultState(blockState.getBaseState()
                .withProperty(LOG_AXIS, BlockLog.EnumAxis.Y)
                .withProperty(blockType, byIndex.apply(0)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState r = super.getStateFromMeta(meta);
        int axis = meta >> 2;
        return r.withProperty(LOG_AXIS, BlockLog.EnumAxis.values()[axis]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int r = super.getMetaFromState(state);
        return (state.getValue(LOG_AXIS).ordinal() << 2) + r;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LOG_AXIS);
    }

    @Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getStateFromMeta(meta).withProperty(LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
        return true;
    }


    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (world.provider instanceof WorldProviderEnd)
        {
            WorldProviderEnd worldproviderend = (WorldProviderEnd) world.provider;
            DragonFightManager dragonfightmanager = worldproviderend.getDragonFightManager();
            if(
                    dragonfightmanager.dragonKilled && // if dragon dead
                    ((EMLog) world.getBlockState(pos)).blockType.getName().equals(EnderBlockTypes.EnderTreeType.DRAGON.getName()) && //if dragon tree
                    world.isAirBlock(pos.down())  &&
                    rand.nextInt(20) == 15 && //rand
                        pos.getX() < 300 && pos.getX() > -300 && //is cenreal island
                        pos.getZ() < 300 && pos.getZ() > -300
              )
            {
                EntityFallingBlock entity = new EntityFallingBlock(world, pos.getX(), pos.getY(), pos.getZ(), world.getBlockState(pos));
                entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
                entity.moveToBlockPosAndAngles(pos, 0F, 0F);
                entity.motionX = 0.0D;
                entity.motionZ = 0.0D;
                entity.setHurtEntities(true);
                world.spawnEntity(entity);
            }
        }
    }
}
