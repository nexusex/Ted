package com.nexusex.ted.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.nexusex.ted.R;

/**
 * Created by davinci42 on 2016/4/13.
 */
public class ItemInfoView extends LinearLayout {

	public ItemInfoView(Context context) {
		this(context, null);
	}

	public ItemInfoView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ItemInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}

	public ItemInfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.itemview_playing_list, this, true);
	}
}
