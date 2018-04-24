package com.fc.worktodate.beans;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WorkToDateAdapter extends BaseAdapter {
	private List<WorkToDateItem> list;
	private Context context;

	public WorkToDateAdapter(List<WorkToDateItem> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return list.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_worktodate_item, null);
		}
		TextView lblNum = (TextView) convertView.findViewById(R.id.lblNum);
		TextView lblTitle = (TextView) convertView.findViewById(R.id.lblTitle);
		TextView lblTime = (TextView) convertView.findViewById(R.id.lblTime);

		convertView.setBackgroundResource(MainTools.getbackground1(position));

		WorkToDateItem item = list.get(position);
		lblNum.setText(position + 1 + "");
		lblTitle.setText(item.getTitle());
		lblTime.setText(item.getCreateDate().replace("T", " ").substring(0, 10));

		return convertView;
	}

}
