package com.fc.reportform.beans;

import java.util.ArrayList;
import java.util.List;

import com.fc.main.tools.MainTools;
import com.fc.zbetuch_sm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReportFormAdapter extends BaseAdapter{
	private Context context;
	private List<ReportFormInfo> reportInfos;
	
	public ReportFormAdapter(Context context, List<ReportFormInfo> reportInfos) {
		super();
		this.context = context;
		this.reportInfos = reportInfos;
	}

	@Override
	public int getCount() {
		return reportInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return reportInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return reportInfos.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemHodler itemHodler;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_reportformlist,null);
			itemHodler = new ItemHodler();
			itemHodler.tv_reportid = (TextView)convertView.findViewById(R.id.tv_reportid);
			itemHodler.tv_reportname = (TextView)convertView.findViewById(R.id.tv_reportname);
			itemHodler.tv_reporttime = (TextView)convertView.findViewById(R.id.tv_reporttime);
			convertView.setTag(itemHodler);
		}else{
			itemHodler = (ItemHodler)convertView.getTag();
		}
		ReportFormInfo reportInfo = reportInfos.get(position);
		itemHodler.tv_reportid.setText(""+(position+1));
		itemHodler.tv_reportname.setText(reportInfo.getTitle());
		itemHodler.tv_reporttime.setText(reportInfo.getReportTime().replace("T", " ").substring(0, 16));
		convertView.setBackgroundResource(MainTools.getbackground1(position+1));
		return convertView;
	}

	class ItemHodler {
		TextView tv_reportid,tv_reportname,tv_reporttime;
	}
}
