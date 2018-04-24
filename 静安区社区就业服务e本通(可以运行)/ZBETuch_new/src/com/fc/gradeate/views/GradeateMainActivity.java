package com.fc.gradeate.views;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.fc.gradeate.beans.GradeateInfo;
import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.test.zbetuch_news.R;

public class GradeateMainActivity extends ActivityGroup implements IActivity {

	private RadioGroup rgpMain;
	private RadioButton rdbPersonInfo, rdbApiration, rdbWorkTrack;
	private TabHost tabhost;

	private Intent personinfoIntent, aprirationIntent, worktrackIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gradeate_main);

		init();
		initView();
		initListener();

	}

	@Override
	public void init() {
		CompanyService.addActivity(this);

	}

	@Override
	public void refresh(Object... params) {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);

	}

	/**
	 * 页面控件初始化
	 */
	private void initView() {
		rgpMain = (RadioGroup) findViewById(R.id.rgpMain);
		rdbPersonInfo = (RadioButton) findViewById(R.id.rdbPersonInfo);
		rdbApiration = (RadioButton) findViewById(R.id.rdbApiration);
		rdbWorkTrack = (RadioButton) findViewById(R.id.rdbWorkTrack);
		tabhost = (TabHost) findViewById(R.id.tabhost);

		Intent intent = getIntent();
		GradeateInfo info = (GradeateInfo) intent.getSerializableExtra("info");

		personinfoIntent = new Intent(this, GradeatePersonInfoActivity.class);
		aprirationIntent = new Intent(this, GradeateApirationActivity.class);
		worktrackIntent = new Intent(this, GradeateWorkTrackActivity.class);
		if (info != null) {
			personinfoIntent.putExtra("info", info);
		}
		personinfoIntent.putExtra("streetStr",
				intent.getStringExtra("streetStr"));
		tabhost.setup(this.getLocalActivityManager());
		tabhost.addTab(tabhost.newTabSpec("personinfo")
				.setIndicator("personinfo").setContent(personinfoIntent));
		tabhost.addTab(tabhost.newTabSpec("apiration")
				.setIndicator("apiration").setContent(aprirationIntent));
		tabhost.addTab(tabhost.newTabSpec("worktrack")
				.setIndicator("worktrack").setContent(worktrackIntent));

		rdbPersonInfo.setChecked(true);

	}

	/**
	 * 页面监听器初始化
	 */
	private void initListener() {
		rgpMain.setOnCheckedChangeListener(new MyOnCheckedChangedListener());
	}

	private class MyOnCheckedChangedListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.rdbPersonInfo:
				tabhost.setCurrentTabByTag("personinfo");
				break;
			case R.id.rdbApiration:
				tabhost.setCurrentTabByTag("apiration");
				break;
			case R.id.rdbWorkTrack:
				tabhost.setCurrentTabByTag("worktrack");
				break;
			}
		}

	}

}
