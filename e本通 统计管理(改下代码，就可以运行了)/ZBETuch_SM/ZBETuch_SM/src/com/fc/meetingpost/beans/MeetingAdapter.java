package com.fc.meetingpost.beans;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.fc.zbetuch_sm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MeetingAdapter extends BaseAdapter{
	private Context context;
	private List<MeetingInfo> meetingInfos;
	
	public MeetingAdapter(Context context, List<MeetingInfo> meetingInfos) {
		super();
		this.context = context;
		this.meetingInfos = meetingInfos;
	}

	@Override
	public int getCount() {
		return meetingInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return meetingInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return meetingInfos.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemHodler itemHodler;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_meetinglist,null);
			itemHodler = new ItemHodler();
			itemHodler.tv_meetid = (TextView)convertView.findViewById(R.id.tv_meetid);
			itemHodler.tv_meetname = (TextView)convertView.findViewById(R.id.tv_meetname);
			itemHodler.tv_meetaddress = (TextView)convertView.findViewById(R.id.tv_meetaddress);
			itemHodler.tv_meettime = (TextView)convertView.findViewById(R.id.tv_meettime);
			convertView.setTag(itemHodler);
		}else{
			itemHodler = (ItemHodler)convertView.getTag();
		}
		MeetingInfo meetingInfo = meetingInfos.get(position);
		itemHodler.tv_meetid.setText(""+(position+1));
		itemHodler.tv_meetname.setText(meetingInfo.getTitle());
		itemHodler.tv_meetaddress.setText(meetingInfo.getMeetingAdd());
		itemHodler.tv_meettime.setText(meetingInfo.getMeetingTime().replace("T", " ").substring(0, 16));
		convertView.setBackgroundResource(MainTools.getbackground1(position+1));
		return convertView;
	}
	
	class ItemHodler {
		TextView tv_meetid,tv_meetname,tv_meetaddress,tv_meettime;
	}
}
