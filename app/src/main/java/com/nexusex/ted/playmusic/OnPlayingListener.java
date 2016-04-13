package com.nexusex.ted.playmusic;

import com.nexusex.ted.bean.Audio;

/**
 * 监听音频播放过程中的状态
 */
public interface OnPlayingListener {
	/**
	 * 播放状态发生改变
	 */
	void onPlayStateChanged(Audio audio, int playState);

	/**
	 * 播放进度发生变化
	 */
	void onPlaying(Audio audio, int currentPosition, int completeLength);

	/**
	 *prepared完成时调用
	 */
	void onPrepared(Audio audio);
}
