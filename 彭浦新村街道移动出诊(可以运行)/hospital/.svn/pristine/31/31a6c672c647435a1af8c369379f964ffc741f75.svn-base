package com.fc.unidentified.views;

import com.example.hospital.R;
import com.example.hospital.R.id;
import com.example.hospital.R.layout;
import com.example.hospital.R.menu;
import com.fc.helper.BaseActivity;
import com.fc.unidentified.beans.UnideInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UnidentifiedActivity2 extends BaseActivity implements
		OnClickListener {

	private TextView card, name, sex, age, address, BirthDate, RCName,
			AppointVisitDate, Contactor, ContactPhone, RegRemark;
	private Button un_btn;
	private UnideInfo info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置屏幕无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置屏幕全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_unidentified_activity2);
		Intent intent = getIntent();
		info = (UnideInfo) intent.getSerializableExtra("unitinfo");
		initView();
	}

	public void initView() {
		card = (TextView) findViewById(R.id.tv_CardNo);
		name = (TextView) findViewById(R.id.tv_PatientName);
		sex = (TextView) findViewById(R.id.tv_Gender);
		age = (TextView) findViewById(R.id.tv_Age);
		address = (TextView) findViewById(R.id.tv_VisitAddr);
		BirthDate = (TextView) findViewById(R.id.tv_BirthDate);
		RCName = (TextView) findViewById(R.id.tv_RCName);
		AppointVisitDate = (TextView) findViewById(R.id.tv_AppointVisitDate);
		Contactor = (TextView) findViewById(R.id.tv_Contactor);
		ContactPhone = (TextView) findViewById(R.id.tv_ContactPhone);
		RegRemark = (TextView) findViewById(R.id.tv_RegRemark);
		un_btn = (Button) findViewById(R.id.btn_un);

		RegRemark.setMovementMethod(ScrollingMovementMethod.getInstance());

		un_btn.setOnClickListener(this);

		card.setText(info.getCard());
		name.setText(info.getName());
		sex.setText(info.getSex());
		age.setText(info.getAge());
		address.setText(info.getAddress());
		BirthDate.setText(info.getBirthDate());
		RCName.setText(info.getRCName());
		AppointVisitDate.setText(info.getAppointVisitDate());
		Contactor.setText(info.getContactor());
		ContactPhone.setText(info.getContactPhone());
		RegRemark.setText(info.getRegRemark());

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_un:
			Intent intent = new Intent(UnidentifiedActivity2.this,
					DiagnosisActivity.class);
			intent.putExtra("mRegId", info.getRegId());
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}

}
