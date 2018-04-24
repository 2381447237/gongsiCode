package com.example.adapters;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

import okhttp3.Call;

import com.example.httpurl.HttpUrl;
import com.example.infoclass.Shopcart;
import com.example.secondlevelactivity.ShopcartActivity;
import com.example.shopping.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopcartAdapter extends BaseAdapter {
	private ShopcartActivity context;
	private ArrayList<Shopcart> list;
	private String MyShoppingCartDeleteUrl = HttpUrl.HttpURL
			+ "/Json/Set_UserChars_Info.aspx";
	private String ShowUrl = HttpUrl.HttpURL + "/Json/Get_UserChars_Info.aspx";

	public ShopcartAdapter(ShopcartActivity context,ArrayList<Shopcart> list) {
		super();
		this.context=context;
		this.list=list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		final ViewHolder holder;
		if(arg1==null){
			holder=new ViewHolder();
			arg1=LayoutInflater.from(context).inflate(R.layout.shopcartadapter, null);
			holder.img_gouwuche=(ImageView)arg1.findViewById(R.id.img_gouwuche);
			holder.tv_shopcartday=(TextView)arg1.findViewById(R.id.tv_shopcartday);
			holder.tv_itemname=(TextView)arg1.findViewById(R.id.tv_itemname);
			holder.tv_shopcartsize=(TextView)arg1.findViewById(R.id.tv_shopcartsize);
			holder.tv_shopcartcolor=(TextView)arg1.findViewById(R.id.tv_shopcartcolor);
			holder.tv_shopcartprice=(TextView)arg1.findViewById(R.id.tv_shopcartprice);
			holder.img_shopcartdelete=(ImageView)arg1.findViewById(R.id.img_shopcartdelete);
			holder.tv_daynum=(TextView) arg1.findViewById(R.id.tv_daynum);
			holder.img_plus=(ImageView) arg1.findViewById(R.id.iv_plus);
			holder.img_minus=(ImageView) arg1.findViewById(R.id.iv_minus);
			arg1.setTag(holder);
		}else{
			holder=(ViewHolder)arg1.getTag();
		}
		final Shopcart shopcart=list.get(position);
		Picasso.with(context).load(HttpUrl.HttpURL+shopcart.img).placeholder(R.drawable.defaultpicture).into(holder.img_gouwuche);
		holder.tv_shopcartday.setText(String.valueOf(shopcart.Quantity));
		holder.tv_itemname.setText(shopcart.ItemName);
		holder.tv_shopcartsize.setText(shopcart.SizeName);
		holder.tv_shopcartcolor.setText(shopcart.ColorName);
		DecimalFormat df = new DecimalFormat(".00");
		// 小数点后两位
		holder.tv_shopcartprice.setText(String.valueOf(df.format(shopcart.RentalPrice)));
		holder.tv_daynum.setText(shopcart.Quantity+"天");
		holder.img_shopcartdelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.showDialog(String.valueOf(shopcart.CartID),position);
			}
		});
		holder.img_minus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				context.showDialog(context);
				
				if(1==shopcart.Quantity){
					
					Toast.makeText(context, "不能再减了",0).show();
					
					context.DismissDialog();
					
					return;
					
				}
				
				holder.tv_daynum.setText((--shopcart.Quantity)+"天");
				
				if(position<list.size()){
					
					updateQuantity(position, shopcart.Quantity);
					
				}else{
					
					context.DismissDialog();
					
					Toast.makeText(context, "您的操作太频繁，请稍后再试", 0)
					.show();
					
				}
			}
		});
		
		holder.img_plus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				holder.tv_daynum.setText((++shopcart.Quantity)+"天");
				context.showDialog(context);
				if(position<list.size()){
					
					updateQuantity(position, shopcart.Quantity);
					
				}else{
					
                    context.DismissDialog();
					
					Toast.makeText(context, "您的操作太频繁，请稍后再试", 0)
					.show();
					
				}
				
			}
		});
		
		return arg1;
	}
	
	private void updateQuantity(final int p,final int num){
		
		if(p<list.size()){
			
			final int itemId=list.get(p).ItemID;
			final String colorName=list.get(p).ColorName;
			final String sizeName=list.get(p).SizeName;
			final String addTime=list.get(p).AddTime;
			
			OkHttpUtils.post().url(MyShoppingCartDeleteUrl).addParams("AcctID",context.userID)
			.addParams("CartID",String.valueOf(context.list.get(p).CartID))
			.addParams("IsDelete", String.valueOf(1)).build()
			.execute(new StringCallback() {
				
				@Override
				public void onResponse(String arg0) {
					changeQuantity(p,num,itemId,colorName,sizeName,addTime);
				}
				
				@Override
				public void onError(Call arg0, Exception arg1) {
					context.DismissDialog();
				}
			});
			
		}else{
			
			context.DismissDialog();
			
			Toast.makeText(context, "您的操作太频繁，请稍后再试", 0)
			.show();
			
		}
		
	}
	
	private void changeQuantity(int p,int num,int itemId,String colorName,String sizeName,
			String addTime){
		
		if(list!=null){
			
			list.clear();
			notifyDataSetChanged();
			context.DismissDialog();
		}
		
		OkHttpUtils.post().url(MyShoppingCartDeleteUrl)
		.addParams("AcctID", context.userID)
					.addParams("Item_Id", itemId + "")
					.addParams("ColorName", colorName)
					.addParams("SizeName", sizeName)
					.addParams("Quantity", num + "")
					.addParams("RentalStartDate", addTime).build()
					.execute(new StringCallback() {
						
						@Override
						public void onResponse(String arg0) {
							
							showShopCart();
							
						}
						
						@Override
						public void onError(Call arg0, Exception arg1) {
							context.DismissDialog();
							Toast.makeText(context, "请检查网络", 0)
							.show();
						}
					});
		
	}
	
	private void showShopCart(){
		
		OkHttpUtils.post().url(ShowUrl).addParams("AcctID",context.userID)
		.build().execute(new StringCallback() {
			
			@Override
			public void onResponse(final String str) {
				
				context.runOnUiThread(new Runnable() {
					public void run() {
					
						Gson gson=new Gson();
						
						Type listType=new TypeToken<LinkedList<Shopcart>>(){}.getType();
						
						LinkedList<Shopcart> lls=gson.fromJson(str,listType);
						
						for(Shopcart sc:lls){
							
							list.add(sc);
							
						}
						
						context.DismissDialog();
						notifyDataSetChanged();
						
						for(int i=0;i<list.size();i++){
							
							context.price += list.get(i).RentalPrice
									* list.get(i).Quantity;

							
						}
						context.tv_shopcarttotalprice.setText(String
								.valueOf(context.price));
						context.price=0;
						
					}
				});
				
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				context.DismissDialog();
				Toast.makeText(context, "请检查网络", 0)
				.show();
			}
		});
	}
	
	public class ViewHolder{
		ImageView img_gouwuche,img_shopcartdelete,img_minus,img_plus;
		TextView tv_shopcartday,tv_itemname,tv_shopcartsize,tv_shopcartcolor,tv_shopcartprice,tv_daynum;
	}
	
	

}
