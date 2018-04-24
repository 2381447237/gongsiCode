package com.example.hospital;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.httpurl.HttpTool;
import com.example.httpurl.HttpUrl;
import com.example.httpurl.UpdateManager;
import com.example.service.MainDaos;
import com.example.service.MainService;
import com.fc.helper.BaseActivity;
import com.fc.helper.CustomApplication;
import com.fc.helper.IActivity;
import com.fc.unidentified.beans.UnideInfo;
import com.fc.unidentified.beans.UnidentifiedAdapter;

import android.app.Activity;
import android.app.Service;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends BaseActivity implements  android.view.View.OnClickListener{
	
	
	private String loginJudgment;// 登录判断信息
	public EditText user,password;
	public Button login_btn;
	private String userName;// 用户名
	private String userPwd;// 密码
	private SharedPreferences mySharedPreferences;
	MainDaos mainDaos=new MainDaos();
	private List<AdminInfo> infos=new ArrayList<AdminInfo>();
	private CustomApplication app;
	private TextView viestcoid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
     // 设置屏幕无标题
     	requestWindowFeature(Window.FEATURE_NO_TITLE);
     // 设置屏幕全屏
     	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
     				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        
        UpdateManager manager = new UpdateManager(this);
		manager.checkUpdate();
		initview();
    }
    public  void initview() {
    	Intent service = new Intent("com.example.service.MainService");
		startService(service);
    	mySharedPreferences = getSharedPreferences("login", 0);
        login_btn=(Button) findViewById(R.id.btn_login);
        user=(EditText) findViewById(R.id.user);
        user.setText(mySharedPreferences.getString("loginName", ""));
        user.setSelection(mySharedPreferences.getString("loginName", "").length()); 
        password=(EditText) findViewById(R.id.password);
        login_btn.setOnClickListener(this);
		viestcoid=(TextView) findViewById(R.id.viestcoid);
		try {
			viestcoid.setText("V"+LoginActivity.this.getPackageManager().getPackageInfo(
					LoginActivity.this.getPackageName(), 0).versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				Log.i("huode de mima ???****%%%%%", loginJudgment);
				System.out.println("loginJudgment"+loginJudgment);
				if (loginJudgment.contains("true")) {
					parseAdminList(loginJudgment);
					SharedPreferences.Editor editor = mySharedPreferences
							.edit();
					editor.putString("loginName", user.getText().toString()
							.trim());
					editor.commit();
					
					
					Intent intent=new Intent();
					intent.setClass(LoginActivity.this, HomePageActivity.class);
					startActivity(intent);
					finish();
				} else if (loginJudgment.equals("False")||loginJudgment.equals("")) {
					Toast.makeText(getApplicationContext(),
							"对不起，您输入的账号或密码错误，请重新输入！", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			}
		}
	};
   
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btn_login:
			userName=user.getText().toString().trim();
			userPwd=password.getText().toString().trim();
			if(userName.length() == 0 || userPwd.length() == 0){
				
				Toast.makeText(LoginActivity.this, "用户名或密码不能为空！请重新输入。。。",
						Toast.LENGTH_SHORT).show();
			}else{
				
				new Thread(judgment_thread).start();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 开启线程，判断用户登录信息
	 */
	Runnable judgment_thread = new Runnable() {

		@Override
		public void run() {
			loginJudgment=mainDaos.getLoginNum(userName, userPwd);
			Log.i("qwj", "loginJudgment=="+loginJudgment);
			Message msg_judgment = new Message();
			msg_judgment.what = 0x30;
			msg_judgment.obj = loginJudgment;
			handler_judgment.obtainMessage(0x30, loginJudgment);
			handler_judgment.sendMessage(msg_judgment);
		
		}
	};
	
	private  void parseAdminList(String mtStr){
		infos.clear();
		//List<UnideInfo> list=new ArrayList<UnideInfo>();
		try {
			JSONArray jsonArray=new JSONArray(mtStr);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject object = jsonArray.getJSONObject(i);
				AdminInfo info = new AdminInfo();
				info.setStaffId(object.getInt("STAFFID"));
				info.setStaffNo(object.getString("STAFFNO"));
				info.setPassword(object.getString("PASSWORD"));
				info.setStaffName(object.getString("STAFFNAME"));
				info.setStaffPhone(object.getString("STAFFPHONE"));
				info.setRoleId(object.getInt("ROLEID"));
				info.setIsEnabled(object.getBoolean("ISENABLED"));
				info.setCreateTime(object.getString("CREATETIME"));
				
				
				infos.add(info);
				app=(CustomApplication) getApplication();
				 Log.i("qwj", "初始值=====" + app.getStaffId()); // 获取进程中的全局变量值，看是否是初始化值
			        
				 	app.setStaffId(info.getStaffId());
			        
			        Log.i("qwj", "修改后=====" + app.getStaffId()); 
				Log.i("qwj", "info.=====" + info.getStaffId()); 
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
	}
	
	


   
}
