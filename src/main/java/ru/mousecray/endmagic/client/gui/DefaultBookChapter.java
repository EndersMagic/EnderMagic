package ru.mousecray.endmagic.client.gui;

import java.util.List;

import net.minecraft.client.resources.I18n;
import ru.mousecray.endmagic.api.embook.BookChapter;
import ru.mousecray.endmagic.api.embook.IChapterComponent;

public class DefaultBookChapter extends BookChapter {

	public DefaultBookChapter(int key) {
		super(key);
	}

	@Override
	public String getChapterTitle() {
		return I18n.format("embook.chapter_title." + String.valueOf(getKey()));
	}

	@Override
	public List<IChapterComponent> getChapterComponents() {
		return null;
	}

}
