package com.nexusex.ted.playmusic;

/**
 * 音频播放类(Service和Activity来持有)
 */
public class LetUsPlay implements Play {
	public static final int STATE_PLAYING = 1;
	public static final int STATE_PAUSE = 2;
	public static final int STATE_STOP = 3;

	private int PLAY_STATE = STATE_STOP;

	private static LetUsPlay mLetUsPlay = null;

	private LetUsPlay() {
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

	@Override public void prepare() {
		PLAY_STATE = STATE_STOP;
		mOnPlayingListener.onPlayStateChanged(STATE_STOP);
	}

	@Override public void start() {
		PLAY_STATE = STATE_PLAYING;
		mOnPlayingListener.onPlayStateChanged(STATE_PLAYING);
	}

	@Override public void pause() {
		PLAY_STATE = STATE_PAUSE;
		mOnPlayingListener.onPlayStateChanged(STATE_PAUSE);
	}

	@Override public void stop() {
		PLAY_STATE = STATE_STOP;
		mOnPlayingListener.onPlayStateChanged(STATE_STOP);
	}

	@Override public void restart() {
	}

	@Override public int getPlayState() {
		return PLAY_STATE;
	}

	@Override public void getMedia() {

	}

	@Override public void setMedia() {

	}

	@Override public void setCurrentPosition(int position) {
	}

	private OnPlayingListener mOnPlayingListener = null;

	public void setOnPlayingListener(OnPlayingListener onPlayingListener) {
		this.mOnPlayingListener = onPlayingListener;
	}
}
