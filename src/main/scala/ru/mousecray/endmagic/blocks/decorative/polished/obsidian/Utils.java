package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSideParts2.HorizontalFaceVisibility;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static net.minecraft.block.BlockSlab.HALF;
import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSideParts2.HorizontalFaceVisibility.*;
import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSideParts2.PROPERTY;

public class Utils {

    static RenderSideParts2 getObsidianSlabParts(IBlockState state) {
        switch (state.getValue(HALF)) {
            case TOP:
                return RenderSideParts2.apply(visible_all, invisible_all, invisible_bottom, invisible_bottom, invisible_bottom, invisible_bottom);
            case BOTTOM:
                return RenderSideParts2.apply(invisible_all, visible_all, invisible_top, invisible_top, invisible_top, invisible_top);
            default:
                throw new IllegalArgumentException("Unsupported value of HALF property");
        }
    }

    static IBlockState getActualObsidianSlabState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        Map<EnumFacing, HorizontalFaceVisibility> actualVisibility = getActualVisibilityMap(worldIn, pos);

        switch (state.getValue(HALF)) {
            case TOP:
                actualVisibility.put(EnumFacing.DOWN, visible_all);
                break;

            case BOTTOM:
                actualVisibility.put(EnumFacing.UP, visible_all);
                break;

            default:
                throw new IllegalArgumentException("Unsupported value of HALF property");
        }

        return state.withProperty(PROPERTY, RenderSideParts2.apply(actualVisibility));
    }

    static IBlockState getActualObsidianFullState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(PROPERTY, RenderSideParts2.apply(getActualVisibilityMap(worldIn, pos)));
    }

    private static Map<EnumFacing, HorizontalFaceVisibility> getActualVisibilityMap(IBlockAccess worldIn, BlockPos pos) {
        return Arrays.stream(EnumFacing.values())
                .collect(toMap(Function.identity(),
                        facing -> getActualVisibility(
                                worldIn.getBlockState(pos.offset(facing)),
                                facing.getOpposite()
                        )));
    }

    private static HorizontalFaceVisibility getActualVisibility(IBlockState state, EnumFacing side) {
        if (state.getBlock() instanceof IPolishedObsidian) {
            RenderSideParts2 obsidianParts = ((IPolishedObsidian) state.getBlock()).getObsidianParts(state);
            HorizontalFaceVisibility existedObsidianPart = obsidianParts.get(side);
            return existedObsidianPart.invert();
        } else
            return visible_all;
    }
}
