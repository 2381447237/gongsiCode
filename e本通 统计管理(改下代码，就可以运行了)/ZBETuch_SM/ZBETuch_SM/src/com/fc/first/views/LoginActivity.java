package com.fc.first.views;


import com.fc.first.beans.UpdateManager;
import com.fc.main.tools.HttpUrls_;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 登陆界面
 * 
 * @author hxl clf
 * 
 */
public class LoginActivity extends Activity implements OnClickListener {
	
	
	public static void Vibrate(final Activity activity, long milliseconds) {
		Vibrator vib = (Vibrator) activity
				.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);

	}
	private EditText et_user;//用户名
	private EditText et_pwd;//密码
	private TextView tv_about,tv_versions;// 关于，版本
	private ImageButton btn_login;//登录按钮
	LocationManager locationManager;
	Context context =LoginActivity.this;//上下文
	private String userName;//用户名
	private String userPwd;//密码
	private String loginJudgment;//登录判断信息
	private PopupWindow popupWindow;
	private LayoutInflater mLayoutInflater;
	private View aboutview; //关于界面
	private String version; //版本信息
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainlogin);
		tv_about =(TextView)findViewById(R.id.tv_about);
		et_user = (EditText) findViewById(R.id.et_name);
		et_pwd = (EditText) findViewById(R.id.et_loginPwd);
		btn_login = (ImageButton) findViewById(R.id.imgbtn_login);
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		//更新apk
		UpdateManager manager = new UpdateManager(LoginActivity.this);
		manager.checkUpdate();
		btn_login.setOnClickListener(this);
		if (locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER) != true) {
			Toast.makeText(context, "请勾选GPS为打开状态", Toast.LENGTH_SHORT)
			.show();
			Intent callGPSSettingIntent = new Intent(
					android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(callGPSSettingIntent);
			
		}
		tv_about.setOnClickListener(this);		
		SharedPreferences sp = context.getSharedPreferences("username", MODE_PRIVATE);
		et_user.setText(sp.getString("USER_NAME", ""));
	}
	/**
	 * 登录信息处理
	 */
	private Handler handler_judgment = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0x30:
				Log.i("huode de mima ???****%%%%%", loginJudgment);
				if(loginJudgment.equals("true")){
					SharedPreferences spPreferences = context.getSharedPreferences("username", MODE_PRIVATE);
					Editor editor = spPreferences.edit();
					editor.putString("USER_NAME", et_user.getText().toString());
					editor.commit();
					ShowAbout();
				}else if(loginJudgment.equals("false")){
					Toast.makeText(LoginActivity.this, "对不起，您输入的账号或密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(LoginActivity.this, "连接超时", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
		//关于对话框
		private void ShowAbout() {
			AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
			PackageManager manager = getPackageManager();
			try {
				version = manager.getPackageInfo("com.fc.zbetuch_sm", 0).versionName;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			dialog.setMessage(version+"\n关于本软件归属静安就业信息！").setCancelable(true).setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(LoginActivity.this, FirstPageActivity.class);
					startActivity(intent);
					finish();
				}
			});
			AlertDialog alert = dialog.create();
			alert.show();
			
		}
		
	};
	/**
	 * 登录按钮点击监听
	 */
	@Override
	public void onClick(View v) {
		String getUserName = null;
		String getUserPwd = null;
		switch(v.getId()){
		case R.id.imgbtn_login:
			userName = et_user.getText().toString();
			userPwd = et_pwd.getText().toString();
			if (userName.length() == 0||userPwd.length()==0) {
				Toast.makeText(LoginActivity.this, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
			}else {
				Intent intent = new Intent();
				intent.putExtra(getUserName, userName);
				intent.putExtra(getUserPwd, userPwd);
				Vibrate(LoginActivity.this,80);
				if (locationManager
						.isProviderEnabled(LocationManager.GPS_PROVIDER) != true) {
					Toast.makeText(context, "请勾选GPS为打开状态", Toast.LENGTH_SHORT)
					.show();
					Intent callGPSSettingIntent = new Intent(
							android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(callGPSSettingIntent);
				}else {
					//发送数据到服务器
					//判断用户名密码是否正确
					new Thread(judgment_thread).start();
				}
				
			}
			break;
		case R.id.tv_about:
			ShowPopupWindow(v);
			break;
		}
	}
	private void ShowPopupWindow(View v) {
		if(popupWindow == null){
			 getLayoutInflater();
			mLayoutInflater = LayoutInflater.from(LoginActivity.this);
			 aboutview = mLayoutInflater.inflate(R.layout.activity_about, null);
			 tv_versions =(TextView)aboutview.findViewById(R.id.tv_versions);
			 popupWindow = new PopupWindow(aboutview, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);	 
		}
		PackageManager manager = getPackageManager();
		try {
			version = manager.getPackageInfo("com.fc.zbetuch_sm", 0).versionName;
			tv_versions.setText(version);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
	}
	/**
	 * 开启线程，判断用户登录信息
	 */
	Runnable judgment_thread = new Runnable() {
		
		@Override
		public void run() {
		    loginJudgment = HttpUrls_.login(LoginActivity.this,userName, userPwd);
			Message msg_judgment = new Message();
			msg_judgment.what = 0x30;
			msg_judgment.obj = loginJudgment;
			handler_judgment.obtainMessage(0x30, loginJudgment);
			handler_judgment.sendMessage(msg_judgment);
		}
	};

}
