package com.fc.ziyuan.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fc.main.beans.IActivity;
import com.fc.main.myservices.MainService;
import com.fc.main.myservices.PersonService;
import com.fc.person.beans.PersonTask;
import com.fc.person.views.PersoninfoMainActivity;
import com.fc.zbetuch_sm.R;
import com.fc.ziyuan.bean.ResourcesDetailInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class ShiYeDetailActivity extends Activity implements IActivity{
	private ResourcesDetailInfo infos;
	private EditText idcard,name,carture,adress,street,
	juwei,status,data,current_yixiang,shideng_data,shidengs_data;
	private Button shideng_btn,diaocha_btn ,shiye_result_tall;
	private EditText diaocha_data,diaocha_remark,
	diaocha_person;
	private Spinner status_spinner,current_spinner;
	private LinearLayout infoLayout;
	private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final int REFRESH_INFO=0;
	private boolean isSave=false;
	private Button xiangxi_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shiye_deatil);
		infos=(ResourcesDetailInfo) getIntent().getSerializableExtra("infos");
		init();
		initView();
		initValue();
	}
	
	private void initView(){
		idcard=(EditText) this.findViewById(R.id.shiye_idcard);
		name=(EditText) this.findViewById(R.id.shiye_name);
		carture=(EditText) this.findViewById(R.id.shiye_carture);
		adress=(EditText) this.findViewById(R.id.shiye_adress);
		street=(EditText) this.findViewById(R.id.shiye_street);
		juwei=(EditText) this.findViewById(R.id.shiye_juwei);
		status=(EditText) this.findViewById(R.id.shiye_status);
		data=(EditText) this.findViewById(R.id.shiye_data);
		current_yixiang=(EditText) this.findViewById(R.id.shiye_current_yixiang);
		shideng_data=(EditText) this.findViewById(R.id.shiye_shideng_data);
		shidengs_data=(EditText) this.findViewById(R.id.shiye_shidengs_data);
		infoLayout=(LinearLayout) this.findViewById(R.id.my_detail_info);
		shideng_btn=(Button) this.findViewById(R.id.shiye_shideng_btn);
		shiye_result_tall=(Button) this.findViewById(R.id.shiye_result_tall);
		shiye_result_tall.setOnClickListener(new MyOnClickListener());
		
		diaocha_data=(EditText) this.findViewById(R.id.shiye_diaocha_data);
		diaocha_remark=(EditText) this.findViewById(R.id.shiye_diaocha_remark);
		diaocha_person=(EditText) this.findViewById(R.id.shiye_diaocha_person);
		diaocha_data.setInputType(InputType.TYPE_NULL);
		xiangxi_btn = (Button) findViewById(R.id.shiye_xxzl_btn);
		xiangxi_btn.setOnClickListener(new MyOnClickListener());
		diaocha_data.setOnClickListener(new MyOnClickListener());
		shideng_btn.setOnClickListener(new MyOnClickListener());
		
		
		status_spinner=(Spinner) this.findViewById(R.id.shiye_status_spinner);
		current_spinner=(Spinner) this.findViewById(R.id.shiye_current_spinner);
		
		diaocha_btn=(Button) this.findViewById(R.id.shiye_diaocha_btn);
		diaocha_btn.setOnClickListener(new MyOnClickListener());
		
		
		setSpinner(status_spinner,R.array.resources_shiye_status);
		setSpinner(current_spinner, R.array.resources_shiye_yixiang);
	}
	
	private void initValue(){
		idcard.setText(infos.getSFZ().toString().trim());
		name.setText(infos.getNAME());
		carture.setText(infos.getEDU());
		adress.setText(infos.getHKDZ());
		street.setText(infos.getJD());
		juwei.setText(infos.getJW());
		status.setText(infos.getMQZK());
		data.setText(infos.getMDRQ());
		current_yixiang.setText(infos.getDQYX());
		shideng_data.setText(infos.getZJSYDJRQ().substring(0, 10));
		shidengs_data.setText(infos.getSYDJYXQ().substring(0, 10));
		
		if ("".equals(infos.getNEW_DQYX())||"".equals(infos.getNEW_MQZK())||null==infos.getNEW_DQYX()||null==infos.getNEW_MQZK()||"null".equals(infos.getNEW_DQYX())||"null".equals(infos.getNEW_MQZK())) {
			infoLayout.setVisibility(View.GONE);
			diaocha_btn.setEnabled(true);
		} else {
			infoLayout.setVisibility(View.VISIBLE);
			if ("null".equals(infos.getMARK().toString().trim())) {
				diaocha_remark.setText("");
			}else{
				diaocha_remark.setText(infos.getMARK().toString().trim());
			}
			setSpinnerSelection(status_spinner, infos.getNEW_MQZK());
			setSpinnerSelection(current_spinner, infos.getNEW_DQYX());
			diaocha_btn.setEnabled(false);
			diaocha_btn.setBackgroundResource(R.drawable.button_enabled);
		}
		diaocha_data.setText(infos.getSURVEY_DATE().substring(0, 10));
		diaocha_person.setText(MainService.STAFFNAME);
	}
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.shiye_shideng_btn:
				infoLayout.setVisibility(View.VISIBLE);
				break;
			
			case R.id.shiye_xxzl_btn:
				String personSFZ = infos.getSFZ().toString().trim();
				Intent intent = new Intent(ShiYeDetailActivity.this,
						PersoninfoMainActivity.class);
				intent.putExtra("mysfz", personSFZ);
				startActivityForResult(intent, 300);
				break;
				
			case R.id.shiye_diaocha_data:
				showDateDialog(diaocha_data);
				break;
				
			case R.id.shiye_diaocha_btn:
				if (isSave) {
					Toast.makeText(ShiYeDetailActivity.this, "数据不能重复上传！", Toast.LENGTH_SHORT).show();
					return ;
				}
