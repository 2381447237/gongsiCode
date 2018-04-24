package com.fc.person.beans;

import com.fc.zbetuch_sm.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



public class Layout5_listAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private String activityname;
	private String[] str = new String[] { "sdfsdf", "sdfsd", "adsfds","dsfsdf","sdfdsf" };
	
	public Layout5_listAdapter(Context context,String activityname) {
		super();
		this.context = context;
		this.activityname = activityname;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return str.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return str[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View layout3_item = inflater.inflate(R.layout.layout5_lvitem,null);
		if (activityname.equals("HospitalizedActivity")) {
			layout3_item = inflater.inflate(R.layout.layout5_lvitem1,null);
			return layout3_item;
		}
		if(position%2==0){
		//layout3_item.setBackgroundColor(R.color.lanse);
		}
		return layout3_item;
	}

}
