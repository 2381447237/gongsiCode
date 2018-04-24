package com.fc.first.beans;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fc.main.tools.MainTools;
import com.fc.zbetuch_sm.R;

public class PendWorkAdapter2 extends BaseAdapter {
	private Context context;
	private List<PendingWorkInformation> pendwork_list;

	public PendWorkAdapter2(Context context,
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
		// TODO Auto-generated method stub
		QueryItemHodler itemHodler;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_pendworkbase2, null);
			itemHodler = new QueryItemHodler();
			itemHodler.tv_pendwork_first_title = (TextView) convertView
					.findViewById(R.id.tv_pendwork_first_title);
			itemHodler.tv_pendwork_first_createtime = (TextView) convertView
					.findViewById(R.id.tv_pendwork_first_createtime);
			convertView.setTag(itemHodler);
		} else {
			itemHodler = (QueryItemHodler) convertView.getTag();
		}
		try {
			PendingWorkInformation pendworkinfo = pendwork_list.get(position);
			itemHodler.tv_pendwork_first_title.setText(pendworkinfo.getTitle());
			itemHodler.tv_pendwork_first_createtime.setText(pendworkinfo
					.getCreate_Time().replace("T", " ")
					.substring(0, 16));
		} catch (Exception e) {
			e.printStackTrace();
		}
		convertView.setBackgroundResource(MainTools.getbackground1(position+1));
		return convertView;
	}

	class QueryItemHodler {
		TextView tv_pendwork_first_title, tv_pendwork_first_createtime;
	}

}
