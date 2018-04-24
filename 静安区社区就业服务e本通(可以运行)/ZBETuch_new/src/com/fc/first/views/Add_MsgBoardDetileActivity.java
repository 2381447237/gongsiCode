package com.fc.first.views;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fc.main.tools.HttpUtil;
import com.test.zbetuch_news.R;

public class Add_MsgBoardDetileActivity extends Activity implements
		OnClickListener {
	EditText et_msgboarddetil_titleeadd, et_msgboarddetile_docadd;
	Button btn_msgdetilesave, btn_msgdetileback;
	String masterid = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add__msgboarddetile);
		et_msgboarddetil_titleeadd = (EditText) findViewById(R.id.et_msgboarddetil_titleeadd);
		et_msgboarddetile_docadd = (EditText) findViewById(R.id.et_msgboarddetile_docadd);
		btn_msgdetilesave = (Button) findViewById(R.id.btn_msgdetilesave);
		btn_msgdetilesave.setOnClickListener(this);
		btn_msgdetileback = (Button) findViewById(R.id.btn_msgdetileback);
		btn_msgdetileback.setOnClickListener(this);

	}

	private class MyThread implements Runnable {

		@Override
		public void run() {
			Map<String, String> data = new HashMap<String, String>();
			data.put("TITLE", et_msgboarddetil_titleeadd.getText().toString());
			data.put("Doc", et_msgboarddetile_docadd.getText().toString());
			data.put("Master_id", masterid);
			System.out.print(data);
			try {
				String value = HttpUtil.postRequest(
						"/Json/Set_MsgBoard_Detile.aspx", data);

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_msgdetilesave:
			try {
				Thread.sleep(1);
				if (!et_msgboarddetil_titleeadd.getText().toString().equals("")
						&& !et_msgboarddetile_docadd.getText().toString()
								.equals("")) {
					new Thread(new MyThread()).start();
				} else {
					Toast.makeText(Add_MsgBoardDetileActivity.this,
							"标题与内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.finish();
			break;
		case R.id.btn_msgdetileback:
			finish();
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}

}
