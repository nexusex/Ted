package com.nexusex.ted;

import android.app.Application;
import android.content.Context;

/**
 * Created by drizzle on 16/4/13.
 */
public class TedApplication extends Application {
	private static Context mContext;

	@Override public void onCreate() {
		super.onCreate();
		mContext = this;
	}

	public static Context getContext() {
		return mContext;
	}
}
