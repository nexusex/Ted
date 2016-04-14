package com.nexusex.ted.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.nexusex.ted.R;
import com.nexusex.ted.utils.Utils;

/**
 * Created by davinci42 on 2016/4/13.
 */
public class ItemInfoView extends LinearLayout {

	public static final String TAG = "ItemInfoView";

	@Bind(R.id.iv_cover) ImageView mIvCover;
	@Bind(R.id.iv_star) ImageView mIvStar;
	@Bind(R.id.iv_disc) ImageView mIvDisc;
	@Bind(R.id.tv_title) TextView mTvTitle;
	@Bind(R.id.tv_artist) TextView mTvArtist;
	@Bind(R.id.tv_duration) TextView mTvDuration;
	@Bind(R.id.rl_main) RelativeLayout mRlMain;

	private boolean mIsFolding;

	private long mAnimDuration = 300;
	private float mIvCoverPreviousX;
	private float mIvCoverTargetX;

	private float mRlMainInitWidth;

	public ItemInfoView(Context context) {
		this(context, null);
	}

	public ItemInfoView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ItemInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}

	public ItemInfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init() {
		mIsFolding = false;
		View view = LayoutInflater.from(getContext()).inflate(R.layout.itemview_playing_list, this, true);
		ButterKnife.bind(this, view);
	}

	public void toggleViewState() {
		if (mIsFolding) {
			unfoldView();
		} else {
			foldView();
		}
	}

	private void foldView() {
		mIsFolding = true;
		mIvCoverPreviousX = mIvCover.getLeft();
		mIvCoverTargetX = mRlMain.getLeft();

		mRlMainInitWidth = mRlMain.getMeasuredWidth();

		mIvCover.animate()
			.x(mIvCoverTargetX)
			.setDuration(mAnimDuration)
			.setInterpolator(new DecelerateInterpolator())
			.setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override public void onAnimationUpdate(ValueAnimator animation) {
					float progress = (float) animation.getAnimatedValue();
					int currentWidth = (int) (mRlMainInitWidth - progress * Utils.convertDpToPixel(100));
					mRlMain.layout(mRlMain.getLeft(), mRlMain.getTop(), mRlMain.getLeft() + currentWidth,
						mRlMain.getTop() + mRlMain.getMeasuredHeight());
					mRlMain.postInvalidate();
				}
			});
	}

	private void unfoldView() {
		mIsFolding = false;
		mIvCoverTargetX = mIvCoverPreviousX;
		mIvCoverPreviousX = mIvCover.getLeft();

		mIvCover.animate()
			.x(mIvCoverTargetX)
			.setDuration(mAnimDuration)
			.setInterpolator(new DecelerateInterpolator())
			.setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override public void onAnimationUpdate(ValueAnimator animation) {
					float progress = (float) animation.getAnimatedValue();
					int currentWidth = (int) (mRlMainInitWidth - (1 - progress) * Utils.convertDpToPixel(100));
					mRlMain.layout(mRlMain.getLeft(), mRlMain.getTop(), mRlMain.getLeft() + currentWidth,
						mRlMain.getTop() + mRlMain.getMeasuredHeight());
					mRlMain.postInvalidate();
				}
			});
	}
}
