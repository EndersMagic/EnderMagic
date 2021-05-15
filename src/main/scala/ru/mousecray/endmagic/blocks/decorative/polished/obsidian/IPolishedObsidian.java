package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import net.minecraft.block.state.IBlockState;

import java.util.function.Function;

public interface IPolishedObsidian {

    RenderSideParts2 getObsidianParts(IBlockState state);



    default <A extends RenderSideParts2.Invertable> A getActualVisibility(IBlockState state, Function<RenderSideParts2, A> field, A ifNotObsidian) {
        if (state.getBlock() instanceof IPolishedObsidian) {
            RenderSideParts2 obsidianParts = ((IPolishedObsidian) state.getBlock()).getObsidianParts(state);
            A existedObsidianPart = field.apply(obsidianParts);
            return (A) existedObsidianPart.invert();
        } else
            return ifNotObsidian;
    }
}
