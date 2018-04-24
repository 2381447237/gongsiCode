package com.fc.person.beans;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WorkInfoAdapter extends BaseAdapter {
	private List<WorkInfo> workInfoList;
	private Context context;

	public WorkInfoAdapter(List<WorkInfo> workInfoList, Context context) {
		this.workInfoList = workInfoList;
		this.context = context;
	}

	@Override
	public int getCount() {

		return workInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return workInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return workInfoList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_workinfo_item, null);
		}

		TextView lblDw_name = (TextView) convertView
				.findViewById(R.id.lblDw_name);
		TextView lblStart_date = (TextView) convertView
				.findViewById(R.id.lblStart_date);
		TextView lblEnd_date = (TextView) convertView
				.findViewById(R.id.lblEnd_date);
		TextView lblDw_type = (TextView) convertView
				.findViewById(R.id.lblDw_type);
		TextView lblTrade = (TextView) convertView.findViewById(R.id.lblTrade);
		TextView lblDept = (TextView) convertView.findViewById(R.id.lblDept);
		TextView lblPosition = (TextView) convertView
				.findViewById(R.id.lblPosition);
		WorkInfo info = workInfoList.get(position);
		lblDw_name.setText(info.getDw_name());
		lblStart_date.setText(info.getStart_date().split("T")[0]);
		lblEnd_date.setText(info.getEnd_date().split("T")[0]);
		lblDw_type.setText(info.getDw_type());
		lblTrade.setText(info.getTrade());
		lblDept.setText(info.getDept());
		lblPosition.setText(info.getPosition());

		convertView.setBackgroundResource(MainTools.getbackground1(position));
		return convertView;
	}

}
