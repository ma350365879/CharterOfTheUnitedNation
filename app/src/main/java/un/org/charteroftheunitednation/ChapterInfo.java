package un.org.charteroftheunitednation;

import java.lang.reflect.Array;

/**
 * Created by yijunma on 5/9/15.
 */
public class ChapterInfo {
	public String chapterName = "";
	public String chapterId = "";

	public ChapterInfo(String[] chapterInfo){
		chapterName = chapterInfo[0];
		chapterId = chapterInfo[1];
	}
}
