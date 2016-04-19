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
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Bind;
import com.nexusex.ted.R;
import com.nexusex.ted.bean.MusicInfo;
import com.nexusex.ted.utils.DensityUtils;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.blurry.Blurry;

public class PlayMusicActivity extends BaseMusicActivity implements View.OnClickListener {

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
	@Bind(R.id.play_cover) ImageView coverIv;
	@Bind(R.id.play_action_layout) LinearLayout actionLayout;
	@Bind(R.id.play_progress_layout) LinearLayout progressLayout;

	private static final int BLUR_HANDLER = 1;
	private static final String TAG = "play_music";
	private int bottomSheetHeight;
	private boolean isBottomSheetExpand = false;

	private List<MusicInfo> mMusicInfos;
	private PlayBottomSheetAdapter mBottomSheetAdapter;
	private BottomSheetBehavior mBottomSheetBehavior;

	@Override public void initActivity() {
		initData();
		initViews();
	}

	private void initViews() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		if (mToolbar != null) {
			mToolbar.setTitle("Yellow");
			mToolbar.setSubtitle("Coldplay");
		}
		setSupportActionBar(mToolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		getSupportActionBar().setHomeButtonEnabled(true);
		changeBlurCover();
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		mBottomSheetBehavior = BottomSheetBehavior.from(mRecyclerView);
		mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
			@Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
				switch (newState) {
					case BottomSheetBehavior.STATE_EXPANDED:
						isBottomSheetExpand = true;
						break;
					case BottomSheetBehavior.STATE_COLLAPSED:
						isBottomSheetExpand = false;
						break;
					default:
						break;
				}
			}

			@Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {
				animLayout(slideOffset);
			}
		});
		mRecyclerView.setAdapter(mBottomSheetAdapter);
		blurCoverIv.setOnClickListener(this);
	}

	private void initData() {
		bottomSheetHeight = DensityUtils.dip2px(this, 180);
		mMusicInfos = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			mMusicInfos.add(new MusicInfo());
		}
		mBottomSheetAdapter = new PlayBottomSheetAdapter(mMusicInfos, this);
	}

	@Override public void onClick(View v) {
		switch (v.getId()) {
			case R.id.play_blur_cover:
				if (isBottomSheetExpand) {
					mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
					mRecyclerView.smoothScrollToPosition(0);
				} else {
					return;
				}
				break;
			default:
				break;
		}
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
	 * 随BottomSheet滑动控件的动画
	 */
	private void animLayout(float offset) {
		float fixOffset = (float) (offset * 0.3);
		actionLayout.animate().translationY(-offset * bottomSheetHeight).setDuration(0);
		progressLayout.animate().translationY(-offset * bottomSheetHeight).setDuration(0);
		coverIv.animate()
			.scaleX(1.0f - fixOffset)
			.scaleY(1.0f - fixOffset)
			.translationY(-fixOffset * bottomSheetHeight)
			.setDuration(0);
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
		secondAnim.setDuration(500);
		secondAnim.setStartDelay(200);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.play(firstAnim).before(secondAnim);
		animatorSet.start();
	}

	private void blurImage(ImageView imageView) {
		Message message = new Message();
		message.what = BLUR_HANDLER;
		message.obj = imageView;
		mHandler.sendMessageDelayed(message, 10);
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

	@Override public void onBackPressed() {
		if (isBottomSheetExpand) {
			mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
			mRecyclerView.smoothScrollToPosition(0);
		} else {
			finishAfterTransition();
		}
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
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
