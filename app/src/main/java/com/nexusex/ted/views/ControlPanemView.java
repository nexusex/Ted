package com.nexusex.ted.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.nexusex.ted.R;

/**
 * Created by davinci42 on 2016/4/18.
 */
public class ControlPanemView extends RelativeLayout {
	public ControlPanemView(Context context) {
		this(context, null);
	}

	public ControlPanemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ControlPanemView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}

	public ControlPanemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initView();
	}

	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.widget_control_panel, this, true);
	}
}
