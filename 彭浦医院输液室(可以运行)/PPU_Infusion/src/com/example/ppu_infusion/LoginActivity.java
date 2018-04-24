package com.example.ppu_infusion;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.Seats.views.SeatsActivity;
import com.example.helper.UpdateManager;
import com.example.services.MainDaos;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText user, pwd;
	private Button btn_ok;
	//private String value;
	private String userName;// 用户名
	private String userPwd;// 密码
	private String loginJudgment;// 登录判断信息
	private SharedPreferences mySharedPreferences;
	private List<AdminInfo> infos = new ArrayList<AdminInfo>();
	MainDaos mainDaos = new MainDaos();
	private TextView viestcoid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置屏幕无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置屏幕全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_login);
		UpdateManager manager = new UpdateManager(this);
		manager.checkUpdate();
		initview();
		
		
		//Log.i("2017/1/16","测试");
	}

	public void initview() {
		Intent service = new Intent("com.example.services.MainService");
		startService(service);
		mySharedPreferences = getSharedPreferences("login", 0);
		
		user = (EditText) findViewById(R.id.user);
		pwd = (EditText) findViewById(R.id.pwd);
		btn_ok = (Button) findViewById(R.id.login);
		user.setText(mySharedPreferences.getString("loginName", ""));
		user.setSelection(mySharedPreferences.getString("loginName", "")
				.length());
		btn_ok.setOnClickListener(this);
		viestcoid = (TextView) findViewById(R.id.viestcoid);
		try {
			viestcoid
					.setText("V"
							+ LoginActivity.this
									.getPackageManager()
									.getPackageInfo(
											LoginActivity.this.getPackageName(),
											0).versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login:
			userName = user.getText().toString().trim();
			userPwd = pwd.getText().toString().trim();
			if (userName.length() == 0) {

				Toast.makeText(LoginActivity.this, "用户名不能为空！请重新输入。。。",
						Toast.LENGTH_SHORT).show();
			} else {

				new Thread(judgment_thread).start();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 登录信息处理
	 */
	private Handler handler_judgment = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x30:
			//	Log.i("huode de mima ???****%%%%%", loginJudgment);
			//	System.out.println("loginJudgment" + loginJudgment);
				if (loginJudgment.equals("False") || loginJudgment.equals("")) {

					Toast.makeText(LoginActivity.this,
							"对不起，您输入的账号或密码错误，请重新输入！", Toast.LENGTH_SHORT)
							.show();
				} else {
					parseAdminList(loginJudgment);
					SharedPreferences.Editor editor = mySharedPreferences
							.edit();
					editor.putString("loginName", user.getText().toString()
							.trim());
					editor.commit();

					Intent intent = new Intent();
					intent.putExtra("STAFFID", infos.get(0).getSTAFFID());
					intent.setClass(LoginActivity.this, SeatsActivity.class);
					startActivity(intent);
					finish();
				}
				break;
			}
		}
	};
	/**
	 * 开启线程，判断用户登录信息
	 */
	Runnable judgment_thread = new Runnable() {

		@Override
		public void run() {
			// loginJudgment=HttpUrls_.login(LoginActivity.this, userName,
			// userPwd);
			loginJudgment = mainDaos.Login(userName, userPwd);
		//	Log.i("qwj", "loginJudgment==" + loginJudgment);
			Message msg_judgment = new Message();
			msg_judgment.what = 0x30;
			msg_judgment.obj = loginJudgment;
			handler_judgment.obtainMessage(0x30, loginJudgment);
			handler_judgment.sendMessage(msg_judgment);

		}
	};

	private void parseAdminList(String mtStr) {
		infos.clear();
		// List<UnideInfo> list=new ArrayList<UnideInfo>();
		try {
			JSONArray jsonArray = new JSONArray(mtStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				AdminInfo info = new AdminInfo();
				info.setSTAFFID(object.getInt("STAFFID"));
				info.setSTAFFNO(object.getString("STAFFNO"));
				info.setPWD(object.getString("PWD"));
				info.setSTAFFNAME(object.getString("STAFFNAME"));
				info.setSTATUS(object.getString("STATUS"));

				infos.add(info);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isPad(this)){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}else{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}
	/**
	 * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
	 * 
	 * @param context
	 * @return 平板返回 True，手机返回 False
	 */
	public static boolean isPad(Context context) {

		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

	}

}
