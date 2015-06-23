package un.org.charteroftheunitednations;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
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

	private static final String TAG = DetailActivity.class.getSimpleName();
	private int position;
	private int textColor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		position = getIntent().getIntExtra(MainActivity.EXTRA_POSITION, -1);
		if (position == -1)
			return;

		SharedPreferences pref = getSharedPreferences(MainActivity.PREF_KEY, MODE_PRIVATE);
		boolean isBrightModel = pref.getBoolean(MainActivity.IS_BRIGHT_MODE, false);

		Resources res = getResources();
		int backgroundColor;
		if (isBrightModel) {
			textColor = res.getColor(R.color.text_color_mode_bright);
			backgroundColor = res.getColor(R.color.background_color_mode_bright);
		} else {
			textColor = res.getColor(R.color.text_color_mode_dark);
			backgroundColor = res.getColor(R.color.background_color_mode_dark);
		}

		LinearLayout detailActivityContainer = (LinearLayout)
				findViewById(R.id.activity_detail_container);
		detailActivityContainer.setBackgroundColor(backgroundColor);

		updateUI();
	}

	private void updateUI() {
		View prevButton = findViewById(R.id.prevButton);
		View nextButton = findViewById(R.id.nextButton);
		if (position == 0) {
			prevButton.setAlpha(.6f);
			prevButton.setEnabled(false);
		} else if (position == MyApplication.getChaptersJSON().length() - 1) {
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
			JSONObject chapter = MyApplication.getChaptersJSON().getJSONObject(position);

			String chapterNumber = chapter.getString("chapterTitle");
			String chapterName = chapter.getString("chapterName");
			getSupportActionBar().setTitle(chapterNumber + " " + chapterName);
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
			titleTextView.setTextColor(textColor);

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
			ruleNameTextView.setPadding(0, 15, 0, 5);
			ruleNameTextView.setTextSize(20f);
			ruleNameTextView.setTypeface(null, Typeface.BOLD);
			ruleNameTextView.setTextColor(textColor);

			ruleLayout.addView(ruleNameTextView);
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
			JustifyTextView paragraphBodyJustifyTextView = new JustifyTextView(this, null);
			paragraphBodyJustifyTextView.setText(paragraph.getString("paragraphBody"));
			paragraphBodyJustifyTextView.setLayoutParams(LAYOUT_PARAMS);

			paragraphBodyJustifyTextView.setPadding(12, 3, 12, 3);
			paragraphBodyJustifyTextView.setTextSize(18f);
			paragraphBodyJustifyTextView.setTextColor(textColor);

			paragraphLayout.addView(paragraphBodyJustifyTextView);
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
		JustifyTextView bulletJustifyTextView = new JustifyTextView(this, null);
		bulletJustifyTextView.setText(bullet);
		bulletJustifyTextView.setLayoutParams(LAYOUT_PARAMS);

		bulletJustifyTextView.setPadding(16, 0, 16, 0);
		bulletJustifyTextView.setTextSize(18f);
		bulletJustifyTextView.setTextColor(textColor);

		return bulletJustifyTextView;
	}

	public void goToPreviousChapter(View view) {
		if (position > 0) {
			--position;
			updateUI();
		}
	}

	public void goToNextChapter(View view) {
		if (position < MyApplication.getChaptersJSON().length() - 1) {
			++position;
			updateUI();
		}
	}
}
