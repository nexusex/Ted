package com.nexusex.ted.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Bind;
import com.nexusex.ted.R;
import com.nexusex.ted.bean.MusicInfo;
import com.nexusex.ted.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class PlayingListFragment extends BaseFragment {

	@Bind(R.id.rv) RecyclerView mRv;

	private String mTitle;
	private List<MusicInfo> mMusicInfoList;
	private PlayingListAdapter mRvAdapter;

	public static PlayingListFragment newInstance(String title, ArrayList<MusicInfo> musicInfoList) {
		PlayingListFragment playingListFragment = new PlayingListFragment();
		Bundle args = new Bundle();
		playingListFragment.setArguments(args);
		args.putString(BUNDLE_KEY_PLAYING_LIST_TITLE, title);
		args.putSerializable(BUNDLE_KEY_PLAYING_LIST_MUSICINFO_LIST, musicInfoList);
		return playingListFragment;
	}

	@Override public int getContentViewResLayout() {
		return R.layout.widget_rv;
	}

	@Override public void initFragment() {
		Bundle args = getArguments();
		mTitle = args.getString(BUNDLE_KEY_PLAYING_LIST_TITLE);

		mMusicInfoList = (ArrayList<MusicInfo>) args.getSerializable(BUNDLE_KEY_PLAYING_LIST_MUSICINFO_LIST);

		if (mRvAdapter == null) {
			mRvAdapter = new PlayingListAdapter(getContext());
		}
		mRvAdapter.setData(mMusicInfoList);
		mRvAdapter.notifyDataSetChanged();
		mRv.addItemDecoration(new VerticalSpaceItemDecoration(8));
		mRv.setLayoutManager(new LinearLayoutManager(getContext()));
		mRv.setAdapter(mRvAdapter);
	}

	public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

		private int mVerticalSpace;

		public VerticalSpaceItemDecoration(int verticalSpace) {
			mVerticalSpace = verticalSpace;
		}

		@Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
			if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
				outRect.set(0, 0, 0, (int) Utils.convertDpToPixel(mVerticalSpace));
			}
		}
	}
}
