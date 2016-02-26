package com.lw.smartcenter.view;

import android.animation.Animator.AnimatorListener;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lw.smartcenter.R;

public class RefreshListView extends ListView {

	private static final int STATUS_DOWN_REFRESH = 0;
	private static final int STATUS_REFRESHING = 1;
	private static final int STATUS_RELEASE_REFRESH = 2;
	private static final String TAG = "RefreshListView";

	private LinearLayout mHeaderLayout;
	private ProgressBar header_pb;
	private RelativeLayout header_refresh_layout;
	private ImageView header_iv_arrow;
	private TextView header_refresh_content_tv;
	private TextView header_refresh_time_tv;

	private LinearLayout mFooterLayout;
	private ProgressBar footer_pb;
	private TextView footer_content_tv;

	private View mCustomLayout;
	private int mFooterHeight;
	private int mHeaderHeight;
	private int mFooterCurrentPadding;
	private int mHeaderCurrentPadding;
	private int mCurrentStatus;

	private int downx;
	private int downy;
	private boolean mIsShowRefresh;
	private RefreshListener mRefreshListener;

	// private int mInvalidDy; //用来记录refreshView出现时，手指滑动的距离

	public RefreshListView(Context context) {
		this(context, null);
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		initialHeader();
		initialFooter();
		initialData();
	}

	private void initialData() {
		mCurrentStatus = STATUS_DOWN_REFRESH;
		mIsShowRefresh = false;
	}

	private void initialFooter() {
		mFooterLayout = (LinearLayout) View.inflate(getContext(),
				R.layout.news_menu_list_footer, null);
		footer_pb = (ProgressBar) mFooterLayout.findViewById(R.id.footer_pb);
		footer_content_tv = (TextView) mFooterLayout
				.findViewById(R.id.footer_content_tv);
		this.addFooterView(mFooterLayout);
		mFooterLayout.measure(0, 0);
		mFooterHeight = mFooterLayout.getMeasuredHeight();
		mFooterLayout.setPadding(0, -mFooterHeight, 0, 0);
		mFooterCurrentPadding = -mFooterHeight;
		// System.out.println(" mFooterPadding = " + mFooterPadding);
	}

	private void initialHeader() {
		mHeaderLayout = (LinearLayout) View.inflate(getContext(),
				R.layout.news_menu_list_header, null);
		header_pb = (ProgressBar) mHeaderLayout.findViewById(R.id.header_pb);
		header_refresh_layout = (RelativeLayout) mHeaderLayout
				.findViewById(R.id.header_refresh_layout);
		header_iv_arrow = (ImageView) mHeaderLayout
				.findViewById(R.id.header_iv_arrow);
		header_refresh_content_tv = (TextView) mHeaderLayout
				.findViewById(R.id.header_refresh_content_tv);
		header_refresh_time_tv = (TextView) mHeaderLayout
				.findViewById(R.id.header_refresh_time_tv);
		this.addHeaderView(mHeaderLayout);

		header_refresh_layout.measure(0, 0);
		mHeaderHeight = header_refresh_layout.getMeasuredHeight();
		mHeaderLayout.setPadding(0, -mHeaderHeight, 0, 0);
		mHeaderCurrentPadding = -mHeaderHeight;
		// System.out.println(" mHeaderPadding = " + mHeaderPadding);
	}

