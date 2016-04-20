package com.nexusex.ted.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.nexusex.ted.R;
import com.nexusex.ted.utils.Utils;

/**
 * Created by davinci42 on 2016/4/18.
 */
public class ControlPanemView extends RelativeLayout {

	@Bind(R.id.iv_cover) ImageView mIvCover;
	@Bind(R.id.iv_clock) ImageView mIvClock;
	@Bind(R.id.iv_disc) ImageView mIvDisc;
	@Bind(R.id.iv_control) ImageView mIvControl;
	@Bind(R.id.rl_main) RelativeLayout mRlMain;
	@Bind(R.id.tv_title) TextView mTvTitle;
	@Bind(R.id.tv_artist) TextView mTvArtist;
	@Bind(R.id.tv_duration) TextView mTvDuration;

	private boolean mIsPlaying;
	private long mAnimDuration;
	private float mPlayingProgress;

	private float mIvControlOriginalCenterY;
	private float mIvControlTargetCenterY;
	private float mIvDiscOriginalCenterX;
	private float mIvDiscTargetCenterX;

	private float mIvCoverOriginalX;
	private float mIvCoverDistanceX;

	public ControlPanemView(Context context) {
		this(context, null);
	}

	public ControlPanemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ControlPanemView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}

	public ControlPanemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initView();
	}

	private void initView() {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_control_panel, this, true);
		ButterKnife.bind(this, view);

		mAnimDuration = 300;
		mPlayingProgress = 0;

		mIvControl.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				if (mIsPlaying) {
					stopPlayingAnim();
				} else {
					startPlayingAnim();
				}
			}
		});
	}

	private void startPlayingAnim() {

		mIvControl.animate()
			.y(mIvControlTargetCenterY - mIvControl.getMeasuredHeight() / 2)
			.setDuration(mAnimDuration)
			.setInterpolator(new DecelerateInterpolator())
			.setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override public void onAnimationUpdate(ValueAnimator animation) {
					float progress = (float) animation.getAnimatedValue();

					float ivDiscTargetCenterX =
						mIvDiscTargetCenterX + (mIvDiscOriginalCenterX - mIvDiscTargetCenterX) * mPlayingProgress;

					float ivDiscCenterX =
						mIvDiscOriginalCenterX - (mIvDiscOriginalCenterX - ivDiscTargetCenterX) * progress;

					float ivDiscCenterY =
						mIvControlOriginalCenterY + (mIvControlTargetCenterY - mIvControlOriginalCenterY) * progress;

					mIvDisc.setScaleX((float) (0.5 + 0.5 * progress));
					mIvDisc.setScaleY((float) (0.5 + 0.5 * progress));

					mIvDisc.layout((int) (ivDiscCenterX - mIvDisc.getMeasuredWidth() / 2),
						(int) (ivDiscCenterY - mIvDisc.getMeasuredHeight() / 2),
						(int) (ivDiscCenterX + mIvDisc.getMeasuredWidth() / 2),
						(int) (ivDiscCenterY + mIvDisc.getMeasuredHeight() / 2));

					mIvCover.layout((int) (mIvCoverOriginalX - mIvCoverDistanceX * progress), mIvCover.getTop(),
						(int) (mIvCoverOriginalX - mIvCoverDistanceX * progress + mIvCover.getMeasuredWidth()),
						mIvCover.getBottom());

					mTvTitle.setAlpha(1 - progress);
					mTvArtist.setAlpha(1 - progress);
					mIvClock.setAlpha(1 - progress);
					mTvDuration.setAlpha(1 - progress);
				}
			});

		mIsPlaying = true;
	}

	private void stopPlayingAnim() {

		final float ivDiscCenterX = mIvDisc.getLeft() + mIvDisc.getMeasuredWidth() / 2;

		mIvControl.animate()
			.y(mIvControlOriginalCenterY - mIvControl.getMeasuredHeight() / 2)
			.setDuration(mAnimDuration)
			.setInterpolator(new DecelerateInterpolator())
			.setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override public void onAnimationUpdate(ValueAnimator animation) {
					float progress = (float) animation.getAnimatedValue();

					float ivDiscCurrentCenterX = ivDiscCenterX + (mIvDiscOriginalCenterX - ivDiscCenterX) * progress;
					float ivDiscCurrentCenterY =
						mIvControlTargetCenterY - (mIvControlTargetCenterY - mIvControlOriginalCenterY) * progress;

					mIvDisc.setScaleX((float) (1 - 0.5 * progress));
					mIvDisc.setScaleY((float) (1 - 0.5 * progress));

					mIvDisc.layout((int) (ivDiscCurrentCenterX - mIvDisc.getMeasuredWidth() / 2),
						(int) (ivDiscCurrentCenterY - mIvDisc.getMeasuredHeight() / 2),
						(int) (ivDiscCurrentCenterX + mIvDisc.getMeasuredWidth() / 2),
						(int) (ivDiscCurrentCenterY + mIvDisc.getMeasuredHeight() / 2));

					mIvCover.layout((int) (mIvCoverOriginalX - mIvCoverDistanceX * (1 - progress)), mIvCover.getTop(),
						(int) (mIvCoverOriginalX - mIvCoverDistanceX * (1 - progress) + mIvCover.getMeasuredWidth()),
						mIvCover.getBottom());

					mTvTitle.setAlpha(progress);
					mTvArtist.setAlpha(progress);
					mIvClock.setAlpha(progress);
					mTvDuration.setAlpha(progress);
				}
			});

		mIsPlaying = false;
	}

	@Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		mIvControlOriginalCenterY = mIvControl.getTop() + mIvControl.getMeasuredHeight() / 2;
		mIvControlTargetCenterY = (mIvCover.getTop() + mIvCover.getBottom()) / 2 + mRlMain.getTop();

		mIvDiscOriginalCenterX = (mIvDisc.getLeft() + mIvDisc.getRight()) / 2;
		mIvDiscTargetCenterX = mIvCover.getRight();

		mIvCoverOriginalX = mIvCover.getLeft();
		mIvCoverDistanceX = Utils.convertDpToPixel(60);
	}
}
