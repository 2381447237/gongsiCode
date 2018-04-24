package com.fc.workQuery.views;

import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.service.CompanyService;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class WorkQueryListActivity extends Activity implements IActivity,
		OnPullDownListener {

	PullDownView pdv_workquerylist;
	ListView lvWorkQueryPeople;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workquery_list);
		initView();
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {

	}

	@Override
	public void onMore() {

	}

	private void initView() {
		pdv_workquerylist = (PullDownView) findViewById(R.id.pdv_workquerylist);
		lvWorkQueryPeople = pdv_workquerylist.getListView();
	}

}
