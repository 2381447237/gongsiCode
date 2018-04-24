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

public class MeetingReadAdapter extends BaseAdapter {

	private Context mContext;
	private List<ReadInfo> meetingReadInfos;

	public MeetingReadAdapter(Context mContext, List<ReadInfo> meetingReadInfos) {
		super();
		this.mContext = mContext;
		this.meetingReadInfos = meetingReadInfos;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return meetingReadInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return meetingReadInfos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		HolderItem holderItem;
		if (contentView == null) {
			contentView = LayoutInflater.from(mContext).inflate(
					R.layout.item_meetingread, null);
			holderItem = new HolderItem();
			holderItem.tv_num = (TextView) contentView
					.findViewById(R.id.tv_meeting_num);
			holderItem.tv_meeting_all = (TextView) contentView
					.findViewById(R.id.tv_meeting_allnum);
			holderItem.tv_meeting_read = (TextView) contentView
					.findViewById(R.id.tv_meeting_readnum);
			holderItem.tv_date = (TextView) contentView
					.findViewById(R.id.tv_meeting_time);
			holderItem.tv_reat = (TextView) contentView
					.findViewById(R.id.tv_meeting_rate);
			contentView.setTag(holderItem);
		} else {
			holderItem = (HolderItem) contentView.getTag();
		}
		contentView.setBackgroundResource(MainTools.getbackground1(position));
		ReadInfo meetingReadInfo = meetingReadInfos.get(position);
		holderItem.tv_num.setText(position + 1 + "");
		holderItem.tv_meeting_all.setText(meetingReadInfo.getA_COUNT() + "");
		holderItem.tv_meeting_read.setText(meetingReadInfo.getB_COUNT() + "");
		holderItem.tv_date.setText(meetingReadInfo.getDATE());
		holderItem.tv_reat.setText(meetingReadInfo.getRATE() + "%");
		return contentView;
	}

	class HolderItem {
		TextView tv_num, tv_meeting_all, tv_meeting_read, tv_date, tv_reat;
	}

}
