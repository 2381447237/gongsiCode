package com.example.secondlevelactivity;

import org.json.JSONException;

import com.base.activity.BaseActivity;
import com.base.activity.ShopApplication;
import com.base.activity.VollyUtils;
import com.base.activity.VollyUtils.VolleyListener;
import com.example.httpurl.HttpUrl;
import com.example.shopping.R;
import com.example.shopping.R.id;
import com.example.shopping.R.layout;
import com.example.shopping.R.menu;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.renderscript.Type;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener,VolleyListener{

	private TextView tv_regist,tv_forget;
	private ImageView img_back,img_eye;
	private boolean flag;
	private EditText edit_passwd,edit_zhanghao;
	private Button btn_denglu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		initView();
	}
	private void initView() {
		tv_regist=(TextView)findViewById(R.id.tv_regist);
		img_back=(ImageView)findViewById(R.id.img_back);
		img_eye=(ImageView)findViewById(R.id.img_eye);
		edit_passwd=(EditText)findViewById(R.id.edit_passwd);
		edit_zhanghao=(EditText)findViewById(R.id.edit_zhanghao);
		tv_forget=(TextView)findViewById(R.id.tv_forget);
		btn_denglu=(Button)findViewById(R.id.btn_denglu);
		tv_regist.setOnClickListener(this);
		img_back.setOnClickListener(this);
		img_eye.setOnClickListener(this);
		tv_forget.setOnClickListener(this);
		btn_denglu.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_regist:
			Intent intent=new Intent(LoginActivity.this, RegistActivity.class);
			startActivity(intent);
			break;

		case R.id.img_back:
			finish();
			break;
		case R.id.tv_forget:
			ShopApplication.REGIST_FLAG=2;
			Intent intent2=new Intent(LoginActivity.this, ChangePasswordActivity.class);
			startActivity(intent2);
			break;
		case R.id.img_eye:
			if(flag){
				img_eye.setImageResource(R.drawable.passwd_off);
				edit_passwd.setTransformationMethod(PasswordTransformationMethod.getInstance()); 
				flag=false;
			}else{
				img_eye.setImageResource(R.drawable.passwd_on);
				edit_passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); 
				flag=true;
			}
			edit_passwd.postInvalidate();
			break;
		case R.id.btn_denglu:
			if(edit_zhanghao.getText().toString().trim().equals("")||edit_passwd.getText().toString().trim().equals("")){
				Toast.makeText(getApplicationContext(), "账号和密码不能为空", 1000).show();
			}else if(edit_zhanghao.getText().length()!=11){
				Toast.makeText(getApplicationContext(), "手机号错误", 1000).show();
			}else{
				login();
			}
		}
	}
	private void login() {
		try {
			ShopApplication.utils
					.init(this)
					.setListener(this)
					.getJson(
							HttpUrl.HttpURL+"/Login.aspx?AcctNo="
									+ edit_zhanghao.getText() + "&Pwd="
									+ edit_passwd.getText());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void getJson(String response) {
		if(response.equals("False")){
			Toast.makeText(getApplicationContext(), "手机号或密码有误", 1000).show();
		}else{
			Toast.makeText(getApplicationContext(), "登陆成功", 1000).show();
			SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
			Editor editor=preferences.edit();
			editor.putString("userID", response);
			editor.putString("phone", edit_zhanghao.getText().toString().trim());
			editor.commit();
			Intent mIntent = new Intent("mineinfo");
			// 发送广播
			sendBroadcast(mIntent);
			finish();
		}
	}
	
	@Override
	public void getFiel() {
		// TODO Auto-generated method stub
		
	}

}
