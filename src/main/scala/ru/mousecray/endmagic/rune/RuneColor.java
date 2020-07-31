package ru.mousecray.endmagic.rune;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;

import java.awt.*;

public enum RuneColor {
    light(new Color(255, 255, 200)),
    fire(new Color(255, 79, 0)),
    water(new Color(50, 100, 255)),
    earth(new Color(200, 170, 0)),
    ender(new Color(27, 123, 107)),
    air(new Color(200, 255, 200), new ResourceLocation(EM.ID, "runes/test")),
    darkness(new Color(10, 10, 10), new ResourceLocation(EM.ID, "runes/test"));


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
