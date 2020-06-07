package ru.mousecray.endmagic.gameobj.blocks.trees;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.gameobj.blocks.utils.AutoMetadataBlock;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static ru.mousecray.endmagic.util.EnderBlockTypes.treeType;

public class EMLeaves extends AutoMetadataBlock implements IShearable {

    public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
    public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");

    public EMLeaves() {
        super(Material.LEAVES);
        setHardness(0.2F);
        setResistance(0.4F);
        setLightOpacity(1);
        setSoundType(SoundType.PLANT);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int r = super.getMetaFromState(state);
        if (!state.getValue(DECAYABLE)) r |= 4;
        if (state.getValue(CHECK_DECAY)) r |= 8;
        return r;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState r = super.getStateFromMeta(meta);
        return r.withProperty(CHECK_DECAY, (meta & 4) == 0).withProperty(DECAYABLE, (meta & 8) > 0);
    }

    @Override
    public List<IProperty<?>> properties() {
        return ImmutableList.of(treeType);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(EMBlocks.enderSapling);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        int chance = 40;

        if (fortune > 0) {
            chance += 2 << fortune;
            if (chance > 100) chance = 100;
        }

        if (rand.nextInt(100) < chance) {
            ItemStack drop = new ItemStack(getItemDropped(state, rand, fortune), quantityDropped(rand), damageDropped(state));
            if (!drop.isEmpty())
                drops.add(drop);
        }
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos) {
//        return item.getItem().getRegistryName().getResourcePath().contains("shears");
        return true;
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return false;
    }

    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

//    @Override
//    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
//        if (!worldIn.isRemote) {
//            if (state.getValue(CHECK_DECAY) && state.getValue(DECAYABLE)) {
//                int i = 4;
//                int j = 5;
//                int k = pos.getX();
//                int l = pos.getY();
//                int i1 = pos.getZ();
//                int j1 = 32;
//                int k1 = 1024;
//                int l1 = 16;
//
//                if (this.surroundings == null) {
//                    this.surroundings = new int[32768];
//                }
//
//                if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent decaying leaves from updating neighbors and loading unloaded chunks
//                if (worldIn.isAreaLoaded(pos,
//                        6)) // Forge: extend range from 5 to 6 to account for neighbor checks in world.markAndNotifyBlock -> world.updateObservingBlocksAt
//                {
//                    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
//
//                    for (int i2 = -4; i2 <= 4; ++i2) {
//                        for (int j2 = -4; j2 <= 4; ++j2) {
//                            for (int k2 = -4; k2 <= 4; ++k2) {
//                                IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2));
//                                Block block = iblockstate.getBlock();
//
//                                if (!block.canSustainLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2))) {
//                                    if (block.isLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2))) {
//                                        this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -2;
//                                    } else {
//                                        this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -1;
//                                    }
//                                } else {
//                                    this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = 0;
//                                }
//                            }
//                        }
//                    }
//
//                    for (int i3 = 1; i3 <= 4; ++i3) {
//                        for (int j3 = -4; j3 <= 4; ++j3) {
//                            for (int k3 = -4; k3 <= 4; ++k3) {
//                                for (int l3 = -4; l3 <= 4; ++l3) {
//                                    if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16] == i3 - 1) {
//                                        if (this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2) {
//                                            this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
//                                        }
//
//                                        if (this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2) {
//                                            this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
//                                        }
//
//                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] == -2) {
//                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] = i3;
//                                        }
//
//                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] == -2) {
//                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] = i3;
//                                        }
//
//                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] == -2) {
//                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] = i3;
//                                        }
//
//                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] == -2) {
//                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] = i3;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                int l2 = this.surroundings[16912];
//
//                if (l2 >= 0) {
//                    worldIn.setBlockState(pos, state.withProperty(CHECK_DECAY, Boolean.valueOf(false)), 4);
//                } else {
//                    destroy(worldIn, pos);
//                }
//            }
//        }
//    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        int k = pos.getX();
        int l = pos.getY();
        int i1 = pos.getZ();

        if (worldIn.isAreaLoaded(new BlockPos(k - 2, l - 2, i1 - 2), new BlockPos(k + 2, l + 2, i1 + 2))) {
            for (int j1 = -1; j1 <= 1; ++j1) {
                for (int k1 = -1; k1 <= 1; ++k1) {
                    for (int l1 = -1; l1 <= 1; ++l1) {
                        BlockPos blockpos = pos.add(j1, k1, l1);
                        IBlockState iblockstate = worldIn.getBlockState(blockpos);

                        if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos)) {
                            iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (!world.isRemote && stack.getItem() instanceof ItemShears) player.addStat(StatList.getBlockStats(this));
        else super.harvestBlock(world, player, pos, state, te, stack);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return !Minecraft.getMinecraft().gameSettings.fancyGraphics;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return Minecraft.getMinecraft().gameSettings.fancyGraphics ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return Blocks.LEAVES.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return ImmutableList.of(new ItemStack(
                Item.getItemFromBlock(this), 1,
                damageDropped(world.getBlockState(pos))));
    }

    @Override
    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
        if (!state.getValue(CHECK_DECAY)) world.setBlockState(pos, state.withProperty(CHECK_DECAY, true), 4);
    }

    private void destroy(World worldIn, BlockPos pos) {
        dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
        worldIn.setBlockToAir(pos);
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (world instanceof World && ((World) world).provider instanceof WorldProviderEnd) {
            World worldIn = (World) world;
            WorldProviderEnd worldproviderend = (WorldProviderEnd) worldIn.provider;
            DragonFightManager dragonfightmanager = worldproviderend.getDragonFightManager();
            assert dragonfightmanager != null;
            return super.canSustainLeaves(state, world, pos) || (dragonfightmanager.dragonKilled &&
                    state.getValue(treeType) == EnderBlockTypes.EnderTreeType.DRAGON);
        } else {
            return super.canSustainLeaves(state, world, pos);
        }
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        Blocks.LEAVES.randomDisplayTick(stateIn, worldIn, pos, rand);
    }
}
