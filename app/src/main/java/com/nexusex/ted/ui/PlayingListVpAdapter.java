package com.nexusex.ted.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;

/**
 * Created by davinci42 on 2016/4/21.
 */
public class PlayingListVpAdapter extends FragmentStatePagerAdapter {

	private List<Fragment> mFragmentList;

	public PlayingListVpAdapter(FragmentManager fm, List<Fragment> fragmentList) {
		super(fm);
		mFragmentList = fragmentList;
	}

	@Override public Fragment getItem(int position) {
		return mFragmentList == null ? null : mFragmentList.get(position);
	}

	@Override public int getCount() {
		return mFragmentList == null ? 0 : mFragmentList.size();
	}
}
