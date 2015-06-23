package org.un.charteroftheunitednations;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class MyApplicationTest {

	private MyApplication application;

	@Before
	public void setUp() {
		application = (MyApplication) RuntimeEnvironment.application;
	}

	@Test
	public void testGetChapters() {
		List<ChapterInfo> chapters = MyApplication.getChapters();
		assertNotNull(chapters);
		assertEquals(21, chapters.size());
		assertEquals("·", chapters.get(0).chapterId);
		assertEquals("介绍性说明", chapters.get(0).chapterName);
	}

	@Test
	public void testGetChaptersJSON() throws Exception {
		JSONArray chaptersJSON = MyApplication.getChaptersJSON();
		assertNotNull(chaptersJSON);
		assertEquals(21, chaptersJSON.length());
		assertEquals("·", chaptersJSON.getJSONObject(0).getString("chapterId"));
		assertEquals("介绍性说明", chaptersJSON.getJSONObject(0).getString("chapterName"));
	}
}