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
	 * �������ʵ��View��ʼ��
	 * @return  RootView
	 */
	protected  abstract View initView();
	
	/**
	 * �����ʼ������
	 */
	public void initData(){
		
	}
	
}
