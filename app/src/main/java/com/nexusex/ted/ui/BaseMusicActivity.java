package com.nexusex.ted.ui;

import android.os.Bundle;
import butterknife.ButterKnife;
import com.nexusex.ted.bean.MusicInfo;
import com.nexusex.ted.playmusic.LetUsPlay;
import com.nexusex.ted.playmusic.OnPlayingListener;

/**
 * Activity基类
 * 持有音频播放类的实例,实现OnPlayingListener接口,可以通知所有activity实时的播放情况
 */
public abstract class BaseMusicActivity extends BaseActivity implements OnPlayingListener {

	public LetUsPlay mLetUsPlay;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getContentViewResLayout());
		mLetUsPlay = LetUsPlay.getInstance();
		mLetUsPlay.setOnPlayingListener(this);
	}

	@Override public void onPlayStateChanged(int playState) {

	}

	@Override public void onPlaying(MusicInfo mMusicInfo, long currentPosition, long completeLength) {

	}

	@Override public void onPrepared(MusicInfo mMusicInfo) {

	}

	@Override public void onCompletion() {

	}

	@Override public void onError() {

	}

	@Override public void onSeekToCompleted() {

	}

	public abstract int getContentViewResLayout();

}



