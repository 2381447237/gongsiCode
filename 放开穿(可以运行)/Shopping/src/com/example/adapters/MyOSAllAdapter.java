package com.example.adapters;

import java.util.List;
import com.example.infoclass.MyOSAllContent;
import com.example.shopping.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyOSAllAdapter extends BaseAdapter{

	private List<MyOSAllContent> data;
	private Context context;
	private LayoutInflater inflater;
	
	public MyOSAllAdapter(List<MyOSAllContent> data, Context context) {
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
			convertView=inflater.inflate(R.layout.item_os_all,null);
			holder.iv1=(ImageView) convertView.findViewById(R.id.iv_os_all_content1);
			holder.iv2=(ImageView) convertView.findViewById(R.id.iv_os_all_content2);
			holder.iv3=(ImageView) convertView.findViewById(R.id.iv_os_all_content3);
			holder.tvPrice=(TextView) convertView.findViewById(R.id.tv_os_all_price);
			holder.tvState=(TextView) convertView.findViewById(R.id.tv_os_all_state);
			
		    convertView.setTag(holder);
		}else{
			
			holder=(ViewHolder) convertView.getTag();

		}

		MyOSAllContent c=data.get(position);
		
		holder.iv1.setImageResource(R.drawable.empty_photo);
		holder.iv2.setImageResource(R.drawable.empty_photo);
		holder.iv3.setImageResource(R.drawable.empty_photo);
		holder.tvPrice.setText(c.tvPrice);
		holder.tvState.setText(c.tvState);
		
		return convertView;
	}

	class ViewHolder{
		
		ImageView iv1,iv2,iv3;
		TextView tvPrice,tvState;
		
	}
	
}
