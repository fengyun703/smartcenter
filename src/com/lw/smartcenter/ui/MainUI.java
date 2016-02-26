package com.lw.smartcenter.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.lw.smartcenter.R;
import com.lw.smartcenter.frament.ContentFragment;
import com.lw.smartcenter.frament.MenuFragment;
import com.lw.smartcenter.utils.DpAndPx;

public class MainUI extends SlidingFragmentActivity {

	private static final String CONTENTTAG = "contentfragment";
	private static final String MENUTAG = "menufragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置主页布局
		setContentView(R.layout.main_contains);
		// 设置菜单布局
		setBehindContentView(R.layout.menu_contains);
		// 获得slidingmenu实例
		SlidingMenu slidingMenu = getSlidingMenu();
		// 设置左边有菜单
		slidingMenu.setMode(SlidingMenu.LEFT);
		// 设置菜单宽
		slidingMenu.setBehindWidth(DpAndPx.dip2px(this, 120));

		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// 这会抢走菜单的点击事件，必须取消。
		// slidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);

		initFragment();
	}

	// 加载Fragment
	private void initFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.content_contains, new ContentFragment(),
				CONTENTTAG);
		transaction.replace(R.id.menu_contains, new MenuFragment(), MENUTAG);
		transaction.commit();
	}

	public MenuFragment getMenuFragment() {
		FragmentManager fm = getSupportFragmentManager();
		return (MenuFragment) fm.findFragmentByTag(MENUTAG);
	}

	public ContentFragment getContentFragmentt() {
		FragmentManager fm = getSupportFragmentManager();
		return (ContentFragment) fm.findFragmentByTag(CONTENTTAG);
	}
}
