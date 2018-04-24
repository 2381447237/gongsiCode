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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class NickNameActivity extends Activity implements OnClickListener {

	private ImageView img_back;
	private Button btn_sure;
	private EditText et_nickname;
	private String myNickname;
	private String myInfoUrl = HttpUrl.HttpURL+"/Json/Set_UpdateUserInfo.aspx";
	private String getInfoUrl=HttpUrl.HttpURL+"/Json/Get_UserInfoForUserId.aspx";
	private String userID;
	//0保密，1男，2女
	private int  sexId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nickname);

		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);

		btn_sure = (Button) findViewById(R.id.btn_sure);
		btn_sure.setOnClickListener(this);

		et_nickname = (EditText) findViewById(R.id.et_nickname);

		Intent intent = getIntent();
		et_nickname.setHint(intent.getStringExtra("nickname"));
		
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.img_back:

			finish();

			break;

		case R.id.btn_sure:

			getUserId();
			
//			myNickname = et_nickname.getText().toString().trim();
//
//			if (TextUtils.isEmpty(myNickname)) {
//				Toast.makeText(this, "昵称不能为空", 0).show();
//				return;
//			}
//
//			changeNickname();

			break;

		default:
			break;
		}

	}

	private void changeNickname() {

		OkHttpUtils.post().url(myInfoUrl).addParams("AcctID", userID)
				.addParams("daveText", myNickname)
				.addParams("sexText", String.valueOf(sexId))
				// .addParams("phoneText","")
				// .addParams("Email","")
				// .addParams("IdCard","")
				// .addParams("Birthday","")
				.build().execute(new StringCallback() {

					@Override
					public void onResponse(String str) {
						
						finish();
					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

	private void changeNicknameHint() {

		OkHttpUtils.post().url(myInfoUrl).addParams("AcctID", userID)
				.addParams("daveText", et_nickname.getHint().toString())
				.addParams("sexText", String.valueOf(1))
				// .addParams("phoneText","")
				// .addParams("Email","")
				// .addParams("IdCard","")
				// .addParams("Birthday","")
				.build().execute(new StringCallback() {

					@Override
					public void onResponse(String str) {

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

	private void getAllInfo(){
		
		OkHttpUtils.post().url(getInfoUrl).addParams("AcctID",userID).build().execute(new StringCallback() {
			
			@Override
			public void onResponse(final String str) {
				
				runOnUiThread( new Runnable() {
					public void run() {
						
						Gson gson=new Gson();
						
						UserInfo uIo = gson.fromJson(str,
								UserInfo.class);
						
						String sexStr=uIo.Sex;
						
						if(TextUtils.equals("男",sexStr)){
							sexId=1;
						}else if(TextUtils.equals("女",sexStr)){
							sexId=2;
						}else if(TextUtils.equals("保密",sexStr)){
							sexId=0;
						}
						
						myNickname = et_nickname.getText().toString().trim();

						if (TextUtils.isEmpty(myNickname)) {
							Toast.makeText(NickNameActivity.this, "昵称不能为空", 0).show();
							return;
						}

						changeNickname();
						
					}
				});
				
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				
			}
		});
		
	}
	
}
