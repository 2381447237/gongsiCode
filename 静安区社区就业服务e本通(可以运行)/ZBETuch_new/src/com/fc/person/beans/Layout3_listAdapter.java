package com.fc.person.beans;

import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class Layout3_listAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private String[] str = new String[] { "sdfsdf", "sdfsd", "adsfds",
			"dsfsdf", "sdfdsf" };

	public Layout3_listAdapter(Context context) {
		super();
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		View layout3_item = inflater.inflate(R.layout.layout3_lvitem, null);
		return layout3_item;
	}

}
