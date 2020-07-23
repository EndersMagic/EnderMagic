package ru.mousecray.endmagic.blocks.base;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.util.EMItemBlock;
import ru.mousecray.endmagic.util.registry.ITechnicalBlock;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.google.common.collect.ImmutableMap.toImmutableMap;

/**
 * Universal implementation of meta<->state convertation functions
 */
public abstract class AutoMetaBlock extends Block implements ITechnicalBlock {
    public AutoMetaBlock(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        init();
    }

    public AutoMetaBlock(Material materialIn) {
        super(materialIn);
        init();
    }

    /**
     * State properties of block
     */
    public abstract List<IProperty> properties();

    void init() {
        List<IProperty> properties = properties();
        if (countOfStates(properties) > 16)
            throw new IllegalArgumentException("To many variants of block states");

        stateByMeta = blockState.getValidStates();
        metaByState = IntStream.range(0, stateByMeta.size()).boxed().collect(toImmutableMap(i -> stateByMeta.get(i).getProperties(), Function.identity()));
    }

    private static int countOfStates(List<IProperty> properties) {
        return properties.stream().map(IProperty::getAllowedValues).map(Collection::size).reduce(1, (a, b) -> a * b);
    }


    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, properties().toArray(new IProperty[0]));
    }

    private Map<ImmutableMap<IProperty<?>, Comparable<?>>, Integer> metaByState;
    private List<IBlockState> stateByMeta;

    @Override
    public int getMetaFromState(IBlockState state) {
        return metaByState.getOrDefault(state.getProperties(), 0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return meta >= 0 && meta < stateByMeta.size() ? stateByMeta.get(meta) : getDefaultState();
    }

    @Override
    public ItemBlock getCustomItemBlock(Block block) {
        return new EMItemBlock(block);
    }

    @Nullable
    @Override
    public CreativeTabs getCustomCreativeTab() {
        return EM.EM_CREATIVE;
    }


    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
