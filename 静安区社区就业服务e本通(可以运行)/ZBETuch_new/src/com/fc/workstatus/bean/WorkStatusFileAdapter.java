package com.fc.workstatus.bean;

import java.util.List;

import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WorkStatusFileAdapter extends BaseAdapter {

	Context context;
	List<WorkStatusFileInfo> items;

	public WorkStatusFileAdapter(Context context, List<WorkStatusFileInfo> items) {
		super();
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_meetingfiledownload, null);
		// view.setBackgroundResource(MainTools.getbackground1(position));
		TextView txtItem = (TextView) view.findViewById(R.id.txtItem);
		txtItem.setText(items.get(position).getFILE_NAME());

		return view;
	}

}
