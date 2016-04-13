package com.nexusex.ted;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;

/**
 * Created by davinci42 on 2016/4/13.
 */
public class PermissionRationaleDialog extends Dialog {
	public PermissionRationaleDialog(Context context) {
		super(context);
	}

	public void setPermissionType(String permission) {
		switch (permission) {
			case Manifest.permission.READ_EXTERNAL_STORAGE:
				// update UI, setText()
				break;
			case Manifest.permission.WRITE_EXTERNAL_STORAGE:
				// same
				break;
		}
	}
}
