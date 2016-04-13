package com.nexusex.ted.playmusic;

import com.nexusex.ted.bean.Audio;

/**
 * 音频播放接口,播放类实现此接口
 */
public interface Play {
	/**
	 * 准备播放
	 */
	void prepare(Audio audio);

	/**
	 * 开始播放
	 */
	void start();

	/**
	 * 暂停播放
	 */
	void pause();

	/**
	 * 停止播放
	 */
	void stop();

	/**
	 * 释放播放器资源
	 */
	void release();

	/**
	 * 获取播放状态
	 */
	int getPlayState();

	/**
	 * 获取正在播放的音频
	 */
	Audio getMedia();

	/**
	 * 切换播放位置
	 */
	void setCurrentPosition(int position);
}
