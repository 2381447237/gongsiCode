package com.fc.first.beans;

import java.util.ArrayList;
import java.util.List;

import com.fc.first.beans.FirstMeetingAdapter.ItemHodler;
import com.fc.meetingpost.beans.MeetingInfo;
import com.fc.reportform.beans.ReportFormInfo;
import com.fc.zbetuch_sm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FirstReportFormAdapter extends BaseAdapter{
	private Context context;
	private List<ReportFormInfo> reportFormInfos;
	
	public FirstReportFormAdapter(Context context,
			List<ReportFormInfo> reportFormInfos) {
		super();
		this.context = context;
		this.reportFormInfos = reportFormInfos;
	}

	@Override
	public int getCount() {
		return reportFormInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return reportFormInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return reportFormInfos.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemHodler itemHodler;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_getnewsbase, null);
			itemHodler = new ItemHodler();
			itemHodler.tv_first_title = (TextView)convertView.
					findViewById(R.id.tv_getnews_first_title);
			itemHodler.tv_first_createtime = (TextView)convertView.
					findViewById(R.id.tv_getnews_first_createtime);
			convertView.setTag(itemHodler);
		}else{
			itemHodler = (ItemHodler)convertView.getTag();
		}
		ReportFormInfo info = reportFormInfos.get(position);
		itemHodler.tv_first_title.setText(info.getTitle());
		itemHodler.tv_first_createtime.setText(info.getCreatedate().replace("T", " ").substring(0, 16));	
		return convertView;
	}

	class ItemHodler {
		TextView tv_first_title,tv_first_createtime;
	}

}
