package com.fc.recruitment.beans;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GeRenTuiJianSFZAdapter extends BaseAdapter {
	private Context mContext;
	private List<TuiJianListItem> items;

	public GeRenTuiJianSFZAdapter(Context mContext, List<TuiJianListItem> items) {
		super();
		this.mContext = mContext;
		this.items = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return items.get(position).getID();
	}

	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Item item;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.recuritmentlist_gerensfz, null);
			item = new Item();
			item.list_sfz = (TextView) convertView.findViewById(R.id.list_sfz);
			convertView.setTag(item);
		} else {
			item = (Item) convertView.getTag();
		}
		convertView.setBackgroundResource(MainTools.getbackground1(position));
		TuiJianListItem text = items.get(position);
		item.list_sfz.setText(text.getSFZ());
		return convertView;
	}

	private class Item {
		TextView list_sfz;
	}

}
