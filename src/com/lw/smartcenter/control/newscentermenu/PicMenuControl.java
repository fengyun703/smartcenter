package com.lw.smartcenter.control.newscentermenu;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lw.smartcenter.R;
import com.lw.smartcenter.base.BaseMenuControl;
import com.lw.smartcenter.bean.NewsMenuListBean;
import com.lw.smartcenter.bean.NewsMenuListBean.NewsBean;
import com.lw.smartcenter.constants.Constants;
import com.lw.smartcenter.utils.SharePrefereceUtils;

public class PicMenuControl extends BaseMenuControl implements OnClickListener {

	protected static final String TAG = "PicMenuControl";
	private static final String KEY_PIC_LIST_OR_GRID = "listorgrid";
	private NewsMenuListBean mBean;
	private List<NewsBean> mNewsBean;
	private boolean isList;
	private ImageView mSwitch;
	private BitmapUtils mBitmapUtils;
	private ListView lv;
	private GridView gv;
	private PictureAdapter mAdapter;

	public PicMenuControl(Context context) {
		super(context);
	}

	@Override
	protected View initView() {
		RelativeLayout rl = (RelativeLayout) View.inflate(mContext,
				R.layout.pic_menu_pic, null);
		lv = (ListView) rl.findViewById(R.id.pic_listview);
		gv = (GridView) rl.findViewById(R.id.pic_gridview);
		isList = SharePrefereceUtils.getBoolean(mContext, KEY_PIC_LIST_OR_GRID,
				true);

		lv.setVisibility(isList ? View.VISIBLE : View.INVISIBLE);
		gv.setVisibility(isList ? View.INVISIBLE : View.VISIBLE);
		return rl;
	}

	@Override
	public void initData() {
		mBitmapUtils = new BitmapUtils(mContext);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, Constants.PIC_URI,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.d(TAG, "Õ¯¬Á∑√Œ ≥…π¶");
						String json = responseInfo.result;
						processData(json);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Log.d(TAG, "Õ¯¬Á∑√Œ  ß∞‹");
						Toast.makeText(mContext, "Õ¯¬Á∑√Œ  ß∞‹£∫" + msg,
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	protected void processData(String json) {
		Gson gson = new Gson();
		mBean = gson.fromJson(json, NewsMenuListBean.class);
		// Log.d(TAG, mBean.data.news.get(2).title);
		mNewsBean = mBean.data.news;
		if (mAdapter == null) {
			mAdapter = new PictureAdapter();
		}
		lv.setAdapter(mAdapter);
		gv.setAdapter(mAdapter);
		mSwitch.setImageResource(isList ? R.drawable.icon_pic_list_type
				: R.drawable.icon_pic_grid_type);
	}

	private class PictureAdapter extends BaseAdapter {

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
			ViewHold hold = null;
			if (convertView == null) {
				convertView = View.inflate(mContext, R.layout.pic_item, null);
				hold = new ViewHold();
				hold.tv = (TextView) convertView.findViewById(R.id.pic_item_tv);
				hold.iv = (ImageView) convertView
						.findViewById(R.id.pic_item_iv);
				convertView.setTag(hold);
			} else {
				hold = (ViewHold) convertView.getTag();
			}
			NewsBean newsBean = mNewsBean.get(position);
			mBitmapUtils.display(hold.iv, newsBean.listimage);
			hold.tv.setText(newsBean.title);
			return convertView;
		}

	}

	public void setListOrGridSwich(ImageView mPic_list_or_grid) {
		mSwitch = mPic_list_or_grid;
		mSwitch.setOnClickListener(this);
	}

	class ViewHold {
		ImageView iv;
		TextView tv;
	}

	@Override
	public void onClick(View v) {
		isList = !isList;
		mSwitch.setImageResource(isList ? R.drawable.icon_pic_list_type
				: R.drawable.icon_pic_grid_type);
		lv.setVisibility(isList ? View.VISIBLE : View.INVISIBLE);
		gv.setVisibility(isList ? View.INVISIBLE : View.VISIBLE);
		SharePrefereceUtils.setBoolean(mContext, KEY_PIC_LIST_OR_GRID, isList);
	}

	@Override
	public void onDestroy() {
		if (mBitmapUtils != null) {
			mBitmapUtils.clearMemoryCache();
			mBitmapUtils.closeCache();
			mBitmapUtils = null;
		}
	}

}
