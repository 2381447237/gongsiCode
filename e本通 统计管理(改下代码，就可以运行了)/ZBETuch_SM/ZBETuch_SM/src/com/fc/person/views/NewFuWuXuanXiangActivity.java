package com.fc.person.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fc.company.beans.CompanyTask;
import com.fc.gradeate.views.GradeateWorkMarkActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.myservices.MainService;
import com.fc.main.myservices.PersonService;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.FuXuanXiangBean;
import com.fc.person.beans.PersonTask;
import com.fc.zbetuch_sm.R;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewFuWuXuanXiangActivity extends Activity implements IActivity,OnClickListener{

	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private EditText tv_new_fuwu_time,
	tv_new_fuwu_remark;
	private Spinner tv_new_fuwu_content;

	private Button btn_new_fuwu,btn_new_exit,btn_Same;
	
	private Activity mActivity=this;
	private String my_sfz;

	public static final int FUWU_TYPE=0;
	
	public static final int FUWU_QUEDING=1;
	
	private FuXuanXiangBean lastFuWuXuanXiang;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_fuwu_xuanxiang);
		my_sfz=getIntent().getStringExtra("SFZ");
		lastFuWuXuanXiang=(FuXuanXiangBean) getIntent().getSerializableExtra("lastFuWuXuanXiang");
		init();
		initView();

		Map<String, Object> params=new HashMap<String, Object>();
		PersonTask personTask=new PersonTask(PersonTask.FUXUANG_NEW_FUWUCONTENT, params);
		PersonService.newTask(personTask);
	}

	private void initView(){
		tv_new_fuwu_time=(EditText) this.findViewById(R.id.tv_new_fuwu_time);
		tv_new_fuwu_time.setText(sdf.format(new Date()));
		tv_new_fuwu_time.setInputType(InputType.TYPE_NULL);
		tv_new_fuwu_time.setOnClickListener(this);
		tv_new_fuwu_content=(Spinner) this.findViewById(R.id.tv_new_fuwu_content);
		tv_new_fuwu_remark=(EditText) this.findViewById(R.id.tv_new_fuwu_remark);

		btn_new_fuwu=(Button) this.findViewById(R.id.btn_new_fuwu);
		btn_new_fuwu.setOnClickListener(this);
		btn_new_exit=(Button) this.findViewById(R.id.btn_new_exit);
		btn_new_exit.setOnClickListener(this);
		btn_Same=(Button) this.findViewById(R.id.btn_Same);
		if (lastFuWuXuanXiang!=null) {
			btn_Same.setEnabled(true);
			btn_Same.setOnClickListener(this);
		}else{
			btn_Same.setEnabled(false);
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_new_fuwu:
			if (testTemp()) {
				AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
				builder.setTitle("保存信息提示");
				builder.setMessage("您确定保存此服务选项吗？");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						parseListToStr();
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				builder.create();
				builder.show();
				
			}
			break;

		case R.id.btn_new_exit:
			NewFuWuXuanXiangActivity.this.finish();
			break;
			
		case R.id.tv_new_fuwu_time:
			showDateDialog(tv_new_fuwu_time);
			break;
			
		case R.id.btn_Same:
			MainTools.setSpinnerSelect(tv_new_fuwu_content, lastFuWuXuanXiang.getTYPE_NAME());
			tv_new_fuwu_remark.setText(lastFuWuXuanXiang.getMARK());
			break;

		default:
			break;
		}
	}


	private void parseListToStr(){
		String time=tv_new_fuwu_time.getText().toString();
		String new_fuwu_remark=tv_new_fuwu_remark.getText().toString();
		Map<String, Object> params=new HashMap<String, Object>();
		Map<String, String> data=new HashMap<String, String>();
		data.put("ID", "0");
		data.put("SFZ", my_sfz);
		data.put("STAFF", MainService.STAFFID);
		data.put("SERVICE_TIME", time);
		data.put("TYPE", tv_new_fuwu_content.getSelectedItemPosition()+"");
		data.put("MARK", new_fuwu_remark);
		params.put("data", data);
		PersonTask task=new PersonTask(PersonTask.NEW_FUWU_XUANXIANG, params);
		PersonService.newTask(task);
	}
	
	/**
	 * 验证空
	 * @return
	 */
	private boolean testTemp(){
		if (tv_new_fuwu_content.getSelectedItemPosition()==0) {
			Toast.makeText(mActivity, "请选择服务内容！", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;
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
		if (params[0].toString().trim()!=null&&!"".equals(params[0].toString().trim())) {
			switch (Integer.parseInt(params[0].toString().trim())) {
			case NewFuWuXuanXiangActivity.FUWU_TYPE:
				if (params[1].toString().trim()!=null&&!"".equals(params[1].toString().trim())) {
					MainTools.fetchSpinner(mActivity, tv_new_fuwu_content, params[1].toString().trim(), "ID", "NAME");
				}
				break;
				
			case NewFuWuXuanXiangActivity.FUWU_QUEDING:
				if ("True".equals(params[1].toString().trim())) {
					Toast.makeText(mActivity, "添加成功", Toast.LENGTH_SHORT).show();
					// 刷新页面
					FuXuanXiangCardActivity activity = (FuXuanXiangCardActivity) PersonService
							.getActivityByName("FuXuanXiangCardActivity");
				
					if (activity != null) {
						activity.refresh(FuXuanXiangCardActivity.REFRESH_FRM);
						
					}
					mActivity.finish();
 
				} else {
					Toast.makeText(mActivity, "添加失败", Toast.LENGTH_SHORT).show();
				}
				
				break;

			default:
				break;
			}

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
							Toast.makeText(mActivity, "时间必须是当年且小于当前时间！",
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



}
