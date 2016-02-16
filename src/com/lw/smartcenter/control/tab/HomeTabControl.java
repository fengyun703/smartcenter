package com.lw.smartcenter.control.tab;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.lw.smartcenter.base.BaseTabControl;

public class HomeTabControl extends BaseTabControl {

	public HomeTabControl(Context context) {
		super(context); 
	}

	@Override
	protected View initContentView(Context context) {
		TextView text = new TextView(context);
		text.setText("主页正文内容");
		text.setGravity(Gravity.CENTER);
		text.setTextColor(Color.RED);
		return text;
	}
	
	@Override
	public  void initData() {
		mIb_menu.setVisibility(View.GONE);
		mTv_title.setText("智慧中心");
	}

}
