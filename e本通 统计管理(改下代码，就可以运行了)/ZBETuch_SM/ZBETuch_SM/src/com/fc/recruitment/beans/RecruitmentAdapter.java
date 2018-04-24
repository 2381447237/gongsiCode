package com.fc.recruitment.beans;

import java.util.List;

import com.fc.zbetuch_sm.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RecruitmentAdapter extends BaseAdapter{
	private Activity mContext;
	private List<RecruitmentInfo> infos;
	
	public RecruitmentAdapter(Activity mContext, List<RecruitmentInfo> infos){
		this.mContext = mContext;
		this.infos = infos;
	}
		
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return infos.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_recruiement_single, null);
			holder = new Holder();
			holder.recuritment_title = (TextView)convertView.
					findViewById(R.id.recuritment_title);
			holder.recuritment_time = (TextView)convertView.
					findViewById(R.id.recuritment_time);
			holder.recuritment_adress = (TextView)convertView.
					findViewById(R.id.recuritment_adress);
			holder.company_Num = (TextView)convertView.
					findViewById(R.id.company_Num);
			holder.job_Num = (TextView)convertView.
					findViewById(R.id.job_Num);
			convertView.setTag(holder);
		}else{
			holder = (Holder)convertView.getTag();
		}
		RecruitmentInfo info = infos.get(position);
		holder.recuritment_title.setText(info.getName().trim());
		holder.recuritment_time.setText(info.getJobFairData().trim().substring(0, 16));
		holder.recuritment_adress.setText(info.getAddress().trim());
		holder.company_Num.setText(""+info.getCompanyNum());
		holder.job_Num.setText(""+info.getJobNum());
		return convertView;
	}

	private class Holder {
		TextView recuritment_title,recuritment_time,recuritment_adress,
		company_Num,job_Num;
	}
}
