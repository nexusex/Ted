package com.nexusex.ted.playmusic;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import com.nexusex.ted.MusicInfoUtils;
import com.nexusex.ted.TedApplication;
import com.nexusex.ted.bean.MusicInfo;
import java.io.IOException;

/**
 * 音频播放类(Service和Activity来持有)
 * 当在Activity中开始播放时开启前台service(开一个notification),这样在Activity被finish时仍保持播放
 * 手动关闭notification时关闭service,这时候得判断一下,如果在app界面未被应该响应停止播放(stop),不在app界面则destroy前台service,释放播放器资源(release)
 */
public class LetUsPlay
	implements Play, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener,
	MediaPlayer.OnSeekCompleteListener {
	public static final int STATE_PLAYING = 1;
	public static final int STATE_PAUSE = 2;
	public static final int STATE_STOP = 3;
	public static final int STATE_RELEASE = 4;

	private int PLAY_STATE = STATE_STOP;

	private MusicInfo mMusicInfo;
	private Context mContext;
	private MediaPlayer mMediaPlayer;

	private volatile static LetUsPlay mLetUsPlay = null;

	private LetUsPlay() {
		mContext = TedApplication.getContext();
		initPlayer();
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

	private void initPlayer() {
		if (mMediaPlayer == null) {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnErrorListener(this);
			mMediaPlayer.setOnSeekCompleteListener(this);
		} else {
			mMediaPlayer.reset();
		}
	}

	@Override public void prepare(MusicInfo musicInfo) {
		if (mMediaPlayer.isPlaying()) {
			throw new IllegalArgumentException("the player is playing , you should first stop it to prepare");
		}
		mMusicInfo = musicInfo;
		try {
			mMediaPlayer.setDataSource(mContext, MusicInfoUtils.getUriWithId(musicInfo.id));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mMediaPlayer.prepareAsync();
		PLAY_STATE = STATE_STOP;
		mOnPlayingListener.onPlayStateChanged(mMusicInfo, STATE_STOP);
	}

	/**
	 * 必须在prepared回调完成后才能start
	 */
	@Override public void start() {
		if (mMediaPlayer.isPlaying()){
			throw new IllegalArgumentException("the player is playing");
		}
		mMediaPlayer.start();
		PLAY_STATE = STATE_PLAYING;
		mOnPlayingListener.onPlayStateChanged(mMusicInfo, STATE_PLAYING);
	}

	@Override public void pause() {
		if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
			mMediaPlayer.pause();
		}
		PLAY_STATE = STATE_PAUSE;
		mOnPlayingListener.onPlayStateChanged(mMusicInfo, STATE_PAUSE);
	}

	/**
	 * 调用stop之后必须prepare才能重新start
	 */
	@Override public void stop() {
		mMediaPlayer.stop();
		PLAY_STATE = STATE_STOP;
		mOnPlayingListener.onPlayStateChanged(mMusicInfo, STATE_STOP);
	}

	@Override public void release() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		PLAY_STATE = STATE_RELEASE;
		mOnPlayingListener.onPlayStateChanged(mMusicInfo, STATE_RELEASE);
	}

	@Override public void reset() {
		initPlayer();
	}

	@Override public int getPlayState() {
		return PLAY_STATE;
	}

	@Override public MusicInfo getMedia() {
		return mMusicInfo;
	}

	@Override public void setCurrentPosition(int position) {
		//TODO:传入audio长度
		if (mMediaPlayer != null) {
			mMediaPlayer.seekTo(position);
		}
		mOnPlayingListener.onPlaying(mMusicInfo, position, 0);
	}

	@Override public void onPrepared(MediaPlayer mediaPlayer) {
		mOnPlayingListener.onPrepared(mMusicInfo);
	}

	@Override public void onCompletion(MediaPlayer mediaPlayer) {
		mOnPlayingListener.onCompletion();
	}

	@Override public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
		Log.d("playback", what + " " + extra);
		mOnPlayingListener.onError();
		return false;
	}

	@Override public void onSeekComplete(MediaPlayer mediaPlayer) {
		mOnPlayingListener.onSeekToCompleted();
	}

	private OnPlayingListener mOnPlayingListener = null;

	public void setOnPlayingListener(OnPlayingListener onPlayingListener) {
		this.mOnPlayingListener = onPlayingListener;
	}
}
