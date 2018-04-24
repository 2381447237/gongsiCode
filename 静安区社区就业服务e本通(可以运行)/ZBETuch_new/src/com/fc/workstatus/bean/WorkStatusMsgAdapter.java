package com.fc.workstatus.bean;

import java.util.List;

import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WorkStatusMsgAdapter extends BaseAdapter {

	private Context mContext;
	private List<WorkStatusMsgInfo> workStatusMsgInfos;

	public WorkStatusMsgAdapter(Context mContext,
			List<WorkStatusMsgInfo> workStatusMsgInfos) {
		super();
		this.mContext = mContext;
		this.workStatusMsgInfos = workStatusMsgInfos;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return workStatusMsgInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return workStatusMsgInfos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		QueryItemHodler itemHodler;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.activity_msgboarddetile, null);
			itemHodler = new QueryItemHodler();
			itemHodler.tv_msgboarddetile_name = (TextView) convertView
					.findViewById(R.id.tv_msgboarddetile_name);
			itemHodler.tv_msgboarddetile_time = (TextView) convertView
					.findViewById(R.id.tv_msgboarddetile_time);
			itemHodler.tv_msgboarddetile_doc = (TextView) convertView
					.findViewById(R.id.tv_msgboarddetile_doc);
			convertView.setTag(itemHodler);
		} else {
			itemHodler = (QueryItemHodler) convertView.getTag();
		}
		WorkStatusMsgInfo workStatusMsgInfo = workStatusMsgInfos.get(position);
		itemHodler.tv_msgboarddetile_name.setText(workStatusMsgInfo
				.getStaffName());
		itemHodler.tv_msgboarddetile_time.setText(workStatusMsgInfo
				.getCREATE_TIME().replace("T", " ").substring(0, 19));
		itemHodler.tv_msgboarddetile_doc.setText(workStatusMsgInfo.getMSG());
		return convertView;
	}

	class QueryItemHodler {
		TextView tv_msgboarddetile_name, tv_msgboarddetile_time,
				tv_msgboarddetile_doc;
	}

}
