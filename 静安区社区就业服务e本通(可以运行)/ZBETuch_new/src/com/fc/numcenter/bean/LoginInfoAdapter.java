package com.fc.numcenter.bean;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LoginInfoAdapter extends BaseAdapter {

	private List<LoginInfo> loginInfos;
	private Context context;

	public LoginInfoAdapter(List<LoginInfo> loginInfos, Context context) {
		super();
		this.loginInfos = loginInfos;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return loginInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return loginInfos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		HolderItem holderItem;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_login, null);
			holderItem = new HolderItem();
			holderItem.num_TextView = (TextView) convertView
					.findViewById(R.id.tv_num);
			holderItem.name_TextView = (TextView) convertView
					.findViewById(R.id.tv_name);
			holderItem.type_TextView = (TextView) convertView
					.findViewById(R.id.tv_type);
			convertView.setTag(holderItem);
		} else {
			holderItem = (HolderItem) convertView.getTag();
		}
		convertView.setBackgroundResource(MainTools.getbackground1(position));
		LoginInfo info = loginInfos.get(position);
		holderItem.num_TextView.setText(position + 1 + "");
		holderItem.name_TextView.setText(info.getNAME());
		holderItem.type_TextView.setText(info.getLOGIN_TIME().replace("T", " ")
				.substring(0, 19));
		return convertView;
	}

	class HolderItem {
		TextView num_TextView, name_TextView, type_TextView;
	}
}
