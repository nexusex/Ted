package com.nexusex.ted.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import com.nexusex.ted.R;

public class MainActivity extends BaseActivity {

	@Bind(R.id.tv) TextView mTv;

	@Override public int getContentViewResLayout() {
		return R.layout.activity_main;
	}

	@Override public void initActivity() {
		mTv.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, PlayingListActivity.class);
				startActivity(intent);
			}
		});
	}
}
