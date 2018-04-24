package com.example.adapters;

import java.util.List;

import okhttp3.Call;

import com.example.httpurl.HttpUrl;
import com.example.infoclass.FootPrint;
import com.example.secondlevelactivity.MyFootPrint;
import com.example.shopping.R;
import com.example.shopping.ShopFragment;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FootPrintAdapter extends BaseAdapter{

	private List<FootPrint> data;
	private MyFootPrint context;
	private LayoutInflater inflater;
	private String userID;
	//http://web.youli.pw:85/Json/Set_FootMake.aspx?AcctID=106&isDelete=1&CookieID=
	
	private String deleteUrl=HttpUrl.HttpURL+"/Json/Set_FootMake.aspx";
	
	public FootPrintAdapter(List<FootPrint> data, MyFootPrint context) {
		super();
		this.data = data;
		this.context = context;
		inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		
		if(convertView==null){
			
			holder=new ViewHolder();
			
			convertView=inflater.inflate(R.layout.item_footprint,null);
			
			holder.contentIv=(ImageView) convertView.findViewById(R.id.img_footprint_item);
			holder.chaIv=(ImageView) convertView.findViewById(R.id.iv_footprint_delete);
			holder.titleTv=(TextView) convertView.findViewById(R.id.tv_itemname);
			holder.leaseTv=(TextView) convertView.findViewById(R.id.tv_lease_item);
			holder.retailTv=(TextView) convertView.findViewById(R.id.tv_retail_item);
			
			convertView.setTag(holder);
		}else{
			
			holder=(ViewHolder) convertView.getTag();
			
		}
		
		FootPrint fp=data.get(position);
		
		Picasso.with(context).load(HttpUrl.HttpURL+fp.Img).placeholder(R.drawable.defaultpicture).error(R.drawable.defaultpicture).into(holder.contentIv);
		holder.titleTv.setText(fp.ItemName);
		holder.leaseTv.setText(String.valueOf(fp.RentalPrice));
		holder.retailTv.setText(String.valueOf(fp.RetailPrice));
		holder.chaIv.setImageResource(R.drawable.cha);
		holder.chaIv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				context.showDialog(context);
				
				deleteFp(position);
				
			}
		});
		
		return convertView;
	}

	private void deleteFp(final int p){
		
		SharedPreferences preferences = context.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");
		
		OkHttpUtils.post().url(deleteUrl).addParams("AcctID",userID).addParams("isDelete",String.valueOf(1)).
		addParams("CookieID",String.valueOf(data.get(p).CookieID)).build().execute(new StringCallback() {
			
			@Override
			public void onResponse(String arg0) {
				
				
						
					    data.remove(p);//注意这句话，如果没有的话，adapter不能刷新的
					 
					    notifyDataSetChanged();
						
						context.DismissDialog();
						
						if(data.size()==0){
							context.ll_fp.setVisibility(View.VISIBLE);
						}else{
							context.ll_fp.setVisibility(View.GONE);
						}
				
				
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				Toast.makeText(context, "请检查网络",0).show();
			}
		});
		
		
	}
	
	
	
	class ViewHolder{
		
		ImageView contentIv,chaIv;
		TextView titleTv,leaseTv,retailTv;
		
	}
	
}
