package com.fc.first.beans;

import java.util.List;

import com.fc.first.beans.FirstWorkTongZhiAdapter.ItemHodler;
import com.fc.work.beans.WorkTongzhiBean;
import com.fc.workstatus.bean.WorkStatusInfo;
import com.fc.zbetuch_sm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FirstWorkStatusAdapter extends BaseAdapter{
	
	private Context context;
	private List<WorkStatusInfo> workStatusInfos;
	
	public FirstWorkStatusAdapter(Context context,
			List<WorkStatusInfo> workStatusInfos) {
		super();
		this.context = context;
		this.workStatusInfos = workStatusInfos;
	}

	@Override
	public int getCount() {
		return workStatusInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return workStatusInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return workStatusInfos.get(position).getID();
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
		WorkStatusInfo info = workStatusInfos.get(position);
		itemHodler.tv_first_title.setText(info.getTITLE());
		itemHodler.tv_first_createtime.setText(info.getCREATE_DATE().replace("T", " ").substring(0, 16));	
		return convertView;
	}

	class ItemHodler {
		TextView tv_first_title,tv_first_createtime;
	}

}
