package com.lw.smartcenter.bean;

import java.util.List;

public class NewsMenuListBean {

	public NewsMenuListData data;
	public int retcode;

	public class NewsMenuListData {
		public String countcommenturl;
		public String more;
		public List<NewsBean> news;
		public String title;
		public List<TopicBean> topic;
		public List<TopnewsBean> topnews;
	}

	public class NewsBean {
		public boolean comment;
		public String commentlist;
		public String commenturl;
		public int id;
		public String listimage;
		public String pubdate;
		public String title;
		public String news;
		public String url;
		public String largeimage;
		public String smallimage;
		public String type;
	}

	public class TopicBean {
		public String description;
		public int id;
		public String listimage;
		public int sort;
		public String title;
		public String url;
	}

	public class TopnewsBean {
		public boolean comment;
		public String commentlist;
		public String commenturl;
		public int id;
		public String pubdate;
		public String title;
		public String topimage;
		public String type;
		public String url;
	}
}
