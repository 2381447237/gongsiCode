package com.example.adapters;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import com.example.httpurl.HttpUrl;
import com.example.infoclass.DetailedAddressInfo;
import com.example.infoclass.EditAddress;
import com.example.infoclass.MyAddressManage;
import com.example.shopping.AddressManageActivity;
import com.example.shopping.EditAddressActivity;
import com.example.shopping.MineInfomationFragment;
import com.example.shopping.R;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAddressmanageAdapter extends BaseAdapter {

	private List<MyAddressManage> data;
	private AddressManageActivity context;
	private LayoutInflater inflater;
	private Handler handler;
	
	private String deleteAddressUrl = HttpUrl.HttpURL+"/Json/Set_User_Address_Info.aspx";
	private String editAddressUrl = HttpUrl.HttpURL+"/Json/Get_User_Address_Info.aspx";
	private Gson gson;
	private int deleteAddressId;
	public static int editAddressId;
	
	public MyAddressmanageAdapter(Handler handler, List<MyAddressManage> data,
			AddressManageActivity context) {
		super();
		this.handler = handler;
		this.data = data;
		this.context = context;
		inflater = LayoutInflater.from(context);
		
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

		ViewHolder viewHolder;

		if (convertView == null) {

			viewHolder = new ViewHolder();

			convertView = inflater.inflate(R.layout.item_addressmanage, null);

			viewHolder.name = (TextView) convertView
					.findViewById(R.id.tv_addressmanage_name);
			viewHolder.phonenumber = (TextView) convertView
					.findViewById(R.id.tv_addressmanage_phonenumber);
			viewHolder.address = (TextView) convertView
					.findViewById(R.id.tv_addressmanage_address);
			viewHolder.editcheck = (ImageView) convertView
					.findViewById(R.id.iv_addressmanage_editcheck);
			viewHolder.addressedit = (ImageView) convertView
					.findViewById(R.id.iv_addressmanage_addressedit);
			viewHolder.delete = (ImageView) convertView
					.findViewById(R.id.iv_addressmanage_delete);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		MyAddressManage mAddressManage = data.get(position);

		viewHolder.name.setText(mAddressManage.Name);
		viewHolder.phonenumber.setText(mAddressManage.PhoneNumber.substring(0,
				3)
				+ "****"
				+ mAddressManage.PhoneNumber.substring(7,
						mAddressManage.PhoneNumber.length()));
		viewHolder.address.setText(mAddressManage.Address);

		if (mAddressManage.isEditcheck == true) {
			viewHolder.editcheck
					.setImageResource(R.drawable.addressmanage_editcheck);
		} else {
			viewHolder.editcheck
					.setImageResource(R.drawable.addressmanage_edituncheck);
		}
		viewHolder.editcheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Message msg = handler.obtainMessage();

				msg.arg1 = position;
				msg.what = 2;

				handler.sendMessage(msg);

			}
		});

		viewHolder.addressedit
				.setImageResource(R.drawable.addressmanage_addressedit);
		viewHolder.addressedit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				editAddressId = AddressManageActivity.addressIdList
						.get(position);
				obtainEditAddressInfo();
			}
		});
		viewHolder.delete.setImageResource(R.drawable.addressmanage_delete);
		viewHolder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				for(int i=0;i<data.size();i++){
					
					if(data.get(position).IsDefault==true){
						
						Toast.makeText(context,"默认地址不可被删除",0).show();
						
						return;
					}
					
				}
				
					showDialog(position);
			
			}
		});

		return convertView;
	}
	
	private void obtainEditAddressInfo() {

		OkHttpUtils.post().url(editAddressUrl)
				.addParams("AcctID",context.userID)
				.addParams("AddrID", Integer.toString(editAddressId)).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						new Thread(new Runnable() {

							@Override
							public void run() {

								gson = new Gson();

								EditAddress ea = gson.fromJson(str,
										EditAddress.class);

								Intent intent = new Intent(context,
										EditAddressActivity.class);
								Bundle mBundle = new Bundle();
								mBundle.putSerializable("editAddress", ea);
								intent.putExtras(mBundle);
								context.startActivity(intent);

							}
						}).start();
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						
					}
				});

	}

	private void deleteAddress() {

		OkHttpUtils.post().url(deleteAddressUrl)
				.addParams("AcctID", context.userID)
				.addParams("IsDelete", Integer.toString(1))
				.addParams("AddrID", Integer.toString(deleteAddressId)).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(String arg0) {
						
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
					
					}
				});
	}

	class ViewHolder {

		TextView name;
		TextView phonenumber;
		TextView address;
		ImageView editcheck;
		ImageView addressedit;
		ImageView delete;

	}

	private void showDialog(final int position){
		
		final AlertDialog dialog=new AlertDialog.Builder(context).create();
		
		View view=LayoutInflater.from(context).inflate(
				R.layout.newadd_dialog_layout,null);
		dialog.setView(view);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		Window window=dialog.getWindow();
		window.setContentView(R.layout.newadd_dialog_layout);
		TextView tv_dialog=(TextView) window.findViewById(R.id.newadd_tv_dialog);
		tv_dialog.setText("您确定要删除吗？");
		Button btnOk=(Button) window.findViewById(R.id.newadd_btn_ok);
		Button btnUndo=(Button) window.findViewById(R.id.newadd_btn_undo);
		
		btnUndo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				// 删除收货地址	
				deleteAddressId = AddressManageActivity.addressIdList
						.get(position);
				deleteAddress();
				data.remove(position);
				AddressManageActivity.addressIdList.remove(position);
				notifyDataSetChanged();
			}
		});
	}
	
}
