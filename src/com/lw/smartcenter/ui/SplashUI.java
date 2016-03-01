package com.lw.smartcenter.ui;

import com.lw.smartcenter.R;
import com.lw.smartcenter.R.id;
import com.lw.smartcenter.R.layout;
import com.lw.smartcenter.R.menu;
import com.lw.smartcenter.utils.SharePrefereceUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class SplashUI extends Activity {

	private ImageView splash_iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_ui);
		splash_iv = (ImageView) findViewById(R.id.splash_iv);

		// 欢迎页面动画
		Animation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		ra.setDuration(400);
		ra.setFillAfter(true);

		Animation sa = new ScaleAnimation(0f, 1f, 0f, 1f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		sa.setDuration(400);
		sa.setFillAfter(true);

		Animation aa = new AlphaAnimation(0f, 1f);
		aa.setDuration(400);
		aa.setFillAfter(true);

		AnimationSet set = new AnimationSet(false);
		set.addAnimation(ra);
		set.addAnimation(sa);
		set.addAnimation(aa);

		splash_iv.startAnimation(set);
		set.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				new Thread() {
					public void run() {
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						startNewUi();
					};
				}.start();
			}
		});
	
		
		
	}

	private void startNewUi() {
		boolean isFinishedGuide = SharePrefereceUtils.getBoolean(this,
				"isFinishedGuide", false);
		if (isFinishedGuide) {
			// 直接启动mainui
			Intent intent = new Intent(SplashUI.this, MainUI.class);
			startActivity(intent);
		} else {
			// 启动guideui
			Intent intent = new Intent(SplashUI.this, GuideUI.class);
			startActivity(intent);
		}

		finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


}
