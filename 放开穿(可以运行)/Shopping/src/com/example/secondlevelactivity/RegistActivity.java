package com.example.secondlevelactivity;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;

import com.base.activity.BaseActivity;
import com.base.activity.ShopApplication;
import com.base.activity.VollyUtils;
import com.base.activity.VollyUtils.VolleyListener;
import com.example.httpurl.HttpUrl;
import com.example.shopping.R;
import com.example.shopping.R.id;
import com.example.shopping.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegistActivity extends BaseActivity implements OnClickListener,
		VolleyListener {

	private ImageView img_back;
	private TextView tv_code;
	private TimeCount time;
	private Button btn_finish;
	private EditText edit_phone, edit_code, edit_passwd, edit_confirm;
	private int m = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhuce);
		initView();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		tv_code = (TextView) findViewById(R.id.tv_code);
		btn_finish = (Button) findViewById(R.id.btn_finish);
		edit_phone = (EditText) findViewById(R.id.edit_phone);
		edit_code = (EditText) findViewById(R.id.edit_code);
		edit_passwd = (EditText) findViewById(R.id.edit_passwd);
		edit_confirm = (EditText) findViewById(R.id.edit_confirm);
		img_back.setOnClickListener(this);
		tv_code.setOnClickListener(this);
		btn_finish.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;

		case R.id.tv_code:
			 if (edit_phone.getText().length() != 11) {
				Toast.makeText(getApplicationContext(), "�ֻ��Ŵ���", 1000).show();
			}else{
				m = 1;
				getCode(edit_phone.getText() + "");
			}
			break;

		case R.id.btn_finish:
			if (edit_phone.getText().toString().trim().equals("")
					|| edit_code.getText().toString().trim().equals("")
					|| edit_passwd.getText().toString().trim().equals("")
					|| edit_confirm.getText().toString().trim().equals("")) {
				Toast.makeText(getApplicationContext(), "�ֻ���,��֤������벻��Ϊ��", 1000)
						.show();
			}  else if (edit_code.getText().length() != 6) {
				Toast.makeText(getApplicationContext(), "��֤��Ϊ6λ", 1000).show();
			} else if (edit_passwd.length() < 6) {
				Toast.makeText(getApplicationContext(), "��������6λ", 1000).show();
			} else if (!edit_passwd.getText().toString().trim().equals(edit_confirm.getText().toString().trim())) {
				Toast.makeText(getApplicationContext(), "�����������벻��ͬ", 1000)
						.show();
			} else {
				m = 2;
				regist();
			}
			break;
		}

	}

	private void regist() {
		try {
			ShopApplication.utils
					.init(this)
					.setListener(this)
					.getJson(
							HttpUrl.HttpURL+"/Regist.aspx?AcctNo="
									+ edit_phone.getText() + "&Code="
									+ edit_code.getText() + "&Pwd="
									+ edit_passwd.getText());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// ��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
		}

		@Override
		public void onFinish() {// ��ʱ���ʱ����
			tv_code.setText("������֤");
			tv_code.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// ��ʱ������ʾ
			tv_code.setClickable(false);
			tv_code.setText(millisUntilFinished / 1000 + "S");
		}
	}

	private void getCode(String phone) {
		try {
			ShopApplication.utils
					.init(this)
					.setListener(this)
					.getJson(
							HttpUrl.HttpURL+"/GetRegistCode.aspx?AcctNo="
									+ phone);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getJson(String response) {
		switch (m) {
		case 1:
			if(response.equals("�û����Ѵ���")){
				Toast.makeText(getApplicationContext(), "�ֻ�����ע��", 1000).show();
			}else{
				time = new TimeCount(60000, 1000);// ����CountDownTimer����
				time.start();// ��ʼ��ʱ
			}
			break;

		case 2:
			if (response.equals("�����»�ȡ��֤��!")) {
				Toast.makeText(getApplicationContext(), "��֤�����!", 1000).show();
			}else if(response.equals("��֤�����")){
				Toast.makeText(getApplicationContext(), "�ֻ�����ע��", 1000).show();
			} 
			else {
				Toast.makeText(getApplicationContext(), "ע��ɹ�!", 1000).show();
				try {
					Thread.sleep(1000);
//					Intent intent = new Intent(RegistActivity.this,
//							LoginActivity.class);
//					startActivity(intent);
					finish();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		}
	}

	@Override
	public void getFiel() {

	}

}
