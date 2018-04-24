package com.fc.reportform.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView2;
import com.fc.main.beans.PullDownView2.OnPullDownListener;
import com.fc.main.myservices.CompanyService;
import com.fc.meetguanli.views.GuanLiMainActivity;
import com.fc.meetingpost.views.MeetingListActivity;
import com.fc.reportform.beans.ReportFormAdapter;
import com.fc.reportform.beans.ReportFormInfo;
import com.fc.zbetuch_sm.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ReportFormListActivity extends Activity implements 
IActivity,OnPullDownListener{
	private Activity mContext = this;
	private TextView tv_totalandread;
	private PullDownView2 mPullDownView;
	private ListView list_report;
	private ReportFormAdapter adapter;
	private List<ReportFormInfo> reportInfo = new ArrayList<ReportFormInfo>();
	public static final int REFRESH_REPORTLIST = 1;
	public static final int GET_READ_NUM=2;
	Map<String, String> data =  new HashMap<String, String>();
	int index = 0;
	private ImageButton new_report;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reportformlist);
		init();
		initView();
		initPulldownattr();
		getPageList(index);
		getReadNum();

	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case REFRESH_REPORTLIST:
			if (params[1]!=null&&!"".equals(params[1].toString().trim())) {
				String value = params[1].toString();
				List<ReportFormInfo> newlist = parseJsonToList(value);
				reportInfo.addAll(newlist);
				adapter.notifyDataSetChanged();
				mPullDownView.notifyDidMore();
			}
			break;
			
		case GET_READ_NUM:
			if (params[1]!=null&&!"".equals(params[1].toString().trim())) {
				parseJosoToNum(params[1].toString().trim());
			}
			break;

		default:
			break;
		}
	}
	
	private class myOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long arg3) {

			Intent intent = new Intent(mContext,ReportFormListDetailActivity.class);
			intent.putExtra("reportformInfo", reportInfo.get(position-1));
			startActivity(intent);
		}

	};

	private void initView(){
		tv_totalandread = (TextView)findViewById(R.id.tv_totalandread);
		mPullDownView = (PullDownView2)findViewById(R.id.mPullDownView);
		new_report = (ImageButton) findViewById(R.id.new_report);
		new_report.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.new_report:
					Intent newReportIntent = new Intent(ReportFormListActivity.this,NewReportActivity.class);
					startActivity(newReportIntent);
					break;
				}
			}
		});
		list_report = mPullDownView.getListView();
		adapter = new ReportFormAdapter(this, reportInfo);
		list_report.setAdapter(adapter);
		list_report.setOnItemClickListener(new myOnItemClickListener());
		mPullDownView.setOnPullDownListener(this);
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

	@Override
	public void onMore() {
		index ++;
		mPullDownView.notifyDidMore();
		getPageList(index);
	}

	private void getPageList(int index){
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("page", "" + index);
		data.put("rows", "15");
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.REPORTFORM_REPORTFORMLISTACTIVITY_GET_MANAGE_REPORT, params);
		CompanyService.newTask(task);
	}

	private List<ReportFormInfo> parseJsonToList(String parseStr){
		List<ReportFormInfo> list = new ArrayList<ReportFormInfo>();
		JSONArray jsonarray;
		try {
			jsonarray = new JSONArray(parseStr);
			int len = jsonarray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonarray.getJSONObject(i);
				ReportFormInfo info = new ReportFormInfo();
				info.setId(object.getInt("ID"));
				info.setTitle(object.getString("TITLE"));
				info.setType(object.getString("TYPE"));
				info.setMark(object.getString("MARK"));
				info.setReportTime(object.getString("REPORT_TIME"));
				info.setCreateStaff(object.getInt("CREATE_STAFF"));
				info.setCreatedate(object.getString("CREATE_DATE"));
				info.setUpdateStaff(object.getInt("UPDATE_STAFF"));
				info.setUpdateDate(object.getString("UPDATE_DATE"));
				info.setRecordCount(object.getInt("RecordCount"));
				info.setType_name(object.getString("TYPE_NAME"));
				info.setTypename(object.getString("TYPE2"));
				info.setCreate_staff_name(object.getString("CREATE_STAFF_NAME"));
				list.add(info);
			}
		}catch (JSONException e) {
			e.printStackTrace();
		}
		return list;

	}
	
	private void getReadNum(){
		Map<String, Object> params=new HashMap<String, Object>();
		Map<String, String> data=new HashMap<String, String>();
		params.put("data", data);
		CompanyTask task=new CompanyTask(CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_REPORT_NUM, params);
		CompanyService.newTask(task);
	}
	
	private void parseJosoToNum(String str){
		int totalNum=0;
		int weiduNum=0;
		double rate=0.00;
		try {
			JSONArray jsonArray=new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject=(JSONObject) jsonArray.get(i);
				totalNum=jsonObject.getInt("VALUE1");
				weiduNum=jsonObject.getInt("VALUE2");
				rate=jsonObject.getDouble("RATE");
			}
			tv_totalandread.setText("本月"+totalNum+"条已读"+weiduNum+"条"+"读取率:"+rate+"%");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
