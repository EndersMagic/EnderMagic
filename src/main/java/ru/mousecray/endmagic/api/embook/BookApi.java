package ru.mousecray.endmagic.api.embook;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BookApi {
	
	private static Map<String, BookChapter> book = new HashMap();
	
	public static void addBookChapter(BookChapter chapter) {
		String key = chapter.getKey();
		if(!book.containsKey(key)) {
			book.put(key, chapter);
		}
		else {
			throw new IllegalArgumentException("Attempt to add two chapters with the same key to the book!");
		}
	}
	
	public static void addBookChapters(BookChapter... chapters) {
		for(BookChapter chapter : chapters) {
			String key = chapter.getKey();
			if(!book.containsKey(key)) {
				book.put(key, chapter);
			}
			else {
				throw new IllegalArgumentException("Attempt to add two chapters with the same key to the book!");
			}
		}
	}
	
	public static Map<String, BookChapter> getBookContent() {
		return book;
	}
}