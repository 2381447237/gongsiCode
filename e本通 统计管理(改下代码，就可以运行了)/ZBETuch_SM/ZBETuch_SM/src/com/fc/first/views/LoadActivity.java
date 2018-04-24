package com.fc.first.views;
import com.fc.main.tools.CheckNet;
import com.fc.main.tools.HttpUrls_;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;
/**
 * 加载渐变动画显示界面
 * @author hxl
 *
 */
public class LoadActivity extends Activity implements AnimationListener{
	private ImageView img_load;//加载动画图片
	private String des;//私钥

		private Handler handler_load = new Handler(){
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0x001:
				Toast.makeText(LoadActivity.this, des, Toast.LENGTH_SHORT).show();
				break;
			}
		}

	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置屏幕无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置屏幕全屏
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_load);
		System.out.println("---------------------");
		//启动服务
		Intent service = new Intent("com.fc.main.myservice.MainService");
		startService(service);
		Intent service2 = new Intent("com.fc.person.myservice.PersonService");
		startService(service2);
		Intent service3 = new Intent("com.fc.company.myservice.CompanyService");
		startService(service3);
		Intent service4 = new Intent("com.fc.main.myservice.PostMsgService");
		startService(service4);
		img_load = (ImageView) findViewById(R.id.img_load);

		// 透明度渐变动画（0.1f-1.0f）	
		AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
		// 设置动画时间
		animation.setDuration(3000);
		img_load.setAnimation(animation);
		animation.setAnimationListener(this);
		new Thread(thread_load).start();
		if(!isOpen()){
			toggleGPS();
		}
	}
	/**
	 * 开启线程获取用户登录的私钥
	 */
	Runnable thread_load = new Runnable() {
		
		@Override
		public void run() {
			des = HttpUrls_.getDes(LoadActivity.this);
			Message msgDes = new Message();
			msgDes.what = 0x20;
			msgDes.obj = des;
			handler_load.obtainMessage(0x20, des);
			handler_load.sendMessage(msgDes);
		}
	};	
	public  void showDialog(String msg) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setMessage(msg).setCancelable(true).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog alert = dialog.create();
		alert.show();
	}
	/**
	 * 动画开始监听 检查网络状态
	 */
	@Override
	public void onAnimationStart(Animation animation) {
		CheckNet.checkNetwork(LoadActivity.this);
	}
	/**
	 * 动画结束监听 进入登录界面
	 */
	@Override
	public void onAnimationEnd(Animation animation) {
		Intent it = new Intent(LoadActivity.this, LoginActivity.class);
		startActivity(it);
		finish();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {	
	}

	/**
	 * 发送请求，打开GPS
	 */
	private void toggleGPS() {
		Intent gpsIntent = new Intent();
		gpsIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
		gpsIntent.setData(Uri.parse("custom:3"));
		try {
			PendingIntent.getBroadcast(this, 0, gpsIntent, 0).send();
		} catch (CanceledException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * [功能描述] 检查GPS的开关状态
	 * 
	 * @return [参数说明] true：开/false：关
	 * 
	 */
	private boolean isOpen() {
		String str = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		Log.v("GPS", str);
		if (str != null) {
			return str.contains("gps");
		} else {
			return false;
		}
	}

}


