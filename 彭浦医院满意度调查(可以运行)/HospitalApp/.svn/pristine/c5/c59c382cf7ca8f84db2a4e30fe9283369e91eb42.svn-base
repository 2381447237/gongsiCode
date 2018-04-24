package com.example.hospitalapp.nonetwork.adapter;

import java.util.List;

import com.example.hospitalapp.R;
import com.example.hospitalapp.entity.RecordContent;
import com.example.hospitalapp.sqlite.RecordNonetWorkContent;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecordNonetWorkAdapter extends BaseAdapter {

	private List<RecordNonetWorkContent> rContent;
	private Context context;
	private LayoutInflater inflater;

	public RecordNonetWorkAdapter(List<RecordNonetWorkContent> rContent, Context context) {
		super();
		this.rContent = rContent;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return rContent.size();
	}

	@Override
	public Object getItem(int position) {
		return rContent.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		
		if(convertView==null){
			
			holder=new ViewHolder();
			
			convertView=inflater.inflate(R.layout.record_item_nonetwork,null);
			
			holder.itemLl=(LinearLayout) convertView.findViewById(R.id.ll_item_record_nonetwork);
			holder.numberTv=(TextView) convertView.findViewById(R.id.tv_number_nonetwork);
			holder.answerTv=(TextView) convertView.findViewById(R.id.tv_answer_nonetwork);
			holder.phoneTv=(TextView) convertView.findViewById(R.id.tv_phone_nonetwork);
			holder.timeTv=(TextView) convertView.findViewById(R.id.tv_time_nonetwork);
			holder.scoreTv=(TextView) convertView.findViewById(R.id.tv_score_nonetwork);
			holder.ygxmTv=(TextView) convertView.findViewById(R.id.tv_ygxm_nonetwork);

			convertView.setTag(holder);
		}else{
			
			holder=(ViewHolder) convertView.getTag();
			
		}
		
		RecordNonetWorkContent rc=rContent.get(position);
		
		if(position%2!=0){			
			holder.itemLl.setBackgroundColor(Color.rgb(0xe9,0xf2,0Xf4));
			
		}else{
			holder.itemLl.setBackgroundColor(Color.rgb(0xbf,0xd9,0Xed));
			
		}
		holder.numberTv.setText(""+rc.MASTERID);
		holder.answerTv.setText(rc.NAME);
		holder.phoneTv.setText(rc.PHONE);
		
		String a[]=rc.CreateTime.split(":");
		
		holder.timeTv.setText(a[0]+":"+a[1]);
		holder.scoreTv.setText(String.valueOf(rc.ScoreSum));
		holder.ygxmTv.setText(rc.YGXM);
		
		return convertView;
	}

	class ViewHolder {

		TextView numberTv,answerTv, phoneTv, timeTv, scoreTv, ygxmTv;
		LinearLayout itemLl;
	}

}
