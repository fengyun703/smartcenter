package com.lw.smartcenter.control.tab;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lw.smartcenter.base.BaseMenuControl;
import com.lw.smartcenter.base.BaseTabControl;
import com.lw.smartcenter.bean.NewsCenterBean;
import com.lw.smartcenter.bean.NewsCenterBean.NewsMenuBean;
import com.lw.smartcenter.constants.Constants;
import com.lw.smartcenter.control.newscentermenu.InteractMenuControl;
import com.lw.smartcenter.control.newscentermenu.NewsMenuControl;
import com.lw.smartcenter.control.newscentermenu.PicMenuControl;
import com.lw.smartcenter.control.newscentermenu.TopicMenuControl;
import com.lw.smartcenter.frament.MenuFragment;
import com.lw.smartcenter.ui.MainUI;

public class NewsCenterTabControl extends BaseTabControl {

	private NewsCenterBean mNewsCenterBean;
	private List<BaseMenuControl> mMenuControls;
	private List<NewsMenuBean> mNewsCenterDatas;
	private FrameLayout mContent_Contain;

	public NewsCenterTabControl(Context context) {
		super(context);
	}

	@Override
	protected View initContentView(Context context) {
		mContent_Contain = new FrameLayout(mContext);
		return mContent_Contain;
	}

	@Override
	public void initData() {
		mIb_menu.setVisibility(View.VISIBLE);
		//mTv_title.setText("����");

		mMenuControls = new ArrayList<BaseMenuControl>();

		HttpUtils http = new HttpUtils();
		String url = Constants.NEWSCENTER_URI;
		http.send(HttpRequest.HttpMethod.GET, url,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						System.out.println("���ݽ��ճɹ�");
						String result = responseInfo.result;
						processData(result);
						// System.out.println(result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						System.out.println("onFailur : " + msg);
						// Toast.makeText(mContext,
						// msg,Toast.LENGTH_SHORT).show();
					}
				});

	}

	protected void processData(String result) {
		Gson gson = new Gson();
		mNewsCenterBean = gson.fromJson(result, NewsCenterBean.class);
		mNewsCenterDatas = mNewsCenterBean.data;
		// ������������Menu����
		MainUI ui = (MainUI) mContext;
		MenuFragment fragment = ui.getMenuFragment();
		fragment.setDatas(mNewsCenterBean.data);

		// System.out.println(bean.data.get(2).title);
		//����MenuControl����
		NewsMenuBean bean;
		for (int i = 0; i < mNewsCenterDatas.size(); i++) {
			bean = mNewsCenterDatas.get(i);
			switch (bean.type) {
			case 1:
				NewsMenuControl newMenuControl = new NewsMenuControl(mContext,
						bean);
				mMenuControls.add(newMenuControl);
				break;
			case 10:
				TopicMenuControl topMenuControl = new TopicMenuControl(mContext);
				mMenuControls.add(topMenuControl);
				break;
			case 2:
				PicMenuControl picMenuControl = new PicMenuControl(mContext);
				mMenuControls.add(picMenuControl);
				break;
			case 3:
				InteractMenuControl interMenuControl = new InteractMenuControl(
						mContext);
				mMenuControls.add(interMenuControl);
				break;

			default:
				break;
			}
		}
		// ��������Ĭ��ѡ���0��menu
		switchMenu(0);
	}

	@Override
	public void switchMenu(int position) {
		mContent_Contain.removeAllViews();
		NewsMenuBean newsMenuBean = mNewsCenterDatas.get(position);
		BaseMenuControl control = mMenuControls.get(position);
		//System.out.println(control+ "     "+ position+"   "+ mContent_Contain+"    "+control.getmRootView());
		// ��������View
		mContent_Contain.addView(control.getmRootView());
		//������������
		control.initData();
		// ���ñ����ı�
		mTv_title.setText(newsMenuBean.title);
	}

}