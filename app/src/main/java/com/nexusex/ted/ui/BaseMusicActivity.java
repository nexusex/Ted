package com.nexusex.ted.ui;

import android.os.Bundle;

/**
 * Activity基类
 * 持有音频播放类的实例,实现OnPlayingListener接口,可以通知所有activity实时的播放情况
 */
public abstract class BaseMusicActivity extends BaseActivity {

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
}



