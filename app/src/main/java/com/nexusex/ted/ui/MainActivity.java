package com.nexusex.ted.ui;

import android.view.View;
import butterknife.Bind;
import com.nexusex.ted.R;
import com.nexusex.ted.views.ItemInfoView;

public class MainActivity extends BaseMusicActivity {

	@Bind(R.id.iiv) ItemInfoView mItemInfoView;

	@Override public int getContentViewResLayout() {
		return R.layout.activity_main;
	}

	@Override public void initActivity() {
		mItemInfoView.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				mItemInfoView.updateViewState(!mItemInfoView.getViewState());
			}
		});
	}
}
