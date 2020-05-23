package ru.mousecray.endmagic.gameobj.blocks.vanilla;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.mousecray.endmagic.api.blocks.IEndSoil;

public class BlockVanillaEndstone extends Block implements IEndSoil {

    public BlockVanillaEndstone() {
        super(Material.ROCK, MapColor.SAND);
        setHardness(3F);
        setResistance(15F);
        setSoundType(SoundType.STONE);
        setUnlocalizedName("whiteStone");
        setRegistryName("minecraft", "end_stone");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public boolean canUseBonemeal() {
        return true;
    }
}
