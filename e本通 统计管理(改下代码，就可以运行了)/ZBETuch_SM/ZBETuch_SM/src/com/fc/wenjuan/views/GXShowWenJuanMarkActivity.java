package com.fc.wenjuan.views;

import java.util.HashMap;
import java.util.Map;

import com.fc.main.beans.IActivity;
import com.fc.main.myservices.PersonService;
import com.fc.person.beans.PersonTask;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GXShowWenJuanMarkActivity extends Activity implements IActivity,
		OnClickListener {
	private EditText mark_text;
	private Button button_true;
	private Button button_false;
	private int position;
	String ed_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wenjuan_mark);
		init();
		initView();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub

	}

	private void initView() {
		mark_text = (EditText) this.findViewById(R.id.et_mark);
		button_true = (Button) this.findViewById(R.id.btn_true);
		button_false = (Button) this.findViewById(R.id.btn_false);
		
		button_true.setOnClickListener(this);
		button_false.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_true:
			ed_text=mark_text.getText().toString();
			if(ed_text.length()==0){
				Toast.makeText(GXShowWenJuanMarkActivity.this, "你还没有输入内容", Toast.LENGTH_SHORT).show();
			}
			else{
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, String> data = new HashMap<String, String>();
			data.put("Personnel_id", getIntent().getStringExtra("pid")
					.toString().trim());
			data.put("mark", mark_text.getText().toString().trim());
			data.put("position", getIntent().getIntExtra("position", 0) + "");
			params.put("data", data);
			PersonTask task = new PersonTask(
					PersonTask.GXUPLOADWENJUAN_SET_WENJUAN_Mark, params);
			PersonService.newTask(task);
			GXShowWenJuanMarkActivity.this.finish();
			GXWenJuanPersonActivity._instance.finish();
			Intent showIntent = new Intent(GXShowWenJuanMarkActivity.this,
					GXWenJuanPersonActivity.class);
			startActivity(showIntent);
			
			}
			break;

		case R.id.btn_false:
			//隐藏软键盘
			 View view = getWindow().peekDecorView();
		        if (view != null) {
		            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		        }
			GXShowWenJuanMarkActivity.this.finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

}
