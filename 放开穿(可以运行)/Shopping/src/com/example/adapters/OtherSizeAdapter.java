package com.example.adapters;

import com.example.adapters.ShopAdapter.ViewHolder;
import com.example.infoclass.Shop;
import com.example.shopping.R;
import com.example.shopping.ShopFragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;

public class OtherSizeAdapter extends BaseAdapter {

	private ShopFragment context;
	private String[] size;
	GradientDrawable drawable;
	//private boolean flag=true;
	
	public OtherSizeAdapter(ShopFragment context, String[] size) {
		super();
		this.context = context;
		this.size = size;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return size.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return size[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		 
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context.getActivity()).inflate(R.layout.size, null);
			holder.btn_size=(Button)(convertView).findViewById(R.id.btn_size);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		 drawable = new GradientDrawable();  
		drawable.setShape(GradientDrawable.RECTANGLE); // »­¿ò  
		drawable.setStroke(2, Color.GRAY);
		holder.btn_size.setText(size[position]);
		holder.btn_size.setBackgroundDrawable(drawable);
		holder.btn_size.setTextColor(0xffdfdfdf);
		if(context.CheckSizeInfo(holder.btn_size.getText()+"")){
			holder.btn_size.setTextColor(0xff000000);
			holder.btn_size.setBackgroundColor(0xffdfdfdf);
		}else{
			holder.btn_size.setTextColor(0xffdfdfdf);
			holder.btn_size.setBackgroundDrawable(drawable);
		}
		
		holder.btn_size.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				context.showDialog(context.getActivity());
				
				if(holder.btn_size.getCurrentTextColor()==0xffdfdfdf){
					holder.btn_size.setTextColor(0xff000000);
					holder.btn_size.setBackgroundColor(0xffdfdfdf);
					context.addSizeInfo(holder.btn_size.getText()+"");
				}else{
					holder.btn_size.setTextColor(0xffdfdfdf);
					holder.btn_size.setBackgroundDrawable(drawable);
					context.delSizeInfo(holder.btn_size.getText()+"");
				}
			}
			
		});
		return convertView;
	}
	
	public class ViewHolder{
		Button btn_size;
	}
}
