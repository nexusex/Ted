package com.nexusex.ted.ui;

import butterknife.Bind;
import com.nexusex.ted.R;

public class PlayingListActivity extends BaseMusicActivity {

	@Bind(R.id.toolbar) android.widget.Toolbar mToolbar;

	@Override public int getContentViewResLayout() {
		return R.layout.activity_playing_list;
	}

	@Override public void initActivity() {
		setActionBar(mToolbar);

		mToolbar.setTitle("Fake Playing List");
	}
}
