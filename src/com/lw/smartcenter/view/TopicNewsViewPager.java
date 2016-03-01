package com.lw.smartcenter.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class TopicNewsViewPager extends ViewPager {

	private static final String TAG = "TopicNewsViewPager";
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
			//Log.d(TAG, "dispatchTouchEvent  down");
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
			//Log.d(TAG, "�ƶ�");
			if (dx > dy) {
				// ˮƽ�ƶ�
				if (currentId == 0) {
					if (itemCount == 1) {
							// ֻ��һ��ͼ
						getParent().requestDisallowInterceptTouchEvent(
								false);
					} else {
						if (vectorX > 0) {
							// ����
							getParent().requestDisallowInterceptTouchEvent(
									false);
						} else {
							// ����
							getParent()
									.requestDisallowInterceptTouchEvent(true);
						}
					}
				} else if (currentId < itemCount - 1) {
					getParent().requestDisallowInterceptTouchEvent(true);
				} else {
					if (vectorX > 0) {
						// ����
						getParent().requestDisallowInterceptTouchEvent(true);
					} else {
						// ����
						getParent().requestDisallowInterceptTouchEvent(false);
					}

				}

			} else {
				// ��ֱ�˶�
				getParent().requestDisallowInterceptTouchEvent(false);
			}

			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			getParent().requestDisallowInterceptTouchEvent(false);
			break;

		default:
			break;
		}

		return super.dispatchTouchEvent(ev);
	}
	
	/*@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.d(TAG, " onTouchEvent ACTION_DOWN");
			break;

		case MotionEvent.ACTION_UP:
			Log.d(TAG, " onTouchEvent ACTION_UP");
			break;
		default:
			break;
		}
		
		return super.onTouchEvent(ev);
	}*/

}
