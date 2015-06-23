package org.un.charteroftheunitednations;

import android.app.Application;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
	private static final String TAG = MyApplication.class.getSimpleName();
	private static JSONArray sChaptersJSON;
	private static List<ChapterInfo> sChapters;

	public static List<ChapterInfo> getChapters() {
		return sChapters;
	}

	public static JSONArray getChaptersJSON() {
		return sChaptersJSON;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		BufferedReader reader;

		sChapters = new ArrayList<>();
		try {
			InputStream in = getResources().openRawResource(R.raw.charter);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}

			JSONObject obj = (JSONObject) new JSONTokener(jsonString.toString()).nextValue();
			sChaptersJSON = obj.getJSONArray("chapters");

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			for (int i = 0; i < sChaptersJSON.length(); ++i) {
				ChapterInfo info = new ChapterInfo(
						sChaptersJSON.getJSONObject(i).getString("chapterId"),
						sChaptersJSON.getJSONObject(i).getString("chapterName"));
				sChapters.add(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
