package com.fc.first.beans;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchPendWorkAdapter extends BaseAdapter {
	private Context context;
	private List<PendingWorkInformation> pendwork_list;

	public SearchPendWorkAdapter(Context context,
			List<PendingWorkInformation> pendwork_list) {
		this.context = context;
		this.pendwork_list = pendwork_list;
	}

	@Override
	public int getCount() {
		return pendwork_list.size();
	}

	@Override
	public Object getItem(int position) {
		return pendwork_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_searchpendwrok_list, null);

		}
		TextView lblName = (TextView) convertView.findViewById(R.id.lblName);
		TextView lblTitle = (TextView) convertView.findViewById(R.id.lblTitle);
		TextView lblCreatTime = (TextView) convertView
				.findViewById(R.id.lblCreatTime);

		PendingWorkInformation info = pendwork_list.get(position);
		lblName.setText(info.getStaff_Name());
		lblTitle.setText(info.getTitle());
		lblCreatTime.setText(info.getCreate_Time().split("T")[0]);

		convertView.setBackgroundResource(MainTools
				.getbackground1(position + 1));
		return convertView;
	}

}
