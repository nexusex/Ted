package com.nexusex.ted.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Bind;
import com.nexusex.ted.R;
import com.nexusex.ted.bean.MusicInfo;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.blurry.Blurry;

public class PlayMusicActivity extends BaseMusicActivity {
	@Bind(R.id.toolbar) Toolbar mToolbar;
	@Bind(R.id.play_music_rv) RecyclerView mRecyclerView;
	@Bind(R.id.play_current_time_tv) TextView currentTimeTv;
	@Bind(R.id.play_total_time_tv) TextView totalTimeTv;
	@Bind(R.id.play_progress) SeekBar playSeekBar;
	@Bind(R.id.play_loop_style) ImageButton loopStyleBtn;
	@Bind(R.id.play_previous) ImageButton previousBtn;
	@Bind(R.id.play_switch) ImageButton switchBtn;
	@Bind(R.id.play_next) ImageButton nextBtn;
	@Bind(R.id.play_star) ImageButton starbtn;
	@Bind(R.id.play_blur_cover) ImageView blurCoverIv;

	private static final int BLUR_HANDLER = 1;

	private List<MusicInfo> mMusicInfos;
	private PlayBottomSheetAdapter mBottomSheetAdapter;

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

		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(mRecyclerView);
		bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
			@Override public void onStateChanged(@NonNull View bottomSheet, int newState) {

			}

			@Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {

			}
		});
		mRecyclerView.setAdapter(mBottomSheetAdapter);
		changeBlurCover();
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

	/**
	 * 切换模糊图片动画
	 */
	private void changeBlurCover() {
		ObjectAnimator firstAnim = ObjectAnimator.ofFloat(blurCoverIv, "alpha", 1.0f, 0.0f);
		firstAnim.setDuration(10);
		firstAnim.addListener(new AnimatorListenerAdapter() {
			@Override public void onAnimationEnd(Animator animation) {
				Picasso.with(PlayMusicActivity.this).load(R.drawable.fake_cover).into(blurCoverIv);
				blurImage(blurCoverIv);
			}
		});
		ObjectAnimator secondAnim = ObjectAnimator.ofFloat(blurCoverIv, "alpha", 0.0f, 1.0f);
		secondAnim.setDuration(300);
		secondAnim.setStartDelay(100);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.play(firstAnim).before(secondAnim);
		animatorSet.start();
	}

	private void blurImage(ImageView imageView) {
		Message message = new Message();
		message.what = BLUR_HANDLER;
		message.obj = imageView;
		mHandler.sendMessageDelayed(message, 5);
	}

	private Handler mHandler = new Handler() {
		@Override public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case BLUR_HANDLER:
					ImageView imageView = (ImageView) msg.obj;
					Blurry.with(PlayMusicActivity.this)
						.radius(10)
						.sampling(8)
						.async()
						.capture(imageView)
						.into(imageView);
					break;
				default:
					break;
			}
		}
	};

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
