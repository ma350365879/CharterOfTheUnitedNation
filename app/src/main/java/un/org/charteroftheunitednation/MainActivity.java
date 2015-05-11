package un.org.charteroftheunitednation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		list = (ListView) findViewById(android.R.id.list);
//		ImageView header = new ImageView(this);
//		header.setImageResource(R.drawable.un_header);
//		header.setLayoutParams(DetailActivity.LAYOUT_PARAMS);
		View header = getLayoutInflater().inflate(R.layout.list_header, null,false);

		list.addHeaderView(header);

		ArrayList<ChapterInfo> chapterInfos = new ArrayList<>();
		try {
			for (int i = 0; i < MyApplication.sChapters.length(); ++i) {
				String[] chapterInfo = {
						MyApplication.sChapters.getJSONObject(i).getString("chapterName"),
						MyApplication.sChapters.getJSONObject(i).getString("chapterId"),
				};
				chapterInfos.add(new ChapterInfo(chapterInfo));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ChapterAdapter adapter = new ChapterAdapter(chapterInfos);

//		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
//				.simple_list_item_1, jsonArray);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position != 0) {
					Intent i = new Intent(MainActivity.this, DetailActivity.class);
					i.putExtra("position", position - 1);
					startActivity(i);
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private class ChapterAdapter extends ArrayAdapter<ChapterInfo> {

		public ChapterAdapter(List<ChapterInfo> chapterInfoList) {
			super(MainActivity.this, android.R.layout.simple_list_item_1, chapterInfoList);
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
