package com.example.adapters;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;

import com.example.httpurl.HttpUrl;
import com.example.infoclass.Shop;
import com.example.infoclass.ShopDetail;
import com.example.infoclass.Shopcart;
import com.example.secondlevelactivity.ShopcartActivity;
import com.example.shopping.AddressManageActivity;
import com.example.shopping.R;
import com.example.shopping.ShoppingCartFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopcartAdapter2 extends BaseAdapter {

	private ShoppingCartFragment context;
	private List<Shopcart> list;
	private String MyShoppingCartDeleteUrl = HttpUrl.HttpURL
			+ "/Json/Set_UserChars_Info.aspx";
	private String ShowUrl = HttpUrl.HttpURL + "/Json/Get_UserChars_Info.aspx";

	public ShopcartAdapter2(ShoppingCartFragment context, List<Shopcart> list) {
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
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context.getActivity()).inflate(
					R.layout.item_shoppingcart, null);
			holder.img_gouwuche = (ImageView) convertView
					.findViewById(R.id.img_gouwuche_item);
			// holder.tv_shopcartday=(TextView)arg1.findViewById(R.id.tv_shopcartday_item);
			holder.tv_itemname = (TextView) convertView
					.findViewById(R.id.tv_itemname_item);
			holder.tv_shopcartsize = (TextView) convertView
					.findViewById(R.id.tv_shopcartsize_item);
			holder.tv_shopcartcolor = (TextView) convertView
					.findViewById(R.id.tv_shopcartcolor_item);
			holder.tv_shopcartprice = (TextView) convertView
					.findViewById(R.id.tv_shopcartprice_item);
			holder.img_shopcartdelete = (ImageView) convertView
					.findViewById(R.id.img_shopcartdelete_item);
			holder.tv_daynum = (TextView) convertView
					.findViewById(R.id.tv_daynum_item);
			holder.img_minus = (ImageView) convertView
					.findViewById(R.id.iv_minus_item);
			holder.img_plus = (ImageView) convertView
					.findViewById(R.id.iv_plus_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Shopcart shopcart = list.get(position);
		Picasso.with(context.getActivity())
				.load(HttpUrl.HttpURL + shopcart.img)
				.placeholder(R.drawable.defaultpicture)
				.into(holder.img_gouwuche);
		// holder.tv_shopcartday.setText(String.valueOf(shopcart.Quantity));
		holder.tv_itemname.setText(shopcart.ItemName);
		holder.tv_shopcartsize.setText(shopcart.SizeName);
		holder.tv_shopcartcolor.setText(shopcart.ColorName);
		DecimalFormat df = new DecimalFormat(".00");
		// 小数点后两位
		holder.tv_shopcartprice.setText(String.valueOf(df
				.format(shopcart.RentalPrice)));
		// holder.tv_shopcartprice.setText(String.valueOf(shopcart.RentalPrice));
		holder.img_shopcartdelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(position);
			}
		});

		holder.tv_daynum.setText(shopcart.Quantity + "天");

		holder.img_minus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				context.showDialog(context.getActivity());
				
				if (1 == shopcart.Quantity) {

					Toast.makeText(context.getActivity(), "不能再减了", 0).show();

					context.DismissDialog();
					
					return;

				}

				holder.tv_daynum.setText((--shopcart.Quantity) + "天");
				
				if (position < list.size()) {

				updateQuantity(position, shopcart.Quantity);

				} else {
					
					context.DismissDialog();
					
					Toast.makeText(context.getActivity(), "您的操作太频繁，请稍后再试", 0)
							.show();
				}
				
				//notifyDataSetChanged();
			}
		});

		holder.img_plus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				holder.tv_daynum.setText((++shopcart.Quantity) + "天");
				context.showDialog(context.getActivity());
				if (position < list.size()) {

					updateQuantity(position, shopcart.Quantity);

				} else {
					
					context.DismissDialog();
					
					Toast.makeText(context.getActivity(), "您的操作太频繁，请稍后再试", 0)
							.show();
				}
				//notifyDataSetChanged();
			}
		});

		return convertView;
	}

	private void updateQuantity(final int p, final int num) {

		if (p < list.size()) {

			final int itemId = list.get(p).ItemID;
			final String colorName = list.get(p).ColorName;
			final String sizeName = list.get(p).SizeName;
			final String addTime = list.get(p).AddTime;

			OkHttpUtils
					.post()
					.url(MyShoppingCartDeleteUrl)
					.addParams("AcctID", context.userID)
					.addParams("CartID",
							String.valueOf(context.list.get(p).CartID))
					.addParams("IsDelete", String.valueOf(1)).build()
					.execute(new StringCallback() {

						@Override
						public void onResponse(String str) {

							// list.clear();
							changeQuantity(p, num, itemId, colorName, sizeName,
									addTime);

						}

						@Override
						public void onError(Call arg0, Exception arg1) {

							context.DismissDialog();
							
						}
					});

		} else {
			
			context.DismissDialog();
			
			Toast.makeText(context.getActivity(), "您的操作太频繁，请稍后再试", 0).show();
		}

	}

	private void changeQuantity(int p, int num, int itemId, String colorName,
			String sizeName, String addTime) {
		if (list != null) {
			list.clear();
			notifyDataSetChanged();
			
			context.DismissDialog();
		}
		try {

			OkHttpUtils.post().url(MyShoppingCartDeleteUrl)
					.addParams("AcctID", context.userID)
					.addParams("Item_Id", itemId + "")
					.addParams("ColorName", colorName)
					.addParams("SizeName", sizeName)
					.addParams("Quantity", num + "")
					.addParams("RentalStartDate", addTime).build()
					.execute(new StringCallback() {

						@Override
						public void onResponse(final String str) {

							showShopCart();

						}

						@Override
						public void onError(Call arg0, Exception arg1) {

							context.DismissDialog();
							
							Toast.makeText(context.getActivity(), "请检查网络", 0)
									.show();
						}
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showShopCart() {

		// http://web.youli.pw:85/Json/Get_UserChars_Info.aspx?AcctID=80

		OkHttpUtils.post().url(ShowUrl).addParams("AcctID", context.userID)
				.build().execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {
						//Log.i("2016/12/26", "show成功2016/12/27" + str);
						context.getActivity().runOnUiThread(new Runnable() {
							public void run() {

								Gson gson = new Gson();

								Type listType = new TypeToken<LinkedList<Shopcart>>() {
								}.getType();

								LinkedList<Shopcart> lls = gson.fromJson(str,
										listType);

								for (Shopcart sc : lls) {

									list.add(sc);

								}

								context.DismissDialog();
								
								notifyDataSetChanged();

								for (int i = 0; i < list.size(); i++) {

									context.totalPrice += list.get(i).RentalPrice
											* list.get(i).Quantity;

								}
								context.tv_shopcarttotalprice.setText(String
										.valueOf(context.totalPrice));
								
								context.totalPrice = 0;

							}
						});

					}

					@Override
					public void onError(Call arg0, Exception arg1) {
		
						context.DismissDialog();
						
						Toast.makeText(context.getActivity(), "请检查网络", 0)
								.show();
					}
				});

	}

	private void showDialog(final int position) {

		final AlertDialog dialog = new AlertDialog.Builder(
				context.getActivity()).create();

		View view = LayoutInflater.from(context.getActivity()).inflate(
				R.layout.dialog_layout, null);
		dialog.setView(view);
		dialog.show();

		Window window = dialog.getWindow();
		window.setContentView(R.layout.dialog_layout);

		TextView tv_dialog = (TextView) window.findViewById(R.id.tv_dialog);
		tv_dialog.setText("您确定删除么？");

		Button btnOk = (Button) window.findViewById(R.id.btn_ok);
		Button btnUndo = (Button) window.findViewById(R.id.btn_undo);
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				deleteGoods(position);
				list.remove(position);
				notifyDataSetChanged();
				dialog.dismiss();
			}
		});

		btnUndo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.dismiss();
			}
		});

	};

	private void deleteGoods(int position) {

		OkHttpUtils
				.post()
				.url(MyShoppingCartDeleteUrl)
				.addParams("AcctID", context.userID)
				.addParams("CartID",
						String.valueOf(context.list.get(position).CartID))
				.addParams("IsDelete", String.valueOf(1)).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(String str) {

						context.getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {

								for (int i = 0; i < list.size(); i++) {

									context.totalPrice += list.get(i).RentalPrice
											* list.get(i).Quantity;

								}
									context.tv_shopcarttotalprice.setText(String
											.valueOf(context.totalPrice));
								if (list.size() == 0) {
									context.tv_shopcarttotalprice
											.setText(String.valueOf(0));
									context.linearlayout2
											.setVisibility(View.INVISIBLE);
									context.ll_noGoods
											.setVisibility(View.VISIBLE);
								} else {
									context.ll_noGoods
											.setVisibility(View.INVISIBLE);
								}
								context.totalPrice = 0;
							}
						});

					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

	public class ViewHolder {
		ImageView img_gouwuche, img_shopcartdelete, img_minus, img_plus;
		TextView tv_shopcartday, tv_itemname, tv_shopcartsize,
				tv_shopcartcolor, tv_shopcartprice, tv_daynum;
	}

}
