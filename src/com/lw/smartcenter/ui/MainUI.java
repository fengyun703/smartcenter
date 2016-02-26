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
		// ȥ����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ������ҳ����
		setContentView(R.layout.main_contains);
		// ���ò˵�����
		setBehindContentView(R.layout.menu_contains);
		// ���slidingmenuʵ��
		SlidingMenu slidingMenu = getSlidingMenu();
		// ��������в˵�
		slidingMenu.setMode(SlidingMenu.LEFT);
		// ���ò˵���
		slidingMenu.setBehindWidth(DpAndPx.dip2px(this, 120));

		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// ������߲˵��ĵ���¼�������ȡ����
		// slidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);

		initFragment();
	}

	// ����Fragment
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
