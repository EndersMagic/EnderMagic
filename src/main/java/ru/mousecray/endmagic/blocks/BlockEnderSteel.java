package ru.mousecray.endmagic.blocks;

import net.minecraft.block.SoundType;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA;

public class BlockEnderSteel extends BlockNamed implements BlockColored {

    private final RGBA color;

    public BlockEnderSteel(String name, RGBA color) {
        super(name);
        this.color = color;
        setHardness(5.0F);
        setResistance(10.0F);
        setSoundType(SoundType.STONE);
    }

    @Override
    public RGBA color() {
        return color;
    }
}
