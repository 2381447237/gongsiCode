package com.fc.worktodate.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.fc.worktodate.beans.WorkToDateItem;
import com.test.zbetuch_news.R;

public class WorkToDateDetailActivity extends Activity implements IActivity {
	private WorkToDateItem item;
	private TextView lblTitle, lblDoc, lblTime;
	private Button btnShow;
	ProgressDialog progressDialog;

	public static final int REFRESH_IMG = 1;
	private String photoBoot = Environment.getExternalStorageDirectory()
			+ File.separator + "WORKPHOTOS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_worktodate_detail);
		init();
		initView();
		initListener();
		Intent intent = getIntent();
		item = (WorkToDateItem) intent.getSerializableExtra("item");
		initStrValue();

		File file = new File(photoBoot);
		if (!file.exists()) {
			file.mkdir();
		}

	}

	@Override
	public void init() {
		CompanyService.addActivity(this);

	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case WorkToDateDetailActivity.REFRESH_IMG:
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
			if (params[1] != null) {
				byte[] data = (byte[]) params[1];
				File file = new File(photoBoot, "show");
				try {
					FileOutputStream oStream = new FileOutputStream(file);
					oStream.write(data, 0, data.length);
					oStream.close();
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file), "image/*");
					startActivity(intent);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	private void initView() {
		lblTitle = (TextView) findViewById(R.id.lblTitle);
		lblDoc = (TextView) findViewById(R.id.lblDoc);
		lblTime = (TextView) findViewById(R.id.lblTime);
		btnShow = (Button) findViewById(R.id.btnShow);
	}

	private void initListener() {
		btnShow.setOnClickListener(new MyOnClickListener());
	}

	private void initStrValue() {
		if (item != null) {
			lblTitle.setText(item.getTitle());
			lblDoc.setText(item.getDoc());
			lblTime.setText(item.getCreateDate().replace("T", " "));
			

		}
	}

	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.show();
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnShow:
				// 以单独页面显示图片
				// Intent intent = new Intent(WorkToDateDetailActivity.this,
				// PhotoShowActivity.class);
				// intent.putExtra("id", item.getId());
				// WorkToDateDetailActivity.this.startActivity(intent);

				// 将图片保存在本地，使用图库显示图片
				showDialog();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", item.getId());
				CompanyTask task = new CompanyTask(
						CompanyTask.WORKTODATEDETAILACTIVITY_GET_IMG, params);
				CompanyService.newTask(task);

				break;
			}
		}

	}

}
