package un.org.charteroftheunitednation;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DetailActivity extends AppCompatActivity {
	public static final ViewGroup.LayoutParams LAYOUT_PARAMS = new ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	private static final ViewGroup.LayoutParams LINE_PARAMS = new ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT, 1);
	private static final String TAG = "DetailActivity";
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		position = getIntent().getIntExtra("position", -1);
		if (position == -1)
			return;

		updateUI();
	}

	private void updateUI() {
		View prevButton = findViewById(R.id.prevButton);
		View nextButton = findViewById(R.id.nextButton);
		if (position == 0) {
			prevButton.setAlpha(.6f);
			prevButton.setEnabled(false);
		} else if (position == MyApplication.sChapters.length() - 1) {
			nextButton.setAlpha(.6f);
			nextButton.setEnabled(false);
		} else {
			prevButton.setEnabled(true);
			nextButton.setEnabled(true);
			prevButton.setAlpha(1);
			nextButton.setAlpha(1);
		}

		LinearLayout layout = (LinearLayout) findViewById(R.id.chapterDetail);
		layout.removeAllViews();
		try {
			JSONObject chapter = MyApplication.sChapters.getJSONObject(position);

			String chapterId = chapter.getString("chapterTitle");
			String chapterName = chapter.getString("chapterName");
			getSupportActionBar().setTitle(chapterId + " " + chapterName);
			JSONArray chapterCotents = chapter.getJSONArray("chapterContents");
			for (int i = 0; i < chapterCotents.length(); ++i)
				layout.addView(createChapterContent(chapterCotents.getJSONObject(i)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private View createChapterContent(JSONObject chapterContent) throws
			JSONException {
		LinearLayout contentLayout = new LinearLayout(this);
		contentLayout.setOrientation(LinearLayout.VERTICAL);
		contentLayout.setLayoutParams(LAYOUT_PARAMS);

		if (!chapterContent.getString("title").equals("")) {
			TextView titleTextView = new TextView(this);
			titleTextView.setPadding(0, 30, 0, 0);
			titleTextView.setText(chapterContent.getString("title"));
			titleTextView.setGravity(Gravity.CENTER);
			titleTextView.setLayoutParams(LAYOUT_PARAMS);
			titleTextView.setTextSize(22f);
			titleTextView.setTypeface(null, Typeface.BOLD);
			titleTextView.setTextColor(0xffa9abac);

			contentLayout.addView(titleTextView);
		}
		LinearLayout bodyLayout = new LinearLayout(this);
		bodyLayout.setOrientation(LinearLayout.VERTICAL);
		bodyLayout.setLayoutParams(LAYOUT_PARAMS);
		JSONArray body = chapterContent.getJSONArray("body");
		for (int i = 0; i < body.length(); ++i)
			bodyLayout.addView(createRule(body.getJSONObject(i)));

		contentLayout.addView(bodyLayout);
		return contentLayout;
	}

	private View createRule(JSONObject body) throws JSONException {
		LinearLayout ruleLayout = new LinearLayout(this);
		ruleLayout.setOrientation(LinearLayout.VERTICAL);
		ruleLayout.setLayoutParams(LAYOUT_PARAMS);

		if (!body.getString("rulesId").equals("")) {
			TextView ruleNameTextView = new TextView(this);
			ruleNameTextView.setText(body.getString("rulesId"));
			ruleNameTextView.setPadding(0, 20, 0, 0);
			ruleNameTextView.setTextSize(18f);
			ruleNameTextView.setTypeface(null, Typeface.BOLD);
			ruleNameTextView.setTextColor(0xffa9abac);

//			LinearLayout blackLine = new LinearLayout(this);
//			ruleLayout.setLayoutParams(LAYOUT_PARAMS);
//			ruleLayout.getLayoutParams().height = 1;
//			ruleLayout.getLayoutParams().width = 1;
//			ruleLayout.setBackgroundColor(0xff1d1e20);
//
//			ImageView whiteLine = new ImageView(this);
//			ruleLayout.setLayoutParams(LINE_PARAMS);
//			ruleLayout.setBackgroundColor(0xff505155);

			ruleLayout.addView(ruleNameTextView);

//			ruleLayout.addView(blackLine);
//			ruleLayout.addView(whiteLine);
		}
		LinearLayout paragraphsLayout = new LinearLayout(this);
		paragraphsLayout.setOrientation(LinearLayout.VERTICAL);
		paragraphsLayout.setLayoutParams(LAYOUT_PARAMS);
		JSONArray paragraphs = body.getJSONArray("paragraphs");
		for (int i = 0; i < paragraphs.length(); ++i)
			paragraphsLayout.addView(createParagraph(paragraphs.getJSONObject(i)));


		ruleLayout.addView(paragraphsLayout);
		return ruleLayout;
	}

	private View createParagraph(JSONObject paragraph) throws JSONException {
		LinearLayout paragraphLayout = new LinearLayout(this);
		paragraphLayout.setOrientation(LinearLayout.VERTICAL);
		paragraphLayout.setLayoutParams(LAYOUT_PARAMS);

		if (!paragraph.getString("paragraphBody").equals("")) {
			TextView paragraphBodyTextView = new TextView(this);
			paragraphBodyTextView.setText(paragraph.getString("paragraphBody"));
			paragraphBodyTextView.setLayoutParams(LAYOUT_PARAMS);

			paragraphBodyTextView.setPadding(12, 3, 12, 3);
			paragraphBodyTextView.setTextSize(18f);
			paragraphBodyTextView.setTextColor(0xffa9abac);

			paragraphLayout.addView(paragraphBodyTextView);

		}
		LinearLayout bulletsLayout = new LinearLayout(this);
		bulletsLayout.setOrientation(LinearLayout.VERTICAL);
		bulletsLayout.setLayoutParams(LAYOUT_PARAMS);
		JSONArray bullets = paragraph.getJSONArray("bullets");
		for (int i = 0; i < bullets.length(); i++)
			bulletsLayout.addView(createBullet(bullets.getString(i)));

		paragraphLayout.addView(bulletsLayout);
		return paragraphLayout;
	}

	private View createBullet(String bullet) {
		TextView bulletTextView = new TextView(this);
		bulletTextView.setText(bullet);
		bulletTextView.setLayoutParams(LAYOUT_PARAMS);

		bulletTextView.setPadding(16, 0, 16, 0);
		bulletTextView.setTextSize(18f);
		bulletTextView.setTextColor(0xffa9abac);

		return bulletTextView;
	}

	public void goToPreviousChapter(View view) {
		if (position > 0) {
			--position;
			updateUI();
		}
	}

	public void goToNextChapter(View view) {
		if (position < MyApplication.sChapters.length() - 1) {
			++position;
			updateUI();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				super.onBackPressed();
				supportFinishAfterTransition();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
