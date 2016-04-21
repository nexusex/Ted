package com.nexusex.ted.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.nexusex.ted.R;
import com.nexusex.ted.bean.MusicInfo;
import com.nexusex.ted.bean.MusicInfoList;

/**
 * Created by davinci42 on 2016/4/15.
 */
public class PlayingListRvAdapter extends RecyclerView.Adapter<PlayingListRvAdapter.ViewHolder> {

	private Context mContext;
	private MusicInfoList mMusicInfoList;

	public PlayingListRvAdapter(Context context) {
		mContext = context;
	}

	public void setData(MusicInfoList musicInfos) {
		mMusicInfoList = musicInfos;
	}

	@Override public int getItemCount() {
		return mMusicInfoList == null ? 0 : mMusicInfoList.getMusicInfoList().size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		@Bind(R.id.tv_title) TextView tvTitle;
		@Bind(R.id.tv_artist) TextView tvArtist;
		@Bind(R.id.tv_duration) TextView tvDuration;

		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.itemview_playing_list, parent, false);
		return new ViewHolder(view);
	}

	@Override public void onBindViewHolder(ViewHolder holder, int position) {
		MusicInfo musicInfo = mMusicInfoList.getMusicInfoList().get(position);
		holder.tvTitle.setText(musicInfo.getTitle());
		holder.tvArtist.setText(musicInfo.getArtist());
		holder.tvDuration.setText(musicInfo.getFormattedDuration());
	}
}
