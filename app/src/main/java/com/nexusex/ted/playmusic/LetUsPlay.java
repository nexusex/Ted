package com.nexusex.ted.playmusic;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import com.nexusex.ted.TedApplication;
import com.nexusex.ted.bean.Audio;

/**
 * 音频播放类(Service和Activity来持有)
 * 当在Activity中开始播放时开启前台service(开一个notification),这样在Activity被finish时仍保持播放
 * 手动关闭notification时关闭service,此时默认应该是停止播放,如果在app界面应该响应停止播放,不在app界面则destroy前台service,彻底退出播放
 * service被强行回收的状态另外解决
 */
public class LetUsPlay implements Play, MediaPlayer.OnPreparedListener {
	public static final int STATE_PLAYING = 1;
	public static final int STATE_PAUSE = 2;
	public static final int STATE_STOP = 3;
	public static final int STATE_RELEASE = 4;

	private int PLAY_STATE = STATE_STOP;

	private Audio mAudio;
	private Context mContext;
	private MediaPlayer mMediaPlayer;

	private volatile static LetUsPlay mLetUsPlay = null;

	private LetUsPlay() {
		mContext = TedApplication.getContext();
		if (mMediaPlayer == null) {
			mMediaPlayer = new MediaPlayer();
		} else {
			mMediaPlayer.reset();
		}
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setOnPreparedListener(this);
	}

	public static LetUsPlay getInstance() {
		if (mLetUsPlay == null) {
			synchronized (LetUsPlay.class) {
				if (mLetUsPlay == null) {
					mLetUsPlay = new LetUsPlay();
				}
			}
		}
		return mLetUsPlay;
	}

	@Override public void prepare(Audio audio) {
		mAudio = audio;
		//mMediaPlayer.setDataSource(mContext,audio.getUri());
		mMediaPlayer.prepareAsync();
		PLAY_STATE = STATE_STOP;
		mOnPlayingListener.onPlayStateChanged(audio, STATE_STOP);
	}

	@Override public void start() {
		mMediaPlayer.start();
		PLAY_STATE = STATE_PLAYING;
		mOnPlayingListener.onPlayStateChanged(mAudio, STATE_PLAYING);
	}

	@Override public void pause() {
		mMediaPlayer.pause();
		PLAY_STATE = STATE_PAUSE;
		mOnPlayingListener.onPlayStateChanged(mAudio, STATE_PAUSE);
	}

	/**
	 * 调用stop之后必须prepare才能重新start
	 */
	@Override public void stop() {
		mMediaPlayer.stop();
		PLAY_STATE = STATE_STOP;
		mOnPlayingListener.onPlayStateChanged(mAudio, STATE_STOP);
	}

	@Override public void release() {
		mMediaPlayer.release();
		PLAY_STATE = STATE_RELEASE;
		mOnPlayingListener.onPlayStateChanged(mAudio, STATE_RELEASE);
	}

	@Override public int getPlayState() {
		return PLAY_STATE;
	}

	@Override public Audio getMedia() {
		return mAudio;
	}

	@Override public void setCurrentPosition(int position) {
		//TODO:传入audio长度
		mOnPlayingListener.onPlaying(mAudio, position, 0);
	}

	@Override public void onPrepared(MediaPlayer mediaPlayer) {
		mOnPlayingListener.onPrepared(mAudio);
	}

	private OnPlayingListener mOnPlayingListener = null;

	public void setOnPlayingListener(OnPlayingListener onPlayingListener) {
		this.mOnPlayingListener = onPlayingListener;
	}
}
