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
import com.fc.gradeate.beans.JobGrideAdapter;
import com.fc.gradeate.beans.JobGrideInfo;
import com.fc.gradeate.beans.JobMarkInfo;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.service.CompanyService;
import com.test.zbetuch_news.R;

public class GradeateWorkGrideActivity extends Activity implements IActivity,
		OnPullDownListener {
	private Activity mContext = this;
	private Button btnNew;
	private ListView lvJobGride;

	private List<JobGrideInfo> infos = new ArrayList<JobGrideInfo>();
	private JobGrideAdapter adapter;
	private PullDownView mPullDownView;

	public static final int REFRESH_JOBGRIDE = 1;
	public static final int REFRESH_FRM = 2;
	public static final int DELETE_JOBGRIDE = 3;
	public static final int REFRESH_ADD_JOBMARK = 4;

	private int index = 0;
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gradeate_jobgride);
		if (GradeatePersonInfoActivity.gradeateId == 0) {
			Toast.makeText(mContext, "人员还未保存，请先保存或选中人员", Toast.LENGTH_SHORT)
					.show();
		}
		init();
		initView();
		initListener();
		initPulldownattr();
		if (GradeatePersonInfoActivity.gradeateId != 0) {
			getJobGrideList(index);
		}
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
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
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case GradeateWorkGrideActivity.REFRESH_JOBGRIDE:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				List<JobGrideInfo> newInfos = parseStrToJobGrides(value);
				if (newInfos.size() > 0) {
					infos.addAll(newInfos);
					adapter.notifyDataSetChanged();
					mPullDownView.notifyDidMore();
				} else {

					if (infos.size() % 15 == 0) {
						index = --count;
					} else {
						index = count;
					}
				}
			} else {

				if (infos.size() % 15 == 0) {
					index = --count;
				} else {
					index = count;
				}
			}
			break;
		case GradeateWorkGrideActivity.REFRESH_FRM:
			// getJobGrideList();
			if (params[1] != null) {
				JobGrideInfo info = (JobGrideInfo) params[1];
				if (infos.size() > 0) {
					for (int i = 0; i < infos.size(); i++) {
						JobGrideInfo jobGrideInfo = infos.get(i);
						if (jobGrideInfo.getId() == info.getId()) {
							jobGrideInfo.setCheck_guide(info.getCheck_guide());
							jobGrideInfo.setCheck_recommend(info
									.getCheck_recommend());
							jobGrideInfo.setCreat_staff(info.getCreat_staff());
							jobGrideInfo.setCreate_date(info.getCreate_date());
							jobGrideInfo.setGuide_date(info.getGuide_date());
							jobGrideInfo.setGuide_doc(info.getGuide_doc());
							jobGrideInfo.setGuide_name(info.getGuide_name());
							jobGrideInfo.setId(info.getId());
							jobGrideInfo.setMaster_id(info.getMaster_id());
							jobGrideInfo.setRecommend_com(info
									.getRecommend_com());
							jobGrideInfo.setRecommend_date(info
									.getRecommend_date());
							jobGrideInfo.setRecommend_job(info
									.getRecommend_job());
							jobGrideInfo.setRecordCount(info.getRecordCount());
							jobGrideInfo.setUpdate_date(info.getUpdate_date());
							jobGrideInfo
									.setUpdate_staff(info.getUpdate_staff());
						}
					}
				}
			}
			adapter = new JobGrideAdapter(mContext, infos);
			lvJobGride.setAdapter(adapter);
			mPullDownView.notifyDidMore();
			break;
		case GradeateWorkGrideActivity.DELETE_JOBGRIDE:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				if (value.trim().equalsIgnoreCase("true")) {
					Toast.makeText(mContext, "删除成功！", Toast.LENGTH_SHORT)
							.show();
					if (infos.size() > 0) {
						List<JobGrideInfo> tempJobMarkInfo = new ArrayList<JobGrideInfo>();
						tempJobMarkInfo.addAll(infos);
						infos.clear();
						for (int i = 0; i < tempJobMarkInfo.size(); i++) {
							JobGrideInfo jobMarkInfo = tempJobMarkInfo.get(i);
							if (jobMarkInfo.getId() == Integer
									.parseInt(params[2].toString().trim())) {
								tempJobMarkInfo.remove(jobMarkInfo);
							}
						}
						if (tempJobMarkInfo.size() > 0) {
							infos.addAll(tempJobMarkInfo);
							tempJobMarkInfo.clear();
						}
					}
					adapter = new JobGrideAdapter(mContext, infos);
					lvJobGride.setAdapter(adapter);
					mPullDownView.notifyDidMore();
				} else {
					Toast.makeText(mContext, "删除失败！", Toast.LENGTH_SHORT)
							.show();
				}
			}
			break;

		case GradeateWorkGrideActivity.REFRESH_ADD_JOBMARK:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				List<JobGrideInfo> newInfos = parseStrToJobGrides(value);
				if (newInfos.size() > 0) {
					infos.add(0, newInfos.get(0));
					if ((infos.size() - 1) % 15 == 0) {
						infos.remove(infos.size() - 1);
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
	 * 页面控件初始化
	 */
	private void initView() {
		btnNew = (Button) findViewById(R.id.btnNew);
		mPullDownView = (PullDownView) findViewById(R.id.lvJobGride);
		lvJobGride = mPullDownView.getListView();
		adapter = new JobGrideAdapter(mContext, infos);
		lvJobGride.setAdapter(adapter);
	}

	/**
	 * 页面监听器初始化
	 */
	private void initListener() {
		btnNew.setOnClickListener(new MyOnClickListener());
		mPullDownView.setOnPullDownListener(this);
	}

	/**
	 * 查询职业指导情况
	 */
	private void getJobGrideList(int index) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("master_id", "" + GradeatePersonInfoActivity.gradeateId);
		data.put("page", "" + index);
		data.put("rows", "15");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.GRADEATEWORKGRIDEACTIVITY_GET_JOBGRIDELIST, params);
		CompanyService.newTask(task);
	}

	/**
	 * 将json字符串转为职业指导情况列表
	 * 
	 * @param value
	 * @return
	 */
	private List<JobGrideInfo> parseStrToJobGrides(String value) {
		List<JobGrideInfo> infos = new ArrayList<JobGrideInfo>();
		if (value != null && value.trim().length() > 0) {
			try {
				JSONArray jsonArray = new JSONArray(value);
				if (jsonArray != null && jsonArray.length() > 0) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.optJSONObject(i);
						JobGrideInfo info = new JobGrideInfo();
						info.setId(object.getInt("ID"));
						info.setMaster_id(object.getInt("MASTER_ID"));
						info.setCheck_guide(object.getString("CHECK_GUIDE")
								.trim());
						info.setGuide_date(object.getString("GUIDE_DATE")
								.trim());
						info.setGuide_name(object.getString("GUIDE_NAME")
								.trim());
						info.setGuide_doc(object.getString("GUIDE_DOC").trim());
						info.setCheck_recommend(object.getString(
								"CHECK_RECOMMEND").trim());
						info.setRecommend_date(object.getString(
								"RECOMMEND_DATE").trim());
						info.setRecommend_com(object.getString("RECOMMEND_COM")
								.trim());
						info.setRecommend_job(object.getString("RECOMMEND_JOB")
								.trim());
						info.setCreate_date(object.getString("CREATE_DATE")
								.trim());
						info.setCreat_staff(object.getInt("CREAT_STAFF"));
						info.setUpdate_date(object.getString("UPDATE_DATE")
								.trim());
						info.setUpdate_staff(object.getInt("UPDATE_STAFF"));
						info.setRecordCount(object.getInt("RecordCount"));
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
						GradeateEditJobGrideActivity.class);
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
		getJobGrideList(index);
	}

}
