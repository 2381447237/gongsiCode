package com.fc.baobiao.views;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.fc.person.views.PersonqueryListActivity2;
import com.fc.resources.beans.ResourcesItem;
import com.fc.resources.beans.ResourcesMainAdapter;
import com.test.zbetuch_news.R;

public class QueryFirstActivity extends Activity implements IActivity {
	private ListView lvResources;
	ProgressDialog progressDialog;

	List<ResourcesItem> items;
	ResourcesMainAdapter adapter;

	public static final int REFRESH_LVRESOURCES = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_first);
		initView();
		init();
		initListener();

		CompanyTask task = new CompanyTask(
				CompanyTask.QUERYFIRSTACTIVITY_GET_RESOURCELIST, null);
		CompanyService.newTask(task);
		showDialog();

	}

	private void initView() {
		lvResources = (ListView) findViewById(R.id.lvResources);
		items = new ArrayList<ResourcesItem>();
		adapter = new ResourcesMainAdapter(items, this);
		lvResources.setAdapter(adapter);
	}

	private void initListener() {
		lvResources.setOnItemClickListener(new MyOnItemClickListener());
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);

	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case QueryFirstActivity.REFRESH_LVRESOURCES:
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
			if (params[1] != null) {
				String resourcesListStr = params[1].toString();
				List<ResourcesItem> newItems = new ArrayList<ResourcesItem>();
				try {
					newItems = JSON.parseArray(resourcesListStr,
							ResourcesItem.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				items.addAll(newItems);
				adapter.notifyDataSetChanged();

			}
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int location,
				long arg3) {
			ResourcesItem item = items.get(location);
			Intent intent = new Intent(QueryFirstActivity.this,
					PersonqueryListActivity2.class);
			intent.putExtra("flag",
					PersonqueryListActivity2.FLAG_FROM_RESOURCES);
			intent.putExtra("type", item.getType());
			intent.putExtra("order_id", item.getOrder_id());
			intent.putExtra("committee_id", item.getCommittee_id());
			startActivity(intent);
		}
	}

	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.show();
	}
}
