package com.fc.workstatus.bean;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.fc.work.beans.WorkTongzhiBean;
import com.fc.zbetuch_sm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WorkStatusAdapter extends BaseAdapter{
	private List<WorkStatusInfo> workStatusInfos;
	private Context mContext;
	
	public WorkStatusAdapter(List<WorkStatusInfo> workStatusInfos,
			Context mContext) {
		super();
		this.workStatusInfos = workStatusInfos;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return workStatusInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return workStatusInfos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		HotelItem item;
		if (view==null) {
			view=LayoutInflater.from(mContext).inflate(R.layout.item_tongzhi_work, null);
			item=new HotelItem();
			item.tv_wordid=(TextView) view.findViewById(R.id.tv_workid);
			item.tv_worktitle=(TextView) view.findViewById(R.id.tv_worktitle);
			item.tv_worktime=(TextView) view.findViewById(R.id.tv_workTongzhitime);
			view.setTag(item);
		} else {
            item=(HotelItem) view.getTag();
		}
		item.tv_wordid.setText(position+1+"");
		item.tv_worktitle.setText(workStatusInfos.get(position).getTITLE());
		item.tv_worktime.setText(workStatusInfos.get(position).getCREATE_DATE().replace("T", " ").substring(0, 16));
		view.setBackgroundResource(MainTools.getbackground1(position+1));
		return view;
	}
	
	private class HotelItem{
		TextView tv_wordid,tv_worktitle,tv_worktime;
	}

}
