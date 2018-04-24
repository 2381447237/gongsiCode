package com.fc.main.beans;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

public class CheckBoxAdapter extends BaseAdapter {

	Context context;
	List<CheckBox> boxs;

	public CheckBoxAdapter(Context context, List<CheckBox> boxs) {
		this.context = context;
		this.boxs = boxs;
	}

	@Override
	public int getCount() {
		return boxs.size();
	}

	@Override
	public Object getItem(int position) {
		return boxs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return boxs.get(position);
	}

}
