package com.fc.company.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.fc.company.beans.CompanyItem;
import com.fc.company.beans.CompanyItemAdapter;
import com.fc.company.beans.CompanyItemExpandAdapter;
import com.fc.company.beans.CompanyTask;
import com.fc.invite.views.InviteJobListActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.MainTask;
import com.fc.main.beans.PullDownExpandView;
import com.fc.main.beans.PullDownExpandView.mOnPullDownListener;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.beans.SpinnerItem;
import com.fc.main.service.CompanyService;
import com.fc.main.service.MainService;
import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

public class CompanyQueryActivity extends Activity implements IActivity,
		OnPullDownListener, mOnPullDownListener {
	private EditText txtCompanyName, txtCompanyNo;
	private Spinner cboCompanyType, cboLinkMan;
	private Button btnCheck;
	private PullDownView lvCompany;
	private PullDownExpandView lvCompany1;
	private ListView mListView;
	private ExpandableListView mListView1;
	List<CompanyItem> items;
	CompanyItemAdapter adapter;
	CompanyItemExpandAdapter adapter1;
	ProgressDialog progressDialog;

	public static final int REFRESH_CBOCOMPANYTYPE = 1;
	public static final int REFRESH_CBOLINKMAN = 2;
	public static final int REFRESH_LVCOMPANY = 3;

	private int index = 0;
	private String companyName = "";
	private String companyNo = "";
	private int companyType = 0;
	private int linkMan = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_query);
		initControl();
		initPulldownattr();
		init();
		initListener();
		initCbo();
		showDialog();

	}

	@Override
	protected void onStart() {
		super.onStart();
		// lvCompany.notifyDidMore();
		lvCompany1.notifyDidMore();
	}

	private void initPulldownattr() {
		// 显示并启用自动获取更多
		// lvCompany.setShowFooter();
		lvCompany1.setShowFooter();
		// 隐藏并且禁用头部刷新
		// lvCompany.setHideHeader();
		lvCompany1.setHideHeader();
	}

	private void initControl() {
		items = new ArrayList<CompanyItem>();
		// adapter = new CompanyItemAdapter(this, items);
		adapter1 = new CompanyItemExpandAdapter(this, items);
		txtCompanyName = (EditText) findViewById(R.id.txtCompanyName);
		txtCompanyNo = (EditText) findViewById(R.id.txtCompanyNo);
		cboCompanyType = (Spinner) findViewById(R.id.cboCompanyType);
		cboLinkMan = (Spinner) findViewById(R.id.cboLinkMan);
		btnCheck = (Button) findViewById(R.id.btnCheck);
		// lvCompany = (PullDownView) findViewById(R.id.lvCompany);
		// mListView = lvCompany.getListView();
		lvCompany1 = (PullDownExpandView) findViewById(R.id.lvCompany1);
		mListView1 = lvCompany1.getListView();

		// mListView.setAdapter(adapter);
		mListView1.setAdapter(adapter1);
	}

	private void initListener() {
		// lvCompany.setOnPullDownListener(this);
		lvCompany1.setOnPullDownListener(this);
		btnCheck.setOnClickListener(new MyOnClickListener());
		// mListView.setOnItemClickListener(new MyOnItemClickListener());
		mListView1.setOnItemClickListener(new MyOnItemClickListener());
	}

	private void initCbo() {
		MainTask task = new MainTask(
				MainTask.COMPANYQUERYACTIVITY_GET_COMPANYPROPERTY, null);
		MainService.newTask(task);
		task = new MainTask(MainTask.COMPANYQUERYACTIVITY_GET_CBOLINKMAN, null);
		MainService.newTask(task);
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
		MainService.addActivity(this);
		Intent intent = new Intent("com.fc.company.service.CompanyService");
		startService(intent);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case CompanyQueryActivity.REFRESH_CBOCOMPANYTYPE:
			if (params[1] != null) {
				String value = params[1].toString();
				MainTools.fetchSpinner(this, cboCompanyType, value,
						"compropertyid", "compropertyname");
			}
			break;

		case CompanyQueryActivity.REFRESH_CBOLINKMAN:
			if (params[1] != null) {
				String value = params[1].toString();
				MainTools.fetchSpinner(this, cboLinkMan, value, "id", "name");
			}
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
			break;
		case CompanyQueryActivity.REFRESH_LVCOMPANY:
			if (params[1] != null) {
				if (index == 0) {
					items.clear();
				}
				String value = params[1].toString();
				List<CompanyItem> newitems = new ArrayList<CompanyItem>();
				try {
					newitems = JSON.parseArray(value, CompanyItem.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				items.addAll(newitems);
				// adapter.notifyDataSetChanged();
				adapter1.notifyDataSetChanged();
				// lvCompany.notifyDidMore();

				lvCompany1.notifyDidMore();

			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
		MainService.allActivity.remove(this);
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnCheck:
				items.clear();
				index = 0;
				companyName = txtCompanyName.getText().toString().trim();
				companyNo = txtCompanyNo.getText().toString().trim();
				companyType = ((SpinnerItem) cboCompanyType.getSelectedItem())
						.getId();
				linkMan = ((SpinnerItem) cboLinkMan.getSelectedItem()).getId();
				getCompanyList(index, companyName, companyNo, companyType,
						linkMan);
				break;
			}

		}

	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int index,
				long id) {
			Intent intent = new Intent(CompanyQueryActivity.this,
					InviteJobListActivity.class);
			intent.putExtra("flag", InviteJobListActivity.GET_ITEMS_BY_COMPANY);
			intent.putExtra("ComId",
					((CompanyItem) adapter.getItem(index - 1)).getComid());
			startActivity(intent);
		}

	}

	@Override
	public void onMore() {
		index++;
		getCompanyList(index, companyName, companyNo, companyType, linkMan);
	}

	public void getCompanyList(int index, String name, String no, int type,
			int linkman) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("Page", "" + index);
		data.put("Row", "" + 15);
		data.put("ComName", name);
		data.put("ComCode", no);
		data.put("ComPropertyId", "" + type);
		data.put("Contacts", "" + linkman);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);

		CompanyTask task = new CompanyTask(
				CompanyTask.COMPANYQUERYACTIVITY_GET_COMPANYITEMS, params);
		CompanyService.newTask(task);
	}

	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.show();
	}

}
