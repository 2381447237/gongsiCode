package com.example.hospitalapp.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.example.hospitalapp.R;
import com.example.hospitalapp.entity.RecordContent;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecordAdapter extends BaseAdapter{

	private List<RecordContent> rContent;
	private Context context;
	private LayoutInflater inflater;

	
	public RecordAdapter(List<RecordContent> rContent, Context context) {
		super();
		this.rContent = rContent;
		this.context = context;
		inflater=LayoutInflater.from(context);
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
			
			convertView=inflater.inflate(R.layout.record_item,null);
			
			holder.itemLl=(LinearLayout) convertView.findViewById(R.id.ll_item_record);
			holder.answerTv=(TextView) convertView.findViewById(R.id.tv_answer);
			holder.phoneTv=(TextView) convertView.findViewById(R.id.tv_phone);
			holder.timeTv=(TextView) convertView.findViewById(R.id.tv_time);
			holder.scoreTv=(TextView) convertView.findViewById(R.id.tv_score);
			holder.ygxmTv=(TextView) convertView.findViewById(R.id.tv_ygxm);
			
			convertView.setTag(holder);
		}else{
			
			holder=(ViewHolder) convertView.getTag();
			
		}
		
		RecordContent rc=rContent.get(position);
		
		if(position%2!=0){			
			holder.itemLl.setBackgroundColor(Color.rgb(0xe9,0xf2,0Xf4));
			
		}else{
			holder.itemLl.setBackgroundColor(Color.rgb(0xbf,0xd9,0Xed));
			
		}
		
		holder.answerTv.setText(rc.NAME);
		holder.phoneTv.setText(rc.PHONE);
		
		holder.timeTv.setText(rc.CreateTime);
		holder.scoreTv.setText(String.valueOf(rc.ScoreSum));
		holder.ygxmTv.setText(rc.YGXM);
		
		return convertView;
	}

	class ViewHolder{
		
		TextView answerTv,phoneTv,timeTv,scoreTv,ygxmTv;
		LinearLayout itemLl;
	}
	
}
