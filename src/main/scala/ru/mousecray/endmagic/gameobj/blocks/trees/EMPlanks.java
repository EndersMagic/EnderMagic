package ru.mousecray.endmagic.gameobj.blocks.trees;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import ru.mousecray.endmagic.gameobj.blocks.utils.AutoMetadataBlock;
import ru.mousecray.endmagic.gameobj.blocks.utils.PropertyFeature;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import java.util.List;

import static ru.mousecray.endmagic.util.EnderBlockTypes.treeType;

public class EMPlanks extends AutoMetadataBlock {

    public EMPlanks() {
        super(Material.WOOD);
        setHardness(2.5F);
        setResistance(7.0F);
        setSoundType(SoundType.WOOD);
    }

    @Override
    public List<IProperty<?>> properties() {
        return ImmutableList.of(treeType);
    }
}
