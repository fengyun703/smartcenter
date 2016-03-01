package com.lw.smartcenter.base;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lw.smartcenter.R;
import com.lw.smartcenter.ui.MainUI;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseTabControl implements OnClickListener {

	protected Context mContext;
	private View mRootView;
	protected ImageView mIb_menu;
	protected TextView mTv_title;
	private FrameLayout mTab_containter_content;
	protected ImageView mPic_list_or_grid;

	public BaseTabControl(Context context) {
		this.mContext = context;
		mRootView = initView(mContext);
	}

	/**
	 * ��ʼ��TabControl����������ģ�
	 * 
	 * @param context
	 * @return
	 */
	private View initView(Context context) {
		View view = View.inflate(context, R.layout.base_tabcontrol, null);
		mTv_title = (TextView) view.findViewById(R.id.tab_tv_title);
		mIb_menu = (ImageView) view.findViewById(R.id.tab_ib_menu);
		mPic_list_or_grid = (ImageView) view.findViewById(R.id.tab_pic_list_or_grid);
		mTab_containter_content = (FrameLayout) view
				.findViewById(R.id.tab_containter_content);
		// ������view��������
		mTab_containter_content.addView(initContentView(context));
		mIb_menu.setOnClickListener(this);
		return view;
	}

	/**
	 * ��ʼ������View
	 * 
	 * @param context
	 * @return
	 */
	protected abstract View initContentView(Context context);

	/**
	 * ��ʼ��view����
	 */
	public void initData() {

	}

	public View getRootView() {
		return this.mRootView;
	}

	public Context getContext() {
		return this.mContext;
	}

	@Override
	public void onClick(View v) {
		if (v == mIb_menu) {
			clickMenu();
		}
	}

	protected void clickMenu() {
		// ���ʱ�򿪻��ǹرղ˵�
		SlidingMenu menu = ((MainUI) mContext).getSlidingMenu();
		menu.toggle();// ����˵��Ǵ򿪵�ʱ��͹رգ������෴
	}

	/**
	 * �л��˵����������ิд
	 * 
	 * @param position
	 */
	public void switchMenu(int position) {
		
	}
	
	/**
	 * ����ʱ����ڴ�
	 */
	public void onDestroy(){
		
	}

}
