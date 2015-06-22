package un.org.charteroftheunitednations;


import android.content.Intent;
import android.widget.LinearLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class DetailActivityTest {

	@Test
	public void shouldDisplayNothingIfNoPostionPassedIn() {
		DetailActivity activity = Robolectric.setupActivity(DetailActivity.class);
		assertEquals(activity.getString(R.string.title_activity_detail),
				activity.getSupportActionBar().getTitle());

		LinearLayout layout = (LinearLayout) activity.findViewById(R.id.chapterDetail);
		assertEquals(0, layout.getChildCount());
	}

	@Test
	public void shouldDisplayContentWhenPositionIsPassedIn() {
		Intent intent = new Intent();
		intent.putExtra(MainActivity.EXTRA_POSITION, 0);
		DetailActivity activity = Robolectric.buildActivity(DetailActivity.class)
				.withIntent(intent).create().get();
		assertEquals(" 介绍性说明", activity.getSupportActionBar().getTitle());

		LinearLayout layout = (LinearLayout) activity.findViewById(R.id.chapterDetail);
		assertTrue(layout.getChildCount() > 0);
	}
}