package ru.mousecray.endmagic.blocks.base;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.google.common.collect.ImmutableMap.toImmutableMap;

public abstract class AutoMetaBlock extends Block {
    public AutoMetaBlock(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        init();
    }

    public AutoMetaBlock(Material materialIn) {
        super(materialIn);
        init();
    }

    public abstract List<IProperty> properties();

    private void init() {
        if (countOfStates(properties()) > 16)
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
}
