package com.nexusex.ted;

import android.os.Bundle;

public class MainActivity extends BaseActivity {

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		MusicInfoUtils.saveMusicInfoList(this, MusicInfoUtils.getMusicInfo(this));
	}
}
