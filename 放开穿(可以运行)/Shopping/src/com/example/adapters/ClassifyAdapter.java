package com.example.adapters;

import java.util.ArrayList;

import com.example.httpurl.HttpUrl;
import com.example.infoclass.Classify;
import com.example.shopping.R;
import com.squareup.picasso.Picasso;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ClassifyAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Classify> list;
	public ClassifyAdapter(ArrayList<Classify> list,Context context) {
		super();
		this.list=list;
		this.context=context;
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
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.networkimageview, null);
			holder.imageView=(ImageView)(convertView).findViewById(R.id.imageview);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		Classify classify=list.get(position);
//		holder.networkImageView.setDefaultImageResId(R.drawable.apppicture);  
//		holder.networkImageView.setErrorImageResId(R.drawable.apppicture);  
//		holder.networkImageView.setImageUrl(HttpUrl.HttpURL+classify.ImgPath,  
//		                imageLoader);	
		Picasso.with(context)  
	    .load(HttpUrl.HttpURL+classify.ImgPath)  
	    .placeholder(R.drawable.defaultpicture)
	    .into(holder.imageView);  
		return convertView;
	}
	
	public class ViewHolder{
		ImageView imageView;
	}

}
