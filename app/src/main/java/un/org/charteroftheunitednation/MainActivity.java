package un.org.charteroftheunitednation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	public static final String EXTRA_POSITION = "position";
	private static final String TAG = "MainActivity";

	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		list = (ListView) findViewById(android.R.id.list);

		View header = getLayoutInflater().inflate(R.layout.list_header, list, false);

		list.addHeaderView(header);

		List<ChapterInfo> chapterInfos = MyApplication.getChapters();

		ChapterAdapter adapter = new ChapterAdapter(chapterInfos);

		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position != 0) {
					Intent i = new Intent(MainActivity.this, DetailActivity.class);
					i.putExtra(EXTRA_POSITION, position - 1);
					startActivity(i);
				}
			}
		});

	}

	private class ChapterAdapter extends ArrayAdapter<ChapterInfo> {

		public ChapterAdapter(List<ChapterInfo> chapterInfoList) {
			super(MainActivity.this, android.R.layout.simple_list_item_activated_1,
					chapterInfoList);
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
			TextView dateTextView = (TextView)
					convertView.findViewById(R.id.chapterName);
			dateTextView.setText(c.chapterName);

			return convertView;
		}
	}
}
