package com.fc.unidentified.beans;

import java.util.List;

import com.example.hospital.R;
import com.example.hospital.R.layout;
import com.fc.unidentified.beans.UnidentifiedAdapter.ItemHodler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemsInfoAdapter extends BaseAdapter {
	private Context context;
	private List<ItemsInfo> itemsInfos;
	public ItemsInfoAdapter(Context context,
			List<ItemsInfo> itemsInfos){
		super();
		this.context=context;
		this.itemsInfos=itemsInfos;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemsInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itemsInfos.get(position);
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
			convertView=LayoutInflater.from(context).inflate(R.layout.activity_items_info_adapter, null);
			itemHodler = new ItemHodler();
			itemHodler.tv_items1=(TextView) convertView.findViewById(R.id.tv_items1);
			itemHodler.tv_items2=(TextView) convertView.findViewById(R.id.tv_items2);
			itemHodler.tv_items3=(TextView) convertView.findViewById(R.id.tv_items3);
			itemHodler.tv_items4=(TextView) convertView.findViewById(R.id.tv_items4);
			itemHodler.tv_items6=(TextView) convertView.findViewById(R.id.tv_items6);
			itemHodler.tv_items7=(TextView) convertView.findViewById(R.id.tv_items7);
			itemHodler.tv_items8=(TextView) convertView.findViewById(R.id.tv_items8);
			itemHodler.liner_gui=(LinearLayout) convertView.findViewById(R.id.liner_gui);
			convertView.setTag(itemHodler);
		}else{
			itemHodler = (ItemHodler)convertView.getTag();
		}
		ItemsInfo info=itemsInfos.get(position);
		itemHodler.tv_items1.setText(info.getItemClassName());
		itemHodler.tv_items2.setText(info.getItemName());
		itemHodler.tv_items4.setText(info.getQuantity());
		itemHodler.tv_items6.setText(info.getItemOutpSpec());
		itemHodler.tv_items8.setText(info.getItemPrice1()+"");
		Log.i("qwj", "info.getDrugOrItemIndicatorName()="+info.getDrugOrItemIndicatorName());
		Log.i("qwj", "info.getItemOutpSpec()="+info.getItemOutpSpec());
		if(info.getDrugOrItemIndicatorName().equals("“©∆∑¿‡")){
			itemHodler.liner_gui.setVisibility(View.VISIBLE);
		}else{
			itemHodler.liner_gui.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ItemHodler {
		TextView tv_items1,tv_items2,tv_items3,tv_items4,
		tv_items6,tv_items7,tv_items8;
		LinearLayout liner_gui;
	}

	
}
