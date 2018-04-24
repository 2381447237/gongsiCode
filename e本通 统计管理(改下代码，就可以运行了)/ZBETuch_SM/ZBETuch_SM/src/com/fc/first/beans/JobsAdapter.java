package com.fc.first.beans;

import java.util.ArrayList;

import com.fc.zbetuch_sm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class JobsAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<GetJobs> getjobslist;
	
	public JobsAdapter(Context context, ArrayList<GetJobs> getjobslist) {
		super();
		this.context = context;
		this.getjobslist = getjobslist;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public ArrayList<GetJobs> getGetjobslist() {
		return getjobslist;
	}

	public void setGetjobslist(ArrayList<GetJobs> getjobslist) {
		this.getjobslist = getjobslist;
	}

	@Override
	public String toString() {
		return "JobsAdapter [context=" + context + ", getjobslist="
				+ getjobslist + "]";
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getjobslist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getjobslist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		QueryItemHodler itemHodler;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_getjobsbase, null);
			itemHodler = new QueryItemHodler();
			itemHodler.tv_jobs_first_jobname = (TextView)convertView.findViewById(R.id.tv_jobs_first_jobname);
			itemHodler.tv_jobs_first_modifydate = (TextView)convertView.findViewById(R.id.tv_jobs_first_modifydate);
			convertView.setTag(itemHodler);
		}else{
			itemHodler = (QueryItemHodler)convertView.getTag();
		}
		GetJobs jobsinfo = getjobslist.get(position);
		itemHodler.tv_jobs_first_jobname.setText(jobsinfo.getJobname());
		itemHodler.tv_jobs_first_modifydate.setText(jobsinfo.getModifydate().substring(0, 19));
		return convertView;
	}
	
	class QueryItemHodler{
		TextView tv_jobs_first_jobname,tv_jobs_first_modifydate; 
	}
	
	public void addPersonItem(ArrayList<GetJobs> list){
		setGetjobslist(list);
	}
}
