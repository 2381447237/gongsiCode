package com.fc.first.beans;

import java.util.List;

import com.fc.work.bean.WorkTongzhiBean;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FirstWorkTongZhiAdapter extends BaseAdapter {

	private Context context;
	private List<WorkTongzhiBean> workTongzhiBeans;

	public FirstWorkTongZhiAdapter(Context context,
			List<WorkTongzhiBean> workTongzhiBeans) {
		super();
		this.context = context;
		this.workTongzhiBeans = workTongzhiBeans;
	}

	@Override
	public int getCount() {
		return workTongzhiBeans.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return workTongzhiBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return workTongzhiBeans.get(position).getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemHodler itemHodler;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_getnewsbase, null);
			itemHodler = new ItemHodler();
			itemHodler.tv_first_title = (TextView) convertView
					.findViewById(R.id.tv_getnews_first_title);
			itemHodler.tv_first_createtime = (TextView) convertView
					.findViewById(R.id.tv_getnews_first_createtime);
			convertView.setTag(itemHodler);
		} else {
			itemHodler = (ItemHodler) convertView.getTag();
		}
		WorkTongzhiBean info = workTongzhiBeans.get(position);
		itemHodler.tv_first_title.setText(info.getTITLE());
		itemHodler.tv_first_createtime.setText(info.getCREATE_DATE()
				.replace("T", " ").substring(0, 19));
		return convertView;
	}

	class ItemHodler {
		TextView tv_first_title, tv_first_createtime;
	}

}
