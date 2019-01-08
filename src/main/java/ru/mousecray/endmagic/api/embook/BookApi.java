package ru.mousecray.endmagic.api.embook;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BookApi {
	
	private static Map<String, BookChapter> book = new HashMap();
	private static int idCount = 1;
	
	public static void addBookChapter(BookChapter chapter) {
		String id = String.valueOf(idCount);
		chapter.setKey(id);
		book.put(id, chapter);
		idCount++;
	}
	
	public static void addBookChapters(BookChapter... chapters) {
		for(BookChapter chapter : chapters) {
			String id = String.valueOf(idCount);
			chapter.setKey(id);
			book.put(id, chapter);
			idCount++;
		}
	}
	
	public static Map<String, BookChapter> getBookContent() {
		return book;
	}
}