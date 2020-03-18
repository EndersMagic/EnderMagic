package ru.mousecray.endmagic.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import ru.mousecray.endmagic.util.registry.NameProvider;

public class BlockNamed extends Block implements NameProvider {
    private String name;

    public BlockNamed(String name) {
        super(Material.ROCK);
        this.name = name;
    }

    public BlockNamed(Material material, MapColor mapColor, String name) {
        super(material, mapColor);
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}