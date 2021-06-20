package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import net.minecraft.block.state.IBlockState;
import ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSidePartsHolder.RenderSideParts;

public interface IPolishedObsidian {

    RenderSideParts getObsidianParts(IBlockState state);

}
