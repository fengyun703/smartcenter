package com.lw.smartcenter.bean;

import java.util.List;

public class NewsCenterBean {
	public List<NewsMenuBean> data;
	public List<Integer> extend;
	public int retcode;

	public class NewsMenuBean {
		public List<NewsBean> children;
		public int id;
		public String title;
		public int type;

		public String url;
		public String url1;

		public String dayurl;
		public String excurl;
		public String weekurl;
	}

	public class NewsBean {
		public int id;
		public String title;
		public int type;
		public String url;
	}
}
