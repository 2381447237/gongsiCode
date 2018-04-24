package com.example.secondlevelactivity;

import com.base.activity.BaseActivity;
import com.base.activity.ShopApplication;
import com.example.shopping.MineInfomationFragment;
import com.example.shopping.R;
import com.example.shopping.R.id;
import com.example.shopping.R.layout;
import com.example.shopping.utils.DataCleanManager;
import com.example.shopping.utils.FileUtils;
import com.example.thirdlevelactivity.AboutUsActivity;
import com.lidroid.xutils.http.client.entity.FileUploadEntity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends BaseActivity implements OnClickListener {

	private ImageView img_back;
	private View layout_cancellate;
	private RelativeLayout rl_aboutus, rl_question, rl_gesturepwd, rl_clear;
    private TextView tv_cache,tv_version;
	
    private Handler handler=new Handler(){
    	
    	public void handleMessage(android.os.Message msg) {
    		
    		switch (msg.what) {
			case 1:
				
				if(TextUtils.equals("0", tv_cache.getText().toString())){
					
					Toast.makeText(SettingActivity.this,"已经清除过了",0).show();
					return;	
				}
				
				DataCleanManager.clearAllCache(SettingActivity.this);
				FileUtils.delete(FileUtils.getRootDirOfSDForApp(SettingActivity.this));
				
				tv_cache.setText("0");
				
				Toast.makeText(SettingActivity.this,"清除成功",0).show();
				
				break;

			case 2:
				
				try {
					String totalCacheSize=DataCleanManager.getTotalCacheSize(SettingActivity.this);
				
					if(TextUtils.isEmpty(totalCacheSize)){
						tv_cache.setText("0");
					}else{
						tv_cache.setText(totalCacheSize);				
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
				
			default:
				break;
			}
    		
    	};
    	
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_setting_back);
		layout_cancellate = findViewById(R.id.layout_cancellate);
		rl_aboutus = (RelativeLayout) findViewById(R.id.layout_aboutus);
		rl_aboutus.setOnClickListener(this);
		rl_question = (RelativeLayout) findViewById(R.id.layout_question);
		rl_question.setOnClickListener(this);
		rl_gesturepwd = (RelativeLayout) findViewById(R.id.layout_gesturepwd);
		rl_gesturepwd.setOnClickListener(this);
		img_back.setOnClickListener(this);
		layout_cancellate.setOnClickListener(this);
		rl_clear = (RelativeLayout) findViewById(R.id.layout_clear);
		rl_clear.setOnClickListener(this);
		tv_cache=(TextView) findViewById(R.id.tv_cache);
		handler.sendEmptyMessage(2);
		tv_version=(TextView) findViewById(R.id.tv_version);
		tv_version.setText(getVersion());
	}

	@Override
	public void onClick(View v) {
		
		Intent auIntent=new Intent(SettingActivity.this,AboutUsActivity.class);
		
		switch (v.getId()) {
		case R.id.img_setting_back:
			finish();
			break;

		case R.id.layout_cancellate:
			showDialog();
			break;

		case R.id.layout_aboutus:

		
			auIntent.putExtra("type","aboutus");
			startActivity(auIntent);
			
			break;
		case R.id.layout_question:

			auIntent.putExtra("type","question");
			startActivity(auIntent);

			break;
		case R.id.layout_gesturepwd:

			Toast.makeText(this, "手势密码", 0).show();

			break;
		case R.id.layout_clear:

			if(TextUtils.equals("0", tv_cache.getText().toString())){
				
				Toast.makeText(this,"已经清除过了",0).show();
				return;				
			}
			
			handler.sendEmptyMessage(1);
			
			break;

		default:
			break;

		}
	}

	private void showDialog() {
		final AlertDialog dialog = new AlertDialog.Builder(SettingActivity.this)
				.create();
		View view = LayoutInflater.from(SettingActivity.this).inflate(
				R.layout.dialog_layout, null);
		dialog.setView(view);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.dialog_layout);
		TextView tv_dialog = (TextView) window.findViewById(R.id.tv_dialog);
		tv_dialog.setText("您确定删除么？");
		Button btnOk = (Button) window.findViewById(R.id.btn_ok);
		Button btnUndo = (Button) window.findViewById(R.id.btn_undo);
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SharedPreferences preferences = getSharedPreferences("user",
						Context.MODE_PRIVATE);
				preferences.edit().clear().commit();
				dialog.cancel();
				Intent mIntent = new Intent("mineinfo");
				// 发送广播
				sendBroadcast(mIntent);
				finish();
			}
		});
		btnUndo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.cancel();
			}
		});

	}
	
	/**
	   * 获取版本号
	   * @return 当前应用的版本号
	   */
	 public String getVersion() {
	      try {
	          PackageManager manager = this.getPackageManager();
	         PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	         String version = info.versionName;
	        // return this.getString(R.string.version_name) + version;
	         return  version;
	     } catch (Exception e) {
	         e.printStackTrace();
	        // return this.getString(R.string.can_not_find_version_name);
	         return "未知";
	    }
	 }
	
}
