package com.lw.smartcenter.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lw.smartcenter.R;
import com.lw.smartcenter.control.newsmenulist.NewsMenuListControl;
import com.lw.smartcenter.utils.SharePrefereceUtils;

public class NewsDetailUI extends Activity implements OnClickListener {

	private static final String TAG = "NewsDetailUI";
	@ViewInject(R.id.detail_back_iv)
	private ImageView mIvBack;
	private static final String TEXT_SIZE = "textsize";
	@ViewInject(R.id.detail_size_iv)
	private ImageView mIvSize;
	@ViewInject(R.id.detail_shared_iv)
	private ImageView mIvShared;
	@ViewInject(R.id.detail_content_wv)
	private WebView mWVContent;
	private WebSettings mSettings;

	private int mTextsizeId = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newdetail);
		ViewUtils.inject(this);

		initail();

	}

	private void initail() {
		Intent intent = getIntent();
		String url = intent.getStringExtra(NewsMenuListControl.KEY_DETAIL_URL);
		//Log.d(TAG, url);
		mWVContent.loadUrl(url);
		mSettings = mWVContent.getSettings();

		mSettings.setJavaScriptEnabled(true);// 设置js可见
		mSettings.setBuiltInZoomControls(true);// 设置放大缩小的控件
		mSettings.setUseWideViewPort(true);// 双击缩放
		// 设置字体大小
		mTextsizeId = SharePrefereceUtils.getInt(this, TEXT_SIZE, mTextsizeId);
		setTextSize(mTextsizeId);

		mIvBack.setOnClickListener(this);
		mIvSize.setOnClickListener(this);
		mIvShared.setOnClickListener(this);
	}

	private void setTextSize(int id) {
		switch (id) {
		case 0:
			mSettings.setTextSize(TextSize.LARGEST);
			break;
		case 1:
			mSettings.setTextSize(TextSize.LARGER);
			break;
		case 2:
			mSettings.setTextSize(TextSize.NORMAL);
			break;
		case 3:
			mSettings.setTextSize(TextSize.SMALLER);
			break;
		case 4:
			mSettings.setTextSize(TextSize.SMALLEST);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.detail_back_iv:
			// 点击返回
			finish();

			break;
		case R.id.detail_shared_iv:
			// 点击分享

			break;
		case R.id.detail_size_iv:
			// 点击字体大小
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			String[] items = new String[] { "超大字体", "大号字体", "正常字体", "小号字体",
					"超小字体" };
			builder.setSingleChoiceItems(items, mTextsizeId,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mTextsizeId = which;
						}
					});
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					SharePrefereceUtils.setInt(NewsDetailUI.this, TEXT_SIZE, mTextsizeId);
					setTextSize(mTextsizeId);
				}
			});
			builder.show();
			break;

		default:
			break;
		}

	}

}
