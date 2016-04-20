package com.nexusex.ted.playmusic;

/**
 * 面向接口编程...
 * PlayMusicManager实现这个接口,让任何持有PlayMusicManager的类都能够以最简单的方式进行播放
 * (不关心播放顺序,不关心MediaPlayer的准备和销毁)
 */
public interface PlayList {

	/**
	 * 播放
	 */
	void play();

	/**
	 * 暂停
	 */
	void pause();

	/**
	 * 上一首
	 */
	void previousMusic();

	/**
	 * 下一首
	 */
	void nextMusic();

	/**
	 * 切换循环方式
	 */
	void switchLoopStyle();

	/**
	 * 收藏当前音乐
	 */
	void star();

	/**
	 * 快进到固定位置
	 */
	void seekToPosition(long targetPosition);

	/**
	 * 释放播放资源,保存播放状态
	 */
	void destroy();
}
