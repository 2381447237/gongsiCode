package com.example.adapters;

import java.util.ArrayList;
import java.util.List;

import com.base.activity.RoundImageView;
import com.example.adapters.DetailedImageAdapter.viewHolder;
import com.example.cusview.CusviewViewPager;
import com.example.cusview.PullableGridView;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.Shop;
import com.example.layout.BidirSlidingLayout;
import com.example.secondlevelactivity.ChooseDaysActivity;
import com.example.secondlevelactivity.DetailActivity;
import com.example.secondlevelactivity.LoginActivity;
import com.example.shopping.R;
import com.example.shopping.ShopFragment;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.squareup.picasso.Picasso;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.sax.StartElementListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShopAdapter extends BaseAdapter {
	private ShopFragment context;
	private ArrayList<Shop> list;
	private String userID;
	boolean flag;

	public static int mItemId;
	public static String mColorName;
    public static String mSizeName;
	
	public ShopAdapter(ShopFragment context, ArrayList<Shop> list, String userID) {
		super();
		this.context = context;
		this.list = list;
		this.userID = userID;
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
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		SharedPreferences preferences = context.getActivity()
				.getSharedPreferences("user", Context.MODE_PRIVATE);
		String userID = preferences.getString("userID", "");

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
			holder.viewPager = (CusviewViewPager) (convertView)
					.findViewById(R.id.viewPager);
			holder.imageView = (ImageView) (convertView)
					.findViewById(R.id.img_collect1);
			holder.img_ivandvp = (ImageView) convertView
					.findViewById(R.id.shopadapter_ivAndVp);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Shop shop = list.get(position);
		holder.tv_ItemName.setText(shop.ItemName);
		holder.tv_IsCollect.setText(shop.RentalPrice + "");
		holder.tv_RetailPrice.setText(shop.RetailPrice + "");
		
		if (userID.equals("")) {
			holder.imageView.setImageResource(R.drawable.collect);
		} else {
			if (context.CheckCollectInfo(shop.ItemId + "")) {
				holder.imageView.setImageResource(R.drawable.selcollect);
				flag = true;
			} else {
				holder.imageView.setImageResource(R.drawable.collect);
				flag = false;
			}	
			if(1==shop.IsCollect){
				holder.imageView.setImageResource(R.drawable.selcollect);
				flag = true;
			}else if(0==shop.IsCollect){
				holder.imageView.setImageResource(R.drawable.collect);
				flag = false;
			}
			
		}
		holder.imageView.setOnClickListener(new OnClickListener() {
			boolean flog = flag;

			@Override
			public void onClick(View v) {

				SharedPreferences preferences = context.getActivity()
						.getSharedPreferences("user", Context.MODE_PRIVATE);
				String userID = preferences.getString("userID", "");

				if (!userID.equals("")) {
									
					if (flog) {
						holder.imageView.setImageResource(R.drawable.collect);
						context.delCollectInfo(shop.ItemId + "");
						context.cancelCollect(v, userID, shop.ItemId);
						flog = false;
						shop.IsCollect=0;
					} else {
						holder.imageView
								.setImageResource(R.drawable.selcollect);
						context.addCollectInfo(shop.ItemId + "");
						context.postCollect(v, userID, shop.ItemId);
						flog = true;
						shop.IsCollect=1;
					}
					
					Intent mIntent = new Intent("favorite");
					// 发送广播
					context.getActivity().sendBroadcast(mIntent);
				} else {
					Intent intent2 = new Intent(context.getActivity(),
							LoginActivity.class);
					context.getActivity().startActivity(intent2);
				}

				if (BidirSlidingLayout.isLeftDisplay == false
						&& BidirSlidingLayout.isRightDisplay == false) {

				} else {
					holder.imageView.setFocusable(false);
					// holder.imageView.setClickable(false);
					BidirSlidingLayout.isLeftDisplay = false;
					BidirSlidingLayout.isRightDisplay = false;
					context.zhiding.setVisibility(View.VISIBLE);

				}
			}
		});

		// 将图片装载到数组中
		ImageView[] mImageViews = new ImageView[shop.img.size()];

		if (mImageViews.length == 0) {
			holder.img_ivandvp.setVisibility(View.VISIBLE);
			holder.viewPager.setVisibility(View.INVISIBLE);
			holder.img_ivandvp.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (BidirSlidingLayout.isLeftDisplay == false
							&& BidirSlidingLayout.isRightDisplay == false) {
						Intent intent = new Intent(context.getActivity(),
								DetailActivity.class);
						intent.putExtra("shop", shop);
						context.startActivity(intent);
					} else {
						holder.img_ivandvp.setFocusable(false);
						BidirSlidingLayout.isLeftDisplay = false;
						BidirSlidingLayout.isRightDisplay = false;
						ShopFragment.zhiding.setVisibility(View.VISIBLE);
					}
				}
			});
		} else {

			holder.img_ivandvp.setVisibility(View.GONE);
			holder.viewPager.setVisibility(View.VISIBLE);

			for (int i = 0; i < mImageViews.length; i++) {
				ImageView imageView = new ImageView(context.getActivity());
				Picasso.with(context.getActivity())
						.load(HttpUrl.HttpURL + shop.img.get(i))
						.placeholder(R.drawable.defaultpicture)
						.error(R.drawable.defaultpicture).into(imageView);
				mImageViews[i] = imageView;
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (BidirSlidingLayout.isLeftDisplay == false
								&& BidirSlidingLayout.isRightDisplay == false) {
							Intent intent = new Intent(context.getActivity(),
									DetailActivity.class);
							mItemId = list.get(position).ItemId;
							mColorName = list.get(position).ColorName;
							mSizeName=list.get(position).SizeName;
							intent.putExtra("shop", shop);
							context.startActivity(intent);
						} else {
							holder.img_ivandvp.setFocusable(false);
							// view.setClickable(false);
							BidirSlidingLayout.isLeftDisplay = false;
							BidirSlidingLayout.isRightDisplay = false;
							ShopFragment.zhiding.setVisibility(View.VISIBLE);
						}

					}
				});
			}
		}
		holder.viewPager.setAdapter(new Myadapter(mImageViews, shop.img, shop,
				context.getActivity()));

		// holder.viewPager.setCurrentItem(0);
		holder.viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

		return convertView;
	}

	public class ViewHolder {
		CusviewViewPager viewPager;
		TextView tv_ItemName, tv_IsCollect, tv_RetailPrice;
		ImageView imageView, img_ivandvp;
	}

}