	public void addCustomHeadView(View view) {
		mCustomLayout = view;
		mHeaderLayout.addView(view);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			downx = (int) ev.getX();
			downy = (int) ev.getY();
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// System.out.println( "firstvisible " + getFirstVisiblePosition()
			// );

			break;
		case MotionEvent.ACTION_MOVE:
			int movex = (int) ev.getX();
			int movey = (int) ev.getY();
			int dx = movex - downx;
			int dy = movey - downy;
			// Log.d(TAG, " downy =    "+downy +", dy =  "+ dy);
			if (dy > 0) {

				// 下拉操作。
				if (mCustomLayout != null) {
					int[] customLocInW = new int[2];
					mCustomLayout.getLocationInWindow(customLocInW);
					int[] lLocInW = new int[2];
					getLocationInWindow(lLocInW);
					// Log.d(TAG, " customLocInWy =  "+ customLocInW[1]);
					if (lLocInW[1] > customLocInW[1]) {
						// 当mCustomLayout不在屏幕顶端，不响应下拉刷新
						// Log.d(TAG,
						// customLocInW[1]);
						return super.onTouchEvent(ev);
					}
				}

				if (getFirstVisiblePosition() == 0) {
					// System.out.println( "firstvisible " +
					// getFirstVisiblePosition() );

					if (!mIsShowRefresh) {
						// 显示刷新界面时，修改downy
						downy = (int) ev.getY();
						mIsShowRefresh = true;
					}

					dy = movey - downy;
					mHeaderCurrentPadding = dy - mHeaderHeight;
					mHeaderLayout.setPadding(0, mHeaderCurrentPadding, 0, 0);
					/*
					 * Log.d(TAG, " mHeaderCurrentPadding =    " +
					 * mHeaderCurrentPadding);
					 */
					if (mHeaderCurrentPadding > 0
							&& mCurrentStatus != STATUS_RELEASE_REFRESH) {
						// 当header更新界面完全显示时,状态变成STATUS_RELEASE_REFRESH
						mCurrentStatus = STATUS_RELEASE_REFRESH;
						Log.d(TAG, " 释放刷新状态   ");
						setRefreshUi();

					} else if (mHeaderCurrentPadding <= 0
							&& mCurrentStatus != STATUS_DOWN_REFRESH) {
						// 当header更新界面不完全显示时,
						mCurrentStatus = STATUS_DOWN_REFRESH;
						Log.d(TAG, " 下拉刷新状态   ");
						setRefreshUi();
					}

					return true;
				}

				/*
				 * int[] customLocInS = new int[2];
				 * mCustomLayout.getLocationOnScreen(customLocInS);
				 * System.out.print("RefreshListView   Window:  " +
				 * customLocInW[0]+",   "+ customLocInW[1]);
				 * System.out.println(".  OnScreen:  " +
				 * customLocInS[0]+",    "+ customLocInS[1]); int[] lLocInW =
				 * new int[2]; getLocationInWindow(lLocInW); int[] lLocInS = new
				 * int[2]; getLocationOnScreen(lLocInS);
				 * System.out.print("ListView   Window:  " + lLocInW[0]+",   "+
				 * lLocInW[1]); System.out.println(".  OnScreen:  " +
				 * lLocInS[0]+",    "+ lLocInS[1]);
				 */

			}

			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:

			if (mCurrentStatus == STATUS_RELEASE_REFRESH) {
				// 释放刷新，需要更新数据
				int end = 0;
				smoothMove(mHeaderCurrentPadding, end, new AnimatorListener() {
					
					@Override
					public void onAnimationStart(Animator animation) {
					}
					@Override
					public void onAnimationRepeat(Animator animation) {
					}
					@Override
					public void onAnimationEnd(Animator animation) {
						mCurrentStatus = STATUS_REFRESHING;
						setRefreshUi();
						if(mRefreshListener != null){
							mRefreshListener.onRefresh();
						}
					}
					@Override
					public void onAnimationCancel(Animator animation) {
					}
				});
				
			} else if (mCurrentStatus == STATUS_DOWN_REFRESH) {
				// 下拉刷新状态释放，直接回复原样。
				int end = -mHeaderHeight;
				smoothMove(mHeaderCurrentPadding, end, null);
				//完成后，不在显示刷新界面
				mIsShowRefresh = false;
			}
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	public interface RefreshListener{
		void onRefresh();
		void  onLoadMore();
	}

	private void smoothMove(int start, int end, AnimatorListener listener) {
		ValueAnimator animator = ValueAnimator.ofInt(start, end);
		animator.setDuration(300);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				mHeaderCurrentPadding = (Integer) animation.getAnimatedValue();
				mHeaderLayout.setPadding(0, mHeaderCurrentPadding, 0, 0);
			}
		});
		animator.start();
		if(listener != null){
			animator.addListener(listener);
		}
	}

	//更新状态后刷新Refresh界面
	private void setRefreshUi() {
		switch (mCurrentStatus) {
		case STATUS_DOWN_REFRESH:
			// 下拉刷新界面
			header_iv_arrow.clearAnimation();
			header_pb.setVisibility(View.INVISIBLE);
			header_refresh_content_tv.setText("下拉刷新");
			break;
		case STATUS_REFRESHING:
			// 正在刷新界面
			header_iv_arrow.setVisibility(View.INVISIBLE);
			header_pb.setVisibility(View.VISIBLE);
			header_iv_arrow.clearAnimation();
			header_refresh_content_tv.setText("正在更新");
			break;
		case STATUS_RELEASE_REFRESH:
			// 释放刷新界面
			header_iv_arrow.setVisibility(View.VISIBLE);
			Animation ra = new RotateAnimation(0, 180,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			ra.setDuration(300);
			ra.setFillAfter(true);
			header_iv_arrow.startAnimation(ra);
			header_pb.setVisibility(View.INVISIBLE);
			header_refresh_content_tv.setText("释放刷新");
			break;

		default:
			break;
		}
	}
	
	public void setRefreshListener( RefreshListener listener){
		mRefreshListener = listener;
	}
	
	//数据更新完成后，回复界面
	public void setRefreshEnd(){
		mIsShowRefresh = false;
		int end = -mHeaderHeight;
		smoothMove(mHeaderCurrentPadding, end, null);
	}

}
