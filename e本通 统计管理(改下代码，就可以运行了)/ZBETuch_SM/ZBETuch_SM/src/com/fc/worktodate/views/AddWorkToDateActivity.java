package com.fc.worktodate.views;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.zbetuch_sm.R;

public class AddWorkToDateActivity extends Activity implements IActivity {
	EditText txtTitle, txtContent;
	ImageView imgPhoto;
	Button btnPhoto, btnSelect, btnOk, btnCancle;

	private static final int CAMERA_PHOTO = 1;
	private static final int SELECT_PHOTO = 2;
	public static final int UPDATE_WORKTODATE=1;

	private String photoBoot = Environment.getExternalStorageDirectory()
			+ File.separator + "WORKPHOTOS";
	private String photoName = "work.jpg";

	private String photoPath = "";
	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_worktodata);
		init();
		initView();
		initListener();

		File boot = new File(photoBoot);
		if (!boot.exists()) {
			boot.mkdir();
		}
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch(Integer.valueOf(params[0].toString().trim())){
		case AddWorkToDateActivity.UPDATE_WORKTODATE:
				btnOk.setEnabled(true);
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
					progressDialog = null;
				}
				if(params[1]!=null){
					String value = params[1].toString().trim();
					if(value.equalsIgnoreCase("true")){
						Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
						
						WorkToDateListActivity.index=0;
						Map<String, Object> aparams = new HashMap<String, Object>();
						Map<String, String> data = new HashMap<String, String>();
						data.put("page", "0" );
						data.put("rows", "15");
						aparams.put("data", data);
						CompanyTask task = new CompanyTask(
								CompanyTask.WORKTODATELISTACTIVITY_GET_WORKDATELIST, aparams);
						CompanyService.newTask(task);
						
						finish();
					}else {
						Toast.makeText(this, "上传失败", Toast.LENGTH_SHORT).show();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == CAMERA_PHOTO) {
				photoPath = photoBoot + File.separator + photoName;
				Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
				imgPhoto.setImageBitmap(bitmap);
			} else if (requestCode == SELECT_PHOTO) {
				Uri uri = data.getData();
				// ContentResolver cr = this.getContentResolver();

				try {
					// InputStream inStream = cr.openInputStream(uri);
					// Bitmap bitmap = BitmapFactory.decodeStream(inStream);

					String[] proj = { MediaStore.Images.Media.DATA };
					Cursor actualimagecursor = managedQuery(uri, proj, null,
							null, null);
					int actual_image_column_index = actualimagecursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					actualimagecursor.moveToFirst();
					photoPath = actualimagecursor
							.getString(actual_image_column_index);
					Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
					imgPhoto.setImageBitmap(bitmap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void initView() {
		txtTitle = (EditText) findViewById(R.id.txtTitle);
		txtContent = (EditText) findViewById(R.id.txtContent);
		imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
		btnPhoto = (Button) findViewById(R.id.btnPhoto);
		btnSelect = (Button) findViewById(R.id.btnSelect);
		btnOk = (Button) findViewById(R.id.btnOk);
		btnCancle = (Button) findViewById(R.id.btnCancle);
	}

	private void initListener() {
		btnPhoto.setOnClickListener(new MyOnClickListener());
		btnSelect.setOnClickListener(new MyOnClickListener());
		btnOk.setOnClickListener(new MyOnClickListener());
		btnCancle.setOnClickListener(new MyOnClickListener());
	}
	
	private boolean checkFrm(){
		if(txtTitle.getText().toString().trim().equals("")){
			Toast.makeText(this, "标题不能为空！", Toast.LENGTH_SHORT).show();
			txtTitle.requestFocus();
			return false;
		}
		
		if(txtContent.getText().toString().trim().equals("")){
			Toast.makeText(this, "内容不能为空！", Toast.LENGTH_SHORT).show();
			txtContent.requestFocus();
			return false;
		}
		
		return true;
	}
	
	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("正在上传中中，请稍后。。。");
		progressDialog.show();
	}
	
	

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnPhoto:
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(photoBoot, photoName)));
				startActivityForResult(intent, CAMERA_PHOTO);
				break;
			case R.id.btnSelect:
				Intent intent2 = new Intent();
				/* 开启Pictures画面Type设定为image */
				intent2.setType("image/*");
				/* 使用Intent.ACTION_GET_CONTENT这个Action */
				intent2.setAction(Intent.ACTION_GET_CONTENT);
				/* 取得相片后返回本画面 */
				startActivityForResult(intent2, SELECT_PHOTO);
				break;
			case R.id.btnOk:
				if(checkFrm()){
					showDialog();
					Map<String, Object> params = new HashMap<String, Object>();
					Map<String, String> data = new HashMap<String, String>();
					data.put("TITLE", txtTitle.getText().toString().trim());
					data.put("DOC", txtContent.getText().toString().trim());
					params.put("data", data);
					params.put("filePath", photoPath);
					CompanyTask task = new CompanyTask(CompanyTask.WORKTODATAACTIVITY_SET_WORKDATE, params);
					CompanyService.newTask(task);
					btnOk.setEnabled(false);
				}
				
				break;
			case R.id.btnCancle:
					AddWorkToDateActivity.this.finish();
				break;
			}
		}

	}

}
