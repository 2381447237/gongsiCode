package com.example.adapters;

import java.util.List;

import com.example.shopping.R;
import com.exmaple.infoclass.ThirdlevelSize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ThirdlevelSizeAdapter extends BaseAdapter{

	private List<ThirdlevelSize> data;
	private Context context;
	private LayoutInflater inflater;
	
	public ThirdlevelSizeAdapter(List<ThirdlevelSize> data, Context context) {
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
			
			convertView=inflater.inflate(R.layout.item_thirdlevelsize,null);
			
			viewHolder.tv_size=(TextView) convertView.findViewById(R.id.tv_thirdlevelsize);
			
			convertView.setTag(viewHolder);
		}else{
			
			viewHolder=(ViewHolder) convertView.getTag();
			
		}
		
		ThirdlevelSize tls=data.get(position);
		
		viewHolder.tv_size.setText(tls.sizeCategory);
		
		return convertView;
	}

	class ViewHolder{
		
		TextView tv_size;
		
	}
	
}
