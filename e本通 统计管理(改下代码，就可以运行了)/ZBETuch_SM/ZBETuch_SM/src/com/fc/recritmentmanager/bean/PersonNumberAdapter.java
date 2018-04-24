package com.fc.recritmentmanager.bean;

import java.util.List;

import com.fc.main.beans.PersonItem;
import com.fc.zbetuch_sm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PersonNumberAdapter extends BaseAdapter{
	
	private List<PersonItem> personItems;
	private Context mContext;
	public PersonNumberAdapter(List<PersonItem> personItems, Context mContext) {
		super();
		this.personItems = personItems;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return personItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return personItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HolderItem item;
		if (convertView==null) {
			convertView=LayoutInflater.from(
					mContext).inflate(
					R.layout.activity_item_center, null);
			item=new HolderItem();
			item.name_text=(TextView) convertView.findViewById(R.id.person_name);
			convertView.setTag(item);
		}else{
			item=(HolderItem) convertView.getTag();
		}
		item.name_text.setText(personItems.get(position).getName());
		return convertView;
	}
	
	class HolderItem{
		TextView name_text;
		Button del_btn;
	}

}
