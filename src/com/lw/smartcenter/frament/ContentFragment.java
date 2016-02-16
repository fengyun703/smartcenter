package com.lw.smartcenter.frament;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lw.smartcenter.R;
import com.lw.smartcenter.base.BaseFragment;
import com.lw.smartcenter.base.BaseTabControl;
import com.lw.smartcenter.control.tab.GovAffairsTabControl;
import com.lw.smartcenter.control.tab.HomeTabControl;
import com.lw.smartcenter.control.tab.NewsCenterTabControl;
import com.lw.smartcenter.control.tab.SettingTabControl;
import com.lw.smartcenter.control.tab.SmartServiceTabControl;
import com.lw.smartcenter.ui.MainUI;
import com.lw.smartcenter.view.NoScrollViewPager;

public class ContentFragment extends BaseFragment {

	@ViewInject(R.id.content_rg)
	private RadioGroup content_rg;

	@ViewInject(R.id.content_vp)
	private NoScrollViewPager content_vp;

	private List<BaseTabControl> tabControls;

	protected int mCurrentTab;

	@Override
	protected View initView() {
		View view = View.inflate(mActivity, R.layout.content_fragment, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	protected void initData() {

		// ��ʼ��viewpager��list����
		tabControls = new ArrayList<BaseTabControl>(5);
		tabControls.add(new HomeTabControl(mActivity));
		tabControls.add(new NewsCenterTabControl(mActivity));
		tabControls.add(new SmartServiceTabControl(mActivity));
		tabControls.add(new GovAffairsTabControl(mActivity));
		tabControls.add(new SettingTabControl(mActivity));

		// ����ViewPager����������
		//System.out.println(content_vp + "    " + content_rg);
		content_vp.setAdapter(new TabViewPaperAdapter());

		// ���Tab����SlideMenu�Ļ���ģʽ����ʾָ��ViewPagerҳ��
		content_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			SlidingMenu slidingMenu = ((MainUI) mActivity).getSlidingMenu();

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.content_tab_home:
					mCurrentTab = 0;
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
					break;

				case R.id.content_tab_news:
					mCurrentTab = 1;
					slidingMenu
							.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					break;

				case R.id.content_tab_smartsevice:
					mCurrentTab = 2;
					slidingMenu
							.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					break;

				case R.id.content_tab_goveraffairs:
					mCurrentTab = 3;
					slidingMenu
							.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					break;

				case R.id.content_tab_setting:
					mCurrentTab = 4;
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
					break;

				default:
					break;
				}
				content_vp.setCurrentItem(mCurrentTab);
			}
		});

		// Ĭ��ѡ���һ��tab
		content_rg.check(R.id.content_tab_home);
		mCurrentTab = 0;
		content_vp.setCurrentItem(mCurrentTab);
	}

	class TabViewPaperAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			if (tabControls != null) {
				return tabControls.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BaseTabControl control = tabControls.get(position);
			control.initData();
			View view = control.getRootView();
			container.addView(view);
			//System.out.println("��ʼ��" + position);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//System.out.println("ɾ��" + position);
			container.removeView((View) object);
		}

	}

	public void switchMenu(int position) {
		BaseTabControl control = tabControls.get(mCurrentTab);
		control.switchMenu(position);
	}

}
