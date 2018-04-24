package com.fc.changepass.views;

import java.util.HashMap;
import java.util.Map;

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetPwdActivity extends Activity implements IActivity {

	private EditText txtOldPass, txtNewPass, txtReNewPass;
	private Button btnOk, btnCancle;

	public static final int SET_PWD = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setpwd);
		init();
		initView();
		initListener();
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case SetPwdActivity.SET_PWD:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				if (value.equalsIgnoreCase("true")) {
					Toast.makeText(this, "密码修改成功！", Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(this, "密码修改失败", Toast.LENGTH_SHORT).show();
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

	private void initView() {
		txtOldPass = (EditText) findViewById(R.id.txtOldPass);
		txtNewPass = (EditText) findViewById(R.id.txtNewPass);
		txtReNewPass = (EditText) findViewById(R.id.txtReNewPass);
		btnOk = (Button) findViewById(R.id.btnOk);
		btnCancle = (Button) findViewById(R.id.btnCancle);
	}

	private void initListener() {
		btnOk.setOnClickListener(new MyOnClickListener());
		btnCancle.setOnClickListener(new MyOnClickListener());
	}

	/**
	 * 页面验证
	 * 
	 * @return
	 */
	private boolean checkFrm() {
		if (txtOldPass.getText().toString().trim().length() == 0) {
			Toast.makeText(this, "原密码不能为空！", Toast.LENGTH_SHORT).show();
			txtOldPass.requestFocus();
			return false;
		}

		if (txtNewPass.getText().toString().trim().length() == 0) {
			Toast.makeText(this, "新密码不能为空！", Toast.LENGTH_SHORT).show();
			txtNewPass.requestFocus();
			return false;
		}

		if (txtReNewPass.getText().toString().trim().length() == 0) {
			Toast.makeText(this, "确认新密码不能为空！", Toast.LENGTH_SHORT).show();
			txtReNewPass.requestFocus();
			return false;
		}
		if (!txtNewPass.getText().toString().trim()
				.equals(txtReNewPass.getText().toString().trim())) {
			Toast.makeText(this, "新密码和确认新密码不同！", Toast.LENGTH_SHORT).show();
			txtNewPass.setText("");
			txtReNewPass.setText("");
			txtNewPass.requestFocus();
			return false;
		}

		return true;
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnOk:
				if (checkFrm()) {
					Map<String, Object> params = new HashMap<String, Object>();
					Map<String, String> data = new HashMap<String, String>();
					data.put("pwd", txtOldPass.getText().toString().trim());
					data.put("new_pwd", txtNewPass.getText().toString().trim());
					params.put("data", data);
					CompanyTask task = new CompanyTask(
							CompanyTask.SETPWDACTIVITY_SET_PWD, params);
					CompanyService.newTask(task);
				}
				break;
			case R.id.btnCancle:
				SetPwdActivity.this.finish();
				break;
			}
		}

	}

}
