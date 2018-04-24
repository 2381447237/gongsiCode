package com.fc.policy.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.MainTask;
import com.fc.main.beans.SpinnerItem;
import com.fc.main.myservices.CompanyService;
import com.fc.main.myservices.MainService;
import com.fc.main.tools.MainTools;
import com.fc.policy.beans.PolicyAdapter;
import com.fc.zbetuch_sm.R;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainPolicyActivity extends Activity implements IActivity {
	
	private Spinner cboPolicyStyle;
	private EditText txtPolicyKeywords;
	private Button btnQuery,btnAsk;
	private ExpandableListView lvPolicy;
	
	public static final int REFRESH_CBOTYPE=1;
	public static final int REFRESH_LVPOLICY=2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_policy_main);
		initControl();		
		init();
		initListener();
		
		MainTask task = new MainTask(MainTask.MAINPOLICYACTIVITY_GET_POLICYTYPE, null);
		MainService.newTask(task);
		
	}
	
	private void initControl(){
		cboPolicyStyle = (Spinner) findViewById(R.id.cbopolicytype);
		txtPolicyKeywords = (EditText) findViewById(R.id.txtpolicykeywords);
		btnQuery = (Button) findViewById(R.id.btnquery);
		btnAsk = (Button) findViewById(R.id.btnask);
		lvPolicy = (ExpandableListView) findViewById(R.id.lvPolicy);		
	}
	
	private void initListener(){
		btnQuery.setOnClickListener(new MyOnclickListener());
		btnAsk.setOnClickListener(new MyOnclickListener());
	}
	
	
	@Override
	public void init() {
		MainService.addActivity(this);
		CompanyService.addActivity(this);
		Intent intent = new Intent("com.fc.company.service.CompanyService");
		startService(intent);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString().trim())) {
		case MainPolicyActivity.REFRESH_CBOTYPE:
			if(params[1]!=null){
				String cboTypeStr =params[1].toString();
				MainTools.fetchSpinner(this, cboPolicyStyle, cboTypeStr, "ID", "TYPE_NAME");
			}			
			break;
		case MainPolicyActivity.REFRESH_LVPOLICY:
			if(params[1]!=null){
				String policyStr = params[1].toString();
				fretchLv(policyStr);
			}			
			break;
			
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MainService.allActivity.remove(this);
		CompanyService.allActivity.remove(this);
	}
	
	private class MyOnclickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btnquery: 
				//if(CheckFrm()){
					Map<String, Object> params = new HashMap<String, Object>();
					String type="";
					if(!((SpinnerItem)(cboPolicyStyle.getSelectedItem())).getName().trim().equals("请选择")){
						type = ((SpinnerItem)(cboPolicyStyle.getSelectedItem())).getName().trim();
					}
					params.put("type",type);
					params.put("keyWord", txtPolicyKeywords.getText().toString().trim());
					CompanyTask task = new CompanyTask(CompanyTask.MAINPOLICYACTIVITY_GET_POLICY, params);
					CompanyService.newTask(task);
				//}
				break;
			case R.id.btnask:
				Intent intent = new Intent(MainPolicyActivity.this,PolicyAskActivity.class);
				startActivity(intent);
				break;
			}
			
		}
		
	}
	
	private boolean CheckFrm(){
		if(((SpinnerItem)cboPolicyStyle.getSelectedItem()).getId()==-1){
			Toast.makeText(this, "请选择政策类型", Toast.LENGTH_SHORT).show();
			cboPolicyStyle.requestFocus();
			return false;
		}	
		return true;
		
	}
	
	private void fretchLv(String policyStr){
		List<Map<String, String>> questions = new ArrayList<Map<String,String>>();
		List<List<Map<String, String>>> answers = new ArrayList<List<Map<String,String>>>();
		
		try {
			JSONArray jsonArray = new JSONArray(policyStr);
			if(jsonArray.length()>0){
				for(int i=0;i<jsonArray.length();i++){
					JSONObject obj = jsonArray.optJSONObject(i);
					String question = obj.getString("QUESTIONS");
					String answer = obj.getString("ANSWERS");
					Map<String,String> questionMap = new HashMap<String, String>();
					questionMap.put("question", question);
					questions.add(questionMap);
					
					List<Map<String, String>> answerList = new ArrayList<Map<String,String>>();
					Map<String, String> answerMap = new HashMap<String, String>();
					answerMap.put("answer", answer);					
					answerList.add(answerMap);
					answers.add(answerList);
				}
			}			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
//		SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
//				this, questions, R.layout.activity_policy_main_groupitem,
//				new String[]{"question"}, new int[]{R.id.txtGroupItem},
//				answers, R.layout.activity_policy_main_subitem, new String[]{"answer"}, new int[]{R.id.txtSubItem});
		PolicyAdapter adapter = new PolicyAdapter(questions, answers, this);
		
		lvPolicy.setAdapter(adapter);
		
	}
	

}