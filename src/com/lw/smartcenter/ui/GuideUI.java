package com.lw.smartcenter.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lw.smartcenter.R;
import com.lw.smartcenter.utils.DpAndPx;
import com.lw.smartcenter.utils.SharePrefereceUtils;

public class GuideUI extends Activity {

	@ViewInject(R.id.guide_vp)
	private ViewPager guide_vp;

	@ViewInject(R.id.guide_ll_dots)
	private LinearLayout guide_ll_dots;

	@ViewInject(R.id.guide_bt)
	private Button guide_bt;

	@ViewInject(R.id.guide_move_dot)
	private View guide_move_dot;

	private List<ImageView> images;
	private int[] imagesId = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };

	protected int dotSpace;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.guide_ui);
		ViewUtils.inject(this);
		initial();
	}

	private void initial() {
		images = new ArrayList<ImageView>(3);
		View dot;
		ImageView image;
		LinearLayout.LayoutParams params;
		for (int i = 0; i < 3; i++) {
			// 初始化viewpager的图片
			image = new ImageView(this);
			image.setBackgroundResource(imagesId[i]);
			images.add(image);

			// 初始化dots
			dot = new View(this);
			dot.setBackgroundResource(R.drawable.guide_dot_nomal);
			params = new LayoutParams(DpAndPx.dip2px(this,  10), DpAndPx.dip2px(this,  10));
			if (i != 0) {
				params.leftMargin = DpAndPx.dip2px(this,  10);
			}
			guide_ll_dots.addView(dot, params);
		}

		// 设置Viewpapger 的adapter
		guide_vp.setAdapter(new MyAdapter());

		// 设置button显示和动态点移动
		guide_vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				if (position == images.size() - 1) {
					guide_bt.setVisibility(View.VISIBLE);
				} else {
					guide_bt.setVisibility(View.GONE);
				}
			}

			// 当viewpager滑动时，改变红点的位置
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				int left = (int) (position * dotSpace + dotSpace
						* positionOffset + 0.5);
				// guide_move_dot.setLeft(left);
				RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) guide_move_dot
						.getLayoutParams();
				params.leftMargin = left;
				guide_move_dot.setLayoutParams(params);
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		guide_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharePrefereceUtils.setBoolean(GuideUI.this, "isFinishedGuide",
						true);
				Intent intent = new Intent(GuideUI.this, MainUI.class);
				startActivity(intent);
				finish();
			}
		});

		// 监听布局完成，获得两点间距离
		guide_ll_dots.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						guide_ll_dots.getViewTreeObserver().removeGlobalOnLayoutListener(this);
						dotSpace = guide_ll_dots.getChildAt(1).getLeft()
								- guide_ll_dots.getChildAt(0).getLeft();
					}
				});

	}

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			if (images != null) {
				return images.size();
			}
			return 0;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView image = images.get(position);
			container.addView(image);
			return image;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}
}
