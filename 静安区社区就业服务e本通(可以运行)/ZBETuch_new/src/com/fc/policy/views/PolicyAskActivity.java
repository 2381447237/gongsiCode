package com.fc.policy.views;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fc.company.beans.CompanyTask;
import com.fc.first.views.FirstPageActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.MainTask;
import com.fc.main.beans.SpinnerItem;
import com.fc.main.service.CompanyService;
import com.fc.main.service.MainService;
import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

public class PolicyAskActivity extends Activity implements IActivity {

	private Spinner cboType;
	private TextView txtQuestionName, txtQuestionValue;
	private Button btnAsk;

	public static final int REFRESH_CBOSTYLE = 1;
	public static final int REFRESH_SUCESS = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_policy_ask);
		initControl();
		init();
		initListener();
		MainTask task = new MainTask(MainTask.POLICYASKACTIVITY_GET_POLICYTYPE,
				null);
		MainService.newTask(task);

	}

	private void initControl() {
		cboType = (Spinner) findViewById(R.id.cboType);
		txtQuestionName = (TextView) findViewById(R.id.txtQuestionName);
		txtQuestionValue = (TextView) findViewById(R.id.txtQuestionValue);
		btnAsk = (Button) findViewById(R.id.btnAsk);
	}

	private void initListener() {
		btnAsk.setOnClickListener(new MyOnClickListener());
	}

	@Override
	public void init() {
		MainService.addActivity(this);
		CompanyService.addActivity(this);

	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case PolicyAskActivity.REFRESH_CBOSTYLE:
			if (params[1] != null) {
				String cboStr = params[1].toString().trim();
				MainTools
						.fetchSpinner(this, cboType, cboStr, "ID", "TYPE_NAME");
			}

			break;
		case PolicyAskActivity.REFRESH_SUCESS:
			if (params[1] != null) {
				String answerStr = params[1].toString().trim();
				System.out.println("answerStr===》" + answerStr);
				if (answerStr.equals("True")) {
					Toast.makeText(this, "提交成功！", Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(this, "提交失败！", Toast.LENGTH_LONG).show();
				}
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MainService.allActivity.remove(this);
		CompanyService.allActivity.remove(this);
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnAsk:
				if (checkFrm()) {
					Map<String, String> data = new HashMap<String, String>();
					String gpsInfo = FirstPageActivity.GetGpsInfo();
					data.put(
							"typeid",
							""
									+ ((SpinnerItem) cboType.getSelectedItem())
											.getId());
					data.put("questionname", txtQuestionName.getText()
							.toString().trim());
					data.put("questionvalue", txtQuestionValue.getText()
							.toString().trim());
					data.put("gps", gpsInfo);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("data", data);
					CompanyTask task = new CompanyTask(
							CompanyTask.POLICYASKACTIVITY_SET_ASK, params);
					CompanyService.newTask(task);
				}
				break;
			}

		}
	}

	private boolean checkFrm() {
		SpinnerItem item = (SpinnerItem) cboType.getSelectedItem();
		if (item.getId() == -1) {
			Toast.makeText(this, "必须选择类型", Toast.LENGTH_SHORT).show();
			cboType.requestFocus();
			return false;
		}
		if (txtQuestionName.getText().toString().trim().equals("")) {
			Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
			txtQuestionName.requestFocus();
			return false;
		}
		if (txtQuestionValue.getText().toString().trim().equals("")) {
			Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
			txtQuestionValue.requestFocus();
			return false;
		}
		return true;
	}

}
