package com.nexusex.ted.ui;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import com.nexusex.ted.R;
import com.nexusex.ted.bean.MusicInfo;
import com.nexusex.ted.manager.MusicInfoUtils;
import java.util.ArrayList;

public class PlayingListActivity extends BaseMusicActivity {

	private static final String TAG = "PlayingListActivity";

	@Bind(R.id.tb) Toolbar mToolbar;

	@Override public int getContentViewResLayout() {
		return R.layout.activity_playing_list;
	}

	@Override public void initActivity() {
		setSupportActionBar(mToolbar);

		ArrayList<MusicInfo> musicInfoList = MusicInfoUtils.getMusicInfos(this);

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
}
