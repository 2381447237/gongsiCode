package com.fc.workstatus.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.service.PersonService;
import com.fc.person.beans.PersonTask;
import com.fc.workstatus.bean.WorkStatusAdapter;
import com.fc.workstatus.bean.WorkStatusInfo;
import com.test.zbetuch_news.R;

public class WorkStatusActivity extends Activity implements IActivity,
		OnPullDownListener {
	private TextView tv_totalworkstatusread;
	private PullDownView mWorkstatusPullDownView;
	private ListView mWorkStatusListView;

	private int index = 0;

	public static final int WORK_STATUS_INFO = 0;
	public static final int WORK_STATUS_READ = 1;

	private WorkStatusAdapter workStatusAdapter;
	private List<WorkStatusInfo> workStatusInfos = new ArrayList<WorkStatusInfo>();
	private ImageButton newDongtai;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workstatus_list);
		init();
		initView();

		initPullDownView();
		getPageList(index);

		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		params.put("data", data);
		PersonTask task = new PersonTask(
				PersonTask.WORKSTATUSINFOACTIVITY_GET_WORKSTATUSINFO_READ,
				params);
		PersonService.newTask(task);

	}

	@Override
	public void onMore() {
		// TODO Auto-generated method stub
		index++;
		mWorkstatusPullDownView.notifyDidMore();
		getPageList(index);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub

		switch (Integer.parseInt(params[0].toString().trim())) {
		case WorkStatusActivity.WORK_STATUS_INFO:
			if (!"".equals(params[1].toString().trim())
					&& params[1].toString().trim() != null) {
				List<WorkStatusInfo> infos = parseJsonToWorkStatus(params[1]
						.toString().trim());
				if (infos.size() > 0) {
					workStatusInfos.addAll(infos);
				}
			}
			workStatusAdapter.notifyDataSetChanged();
			mWorkstatusPullDownView.notifyDidMore();
			break;

		case WorkStatusActivity.WORK_STATUS_READ:
			if (params[1].toString().trim() != null
					&& !"".equals(params[1].toString().trim())) {
				parseWorkSatusNum(params[1].toString().trim());
			}
			break;

		default:
			break;
		}

	}

	private void initView() {
		tv_totalworkstatusread = (TextView) this
				.findViewById(R.id.tv_totalworkstatusread);
		mWorkstatusPullDownView = (PullDownView) this
				.findViewById(R.id.mWorkstatusPullDownView);
		mWorkStatusListView = mWorkstatusPullDownView.getListView();

		workStatusAdapter = new WorkStatusAdapter(workStatusInfos,
				WorkStatusActivity.this);
		mWorkStatusListView.setAdapter(workStatusAdapter);
		mWorkStatusListView.setOnItemClickListener(new MyItemClickListener());
		mWorkstatusPullDownView.setOnPullDownListener(this);

	}

	private void initPullDownView() {
		// 设置可以自动获取更多 滑到最后一个自动获取 改成false将禁用自动获取更多
		// mWorkstatusPullDownView.enableAutoFetchMore(true, 0);
		// 隐藏 并禁用尾部
		// mWorkstatusPullDownView.setHideFooter();
		// 显示并启用自动获取更多
		mWorkstatusPullDownView.setShowFooter();
		// 隐藏并且禁用头部刷新
		mWorkstatusPullDownView.setHideHeader();
		// 显示并且可以使用头部刷新
		// mWorkstatusPullDownView.setShowHeader();
	}

	private void getPageList(int index) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("page", "" + index);
		data.put("rows", "15");
		params.put("data", data);

		PersonTask task = new PersonTask(
				PersonTask.WORKSTATUSINFOACTIVITY_GET_WORKSTATUSINFO, params);
		PersonService.newTask(task);
	}

	private void parseWorkSatusNum(String str) {
		int totalNum = 0;
		int weiduNum = 0;
		double rate = 0.00;
		try {
			JSONArray jsonArray = new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				totalNum = jsonObject.getInt("VALUE1");
				weiduNum = jsonObject.getInt("VALUE2");
				rate = jsonObject.getDouble("RATE");
			}
			tv_totalworkstatusread.setText("本月" + totalNum + "条已读" + weiduNum
					+ "条" + "读取率:" + rate + "%");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class MyItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(WorkStatusActivity.this,
					WorkStatusDetailActivity.class);
			intent.putExtra("workstatusInfo",
					(Serializable) workStatusInfos.get(position - 1));
			startActivity(intent);
		}

	}

	private List<WorkStatusInfo> parseJsonToWorkStatus(String str) {
		List<WorkStatusInfo> workStatusInfos = new ArrayList<WorkStatusInfo>();
		try {
			JSONArray jsonArray = new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				WorkStatusInfo workStatusInfo = new WorkStatusInfo();
				workStatusInfo.setID(jsonObject.getInt("ID"));
				workStatusInfo.setTITLE(jsonObject.getString("TITLE"));
				workStatusInfo.setDOC(jsonObject.getString("DOC"));
				workStatusInfo.setNEWS_TIME(jsonObject.getString("NEWS_TIME"));
				workStatusInfo.setCREATE_STAFF(jsonObject
						.getInt("CREATE_STAFF"));
				workStatusInfo.setCREATE_DATE(jsonObject
						.getString("CREATE_DATE"));
				workStatusInfo.setUPDATE_STAFF(jsonObject
						.getInt("UPDATE_STAFF"));
				workStatusInfo.setUPDATE_DATE(jsonObject
						.getString("UPDATE_DATE"));
				workStatusInfo.setTYPE_NAME(jsonObject.getString("TYPE_NAME"));
				workStatusInfo.setTYPE(jsonObject.getInt("TYPE"));
				workStatusInfo.setRecordCount(jsonObject.getInt("RecordCount"));
				workStatusInfo.setCREATE_STAFF_NAME(jsonObject
						.getString("CREATE_STAFF_NAME"));
				workStatusInfos.add(workStatusInfo);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return workStatusInfos;
	}

}
