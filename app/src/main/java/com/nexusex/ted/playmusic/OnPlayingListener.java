package com.nexusex.ted.playmusic;

import com.nexusex.ted.bean.MusicInfo;

/**
 * 监听音频播放过程中的状态
 */
public interface OnPlayingListener {
	/**
	 * 播放状态发生改变
	 */
	void onPlayStateChanged(int playState);

	/**
	 * 播放进度发生变化
	 */
	void onPlaying(MusicInfo musicInfo, long currentPosition, long completeLength);

	/**
	 * prepared完成时调用
	 */
	void onPrepared(MusicInfo musicInfo);

	/**
	 * 一首播放完成时调用
	 */
	void onCompletion();

	/**
	 * 播放错误时调用
	 */
	void onError();


	void onSeekToCompleted();
}
