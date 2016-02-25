package com.lw.smartcenter.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TopicNewsViewPager extends ViewPager {

	private int downx;
	private int downy;

	public TopicNewsViewPager(Context context) {
		super(context);
	}

	public TopicNewsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		int currentId = this.getCurrentItem();
		int itemCount = this.getAdapter().getCount();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			downx = (int) ev.getRawX();
			downy = (int) ev.getRawY();
			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_MOVE:
			int movex = (int) ev.getRawX();
			int movey = (int) ev.getRawY();
			int vectorX = movex - downx;
			int dx = Math.abs(vectorX);
			float dy = Math.abs(downy - movey);
			if (dx > dy) {
				// 水平移动
				if (currentId == 0) {
					if (itemCount == 1) {
							// 只有一张图
						getParent().requestDisallowInterceptTouchEvent(
								false);
					} else {
						if (vectorX > 0) {
							// 右移
							getParent().requestDisallowInterceptTouchEvent(
									false);
						} else {
							// 左移
							getParent()
									.requestDisallowInterceptTouchEvent(true);
						}
					}
				} else if (currentId < itemCount - 1) {
					getParent().requestDisallowInterceptTouchEvent(true);
				} else {
					if (vectorX > 0) {
						// 右移
						getParent().requestDisallowInterceptTouchEvent(true);
					} else {
						// 左移
						getParent().requestDisallowInterceptTouchEvent(false);
					}

				}

			} else {
				// 垂直运动
				getParent().requestDisallowInterceptTouchEvent(false);
			}

			break;
		case MotionEvent.ACTION_UP:

			break;

		default:
			break;
		}

		return super.dispatchTouchEvent(ev);
	}

}
