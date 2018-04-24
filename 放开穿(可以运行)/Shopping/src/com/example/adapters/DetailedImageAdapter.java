package com.example.adapters;

import java.util.ArrayList;

import com.example.httpurl.HttpUrl;
import com.example.shopping.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class DetailedImageAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> list;
	
	public DetailedImageAdapter(Context context,ArrayList<String> list){
		this.context=context;
		this.list=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder holder;
		if(convertView==null){
			holder=new viewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.activity_detailedimage, null);
			holder.imageView=(ImageView)convertView.findViewById(R.id.img_detailed);
			convertView.setTag(holder);
		}else{
			holder=(viewHolder)convertView.getTag();
		}
		Picasso.with(context)
		.load(HttpUrl.HttpURL+list.get(position))
		.placeholder(R.drawable.defaultpicture)
		.into(holder.imageView);
		return convertView;
	}

	public class viewHolder{
		ImageView imageView;
	}
}
