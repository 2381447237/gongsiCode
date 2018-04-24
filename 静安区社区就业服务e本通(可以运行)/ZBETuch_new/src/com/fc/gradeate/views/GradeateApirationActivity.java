package com.fc.gradeate.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fc.company.beans.CompanyTask;
import com.fc.gradeate.beans.CheckBoxAdapter;
import com.fc.gradeate.beans.RadioButtonAdapter;
import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.test.zbetuch_news.R;

public class GradeateApirationActivity extends Activity implements IActivity {

	private GridView gvComproperty, gvIndustryCategory, gvJobCategory,
			gvSalary;
	private Button btnSave;

	private List<CheckBox> compropertyList = new ArrayList<CheckBox>();
	private List<CheckBox> industryCatagoryList = new ArrayList<CheckBox>();
	private List<CheckBox> jobCatagoryList = new ArrayList<CheckBox>();
	private List<RadioButton> salaryList = new ArrayList<RadioButton>();

	private CheckBoxAdapter compropertyAdapter, industryCatagoryAdapter,
			jobCatagoryAdapter;
	private RadioButtonAdapter salaryAdapter;
	private Activity mContext = this;

	private int id = 0;

	public static final int REFRESH_COMPROPERTY = 1;
	public static final int REFRESH_INDUSTRYCATAGORY = 2;
	public static final int REFRESH_APIRATION = 3;
	public static final int SAVE_APIRATION = 4;
	public static final int REFRESH_FRM = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gradeate_apiration);
		if (GradeatePersonInfoActivity.gradeateId == 0) {
			Toast.makeText(mContext, "人员还未保存，请先保存或选中人员", Toast.LENGTH_SHORT)
					.show();
		}
		init();
		initView();
		initListener();
		initGradeView();
		initApiration();
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case GradeateApirationActivity.REFRESH_COMPROPERTY:
			if (params[1] != null) {
				String value = params[1].toString();
				if (value.trim().length() > 0) {
					fretchComproperty(value);
				}
			}
			break;
		case GradeateApirationActivity.REFRESH_INDUSTRYCATAGORY:
			if (params[1] != null) {
				String value = params[1].toString();
				if (value.trim().length() > 0) {
					fretchIndustryCatagory(value);
				}
			}
			break;
		case GradeateApirationActivity.REFRESH_APIRATION:
			if (params[1] != null) {
				String value = params[1].toString();
				if (value.trim().length() > 0 && !value.trim().equals("[null]")) {
					fretchApiration(value);
				}
			}
			break;
		case GradeateApirationActivity.SAVE_APIRATION:
			if (params[1] != null) {
				String value = params[1].toString();
				if (value.trim().length() > 0) {
					int paramid = Integer.valueOf(value.trim());
					if (paramid != -1) {
						id = paramid;
						Toast.makeText(mContext, "保存成功！", Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(mContext, "保存失败！", Toast.LENGTH_SHORT)
								.show();
					}

				} else {
					Toast.makeText(mContext, "保存失败！", Toast.LENGTH_SHORT)
							.show();
				}
			}
			break;
		case GradeateApirationActivity.REFRESH_FRM:
			initApiration();
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
		gvComproperty = (GridView) findViewById(R.id.gvComproperty);
		gvIndustryCategory = (GridView) findViewById(R.id.gvIndustryCategory);
		gvJobCategory = (GridView) findViewById(R.id.gvJobCategory);
		gvSalary = (GridView) findViewById(R.id.gvSalary);
		btnSave = (Button) findViewById(R.id.btnSave);

		compropertyAdapter = new CheckBoxAdapter(compropertyList, mContext);
		industryCatagoryAdapter = new CheckBoxAdapter(industryCatagoryList,
				mContext);
		jobCatagoryAdapter = new CheckBoxAdapter(jobCatagoryList, mContext);
		salaryAdapter = new RadioButtonAdapter(salaryList, mContext);

		gvComproperty.setAdapter(compropertyAdapter);
		gvIndustryCategory.setAdapter(industryCatagoryAdapter);
		gvJobCategory.setAdapter(jobCatagoryAdapter);
		gvSalary.setAdapter(salaryAdapter);

	}

	/**
	 * 页面监听器初始化
	 */
	private void initListener() {
		btnSave.setOnClickListener(new MyOnClickListener());
	}

	/**
	 * 初始化个人求职意向
	 */
	private void initApiration() {
		if (GradeatePersonInfoActivity.gradeateId != 0) {
			Map<String, String> data = new HashMap<String, String>();
			data.put("master_id", "" + GradeatePersonInfoActivity.gradeateId);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", data);
			CompanyTask task = new CompanyTask(
					CompanyTask.GRADEATEAPIRATIONACTIVITY_GET_APIRATION, params);
			CompanyService.newTask(task);
		}
	}

	/**
	 * 初始化多选框
	 */
	private void initGradeView() {
		CompanyTask task1 = new CompanyTask(
				CompanyTask.GRADEATEAPIRATIONACTIVITY_GET_COMPROPERTY, null);
		CompanyService.newTask(task1);
		CompanyTask task2 = new CompanyTask(
				CompanyTask.GRADEATEAPIRATIONACTIVITY_GET_INDUSTRYCATAGORY,
				null);
		CompanyService.newTask(task2);
		fretchJobCatagory();
		fretchSalary();
	}

	/**
	 * 填充个人求职意向
	 * 
	 * @param value
	 */
	private void fretchApiration(String value) {
		gvComproperty.setAdapter(compropertyAdapter);
		gvIndustryCategory.setAdapter(industryCatagoryAdapter);
		gvJobCategory.setAdapter(jobCatagoryAdapter);
		gvSalary.setAdapter(salaryAdapter);

		try {
			JSONArray jsonArray = new JSONArray(value);
			if (jsonArray != null && jsonArray.length() > 0) {
				JSONObject object = jsonArray.optJSONObject(0);
				compropertyAdapter.setValue(object.getString("COMPROPERTYID1")
						.trim());
				compropertyAdapter.setValue(object.getString("COMPROPERTYID2")
						.trim());
				compropertyAdapter.setValue(object.getString("COMPROPERTYID3")
						.trim());
				compropertyAdapter.notifyDataSetChanged();
				industryCatagoryAdapter.setValue(object.getString(
						"INDUSTRY_CATEGORY1").trim());
				industryCatagoryAdapter.setValue(object.getString(
						"INDUSTRY_CATEGORY2").trim());
				industryCatagoryAdapter.setValue(object.getString(
						"INDUSTRY_CATEGORY3").trim());
				industryCatagoryAdapter.notifyDataSetChanged();
				jobCatagoryAdapter.setValue(object.getString("JOB_CATEGORY1")
						.trim());
				jobCatagoryAdapter.setValue(object.getString("JOB_CATEGORY2")
						.trim());
				jobCatagoryAdapter.setValue(object.getString("JOB_CATEGORY3")
						.trim());
				jobCatagoryAdapter.notifyDataSetChanged();
				salaryAdapter.setValue(object.getString("SALARY1").trim());
				// salaryAdapter.setValue(object.getString("SALARY2").trim());
				// salaryAdapter.setValue(object.getString("SALARY3").trim());
				salaryAdapter.notifyDataSetChanged();
				id = object.getInt("ID");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 填充企业类型复选框
	 * 
	 * @param value
	 */
	private void fretchComproperty(String value) {
		try {
			JSONArray jsonArray = new JSONArray(value);
			if (jsonArray != null && jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject object = jsonArray.optJSONObject(i);
					CheckBox box = (CheckBox) LayoutInflater.from(mContext)
							.inflate(R.layout.item_gradeate_checkbox, null);
					box.setText(object.getString("compropertyname").trim());
					box.setTag(object.getString("compropertyid").trim());
					compropertyList.add(box);
				}
			}
			compropertyAdapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 填充行业类型复选框
	 * 
	 * @param value
	 */
	private void fretchIndustryCatagory(String value) {
		try {
			JSONArray jsonArray = new JSONArray(value);
			if (jsonArray != null && jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject object = jsonArray.optJSONObject(i);
					CheckBox box = (CheckBox) LayoutInflater.from(mContext)
							.inflate(R.layout.item_gradeate_checkbox, null);
					box.setText(object.getString("name").trim());
					box.setTag(object.getString("id").trim());
					industryCatagoryList.add(box);
				}
			}
			industryCatagoryAdapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 填充岗位类别复选框
	 */
	private void fretchJobCatagory() {
		String[] jobStys = getResources().getStringArray(
				R.array.gradeate_jobStyle);
		for (String job : jobStys) {
			CheckBox box = (CheckBox) LayoutInflater.from(mContext).inflate(
					R.layout.item_gradeate_checkbox, null);
			box.setText(job.trim());
			box.setTag(job.trim());
			jobCatagoryList.add(box);
		}
		jobCatagoryAdapter.notifyDataSetChanged();
	}

	/**
	 * 填充工资类别复选框
	 */
	private void fretchSalary() {
		String[] jobStys = getResources().getStringArray(
				R.array.gradeate_salaryStyle);
		for (String job : jobStys) {
			RadioButton box = (RadioButton) LayoutInflater.from(mContext)
					.inflate(R.layout.item_gradeate_radiobutton, null);
			box.setText(job.trim());
			box.setTag(job.trim());
			salaryList.add(box);
		}
		salaryAdapter.notifyDataSetChanged();
	}

	/**
	 * 页面非空验证
	 * 
	 * @return
	 */
	private boolean checkFrm() {
		if (GradeatePersonInfoActivity.gradeateId == 0) {
			Toast.makeText(mContext, "人员还未保存，请先保存或选中人员", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (compropertyAdapter.getSelectedBoxs().size() == 0) {
			Toast.makeText(mContext, "请选择企业类型！", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (industryCatagoryAdapter.getSelectedBoxs().size() == 0) {
			Toast.makeText(mContext, "请选择行业类别", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (jobCatagoryAdapter.getSelectedBoxs().size() == 0) {
			Toast.makeText(mContext, "请选择岗位类别！", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (salaryAdapter.getSelectedButton() == null) {
			Toast.makeText(mContext, "请选择税后薪资！", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	/**
	 * 保存求职意愿
	 */
	private void saveApiration() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("ID", "" + id);
		data.put("MASTER_ID", "" + GradeatePersonInfoActivity.gradeateId);
		List<CheckBox> selectedBoxs = null;
		selectedBoxs = compropertyAdapter.getSelectedBoxs();
		data.put("COMPROPERTYID1", selectedBoxs.get(0).getTag().toString()
				.trim());
		data.put("COMPROPERTYID2",
				selectedBoxs.size() >= 2 ? selectedBoxs.get(1).getTag()
						.toString().trim() : "-1");
		data.put("COMPROPERTYID3",
				selectedBoxs.size() == 3 ? selectedBoxs.get(2).getTag()
						.toString().trim() : "-1");
		selectedBoxs = industryCatagoryAdapter.getSelectedBoxs();
		data.put("INDUSTRY_CATEGORY1", selectedBoxs.get(0).getTag().toString()
				.trim());
		data.put("INDUSTRY_CATEGORY2", selectedBoxs.size() >= 2 ? selectedBoxs
				.get(1).getTag().toString().trim() : "-1");
		data.put("INDUSTRY_CATEGORY3", selectedBoxs.size() == 3 ? selectedBoxs
				.get(2).getTag().toString().trim() : "-1");
		selectedBoxs = jobCatagoryAdapter.getSelectedBoxs();
		data.put("JOB_CATEGORY1", selectedBoxs.get(0).getTag().toString()
				.trim());
		data.put("JOB_CATEGORY2", selectedBoxs.size() >= 2 ? selectedBoxs
				.get(1).getTag().toString().trim() : "");
		data.put("JOB_CATEGORY3", selectedBoxs.size() == 3 ? selectedBoxs
				.get(2).getTag().toString().trim() : "");
		// selectedBoxs = salaryAdapter.getSelectedBoxs();
		// data.put("SALARY1", selectedBoxs.get(0).getTag().toString().trim());
		// data.put("SALARY2", selectedBoxs.size() >= 2 ? selectedBoxs.get(1)
		// .getTag().toString().trim() : "");
		// data.put("SALARY3", selectedBoxs.size() == 3 ? selectedBoxs.get(2)
		// .getTag().toString().trim() : "");
		data.put("SALARY1", salaryAdapter.getSelectedButton().getTag()
				.toString().trim());
		data.put("SALARY2", "");
		data.put("SALARY3", "");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		
		Log.e("2017/10/19", "个人求职意愿="+data);
		
		CompanyTask task = new CompanyTask(
				CompanyTask.GRADEATEAPIRATIONACTIVITY_SAVE_APIRATION, params);
		CompanyService.newTask(task);
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnSave:
				if (checkFrm()) {
					AlertDialog.Builder dialog = new AlertDialog.Builder(
							mContext);
					dialog.setTitle("保存信息提示")
							.setMessage("您确定保存此个人求职意愿吗？")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setCancelable(true)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											saveApiration();

										}
									}).setNegativeButton("取消", null).show();
				}
				break;
			}
		}

	}

}
