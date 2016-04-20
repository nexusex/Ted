package com.nexusex.ted.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import com.nexusex.ted.R;
import com.nexusex.ted.bean.MusicInfo;
import com.nexusex.ted.manager.MusicInfoUtils;
import java.util.ArrayList;

public class PlayingListActivity extends BaseMusicActivity {

	private static final String TAG = "PlayingListActivity";

	private static final int REQUEST_CODE_STORAGE = 0;

	@Bind(R.id.tb) Toolbar mToolbar;

	@Override public int getContentViewResLayout() {
		return R.layout.activity_playing_list;
	}

	@Override public void initActivity() {

		if (checkIfHavePermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
			initView();
		} else {
			requestPermission(REQUEST_CODE_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
		}
	}

	private void initView() {

		setSupportActionBar(mToolbar);

		ArrayList<MusicInfo> musicInfoList =
			(ArrayList<MusicInfo>) MusicInfoUtils.arrangeMusicInfoListByTitle(MusicInfoUtils.getMusicInfos(this));

		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle("Fake Playing List");
		}

		String tempTitle = "Title";
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		if (getSupportFragmentManager().findFragmentByTag(tempTitle) == null) {
			transaction.add(R.id.fl_container, PlayingListFragment.newInstance(tempTitle, musicInfoList), tempTitle);
		} else {
			transaction.replace(R.id.fl_container, PlayingListFragment.newInstance(tempTitle, musicInfoList),
				tempTitle);
		}

		transaction.commit();
	}

	@Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
		@NonNull int[] grantResults) {
		if (requestCode == REQUEST_CODE_STORAGE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				initView();
			} else {
				// show some dialog of toast
			}
		}
	}
}
