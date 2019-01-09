package ru.mousecray.endmagic.api.embook;

import net.minecraft.client.resources.I18n;
import ru.mousecray.endmagic.api.embook.components.BookChapter;
import ru.mousecray.endmagic.api.embook.components.ChapterPage;

public class DefaultBookChapter extends BookChapter {

	@Override
	public String getChapterTitle() {
		return I18n.format("embook.chapter_title." + getKey());
	}
	
	public DefaultBookChapter build(ChapterPage... elements) {
		for(ChapterPage component : elements) {
			pages.add(component);
		}
		return this;
	}
}