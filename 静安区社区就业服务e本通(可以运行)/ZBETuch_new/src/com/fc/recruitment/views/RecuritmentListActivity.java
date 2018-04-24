package com.fc.recruitment.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.service.CompanyService;
import com.fc.recritmentmanager.views.RecritmentManagerListActivity;
import com.fc.recruitment.beans.RecruitmentAdapter;
import com.fc.recruitment.beans.RecruitmentInfo;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RecuritmentListActivity extends Activity implements IActivity,
		OnPullDownListener {

	private Activity mContext = this;
	private List<RecruitmentInfo> infos = new ArrayList<RecruitmentInfo>();
	private RecruitmentAdapter adapter;
	private ListView lv_recuritment;
	private PullDownView mPullDownView;
	private Map<String, String> data = new HashMap<String, String>();
	private int index = 0;
	public static final int REFRESH_ZPLIST = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recuritment_list);
		init();
		initPulldownattr();
		getPageList(index);
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
		mPullDownView = (PullDownView) findViewById(R.id.lv_recuritment);
		lv_recuritment = mPullDownView.getListView();
		adapter = new RecruitmentAdapter(mContext, infos);
		lv_recuritment.setAdapter(adapter);
		mPullDownView.setOnPullDownListener(this);
		lv_recuritment.setOnItemClickListener(new MyOnItemClickListener());
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case RecuritmentListActivity.REFRESH_ZPLIST:
			if (params[1] != null) {
				String listStr = params[1].toString().trim();
				List<RecruitmentInfo> newList = parseStrToRecruitmentInfo(listStr);
				if (newList != null && newList.size() > 0) {
					infos.addAll(newList);
				}

				adapter.notifyDataSetChanged();
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

	public class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(mContext,
					RecuritmentListDetailActivity.class);
			intent.putExtra("info", infos.get(position - 1));
			startActivity(intent);
		}

	}

	@Override
	public void onMore() {
		index++;
		mPullDownView.notifyDidMore();
		getPageList(index);
	}

	/**
	 * 查询某页数据
	 * 
	 * @param index
	 */
	private void getPageList(int index) {
		Map<String, Object> params = new HashMap<String, Object>();

		data.put("page", "" + index);
		data.put("rows", "15");
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.RECRUITMENT_RecuritmentListActivity_GET_RECRUITMENTLIST,
				params);
		CompanyService.newTask(task);
	}

	private List<RecruitmentInfo> parseStrToRecruitmentInfo(String str) {
		List<RecruitmentInfo> list = new ArrayList<RecruitmentInfo>();
		try {
			if (str.length() > 0) {
				JSONArray jsonArray;
				jsonArray = new JSONArray(str);
				if (jsonArray != null && jsonArray.length() > 0) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.optJSONObject(i);
						RecruitmentInfo info = new RecruitmentInfo();
						info.setId(object.getInt("ID"));
						info.setName(object.getString("NAME").trim());
						info.setJobFairData(object.getString("JOBFAIRDATA")
								.replace("T", " "));
						info.setAddress(object.getString("ADDRESS").trim());
						info.setCreateDate(object.getString("CREATE_DATE")
								.trim());
						info.setCreateStaff(object.getInt("CREATE_STAFF"));
						info.setUpdateDate(object.getString("UPDATE_DATE")
								.trim());
						info.setUpdateStaff(object.getInt("UPDATE_STAFF"));
						info.setRecordCount(object.getInt("RecordCount"));
						info.setCompanyNum(object.getInt("Company_num"));
						info.setJobNum(object.getInt("Job_num"));
						list.add(info);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
}
