package un.org.charteroftheunitednation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChapterInfoTest {

	@Test
	public void shouldCreateObjectWithCorrectInfo() {
		String id = "One";
		String name = "Hello World";

		ChapterInfo info = new ChapterInfo(id, name);
		assertEquals(id, info.chapterId);
		assertEquals(name, info.chapterName);
	}
}