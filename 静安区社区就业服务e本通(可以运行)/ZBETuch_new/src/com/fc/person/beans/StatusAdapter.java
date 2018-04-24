package com.fc.person.beans;

import java.util.List;

import com.fc.main.beans.PersonInfo;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.PersonAdapter.MarkHodler;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StatusAdapter extends BaseAdapter {

	private List<TypeInfo> infos;
	private Context mContext;

	public StatusAdapter(List<TypeInfo> infos, Context mContext) {
		super();
		this.infos = infos;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return infos.get(position).getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MarkHodler markhodler = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.status_item, null);
			markhodler = new MarkHodler();
			markhodler.tv_markname = (TextView) convertView
					.findViewById(R.id.tv_markname);
			convertView.setTag(markhodler);
		} else {
			markhodler = (MarkHodler) convertView.getTag();
		}
		convertView.setBackgroundResource(MainTools.getbackground1(position));
		TypeInfo typeInfo = infos.get(position);
		markhodler.tv_markname.setText(typeInfo.getNAME().trim()
				.replaceAll(" ", ""));
		return convertView;
	}

	class MarkHodler {
		TextView tv_markname;
	}

}
