package un.org.charteroftheunitednations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
	public static final String EXTRA_POSITION = "position";

	private static final String TAG = MainActivity.class.getSimpleName();
	boolean isBrightModel;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		list = (ListView) findViewById(android.R.id.list);
		View header = getLayoutInflater().inflate(R.layout.list_header, null, false);

		list.addHeaderView(header);

		final List<ChapterInfo> chapterInfos = MyApplication.getChapters();

		pref = getApplicationContext().getSharedPreferences(IS_BRIGHT_MODE, MODE_PRIVATE);
		editor = pref.edit();
		isBrightModel = pref.getBoolean("isBrightModel", false); // getting isBringhtModel

		initialList(chapterInfos, isBrightModel);
		setHeaderPic(isBrightModel);

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0) {
				} else if (position != 0) {
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
				editor.putBoolean("isBrightModel", isBrightModel).commit(); //Storing isBrightModel
				setHeaderPic(isBrightModel);
				initialList(chapterInfos, isBrightModel);
			}
		});
	}

	private void setHeaderPic(boolean isBrightModel) {
		ImageView un_header = (ImageView) findViewById(R.id.un_header);
		if (isBrightModel) {
			un_header.setImageResource(R.drawable.un_header_bright_model);
		} else {
			un_header.setImageResource(R.drawable.un_header_not_bright_model);
		}
	}

	private void initialList(List<ChapterInfo> chapterInfos, boolean isBrightModel) {
		ChapterAdapter adapter = new ChapterAdapter(chapterInfos, isBrightModel);
		list.setAdapter(adapter);
	}

	private class ChapterAdapter extends ArrayAdapter<ChapterInfo> {

		int textColor;
		int backgroundColor;
		int lineUpColor;
		int lineDownColor;

		public ChapterAdapter(List<ChapterInfo> chapterInfoList, boolean isBrightModel) {
			super(MainActivity.this, android.R.layout.simple_list_item_activated_1,
					chapterInfoList);
			if (isBrightModel) {
				this.textColor = 0xff363636;       //dark grey
				this.backgroundColor = 0xffcececf; // white
				this.lineUpColor = 0xff676869;     //grey
				this.lineDownColor = 0x00000000;   //white <- transparent
			} else {
				this.textColor = 0xffc4c4c4;       // white
				this.backgroundColor = 0xff303030; // dark grey
				this.lineUpColor = 0xff1d1e20;     // light grey
				this.lineDownColor = 0xff55565a;   // dark dark grey
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

			FrameLayout contentLayoout = (FrameLayout)
					findViewById(R.id.main);
			contentLayoout.setBackgroundColor(backgroundColor);
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
