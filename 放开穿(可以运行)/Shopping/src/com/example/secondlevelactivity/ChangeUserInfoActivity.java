package com.example.secondlevelactivity;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;

import com.base.activity.BaseActivity;
import com.base.activity.ShopApplication;
import com.base.activity.VollyUtils.VolleyListener;
import com.example.shopping.R;
import com.example.shopping.R.id;
import com.example.shopping.R.layout;
import com.example.shopping.R.menu;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeUserInfoActivity extends BaseActivity implements
		OnClickListener, VolleyListener {

	private EditText edit_change;
	private ImageView img_back;
	private Button btn_sure;
	public static int b = 0;
	private int sex;
	private RadioGroup group;
	private String str = "";
	private View layout_edit;
	private TextView tv_birthday;
	private int year;
	private int month;
	private int day;
	private ProgressDialog dialog;
	String userID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_user_info);
		SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
		userID=preferences.getString("userID", "");
		initView();
		//initData();
	}

//	private void initData() {
//		if (UserInformationActivity.tv_sex.getText().toString().equals("��")) {
//			sex = 1;
//		} else if (UserInformationActivity.tv_sex.getText().toString()
//				.equals("Ů")) {
//			sex = 2;
//		} else {
//			sex = 0;
//		}
//		Intent intent = getIntent();
//		switch (UserInformationActivity.a) {
//		case 1:
//			tv_birthday.setVisibility(View.GONE);
//			group.setVisibility(View.GONE);
//			edit_change.setHint(intent.getStringExtra("nickname"));
//			break;
//		case 2:
//			tv_birthday.setVisibility(View.GONE);
//			layout_edit.setVisibility(View.GONE);
//			edit_change.setHint(intent.getStringExtra("nickname"));
//			break;
//		case 3:
//			layout_edit.setVisibility(View.GONE);
//			group.setVisibility(View.GONE);
//			tv_birthday.setText(intent.getStringExtra("nickname"));
//			break;
//		case 4:
//			tv_birthday.setVisibility(View.GONE);
//			group.setVisibility(View.GONE);
//			edit_change.setHint(intent.getStringExtra("nickname"));
//			break;
//		case 5:
//			tv_birthday.setVisibility(View.GONE);
//			group.setVisibility(View.GONE);
//			edit_change.setHint(intent.getStringExtra("nickname"));
//			break;
//
//		}
//	}

	private void initView() {
		edit_change = (EditText) findViewById(R.id.edit_change);
		img_back = (ImageView) findViewById(R.id.img_back4);
		btn_sure = (Button) findViewById(R.id.btn_sure);
		group = (RadioGroup) findViewById(R.id.radioGroup);
		layout_edit = findViewById(R.id.layout_edit);
		tv_birthday = (TextView) findViewById(R.id.tv_birthday);
		img_back.setOnClickListener(this);
		btn_sure.setOnClickListener(this);
		tv_birthday.setOnClickListener(this);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int radioButtonId = group.getCheckedRadioButtonId();
				// ����ID��ȡRadioButton��ʵ��
				RadioButton rb = (RadioButton) findViewById(radioButtonId);
				str = rb.getText().toString();
			}
		});
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.img_back4:
//			finish();
//			break;
//
//		case R.id.tv_birthday:
//			Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
//			Date mydate = new Date(); // ��ȡ��ǰ����Date����
//			mycalendar.setTime(mydate);// //ΪCalendar��������ʱ��Ϊ��ǰ����
//
//			year = mycalendar.get(Calendar.YEAR); // ��ȡCalendar�����е���
//			month = mycalendar.get(Calendar.MONTH);// ��ȡCalendar�����е���
//			day = mycalendar.get(Calendar.DAY_OF_MONTH);// ��ȡ����µĵڼ���
//			DatePickerDialog dpd=new DatePickerDialog(ChangeUserInfoActivity.this,Datelistener,year,month,day);
//			dpd.setCanceledOnTouchOutside(false);
//            dpd.show();
//			break;
//		case R.id.btn_sure:
//			switch (UserInformationActivity.a) {
//			case 1:
//				if (!edit_change.getText().toString().trim().equals("")) {
//					changeUsername();
//					showDialog();
//				} else {
//					Toast.makeText(getApplicationContext(), "���벻��Ϊ��", 1000)
//							.show();
//				}
//				break;
//			case 2:
//				int index = 0;
//				if (str.equals("")) {
//					Toast.makeText(ChangeUserInfoActivity.this, "δ��ѡ", 1000)
//							.show();
//				} else if (str.equals("��")) {
//					index = 1;
//					changeSex(index);
//				} else if (str.equals("Ů")) {
//					index = 2;
//					changeSex(index);
//					showDialog();
//				}
//				break;
//			case 3:
//				changeBirthday();
//				break;
//			case 4:
//				if (!edit_change.getText().toString().trim().equals("")) {
//					changeEmail();
//					showDialog();
//				} else {
//					Toast.makeText(getApplicationContext(), "���벻��Ϊ��", 1000)
//							.show();
//				}
//
//				break;
//			case 5:
//				if (!edit_change.getText().toString().trim().equals("")) {
//					changeID();
//					showDialog();
//				} else {
//					Toast.makeText(getApplicationContext(), "���벻��Ϊ��", 1000)
//							.show();
//				}
//				break;
//			}
//		}
	}
	
	private DatePickerDialog.OnDateSetListener Datelistener=new DatePickerDialog.OnDateSetListener()
    {
        /**params��view�����¼����������
         * params��myyear����ǰѡ�����
         * params��monthOfYear����ǰѡ�����
         * params��dayOfMonth����ǰѡ�����
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {
            
            
            //�޸�year��month��day�ı���ֵ���Ա��Ժ󵥻���ťʱ��DatePickerDialog����ʾ��һ���޸ĺ��ֵ
            year=myyear;
            month=monthOfYear;
            day=dayOfMonth;
            //��������
            updateDate();
            
        }
        //��DatePickerDialog�ر�ʱ������������ʾ
        private void updateDate()
        {
            //��TextView����ʾ����
            tv_birthday.setText(year+"-"+(month+1)+"-"+day);
        }
    };
    
    


//	private void changeUsername() {
//		try {
//			ShopApplication.utils
//					.init(this)
//					.setListener(this)
//					.getJson(
//							"http://www.youli.pw:85/Json/Set_UpdateUserInfo.aspx?AcctID="
//									+ userID
//									+ "&daveText="
//									+ java.net.URLEncoder.encode(edit_change
//											.getText().toString().trim(),
//											"utf-8")
//									+ "&sexText="
//									+ sex
//									+ "&phoneText="
//									+ UserInformationActivity.tv_phone
//											.getText().toString().trim()
//									+ "&Email="
//									+ java.net.URLEncoder.encode(
//											UserInformationActivity.tv_mailbox
//													.getText().toString()
//													.trim(), "utf-8")
//									+ "&IdCard="
//									+ java.net.URLEncoder.encode(
//											UserInformationActivity.tv_id
//													.getText().toString()
//													.trim(), "utf-8")
//									+ "&Birthday="
//									+ UserInformationActivity.tv_birthday
//											.getText().toString().trim());
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	private void changeSex(int index) {
//		try {
//			ShopApplication.utils
//					.init(this)
//					.setListener(this)
//					.getJson(
//							"http://www.youli.pw:85/Json/Set_UpdateUserInfo.aspx?AcctID="
//									+ userID
//									+ "&daveText="
//									+ java.net.URLEncoder.encode(
//											UserInformationActivity.tv_username
//													.getText().toString()
//													.trim(), "utf-8")
//									+ "&sexText="
//									+ index
//									+ "&phoneText="
//									+ UserInformationActivity.tv_phone
//											.getText().toString().trim()
//									+ "&Email="
//									+ java.net.URLEncoder.encode(
//											UserInformationActivity.tv_mailbox
//													.getText().toString()
//													.trim(), "utf-8")
//									+ "&IdCard="
//									+ java.net.URLEncoder.encode(
//											UserInformationActivity.tv_id
//													.getText().toString()
//													.trim(), "utf-8")
//									+ "&Birthday="
//									+ UserInformationActivity.tv_birthday
//											.getText());
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	private void changeBirthday() {
//		try {
//			ShopApplication.utils
//			.init(this)
//			.setListener(this)
//			.getJson(
//					"http://www.youli.pw:85/Json/Set_UpdateUserInfo.aspx?AcctID="
//							+ userID
//							+ "&daveText="
//							+ java.net.URLEncoder.encode(
//									UserInformationActivity.tv_username
//									.getText().toString()
//									.trim(), "utf-8")
//									+ "&sexText="
//									+ sex
//									+ "&phoneText="
//									+ UserInformationActivity.tv_phone
//									.getText().toString().trim()
//									+ "&Email="
//									+ java.net.URLEncoder.encode(
//											UserInformationActivity.tv_mailbox
//											.getText().toString()
//											.trim(), "utf-8")
//											+ "&IdCard="
//											+ java.net.URLEncoder.encode(
//													UserInformationActivity.tv_id
//													.getText().toString()
//													.trim(), "utf-8")
//													+ "&Birthday="
//													+ tv_birthday.getText());
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	private void changeEmail() {
//		try {
//			ShopApplication.utils
//					.init(this)
//					.setListener(this)
//					.getJson(
//							"http://www.youli.pw:85/Json/Set_UpdateUserInfo.aspx?AcctID="
//									+ userID
//									+ "&daveText="
//									+ java.net.URLEncoder.encode(
//											UserInformationActivity.tv_username
//													.getText().toString()
//													.trim(), "utf-8")
//									+ "&sexText="
//									+ sex
//									+ "&phoneText="
//									+ UserInformationActivity.tv_phone
//											.getText().toString().trim()
//									+ "&Email="
//									+ java.net.URLEncoder.encode(edit_change
//											.getText().toString().trim(),
//											"utf-8")
//									+ "&IdCard="
//									+ java.net.URLEncoder.encode(
//											UserInformationActivity.tv_id
//													.getText().toString()
//													.trim(), "utf-8")
//									+ "&Birthday="
//									+ UserInformationActivity.tv_birthday
//											.getText().toString().trim());
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	private void changeID() {
//		try {
//			ShopApplication.utils
//					.init(this)
//					.setListener(this)
//					.getJson(
//							"http://www.youli.pw:85/Json/Set_UpdateUserInfo.aspx?AcctID="
//									+ userID
//									+ "&daveText="
//									+ java.net.URLEncoder.encode(
//											UserInformationActivity.tv_username
//													.getText().toString()
//													.trim(), "utf-8")
//									+ "&sexText="
//									+ sex
//									+ "&phoneText="
//									+ UserInformationActivity.tv_phone
//											.getText().toString().trim()
//									+ "&Email="
//									+ java.net.URLEncoder.encode(
//											UserInformationActivity.tv_mailbox
//													.getText().toString()
//													.trim(), "utf-8")
//									+ "&IdCard="
//									+ java.net.URLEncoder.encode(edit_change
//											.getText().toString().trim(),
//											"utf-8")
//									+ "&Birthday="
//									+ UserInformationActivity.tv_birthday
//											.getText().toString().trim());
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Override
	public void getJson(String response) {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		if (response.equals("true")) {
			UserInformationActivity.a = 3;
			Intent mIntent = new Intent("cctv");
			// ���͹㲥
			sendBroadcast(mIntent);
			finish();

		} else {
			Toast.makeText(this, "�޸�ʧ�ܣ���������", 1000).show();
		}
	}

	@Override
	public void getFiel() {
		// TODO Auto-generated method stub

	}
	
	private void showDialog() {
		dialog = new ProgressDialog(this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("�޸���...");
		dialog.show();

	}

}
