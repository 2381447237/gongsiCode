package com.example.adapters;

import java.util.ArrayList;

import com.base.activity.RoundImageView;
import com.example.adapters.ShopAdapter.ViewHolder;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.Shop;
import com.example.infoclass.ShopSame;
import com.example.shopping.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SameStyleAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<ShopSame> list;

	public SameStyleAdapter(Context context, ArrayList<ShopSame> list) {
		super();
		this.context = context;
		this.list = list;

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
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.samestyle, null);
			holder.imageView=(ImageView)convertView.findViewById(R.id.img_same);
			holder.tv_ItemName = (TextView) (convertView)
					.findViewById(R.id.tv_ItemName);
			holder.tv_IsCollect = (TextView) (convertView)
					.findViewById(R.id.tv_IsCollect);
			holder.tv_RetailPrice = (TextView) (convertView)
					.findViewById(R.id.tv_RetailPrice);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ShopSame shop = list.get(position);
		holder.tv_ItemName.setText(shop.ItemName);
		holder.tv_IsCollect.setText(shop.RentalPrice + "");
		holder.tv_RetailPrice.setText(shop.RetailPrice + "");
			Picasso.with(context).load(HttpUrl.HttpURL + shop.img.get(0))
			.placeholder(R.drawable.defaultpicture).into(holder.imageView);
		return convertView;
	}

	public class ViewHolder {
		ImageView imageView;
		TextView tv_ItemName, tv_IsCollect, tv_RetailPrice;
	}

}
