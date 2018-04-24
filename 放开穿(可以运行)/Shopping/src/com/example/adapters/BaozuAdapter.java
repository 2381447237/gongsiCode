package com.example.adapters;

import java.util.List;

import com.example.infoclass.BaozuContent;
import com.example.shopping.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaozuAdapter extends BaseAdapter{

	private List<BaozuContent> data;
	private Context context;
	private LayoutInflater inflater;
	
	public BaozuAdapter(List<BaozuContent> data, Context context) {
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
		
		ViewHolder holder;
		
		if(convertView==null){
			
			holder=new ViewHolder();
			
			convertView=inflater.inflate(R.layout.item_baozu,null);
			
			holder.ll=(LinearLayout) convertView.findViewById(R.id.ll_baozu);
			holder.name=(TextView) convertView.findViewById(R.id.tv_name_baozu);
			holder.price=(TextView) convertView.findViewById(R.id.tv_price_baozu);
			
			convertView.setTag(holder);
			
		}else{
			
			holder=(ViewHolder) convertView.getTag();
			
		}
		
		BaozuContent c=data.get(position);
		
		holder.name.setText(c.PackageName);
		holder.price.setText(c.Description);
		
		if(c.isSelect()){
			holder.ll.setBackgroundResource(R.drawable.becomevip_select);
		}else{
			holder.ll.setBackgroundResource(R.drawable.becomevip);
		}
		
		return convertView;
	}

	class ViewHolder{
		
		LinearLayout ll;
		
		TextView name,price;
		
	}
	
}
