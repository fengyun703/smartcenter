package com.lw.smartcenter.control.newscentermenu;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lw.smartcenter.base.BaseMenuControl;

public class InteractMenuControl extends BaseMenuControl {

	public InteractMenuControl(Context context) {
		super(context);
	}

	@Override
	protected View initView() {
		TextView tv = new TextView(mContext);
		tv.setText("互动的正文内容");
		return tv;
	}

}
