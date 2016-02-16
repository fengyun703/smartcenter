package com.lw.smartcenter.base;

import android.content.Context;
import android.view.View;

public abstract class BaseMenuControl {

	protected  Context mContext;
	protected View mRootView;
	public BaseMenuControl(Context context){
		mContext = context;
		mRootView = initView();
	}
	

	public Context getmContext() {
		return mContext;
	}


	public View getmRootView() {
		return mRootView;
	}


	/**
	 * 子类必须实现View初始化
	 * @return  RootView
	 */
	protected  abstract View initView();
	
	/**
	 * 子类初始化数据
	 */
	public void initData(){
		
	}
	
}
