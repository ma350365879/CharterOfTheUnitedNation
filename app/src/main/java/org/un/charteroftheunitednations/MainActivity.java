package org.un.charteroftheunitednations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	public static final String IS_BRIGHT_MODE = "KEY_FOR_IS_BRIGHT_MODE";
	public static final String PREF_KEY = MainActivity.class.getPackage().toString();
	public static final String EXTRA_POSITION = "position";

	private static final String TAG = MainActivity.class.getSimpleName();
	boolean isBrightModel;
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		list = (ListView) findViewById(android.R.id.list);
		View header = getLayoutInflater().inflate(R.layout.list_header, null, false);

		list.addHeaderView(header);

		final List<ChapterInfo> chapterInfos = MyApplication.getChapters();

		final SharedPreferences pref = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
		Log.d(TAG, "PREF_KEY: " + PREF_KEY);
		isBrightModel = pref.getBoolean(IS_BRIGHT_MODE, false); // getting isBringhtModel

		initialList(chapterInfos, isBrightModel);
		setHeaderPic(isBrightModel);

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position > 0) {
					Intent i = new Intent(MainActivity.this, DetailActivity.class);
					i.putExtra(EXTRA_POSITION, position - 1);
					startActivity(i);
				}
			}
		});
		LinearLayout changeBrightModel = (LinearLayout) findViewById(R.id.changeBrightModel);
		changeBrightModel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isBrightModel = !isBrightModel;
				pref.edit().putBoolean(IS_BRIGHT_MODE, isBrightModel).apply(); //Storing
				// isBrightModel
				setHeaderPic(isBrightModel);
				initialList(chapterInfos, isBrightModel);
			}
		});
	}

	private void setHeaderPic(boolean isBrightModel) {
		ImageView un_header = (ImageView) findViewById(R.id.un_header);
		if (isBrightModel)
			un_header.setImageResource(R.drawable.un_header_bright_model);
		else
			un_header.setImageResource(R.drawable.un_header_not_bright_model);
	}

	private void initialList(List<ChapterInfo> chapterInfos, boolean isBrightModel) {
		ChapterAdapter adapter = new ChapterAdapter(chapterInfos, isBrightModel);
		list.setAdapter(adapter);
	}

	private class ChapterAdapter extends ArrayAdapter<ChapterInfo> {

		private int textColor;
		private int backgroundColor;
		private int lineUpColor;
		private int lineDownColor;

		public ChapterAdapter(List<ChapterInfo> chapterInfoList, boolean isBrightModel) {
			super(MainActivity.this, android.R.layout.simple_list_item_activated_1,
					chapterInfoList);

			Resources res = getResources();
			if (isBrightModel) {
				textColor = res.getColor(R.color.text_color_mode_bright);
				backgroundColor = res.getColor(R.color.background_color_mode_bright);
				lineUpColor = res.getColor(R.color.line_up_color_mode_bright);
				lineDownColor = res.getColor(R.color.line_down_color_mode_bright);
			} else {
				textColor = res.getColor(R.color.text_color_mode_dark);
				backgroundColor = res.getColor(R.color.background_color_mode_dark);
				lineUpColor = res.getColor(R.color.line_up_color_mode_dark);
				lineDownColor = res.getColor(R.color.line_down_color_mode_dark);
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView =
						getLayoutInflater().inflate(R.layout.list_custom_item, null);
			}

			ChapterInfo c = getItem(position);

			TextView titleTextView = (TextView)
					convertView.findViewById(R.id.chapterId);
			titleTextView.setText(c.chapterId);
			titleTextView.setTextColor(textColor);

			TextView dateTextView = (TextView)
					convertView.findViewById(R.id.chapterName);
			dateTextView.setText(c.chapterName);
			dateTextView.setTextColor(textColor);

			FrameLayout contentLayout = (FrameLayout)
					findViewById(R.id.main);
			contentLayout.setBackgroundColor(backgroundColor);
			LinearLayout lineLayout = (LinearLayout)
					convertView.findViewById(R.id.line_background);
			lineLayout.setBackgroundColor(backgroundColor);
			LinearLayout lineUp = (LinearLayout)
					convertView.findViewById(R.id.line_up);
			lineUp.setBackgroundColor(lineUpColor);
			LinearLayout lineDown = (LinearLayout)
					convertView.findViewById(R.id.line_down);
			lineDown.setBackgroundColor(lineDownColor);

			return convertView;
		}
	}
}
