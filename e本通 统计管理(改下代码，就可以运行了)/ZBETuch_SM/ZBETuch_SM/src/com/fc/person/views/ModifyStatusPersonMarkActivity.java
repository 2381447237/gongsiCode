package com.fc.person.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fc.main.beans.IActivity;
import com.fc.main.myservices.PersonService;
import com.fc.person.beans.PersonTask;
import com.fc.person.beans.StatusAdapter;
import com.fc.person.beans.TypeInfo;
import com.fc.zbetuch_sm.R;

public class ModifyStatusPersonMarkActivity extends Activity implements IActivity{

	private ListView statusListView;
	private List<TypeInfo> infos=new ArrayList<TypeInfo>();
	private StatusAdapter adapter;
	private EditText my_type_name;
	private TypeInfo typeInfo;
	private Button button_add_markok,button_update_markok,button_markno,button_delete_markok;
	public static final int REFRESH_INFO=0;

	public static final int REFRESH_INFO_NAME=1;
	
	private Map<String, String> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_status);
		init();
		initView();


		Map<String, Object> params=new HashMap<String, Object>();
		Map<String, String> data=new HashMap<String, String>();
		params.put("data", data);
		PersonTask task=new PersonTask(PersonTask.MODIFYPERSONMARK_GET_INFOS, params);
		PersonService.newTask(task);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case REFRESH_INFO:
			if (!"".equals(params[1].toString().trim())&&params[1].toString().trim()!=null) {
				List<TypeInfo> newList=parseToJson(params[1].toString().trim());
				if (newList.size()>0&&newList!=null) {
					infos.clear();
					infos.addAll(newList);
				}
				adapter.notifyDataSetChanged();
				adapter=new StatusAdapter(infos, ModifyStatusPersonMarkActivity.this);
				statusListView.setAdapter(adapter);
			}
			break;

		case REFRESH_INFO_NAME:
			if (!"".equals(params[1].toString().trim())&&params[1].toString().trim()!=null) {
				my_type_name.setText("");
				typeInfo=(TypeInfo) params[1];
				my_type_name.setText(typeInfo.getNAME());
			}
			break;

		default:
			break;
		}
	}

	private void initView(){
		statusListView=(ListView) this.findViewById(R.id.list_personmark);
		adapter=new StatusAdapter(infos, ModifyStatusPersonMarkActivity.this);
		statusListView.setAdapter(adapter);

		statusListView.setOnItemClickListener(new MyOnItemClickListener());

		my_type_name=(EditText) this.findViewById(R.id.my_type_name);

		button_add_markok=(Button) this.findViewById(R.id.button_add_markok);
		button_update_markok=(Button) this.findViewById(R.id.button_update_markok);
		button_markno=(Button) this.findViewById(R.id.button_markno);
		button_delete_markok=(Button) this.findViewById(R.id.button_delete_markok);

		button_add_markok.setOnClickListener(new MyOnClickListener());
		button_update_markok.setOnClickListener(new MyOnClickListener());
		button_markno.setOnClickListener(new MyOnClickListener());
		button_delete_markok.setOnClickListener(new MyOnClickListener());
	}

	private List<TypeInfo> parseToJson(String string){
		List<TypeInfo> infos=new ArrayList<TypeInfo>();
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(string);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject=(JSONObject) jsonArray.get(i);
				TypeInfo info=new TypeInfo();
				info.setID(jsonObject.getInt("ID"));
				info.setNAME(jsonObject.getString("NAME"));
				info.setCREATE_DATE(jsonObject.getString("CREATE_DATE"));
				info.setCREATE_STAFF(jsonObject.getInt("CREATE_STAFF"));
				info.setRecordCount(jsonObject.getInt("RecordCount"));
				infos.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return infos;

	}

	private class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("data", infos.get(position));
			PersonTask task=new PersonTask(PersonTask.MODIFYPERSONMARK_TYPE_NAME, params);
			PersonService.newTask(task);
		}

	}

	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.button_add_markok:
				if (checkFirm()) {
					data=new HashMap<String, String>();
					data.put("name", my_type_name.getText().toString().trim());
					showDialog("确定要添加吗？");
				}
				break;
				
			case R.id.button_update_markok:
				if (checkFirm()) {
					data=new HashMap<String, String>();
					data.put("id", typeInfo.getID()+"");
					data.put("name", my_type_name.getText().toString().trim());
					showDialog("确定要修改吗？");
				}
				break;
				
			case R.id.button_delete_markok:
				if (checkFirm()) {
					data=new HashMap<String, String>();
					data.put("id", typeInfo.getID()+"");
					data.put("del", "true");
					showDialog("确定要删除吗？");
				}
				break;
				
			case R.id.button_markno:
                finish();
				break;

			default:
				break;
			}
		}

	}
	
	private void showDialog(String mesString){
		Builder builder=new AlertDialog.Builder(ModifyStatusPersonMarkActivity.this);
		builder.setTitle("温馨提示").setMessage(mesString).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("data", data);
				PersonTask task=new PersonTask(PersonTask.MODIFYPERSONMARK_TYPE_DETAIL, params);
				PersonService.newTask(task);
				ModifyStatusPersonMarkActivity.this.finish();
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		}).create().show();
	}
	
	private boolean checkFirm(){
		if (my_type_name.getText().toString().trim()==null||"".equals(my_type_name.getText().toString().trim())) {
			Toast.makeText(ModifyStatusPersonMarkActivity.this, "名称不能为空!", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
