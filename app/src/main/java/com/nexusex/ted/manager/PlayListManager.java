package com.nexusex.ted.manager;

import android.content.Context;
import com.google.gson.Gson;
import com.nexusex.ted.bean.MusicInfo;
import com.nexusex.ted.bean.MusicInfoList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by davinci42 on 2016/4/20.
 */
public class PlayListManager {

	public static MusicInfoList musicInfoList;

	public static void removeMusicInfoFromList(Context context, MusicInfo musicInfo) {
		musicInfoList.getMusicInfoList().remove(musicInfo);
		saveMusicInfoList(context);
	}

	public static void addMusicInfoToList(Context context, MusicInfo musicInfo) {
		musicInfoList.getMusicInfoList().add(musicInfo);
		arrangeMusicInfoListByTitle();
		saveMusicInfoList(context);
	}

	public static void saveMusicInfoList(Context context) {
		String jsonString = new Gson().toJson(musicInfoList);
		String filename = musicInfoList.getTitle();

		FileOutputStream outputStream;

		try {
			outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(jsonString.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void arrangeMusicInfoListByTitle() {
		Collections.sort(musicInfoList.getMusicInfoList());
	}

	public static void getMusicInfoListByTitle(Context context, String title) {

		FileInputStream inputStream;

		try {
			inputStream = context.openFileInput(title);
			StringBuilder builder = new StringBuilder();

			int ch;
			while ((ch = inputStream.read()) != -1) {
				builder.append((char) ch);
			}

			String temp = builder.toString();
			musicInfoList = new Gson().fromJson(temp, MusicInfoList.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void changeListTitle(Context context, String title) {
		musicInfoList.setTitle(title);
		saveMusicInfoList(context);
	}
}
