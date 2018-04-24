package com.example.secondlevelactivity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;

import com.base.activity.GetSdcardInfomation;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.UserInfo;
import com.example.shopping.R;
import com.example.thirdlevelactivity.IdentityPhotoActivity;
import com.google.gson.Gson;
import com.loveplusplus.demo.image.ImagePagerActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class IdentityInfoActivity extends Activity implements OnClickListener{
	
	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	private static final String PHOTO_NAME = "identity.jpg";
	private ImageView img_back,iv_front,iv_back;
	private Button btn_upload,btn_front,btn_back;
	//http://web.youli.pw:85/Json/Get_UserInfoForUserId.aspx?AcctID=80
	
	private String userInfoUrl = HttpUrl.HttpURL+"/Json/Get_UserInfoForUserId.aspx";//用户信息
	
	private String userID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_identity);
		
		img_back=(ImageView) findViewById(R.id.img_back_id);
		img_back.setOnClickListener(this);
		iv_front=(ImageView) findViewById(R.id.iv_front_id);
		iv_front.setOnClickListener(this);
		iv_back=(ImageView) findViewById(R.id.iv_back_id);
		iv_back.setOnClickListener(this);
		btn_upload=(Button) findViewById(R.id.btn_upload_id);
		btn_upload.setOnClickListener(this);
		btn_front=(Button) findViewById(R.id.btn_shotf_id);
		btn_front.setOnClickListener(this);
		btn_back=(Button) findViewById(R.id.btn_shotb_id);
		btn_back.setOnClickListener(this);
		
		setDefaultIv();
	}

	private void setDefaultIv(){
		
		SharedPreferences preferences = getSharedPreferences("user",
				Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");
		
		OkHttpUtils.post().url(userInfoUrl).addParams("AcctID",userID).build().execute(new StringCallback() {
			
			@Override
			public void onResponse(final String str) {
				
				runOnUiThread(new  Runnable() {
					public void run() {
						
						Gson gson=new Gson();
						
						UserInfo ui=gson.fromJson(str,UserInfo.class);
						
						if(TextUtils.isEmpty(ui.FrontImage)){
							iv_front.setImageResource(R.drawable.id1);
						}else{
							//读取网络图片
						}
						
						if(TextUtils.isEmpty(ui.BackImage)){
							iv_back.setImageResource(R.drawable.id2);
						}else{
							//读取网络图片
						}
					}
				});
				
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		
		Intent intent=new Intent(IdentityInfoActivity.this,IdentityPhotoActivity.class);
		
		switch (v.getId()) {
		case R.id.img_back_id:
			//返回上个界面
			
			finish();
			
			break;
		case R.id.iv_front_id:
			//身份证的正面
			
			intent.putExtra("dPhoto","lfront");
			startActivity(intent);
			break;
		case R.id.iv_back_id:
			//身份证的背面
			
			intent.putExtra("dPhoto","lback");
			startActivity(intent);
			break;
		case R.id.btn_upload_id:
			//上传图片
			
			//Toast.makeText(this,"确认上传", 0).show();
			showDialogUpload();
			break;
		case R.id.btn_shotf_id:
		//	Toast.makeText(this,"拍摄正面", 0).show();
			showDialogChoose();
			break;
		case R.id.btn_shotb_id:
		//	Toast.makeText(this,"拍摄背面", 0).show();
			showDialogChoose();
			break;
		default:
			break;
		}
		
	}
	
	private void showDialogUpload(){
		LinearLayout ensure;
		final AlertDialog dialog=new AlertDialog.Builder(this).create();
		View view=LayoutInflater.from(this).inflate(R.layout.dialog_upload,null);
		dialog.setView(view);
		dialog.show();
		Window window = dialog.getWindow();

		window.setContentView(R.layout.dialog_upload);
		
		ensure=(LinearLayout) window.findViewById(R.id.ll_ensure);
		ensure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
	
	private void showDialogChoose() {
		LinearLayout paizhao, xiangce, quxiao;
		final AlertDialog dialog = new AlertDialog.Builder(this).create();

		View view = LayoutInflater.from(this).inflate(
				R.layout.dialog_choosehead, null);

		dialog.setView(view);
		dialog.show();

		Window window = dialog.getWindow();

		window.setContentView(R.layout.dialog_choosehead);

		paizhao = (LinearLayout) window.findViewById(R.id.ll_paizhao);
		paizhao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ImageLoader.getInstance().clearMemoryCache();
				ImageLoader.getInstance().clearDiscCache();
				dialog.dismiss();
				//camera();
				//a = 1;
			}
		});
		xiangce = (LinearLayout) window.findViewById(R.id.ll_xiangce);
		xiangce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ImageLoader.getInstance().clearMemoryCache();
				ImageLoader.getInstance().clearDiscCache();
				dialog.dismiss();
				gallery();
				//a = 1;
			}
		});
		quxiao = (LinearLayout) window.findViewById(R.id.ll_quxiao);
		quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	public void gallery() {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
//		if(requestCode==PHOTO_REQUEST_GALLERY){
//			
//			Uri uri=data.getData();
//			
//		}
		
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				// img_user.setImageURI(uri);
				Bitmap bitmap = GetSdcardInfomation
						.lessenUriImage(getPath(uri));
				if (hasSdcard()) {
					saveBitmap(bitmap);
				}
				
			}
		}
	}
	
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	
	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	private void saveBitmap(Bitmap bitmap) {
		FileOutputStream b = null;
		File file = new File("/sdcard/shop/");
		if (!file.exists()) {
			file.mkdirs();
		}
		File imageFile = new File(file, PHOTO_NAME);
		try {
			imageFile.createNewFile();
			b = new FileOutputStream(imageFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, b);
			iv_front.setImageBitmap(bitmap);
			b.flush();
			b.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
