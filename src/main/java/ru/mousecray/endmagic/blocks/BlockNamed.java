package ru.mousecray.endmagic.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ru.mousecray.endmagic.util.NameProvider;

public class BlockNamed extends Block implements NameProvider {
    public BlockNamed(String name) {
        super(Material.ROCK);
        this.name = name;
    }

    private String name;

    @Override
    public String name() {
        return name;
    }
}