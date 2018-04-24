package com.example.shopping;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;

import com.alipay.sdk.protocol.a;
import com.example.adapters.MyAddressmanageAdapter;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.DetailedAddressInfo;
import com.example.infoclass.MyAddressManage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddressManageActivity extends Activity implements OnClickListener,
		SwipeRefreshLayout.OnRefreshListener {

	private ImageView img_back, img_addressmanage_add;
	private LinearLayout layout_addressmanage;
	private ListView lv;
	private List<MyAddressManage> addressManageData = new ArrayList<MyAddressManage>();
	private MyAddressmanageAdapter mamAdapter;
	//private String addressManageUrl = "http://web.youli.pw:85/Json/Get_User_Address_Info.aspx";
	private String addressManageUrl = HttpUrl.HttpURL+"/Json/Get_User_Address_Info.aspx";
	//private String setDefaultUrl = "http://web.youli.pw:85/Json/Set_User_Address_Info.aspx";
	private String setDefaultUrl =HttpUrl.HttpURL+"/Json/Set_User_Address_Info.aspx";
	private Gson gson;
	private Dialog progressDialog;
	public static List<Integer> addressIdList = new ArrayList<Integer>();
	public String userID;

	// private String Response;

	private int myDistrictID;
	private String zipCodeStr;
	private String detailAddrStr;
	private DetailedAddressInfo daInfo = new DetailedAddressInfo();

	private SwipeRefreshLayout mSwipeLayout;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			if (msg.what == 2) {
				for (int i = 0; i < addressManageData.size(); i++) {
					if (i == msg.arg1) {
						addressManageData.get(i).isEditcheck = true;
						addressManageData.get(i).IsDefault = true;

						Toast.makeText(AddressManageActivity.this, "设置为默认地址", 0)
								.show();

						getAllData(i);

					} else {
						addressManageData.get(i).isEditcheck = false;
						addressManageData.get(i).IsDefault = false;
					}
					mamAdapter.notifyDataSetChanged();
				}
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addressmanage);

		getUserId();

		img_back = (ImageView) findViewById(R.id.img_addressmanage_back);
		img_back.setOnClickListener(this);

		img_addressmanage_add = (ImageView) findViewById(R.id.img_addressmanage_add);
		img_addressmanage_add.setOnClickListener(this);

		layout_addressmanage = (LinearLayout) findViewById(R.id.layout_addressmanage);
		layout_addressmanage.setVisibility(View.INVISIBLE);
		lv = (ListView) findViewById(R.id.lv_addressmanage);

		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.my_swipe_ly);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_red_light,
				android.R.color.holo_green_dark,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light);

		showDialog(this);

		getJSONOkhttpPost();

		dismissDialog();
	}

	private void getJSONOkhttpPost() {

		OkHttpUtils.post().url(addressManageUrl).addParams("AcctID", userID)
				.build().execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						runOnUiThread(new Runnable() {

							@Override
							public void run() {

								Type listType = new TypeToken<LinkedList<MyAddressManage>>() {
								}.getType();

								gson = new Gson();

								LinkedList<MyAddressManage> mams = gson
										.fromJson(str, listType);

								for (Iterator iterator = mams.iterator(); iterator
										.hasNext();) {

									MyAddressManage m = (MyAddressManage) iterator
											.next();

									addressManageData.add(m);

									addressManageData.get(0).isEditcheck = true;

									addressIdList.add(m.AddressId);

								}

								if (NewaddActivity.isNewaddAddress == false
										|| EditAddressActivity.isEdit == false) {
									mamAdapter = new MyAddressmanageAdapter(
											handler, addressManageData,
											AddressManageActivity.this);
									lv.setAdapter(mamAdapter);
								}

								if (str == null || str.isEmpty()
										|| str.equals("[]")) {

									layout_addressmanage
											.setVisibility(View.VISIBLE);
								} else {

									layout_addressmanage
											.setVisibility(View.INVISIBLE);
								}
							}
						});
					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

	private void getUserId() {

		SharedPreferences preferences = this.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");

	}

	private void getAllData(final int i) {

		OkHttpUtils
				.post()
				.url(addressManageUrl)
				.addParams("AcctID", userID)
				.addParams("AddrID",
						String.valueOf(addressManageData.get(i).AddressId))
				.build().execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						runOnUiThread(new Runnable() {
							public void run() {

								Gson gson = new Gson();

								daInfo = gson.fromJson(str,
										DetailedAddressInfo.class);

								myDistrictID = daInfo.DistrictID;
								zipCodeStr = daInfo.ZipCode;
								detailAddrStr = daInfo.DetailAddr;

								setDefaultUrl(i);
							}
						});

					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

	private void setDefaultUrl(int i) {

		OkHttpUtils
				.post()
				.url(setDefaultUrl)
				.addParams("AcctID", userID)
				.addParams("IsDefault", String.valueOf(1))
				.addParams("DistrictID", String.valueOf(myDistrictID))
				.addParams("Address", detailAddrStr)
				.addParams("AddrID",
						String.valueOf(addressManageData.get(i).AddressId))
				.addParams("ZipCode", zipCodeStr)
				.addParams("PhoneNumber", addressManageData.get(i).PhoneNumber)
				.addParams("Consigner", addressManageData.get(i).Name).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(String str) {

					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.img_addressmanage_back:
			finish();
			addressManageData.clear();
			if (addressManageData != null && !addressManageData.isEmpty()) {
				mamAdapter.notifyDataSetChanged();
			}
			break;

		case R.id.img_addressmanage_add:
			Intent intent = new Intent(this, NewaddActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (addressManageData != null) {
				addressManageData.clear();
			}
			if (mamAdapter != null) {
				mamAdapter.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {

		super.onResume();

		// if (Response== null||Response.isEmpty()) {
		// layout_addressmanage
		// .setVisibility(View.VISIBLE);
		// } else {
		// layout_addressmanage
		// .setVisibility(View.INVISIBLE);
		// }

		if (mamAdapter != null) {
			mamAdapter.notifyDataSetChanged();
		}

		if (NewaddActivity.isNewaddAddress == true) {
			getJSONOkhttpPost();
			mamAdapter.notifyDataSetChanged();
			addressManageData.clear();
			addressIdList.clear();
			NewaddActivity.isNewaddAddress = false;
		}
		if (EditAddressActivity.isEdit == true) {
			getJSONOkhttpPost();
			mamAdapter.notifyDataSetChanged();
			addressManageData.clear();
			addressIdList.clear();
			EditAddressActivity.isEdit = false;
		}

	}

	private void showDialog(Context context) {

		progressDialog = new Dialog(context, R.style.progress_dialog);
		progressDialog.setContentView(R.layout.dialog);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		TextView msg = (TextView) progressDialog
				.findViewById(R.id.id_tv_loadingmsg);
		msg.setText("正在加载中...");
		progressDialog.show();

	}

	// 提示框消失处理
	private void dismissDialog() {
		if (AddressManageActivity.this != null && progressDialog != null
				&& progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	@Override
	public void onRefresh() {
		refresh();
	}

	private void refresh() {

		OkHttpUtils.post().url(addressManageUrl).addParams("AcctID", userID)
				.build().execute(new StringCallback() {

					@Override
					public void onResponse(final String arg0) {
						runOnUiThread(new Runnable() {
							public void run() {
								if (mamAdapter != null) {
									mamAdapter.notifyDataSetChanged();
								}
								mSwipeLayout.setRefreshing(false);

								if (arg0 == null || arg0.isEmpty()
										|| arg0.equals("[]")) {

									layout_addressmanage
											.setVisibility(View.VISIBLE);
								} else {

									layout_addressmanage
											.setVisibility(View.INVISIBLE);
								}
							}
						});
					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}
}
