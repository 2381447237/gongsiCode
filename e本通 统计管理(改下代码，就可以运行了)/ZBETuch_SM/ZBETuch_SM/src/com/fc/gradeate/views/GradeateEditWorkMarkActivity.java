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
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
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
import com.fc.gradeate.beans.JobMarkInfo;
import com.fc.main.beans.IActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.main.myservices.MainService;
import com.fc.main.tools.MainTools;
import com.fc.zbetuch_sm.R;

public class GradeateEditWorkMarkActivity extends Activity implements IActivity {

	private Activity mContext = this;
	private EditText txtVisitDate, txtDetailCompany,txtRemark;
	private Spinner cboBaseSituation, cboDetailSituation,cboInquirer;
	private Button btnOk, btnCancle,btnSame;
	//jobMarkInfo:更新用jobmarkinfo 更新时才有
	//lastJobMarkInfo：用于调查结果同上的jobmarkInfo 新建时才有
	private JobMarkInfo jobMarkInfo,lastJobMarkInfo;
	//是否点击了同上次
	private boolean isFretchLast = false;

	public static final int SAVE_WORKMARK = 1;
	public static final int REFRESH_CBOINQUIRER = 2;

	private ProgressDialog dialog;

	private boolean isShowDialog=false;
	
	private JobMarkInfo updatejobMarkInfo=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gradeate_editworkmark);
		showDialog();
		init();
		initView();
		initListener();
		Intent intent = getIntent();

		jobMarkInfo = (JobMarkInfo) intent.getSerializableExtra("jobMarkInfo");
		lastJobMarkInfo = (JobMarkInfo) intent.getSerializableExtra("lastJobMarkInfo");
		if (jobMarkInfo != null) {
			fretchJobMark();
		}

		if(lastJobMarkInfo==null){
			btnSame.setVisibility(View.GONE);
		}
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case GradeateEditWorkMarkActivity.SAVE_WORKMARK:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				if (value.equalsIgnoreCase("true")) {
					Toast.makeText(mContext, "保存成功！", Toast.LENGTH_SHORT)
					.show();
					// 刷新页面
//					IActivity activity = (IActivity) CompanyService
//							.getActivityByName("GradeateWorkMarkActivity");
//					if (activity != null) {
//						activity.refresh(GradeateWorkMarkActivity.REFRESH_FRM);
//					}
					if (jobMarkInfo!=null) {
						Map<String, Object> param=new HashMap<String, Object>();
						param.put("updatejobMarkInfo", updatejobMarkInfo);
						CompanyTask task=new CompanyTask(CompanyTask.UPDATE_INFO, param);
						CompanyService.newTask(task);
					} else {
						Map<String,Object> param=new HashMap<String, Object>();
						Map<String, String> data=new HashMap<String, String>();
						data.put("master_id", "" + GradeatePersonInfoActivity.gradeateId);
						data.put("rows", "1");
						data.put("page", "0");
						param.put("data", data);
						CompanyTask task=new CompanyTask(CompanyTask.ADD_INFO, param);
						CompanyService.newTask(task);
					}
					mContext.finish();
				} else {
					Toast.makeText(mContext, "保存失败！", Toast.LENGTH_SHORT)
					.show();
				}
			}
			break;
		case GradeateEditWorkMarkActivity.REFRESH_CBOINQUIRER:
			if(params[1]!=null){
				if (dialog.isShowing()&&dialog!=null) {
					dialog.dismiss();
				}
				isShowDialog=true;
				String value = params[1].toString().trim();
				if(value.length()>0){
					GradeatePersonInfoActivity.map.put(GradeatePersonInfoActivity.commiteeId, value);
					MainTools.fetchSpinner(mContext, cboInquirer, value, "ID", "NAME");
					MainTools.setSpinnerSelect(cboInquirer, MainService.STAFFNAME);
					if(jobMarkInfo!=null){
						MainTools.setSpinnerSelect(cboInquirer, jobMarkInfo.getInquirer());
					}
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
	 * 填充走访记录
	 */
	private void fretchJobMark() {
		MainTools.setSpinnerSelect(cboBaseSituation, jobMarkInfo
				.getBase_situation().trim());
		if (jobMarkInfo.getVisit_date().indexOf("T")>0) {
			txtVisitDate.setText(jobMarkInfo.getVisit_date()
					.substring(0, jobMarkInfo.getVisit_date().indexOf("T")).trim());
		}else{
			txtVisitDate.setText(jobMarkInfo.getVisit_date());
		}
		txtDetailCompany.setText(jobMarkInfo.getDetail_company().trim());
		txtRemark.setText(jobMarkInfo.getRemark().trim());

	}

	private void fretchLastJobMark() {
		MainTools.setSpinnerSelect(cboBaseSituation, lastJobMarkInfo
				.getBase_situation().trim());
		txtDetailCompany.setText(lastJobMarkInfo.getDetail_company().trim());
		txtRemark.setText(lastJobMarkInfo.getRemark().trim());

	}

	/**
	 * 页面控件初始化
	 */
	private void initView() {
		txtVisitDate = (EditText) findViewById(R.id.txtVisitDate);
		txtDetailCompany = (EditText) findViewById(R.id.txtDetailCompany);
		cboBaseSituation = (Spinner) findViewById(R.id.cboBaseSituation);
		cboDetailSituation = (Spinner) findViewById(R.id.cboDetailSituation);
		btnOk = (Button) findViewById(R.id.btnOk);
		btnCancle = (Button) findViewById(R.id.btnCancle);
		txtRemark = (EditText) findViewById(R.id.txtRemark);
		btnSame = (Button) findViewById(R.id.btnSame);
		cboInquirer = (Spinner) findViewById(R.id.cboInquirer);

		txtVisitDate.setInputType(InputType.TYPE_NULL);

		Date date = new Date();
		SimpleDateFormat foramt = new SimpleDateFormat("yyyy-MM-dd");
		txtVisitDate.setText(foramt.format(date));

		setTextNotUserful(txtDetailCompany);

		MainTools.setSpinner(mContext, cboBaseSituation,
				R.array.gradeate_baseSituation);
		MainTools.setSpinner(mContext, cboDetailSituation,
				R.array.gradeate_empty);


		if (GradeatePersonInfoActivity.map.get(GradeatePersonInfoActivity.commiteeId)!=null&&!"".equals(GradeatePersonInfoActivity.map.get(GradeatePersonInfoActivity.commiteeId))) {
			Map<String, String>	data=new HashMap<String, String>();
			Map<String, Object> params=new HashMap<String, Object>();
			data.put("info", GradeatePersonInfoActivity.map.get(GradeatePersonInfoActivity.commiteeId));
			params.put("data", data);
			CompanyTask task = new CompanyTask(CompanyTask.GRADEATE_EDITWORKMARKACTIVITY_GET_COMMITTEESTAFFLIST1, params);
			CompanyService.newTask(task);
		} else {
			Map<String, String> data = new HashMap<String, String>();
			data.put("COMMITTEE_ID",""+ GradeatePersonInfoActivity.commiteeId);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", data);
			CompanyTask task = new CompanyTask(CompanyTask.GRADEATE_EDITWORKMARKACTIVITY_GET_COMMITTEESTAFFLIST, params);
			CompanyService.newTask(task);
		}


	}

	/**
	 * 页面监听器初始化
	 */
	private void initListener() {
		btnOk.setOnClickListener(new MyOnClickListener());
		btnCancle.setOnClickListener(new MyOnClickListener());
		cboBaseSituation
		.setOnItemSelectedListener(new MyOnItemSelectedListener());
		txtVisitDate.setOnClickListener(new MyOnClickListener());
		btnSame.setOnClickListener(new MyOnClickListener());
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
		if (cboBaseSituation.getSelectedItem().toString().trim().equals("请选择")) {
			Toast.makeText(mContext, "请选择人员基本情况！", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (cboDetailSituation.getSelectedItem().toString().trim()
				.equals("请选择")) {
			Toast.makeText(mContext, "请选择人员具体情况！", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (cboBaseSituation.getSelectedItem().toString().trim().equals("已就业")
				&& txtDetailCompany.getText().toString().trim().equals("")) {
			Toast.makeText(mContext, "请输入单位名称！", Toast.LENGTH_SHORT).show();
			txtDetailCompany.requestFocus();
			return false;
		}
		if(cboInquirer.getSelectedItemPosition()==0){
			Toast.makeText(mContext, "请选择调查人！", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	/**
	 * 保存走访情况
	 */
	private void saveJobMark() {
		Map<String, String> data = new HashMap<String, String>();
		if (jobMarkInfo == null) {
			data.put("ID", "0");
		} else {
			data.put("ID", "" + jobMarkInfo.getId());
		}
		data.put("MASTER_ID", "" + GradeatePersonInfoActivity.gradeateId);
		data.put("VISIT_DATE", txtVisitDate.getText().toString().trim());
		data.put("BASE_SITUATION", cboBaseSituation.getSelectedItem()
				.toString().trim());
		data.put("DETAIL_SITUATION1", cboDetailSituation.getSelectedItem()
				.toString().trim());
		data.put("DETAIL_SITUATION2", "");
		if (cboBaseSituation.getSelectedItem().toString().trim().equals("已就业")) {
			data.put("DETAIL_COMPANY", txtDetailCompany.getText().toString()
					.trim());
		} else {
			data.put("DETAIL_COMPANY", "");
		}
		data.put("REMARK", txtRemark.getText().toString());
		data.put("INQUIRER", cboInquirer.getSelectedItem().toString().trim());
		updatejobMarkInfo=new JobMarkInfo();
		updatejobMarkInfo.setId(Integer.parseInt(data.get("ID")));
		updatejobMarkInfo.setMaster_id(Integer.parseInt(data.get("MASTER_ID")));
		updatejobMarkInfo.setVisit_date(data.get("VISIT_DATE")
				.trim());
		updatejobMarkInfo.setBase_situation(data.get(
				"BASE_SITUATION").trim());
		updatejobMarkInfo.setDetail_situation1(data.get(
				"DETAIL_SITUATION1").trim());
		updatejobMarkInfo.setDetail_situation2(data.get(
				"DETAIL_SITUATION2").trim());
		updatejobMarkInfo.setDetail_company(data.get(
				"DETAIL_COMPANY").trim());
		updatejobMarkInfo.setRemark(data.get("REMARK").trim());
		updatejobMarkInfo.setInquirer(data.get("INQUIRER").trim());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.GRADEATEEDITWORKMARK_SAVE_WORKMARK, params);
		CompanyService.newTask(task);
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

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnOk:
				if (checkFrm()) {
					AlertDialog.Builder dialog = new AlertDialog.Builder(
							mContext);
					dialog.setTitle("保存信息提示")
					.setMessage("您确定保存此走访情况吗？")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setCancelable(true)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(
								DialogInterface dialog,
								int which) {
							dialog.dismiss();
							saveJobMark();
						}
					}).setNegativeButton("取消", null).show();

				}
				break;
			case R.id.btnCancle:
				finish();
				break;
			case R.id.txtVisitDate:
				showDateDialog(txtVisitDate);
				break;
			case R.id.btnSame:
				if(lastJobMarkInfo!=null){
					isFretchLast = true;
					fretchLastJobMark();
				}
				break;

			}
		}

	}

	private class MyOnItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> v, View view, int position,
				long id) {
			switch (v.getId()) {
			case R.id.cboBaseSituation:
				String value = cboBaseSituation.getSelectedItem().toString()
				.trim();
				if (value.trim().equals("已就业")) {
					MainTools.setSpinner(mContext, cboDetailSituation,
							R.array.gradeate_detailSituation_yjy);
					setTextUserful(txtDetailCompany);
				} else if (value.trim().equals("未就业")) {
					MainTools.setSpinner(mContext, cboDetailSituation,
							R.array.gradeate_detailSituation_wjy);
					setTextNotUserful(txtDetailCompany);
				} else if (value.trim().equals("暂不要求就业")) {
					MainTools.setSpinner(mContext, cboDetailSituation,
							R.array.gradeate_detailSituation_zbjy);
					setTextNotUserful(txtDetailCompany);
				} else if (value.trim().equals("其他")) {
					MainTools.setSpinner(mContext, cboDetailSituation,
							R.array.gradeate_detailSituation_qt);
					setTextNotUserful(txtDetailCompany);
				}
				if (jobMarkInfo != null) {
					MainTools.setSpinnerSelect(cboDetailSituation, jobMarkInfo
							.getDetail_situation1().trim());
				}
				if(isFretchLast && lastJobMarkInfo!=null){
					MainTools.setSpinnerSelect(cboDetailSituation, lastJobMarkInfo
							.getDetail_situation1().trim());
				}
				isFretchLast = false;
				break;
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}

	private void showDialog(){
		dialog=new ProgressDialog(mContext);
		dialog.setTitle("温馨提示");
		dialog.setMessage("数据信息加载中...");
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (isShowDialog&&dialog.isShowing()) {
				dialog.dismiss();
			}else if (!dialog.isShowing()) {
				finish();
			}
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

}
