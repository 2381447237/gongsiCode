package com.fc.download.beans;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.fc.zbetuch_sm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DownLoadFileAdapter extends BaseAdapter {
	Context context;
	List<DownLoadItem> items;

	public DownLoadFileAdapter(Context context, List<DownLoadItem> items) {
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

		return items.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = LayoutInflater.from(context).inflate(
				R.layout.filedownload_main_item, null);
		// view.setBackgroundResource(MainTools.getbackground1(position));
		TextView txtItem = (TextView) view.findViewById(R.id.txtItem);
		txtItem.setText(items.get(position).getTitle());
		view.setBackgroundResource(MainTools.getbackground1(position));

		return view;
	}

}
