package com.example.adapters;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import com.base.activity.RoundImageView;
import com.example.adapters.ShopAdapter.ViewHolder;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.Shop;
import com.example.secondlevelactivity.LoginActivity;
import com.example.shopping.FavoriteFragment;
import com.example.shopping.R;
import com.example.shopping.ShopFragment;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectAdapter extends BaseAdapter {

	private FavoriteFragment context;
	private List<Shop> list;
	private String myDeleteOrAddFavoriteUrl = HttpUrl.HttpURL+"/Json/Set_Favorites_Info.aspx";

	private int itemId;

	public CollectAdapter(FavoriteFragment context, List<Shop> list) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context.getActivity()).inflate(
					R.layout.shopadapter, null);
			holder.tv_ItemName = (TextView) (convertView)
					.findViewById(R.id.tv_ItemName);
			holder.tv_IsCollect = (TextView) (convertView)
					.findViewById(R.id.tv_IsCollect);
			holder.tv_RetailPrice = (TextView) (convertView)
					.findViewById(R.id.tv_RetailPrice);
			holder.viewPager = (ViewPager) (convertView)
					.findViewById(R.id.viewPager);
			holder.imageView = (ImageView) (convertView)
					.findViewById(R.id.img_collect1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Shop shop = list.get(position);
		holder.tv_ItemName.setText(shop.ItemName);
		holder.tv_IsCollect.setText(shop.RentalPrice + "");
		holder.tv_RetailPrice.setText(shop.RetailPrice + "");
		// if(shop.IsCollect==1){
		// holder.imageView.setImageResource(R.drawable.selcollect);
		// }

		itemId = context.itemIdList.get(position);

		if (list.get(position).isCheck()) {
			holder.imageView.setImageResource(R.drawable.selcollect);// 选中的图片
			addFavoriteGoods();
		} else {
			holder.imageView.setImageResource(R.drawable.collect);// 未选中的图片
			deleteFavoriteGoods();
		}

		holder.imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// context.deleteCollect(shop.ItemId,position);
				// ShopFragment.delCollectInfo(shop.ItemId+"");
				// ShopFragment.shopAdapter.notifyDataSetChanged();
				Intent mIntent = new Intent("shopfragment");
				// 发送广播
				context.getActivity().sendBroadcast(mIntent);

				list.get(position).setCheck(!list.get(position).isCheck());
				notifyDataSetChanged();
			}
		});
		// 将图片装载到数组中
		ImageView[] mImageViews = new ImageView[shop.img.size()];
		for (int i = 0; i < mImageViews.length; i++) {
			// RoundImageView imageView = new
			// RoundImageView(context.getActivity());
			// imageView.setBorderRadius(15);
			// imageView.setType(1);
			// Picasso.with(context.getActivity()).load(HttpUrl.HttpURL +
			// shop.img.get(i))
			// .placeholder(R.drawable.defaultpicture).into(imageView);
			// mImageViews[i] = imageView;

			ImageView imageView = new ImageView(context.getActivity());
			Picasso.with(context.getActivity())
					.load(HttpUrl.HttpURL + shop.img.get(i))
					.placeholder(R.drawable.defaultpicture).into(imageView);
			mImageViews[i] = imageView;
		}
		holder.viewPager.setAdapter(new Myadapter(mImageViews, shop.img, shop,
				context.getActivity()));
		//
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
		.addParams("IsFavorite",String.valueOf(1)).build()
		.execute(new StringCallback() {

			@Override
			public void onResponse(String arg0) {

			}

			@Override
			public void onError(Call arg0, Exception arg1) {

			}
		});
		
	}

	public class ViewHolder {
		ViewPager viewPager;
		TextView tv_ItemName, tv_IsCollect, tv_RetailPrice;
		ImageView imageView;
	}
}
