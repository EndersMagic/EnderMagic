package ru.mousecray.endmagic.api.embook.components;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.api.embook.IChapterComponent;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.pages.ImagePage;

import java.util.List;

public class ImageComponent implements IChapterComponent {
    public final ResourceLocation texture;
    public final String label;

    public ImageComponent(ResourceLocation texture, String label) {
        this.texture = texture;
        this.label = label;
    }

    @Override
    public List<IPage> pages() {
        return ImmutableList.of(new ImagePage(texture, label));
    }
}
