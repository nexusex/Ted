package com.nexusex.ted;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.nexusex.ted.bean.Audio;
import com.nexusex.ted.playmusic.LetUsPlay;
import com.nexusex.ted.playmusic.OnPlayingListener;

/**
 * Activity基类
 * 持有音频播放类的实例,实现OnPlayingListener接口,可以通知所有activity实时的播放情况
 */
public class BaseActivity extends AppCompatActivity implements OnPlayingListener {
	public LetUsPlay mLetUsPlay;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLetUsPlay = LetUsPlay.getInstance();
		mLetUsPlay.setOnPlayingListener(this);
	}

	@Override public void onPlayStateChanged(Audio audio, int playState) {

	}

	@Override public void onPlaying(Audio audio, int currentPosition, int completeLength) {

	}

	@Override public void onPrepared(Audio audio) {

	}
}
