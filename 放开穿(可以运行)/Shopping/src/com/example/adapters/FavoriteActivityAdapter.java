package com.example.adapters;

import java.util.List;

import okhttp3.Call;

import com.example.httpurl.HttpUrl;
import com.example.infoclass.Shop;
import com.example.shopping.FavoriteActivity;
import com.example.shopping.R;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FavoriteActivityAdapter extends BaseAdapter {

	private FavoriteActivity context;
	private List<Shop> list;
	private String myDeleteOrAddFavoriteUrl = HttpUrl.HttpURL+"/Json/Set_Favorites_Info.aspx";

	private int itemId;

	public FavoriteActivityAdapter(FavoriteActivity context, List<Shop> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;

		if (convertView == null) {

			holder = new ViewHolder();

			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_favoriteactivity, null);

			holder.tv_ItemName = (TextView) convertView
					.findViewById(R.id.tv_ItemName_favoriteactivity);
			holder.tv_IsCollect = (TextView) convertView
					.findViewById(R.id.tv_IsCollect_favoriteactivity);
			holder.tv_RetailPrice = (TextView) convertView
					.findViewById(R.id.tv_RetailPrice_favoriteactivity);
			holder.viewPager = (ViewPager) convertView
					.findViewById(R.id.viewPager_favoriteactivity);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.img_collect1_favoriteactivity);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		Shop shop = list.get(position);
		holder.tv_ItemName.setText(shop.ItemName);
		holder.tv_IsCollect.setText(shop.RentalPrice + "");
		holder.tv_RetailPrice.setText(shop.RetailPrice + "");
		// 注意下面这里啊
		// if(shop.IsCollect==1){
		// holder.imageView.setImageResource(R.drawable.selcollect);//选中的图片
		// }

		
		itemId = context.itemIdList.get(position);
		
		if (list.get(position).isCheck()) {
			holder.imageView.setImageResource(R.drawable.selcollect);// 选中的图片
			// itemId=context.itemIdList.get(position);
			addFavoriteGoods();
		} else {
			holder.imageView.setImageResource(R.drawable.collect);// 未选中的图片
			
			deleteFavoriteGoods();
		}

		holder.imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				list.get(position).setCheck(!list.get(position).isCheck);

				notifyDataSetChanged();
				
				//下面4句话是重点
				Intent mIntent = new Intent("shopfragment");
				// 发送广播
				context.sendBroadcast(mIntent);
				Intent mIntent2 = new Intent("favorite");
				// 发送广播
				context.sendBroadcast(mIntent2);
			}
		});

		// 将图片装载到数组中
		ImageView[] mImageViews = new ImageView[shop.img.size()];
		for (int i = 0; i < mImageViews.length; i++) {

			ImageView imageView = new ImageView(context);
			Picasso.with(context).load(HttpUrl.HttpURL + shop.img.get(i))
					.placeholder(R.drawable.defaultpicture).into(imageView);
			mImageViews[i] = imageView;
		}

		holder.viewPager.setAdapter(new Myadapter(mImageViews, shop.img, shop,
				context));

		holder.viewPager.setCurrentItem(0);
		holder.viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

		return convertView;
	}

	private void addFavoriteGoods() {

		OkHttpUtils.post().url(myDeleteOrAddFavoriteUrl)
				.addParams("Item_Id", String.valueOf(itemId))
				.addParams("AcctID", context.userID).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(String arg0) {

					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

	private void deleteFavoriteGoods() {

		OkHttpUtils.post().url(myDeleteOrAddFavoriteUrl)
				.addParams("Item_Id", String.valueOf(itemId))
				.addParams("AcctID", context.userID)
				.addParams("IsFavorite", String.valueOf(1)).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(String arg0) {

					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

	private class ViewHolder {

		ViewPager viewPager;
		TextView tv_ItemName, tv_IsCollect, tv_RetailPrice;
		ImageView imageView;

	}

}
