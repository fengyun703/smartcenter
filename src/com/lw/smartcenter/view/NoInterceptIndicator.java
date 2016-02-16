package com.lw.smartcenter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.viewpagerindicator.TabPageIndicator;

public class NoInterceptIndicator extends TabPageIndicator {

	public NoInterceptIndicator(Context context) {
		super(context);
	}

	public NoInterceptIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(ev);
	}

}
