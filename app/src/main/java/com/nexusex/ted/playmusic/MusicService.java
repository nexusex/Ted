package com.nexusex.ted.playmusic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.nexusex.ted.R;
import com.nexusex.ted.ui.MainActivity;

public class MusicService extends Service {

	private static final String TAG = "MusicService";
	private RemoteViews mBigRemoteViews;
	private RemoteViews mNormalRemoteViews;
	private NotificationManager mNotificationManager;
	private Notification mNotification;

	private static final String NOTIFICATION_ACTION = "notification_action";
	private static final int NOTIFICATION_NOTIFY_ID = 1;
	/*
	intent keys
	 */
	private static final String NOTIFICATION_ACTION_KEY = "notification_action_key";
	private static final int BIG_COVER = 1;
	private static final int NORMAL_COVER = 2;
	private static final int BIG_PREVIOUS = 3;
	private static final int NORMAL_PREVIOUS = 4;
	private static final int BIG_SWITCH = 5;
	private static final int NORMAL_SWITCH = 6;
	private static final int BIG_NEXT = 7;
	private static final int NORMAL_NEXT = 8;
	private static final int BIG_STAR = 9;
	private static final int BIG_SHUT = 10;
	private static final int BIG_PROGRESS = 11;

	@Override public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override public void onCreate() {
		super.onCreate();
		initRemoteViewsAction();
	}

	@Override public int onStartCommand(Intent intent, int flags, int startId) {
		String action = intent.getAction();
		int action_code = intent.getIntExtra(NOTIFICATION_ACTION_KEY, BIG_COVER);
		if (TextUtils.equals(action, NOTIFICATION_ACTION)) {
			switch (action_code) {
				case BIG_COVER:
					Intent intent1 = new Intent(this, MainActivity.class);
					intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent1);
					break;
				case BIG_PREVIOUS:

					break;
				case BIG_SWITCH:
					//notification更新示例,必须nofity
					mBigRemoteViews.setInt(R.id.big_notification_switch, "setImageResource", R.drawable.svg_play);
					mBigRemoteViews.setProgressBar(R.id.big_notification_progress, 100, 30, false);
					mNotificationManager.notify(NOTIFICATION_NOTIFY_ID, mNotification);
					break;
				case BIG_NEXT:

					break;
				case BIG_STAR:

					break;
				case BIG_SHUT:
					stopSelf();
					break;
				case NORMAL_COVER:

					break;
				case NORMAL_PREVIOUS:

					break;
				case NORMAL_SWITCH:

					break;
				case NORMAL_NEXT:

					break;
				default:
					break;
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void initRemoteViewsAction() {
		Intent intent = new Intent(this, MusicService.class);
		intent.setAction(NOTIFICATION_ACTION);

		//big notification的点击事件
		mBigRemoteViews = new RemoteViews(getPackageName(), R.layout.big_music_notification);
		intent.putExtra(NOTIFICATION_ACTION_KEY, BIG_COVER);
		PendingIntent bigCoverPi = PendingIntent.getService(this, BIG_COVER, intent, 0);
		mBigRemoteViews.setOnClickPendingIntent(R.id.big_notification_cover, bigCoverPi);

		intent.putExtra(NOTIFICATION_ACTION_KEY, BIG_PREVIOUS);
		PendingIntent bigPreviousPi = PendingIntent.getService(this, BIG_PREVIOUS, intent, 0);
		mBigRemoteViews.setOnClickPendingIntent(R.id.big_notification_previous, bigPreviousPi);

		intent.putExtra(NOTIFICATION_ACTION_KEY, BIG_SWITCH);
		PendingIntent bigSwitchPi =
			PendingIntent.getService(this, BIG_SWITCH, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBigRemoteViews.setOnClickPendingIntent(R.id.big_notification_switch, bigSwitchPi);

		intent.putExtra(NOTIFICATION_ACTION_KEY, BIG_NEXT);
		PendingIntent bigNextPi = PendingIntent.getService(this, BIG_NEXT, intent, 0);
		mBigRemoteViews.setOnClickPendingIntent(R.id.big_notification_next, bigNextPi);

		intent.putExtra(NOTIFICATION_ACTION_KEY, BIG_STAR);
		PendingIntent bigStarPi = PendingIntent.getService(this, BIG_STAR, intent, 0);
		mBigRemoteViews.setOnClickPendingIntent(R.id.big_notification_star, bigStarPi);

		intent.putExtra(NOTIFICATION_ACTION_KEY, BIG_SHUT);
		PendingIntent bigShutPi = PendingIntent.getService(this, BIG_SHUT, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		mBigRemoteViews.setOnClickPendingIntent(R.id.big_notification_shut, bigShutPi);

		//normal notification的点击事件
		mNormalRemoteViews = new RemoteViews(getPackageName(), R.layout.normal_music_notification);
		intent.putExtra(NOTIFICATION_ACTION_KEY, NORMAL_COVER);
		PendingIntent normalCoverPi = PendingIntent.getService(this, NORMAL_COVER, intent, 0);
		mBigRemoteViews.setOnClickPendingIntent(R.id.notification_cover, normalCoverPi);

		intent.putExtra(NOTIFICATION_ACTION_KEY, NORMAL_PREVIOUS);
		PendingIntent normalPreviousPi = PendingIntent.getService(this, NORMAL_PREVIOUS, intent, 0);
		mBigRemoteViews.setOnClickPendingIntent(R.id.notification_previous, normalPreviousPi);

		intent.putExtra(NOTIFICATION_ACTION_KEY, NORMAL_SWITCH);
		PendingIntent normalSwitchPi =
			PendingIntent.getService(this, NORMAL_SWITCH, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBigRemoteViews.setOnClickPendingIntent(R.id.notification_switch, normalSwitchPi);

		intent.putExtra(NOTIFICATION_ACTION_KEY, NORMAL_NEXT);
		PendingIntent normalNextPi = PendingIntent.getService(this, NORMAL_NEXT, intent, 0);
		mBigRemoteViews.setOnClickPendingIntent(R.id.notification_next, normalNextPi);

		//创建notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setContent(mNormalRemoteViews).setOngoing(true).setSmallIcon(R.drawable.svg_disc);
		mNotification = builder.build();
		mNotification.flags = Notification.FLAG_FOREGROUND_SERVICE;
		mNotification.bigContentView = mBigRemoteViews;
		startForeground(NOTIFICATION_NOTIFY_ID, mNotification);
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

}
