package com.fc.main.beans;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.test.zbetuch_news.R;

/**
 * 主界面网格的Adapter
 * 
 * @author hxl
 * 
 */
public class GridAdapter extends BaseAdapter {

	/**
	 * 持有者
	 * 
	 * @author Administrator
	 * 
	 */
	private class GridHolder {
		private ImageView imgview;
	}

	private Context context;
	private List<GridInfo> list;
	private LayoutInflater mInflater;

	public GridAdapter(int imgIds[], Context context) {
		super();
		this.context = context;
		mInflater = LayoutInflater.from(context);
		list = new ArrayList<GridInfo>();
		for (int i = 0; i < imgIds.length; i++) {
			GridInfo gridInfo = new GridInfo(imgIds[i]);
			list.add(gridInfo);
		}
	}

	/*
	 * public void setList(List<GridInfo> list) { this.list = list; mInflater =
	 * (LayoutInflater) context
	 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); }
	 */

	@Override
	public int getCount() {
		if (null != list) {
			return list.size();
		} else {
			return 0;
		}

	}

	@Override
	public Object getItem(int index) {

		return list.get(index);
	}

	@Override
	public long getItemId(int index) {

		return index;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		GridHolder holder;
		if (convertView == null) {
			// convertView = mInflater.inflate(R.layout.item, null);
			convertView = mInflater.inflate(R.layout.mian_a, null);
			holder = new GridHolder();
			holder.imgview = (ImageView) convertView
					.findViewById(R.id.imageView1);
			convertView.setTag(holder);
			// convertView.setPadding(15, 15, 15, 15);
		} else {
			holder = (GridHolder) convertView.getTag();
		}
		GridInfo info = list.get(index);
		if (info != null) {
		}
		holder.imgview.setImageResource(info.getImgId());
		return convertView;
	}

}
