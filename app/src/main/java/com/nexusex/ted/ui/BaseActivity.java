package com.nexusex.ted.ui;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.nexusex.ted.views.PermissionRationaleDialog;

public abstract class BaseActivity extends AppCompatActivity {

	private int mRequestCode;
	private String mRequestPermission;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getContentViewResLayout());
		ButterKnife.bind(this);
		initActivity();
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

	public boolean checkIfHavePermission(String permission) {
		return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
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

	public abstract int getContentViewResLayout();

	public abstract void initActivity();
}
