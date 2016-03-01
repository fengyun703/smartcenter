package com.lw.smartcenter.control.newscentermenu;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lw.smartcenter.R;
import com.lw.smartcenter.base.BaseMenuControl;
import com.lw.smartcenter.bean.NewsCenterBean.NewsChild;
import com.lw.smartcenter.bean.NewsCenterBean.NewsMenuBean;
import com.lw.smartcenter.control.newsmenulist.NewsMenuListControl;
import com.lw.smartcenter.ui.MainUI;
import com.viewpagerindicator.TabPageIndicator;

public class NewsMenuControl extends BaseMenuControl implements
		OnPageChangeListener {

	private static final String TAG = "NewsMenuControl";

	@ViewInject(R.id.newsmenu_vp)
	private ViewPager mNewsMenu_Vp;

	@ViewInject(R.id.newsmenu_indicator)
	private TabPageIndicator mNewsMenu_Indicator;
	
	@ViewInject(R.id.newsmenu_arr_iv)
	private ImageView newsmenu_arr_iv;
	private NewsMenuBean mNewsMenuBean;
	private List<NewsChild> newsDatas;
	private NewsMenuListControl mControl;

	public NewsMenuControl(Context context, NewsMenuBean bean) {
		super(context);
		mNewsMenuBean = bean;
		newsDatas = mNewsMenuBean.children;
	//	System.out.println("mNewsMenuBean  "+ mNewsMenuBean+", newsDatas=  "+newsDatas);
	}

	@Override
	protected View initView() {
		View view = View.inflate(mContext, R.layout.newsmenu_content, null);
		ViewUtils.inject(this, view);
		return view;
	}

	class NewsMenuAdapter extends PagerAdapter {

		

		@Override
		public int getCount() {
			if (newsDatas != null) {
				return newsDatas.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
		
			NewsChild bean = newsDatas.get(position);
			//System.out.println("NewsChild" + bean);
			mControl = new NewsMenuListControl(mContext, bean);
			View view = mControl.getmRootView();
			mControl.initData();
			container.addView(view);
			return view;
		}


		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			
			container.removeView((View) object);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			if (newsDatas != null) {
				NewsChild bean = newsDatas.get(position);
				return bean.title;
			}
			return super.getPageTitle(position);
		}

	}

	@Override
	public void initData() {
		// 设置ViewPager适配器
		mNewsMenu_Vp.setAdapter(new NewsMenuAdapter());
		// 设置开源类对象
		mNewsMenu_Indicator.setViewPager(mNewsMenu_Vp);
		// 监听ViewPager 页面变化
		mNewsMenu_Indicator.setOnPageChangeListener(this);
		/*System.out.println("newsDatas.size = " + newsDatas.size()+",   mNewsMenu_Vp = "+mNewsMenu_Vp
			+",   mNewsMenu_Indicator = "	+mNewsMenu_Indicator);*/
		
		newsmenu_arr_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			  int itemid = mNewsMenu_Vp.getCurrentItem();
			  mNewsMenu_Vp.setCurrentItem(++itemid);
			}
		});
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		SlidingMenu sm = ((MainUI) mContext).getSlidingMenu();
		sm.setTouchModeAbove(position == 0 ? SlidingMenu.TOUCHMODE_FULLSCREEN
				: SlidingMenu.TOUCHMODE_NONE);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
	
	@Override
	public void onDestroy() {
		if(mControl !=null){
			Log.d(TAG, "NewsMenuControl onDestroy  mControl ");
			mControl.onDestroy();
		}
		
	}

}
