package com.fc.ziyuan.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fc.main.beans.CheckBoxAdapter;
import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.test.zbetuch_news.R;

public class AllXuqiuActivity extends Activity implements IActivity {
	Button btnOk, btnCancle;
	TextView jobtx;
	EditText qitaet;
	GridView jobgv;
	Button btn_ok, btn_cancle;
	String[] all;
	CheckBox cball;
	CheckBoxAdapter adapter;
	List<CheckBox> boxs;
	Intent intent;
	List<String> name;
	LinearLayout qitalay;
	LinearLayout joblayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qihang_item);
		init();
		initView();
		intent = getIntent();
		initListener();
	}

	private void initListener() {
		btnOk.setOnClickListener(btnclick);
		btnCancle.setOnClickListener(btnclick);
		// cball.setOnCheckedChangeListener(cbachecked);
		// jobgv.setOnItemClickListener(jobgvclick);
		for (CheckBox cb : boxs) {
			if (cb.getText().toString().equals("其他")) {
				cb.setOnCheckedChangeListener(qitaclick);
			}

		}
	}

	private OnCheckedChangeListener qitaclick = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				qitalay.setVisibility(View.VISIBLE);
			} else {
				qitalay.setVisibility(View.GONE);
			}

		}
	};

	// private OnItemClickListener jobgvclick=new OnItemClickListener() {
	//
	// @Override
	// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	// long arg3) {
	// cball=(CheckBox) arg1;
	// if (cball.isChecked()&&cball.getText().toString().equals("其他")) {
	// qitalay.setVisibility(View.VISIBLE);
	// }
	//
	// }
	// };

	/**
	 * 选择 (最多三个)
	 */
	private OnClickListener btnclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnok:
				int j = 0;
				name = new ArrayList<String>();
				if (boxs != null && boxs.size() > 0) {
					for (int i = 0; i < boxs.size(); i++) {
						if (boxs.get(i).isChecked()) {
							j++;
						}
					}
				}
				if (j > 3) {
					Toast.makeText(AllXuqiuActivity.this, "最多选3项", 1).show();
				} else {
					if (boxs != null && boxs.size() > 0) {
						for (int i = 0; i < boxs.size(); i++) {
							if (boxs.get(i).isChecked()) {
								if (boxs.get(i).getText().toString()
										.equals("其他")) {
									name.add("其他 "
											+ qitaet.getText().toString());
								} else {
									name.add(boxs.get(i).getText().toString());
								}
							}
						}
					}
					intent.putExtra("xuqiu", (Serializable) name);
					setResult(100, intent);
					AllXuqiuActivity.this.finish();
				}
				break;
			case R.id.btncancle:
				AllXuqiuActivity.this.finish();
				break;

			default:
				break;
			}

		}
	};

	private void initView() {
		btnOk = (Button) findViewById(R.id.btnok);
		btnCancle = (Button) findViewById(R.id.btncancle);
		jobtx = (TextView) findViewById(R.id.jobtx);
		jobgv = (GridView) findViewById(R.id.jobgv);
		jobtx.setText("就业服务需求（最多选3项）");
		btn_ok = (Button) findViewById(R.id.btn_Ok);
		btn_cancle = (Button) findViewById(R.id.btn_Cancle);
		all = getResources().getStringArray(R.array.xuqiu);
		boxs = new ArrayList<CheckBox>();
		qitalay = (LinearLayout) findViewById(R.id.qitalay);
		qitaet = (EditText) findViewById(R.id.qitaet);
		joblayout = (LinearLayout) findViewById(R.id.joblayout);
		// cball=(CheckBox) findViewById(R.id.cbxPeople);
		for (String name : all) {
			CheckBox box = (CheckBox) getLayoutInflater().from(
					AllXuqiuActivity.this).inflate(
					R.layout.activity_getperson_checkbox, null);
			box.setText(name);
			boxs.add(box);
		}
		adapter = new CheckBoxAdapter(this, boxs);
		jobgv.setAdapter(adapter);
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {

	}

}
