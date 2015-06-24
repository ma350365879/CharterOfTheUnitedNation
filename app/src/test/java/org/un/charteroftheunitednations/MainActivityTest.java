package org.un.charteroftheunitednations;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowListView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class MainActivityTest {

	private ShadowActivity shadowActivity;
	private ListView list;
	private ShadowListView shadowList;

	@Before
	public void setUp() {
		shadowActivity = Shadows.shadowOf(Robolectric.setupActivity(MainActivity.class));
		list = (ListView) shadowActivity.findViewById(android.R.id.list);
		shadowList = Shadows.shadowOf(list);

	}

	@Test
	public void shouldDisplayChapterInfoOnListItem() {
		assertEquals(1, list.getHeaderViewsCount());
		assertEquals(22, list.getCount());
		ChapterInfo info = (ChapterInfo) list.getAdapter().getItem(1);
		assertEquals("·", info.chapterId);
		assertEquals("介绍性说明", info.chapterName);
	}

	@Test
	public void shouldNotLaunchDetailActivityWhenHeaderListItemIsClicked() {
		shadowList.performItemClick(0);
		Intent actualIntent = shadowActivity.getNextStartedActivity();
		assertNull(actualIntent);
	}

	@Test
	public void shouldLaunchDetailActivityWhenNonHeaderListItemIsClicked() {
		shadowList.performItemClick(1);
		Intent actualIntent = shadowActivity.getNextStartedActivity();
		assertNotNull(actualIntent);
		assertEquals(DetailActivity.class.getName(), actualIntent.getComponent().getClassName());
		assertEquals(0, actualIntent.getIntExtra(MainActivity.EXTRA_POSITION, -1));
	}

	@Test
	public void shouldChangeSettingIfButtonIsPressed() {
		SharedPreferences prefs = shadowActivity.getSharedPreferences(MainActivity.PREF_KEY,
				Activity.MODE_PRIVATE);
		boolean isBright = prefs.getBoolean(MainActivity.IS_BRIGHT_MODE, false);
		View changeThemeButton = shadowActivity.findViewById(R.id.changeBrightModel);
		changeThemeButton.performClick();
		assertTrue(isBright != prefs.getBoolean(MainActivity.IS_BRIGHT_MODE, false));
		changeThemeButton.performClick();
		assertTrue(isBright == prefs.getBoolean(MainActivity.IS_BRIGHT_MODE, false));
	}
}