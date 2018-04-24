package com.fc.wenjuan.beans;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WenTypeAdapter extends BaseAdapter {

	private Context context;
	private List<WenJuanType> juanTypes;

	public WenTypeAdapter(Context context, List<WenJuanType> juanTypes) {
		super();
		this.context = context;
		this.juanTypes = juanTypes;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return juanTypes.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return juanTypes.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return juanTypes.get(arg0).getID();
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		HoldItem holdItem = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_wenjuantype_info, null);
			holdItem = new HoldItem();
			holdItem.tv_id = (TextView) view.findViewById(R.id.tv_id);
			holdItem.tv_name = (TextView) view.findViewById(R.id.tv_name);
			holdItem.tv_no = (TextView) view.findViewById(R.id.tv_no);
			holdItem.tv_time = (TextView) view.findViewById(R.id.tv_time);
			view.setTag(holdItem);
		} else {
			holdItem = (HoldItem) view.getTag();
		}
		view.setBackgroundResource(MainTools.getbackground1(position));
		WenJuanType wenJuanType = juanTypes.get(position);
		holdItem.tv_id.setText(position + 1 + "");
		holdItem.tv_name.setText(wenJuanType.getTITLE());
		holdItem.tv_no.setText(wenJuanType.getNO());
		holdItem.tv_time.setText(wenJuanType.getCREATE_TIME().substring(0, 10));
		return view;
	}

	class HoldItem {
		TextView tv_id, tv_name, tv_no, tv_time;
	}

}
