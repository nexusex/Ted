package com.nexusex.ted.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import com.nexusex.ted.R;
import com.nexusex.ted.bean.MusicInfoList;
import com.nexusex.ted.manager.MusicInfoUtils;
import com.nexusex.ted.views.ControlPanemView;
import java.util.ArrayList;
import java.util.List;

public class PlayingListActivity extends BaseMusicActivity {

	private static final String TAG = "PlayingListActivity";

	private static final int REQUEST_CODE_STORAGE = 0;

	@Bind(R.id.tb) Toolbar mTb;
	@Bind(R.id.tl) TabLayout mTl;
	@Bind(R.id.vp) ViewPager mVp;
	@Bind(R.id.cpv) ControlPanemView mCpv;

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

		setSupportActionBar(mTb);

		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle("Fake Playing List");
		}

		//MusicInfoList musicInfoList = PlayListManager.getMusicInfoListByTitle(this, title);
		MusicInfoList musicInfoList = MusicInfoUtils.getAllMusicInfoList(this);

		PlayingListFragment allListFragment = PlayingListFragment.newInstance(musicInfoList);
		List<Fragment> fragmentList = new ArrayList<>();
		fragmentList.add(allListFragment);
		fragmentList.add(PlayingListFragment.newInstance(musicInfoList));
		fragmentList.add(PlayingListFragment.newInstance(musicInfoList));

		PlayingListVpAdapter vpAdapter = new PlayingListVpAdapter(getSupportFragmentManager(), fragmentList);
		mVp.setAdapter(vpAdapter);

		mTl.setupWithViewPager(mVp);
		for (int i = 0; i < fragmentList.size(); i++) {
			mTl.getTabAt(i).setText("List" + i);
		}
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
