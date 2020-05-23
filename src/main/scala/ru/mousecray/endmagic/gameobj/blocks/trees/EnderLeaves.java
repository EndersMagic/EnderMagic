package ru.mousecray.endmagic.gameobj.blocks.trees;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.api.metadata.BlockStateGenerator;
import ru.mousecray.endmagic.api.metadata.MetadataBlock;
import ru.mousecray.endmagic.api.metadata.PropertyFeature;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EnderLeaves extends MetadataBlock implements IShearable {

    public static final PropertyBool DECAYABLE = BlockLeaves.DECAYABLE;
    public static final PropertyBool CHECK_DECAY = BlockLeaves.CHECK_DECAY;
    public static final PropertyFeature<EnderBlockTypes.EnderTreeType> TREE_TYPE = EnderBlockTypes.TREE_TYPE;

    public EnderLeaves() {
        super(Material.LEAVES);
        setHardness(0.2F);
        setResistance(0.4F);
        setLightOpacity(1);
        setSoundType(SoundType.PLANT);
        setTickRandomly(true);
        setDefaultState(getDefaultState().withProperty(DECAYABLE, true).withProperty(CHECK_DECAY, false));
    }

    @Override
    protected BlockStateContainer createBlockStateContainer() {
        return BlockStateGenerator.create(this).addProperties(DECAYABLE, CHECK_DECAY).addFeature(TREE_TYPE).buildContainer();
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
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return state.getValue(TREE_TYPE) == EnderBlockTypes.EnderTreeType.PHANTOM ?
                EnumBlockRenderType.ENTITYBLOCK_ANIMATED :
                EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return false;
    }

    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!state.getValue(EnderLeaves.DECAYABLE)) return;

        if (state.getValue(CHECK_DECAY)) {
            boolean isNotLog = findingArea(pos).stream().noneMatch(currPos -> {
                IBlockState currState = world.getBlockState(currPos);
                return currState.getBlock() == EMBlocks.enderLog &&
                        ((EnderLog) currState.getBlock()).canSustainLeaves(world, currState, currPos, pos);
            });
            if (isNotLog) destroy(world, pos);
            else world.setBlockState(pos, state.withProperty(CHECK_DECAY, false));
        }
    }

    private List<BlockPos> findingArea(BlockPos pos) {
        return IntStream.range(-5, 5).mapToObj(x -> IntStream.range(-5, 5)
                .mapToObj(y -> IntStream.range(-5, 5).mapToObj(z -> pos.add(x, y, z)))
                .flatMap(Function.identity()))
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    //TODO: sdjfhdbgh
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        int k = pos.getX();
        int l = pos.getY();
        int i1 = pos.getZ();

        if (worldIn.isAreaLoaded(new BlockPos(k - 2, l - 2, i1 - 2), new BlockPos(k + 2, l + 2, i1 + 2))) for (int j1 = -1; j1 <= 1; ++j1)
            for (int k1 = -1; k1 <= 1; ++k1)
                for (int l1 = -1; l1 <= 1; ++l1) {
                    BlockPos blockpos = pos.add(j1, k1, l1);
                    IBlockState iblockstate = worldIn.getBlockState(blockpos);

                    if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos))
                        iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
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
        if (!state.getValue(CHECK_DECAY)) world.setBlockState(pos, state.withProperty(CHECK_DECAY, true));
    }

    private void destroy(World world, BlockPos pos) {
        dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
        world.setBlockToAir(pos);
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        Blocks.LEAVES.randomDisplayTick(stateIn, worldIn, pos, rand);
    }
}
