package com.lw.smartcenter.control.newsmenulist;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lw.smartcenter.R;
import com.lw.smartcenter.base.BaseMenuControl;
import com.lw.smartcenter.bean.NewsCenterBean.NewsChild;
import com.lw.smartcenter.bean.NewsMenuListBean;
import com.lw.smartcenter.bean.NewsMenuListBean.NewsBean;
import com.lw.smartcenter.bean.NewsMenuListBean.TopnewsBean;
import com.lw.smartcenter.constants.Constants;
import com.lw.smartcenter.utils.SharePrefereceUtils;
import com.lw.smartcenter.view.RefreshListView;
import com.lw.smartcenter.view.RefreshListView.RefreshListener;
import com.lw.smartcenter.view.TopicNewsViewPager;

public class NewsMenuListControl extends BaseMenuControl implements
		OnPageChangeListener, OnTouchListener, RefreshListener {

	private static final long MAXDATESPAN = 2 * 1000 * 60;
	private static final String TAG = "NewsMenuListControl";
	private RefreshListView news_menu_listview;
	private NewsChild mBean;
	private NewsMenuListBean mNewsMenuListBean;
	private View mTopicView;
	private TopicNewsViewPager topic_vp;
	private TextView topic_title_tv;
	private LinearLayout topic_dots_container;
	private List<NewsBean> mNewsBean;
	private List<TopnewsBean> mTopnews;
	private BitmapUtils mBitmapUtils;
	private TopVPAdapter mViewPagerAdapter;
	private RefreshAdapter mListViewAdapter;
	private AutoTask mAotuTask;

	public NewsMenuListControl(Context context, NewsChild bean) {
		super(context);
		this.mBean = bean;
	}

	@Override
	protected View initView() {
		View view = View.inflate(mContext, R.layout.news_menu_list, null);
		news_menu_listview = (RefreshListView) view
				.findViewById(R.id.news_menu_listview);
		mTopicView = View.inflate(mContext, R.layout.header_topic_news, null);
		// 把自定义view加入header
		news_menu_listview.addCustomHeadView(mTopicView);

		topic_vp = (TopicNewsViewPager) mTopicView.findViewById(R.id.topic_vp);
		topic_title_tv = (TextView) mTopicView
				.findViewById(R.id.topic_title_tv);
		topic_dots_container = (LinearLayout) mTopicView
				.findViewById(R.id.topic_dots_container);

		topic_vp.setOnPageChangeListener(this);
		news_menu_listview.setRefreshListener(this);
		return view;
	}

	@Override
	public void initData() {
		String url = Constants.BASEURI + mBean.url;
		if (mBitmapUtils == null) {
			mBitmapUtils = new BitmapUtils(mContext);
		}
		// 使用缓存保存json数据
		String json = SharePrefereceUtils.getString(mContext, url);
		long datetime = SharePrefereceUtils.getLong(mContext, url + "_date");
		if (!TextUtils.isEmpty(json)) {
			processData(json);
			if (System.currentTimeMillis() - datetime < MAXDATESPAN) {
				System.out.println("NewsMenuListControl 不用网络加载");
				return;
			}
		}
		getDataFromWeb();

	}

	class AutoTask extends Handler implements Runnable {

		public void start() {
			stop();
			postDelayed(this, 2000);
		}

		public void stop() {
			removeCallbacks(this);
		}

		@Override
		public void run() {
			int id = topic_vp.getCurrentItem();
			if (id == topic_vp.getAdapter().getCount() - 1) {
				topic_vp.setCurrentItem(0);
			} else {
				topic_vp.setCurrentItem(++id);
			}
			postDelayed(this,2000);
		}

	}

	// 从网络获取数据
	private void getDataFromWeb() {
		HttpUtils http = new HttpUtils();
		final String url = Constants.BASEURI + mBean.url;
		http.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				System.out.println("listcontrol  网络访问正常");
				String json = responseInfo.result;
				processData(json);
				// 存储到缓存
				System.out.println("listcontrol  存储到缓存");
				SharePrefereceUtils.setString(mContext, url, json);
				SharePrefereceUtils.setLong(mContext, url + "_date",
						System.currentTimeMillis());
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				System.out.println("listcontrol  网络访问bu正常");
			}
		});

	}

	private void processData(String json) {
		Gson gson = new Gson();
		mNewsMenuListBean = gson.fromJson(json, NewsMenuListBean.class);
		// System.out.println(mNewsMenuListBean.data.title);
		mNewsBean = mNewsMenuListBean.data.news;
		mTopnews = mNewsMenuListBean.data.topnews;
		// System.out.println(mTopnews.get(0).title+"  , "+
		// mTopnews.get(0).topimage);
		// System.out.println(mNewsBean.get(0).title+"  , "+
		// mNewsBean.get(0).listimage);
		// System.out.println("mTopnews.size() ="+mTopnews.size());
		/*
		 * System.out.println("mNewsMenuListBean = " + mNewsMenuListBean +
		 * ",   mNewsBean =   " + mNewsBean + ", mTopnews= " + mTopnews);
		 */

		// 加载数据前清除dot；
		topic_dots_container.removeAllViews();
		// 动态添加点
		View dot;
		LinearLayout.LayoutParams params = new LayoutParams(5, 5);
		for (int i = 0; i < mTopnews.size(); i++) {
			dot = new View(mContext);
			if (i == 0) {
				params.leftMargin = 0;
				dot.setBackgroundResource(R.drawable.guide_dot_focus);
			} else {
				params.leftMargin = 10;
				dot.setBackgroundResource(R.drawable.guide_dot_nomal);
			}
			// System.out.println("加入点");
			topic_dots_container.addView(dot, params);
		}

		if (mViewPagerAdapter == null) {
			mViewPagerAdapter = new TopVPAdapter();
		}
		topic_vp.setAdapter(mViewPagerAdapter);

		if (mListViewAdapter == null) {
			mListViewAdapter = new RefreshAdapter();
		}
		news_menu_listview.setAdapter(mListViewAdapter);

		// 开启轮播
		if (mAotuTask == null) {
			mAotuTask = new AutoTask();
		}
		mAotuTask.start();
		// 控制轮播关闭
		topic_vp.setOnTouchListener(this);
	}

	class RefreshAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (mNewsBean != null) {
				return mNewsBean.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return mNewsBean.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			NewsBean bean = mNewsBean.get(position);
			ViewHold hold;
			if (convertView == null) {
				convertView = View.inflate(mContext,
						R.layout.news_menu_list_item, null);
				hold = new ViewHold();
				hold.list_item_content_text = (TextView) convertView
						.findViewById(R.id.list_item_content_text);
				hold.list_item_iv = (ImageView) convertView
						.findViewById(R.id.list_item_iv);
				hold.list_item_time_text = (TextView) convertView
						.findViewById(R.id.list_item_time_text);
				convertView.setTag(hold);
			} else {
				hold = (ViewHold) convertView.getTag();
			}

			hold.list_item_content_text.setText(bean.title);
			hold.list_item_time_text.setText(bean.pubdate);
			mBitmapUtils.display(hold.list_item_iv, bean.listimage);
			return convertView;
		}

	}

	class ViewHold {
		ImageView list_item_iv;
		TextView list_item_content_text;
		TextView list_item_time_text;

	}

	class TopVPAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			if (mTopnews != null) {
				return mTopnews.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView view = new ImageView(mContext);
			view.setBackgroundResource(R.drawable.home_scroll_default);
			view.setScaleType(ScaleType.FIT_XY);
			TopnewsBean bean = mTopnews.get(position);
			mBitmapUtils.display(view, bean.topimage);
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {

		// 修改点的状态
		int count = topic_dots_container.getChildCount();
		View view = null;
		for (int i = 0; i < count; i++) {
			view = topic_dots_container.getChildAt(i);
			view.setBackgroundResource(R.drawable.guide_dot_nomal);
		}
		view = topic_dots_container.getChildAt(position);
		view.setBackgroundResource(R.drawable.guide_dot_focus);

		// 更新topic 的标题
		TopnewsBean bean = mTopnews.get(position);
		topic_title_tv.setText(bean.title);

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// 控制轮播
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			//有触摸就停止轮播
			mAotuTask.stop();
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			//触摸完成就开启轮播
			mAotuTask.start();
			break;

		default:
			break;
		}
		return false;
	}

	//更新数据
	@Override
	public void onRefresh() {
		Log.d(TAG, "正在更新数据");
		
		//数据更新完成后，回复界面
		news_menu_listview.setRefreshEnd();
	}

	//加载更多
	@Override
	public void onLoadMore() {
		Log.d(TAG, "正在加载数据");
	}

}
