package com.lw.smartcenter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lw.smartcenter.R;

public class RefreshListView extends ListView {

	private LinearLayout mHeaderLayout;
	private ProgressBar header_pb;
	private RelativeLayout header_refresh_layout;
	private ImageView header_iv_arrow;
	private TextView header_refresh_content_tv;
	private TextView header_refresh_time_tv;

	private LinearLayout mFooterLayout;
	private ProgressBar footer_pb;
	private TextView footer_content_tv;

	private View mCustomLayout;
	private int mFooterPadding;
	private int mHeaderPadding;

	public RefreshListView(Context context) {
		this(context, null);
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		initialHeader();
		initialFooter();
	}

	private void initialFooter() {
		mFooterLayout = (LinearLayout) View.inflate(getContext(),
				R.layout.news_menu_list_footer, null);
		footer_pb = (ProgressBar) mFooterLayout.findViewById(R.id.footer_pb);
		footer_content_tv = (TextView) mFooterLayout
				.findViewById(R.id.footer_content_tv);
		this.addFooterView(mFooterLayout);
		mFooterLayout.measure(0, 0);
		mFooterPadding = mFooterLayout.getMeasuredHeight();
		mFooterLayout.setPadding(0, -mFooterPadding, 0, 0);
		//System.out.println(" mFooterPadding = " + mFooterPadding);
	}

	private void initialHeader() {
		mHeaderLayout = (LinearLayout) View.inflate(getContext(),
				R.layout.news_menu_list_header, null);
		header_pb = (ProgressBar) mHeaderLayout.findViewById(R.id.header_pb);
		header_refresh_layout = (RelativeLayout) mHeaderLayout
				.findViewById(R.id.header_refresh_layout);
		header_iv_arrow = (ImageView) mHeaderLayout
				.findViewById(R.id.header_iv_arrow);
		header_refresh_content_tv = (TextView) mHeaderLayout
				.findViewById(R.id.header_refresh_content_tv);
		header_refresh_time_tv = (TextView) mHeaderLayout
				.findViewById(R.id.header_refresh_time_tv);
		this.addHeaderView(mHeaderLayout);
		
		header_refresh_layout.measure(0, 0);
		mHeaderPadding = header_refresh_layout.getMeasuredHeight();
		mHeaderLayout.setPadding(0, -mHeaderPadding, 0, 0);
		//System.out.println(" mHeaderPadding = " + mHeaderPadding);
	}

	public void addCustomHeadView(View view) {
		mCustomLayout = view;
		mHeaderLayout.addView(view);
	}

}
