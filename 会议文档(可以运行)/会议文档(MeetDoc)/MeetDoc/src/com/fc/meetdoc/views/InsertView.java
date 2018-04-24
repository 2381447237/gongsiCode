package com.fc.meetdoc.views;

import com.fc.meetdoc.R;
import com.fc.meetdoc.face.IActivity;
import com.fc.meetdoc.service.MainService;
import com.fc.meetdoc.tools.MainTools;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertView extends Activity implements OnClickListener,IActivity{
	private EditText edittext_insert;
	private Button button_insert_in,button_close_in;
	private String arr[]=null;
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		setContentView(R.layout.insertviewxml);
		init();	 
	}
	public void init(){
		MainService.addActivity(this);
		
		edittext_insert=(EditText) findViewById(R.id.editText_insert);
		button_insert_in=(Button) findViewById(R.id.button_insert_in);
		button_close_in=(Button) findViewById(R.id.button_close_in);
		button_insert_in.setOnClickListener(this);
		button_close_in.setOnClickListener(this);
		
		arr=MainTools.getAllIP(this);
		edittext_insert.setHint("请输入IP");
		
		edittext_insert.setKeyListener(new NumberKeyListener() {
			
			@Override
			public int getInputType() {
				return InputType.TYPE_CLASS_PHONE;
			}
			
			@Override
			protected char[] getAcceptedChars() {
				return new char[]{'1','2','3','4','5','6','7','8','9','0','.'};
			}
		});
	}
	/**
	 * 添加
	 */
	public void insert(String str) {
		Log.e("getrealIP", "getrealIP=" + MainTools.getRealIp());
		if (TextUtils.isEmpty(str)) {
			Toast.makeText(InsertView.this, "IP不能为空", 1).show();
		}else if(!MainTools.IPformat(str)){
//			edittext_insert.setText("");
			Toast.makeText(InsertView.this, "IP地址不正确！", 0).show();
		}
		else if (str.equals(MainTools.getRealIp())) {
//			edittext_insert.setText("");
			Toast.makeText(InsertView.this, "不能添加本机IP", 1).show();
		} else {
			if (arr != null) {
				for (int i = 0; i < arr.length; i++) {
					if (arr[i].equals(str)) {
						Toast.makeText(InsertView.this, "IP已经存在", 1).show();
//						edittext_insert.setText("");
						return;
					}
				}
				MainTools.sharemeet.putIP(MainTools.sharemeet.getIP() + ";"+ str
						);
			} else {
				MainTools.sharemeet.putIP(str);
			}
			// 输入框清空
//			edittext_insert.setText("");
			setResult(1);
			finish();
		}
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button_insert_in:
			insert(edittext_insert.getText().toString());
			break;
		case R.id.button_close_in:
			finish();
			break;
		}
	}
	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		
	}

}
