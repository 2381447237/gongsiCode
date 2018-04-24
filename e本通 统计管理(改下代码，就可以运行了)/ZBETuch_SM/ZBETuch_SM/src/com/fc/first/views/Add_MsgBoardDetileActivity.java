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

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.main.tools.HttpUtil;
import com.fc.zbetuch_sm.R;

public class Add_MsgBoardDetileActivity extends Activity implements IActivity, OnClickListener {
	EditText et_msgboarddetil_titleeadd,et_msgboarddetile_docadd;
	Button btn_msgdetilesave,btn_msgdetileback;
	String masterid = "0";
	public static final int REFRESH_MSGBOARDLIST = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add__msgboarddetile);
		et_msgboarddetil_titleeadd = (EditText)findViewById(R.id.et_msgboarddetil_titleeadd);
		et_msgboarddetile_docadd = (EditText)findViewById(R.id.et_msgboarddetile_docadd);
		btn_msgdetilesave = (Button)findViewById(R.id.btn_msgdetilesave);
		btn_msgdetilesave.setOnClickListener(this);
		btn_msgdetileback = (Button)findViewById(R.id.btn_msgdetileback);
		btn_msgdetileback.setOnClickListener(this);

	}
	private class MyThread implements Runnable{

		@Override
		public void run() {
			Map<String, String> data = new HashMap<String, String>();
			data.put("TITLE", et_msgboarddetil_titleeadd.getText().toString());
			data.put("Doc", et_msgboarddetile_docadd.getText().toString());
			data.put("Master_id", masterid);
			System.out.print(data);
			try {
				String value = HttpUtil.postRequest("/Json/Set_MsgBoard_Detile.aspx", data);


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
			if(!et_msgboarddetil_titleeadd.getText().toString().equals("") && !et_msgboarddetile_docadd.getText().toString().equals("")){
				Map<String,Object> params=new HashMap<String, Object>();
				Map<String, String> data = new HashMap<String, String>();
				data.put("TITLE", et_msgboarddetil_titleeadd.getText().toString());
				data.put("Doc", et_msgboarddetile_docadd.getText().toString());
				data.put("Master_id", masterid);
				params.put("data", data);
				CompanyTask task=new CompanyTask(CompanyTask.ADD_MSG_INFO, params);
				CompanyService.newTask(task);
			}else {
				Toast.makeText(Add_MsgBoardDetileActivity.this,"标题与内容不能为空", Toast.LENGTH_SHORT).show();
			}
			finish();
			break;
		case R.id.btn_msgdetileback:
			finish();
		default:
			break;
		}
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		CompanyService.addActivity(this);
	}
	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case REFRESH_MSGBOARDLIST:
				if ("True".equals(params[1].toString().trim())) {
					Toast.makeText(Add_MsgBoardDetileActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

}
