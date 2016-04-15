package com.nexusex.ted.ui;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import butterknife.Bind;
import com.nexusex.ted.R;
import com.nexusex.ted.bean.MusicInfo;
import com.nexusex.ted.manager.MusicInfoUtils;
import java.util.ArrayList;

public class PlayingListActivity extends BaseMusicActivity {

	@Bind(R.id.tb) Toolbar mToolbar;
	@Bind(R.id.fl_container) FrameLayout mFlContainer;

	@Override public int getContentViewResLayout() {
		return R.layout.activity_playing_list;
	}

	@Override public void initActivity() {

		setSupportActionBar(mToolbar);

		ArrayList<MusicInfo> musicInfoList = MusicInfoUtils.getMusicInfos(this);

		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle("Fake Playing List");
		}

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.fl_container, PlayingListFragment.newInstance("title", musicInfoList));
		transaction.commit();
	}
}