//				if ("".equals(infos.getDQYX())||"".equals(infos.getMQZK())||"".equals(infos.getMARK())||null==infos.getDQYX()||null!=infos.getMQZK()||null==infos.getMARK()) {
//					Toast.makeText(ShiYeDetailActivity.this, "数据不能上传！", Toast.LENGTH_SHORT).show();
//					return ;
//				}
				Builder builder=new AlertDialog.Builder(ShiYeDetailActivity.this);
				builder.setTitle("温馨提示").setMessage("确定保存此信息吗?");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						saveInfo();
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
				break;
				
			case R.id.shiye_result_tall:
				setSpinnerSelection(status_spinner, infos.getMQZK());
				setSpinnerSelection(current_spinner,infos.getDQYX());			
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
						Date today = format.parse(diaocha_data.getText().toString().trim());
						Date selectDate = new Date(year - 1900, monthOfYear,
								dayOfMonth);
						if (selectDate.compareTo(today) <= 0) {
							txt.setText(format.format(selectDate));
						} else {
							Toast.makeText(ShiYeDetailActivity.this, "时间必须是当年且小于当前时间！",
									Toast.LENGTH_SHORT).show();
//							txt.setText(format.format(today));
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
		case REFRESH_INFO:
			if (!"".equals(params[1].toString().trim())||params[1].toString().trim()!=null) {
				if ("True".equals(params[1].toString().trim())) {
					isSave=true;
					Toast.makeText(ShiYeDetailActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(ShiYeDetailActivity.this, "上传失败！", Toast.LENGTH_SHORT).show();
				}
				
			}
			break;

		default:
			break;
		}
	}
	
	private void saveInfo(){
		if (status_spinner.getSelectedItemPosition()==0) {
			Toast.makeText(ShiYeDetailActivity.this, "请选择目前情况!", Toast.LENGTH_SHORT).show();
			return ;
		}
		if (current_spinner.getSelectedItemPosition()==0) {
			Toast.makeText(ShiYeDetailActivity.this, "请选择当前意向!", Toast.LENGTH_SHORT).show();
			return ;
		}
		Map<String, Object> params=new HashMap<String, Object>();
		Map<String, String> data=new HashMap<String, String>();
		data.put("SURVEY_DATE", diaocha_data.getText().toString().trim());
		data.put("NEW_MQZK", status_spinner.getSelectedItem().toString().trim());
		data.put("NEW_DQYX", current_spinner.getSelectedItem().toString().trim());
		data.put("ID", infos.getID()+"");
		data.put("MARK", diaocha_remark.getText().toString().trim());
		params.put("data", data);
		PersonTask task=new PersonTask(PersonTask.ZHIYUANDETAILACTIVITY_SET_SHIYE_DETAIL_INFO, params);
		PersonService.newTask(task);
	}
	
	
	/**
	 * 根据值，设置下拉框的选中项
	 * 
	 * @param spinner
	 * @param value
	 */
	private void setSpinnerSelection(Spinner spinner, String value) {
		for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
			if (spinner.getAdapter().getItem(i).toString().trim()
					.equals(value.trim())) {
				spinner.setSelection(i);
				break;
			}
		}
	}
	
	
	/**
	 * 设置下拉框工具类
	 * 
	 * @param spinner
	 * @param dataid
	 */
	private void setSpinner(Spinner spinner, int dataid) {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, dataid, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

}
