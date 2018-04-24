package com.fc.invite.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.fc.company.beans.CompanyTask;
import com.fc.company.beans.JobItem;
import com.fc.company.beans.JobListdAdapter;

import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.service.CompanyService;
import com.test.zbetuch_news.R;

public class InviteJobListActivity extends Activity implements IActivity,
		OnPullDownListener {

	private ListView jobListView;
	private PullDownView mPullDownView;
	private List<JobItem> jobList;
	private JobListdAdapter adapter;
	private TextView lblAllNum;

	private int index = 0;
	// 存放参数数据
	private Map<String, String> data;
	// 当前类型
	private int currentSty = 0;

	public static final int GETMOREITEM = 1;

	/**
	 * 查询所有工作列表
	 */
	public static final int GET_ITEMS_ALL = 10;
	/**
	 * 根据公司查询工作列表
	 */
	public static final int GET_ITEMS_BY_COMPANY = 11;
	/**
	 * 根据条件查询工作列表
	 */
	public static final int GET_ITEMS_BY_PARAMS = 12;

	/**
	 * 根据身份证查询工作列表
	 */
	public static final int GET_ITEM_BY_SFZ = 13;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_job_list);
		
		Log.e("2017/8/23","岗位信息列表=======");
		
		initViews();
		init();
		initPulldownattr();

		Intent intent = getIntent();
		currentSty = intent.getIntExtra("flag", 10);

		switch (currentSty) {
		case InviteJobListActivity.GET_ITEMS_BY_COMPANY:
			data = new HashMap<String, String>();
			data.put("ComId", "" + intent.getIntExtra("ComId", 0));
			break;
		case InviteJobListActivity.GET_ITEMS_BY_PARAMS:
			data = new HashMap<String, String>();
			data.put("ComName", intent.getStringExtra("ComName"));
			data.put("ComPropertyId",
					"" + intent.getIntExtra("ComPropertyId", -1));
			data.put("JobNo", intent.getStringExtra("JobNo"));
			data.put("JobName", intent.getStringExtra("JobName"));
			data.put("StartSalary", intent.getStringExtra("StartSalary"));
			data.put("EndSalary", intent.getStringExtra("EndSalary"));
			data.put("Age", intent.getStringExtra("Age"));
			data.put("IndustryClassId",
					"" + intent.getIntExtra("IndustryClassId", -1));
			data.put("IndustryClassChildId",
					"" + intent.getIntExtra("IndustryClassChildId", -1));
			data.put("ZyflId", "" + intent.getIntExtra("ZyflId", -1));
			data.put("ZyflChildId", "" + intent.getIntExtra("ZyflChildId", -1));
			data.put("GZXZId", "" + intent.getIntExtra("GZXZId", -1));
			data.put("GZBSId", "" + intent.getIntExtra("GZBSId", -1));
			// data.put("AreaId", intent.getStringExtra("AreaId"));
			data.put("AreaId1", "" + intent.getIntExtra("AreaId1", -1));
			data.put("AreaId2", "" + intent.getIntExtra("AreaId2", -1));
			data.put("AreaId3", "" + intent.getIntExtra("AreaId3", -1));
			data.put("ModifyStartDate",
					intent.getStringExtra("ModifyStartDate"));
			data.put("ModifyEndDate", intent.getStringExtra("ModifyEndDate"));
			data.put("IsDirectInterview",
					"" + intent.getBooleanExtra("IsDirectInterview", false));
			data.put("IsNewGraduates",
					"" + intent.getBooleanExtra("IsNewGraduates", false));
			data.put("IsDisabledPerson",
					"" + intent.getBooleanExtra("IsDisabledPerson", false));
			data.put("IsAssurance",
					"" + intent.getBooleanExtra("IsAssurance", false));
			data.put("EduID", "" + intent.getIntExtra("EduLevel", -1));
			data.put("PageRecCnts", "" + 15);
			break;
		case InviteJobListActivity.GET_ITEM_BY_SFZ:
			data = new HashMap<String, String>();
			data.put("sfz", "" + intent.getStringExtra("sfz"));
			break;

		}

		getPageList(index);
	}

	/**
	 * 初始化pulldown属性
	 */
	private void initPulldownattr() {
		// 设置可以自动获取更多 滑到最后一个自动获取 改成false将禁用自动获取更多
		// mPullDownView.enableAutoFetchMore(true, 0);
		// 隐藏 并禁用尾部
		// mPullDownView.setHideFooter();
		// 显示并启用自动获取更多
		mPullDownView.setShowFooter();
		// 隐藏并且禁用头部刷新
		mPullDownView.setHideHeader();
		// 显示并且可以使用头部刷新
		// mPullDownView.setShowHeader();
	}

	/**
	 * 初始化控件
	 */
	public void initViews() {
		jobList = new ArrayList<JobItem>();
		mPullDownView = (PullDownView) findViewById(R.id.pull_down_view);
		jobListView = mPullDownView.getListView();
		adapter = new JobListdAdapter(this, jobList);
		jobListView.setAdapter(adapter);
		mPullDownView.setOnPullDownListener(this);
		jobListView.setOnItemClickListener(new MyOnItemClickListener());
		lblAllNum = (TextView) findViewById(R.id.lblAllnum);
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
		Intent intent = new Intent("com.fc.company.service.CompanyService");
		startService(intent);
	}

	@Override
	public void refresh(Object... params) {
		System.out.println("");
		switch (Integer.valueOf(params[0].toString())) {
		case InviteJobListActivity.GETMOREITEM:
			if (params[1] != null) {
				String itemStr = params[1].toString();
				List<JobItem> newJobs = new ArrayList<JobItem>();
				try {
					newJobs = JSON.parseArray(itemStr, JobItem.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (newJobs != null && newJobs.size() > 0) {
					jobList.addAll(newJobs);
					adapter.notifyDataSetChanged();
					lblAllNum.setText("共有数据" + newJobs.get(0).getMax_row()
							+ "条");
				}
				if (jobList.size() == 0) {
					lblAllNum.setText("没有数据");
				}
				mPullDownView.notifyDidMore();
			}
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	@Override
	public void onMore() {
		index++;
		getPageList(index);
	}

	/**
	 * 取得某页的数据
	 * 
	 * @param index
	 */
	private void getPageList(int index) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageIndex", index);
		if (currentSty == InviteJobListActivity.GET_ITEMS_ALL) {

			CompanyTask task = new CompanyTask(
					CompanyTask.INVITEJOBLIST_BYPAGE, params);
			CompanyService.newTask(task);
		} else if (currentSty == InviteJobListActivity.GET_ITEMS_BY_COMPANY) {
			if (data != null) {
				params.put("data", data);
				CompanyTask task = new CompanyTask(
						CompanyTask.INVITEJOBLISTACTIVITY_GET_JOBLIST_BY_COMPANY,
						params);
				CompanyService.newTask(task);
			}
		} else if (currentSty == InviteJobListActivity.GET_ITEMS_BY_PARAMS) {
			if (data != null) {
				data.put("PageIndex", "" + index);
				params.put("data", data);
				
				Log.e("2017/8/23","===data==="+data);
				
				CompanyTask task = new CompanyTask(
						CompanyTask.INVITEJOBLISTACTIVITY_GET_JOBLIST_BY_PARAMS,
						params);
				CompanyService.newTask(task);
			}
		} else if (currentSty == InviteJobListActivity.GET_ITEM_BY_SFZ) {
			if (data != null) {
				data.put("PageIndex", "" + index);
				data.put("PageRecCnts", "" + 15);
				params.put("data", data);
				CompanyTask task = new CompanyTask(
						CompanyTask.INVITEJOBLISTACTIVITY_GET_JOBLIST_BY_SFZ,
						params);
				CompanyService.newTask(task);
			}
		}
	}

	private class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
			String jobno = ((JobItem) adapter.getItem(position - 1)).getJobno();
			Intent intent = new Intent(InviteJobListActivity.this,
					InviteJobDetailActivity.class);
			intent.putExtra("id", jobno);
			startActivity(intent);
		}

	}

}
