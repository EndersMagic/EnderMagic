package ru.mousecray.endmagic.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

public class TextureAtlasDynamic extends TextureAtlasSprite {
    public TextureAtlasDynamic(String spriteName, Map<String, Integer> textures) {
        super(spriteName);
        this.textures = textures;
    }

    protected Map<String, Integer> textures;

    @Override
    public void updateAnimation() {
        TextureUtil.uploadTextureMipmap(framesTextureData.get(0), width, height, originX, originY, false, false);
    }

    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
        return true;
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location, Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
        framesTextureData.clear();
        frameCounter = 0;
        tickCounter = 0;

        AtomicInteger min = new AtomicInteger(0);

        TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
        Optional<int[]> reduce = textures.entrySet().stream().flatMap(e -> {
            ResourceLocation resource1 = new ResourceLocation(e.getKey());
            try {
                resource1 = completeResourceLocation(map, resource1, 0);
                BufferedImage buff = ImageIO.read(manager.getResource(resource1).getInputStream());
                min.set(buff.getWidth());
                int[] rawBase = new int[buff.getWidth() * buff.getHeight()];
                buff.getRGB(0, 0, buff.getWidth(), buff.getHeight(), rawBase, 0, buff.getWidth());
                for (int p = 0; p < rawBase.length; p++) {
                    rawBase[p] = colorize(new Color(rawBase[p], true), new Color(e.getValue(), true), false).getRGB();
                }
                return Stream.of(rawBase);
            } catch (IOException exc) {
                //e.printStackTrace();
                System.out.println("Using missing texture, unable to load " + resource1 + "|" + exc);
                return Stream.empty();
            }
        }).reduce((a, b) -> {
            int[] r = new int[a.length];
            int r1, g1, b1;
            int r2, g2, b2;
            float a1, a2, a3;
            for (int p = 0; p < a.length; p++) {
                Color c1 = new Color(a[p], true);
                Color c2 = new Color(b[p], true);

                a1 = c1.getAlpha() / 255f;
                r1 = c1.getRed();
                g1 = c1.getGreen();
                b1 = c1.getBlue();
                a2 = c2.getAlpha() / 255f;
                r2 = c2.getRed();
                g2 = c2.getGreen();
                b2 = c2.getBlue();
                a3 = a2 + a1 * (1 - a2);

                if (a3 > 0) {
                    r1 = Math.round(((r2 * a2) + (r1 * a1 * (1 - a2))) / a3);
                    g1 = Math.round(((g2 * a2) + (g1 * a1 * (1 - a2))) / a3);
                    b1 = Math.round(((b2 * a2) + (b1 * a1 * (1 - a2))) / a3);
                } else {
                    r1 = g1 = b1 = 0;
                }
                Color c3 = new Color(r1, g1, b1, Math.round(a3 * 255));
                r[p] = c3.getRGB();
            }
            return r;
        });
        reduce.ifPresent(rawBase -> {
            int[][] aint = new int[Minecraft.getMinecraft().gameSettings.mipmapLevels + 1][];
            for (int k = 0; k < aint.length; ++k) {
                aint[k] = rawBase;
            }

            framesTextureData.add(aint);
        });
        return false;
    }

    private static ResourceLocation completeResourceLocation(TextureMap map, ResourceLocation loc, int c) {
        try {
            return new ResourceLocation(loc.getResourceDomain(), String.format("%s/%s%s", "textures", loc.getResourcePath(), ".png"));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Color colorize(Color cm, Color c2, boolean invert) {
        float[] pix = new float[3];
        float[] mul = new float[3];
        Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), pix);
        Color.RGBtoHSB(cm.getRed(), cm.getGreen(), cm.getBlue(), mul);
        float v = (invert ? 1 - pix[2] : pix[2]);// + mul[2];
        //v /= 2;
        Color c3 = new Color(Color.HSBtoRGB(mul[0], mul[1], v));
        return new Color(c3.getRed(), c3.getGreen(), c3.getBlue(), c2.getAlpha());
    }
}