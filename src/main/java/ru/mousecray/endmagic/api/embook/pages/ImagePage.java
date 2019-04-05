package ru.mousecray.endmagic.api.embook.pages;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.Rectangle;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.alignment.Center;
import ru.mousecray.endmagic.api.embook.alignment.Max;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;
import ru.mousecray.endmagic.client.gui.elements.ImageView;
import ru.mousecray.endmagic.client.gui.elements.TextLine;

import java.util.List;

public class ImagePage implements IPage {
    private final ResourceLocation texture;
    private final String label;

    public ImagePage(ResourceLocation texture, String label) {
        this.texture = texture;
        this.label = label;
    }

    @Override
    public List<IStructuralGuiElement> elements() {
        return ImmutableList.of(
                new ImageView(texture, new Rectangle(20, 20, BookApi.pageWidth - 20, BookApi.pageHeight - 20)),
                new TextLine(label, new Center(0), new Max(-0.1f))
        );
    }
}
