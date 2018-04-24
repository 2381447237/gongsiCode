package com.fc.person.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.main.beans.IActivity;
import com.fc.main.beans.PersonInfo;
import com.fc.main.beans.SpinnerItem;
import com.fc.main.service.PersonService;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.PersonAdapter;
import com.fc.person.beans.PersonTask;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ModifyStatusMarkActivity extends Activity implements IActivity {
	private String personSFZ;
	public Button btn_modify_mark, button_addmark, button_markok,
			button_markno;
	private ListView personListView;
	private Spinner spinner_personmark;
	private List<PersonInfo> personInfos = new ArrayList<PersonInfo>();
	private PersonAdapter adapter;

	public static final int REFRESH_INFO = 0;
	public static final int UPLOAD_INFO = 3;
	private String typename;
	private SpinnerItem personInfo;

	public static final int REFRESH_TYPE_INFO = 1;
	public static final int REFRESH_TYPE_INFO2 = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_mark);
		personSFZ = getIntent().getStringExtra("sfz");
		init();
		initView();

		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("sfz", personSFZ.trim());
		params.put("data", data);
		PersonTask task = new PersonTask(
				PersonTask.MODIFYPERSONMARK_GET_PERSONINFOS, params);
		PersonService.newTask(task);

		getTypeInfo();
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
			if (params[1].toString().trim() != null
					&& !params[1].toString().trim().equals("")) {
				List<PersonInfo> newList = parseToJson(params[1].toString()
						.trim());
				if (newList.size() > 0 && newList != null) {
					personInfos.clear();
					personInfos.addAll(newList);
				}
				adapter.notifyDataSetChanged();
				adapter = new PersonAdapter(personInfos,
						ModifyStatusMarkActivity.this);
				personListView.setAdapter(adapter);
			}
			break;

		case REFRESH_TYPE_INFO:
			if (!"".equals(params[1].toString().trim())
					&& params[1].toString().trim() != null) {
				MainTools.fetchSpinner(ModifyStatusMarkActivity.this,
						spinner_personmark, params[1].toString().trim(), "ID",
						"NAME");
			}
			break;

		case REFRESH_TYPE_INFO2:
			if (!"".equals(params[1].toString().trim())
					&& params[1].toString().trim() != null) {
				if ("True".equals(params[1].toString().trim())) {
					Toast.makeText(ModifyStatusMarkActivity.this, "操作成功!",
							Toast.LENGTH_SHORT).show();
					getTypeInfo();
				} else {
					Toast.makeText(ModifyStatusMarkActivity.this, "操作失败!",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;

		case UPLOAD_INFO:
			if (!"".equals(params[1].toString().trim())
					&& params[1].toString().trim() != null) {
				if ("True".equals(params[1].toString().trim())) {
					Toast.makeText(ModifyStatusMarkActivity.this, "上传成功",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(ModifyStatusMarkActivity.this, "上传失败",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;

		default:
			break;
		}
	}

	private void initView() {
		personListView = (ListView) this.findViewById(R.id.list_personmark);
		adapter = new PersonAdapter(personInfos, ModifyStatusMarkActivity.this);
		personListView.setAdapter(adapter);
		btn_modify_mark = (Button) this.findViewById(R.id.button_mark);
		btn_modify_mark.setOnClickListener(new MyOnClickListener());

		spinner_personmark = (Spinner) this
				.findViewById(R.id.spinner_personmark);

		button_addmark = (Button) this.findViewById(R.id.button_addmark);
		button_addmark.setOnClickListener(new MyOnClickListener());
		button_markok = (Button) this.findViewById(R.id.button_markok);
		button_markok.setOnClickListener(new MyOnClickListener());
		button_markno = (Button) this.findViewById(R.id.button_markno);
		button_markno.setOnClickListener(new MyOnClickListener());

	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.button_mark:
				Intent intent = new Intent(ModifyStatusMarkActivity.this,
						ModifyStatusPersonMarkActivity.class);
				startActivity(intent);
				break;

			case R.id.button_addmark:
				typename = spinner_personmark.getSelectedItem().toString();
				personInfo = (SpinnerItem) spinner_personmark.getSelectedItem();
				
				
				if (typename.equals("请选择")) {
					Toast.makeText(ModifyStatusMarkActivity.this, "请选择标识类型。。。",
							Toast.LENGTH_SHORT).show();
				} else {
					if (!judmentPersonInfo(personInfos, typename)) {
						PersonInfo info = new PersonInfo();
						info.setSFZ(personSFZ);
						info.setTYPE(personInfo.getId());
						info.setType_Name(typename);
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						info.setUPDATE_DATE(sdf.format(new Date()));
						personInfos.add(info);
						adapter.notifyDataSetChanged();
					}
				}
				break;

			case R.id.button_markok:
				showpersonupdateDialog("修改标识提示", "您确认修改的标识上传服务器？");
				break;

			case R.id.button_markno:
				Map<String, Object> params3 = new HashMap<String, Object>();
				Map<String, String> data3 = new HashMap<String, String>();
				data3.put("sfz", personSFZ.trim());
				params3.put("data", data3);
				PersonTask task3 = new PersonTask(
						PersonTask.MODIFYPERSONMARK_TYPE_INFO, params3);
				PersonService.newTask(task3);
				finish();
				break;

			default:
				break;
			}
		}

	}

	private List<PersonInfo> parseToJson(String str) {
		List<PersonInfo> infos = new ArrayList<PersonInfo>();
		try {
			JSONArray jsonArray = new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				PersonInfo info = new PersonInfo();
				info.setID(jsonObject.getInt("ID"));
				info.setSFZ(jsonObject.getString("SFZ"));
				info.setTYPE(jsonObject.getInt("TYPE"));
				info.setCREATE_DATE(jsonObject.getString("CREATE_DATE"));
				info.setCREATE_STAFF(jsonObject.getInt("CREATE_STAFF"));
				info.setUPDATE_DATE(jsonObject.getString("UPDATE_DATE"));
				info.setUPDATE_STAFF(jsonObject.getInt("UPDATE_STAFF"));
				info.setRecordCount(jsonObject.getInt("RecordCount"));
				info.setType_Name(jsonObject.getString("Type_Name"));
				infos.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return infos;
	}

	private void getTypeInfo() {
		Map<String, Object> params1 = new HashMap<String, Object>();
		Map<String, String> data1 = new HashMap<String, String>();
		params1.put("data", data1);
		PersonTask task1 = new PersonTask(
				PersonTask.MODIFYPERSONMARK_GET_TYPE_INFO, params1);
		PersonService.newTask(task1);
	}

	public boolean judmentPersonInfo(List<PersonInfo> personInfos,
			String typename) {
		for (int i = 0; i < personInfos.size(); i++) {
			String personMarkName = personInfos.get(i).getType_Name();
			if (personMarkName.equals(typename)) {
				Toast.makeText(ModifyStatusMarkActivity.this,
						"对不起，不能重复添加标识,请重新选择。。。", Toast.LENGTH_SHORT).show();
				return true;
			}
		}
		return false;
	}

	private void showpersonupdateDialog(String title, String msg) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(title)
				.setMessage(msg)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setCancelable(true)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String jsonInfo = parseListToJson(personInfos);
						Map<String, Object> params = new HashMap<String, Object>();
						Map<String, String> data = new HashMap<String, String>();
						data.put("json", jsonInfo);
						params.put("data", data);
						PersonTask task = new PersonTask(
								PersonTask.MODIFYPERSONMARK_INFO, params);
						PersonService.newTask(task);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = dialog.create();
		alert.show();
	}

	private String parseListToJson(List<PersonInfo> infos) {
		JSONArray jsonArray = new JSONArray();
		for (PersonInfo personInfo : infos) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("SFZ", personInfo.getSFZ());
				jsonObject.put("TYPE", personInfo.getTYPE());
				jsonObject.put("UPDATE_DATE", personInfo.getUPDATE_DATE());
				jsonArray.put(jsonObject);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return jsonArray.toString();
	}
}
