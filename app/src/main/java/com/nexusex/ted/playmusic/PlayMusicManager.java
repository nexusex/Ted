package com.nexusex.ted.playmusic;

import com.nexusex.ted.bean.MusicInfo;

/**
 * 由这个类来操纵播放列表,包括播放列表的所有播放属性(播放顺序,上次播放的音乐)
 * 和正在播放音乐的所有属性(播放进度,播放状态,所有信息)
 */
public class PlayMusicManager implements PlayList, LetUsPlay.OnPlayingListener {

	//当前正在播放的音乐(默认为上次播放的音乐,没有则为空)
	private MusicInfo mMusicInfo;

	private LetUsPlay mLetUsPlay;

	//当前的循环模式(默认为上次记录的循环模式,没有则为列表循环a)
	private int LOOP_STATE;
	public static final int LIST_LOOP = 1;//列表循环
	public static final int SINGLE_LOOP = 2;//单曲循环
	public static final int RANDOM_LOOP = 3;//随机循环

	//当前的播放模式
	private int PLAY_STATE;
	public static final int STATE_PLAYING = 1;//正在播放
	public static final int STATE_PREPARING = 2;//正在准备
	public static final int STATE_PAUSE = 3;//暂停
	public static final int STATE_STOP = 4;//停止(重启需要prepare)

	private volatile static PlayMusicManager mPlayMusicManager = null;

	private PlayMusicManager() {
		mLetUsPlay = LetUsPlay.getInstance();
		mLetUsPlay.setOnPlayingListener(this);
		mLetUsPlay.initPlayer();
	}

	public static PlayMusicManager getInstance() {
		if (mPlayMusicManager == null) {
			synchronized (PlayMusicManager.class) {
				if (mPlayMusicManager == null) {
					mPlayMusicManager = new PlayMusicManager();
				}
			}
		}
		return mPlayMusicManager;
	}

	@Override public void play() {
		if (mLetUsPlay.getPlayState() == LetUsPlay.STATE_RELEASE) {
			mLetUsPlay.reset();
		}
		if (PLAY_STATE == STATE_PLAYING || PLAY_STATE == STATE_PREPARING) {
			return;
		}
		if (PLAY_STATE == STATE_STOP) {
			//mLetUsPlay.prepare();
			if (mOnPlayListListener != null) {
				mOnPlayListListener.onPlayStateChanged(STATE_STOP, STATE_PREPARING);
			}
			PLAY_STATE = STATE_PREPARING;
		} else {
			mLetUsPlay.start();
			if (mOnPlayListListener != null) {
				mOnPlayListListener.onPlayStateChanged(STATE_PAUSE, STATE_PLAYING);
			}
			PLAY_STATE = STATE_PLAYING;
		}
	}

	@Override public void pause() {

	}

	@Override public void previousMusic() {

	}

	@Override public void nextMusic() {

	}

	@Override public void switchLoopStyle() {

	}

	@Override public void star() {

	}

	@Override public void seekToPosition(long targetPosition) {

	}

	@Override public void destroy() {
		mLetUsPlay.release();
	}

	/**
	 * 获取正在播放的音乐
	 */
	public MusicInfo getMusicInfo() {
		if (mMusicInfo == null) {
			throw new NullPointerException("the current music is null!");
		}
		return mMusicInfo;
	}

	/**
	 * 获取当前循环模式
	 */
	public int getLoopState() {
		if (LOOP_STATE != LIST_LOOP && LOOP_STATE != SINGLE_LOOP && LOOP_STATE != RANDOM_LOOP) {
			throw new IllegalArgumentException("the loop state is invalid");
		}
		return LOOP_STATE;
	}

	/**
	 * 获取当前播放模式
	 */
	public int getPlayState() {
		if (PLAY_STATE != STATE_PLAYING && PLAY_STATE != STATE_PAUSE && PLAY_STATE != STATE_PREPARING) {
			throw new IllegalArgumentException("the play state is invalid");
		}
		return PLAY_STATE;
	}

	@Override public void onPlayStateChanged(int playState) {

	}

	@Override public void onPlaying(MusicInfo musicInfo, long currentPosition, long completeLength) {

	}

	@Override public void onPrepared(MusicInfo musicInfo) {
		mLetUsPlay.start();
		if (mOnPlayListListener != null) {
			mOnPlayListListener.onPlayStateChanged(STATE_PREPARING, STATE_PLAYING);
		}
		PLAY_STATE = STATE_PLAYING;
	}

	@Override public void onCompletion() {

	}

	@Override public void onError() {

	}

	@Override public void onSeekToCompleted() {

	}

	private OnPlayListListener mOnPlayListListener;

	public void setOnPlayListListener(OnPlayListListener onPlayListListener) {
		this.mOnPlayListListener = onPlayListListener;
	}

	public interface OnPlayListListener {
		/**
		 * 循环模式改变
		 */
		void onLoopStateChanged(int oldLoopState, int newLoopState);

		/**
		 * 播放列表改变
		 */
		void onPlayListChanged();

		/**
		 * 播放状态改变
		 */
		void onPlayStateChanged(int oldPlayState, int newPlayState);

		/**
		 * 播放进度变化
		 */
		void onPlaying(MusicInfo musicInfo, long currentPosition, long totalLength);

		/**
		 * 等待prepare
		 */
		void showWaitingForPrepareView();

		/**
		 * prepare完成
		 */
		void hideWaitingForPrepareView();

		/**
		 * 播放发生错误
		 */
		void onError();
	}
}
