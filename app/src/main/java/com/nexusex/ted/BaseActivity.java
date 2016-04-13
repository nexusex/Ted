package com.nexusex.ted;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import com.nexusex.ted.playmusic.LetUsPlay;
import com.nexusex.ted.playmusic.OnPlayingListener;

/**
 * Activity基类
 * 持有音频播放类的实例,实现OnPlayingListener接口,可以通知所有activity实时的播放情况
 */
public class BaseActivity extends AppCompatActivity implements OnPlayingListener {

	public LetUsPlay mLetUsPlay;
	private int mRequestCode;
	private String mRequestPermission;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLetUsPlay = LetUsPlay.getInstance();
		mLetUsPlay.setOnPlayingListener(this);
	}

	@Override public void onPlayStateChanged(int playState) {

	}

	@Override public void onPlaying(int currentPosition, int completeLength) {

	}

	public void requestPermission(int requestCode, String permission) {
		if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

			if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
				mRequestCode = requestCode;
				mRequestPermission = permission;
				showRequestPermissionRationale(permission);
			} else {
				ActivityCompat.requestPermissions(this, new String[] { permission }, requestCode);
			}
		}
	}

	public void showRequestPermissionRationale(String permission) {

		PermissionRationaleDialog dialog = new PermissionRationaleDialog(this);
		dialog.setPermissionType(permission);
		dialog.setOnDismissListener(mOnDismissListener);
		dialog.show();
	}

	private DialogInterface.OnDismissListener mOnDismissListener = new DialogInterface.OnDismissListener() {
		@Override public void onDismiss(DialogInterface dialog) {
			requestPermission(mRequestCode, mRequestPermission);
		}
	};
}
