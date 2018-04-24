package com.example.hospital;



import com.fc.has.views.HomeVisitActivity;
import com.fc.homebed.views.HomebedActivity;
import com.fc.statistics.views.DataStatisticsActivity;
import com.fc.unidentified.views.DiagnosisActivity;
import com.fc.unidentified.views.UnidentifiedActivity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TabHost;

public class HomePageActivity extends Activity implements OnClickListener{
	public static TabHost tabHost;
	public static HomePageActivity _instance = null; 
	public static Button unident_button,has_button,homebed_button,statistics_button;
	public static final boolean flag=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home_page);
		_instance=this;
		tabHost=(TabHost) findViewById(android.R.id.tabhost);
		LocalActivityManager activityManager=new LocalActivityManager(this, false);
		activityManager.dispatchCreate(savedInstanceState);
		tabHost.setup(activityManager);
		//选项卡添加Activity
				tabHost.addTab(tabHost.newTabSpec("t1")
						.setIndicator(null, getResources()
								.getDrawable(R.drawable.unident))
						.setContent(new Intent(this, UnidentifiedActivity.class)));
				tabHost.addTab(tabHost.newTabSpec("t2")
						.setIndicator(null, getResources()
								.getDrawable(R.drawable.has))
						.setContent(new Intent(this, HomeVisitActivity.class)));
				tabHost.addTab(tabHost.newTabSpec("t3")
						.setIndicator(null, getResources()
								.getDrawable(R.drawable.homebed))
						.setContent(new Intent(this, HomebedActivity.class)));
				tabHost.addTab(tabHost.newTabSpec("t4")
						.setIndicator(null, getResources()
								.getDrawable(R.drawable.statistics))
						.setContent(new Intent(this, DataStatisticsActivity.class)));
				CurrentTab();
//				if(flag){
//					tabHost.setCurrentTab(0);
//					flag=false;
//				}else{
//					tabHost.setCurrentTab(1);
//				}
				
	}

	private void CurrentTab(){
//		RadioGroup radioGroup=(RadioGroup) findViewById(R.id.main_radio);
//		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				// TODO Auto-generated method stub
//				int m=0;
//				switch (checkedId) {
//				case R.id.unident_button:
//					m=0;
//					break;
//				case R.id.has_button:
//					m=1;
//					break;
//				case R.id.homebed_button:
//					m=2;
//					break;
//				case R.id.statistics_button:
//					m=3;
//					break;
//
//				default:
//					break;
//				}
//					tabHost.setCurrentTab(m);
//				
//			}
//		});
		 unident_button=(Button)findViewById(R.id.unident_button);
		 has_button=(Button)findViewById(R.id.has_button);
		 homebed_button=(Button)findViewById(R.id.homebed_button);
		 statistics_button=(Button)findViewById(R.id.statistics_button);
		unident_button.setOnClickListener(this);
		has_button.setOnClickListener(this);
		homebed_button.setOnClickListener(this);
		statistics_button.setOnClickListener(this);
		unident_button.setBackgroundResource(R.drawable.unident_on);
	}

	@Override
	public void onClick(View v) {
		clearSelection();
		switch (v.getId()) {
		case R.id.unident_button:
			unident_button.setBackgroundResource(R.drawable.unident_on);
			tabHost.setCurrentTab(0);
			break;
		case R.id.has_button:
			has_button.setBackgroundResource(R.drawable.has_on);
			tabHost.setCurrentTab(1);
			break;
		case R.id.homebed_button:
			homebed_button.setBackgroundResource(R.drawable.homebed_on);
			tabHost.setCurrentTab(2);
			break;
		case R.id.statistics_button:
			statistics_button.setBackgroundResource(R.drawable.statistics_on);
			tabHost.setCurrentTab(3);
			break;

		default:
			break;
		}
	}
	
	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		unident_button.setBackgroundResource(R.drawable.unident);
		has_button.setBackgroundResource(R.drawable.has);
		homebed_button.setBackgroundResource(R.drawable.homebed);
		statistics_button.setBackgroundResource(R.drawable.statistics);
	}
	
	
}
