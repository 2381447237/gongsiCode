package com.fc.gradeate.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fc.company.beans.CompanyTask;
import com.fc.gradeate.beans.JobGrideInfo;
import com.fc.main.beans.IActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.zbetuch_sm.R;

public class GradeateEditJobGrideActivity extends Activity implements IActivity {

	private Activity mContext = this;
	private Spinner cboCheckGride, cboCheckRecommend;
	private EditText txtGrideDate, txtGrideName, txtGridDoc, txtRecommendCom,
			txtRecommendJob, txtRecommendDate;
	private Button btnOk, btnCancle;
	private JobGrideInfo jobGrideInfo;
	public static final int SAVE_JOBGRIDE = 1;
	
	private JobGrideInfo updatejobGrideInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gradeate_editjobgride);
		init();
		initView();
		initListener();
		Intent intent = getIntent();
		jobGrideInfo = (JobGrideInfo) intent
				.getSerializableExtra("jobGrideInfo");
		if (jobGrideInfo != null) {
			fretchJobGride();
		}
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case GradeateEditJobGrideActivity.SAVE_JOBGRIDE:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				if (value.equalsIgnoreCase("true")) {
					Toast.makeText(mContext, "保存成功！", Toast.LENGTH_SHORT)
							.show();
//					// 刷新页面
//					IActivity activity = (IActivity) CompanyService
//							.getActivityByName("GradeateWorkGrideActivity");
//
//					if (activity != null) {
//						activity.refresh(GradeateWorkGrideActivity.REFRESH_FRM);
//					}
					if (jobGrideInfo!=null) {
						Map<String, Object> param=new HashMap<String, Object>();
						param.put("updatejobGrideInfo", updatejobGrideInfo);
						CompanyTask task=new CompanyTask(CompanyTask.UPDATE_GRADEINFO, param);
						CompanyService.newTask(task);
					} else {
						Map<String,Object> param=new HashMap<String, Object>();
						Map<String, String> data=new HashMap<String, String>();
						data.put("master_id", "" + GradeatePersonInfoActivity.gradeateId);
						data.put("rows", "1");
						data.put("page", "0");
						param.put("data", data);
						CompanyTask task=new CompanyTask(CompanyTask.ADD_GRADEINFO, param);
						CompanyService.newTask(task);
					}
					mContext.finish();
				} else {
					Toast.makeText(mContext, "保存失败！", Toast.LENGTH_SHORT)
							.show();
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
	 * 控件初始化
	 */
	private void initView() {
		cboCheckGride = (Spinner) findViewById(R.id.cboCheckGride);
		cboCheckRecommend = (Spinner) findViewById(R.id.cboCheckRecommend);
		txtGrideDate = (EditText) findViewById(R.id.txtGrideDate);
		txtGrideName = (EditText) findViewById(R.id.txtGrideName);
		txtGridDoc = (EditText) findViewById(R.id.txtGridDoc);
		txtRecommendCom = (EditText) findViewById(R.id.txtRecommendCom);
		txtRecommendJob = (EditText) findViewById(R.id.txtRecommendJob);
		txtRecommendDate = (EditText) findViewById(R.id.txtRecommendDate);
		btnOk = (Button) findViewById(R.id.btnOk);
		btnCancle = (Button) findViewById(R.id.btnCancle);

		txtGrideDate.setInputType(InputType.TYPE_NULL);
		txtRecommendDate.setInputType(InputType.TYPE_NULL);

		setGrideNotUserful();
		setRecommendNotUserful();
	}

	/**
	 * 设置推荐企业无效
	 */
	private void setRecommendNotUserful() {
		setTextNotUserful(txtRecommendCom);
		setTextNotUserful(txtRecommendJob);
		setTextNotUserful(txtRecommendDate);
	}

	/**
	 * 设置推荐企业有效
	 */
	private void setRecommendUserful() {
		setTextUserful(txtRecommendCom);
		setTextUserful(txtRecommendJob);
		setTextUserful(txtRecommendDate);
		Date date = new Date();
		SimpleDateFormat foramt = new SimpleDateFormat("yyyy-MM-dd");
		txtRecommendDate.setText(foramt.format(date));
	}

	/**
	 * 设置职业指导员无效
	 */
	private void setGrideNotUserful() {
		setTextNotUserful(txtGrideDate);
		setTextNotUserful(txtGrideName);
		setTextNotUserful(txtGridDoc);
	}

	/**
	 * 设置职业指导员有效
	 */
	private void setGrideuserful() {
		setTextUserful(txtGrideDate);
		setTextUserful(txtGrideName);
		setTextUserful(txtGridDoc);

		Date date = new Date();
		SimpleDateFormat foramt = new SimpleDateFormat("yyyy-MM-dd");
		txtGrideDate.setText(foramt.format(date));
	}

	/**
	 * 设置文本框有效
	 * 
	 * @param txt
	 */
	private void setTextUserful(EditText txt) {
		txt.setEnabled(true);
		txt.setBackgroundResource(R.drawable.txtbg);
	}

	/**
	 * 设置文本框无效
	 * 
	 * @param txt
	 */
	private void setTextNotUserful(EditText txt) {
		txt.setEnabled(false);
		txt.setBackgroundResource(R.drawable.txtbgnouse);
		txt.setText("");
	}

	/**
	 * 初始化页面监听器
	 */
	private void initListener() {
		txtGrideDate.setOnClickListener(new MyOnClickListener());
		txtRecommendDate.setOnClickListener(new MyOnClickListener());
		btnOk.setOnClickListener(new MyOnClickListener());
		btnCancle.setOnClickListener(new MyOnClickListener());
		cboCheckGride.setOnItemSelectedListener(new MyOnItemSelectedListener());
		cboCheckRecommend
				.setOnItemSelectedListener(new MyOnItemSelectedListener());
	}

	/**
	 * 填充职业指导情况
	 */
	private void fretchJobGride() {
		if (Boolean.parseBoolean(jobGrideInfo.getCheck_guide().trim()
				.toLowerCase())) {
			cboCheckGride.setSelection(1);
			if (jobGrideInfo.getGuide_date().indexOf("T")>0) {
				txtGrideDate.setText(jobGrideInfo.getGuide_date()
						.substring(0, jobGrideInfo.getGuide_date().indexOf("T"))
						.trim());
			} else {
				txtGrideDate.setText(jobGrideInfo.getGuide_date());
			}
			
			txtGrideName.setText(jobGrideInfo.getGuide_name().trim());
			txtGridDoc.setText(jobGrideInfo.getGuide_doc().trim());
		} else {
			cboCheckGride.setSelection(2);
		}
		if (Boolean.parseBoolean(jobGrideInfo.getCheck_recommend().trim()
				.toLowerCase())) {
			cboCheckRecommend.setSelection(1);
			if (jobGrideInfo.getRecommend_date().indexOf("T")>0) {
				txtRecommendDate.setText(jobGrideInfo.getRecommend_date()
						.substring(0, jobGrideInfo.getRecommend_date().indexOf("T"))
						.trim());
			} else {
				txtRecommendDate.setText(jobGrideInfo.getRecommend_date());
			}
			
			txtRecommendCom.setText(jobGrideInfo.getRecommend_com().trim());
			txtRecommendJob.setText(jobGrideInfo.getRecommend_job().trim());
		} else {
			cboCheckRecommend.setSelection(2);
		}
	}

	/**
	 * 显示时间对话框
	 * 
	 * @param txt
	 */
	private void showDateDialog(final EditText txt) {
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String value = txt.getText().toString().trim();
		Calendar day = Calendar.getInstance();
		try {
			day.setTime(format.parse(value));
			new DatePickerDialog(this, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					try {
						Date today = format.parse(format.format(new Date()));
						Date selectDate = new Date(year - 1900, monthOfYear,
								dayOfMonth);
						if (today.getYear() == selectDate.getYear()
								&& selectDate.compareTo(today) <= 0) {
							txt.setText(format.format(selectDate));
						} else {
							Toast.makeText(mContext, "时间必须是当年且小于当前时间！",
									Toast.LENGTH_SHORT).show();
							txt.setText(format.format(today));
						}

					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}, day.get(Calendar.YEAR), day.get(Calendar.MONTH),
					day.get(Calendar.DATE)).show();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 页面非空验证
	 * 
	 * @return
	 */
	private boolean checkFrm() {
		if (GradeatePersonInfoActivity.gradeateId == 0) {
			Toast.makeText(mContext, "人员还未保存，请先保存或选中人员！", Toast.LENGTH_SHORT)
					.show();
			return false;
		}

		if (cboCheckGride.getSelectedItem().toString().trim().equals("请选择")) {
			Toast.makeText(mContext, "请选择是否职业指导！", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (cboCheckGride.getSelectedItem().toString().trim().equals("是")) {
			if (txtGrideName.getText().toString().trim().length() == 0) {
				Toast.makeText(mContext, "请输入职业指导员！", Toast.LENGTH_SHORT)
						.show();
				txtGrideName.requestFocus();
				return false;
			}
			if (txtGridDoc.getText().toString().trim().length() == 0) {
				Toast.makeText(mContext, "请输入职业指导内容！", Toast.LENGTH_SHORT)
						.show();
				txtGridDoc.requestFocus();
				return false;
			}
		}
		if (cboCheckRecommend.getSelectedItem().toString().trim().equals("请选择")) {
			Toast.makeText(mContext, "请选择是否推荐就业岗位！", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (cboCheckRecommend.getSelectedItem().toString().trim().equals("是")) {
			if (txtRecommendCom.getText().toString().trim().length() == 0) {
				Toast.makeText(mContext, "请输入推荐面试单位名称！", Toast.LENGTH_SHORT)
						.show();
				txtRecommendCom.requestFocus();
				return false;
			}
			if (txtRecommendJob.getText().toString().trim().length() == 0) {
				Toast.makeText(mContext, "请输入推荐面试岗位名称！", Toast.LENGTH_SHORT)
						.show();
				txtRecommendJob.requestFocus();
				return false;
			}
		}

		return true;
	}

	/**
	 * 保存职业指导情况
	 */
	private void saveJobGride() {
		Map<String, String> data = new HashMap<String, String>();
		if (jobGrideInfo == null) {
			data.put("ID", "0");
		} else {
			data.put("ID", "" + jobGrideInfo.getId());
		}
		data.put("MASTER_ID", "" + GradeatePersonInfoActivity.gradeateId);
		data.put("CHECK_GUIDE", cboCheckGride.getSelectedItem().toString()
				.trim().equals("是") ? "true" : "false");
		data.put("GUIDE_DATE", txtGrideDate.getText().toString().trim()
				.length() == 0 ? "" : txtGrideDate
				.getText().toString().trim());
		data.put("GUIDE_NAME", txtGrideName.getText().toString().trim());
		data.put("GUIDE_DOC", txtGridDoc.getText().toString().trim());
		data.put("CHECK_RECOMMEND", cboCheckRecommend.getSelectedItem()
				.toString().trim().equals("是") ? "true" : "false");
		data.put("RECOMMEND_DATE", txtRecommendDate.getText().toString().trim()
				.length() == 0 ? (new SimpleDateFormat("yyyy-MM-dd").format(new Date())) : txtRecommendDate
				.getText().toString().trim());
		data.put("RECOMMEND_COM", txtRecommendCom.getText().toString().trim());
		data.put("RECOMMEND_JOB", txtRecommendJob.getText().toString().trim());
		updatejobGrideInfo=new JobGrideInfo();
		updatejobGrideInfo.setCheck_guide(data.get("CHECK_GUIDE"));
		updatejobGrideInfo.setCheck_recommend(data.get("CHECK_RECOMMEND"));
		updatejobGrideInfo.setGuide_date(data.get("GUIDE_DATE"));
		updatejobGrideInfo.setGuide_doc(data.get("GUIDE_DOC"));
		updatejobGrideInfo.setGuide_name(data.get("GUIDE_NAME"));
		updatejobGrideInfo.setId(Integer.parseInt(data.get("ID")));
		updatejobGrideInfo.setMaster_id(Integer.parseInt(data.get("MASTER_ID")));
		updatejobGrideInfo.setRecommend_com(data.get("RECOMMEND_COM"));
		updatejobGrideInfo.setRecommend_date(data.get("RECOMMEND_DATE"));
		updatejobGrideInfo.setRecommend_job(data.get("RECOMMEND_JOB"));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.GRADEATEEDITJOBGRIDACTIVITY_SAVE_JOBGRIDE, params);
		CompanyService.newTask(task);
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnOk:
				if (checkFrm()) {
					AlertDialog.Builder dialog = new AlertDialog.Builder(
							mContext);
					dialog.setTitle("保存信息提示")
							.setMessage("您确定保存此职业指导情况信息吗？")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setCancelable(true)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											saveJobGride();
										}
									}).setNegativeButton("取消", null).show();

				}
				break;

			case R.id.btnCancle:
				finish();
				break;
			case R.id.txtGrideDate:
				showDateDialog(txtGrideDate);
				break;
			case R.id.txtRecommendDate:
				showDateDialog(txtRecommendDate);
				break;
			}
		}

	}

	private class MyOnItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> v, View arg1, int arg2,
				long arg3) {
			switch (v.getId()) {
			case R.id.cboCheckGride:
				if (cboCheckGride.getSelectedItem().toString().trim()
						.equals("是")) {
					setGrideuserful();
				} else {
					setGrideNotUserful();
				}
				break;
			case R.id.cboCheckRecommend:
				if (cboCheckRecommend.getSelectedItem().toString().trim()
						.equals("是")) {
					setRecommendUserful();
				} else {
					setRecommendNotUserful();
				}
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}

}
