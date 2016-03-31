package com.nexusex.ted.playmusic;

/**
 * 音频播放接口,播放类实现此接口
 */
public interface Play {
	/**
	 * 准备播放
	 */
	void prepare();

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
	 * 重新播放
	 */
	void restart();

	/**
	 * 获取播放状态
	 */
	int getPlayState();

	/**
	 * 获取正在播放的音频
	 * TODO
	 */
	void getMedia();

	/**
	 * 装载音频
	 * TODO:还要传入音频信息
	 */
	void setMedia();

	/**
	 * 切换播放位置
	 */
	void setCurrentPosition(int position);
}
