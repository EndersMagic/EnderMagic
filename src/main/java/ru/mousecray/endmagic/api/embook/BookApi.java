package ru.mousecray.endmagic.api.embook;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.api.embook.components.BookChapter;

@SideOnly(Side.CLIENT)
public class BookApi {
	
	private static List<BookChapter> book = new ArrayList();
	private static int idCount = 1;
	
	public static void addBookChapter(BookChapter chapter) {
//		String id = String.valueOf(idCount);
//		chapter.setKey(id);
//		book.put(id, chapter);
//		idCount++;
	}
	
	public static void addBookChapters(BookChapter... chapters) {
		for(BookChapter chapter : chapters) {
//			String id = String.valueOf(idCount);
//			chapter.setKey(id);
//			book.put(id, chapter);
//			idCount++;
		}
	}
	
	public static List<BookChapter> getBookContent() {
		return book;
	}
}