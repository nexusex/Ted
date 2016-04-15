package com.nexusex.ted.playmusic;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.PowerManager;
import com.nexusex.ted.TedApplication;
import com.nexusex.ted.bean.MusicInfo;
import com.nexusex.ted.manager.MusicInfoUtils;
import com.nexusex.ted.utils.Utils;
import java.io.IOException;

/**
 * 音频播放类(Service和Activity来持有)
 * 当在Activity中开始播放时开启前台service(开一个notification),这样在Activity被finish时仍保持播放
 * 手动关闭notification时关闭service,这时候得判断一下,如果在app界面未被应该响应停止播放(stop),不在app界面则destroy前台service,释放播放器资源(release)
 *
 * 获取到该类的实例后,基本调用顺序  initPlayer->prepare->在onPrepared回调中start->pause->stop->prepare
 * 释放和重启顺序  在initPlayer之后任何时候调用release->重启调用reset->prepare...
 */
public class LetUsPlay
	implements Play, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener,
	MediaPlayer.OnSeekCompleteListener {

	private static final String LOG_TAG = "let_us_play";

	//准备过程
	public static final int STATE_PREPARING = 1;
	//正在播放
	public static final int STATE_PLAYING = 2;
	//暂停状态
	public static final int STATE_PAUSE = 3;
	//停止状态(stop之后,prepare之前)
	public static final int STATE_STOP = 4;
	//播放器被释放之后(release之后,reset之前)
	public static final int STATE_RELEASE = 5;

	private int PLAY_STATE = STATE_RELEASE;

	private MusicInfo mMusicInfo;
	private Context mContext;
	private MediaPlayer mMediaPlayer;
	private int totalAudioLength = 0;

	private volatile static LetUsPlay mLetUsPlay = null;

	private LetUsPlay() {
		mContext = TedApplication.getContext();
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

	@Override public void initPlayer() {
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
		PLAY_STATE = STATE_STOP;
		mOnPlayingListener.onPlayStateChanged(PLAY_STATE);
	}

	@Override public void prepare(MusicInfo musicInfo) {
		if (mMediaPlayer == null) {
			throw new NullPointerException("the mediaPlayer is null!");
		}
		if (mMediaPlayer.isPlaying() && PLAY_STATE != STATE_STOP) {
			Utils.handleError(LOG_TAG, "the player has prepared , you should first stop it to prepare");
			return;
		}
		mMusicInfo = musicInfo;
		try {
			mMediaPlayer.setDataSource(mContext, MusicInfoUtils.getUriWithId(mMusicInfo.id));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mMediaPlayer.prepareAsync();
		PLAY_STATE = STATE_PREPARING;
		mOnPlayingListener.onPlayStateChanged(PLAY_STATE);
	}

	/**
	 * 必须在prepared回调完成后才能start
	 */
	@Override public void start() {
		if (mMediaPlayer == null) {
			throw new NullPointerException("the mediaPlayer is null!");
		}
		if (PLAY_STATE == STATE_PAUSE) {
			mMediaPlayer.start();
			mMediaPlayer.setWakeMode(mContext, PowerManager.PARTIAL_WAKE_LOCK);//保证屏幕熄灭时还能使用cpu
		}
		PLAY_STATE = STATE_PLAYING;
		mOnPlayingListener.onPlayStateChanged(PLAY_STATE);
		mHandler.postDelayed(mRunnable, 100);
	}

	@Override public void pause() {
		if (mMediaPlayer == null) {
			throw new NullPointerException("the mediaPlayer is null!");
		}
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.pause();
		}
		PLAY_STATE = STATE_PAUSE;
		mOnPlayingListener.onPlayStateChanged(PLAY_STATE);
	}

	/**
	 * 调用stop之后必须prepare才能重新start
	 */
	@Override public void stop() {
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
		}
		PLAY_STATE = STATE_STOP;
		mOnPlayingListener.onPlayStateChanged(PLAY_STATE);
		//手动stop的话进度条回到初始状态
		mOnPlayingListener.onPlaying(mMusicInfo, 0, totalAudioLength);
	}

	@Override public void release() {
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		PLAY_STATE = STATE_RELEASE;
		mOnPlayingListener.onPlayStateChanged(PLAY_STATE);
	}

	@Override public void reset() {
		initPlayer();
	}

	/**
	 * 快速移动到某个位置播放,传入milliseconds
	 */
	@Override public void setCurrentPosition(int position) {
		if (mMediaPlayer != null) {
			if (position < 0 && position > mMediaPlayer.getDuration()) {
				Utils.handleError(LOG_TAG, "the position is invalid");
				stop();
			} else {
				mMediaPlayer.seekTo(position);
				mOnPlayingListener.onPlaying(mMusicInfo, position, totalAudioLength);
			}
		}
	}

	/**
	 * 播放下一首,先stop,再prepare
	 */
	@Override public void playNextAudio(MusicInfo musicInfo) {
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
		}
		PLAY_STATE = STATE_STOP;
		if (mOnPlayingListener != null) {
			mOnPlayingListener.onPlayStateChanged(PLAY_STATE);
		}
		prepare(musicInfo);
	}

	/**
	 * 获取当前播放状态
	 */
	public int getPlayState() {
		return PLAY_STATE;
	}

	/**
	 * 获取当前播放的音频
	 */
	public MusicInfo getMedia() {
		return mMusicInfo;
	}

	/**
	 * 音频prepare完成,状态变为pause,这时候获取音频的
	 */
	@Override public void onPrepared(MediaPlayer mediaPlayer) {
		PLAY_STATE = STATE_PAUSE;
		totalAudioLength = mediaPlayer.getDuration();
		if (mOnPlayingListener != null) {
			mOnPlayingListener.onPlaying(mMusicInfo, 0, totalAudioLength);
			mOnPlayingListener.onPlayStateChanged(PLAY_STATE);
			mOnPlayingListener.onPrepared(mMusicInfo);
		}
	}

	/**
	 * 音频播放完成,直接变为stop,继续播放下一首还是就此停止自行判断.再次播放需要prepare
	 */
	@Override public void onCompletion(MediaPlayer mediaPlayer) {
		if (mOnPlayingListener != null) {
			mOnPlayingListener.onPlaying(mMusicInfo, totalAudioLength, totalAudioLength);
		}
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
		}
		PLAY_STATE = STATE_STOP;
		if (mOnPlayingListener != null) {
			mOnPlayingListener.onPlayStateChanged(PLAY_STATE);
			mOnPlayingListener.onCompletion();
		}
	}

	@Override public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
		Utils.handleError(LOG_TAG, "what:" + what + "+extra: " + extra);
		if (mOnPlayingListener != null) {
			mOnPlayingListener.onError();
		}
		return false;
	}

	@Override public void onSeekComplete(MediaPlayer mediaPlayer) {
		if (mOnPlayingListener != null) {
			mOnPlayingListener.onSeekToCompleted();
		}
	}

	/**
	 * 用一个循环的Handler取得当前播放的位置
	 */
	Handler mHandler = new Handler();
	Runnable mRunnable = new Runnable() {
		@Override public void run() {
			if (PLAY_STATE == STATE_PLAYING) {
				if (mOnPlayingListener != null) {
					mOnPlayingListener.onPlaying(mMusicInfo, mMediaPlayer.getCurrentPosition(), totalAudioLength);
				}
				mHandler.postDelayed(mRunnable, 1000);
			}
		}
	};

	private OnPlayingListener mOnPlayingListener = null;

	public void setOnPlayingListener(OnPlayingListener onPlayingListener) {
		this.mOnPlayingListener = onPlayingListener;
	}
}
