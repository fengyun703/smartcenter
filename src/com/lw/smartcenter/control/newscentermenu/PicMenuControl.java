package com.lw.smartcenter.control.newscentermenu;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lw.smartcenter.base.BaseMenuControl;

public class PicMenuControl extends BaseMenuControl {

	public PicMenuControl(Context context) {
		super(context);
	}

	@Override
	protected View initView() {
		TextView tv = new TextView(mContext);
		tv.setText("图片的正文内容");
		return tv;
	}

}
