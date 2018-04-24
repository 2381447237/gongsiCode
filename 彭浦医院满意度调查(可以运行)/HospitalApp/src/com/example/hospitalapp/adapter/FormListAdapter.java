package com.example.hospitalapp.adapter;

import java.util.List;

import com.example.hospitalapp.R;
import com.example.hospitalapp.entity.FormListContent;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FormListAdapter extends BaseAdapter{

	private List<FormListContent> data;
	private Context context;
	private LayoutInflater inflater;
	
	public FormListAdapter(List<FormListContent> data, Context context) {
		super();
		this.data = data;
		this.context = context;
		inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		
		if(convertView==null){
			
			viewHolder=new ViewHolder();
			
			convertView=inflater.inflate(R.layout.formlist_item,null);
			
			viewHolder.ll=(LinearLayout) convertView.findViewById(R.id.item_ll);
			
			viewHolder.tv=(TextView) convertView.findViewById(R.id.item_tv);
			
			viewHolder.iv=(ImageView) convertView.findViewById(R.id.item_iv);
			
			convertView.setTag(viewHolder);
		}else{
			
			viewHolder=(ViewHolder) convertView.getTag();
			
		}
		
		FormListContent flc=data.get(position);
		
		if(position%2!=0){			
			viewHolder.ll.setBackgroundColor(Color.rgb(0xe9,0xf2,0Xf4));
			viewHolder.iv.setImageResource(R.drawable.qian);
		}else{
			viewHolder.ll.setBackgroundColor(Color.rgb(0xbf,0xd9,0Xed));
			viewHolder.iv.setImageResource(R.drawable.sheng);
		}
		viewHolder.tv.setText(flc.TITLE);
		
		return convertView;
	}

	class ViewHolder{
		
		LinearLayout ll;
		TextView tv;
		ImageView iv;
	}
	
}
