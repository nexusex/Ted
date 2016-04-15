package com.nexusex.ted.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

	public static final String BUNDLE_KEY_PLAYING_LIST_TITLE = "playing list title";
	public static final String BUNDLE_KEY_PLAYING_LIST_MUSICINFO_LIST = "musicinfo list";

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(getContentViewResLayout(), container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initFragment();
	}

	public abstract int getContentViewResLayout();

	public abstract void initFragment();
}
