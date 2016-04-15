package com.nexusex.ted.ui;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import com.nexusex.ted.R;
import com.nexusex.ted.bean.MusicInfo;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicActivity extends BaseMusicActivity {
	@Bind(R.id.toolbar) Toolbar mToolbar;
	@Bind(R.id.play_music_rv) RecyclerView mRecyclerView;

	private List<MusicInfo> mMusicInfos;
	private PlayBottomSheetAdapter mBottomSheetAdapter;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override public void initActivity() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		mMusicInfos = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			mMusicInfos.add(new MusicInfo());
		}
		mBottomSheetAdapter = new PlayBottomSheetAdapter(mMusicInfos,this);
		mRecyclerView.setAdapter(mBottomSheetAdapter);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
	}

	@Override public void onPlayStateChanged(int playState) {
		super.onPlayStateChanged(playState);
	}

	@Override public void onPlaying(MusicInfo mMusicInfo, long currentPosition, long completeLength) {
		super.onPlaying(mMusicInfo, currentPosition, completeLength);
	}

	@Override public void onPrepared(MusicInfo mMusicInfo) {
		super.onPrepared(mMusicInfo);
	}

	@Override public void onCompletion() {
		super.onCompletion();
	}

	@Override public void onError() {
		super.onError();
	}

	@Override public void onSeekToCompleted() {
		super.onSeekToCompleted();
	}

	@Override public int getContentViewResLayout() {
		return R.layout.activity_play_music;
	}
}
