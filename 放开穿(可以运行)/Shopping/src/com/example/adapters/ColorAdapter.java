package com.example.adapters;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.base.activity.BitmapCache;
import com.base.activity.MySingleton;
import com.base.activity.ShopApplication;
import com.example.adapters.SizeAdapter.ViewHolder;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.MyColorCheck;
import com.example.shopping.R;
import com.example.shopping.ShopFragment;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class ColorAdapter extends BaseAdapter {
	private ShopFragment context;
	private String[] color;
	private ShopApplication app;
	// private Bitmap choosemap;
	public static List<MyColorCheck> myColorList = new ArrayList<MyColorCheck>();
	public static String AllColorName = "";

	public ColorAdapter(ShopFragment context, String[] color) {
		super();
		this.context = context;
		this.color = color;
		app = (ShopApplication) context.getActivity().getApplication();
	}

	@Override
	public int getCount() {
		return color.length;
	}

	@Override
	public Object getItem(int position) {
		return color[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			if (color[position].length() <= 6) {
				convertView = LayoutInflater.from(context.getActivity())
						.inflate(R.layout.color, null);
				holder.btn_color = (ImageButton) (convertView)
						.findViewById(R.id.btn_color);
				holder.imageViewMiddle = (ImageView) convertView
						.findViewById(R.id.btn_color_middle);
			} else {
				convertView = LayoutInflater.from(context.getActivity())
						.inflate(R.layout.colornetworkimageview, null);
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.colornetworkimage);
				holder.colorNetworkImageMiddle = (ImageView) convertView
						.findViewById(R.id.colornetworkimage_middle);
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final MyColorCheck mcc = myColorList.get(position);


		if (context.CheckColorInfo(color[position])) {
			if (color[position].length() <= 6) {
				holder.btn_color.setBackgroundColor(Color.parseColor("#"
						+ color[position]));
				holder.btn_color.setImageDrawable(context.getResources()
						.getDrawable(R.drawable.rightsel));
				//=====================================================
				if (mcc.isChecked()) {
					holder.imageViewMiddle.setVisibility(View.VISIBLE);
				} else {
					holder.imageViewMiddle.setVisibility(View.INVISIBLE);
				}
				
			} else {
				String[] bb = color[position].substring(3,
						color[position].length()).split("=");
				try {
					Picasso.with(context.getActivity())
							.load(HttpUrl.HttpURL
									+ bb[0]
									+ "="
									+ java.net.URLEncoder
											.encode(bb[1], "utf-8"))
							.placeholder(R.drawable.apppicture)
							.into(holder.imageView);

					holder.imageView.setDrawingCacheEnabled(true);
					holder.imageView.buildDrawingCache(true);
					holder.imageView.setImageBitmap(app.getBitmap());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				//=====================================================
				if (mcc.isChecked()) {
					holder.colorNetworkImageMiddle
							.setVisibility(View.VISIBLE);
				} else {
					holder.colorNetworkImageMiddle
							.setVisibility(View.INVISIBLE);
				}
			}
		} else {
			if (color[position].length() <= 6) {
				// 下面这句是给方形的ImageButton填充颜色的
				holder.btn_color.setBackgroundColor(Color.parseColor("#"
						+ color[position]));
				//=======================================================
				if (mcc.isChecked()) {
					holder.imageViewMiddle.setVisibility(View.VISIBLE);
				} else {
					holder.imageViewMiddle.setVisibility(View.INVISIBLE);
				}
			} else {
				String[] cc = color[position].substring(3,
						color[position].length()).split("=");
				try {
					Picasso.with(context.getActivity())
							.load(HttpUrl.HttpURL
									+ cc[0]
									+ "="
									+ java.net.URLEncoder
											.encode(cc[1], "utf-8"))
							.placeholder(R.drawable.apppicture)
							.into(holder.imageView);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				//==============================================
				if (mcc.isChecked()) {
					holder.colorNetworkImageMiddle
							.setVisibility(View.VISIBLE);
				} else {
					holder.colorNetworkImageMiddle
							.setVisibility(View.INVISIBLE);
				}
			}
		}
		
		if (color[position].length() <= 6) {

			holder.btn_color.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					context.showDialog(context.getActivity());
					
					context.gridView.setSelection(0);
					
					myColorList.get(position).setChecked(
							!myColorList.get(position).isChecked());
					if (ShopFragment.isChangeCategory_Id == true) {
						AllColorName = "";
						ShopFragment.isChangeCategory_Id = false;
					}
					if (mcc.isChecked()) {
						holder.imageViewMiddle.setVisibility(View.VISIBLE);
					} else {
						holder.imageViewMiddle.setVisibility(View.INVISIBLE);
					}
					AllColorName = "";
					String reg = "[^\u4e00-\u9fa5,]";
					for (MyColorCheck mcc : myColorList) {
						if (mcc.isChecked())

							AllColorName += (mcc.getImageButtonBg().length() <= 6 ? mcc
									.getImageButtonBg() + ","
									: mcc.getImageButtonBg()
											.replaceAll(reg, "") + ",");
					}
					context.addSizeInfo("");
				}
			});
		} else {
			holder.imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					context.showDialog(context.getActivity());
					
					context.gridView.setSelection(0);
					
					myColorList.get(position).setChecked(
							!myColorList.get(position).isChecked());
					if (ShopFragment.isChangeCategory_Id == true) {
						AllColorName = "";
						ShopFragment.isChangeCategory_Id = false;
					}
					if (mcc.isChecked()) {
						holder.colorNetworkImageMiddle
								.setVisibility(View.VISIBLE);
					} else {
						holder.colorNetworkImageMiddle
								.setVisibility(View.INVISIBLE);
					}
					AllColorName = "";
					String reg = "[^\u4e00-\u9fa5,]";
					for (MyColorCheck mcc : myColorList) {
						if (mcc.isChecked())

							AllColorName += (mcc.getImageButtonBg().length() <= 6 ? mcc
									.getImageButtonBg() + ","
									: mcc.getImageButtonBg()
											.replaceAll(reg, "") + ",");
					}
					// 将字符串中除了文字的其他字母，符号都去除掉
					context.addSizeInfo("");
				}
			});
		}

		return convertView;
	}

	public class ViewHolder {
		ImageButton btn_color;
		ImageView imageView, imageViewMiddle, colorNetworkImageMiddle;
	}

}
