package com.fc.numcenter.bean;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RedecodeAdapter extends BaseAdapter {

	private List<RedecodeInfo> redecodeInfos;
	private Context mContext;

	public RedecodeAdapter(List<RedecodeInfo> redecodeInfos, Context mContext) {
		super();
		this.redecodeInfos = redecodeInfos;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return redecodeInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return redecodeInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HolderItem holderItem;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_redecode, null);
			holderItem = new HolderItem();
			holderItem.num_TextView = (TextView) convertView
					.findViewById(R.id.tv_num);
			holderItem.name_TextView = (TextView) convertView
					.findViewById(R.id.tv_name);
			holderItem.sfz_TextView = (TextView) convertView
					.findViewById(R.id.tv_sfz);
			holderItem.type_TextView = (TextView) convertView
					.findViewById(R.id.tv_type);
			holderItem.time_TextView = (TextView) convertView
					.findViewById(R.id.tv_time);
			convertView.setTag(holderItem);
		} else {
			holderItem = (HolderItem) convertView.getTag();
		}
		convertView.setBackgroundResource(MainTools.getbackground1(position));
		RedecodeInfo info = redecodeInfos.get(position);
		holderItem.num_TextView.setText(position + 1 + "");
		holderItem.name_TextView.setText(info.getDETAIL());
		holderItem.sfz_TextView.setText(info.getSFZ());
		holderItem.type_TextView.setText(info.getCREATE_TIME()
				.replace("T", " ").substring(0, 10));
		if (info.getGPS().indexOf(",") > 0 && info.getGPS().trim() != null
				&& !"".equals(info.getGPS().trim())) {
			String gps[] = info.getGPS().split(",");
			if (gps[0].indexOf(".") > 0 && gps[1].indexOf(".") > 0) {
				holderItem.time_TextView.setText("经度:"
						+ gps[0].substring(0, gps[0].indexOf(".")) + "\n"
						+ "纬度:" + gps[1].substring(0, gps[1].indexOf(".")));
			} else {
				holderItem.time_TextView.setText(" ");
			}
		} else {
			holderItem.time_TextView.setText(" ");
		}
		return convertView;
	}

	class HolderItem {
		TextView num_TextView, name_TextView, type_TextView, time_TextView,
				sfz_TextView;
	}

}
