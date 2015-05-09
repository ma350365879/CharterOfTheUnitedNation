package un.org.charteroftheunitednation;

import android.app.Application;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyApplication extends Application {
	private static final String TAG = "MyApplicaiton";
	public static JSONArray sChapters = null;

	@Override
	public void onCreate() {
		super.onCreate();
		BufferedReader reader;
		try {
			InputStream in = getResources().openRawResource(R.raw.charter);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}

			JSONObject obj = (JSONObject) new JSONTokener(jsonString.toString()).nextValue();
			sChapters = obj.getJSONArray("chapters");

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
