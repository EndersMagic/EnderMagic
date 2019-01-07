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
//		if(!book.containsKey(id)) {
		chapter.setKey(id);
		book.put(id, chapter);
		idCount++;
//		}
//		else throw new IllegalArgumentException("Attempt to add two chapters with the same key to the book!");
	}
	
	public static void addBookChapters(BookChapter... chapters) {
		for(BookChapter chapter : chapters) {
			String id = String.valueOf(idCount);
//			if(!book.containsKey(id)) {
			chapter.setKey(id);
			book.put(id, chapter);
			idCount++;
//			}
//			else throw new IllegalArgumentException("Attempt to add two chapters with the same key to the book!");
		}
	}
	
	public static Map<String, BookChapter> getBookContent() {
		return book;
	}
}