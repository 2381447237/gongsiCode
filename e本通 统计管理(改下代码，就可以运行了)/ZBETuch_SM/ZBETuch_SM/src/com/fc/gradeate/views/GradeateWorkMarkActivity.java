package com.fc.gradeate.views;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fc.company.beans.CompanyTask;
import com.fc.gradeate.beans.JobMarkAdapter;
import com.fc.gradeate.beans.JobMarkInfo;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView2;
import com.fc.main.beans.PullDownView2.OnPullDownListener;
import com.fc.main.myservices.CompanyService;
import com.fc.zbetuch_sm.R;

public class GradeateWorkMarkActivity extends Activity implements IActivity,OnPullDownListener{

	private Activity mContext = this;
	private Button btnNew;
	private ListView lvWorkMark;
	private PullDownView2 mPullDownView;

	private List<JobMarkInfo> infos = new ArrayList<JobMarkInfo>();
	private JobMarkAdapter adapter;

	public static final int REFRESH_JOBMARK = 1;
	public static final int REFRESH_FRM = 2;
	public static final int DELETE_JOBMARK = 3;
	public static final int REFRESH_ADD_JOBMARK=4;
	
	private int index=0;
	private int count=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gradeate_jobmark);
		if (GradeatePersonInfoActivity.gradeateId == 0) {
			Toast.makeText(mContext, "人员还未保存，请先保存或选中人员", Toast.LENGTH_SHORT)
					.show();
		}
		init();
		initView();
		initListener();
		initPulldownattr();
		if (GradeatePersonInfoActivity.gradeateId != 0) {
			getJobMarkList(index);
		}

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
	public void init() {
		CompanyService.addActivity(this);
	}
	
	

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case GradeateWorkMarkActivity.REFRESH_JOBMARK:
			if (params[1] != null&&!"".equals(params[1].toString().trim())) {
				String value = params[1].toString().trim();
				List<JobMarkInfo> newInfos = parseStrToJobMarks(value);
				count=index;
				if (newInfos.size() > 0) {
					infos.addAll(newInfos);
					adapter.notifyDataSetChanged();
					mPullDownView.notifyDidMore();
				}else{
					
					if (infos.size()%15==0) {
						index=--count;
					} else {
						index=count;
					}
				}
			}else{
				if (infos.size()%15==0) {
					index=--count;
				} else {
					index=count;
				}
			}
			break;
		case GradeateWorkMarkActivity.REFRESH_FRM:
