package un.org.charteroftheunitednation;

public class ChapterInfo {
	public String chapterName = "";
	public String chapterId = "";

	public ChapterInfo(String[] chapterInfo){
		chapterName = chapterInfo[0];
		chapterId = chapterInfo[1];
	}
}
