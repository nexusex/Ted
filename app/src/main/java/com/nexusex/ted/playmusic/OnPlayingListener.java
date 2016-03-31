package com.nexusex.ted.playmusic;

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
	 * TODO:还要传入音频信息
	 */
	void onPlaying(int currentPosition, int completeLength);
}
