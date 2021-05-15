package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.mousecray.endmagic.blocks.base.TranslucentBlock;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.client.render.model.baked.TranslucentPartsModel;
import ru.mousecray.endmagic.util.registry.IExtendedProperties;

import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSideParts2.HorizontalFaceVisibility.visible_all;
import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSideParts2.PROPERTY;
import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.Utils.getActualObsidianFullState;

public class BlockPolishedObsidian extends TranslucentBlock implements IPolishedObsidian, IExtendedProperties {

    public BlockPolishedObsidian() {
        super(Material.ICE, MapColor.PURPLE, IPolishedObsidian.class);
        setSoundType(SoundType.STONE);
        setHardness(50);
        setResistance(2000);
        setLightOpacity(20);
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type) {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PROPERTY);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return getActualObsidianFullState(state, worldIn, pos);
    }

    @Override
    public RenderSideParts2 getObsidianParts(IBlockState state) {
        return RenderSideParts2.allSidesIsFull;
    }

    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        modelRegistration.addBakedModelOverride(getRegistryName(), TranslucentPartsModel::new);
    }
}
