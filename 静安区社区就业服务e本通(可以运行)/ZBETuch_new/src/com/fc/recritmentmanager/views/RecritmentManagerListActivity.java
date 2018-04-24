package com.fc.recritmentmanager.views;

import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.os.Bundle;

public class RecritmentManagerListActivity extends Activity implements
		IActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recritmentmanager_list);
		init();
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

}
