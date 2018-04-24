package com.fc.first.beans;

import java.util.ArrayList;

import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetNewsAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<GetNews> getnewslist;

	public GetNewsAdapter(Context context, ArrayList<GetNews> getnewslist) {
		super();
		this.context = context;
		this.getnewslist = getnewslist;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public ArrayList<GetNews> getGetnewslist() {
		return getnewslist;
	}

	public void setGetnewslist(ArrayList<GetNews> getnewslist) {
		this.getnewslist = getnewslist;
	}

	@Override
	public String toString() {
		return "GetNewsAdapter [context=" + context + ", getnewslist="
				+ getnewslist + "]";
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getnewslist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getnewslist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		QueryItemHodler itemHodler;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_getnewsbase, null);
			itemHodler = new QueryItemHodler();
			itemHodler.tv_getnews_first_title = (TextView) convertView
					.findViewById(R.id.tv_getnews_first_title);
			itemHodler.tv_getnews_first_createtime = (TextView) convertView
					.findViewById(R.id.tv_getnews_first_createtime);
			convertView.setTag(itemHodler);
		} else {
			itemHodler = (QueryItemHodler) convertView.getTag();
		}
		GetNews newsinfo = getnewslist.get(position);
		itemHodler.tv_getnews_first_title.setText(newsinfo.getTitle());
		itemHodler.tv_getnews_first_createtime.setText(newsinfo
				.getCreate_Time().replace("T", " "));
		return convertView;
	}

	class QueryItemHodler {
		TextView tv_getnews_first_title, tv_getnews_first_createtime;
	}

	public void addPersonItem(ArrayList<GetNews> list) {
		setGetnewslist(list);
	}
}
