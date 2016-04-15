package com.nexusex.ted.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by davinci42 on 2016/4/13.
 */
public class Utils {

	public static void handleError(String TAG, String msg) {
		Log.e(TAG, msg);
	}

	public static float convertPixelsToDp(float px) {
		DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return Math.round(dp);
	}

	public static float convertDpToPixel(float dp) {
		DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return Math.round(px);
	}

	public static int getScreenWidth() {
		return Resources.getSystem().getDisplayMetrics().widthPixels;
	}

	public static int getScreenHeight() {
		return Resources.getSystem().getDisplayMetrics().heightPixels;
	}

	public static String convertSecondsToHhMmSs(long millis) {

		return String.format(Locale.ENGLISH, "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
			TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
			TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
				TimeUnit.MILLISECONDS.toMinutes(millis)));
	}

	public static String convertSecondsToMmSs(long millis) {

		return String.format(Locale.ENGLISH, "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis),
			TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
				TimeUnit.MILLISECONDS.toMinutes(millis)));
	}
}
