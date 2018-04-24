package com.fc.recritmentmanager.bean;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.fc.zbetuch_sm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ERecordAdapter extends BaseAdapter{

	private List<ERecordInfo> recordInfos;
	private Context mContext;
	
	public ERecordAdapter(List<ERecordInfo> recordInfos, Context mContext) {
		super();
		this.recordInfos = recordInfos;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return recordInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return recordInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return recordInfos.get(position).getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HolderItem holderItem;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_erecord, null);
			holderItem=new HolderItem();
			holderItem.num_TextView=(TextView) convertView.findViewById(R.id.tv_num);
			holderItem.name_TextView=(TextView) convertView.findViewById(R.id.tv_name);
			holderItem.type_TextView=(TextView) convertView.findViewById(R.id.tv_type);
			holderItem.time_TextView=(TextView) convertView.findViewById(R.id.tv_time);
			convertView.setTag(holderItem);
		} else {
			holderItem=(HolderItem) convertView.getTag();
		}
		convertView.setBackgroundResource(MainTools.getbackground1(position));
		ERecordInfo info=recordInfos.get(position);
		holderItem.num_TextView.setText(position+1+"");
		holderItem.name_TextView.setText(info.getNAME());
		holderItem.type_TextView.setText(info.getTYPE());
		holderItem.time_TextView.setText(info.getCREATE_DATE().replace("T", " ").substring(0, 10));
		return convertView;
	}
	
	class HolderItem{
		TextView num_TextView,name_TextView,type_TextView,time_TextView;
	}

}
