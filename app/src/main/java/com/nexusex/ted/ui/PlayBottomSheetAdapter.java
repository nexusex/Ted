package com.nexusex.ted.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.nexusex.ted.R;
import com.nexusex.ted.bean.MusicInfo;
import java.util.List;

/**
 * Created by drizzle on 16/4/15.
 */
public class PlayBottomSheetAdapter extends RecyclerView.Adapter<PlayBottomSheetAdapter.BottomSheetViewHolder> {
	private List<MusicInfo> mMusicInfoList;
	private Context mContext;
	private LayoutInflater mLayoutInflater;

	public PlayBottomSheetAdapter(List<MusicInfo> musicInfoList, Context context) {
		mMusicInfoList = musicInfoList;
		mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override public void onBindViewHolder(BottomSheetViewHolder holder, int position) {
		MusicInfo musicInfo = getItem(position);
	}

	@Override public BottomSheetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new BottomSheetViewHolder(mLayoutInflater.inflate(R.layout.item_play_bottomsheet, parent, false));
	}

	@Override public int getItemCount() {
		return mMusicInfoList == null ? 0 : mMusicInfoList.size();
	}

	private MusicInfo getItem(int position) {
		return mMusicInfoList.get(position);
	}

	public class BottomSheetViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.item_bs_title) TextView itemTitle;
		@Bind(R.id.item_bs_subtitle) TextView itemSubTitle;
		@Bind(R.id.item_bs_duration) TextView itemDuration;
		@Bind(R.id.item_bs_headphone) ImageView itemHeadphone;

		public BottomSheetViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
