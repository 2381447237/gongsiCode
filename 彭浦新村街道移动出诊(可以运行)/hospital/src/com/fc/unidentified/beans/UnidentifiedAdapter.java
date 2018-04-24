package com.fc.unidentified.beans;

import java.util.List;

import com.example.hospital.R;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UnidentifiedAdapter  extends BaseAdapter {
	private Context context;
	private List<UnideInfo> unideInfos;
	public UnidentifiedAdapter(Context context,
			List<UnideInfo> unideInfos) {
		super();
		this.context = context;
		this.unideInfos = unideInfos;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return unideInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return unideInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ItemHodler itemHodler;
		if(convertView == null){
			convertView=LayoutInflater.from(context).inflate(R.layout.activity_unidentified_adapter, null);
			itemHodler = new ItemHodler();
			itemHodler.tv_card=(TextView) convertView.findViewById(R.id.tv_card);
			itemHodler.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			itemHodler.tv_sex=(TextView) convertView.findViewById(R.id.tv_sex);
			itemHodler.tv_age=(TextView) convertView.findViewById(R.id.tv_age);
			itemHodler.tv_address=(TextView) convertView.findViewById(R.id.tv_address);
			convertView.setTag(itemHodler);
		}else{
			itemHodler = (ItemHodler)convertView.getTag();
		}
		UnideInfo info=unideInfos.get(position);
		itemHodler.tv_card.setText(info.getCard());
		itemHodler.tv_name.setText(info.getName());
		itemHodler.tv_sex.setText(info.getSex());
		itemHodler.tv_age.setText(info.getAge());
		itemHodler.tv_address.setText(info.getAddress());
		return convertView;
	}
	class ItemHodler {
		TextView tv_card,tv_name,tv_sex,tv_age,tv_address;
	}
	
}
