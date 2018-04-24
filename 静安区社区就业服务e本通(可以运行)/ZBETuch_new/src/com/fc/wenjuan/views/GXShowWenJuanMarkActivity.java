package com.fc.wenjuan.views;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.fc.main.beans.IActivity;
import com.fc.main.service.PersonService;
import com.fc.person.beans.PersonTask;
import com.test.zbetuch_news.R;

public class GXShowWenJuanMarkActivity extends Activity implements IActivity,
		OnClickListener,OnCheckedChangeListener{
	private EditText mark_text;
	private RadioGroup radioGroup;
	private RadioButton rbtn1,rbtn2,rbtn3,rbtn4;
	private Button button_true;
	private Button button_false;
	private int position;
	private String ed_text ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_gxwenjuan_mark);
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
		radioGroup=(RadioGroup) this.findViewById(R.id.radioGroup);
		rbtn1=(RadioButton) this.findViewById(R.id.rbtn1);
		rbtn2=(RadioButton) this.findViewById(R.id.rbtn2);
		rbtn3=(RadioButton) this.findViewById(R.id.rbtn3);
		rbtn4=(RadioButton) this.findViewById(R.id.rbtn4);
		
		
		mark_text.setEnabled(false);
		button_true.setOnClickListener(this);
		button_false.setOnClickListener(this);
		radioGroup.setOnCheckedChangeListener(this);
		
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.btn_true:
			//ed_text=mark_text.getText().toString();
			
				if(rbtn1.isChecked()||rbtn2.isChecked()||rbtn3.isChecked()){
					Map<String, Object> params = new HashMap<String, Object>();
					Map<String, String> data = new HashMap<String, String>();
					data.put("Personnel_id", getIntent().getStringExtra("pid")
							.toString().trim());
					data.put("mark", ed_text);
					data.put("Phone", "");
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
				else if(rbtn4.isChecked()&&!mark_text.getText().toString().equals("")){
					Map<String, Object> params = new HashMap<String, Object>();
					Map<String, String> data = new HashMap<String, String>();
					data.put("Personnel_id", getIntent().getStringExtra("pid")
							.toString().trim());
					data.put("mark", mark_text.getText().toString().trim());
					data.put("Phone", "");
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
				else if(rbtn4.isChecked()&&mark_text.getText().toString().equals("")){
					Toast.makeText(GXShowWenJuanMarkActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
				}
				/*if(ed_text.length()==0&&ed_phone.length()==0){
					
				}*/
				
				else{
					Toast.makeText(GXShowWenJuanMarkActivity.this, "请选择合适答案", Toast.LENGTH_SHORT).show();
				
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
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.rbtn1:
			ed_text=rbtn1.getText().toString().trim();
			mark_text.setEnabled(false);
			mark_text.setText("");
			break;
		case R.id.rbtn2:
			ed_text=rbtn2.getText().toString().trim();
			mark_text.setEnabled(false);
			mark_text.setText("");
			break;
		case R.id.rbtn3:
			ed_text=rbtn3.getText().toString().trim();
			mark_text.setEnabled(false);
			mark_text.setText("");
			break;
		case R.id.rbtn4:
			mark_text.setEnabled(true);
			mark_text.setFocusable(true);
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
