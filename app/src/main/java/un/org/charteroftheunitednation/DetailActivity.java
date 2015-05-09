package un.org.charteroftheunitednation;

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

	private static final String TAG = "DetailActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		int position = getIntent().getIntExtra("position", -1);
		if (position == -1)
			return;

		LinearLayout layout = (LinearLayout) findViewById(R.id.chapterDetail);
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

		TextView titleTextView = new TextView(this);
		titleTextView.setText(chapterContent.getString("title"));
		titleTextView.setGravity(Gravity.CENTER);
		titleTextView.setLayoutParams(LAYOUT_PARAMS);

		LinearLayout bodyLayout = new LinearLayout(this);
		bodyLayout.setOrientation(LinearLayout.VERTICAL);
		bodyLayout.setLayoutParams(LAYOUT_PARAMS);
		JSONArray body = chapterContent.getJSONArray("body");
		for (int i = 0; i < body.length(); ++i)
			bodyLayout.addView(createRule(body.getJSONObject(i)));

		contentLayout.addView(titleTextView);
		contentLayout.addView(bodyLayout);
		return contentLayout;
	}

	private View createRule(JSONObject body) throws JSONException {
		LinearLayout ruleLayout = new LinearLayout(this);
		ruleLayout.setOrientation(LinearLayout.VERTICAL);
		ruleLayout.setLayoutParams(LAYOUT_PARAMS);

		TextView ruleNameTextView = new TextView(this);
		ruleNameTextView.setText(body.getString("rulesId"));

		LinearLayout paragraphsLayout = new LinearLayout(this);
		paragraphsLayout.setOrientation(LinearLayout.VERTICAL);
		paragraphsLayout.setLayoutParams(LAYOUT_PARAMS);
		JSONArray paragraphs = body.getJSONArray("paragraphs");
		for (int i = 0; i < paragraphs.length(); ++i)
			paragraphsLayout.addView(createParagraph(paragraphs.getJSONObject(i)));


		ruleLayout.addView(ruleNameTextView);
		ruleLayout.addView(paragraphsLayout);
		return ruleLayout;
	}

	private View createParagraph(JSONObject paragraph) throws JSONException {
		LinearLayout paragraphLayout = new LinearLayout(this);
		paragraphLayout.setOrientation(LinearLayout.VERTICAL);
		paragraphLayout.setLayoutParams(LAYOUT_PARAMS);

		TextView paragraphBodyTextView = new TextView(this);
		paragraphBodyTextView.setText(paragraph.getString("paragraphBody"));
		paragraphBodyTextView.setLayoutParams(LAYOUT_PARAMS);

		LinearLayout bulletsLayout = new LinearLayout(this);
		bulletsLayout.setOrientation(LinearLayout.VERTICAL);
		bulletsLayout.setLayoutParams(LAYOUT_PARAMS);
		JSONArray bullets = paragraph.getJSONArray("bullets");
		for (int i = 0; i < bullets.length(); i++)
			bulletsLayout.addView(createBullet(bullets.getString(i)));

		paragraphLayout.addView(paragraphBodyTextView);
		paragraphLayout.addView(bulletsLayout);
		return paragraphLayout;
	}

	private View createBullet(String bullet) {
		TextView bulletTextView = new TextView(this);
		bulletTextView.setText(bullet);
		bulletTextView.setLayoutParams(LAYOUT_PARAMS);

		return bulletTextView;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}