//			getJobMarkList();
			if (params[1]!=null) {
				JobMarkInfo info=(JobMarkInfo) params[1];
				if (infos.size()>0) {
					for (int i = 0; i < infos.size(); i++) {
						JobMarkInfo jobMarkInfo=infos.get(i);
						if (jobMarkInfo.getId()==info.getId()) {
							jobMarkInfo.setBase_situation(info.getBase_situation());
							jobMarkInfo.setCreat_date(info.getCreat_date());
							jobMarkInfo.setCreat_staff(info.getCreat_staff());
							jobMarkInfo.setDetail_company(info.getDetail_company());
							jobMarkInfo.setDetail_situation1(info.getDetail_situation1());
							jobMarkInfo.setDetail_situation2(info.getDetail_situation2());
							jobMarkInfo.setId(info.getId());
							jobMarkInfo.setInquirer(info.getInquirer());
							jobMarkInfo.setMaster_id(info.getMaster_id());
							jobMarkInfo.setRecordCount(info.getRecordCount());
							jobMarkInfo.setRemark(info.getRemark());
							jobMarkInfo.setUpdate_date(info.getUpdate_date());
							jobMarkInfo.setUpdate_staff(info.getUpdate_staff());
							jobMarkInfo.setVisit_date(info.getVisit_date());
						}
					}
				}
			}
			adapter.notifyDataSetChanged();
			mPullDownView.notifyDidMore();
			break;
		case GradeateWorkMarkActivity.DELETE_JOBMARK:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				if (value.trim().equalsIgnoreCase("true")) {
					Toast.makeText(mContext, "删除成功！", Toast.LENGTH_SHORT)
							.show();
					if (infos.size()>0) {
						List<JobMarkInfo> tempJobMarkInfo=new ArrayList<JobMarkInfo>();
						tempJobMarkInfo.addAll(infos);
						infos.clear();
						for (int i = 0; i < tempJobMarkInfo.size(); i++) {
							JobMarkInfo jobMarkInfo=tempJobMarkInfo.get(i);
							if (jobMarkInfo.getId()==Integer.parseInt(params[2].toString().trim())) {
								tempJobMarkInfo.remove(jobMarkInfo);
							}
						}
						if (tempJobMarkInfo.size()>0) {
							infos.addAll(tempJobMarkInfo);
							tempJobMarkInfo.clear();
						}
					}
					adapter = new JobMarkAdapter(mContext, infos);
					lvWorkMark.setAdapter(adapter);
					mPullDownView.notifyDidMore();
				} else {
					Toast.makeText(mContext, "删除失败！", Toast.LENGTH_SHORT)
							.show();
				}
			}
			break;
			
		case GradeateWorkMarkActivity.REFRESH_ADD_JOBMARK:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				List<JobMarkInfo> newInfos = parseStrToJobMarks(value);
				if (newInfos.size() > 0) {
					infos.add(0,newInfos.get(0));
					if ((infos.size()-1)%15==0) {
						infos.remove(infos.size()-1);
					}
					adapter.notifyDataSetChanged();
					mPullDownView.notifyDidMore();
				}
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
	 * 查询走访情况
	 */
	private void getJobMarkList(int index) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("master_id", "" + GradeatePersonInfoActivity.gradeateId);
		data.put("page", ""+index);
		data.put("rows", "15");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.GRADEATEWORKMARKACTIVITY_GET_JOBMARKLIST, params);
		CompanyService.newTask(task);
	}

	/**
	 * 页面控件初始化
	 */
	private void initView() {
		btnNew = (Button) findViewById(R.id.btnNew);
		mPullDownView = (PullDownView2) findViewById(R.id.lvWorkMark);
		lvWorkMark=mPullDownView.getListView();
		adapter = new JobMarkAdapter(mContext, infos);
		lvWorkMark.setAdapter(adapter);
	}

	/**
	 * 页面监听器初始化
	 */
	private void initListener() {
		btnNew.setOnClickListener(new MyOnClickListener());
		mPullDownView.setOnPullDownListener(this);
	}

	/**
	 * 将json字符串转为调查走访列表
	 * 
	 * @param value
	 * @return
	 */
	private List<JobMarkInfo> parseStrToJobMarks(String value) {
		List<JobMarkInfo> infos = new ArrayList<JobMarkInfo>();
		if (value != null && value.trim().length() > 0) {
			try {
				JSONArray jsonArray = new JSONArray(value);
				if (jsonArray != null && jsonArray.length() > 0) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.optJSONObject(i);
						JobMarkInfo info = new JobMarkInfo();
						info.setId(object.getInt("ID"));
						info.setMaster_id(object.getInt("MASTER_ID"));
						info.setVisit_date(object.getString("VISIT_DATE")
								.trim());
						info.setBase_situation(object.getString(
								"BASE_SITUATION").trim());
						info.setDetail_situation1(object.getString(
								"DETAIL_SITUATION1").trim());
						info.setDetail_situation2(object.getString(
								"DETAIL_SITUATION2").trim());
						info.setDetail_company(object.getString(
								"DETAIL_COMPANY").trim());
						info.setCreat_date(object.getString("CREAT_DATE")
								.trim());
						info.setCreat_staff(object.getInt("CREAT_STAFF"));
						info.setUpdate_date(object.getString("UPDATE_DATE")
								.trim());
						info.setUpdate_staff(object.getInt("UPDATE_STAFF"));
						info.setRecordCount(object.getInt("RecordCount"));
						info.setRemark(object.getString("REMARK").trim());
						info.setInquirer(object.getString("INQUIRER").trim());
						infos.add(info);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return infos;
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnNew:
				Intent intent = new Intent(mContext,
						GradeateEditWorkMarkActivity.class);
				if (infos != null && infos.size() > 0) {
					intent.putExtra("lastJobMarkInfo",
							infos.get(infos.size() - 1));
				}

				startActivity(intent);
				break;
			}
		}

	}

	@Override
	public void onMore() {
		// TODO Auto-generated method stub
		index++;
		mPullDownView.notifyDidMore();
		getJobMarkList(index);
	}

}
