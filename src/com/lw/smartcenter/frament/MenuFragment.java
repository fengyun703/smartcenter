package com.lw.smartcenter.frament;

import java.util.List;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lw.smartcenter.R;
import com.lw.smartcenter.base.BaseFragment;
import com.lw.smartcenter.bean.NewsCenterBean.NewsMenuBean;
import com.lw.smartcenter.ui.MainUI;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MenuFragment extends BaseFragment implements OnItemClickListener {

	private ListView mListView;
	private List<NewsMenuBean> mBeans;
	public int mCurrentMenu;
	private MenuAdapter menuAdapter;

	@Override
	protected View initView() {
		mListView = new ListView(mActivity);
		mListView.setBackgroundColor(Color.BLACK);
		mListView.setPadding(0, 40, 0, 0);
		mListView.setCacheColorHint(android.R.color.transparent);// 设置为透明
		//mListView.setSelector(android.R.color.transparent);
		 mListView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
		 //mListView.setFocusable(true);
		 
		mListView.setOnItemClickListener(this);
		return mListView;
	}

	public void setDatas(List<NewsMenuBean> datas) {
		mBeans = datas;
		mCurrentMenu = 0;
		menuAdapter = new MenuAdapter();
		mListView.setAdapter(menuAdapter);
	}

	class MenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (mBeans != null) {
				return mBeans.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if (mBeans != null) {
				return mBeans.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHold hold;
			if (convertView == null) {
				hold = new ViewHold();
				convertView = View.inflate(mActivity, R.layout.menu_item, null);
				hold.tv = (TextView) convertView;
				convertView.setTag(hold);
			} else {
				hold = (ViewHold) convertView.getTag();
			}
			hold.tv.setText(mBeans.get(position).title);

			hold.tv.setEnabled(mCurrentMenu == position);
			return convertView;
		}
	}

	class ViewHold {
		TextView tv;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//System.out.println("点击" + position);
		MainUI ui = (MainUI) mActivity;
		ContentFragment fragment = ui.getContentFragmentt();
		fragment.switchMenu(position);

		SlidingMenu menu = ui.getSlidingMenu();
		menu.toggle();

		// 设置当前选中的菜单
		mCurrentMenu = position;
		// ui刷新
		menuAdapter.notifyDataSetChanged();
	}

}
