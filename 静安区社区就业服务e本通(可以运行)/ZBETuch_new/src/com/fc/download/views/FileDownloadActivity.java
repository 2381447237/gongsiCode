package com.fc.download.views;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fc.company.beans.CompanyTask;
import com.fc.download.beans.DownLoadFileAdapter;
import com.fc.download.beans.DownLoadItem;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.MainTask;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.beans.SpinnerItem;
import com.fc.main.service.CompanyService;
import com.fc.main.service.MainService;
import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

public class FileDownloadActivity extends Activity implements IActivity,
		OnPullDownListener {

	private Spinner cboPolicyType;
	private EditText txtPolicyKeyWord;
	private Button btnQuery;
	private PullDownView lvPolicy;
	private ListView mListView;
	List<DownLoadItem> items;
	DownLoadFileAdapter adapter;
	ProgressDialog progressDialog;

	public static final int REFRESH_FILE = 1;
	public static final int REFRESH_CBOSTYLE = 2;
	public static final int SHOW_FILE = 3;

	private int index = 0;
	private String code = "";
	private String type = "";
	private String currentFile = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filedownload_main);
		initControl();
		initListener();
		initPulldownattr();
		init();

		MainTask task = new MainTask(
				MainTask.FILEDOWNLOADACTIVITY_GET_POLICYTYPE, null);
		MainService.newTask(task);
	}

	@Override
	protected void onStart() {
		super.onStart();
		lvPolicy.notifyDidMore();
	}

	private void initControl() {
		cboPolicyType = (Spinner) findViewById(R.id.cbopolicytype);
		txtPolicyKeyWord = (EditText) findViewById(R.id.txtpolicykeywords);
		btnQuery = (Button) findViewById(R.id.btnquery);
		lvPolicy = (PullDownView) findViewById(R.id.lvPolicy);
		mListView = lvPolicy.getListView();
		items = new ArrayList<DownLoadItem>();
		adapter = new DownLoadFileAdapter(this, items);
		mListView.setAdapter(adapter);
	}

	private void initListener() {
		lvPolicy.setOnPullDownListener(this);
		mListView.setOnItemClickListener(new MyOnItemClickListener());
		btnQuery.setOnClickListener(new MyOnClickListener());
	}

	/**
	 * 初始化pulldown属性
	 */
	private void initPulldownattr() {
		// 显示并启用自动获取更多
		lvPolicy.setShowFooter();
		// 隐藏并且禁用头部刷新
		lvPolicy.setHideHeader();
	}

	@Override
	public void init() {
		MainService.allActivity.add(this);
		CompanyService.allActivity.add(this);
		Intent intent = new Intent("com.fc.company.service.CompanyService");
		startService(intent);
	}

	@Override
	public void refresh(Object... params) {
		// if(index==0 && items!=null){
		// items.clear();
		// }
		switch (Integer.valueOf(params[0].toString())) {
		case FileDownloadActivity.REFRESH_CBOSTYLE:
			if (params[1] != null) {
				String cboStr = params[1].toString().trim();
				MainTools.fetchSpinner(this, cboPolicyType, cboStr, "ID",
						"TYPE_NAME");
			}
			break;
		case FileDownloadActivity.REFRESH_FILE:
			if (params[1] != null) {
				String fileSt = params[1].toString().trim();
				List<DownLoadItem> newItems = fretchStrToList(fileSt);

				items.addAll(newItems);
				adapter.notifyDataSetChanged();
				lvPolicy.notifyDidMore();

			}
			break;
		case FileDownloadActivity.SHOW_FILE:
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
			if (params[1] != null) {
				String success = params[1].toString().trim();
				if (success.equals("true")) {
					File file = new File(
							Environment.getExternalStorageDirectory()
									+ File.separator + "MYFILE"
									+ File.separator + currentFile);
					
					Intent intent = MainTools.openFile(file.getAbsolutePath());
					startActivity(intent);
					currentFile = "";
				} else {
					Toast.makeText(this, "文件下载失败，请重新点击", Toast.LENGTH_SHORT)
							.show();
				}
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

	@Override
	public void onMore() {
		index++;
		getFileList(index, code, type);
	}

	private class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			
			String filePath = ((DownLoadItem) adapter.getItem(position - 1))
					.getFilePath().replaceAll(" ", "%20");
			String[] str = filePath.split("/");
			
			currentFile = str[str.length - 1];
			
		//	Toast.makeText(FileDownloadActivity.this, filePath, 0).show();
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("path", filePath);
			CompanyTask task = new CompanyTask(
					CompanyTask.FILEDOWNLOADACTIVITY_DOWNLOADFILE, params);
			CompanyService.newTask(task);
			showDialog();
		}
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnquery:
				// if (checkFrm()) {
				items.clear();
				index = 0;
				code = txtPolicyKeyWord.getText().toString().trim();
				if (cboPolicyType.getSelectedItem() != null) {
					type = ((SpinnerItem) cboPolicyType.getSelectedItem())
							.getName().trim();
				}
				getFileList(index, code, type);
				// }
				break;
			}
		}

	}

	private boolean checkFrm() {
		if (((SpinnerItem) cboPolicyType.getSelectedItem()).getId() == -1) {
			Toast.makeText(this, "请选择政策类型", Toast.LENGTH_SHORT).show();
			cboPolicyType.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * 查询相关文件数据
	 * 
	 * @param index
	 * @param code
	 * @param type
	 */
	private void getFileList(int index, String code, String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("index", index);
		params.put("code", code);
		params.put("type", type);
		CompanyTask task = new CompanyTask(
				CompanyTask.FILEDOWNLOADACTIVITY_GET_DOWNLOADITEMS, params);
		CompanyService.newTask(task);
	}

	private List<DownLoadItem> fretchStrToList(String jsonStr) {
		List<DownLoadItem> items = new ArrayList<DownLoadItem>();

		try {
			JSONArray array = new JSONArray(jsonStr);
			if (array.length() > 0) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.optJSONObject(i);
					DownLoadItem item = new DownLoadItem();
					item.setId(obj.getInt("ID"));
					item.setTitle(obj.getString("TITLE"));
					item.setFilePath(obj.getString("FILE_PATH"));
					items.add(item);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
	}

	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

}
