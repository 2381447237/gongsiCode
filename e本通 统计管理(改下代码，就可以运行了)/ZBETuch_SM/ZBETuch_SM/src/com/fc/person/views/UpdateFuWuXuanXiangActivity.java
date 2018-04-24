package com.fc.person.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fc.main.beans.IActivity;
import com.fc.main.myservices.MainService;
import com.fc.main.myservices.PersonService;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.FuXuanXiangBean;
import com.fc.person.beans.PersonTask;
import com.fc.zbetuch_sm.R;

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

public class UpdateFuWuXuanXiangActivity extends Activity implements IActivity,OnClickListener{
	private EditText tv_update_fuwu_time,tv_fuwu_remark;
	private Spinner tv_fuwu_content;

	private Button btn_update_fuwu,btn_update_exit;
	private FuXuanXiangBean fuXuanXiangBean;
	
	private UpdateFuWuXuanXiangActivity mActivity=this;
	public static final int FUWU_TYPE=0;
	public static final int FUWU_UPDATE=1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_fuwu_xuanxiang);
		fuXuanXiangBean=(FuXuanXiangBean) getIntent().getSerializableExtra("fuwuxuanxiang");
		init();
		initView();
		
		Map<String, Object> params=new HashMap<String, Object>();
		PersonTask personTask=new PersonTask(PersonTask.FUWUANG_UPDATE_FUWUCONTENT, params);
		PersonService.newTask(personTask);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	private void initView(){
		tv_update_fuwu_time=(EditText) this.findViewById(R.id.tv_update_fuwu_time);
		tv_update_fuwu_time.setText(fuXuanXiangBean.getSERVICE_TIME().replace("T", " ").substring(0, 19));
		tv_update_fuwu_time.setInputType(InputType.TYPE_NULL);
		tv_update_fuwu_time.setOnClickListener(this);
		tv_fuwu_content=(Spinner) this.findViewById(R.id.tv_fuwu_content);
//		tv_fuwu_content.setText(fuXuanXiangBean.getTYPE_NAME());
		tv_fuwu_remark=(EditText) this.findViewById(R.id.tv_fuwu_remark);
		tv_fuwu_remark.setText(fuXuanXiangBean.getMARK());
		
		btn_update_fuwu=(Button) this.findViewById(R.id.btn_update_fuwu);
		btn_update_fuwu.setOnClickListener(this);
		btn_update_exit=(Button) this.findViewById(R.id.btn_update_exit);
		btn_update_exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_update_fuwu:
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

		case R.id.btn_update_exit:
			UpdateFuWuXuanXiangActivity.this.finish();
			break;
			
		case R.id.tv_update_fuwu_time:
			showDateDialog(tv_update_fuwu_time);
			break;

		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		if (params[0].toString().trim()!=null&&!"".equals(params[0].toString().trim())) {
			switch (Integer.parseInt(params[0].toString().trim())) {
			case UpdateFuWuXuanXiangActivity.FUWU_TYPE:
				if (params[1].toString().trim()!=null&&!"".equals(params[1].toString().trim())) {
					MainTools.fetchSpinner(mActivity, tv_fuwu_content, params[1].toString().trim(), "ID", "NAME");
					MainTools.setSpinnerSelect(tv_fuwu_content, fuXuanXiangBean.getTYPE_NAME());
				}
				break;
				
			case UpdateFuWuXuanXiangActivity.FUWU_UPDATE:
				if ("True".equals(params[1].toString().trim())) {
					Toast.makeText(mActivity, "修改成功", Toast.LENGTH_SHORT).show();
					// 刷新页面
					IActivity activity = (IActivity) PersonService
							.getActivityByName("FuXuanXiangCardActivity");
					if (activity != null) {
						activity.refresh(FuXuanXiangCardActivity.REFRESH_FRM);
					}
					mActivity.finish();
				} else {
					Toast.makeText(mActivity, "修改失败", Toast.LENGTH_SHORT).show();					
				}
				
				break;

			default:
				break;
			}

		}
	}
	
	private void parseListToStr(){
		if (!String.valueOf(MainService.STAFFID.trim()).equals(String.valueOf(fuXuanXiangBean.getSTAFF()).trim())) {
			Toast.makeText(mActivity, "没有权限修改", Toast.LENGTH_SHORT).show();
			return ;
		} else {
			String time=tv_update_fuwu_time.getText().toString();
			String new_fuwu_remark=tv_fuwu_remark.getText().toString();
			Map<String, Object> params=new HashMap<String, Object>();
			Map<String, String> data=new HashMap<String, String>();
			data.put("ID", fuXuanXiangBean.getID()+"");
			data.put("SFZ", fuXuanXiangBean.getSFZ());
			data.put("STAFF", MainService.STAFFID);
			data.put("SERVICE_TIME", time);
			data.put("TYPE", tv_fuwu_content.getSelectedItemPosition()+"");
			data.put("MARK", new_fuwu_remark);
			params.put("data", data);
			PersonTask task=new PersonTask(PersonTask.UPDATE_FUWU_XUANXIANG, params);
			PersonService.newTask(task);

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
	
	/**
	 * 验证空
	 * @return
	 */
	private boolean testTemp(){
		if (tv_fuwu_content.getSelectedItemPosition()==0) {
			Toast.makeText(mActivity, "请选择服务内容！", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;
	}

}
