package com.fc.worktodate.views;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.test.zbetuch_news.R;

public class PhotoShowActivity extends Activity implements IActivity {

	private int id;
	ImageView imgShow;
	ProgressDialog progressDialog;

	public static final int REFRESH_IMG = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showphoto);
		init();

		imgShow = (ImageView) findViewById(R.id.imgShow);
		Intent intent = getIntent();
		id = intent.getIntExtra("id", 0);

		showDialog();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		CompanyTask task = new CompanyTask(
				CompanyTask.PHOTOSHOWACTIVITY_GET_IMG, params);
		CompanyService.newTask(task);

	}

	@Override
	public void init() {
		CompanyService.addActivity(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case PhotoShowActivity.REFRESH_IMG:
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
			if (params[1] != null) {
				byte[] imgdata = (byte[]) params[1];
				Bitmap map = BitmapFactory.decodeByteArray(imgdata, 0,
						imgdata.length);
				imgShow.setImageBitmap(map);
			}
			break;
		}
	}

	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.show();
	}

}
