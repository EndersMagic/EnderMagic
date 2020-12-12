package ru.mousecray.endmagic.rune;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;

import java.awt.*;

public enum RuneColor {
    Fire(new Color(255, 79, 0)),
    Earth(new Color(153, 191, 0)),
    Cold(new Color(0, 230, 255)),
    Air(new Color(200, 255, 200)),
    Void(new Color(27, 123, 107)),
    Darkness(new Color(10, 10, 10)),
    Time(new Color(220, 198, 255));

    public final int r;
    public final int g;
    public final int b;
    public final ResourceLocation texture;

    private TextureAtlasSprite atlasSpriteRune;

    public TextureAtlasSprite atlasSpriteRune() {
        if (atlasSpriteRune == null)
            atlasSpriteRune = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
        return atlasSpriteRune;
    }

    RuneColor(Color color) {
        this(color, new ResourceLocation(EM.ID, "runes/rune"));
    }

    RuneColor(Color color, ResourceLocation texture) {
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
        this.texture = texture;
    }

    @Override
    public String toString() {
        return name() + "(" + r + ", " + g + ", " + b + ")";
    }
}
