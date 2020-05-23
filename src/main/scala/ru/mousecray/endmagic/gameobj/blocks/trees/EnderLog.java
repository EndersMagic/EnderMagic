package ru.mousecray.endmagic.gameobj.blocks.trees;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;
import ru.mousecray.endmagic.api.metadata.BlockStateGenerator;
import ru.mousecray.endmagic.api.metadata.MetadataBlock;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.minecraft.block.BlockLog.LOG_AXIS;
import static ru.mousecray.endmagic.util.EnderBlockTypes.TREE_TYPE;

public class EnderLog extends MetadataBlock {

    public EnderLog() {
        super(Material.WOOD);
        setHardness(2.5F);
        setResistance(4.0F);
        setSoundType(SoundType.WOOD);
        setHarvestLevel("axe", 2, getDefaultState().withProperty(TREE_TYPE, EnderBlockTypes.EnderTreeType.DRAGON));
        setHarvestLevel("axe", 2, getDefaultState().withProperty(TREE_TYPE, EnderBlockTypes.EnderTreeType.NATURAL));
        setHarvestLevel("axe", 3, getDefaultState().withProperty(TREE_TYPE, EnderBlockTypes.EnderTreeType.IMMORTAL));
        setHarvestLevel("axe", 1, getDefaultState().withProperty(TREE_TYPE, EnderBlockTypes.EnderTreeType.PHANTOM));
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (state.getValue(TREE_TYPE) == EnderBlockTypes.EnderTreeType.DRAGON) if (world.provider instanceof WorldProviderEnd) {
            DragonFightManager dragonfightmanager = ((WorldProviderEnd) world.provider).getDragonFightManager();
            if (
                    dragonfightmanager.dragonKilled && // if dragon dead
                            world.isAirBlock(pos.down()) &&
                            rand.nextInt(20) == 15 && //rand
                            pos.getX() < 300 && pos.getX() > -300 && //is cenreal island
                            pos.getZ() < 300 && pos.getZ() > -300
            ) {
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

    @Override
    protected BlockStateContainer createBlockStateContainer() {
        return BlockStateGenerator.create(this).addProperties(LOG_AXIS).addFeatures(TREE_TYPE).buildContainer();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return state.getValue(TREE_TYPE) == EnderBlockTypes.EnderTreeType.PHANTOM ?
                EnumBlockRenderType.ENTITYBLOCK_ANIMATED :
                EnumBlockRenderType.MODEL;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getStateFromMeta(meta).withProperty(LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    /**
     * EM: Fixed method canSustainLeaves
     *
     * @param world    current world
     * @param logState state current block
     * @param logPos   pos current block
     * @param callPos  pos which called to this method
     * @return true if the presence this block can prevent leaves from decaying.
     */
    public boolean canSustainLeaves(@Nonnull World world, @Nonnull IBlockState logState, @Nonnull BlockPos logPos, @Nonnull BlockPos callPos) {
        IBlockState leavesState = world.getBlockState(callPos);
        return logState.getValue(TREE_TYPE) == leavesState.getValue(TREE_TYPE);
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
        return true;
    }
}
