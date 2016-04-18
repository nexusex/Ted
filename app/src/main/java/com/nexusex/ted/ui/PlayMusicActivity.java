package com.nexusex.ted.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import com.nexusex.ted.R;
import com.nexusex.ted.bean.MusicInfo;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicActivity extends BaseMusicActivity {
	@Bind(R.id.toolbar) Toolbar mToolbar;
	@Bind(R.id.play_music_rv) RecyclerView mRecyclerView;
	@Bind(R.id.play_current_time_tv) TextView currentTimeTv;
	@Bind(R.id.play_total_time_tv) TextView totalTimeTv;


	private List<MusicInfo> mMusicInfos;
	private PlayBottomSheetAdapter mBottomSheetAdapter;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override public void initActivity() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		mMusicInfos = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			mMusicInfos.add(new MusicInfo());
		}
		mBottomSheetAdapter = new PlayBottomSheetAdapter(mMusicInfos, this);
		mRecyclerView.setAdapter(mBottomSheetAdapter);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(mRecyclerView);
		bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
			@Override public void onStateChanged(@NonNull View bottomSheet, int newState) {

			}

			@Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {

			}
		});
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

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_play, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case android.R.id.home:
				finishAfterTransition();
		}
		return super.onOptionsItemSelected(item);
	}
}
