package com.example.secondlevelactivity;

import okhttp3.Call;

import com.example.httpurl.HttpUrl;
import com.example.infoclass.UserInfo;
import com.example.shopping.R;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

public class SetSexActivity extends Activity implements OnClickListener {

	private ImageView img_back;
	private Button btn_sure;
	private RadioButton rb_nan, rb_nv,rb_secrecy;
	private String userID;
	private String myInfoUrl = HttpUrl.HttpURL
			+ "/Json/Set_UpdateUserInfo.aspx";
	private String getInfoUrl = HttpUrl.HttpURL
			+ "/Json/Get_UserInfoForUserId.aspx";

	// 0保密，1男，2女
	private int sexId;
	private String myNickname;
    private String sexStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setsex);

		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);

		btn_sure = (Button) findViewById(R.id.btn_sure);
		btn_sure.setOnClickListener(this);

		rb_nan = (RadioButton) findViewById(R.id.rb_nan);
		rb_nan.setOnClickListener(this);

		rb_nv = (RadioButton) findViewById(R.id.rb_nv);
		rb_nv.setOnClickListener(this);

		rb_secrecy=(RadioButton) findViewById(R.id.rb_secrecy);
		rb_secrecy.setOnClickListener(this);
		
		Intent intent=getIntent();
		
		sexStr=intent.getStringExtra("nickname");
		
		if(TextUtils.equals("男", sexStr)){
			rb_nan.setChecked(true);
			rb_nv.setChecked(false);
			rb_secrecy.setChecked(false);
		}else if(TextUtils.equals("女", sexStr)){
			rb_nv.setChecked(true);
			rb_nan.setChecked(false);
			rb_secrecy.setChecked(false);
		}else if(TextUtils.equals("保密", sexStr)){
			rb_secrecy.setChecked(true);
			rb_nv.setChecked(false);
			rb_nan.setChecked(false);
		}
		
		
		
	}

	@Override
	protected void onResume() {

		super.onResume();

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.img_back:

			finish();

			break;

		case R.id.rb_nan:
			getUserId();
			sexId = 1;
			break;
		case R.id.rb_nv:
			getUserId();
			sexId = 2;	
			break;		
		case R.id.rb_secrecy:
			getUserId();
			sexId = 0;
			break;

		default:
			break;
		}

	}

	private void setSex() {

		OkHttpUtils.post().url(myInfoUrl).addParams("AcctID", userID)
				.addParams("daveText", myNickname)
				.addParams("sexText", String.valueOf(sexId)).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(String arg0) {

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

		getAllInfo();

	}

	private void getAllInfo() {

		OkHttpUtils.post().url(getInfoUrl).addParams("AcctID", userID).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						runOnUiThread(new Runnable() {
							public void run() {

								Gson gson=new Gson();
								
								UserInfo uIo=gson.fromJson(str, UserInfo.class);
								
								myNickname=uIo.UserName;
								
								setSex();
								
							}
						});

					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

}
