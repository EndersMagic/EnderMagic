package ru.mousecray.endmagic.blocks.trees;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.NameProvider;

public class Default extends Block implements NameProvider {

    private final String name;

    public Default(Material material, String name) {
        this(material, name, 1.5F, 30F);
    }

    public Default(Material material, String name, float hardness, float resistance) {
        this(material, name, hardness, resistance, 0F);
    }

    public Default(Material material, String name, float hardness, float resistance, float light) {
        this(material, name, hardness, resistance, light, false);
    }

    public Default(Material material, String name, float hardness, float resistance, boolean tick) {
        this(material, name, hardness, resistance, 0F, tick);
    }

    public Default(Material material, String name, String tool, int num) {
        this(material, name, 1.5F, 30F, 0F, false);
        setHarvestLevel(tool, num);
    }

    public Default(Material material, String name, float hardness, float resistance, float light, boolean tick) {
        super(material);
        this.name = name;
        setTickRandomly(tick);
        setCreativeTab(EM.EM_CREATIVE);
        setHardness(hardness);
        setResistance(resistance);
        setLightLevel(light);
    }

    @Override
    public String name() {
        return name;
    }
}