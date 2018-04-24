package com.fc.gradeate.views;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.test.zbetuch_news.R;

public class GradeateWorkTrackActivity extends ActivityGroup implements
		IActivity {

	private RadioGroup rgpMain;
	private RadioButton rdbWorkMark, rdbWorkGride;
	private TabHost tabhost;

	private Intent workMarkIntent, workGridIntent;
	private ActivityGroup mContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gradeate_worktrack);
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
		rdbWorkMark = (RadioButton) findViewById(R.id.rdbWorkMark);
		rdbWorkGride = (RadioButton) findViewById(R.id.rdbWorkGride);
		tabhost = (TabHost) findViewById(R.id.tabhost);

		workMarkIntent = new Intent(this, GradeateWorkMarkActivity.class);
		workGridIntent = new Intent(this, GradeateWorkGrideActivity.class);

		tabhost.setup(this.getLocalActivityManager());
		tabhost.addTab(tabhost.newTabSpec("workMark").setIndicator("workMark")
				.setContent(workMarkIntent));
		tabhost.addTab(tabhost.newTabSpec("workGride")
				.setIndicator("workGride").setContent(workGridIntent));

		rdbWorkMark.setChecked(true);

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
			case R.id.rdbWorkMark:
				tabhost.setCurrentTabByTag("workMark");
				break;
			case R.id.rdbWorkGride:
				tabhost.setCurrentTabByTag("workGride");
				break;
			}
		}

	}

}
