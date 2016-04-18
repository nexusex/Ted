package com.nexusex.ted.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import com.nexusex.ted.R;
import com.nexusex.ted.playmusic.MusicService;

public class MainActivity extends BaseActivity {

	@Bind(R.id.tv) TextView mTv;

	@Override public int getContentViewResLayout() {
		return R.layout.activity_main;
	}

	@Override public void initActivity() {
		startService(new Intent(this, MusicService.class));
		mTv.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, PlayMusicActivity.class);
				startActivity(intent);
			}
		});
	}
}
