package com.example.adapters;

import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.base.activity.BitmapCache;
import com.base.activity.MySingleton;
import com.example.adapters.ShopAdapter.ViewHolder;
import com.example.httpurl.HttpUrl;
import com.example.shopping.R;
import com.exmaple.infoclass.Shop;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> list;
	public ImageAdapter(ArrayList<String> list,Context context)
	{
		this.list=list;
		this.context=context;
		mQueue = MySingleton.getInstance(context).
			    getRequestQueue();
		imageLoader= new ImageLoader(mQueue, new BitmapCache());
	}
	 RequestQueue mQueue ;
	private ImageLoader imageLoader;
	
	//返回数据源的数量
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
		return position;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.networkimageview, null);
			holder.networkImageView=(NetworkImageView)convertView.findViewById(R.id.networkimage);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.networkImageView.setDefaultImageResId(R.drawable.apppicture);  
		holder.networkImageView.setErrorImageResId(R.drawable.apppicture);  
		holder.networkImageView.setImageUrl(HttpUrl.HttpURL+list.get(position),  
		                imageLoader);
		return convertView;
	}
	
	public class ViewHolder{
		NetworkImageView networkImageView;
	}
	
	
}
