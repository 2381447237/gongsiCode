package com.example.secondlevelactivity;

import org.json.JSONException;

import com.base.activity.BaseActivity;
import com.base.activity.ShopApplication;
import com.base.activity.VollyUtils.VolleyListener;
import com.example.httpurl.HttpUrl;
import com.example.shopping.R;
import com.example.shopping.R.id;
import com.example.shopping.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class AddressActivity extends BaseActivity implements VolleyListener,OnClickListener {

	private ListView listView_address;
	private String userID;
	private ImageView address_plus,addressback;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
		userID=preferences.getString("userID", "");
		initView();
		initData();
	}
	private void initData() {
		try {
			ShopApplication.utils
					.init(this)
					.setListener(this)
					.getJson(
							HttpUrl.HttpURL+"/Json/Get_User_Address_Info.aspx?AcctID="+userID+"&AddrID="
									);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	private void initView() {
		listView_address=(ListView)findViewById(R.id.listview_address);
		address_plus=(ImageView)findViewById(R.id.address_plus);
		addressback=(ImageView)findViewById(R.id.img_addressback);
		address_plus.setOnClickListener(this);
		addressback.setOnClickListener(this);
	}
	@Override
	public void getJson(String response) {
		
	}
	@Override
	public void getFiel() {
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.address_plus:
			Intent intent=new Intent(AddressActivity.this, AddaddressActivity.class);
			startActivity(intent);
			break;

		case R.id.img_addressback:
			finish();
			break;
		}
	}
}
