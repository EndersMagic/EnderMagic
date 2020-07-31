package ru.mousecray.endmagic.rune;

public enum RuneColor {
    white(255, 255, 255),
    green(0, 255, 0)
    ;

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
        return name()+"("+r+", "+g+", "+b+")";
    }
}
