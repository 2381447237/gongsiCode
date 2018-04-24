package com.example.secondlevelactivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;

import org.json.JSONException;

import com.base.activity.BaseActivity;
import com.base.activity.GetSdcardInfomation;
import com.base.activity.RoundImageView;
import com.base.activity.ShopApplication;
import com.base.activity.VollyUtils.VolleyListener;
import com.example.cusview.CircleTransform;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.ShopDetail;
import com.example.infoclass.UserInfo;
import com.example.shopping.AddressManageActivity;
import com.example.shopping.MineInfomationFragment;
import com.example.shopping.MyOrderActivity;
import com.example.shopping.R;
import com.example.shopping.R.id;
import com.example.shopping.R.layout;
import com.example.shopping.R.menu;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.loveplusplus.demo.image.ImageDetailFragment;
import com.loveplusplus.demo.image.ImagePagerActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class UserInformationActivity extends BaseActivity implements
		OnClickListener, VolleyListener {

	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	private static final String PHOTO_NAME = "user.jpg";

	private String myInfoUrl = HttpUrl.HttpURL+"/Json/Set_UpdateUserInfo.aspx";
	private String userInfoUrl = HttpUrl.HttpURL+"/Json/Get_UserInfoForUserId.aspx";
	private LinearLayout paizhao, xiangce, quxiao;
	private TextView tv_username, tv_birthday, tv_mailbox, tv_phone,
			tv_id, tv_sex;
	private ImageView img_user;
	private ImageView img_back;
	private View layout_user, layout_nickname, layout_sex, layout_birthday,
			layout_mailbox, layout_ID, layout_changpwd;
	private Button btn_picture, btn_photo, btn_cancel;
	private UserInfo userInfo;
	private ProgressDialog dialog;
	public static Spinner spinner_sex;
	private List<String> list;
	private int m = 0;
	public static int a = 0;
	private int sex;
	String userID;
	String phone;
	private int mYear, mMonth, mDay;//出生日期 年月日
	private final int DATE_DIALOG = 1;
	//0保密，1男，2女
	private int  sexId;
	private String myNickname;
	private String birthdayStr;
	
	private RelativeLayout layout_am;//地址管理
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_information);
		SharedPreferences preferences = getSharedPreferences("user",
				Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");
		phone = preferences.getString("phone", "");
		registerBoradcastReceiver();
		initView();
		initData();
	}

	private void initData() {
		tv_phone.setText(phone.substring(0, 3) + "****"
				+ phone.substring(7, phone.length()));
		// if
		// (GetSdcardInfomation.getBitmap(GetSdcardInfomation.PHOTO_PATH_NAME)
		// != null) {
		// img_user.setImageBitmap(GetSdcardInfomation
		// .getBitmap(GetSdcardInfomation.PHOTO_PATH_NAME));
		// } else {
		a = 2;
		getJson();
		// }
	}

	private void getJson() {
		try {
			ShopApplication.utils
					.init(this)
					.setListener(this)
					.getJson(
							HttpUrl.HttpURL+"/Json/Get_UserInfoForUserId.aspx?AcctID="
									+ userID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void initView() {
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_sex = (TextView) findViewById(R.id.tv_sex);
		tv_birthday = (TextView) findViewById(R.id.tv_birthday);
		tv_mailbox = (TextView) findViewById(R.id.tv_mailbox);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_id = (TextView) findViewById(R.id.tv_id);
		img_user = (ImageView) findViewById(R.id.img_user);
		img_back = (ImageView) findViewById(R.id.img_back3);
		layout_user = findViewById(R.id.layout_user);
		layout_nickname = findViewById(R.id.layout_nickname);
		layout_sex = findViewById(R.id.layout_sex);
		layout_birthday = findViewById(R.id.layout_birthday);
		layout_mailbox = findViewById(R.id.layout_mailbox);
		layout_ID = findViewById(R.id.layout_ID);
		layout_changpwd = findViewById(R.id.layout_changpwd);
		layout_am=(RelativeLayout) findViewById(R.id.layout_AM);
		layout_am.setOnClickListener(this);
		layout_user.setOnClickListener(this);
		layout_nickname.setOnClickListener(this);
		layout_sex.setOnClickListener(this);
		layout_birthday.setOnClickListener(this);
		layout_mailbox.setOnClickListener(this);
		layout_ID.setOnClickListener(this);
		layout_changpwd.setOnClickListener(this);
		img_back.setOnClickListener(this);
		img_user.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_user:
			// showMakeGradeMarkedWindow(v);
			showDialogChoose();
			break;
		case R.id.img_back3:
			MineInfomationFragment.refresh();
			finish();
			break;
		case R.id.img_user:
			// if (GetSdcardInfomation
			// .getBitmap(GetSdcardInfomation.PHOTO_PATH_NAME) != null) {
			// String[] array = { "file://"
			// + GetSdcardInfomation.PHOTO_PATH_NAME };
			// imageBrower(0, array);
			// } else {
			// if (userInfo.HeadImg != null) {
			// String[] array = { HttpUrl.HttpURL + "/" + userInfo.HeadImg };
			// imageBrower(0, array);
			// }
			// }
			if (GetSdcardInfomation.getBitmap(
					GetSdcardInfomation.PHOTO_PATH_NAME, 500, 500) != null) {
				
				String[] array = { "file://"
						+ GetSdcardInfomation.PHOTO_PATH_NAME };
				imageBrower(0, array);
			} else {
				
				if (userInfo.HeadImg != null) {
					String[] array = { HttpUrl.HttpURL + "/" + userInfo.HeadImg };
					imageBrower(0, array);
				}
			}
			break;
		case R.id.layout_nickname:
			a = 1;
			// Intent intent4 = new Intent(UserInformationActivity.this,
			// ChangeUserInfoActivity.class);
			// intent4.putExtra("nickname", tv_username.getText());
			// startActivity(intent4);
			Intent intent4 = new Intent(UserInformationActivity.this,
					NickNameActivity.class);

			if (!TextUtils.isEmpty(tv_username.getText().toString().trim())) {
				intent4.putExtra("nickname", tv_username.getText());
			}

			// intent4.putExtra("nickname", tv_username.getText());
			startActivity(intent4);
			break;
		case R.id.layout_sex:
			a = 2;
			// Intent intent1 = new Intent(UserInformationActivity.this,
			// ChangeUserInfoActivity.class);
			// intent1.putExtra("nickname", tv_sex.getText());
			// startActivity(intent1);
			Intent intent1 = new Intent(UserInformationActivity.this,
					SetSexActivity.class);
			intent1.putExtra("nickname", tv_sex.getText());
			startActivity(intent1);
			break;
		case R.id.layout_birthday:
			a = 3;
//			Intent intent5 = new Intent(UserInformationActivity.this,
//					ChangeUserInfoActivity.class);
//			intent5.putExtra("nickname", tv_birthday.getText());
//			
//			startActivity(intent5);
			
			
			final Calendar ca = Calendar.getInstance();
			mYear = ca.get(Calendar.YEAR);
			mMonth = ca.get(Calendar.MONTH);
			mDay = ca.get(Calendar.DAY_OF_MONTH);
			
			showDialog(DATE_DIALOG);
			
			break;
		case R.id.layout_mailbox:
			a = 4;
			Intent intent2 = new Intent(UserInformationActivity.this,
					ChangeUserInfoActivity.class);
			intent2.putExtra("nickname", tv_mailbox.getText());
			startActivity(intent2);
			break;
		case R.id.layout_ID:
			a = 5;
//			Intent intent3 = new Intent(UserInformationActivity.this,
//					ChangeUserInfoActivity.class);
//			intent3.putExtra("nickname", tv_id.getText());
			Intent intent3=new Intent(UserInformationActivity.this,IdentityInfoActivity.class);
			startActivity(intent3);
			break;
		case R.id.layout_changpwd:
			ShopApplication.REGIST_FLAG = 1;
			Intent intent6 = new Intent(UserInformationActivity.this,
					ChangePasswordActivity.class);
			startActivity(intent6);
			break;
		case R.id.layout_AM:
			Intent intent7=new Intent(UserInformationActivity.this,AddressManageActivity.class);
			startActivity(intent7);
			break;
		}
	}

	 @Override
	    protected Dialog onCreateDialog(int id) {
	        switch (id) {
	            case DATE_DIALOG:			
	                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
	        }
	        return null;
	    }
	    
	    public void display() {
	    	tv_birthday.setText(birthdayStr);
	    }
	    
	    private DatePickerDialog.OnDateSetListener mdateListener=new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				
				    mYear = year;
		            mMonth = monthOfYear;
		            mDay = dayOfMonth;
		          
		            birthdayStr=mYear+"-"+(mMonth+1)+"-"+mDay;
		            getAllInfo();
		            
		           
				
			}
		};
	
		private void setBirthday(){
			
			OkHttpUtils.post().url(myInfoUrl).addParams("AcctID",userID).addParams("sexText",String.valueOf(sexId)).addParams("Birthday",birthdayStr)
			.addParams("daveText",myNickname).build().execute(new StringCallback() {
				
				@Override
				public void onResponse(String arg0) {
					 display();
				}
				
				@Override
				public void onError(Call arg0, Exception arg1) {
					
				}
			});
			
		}
		
		private void getAllInfo(){
			
			OkHttpUtils.post().url(userInfoUrl).addParams("AcctID",userID).build().execute(new StringCallback() {
				
				@Override
				public void onResponse(final String str) {
					
					runOnUiThread( new Runnable() {
						public void run() {
							
							Gson gson=new Gson();
							
							UserInfo uIo = gson.fromJson(str,
									UserInfo.class);
							
							String sexStr=uIo.Sex;
							
							if(TextUtils.equals("男",sexStr)){
								sexId=1;
							}else if(TextUtils.equals("女",sexStr)){
								sexId=2;
							}else if(TextUtils.equals("保密",sexStr)){
								sexId=0;
							}
							
							myNickname=uIo.UserName;
							
							setBirthday();
						}
					});
					
				}
				
				@Override
				public void onError(Call arg0, Exception arg1) {
					
				}
			});
			
		}
		
	// private void showMakeGradeMarkedWindow(View v) {
	// View view = LayoutInflater.from(UserInformationActivity.this).inflate(
	// R.layout.popupwindow, null);
	// btn_picture = (Button) view.findViewById(R.id.btn_picture);
	// btn_photo = (Button) view.findViewById(R.id.btn_photo);
	// btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
	// //final PopupWindow mPopupWindow = new PopupWindow(view, 469, 250);
	// final PopupWindow mPopupWindow = new
	// PopupWindow(view,LayoutParams.FILL_PARENT,250);
	// mPopupWindow.setFocusable(true);
	// ColorDrawable dw = new ColorDrawable(0xDCDCDC);
	// mPopupWindow.setBackgroundDrawable(dw);
	// mPopupWindow.setAnimationStyle(R.style.take_photo_anim);
	// mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
	//
	// // 锟斤拷锟矫憋拷锟斤拷锟斤拷色锟戒暗
	// WindowManager.LayoutParams lp = getWindow().getAttributes();
	// lp.alpha = 0.7f;
	// getWindow().setAttributes(lp);
	// mPopupWindow.setOnDismissListener(new OnDismissListener() {
	//
	// @Override
	// public void onDismiss() {
	// WindowManager.LayoutParams lp = getWindow().getAttributes();
	// lp.alpha = 1f;
	// getWindow().setAttributes(lp);
	// }
	// });
	// btn_cancel.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// mPopupWindow.dismiss();
	// }
	// });
	// btn_picture.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// ImageLoader.getInstance().clearMemoryCache();
	// ImageLoader.getInstance().clearDiscCache();
	// mPopupWindow.dismiss();
	// gallery();
	// a = 1;
	// }
	// });
	// btn_photo.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// ImageLoader.getInstance().clearMemoryCache();
	// ImageLoader.getInstance().clearDiscCache();
	// mPopupWindow.dismiss();
	// camera();
	// a = 1;
	// }
	// });
	// }

	private void showDialogChoose() {

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
				camera();
				a = 1;
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
				a = 1;
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

	public void camera() {
		if (hasSdcard()) {
			try {
				File file = new File("/sdcard/camerashop/camera.jpg");
				if (file.exists()) {
					file.delete();
				}
				File dir = new File(Environment.getExternalStorageDirectory()
						+ "/" + "camerashop");
				if (!dir.exists())
					dir.mkdirs();

				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				File f = new File(dir, "camera.jpg");
				Uri u = Uri.fromFile(f);
				intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
				startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
			} catch (ActivityNotFoundException e) {
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
				
				getAllInfo2();
				
				//uploadImage();
			}

		} else if (requestCode == PHOTO_REQUEST_CAMERA) {

			File file = new File("/sdcard/camerashop/camera.jpg");
			FileInputStream inputStream;
			try {
				inputStream = new FileInputStream(file);
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				if (bitmap != null) {
					saveBitmap(bitmap);
					getAllInfo2();
					//uploadImage();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void getAllInfo2(){
		
		OkHttpUtils.post().url(userInfoUrl).addParams("AcctID",userID).build().execute(new StringCallback() {
			
			@Override
			public void onResponse(final String str) {
				
				runOnUiThread( new Runnable() {
					public void run() {
						
						Gson gson=new Gson();
						
						UserInfo uIo = gson.fromJson(str,
								UserInfo.class);
						
						String sexStr=uIo.Sex;
						
						if(TextUtils.equals("男",sexStr)){
							sexId=1;
						}else if(TextUtils.equals("女",sexStr)){
							sexId=2;
						}else if(TextUtils.equals("保密",sexStr)){
							sexId=0;
						}
						
						myNickname=uIo.UserName;
						
						uploadImage();
						
					}
				});
				
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				
			}
		});
		
	}
	
	private void uploadImage() {
	
		String uploadHost = HttpUrl.HttpURL+"/Json/Set_UpdateUserInfo.aspx?AcctID="
				+ userID
				+ "&daveText="
				+ tv_username.getText()
				+ "&sexText="
				+ String.valueOf(sexId)
				+ "&phoneText="
				+ tv_phone.getText()
				+ "&Email="
				+ tv_mailbox.getText()
				+ "&IdCard="
				+ tv_id.getText()
				+ "&Birthday=" + tv_birthday.getText();
		String filePath = GetSdcardInfomation.PHOTO_PATH_NAME;
		RequestParams params = new RequestParams();
		params.addBodyParameter(filePath.replace("/", ""), new File(filePath));
		uploadMethod(params, uploadHost);
		showDialog();
		
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
			b.flush();
			b.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	
				setUserInfo();
			
	}

	private void setUserInfo() {

		OkHttpUtils.post().url(userInfoUrl).addParams("AcctID", userID).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						runOnUiThread(new Runnable() {

							@Override
							public void run() {

								Gson gson = new Gson();

								UserInfo uIo = gson.fromJson(str,
										UserInfo.class);
                             
//								tv_sex.setText(uIo.Sex);
//								tv_username.setText(uIo.UserName);
								
								tv_username.setText(uIo.UserName);
								 tv_sex.setText(uIo.Sex);
								tv_birthday.setText(uIo.Birthday);
								tv_mailbox.setText(uIo.Email);
								tv_id.setText(uIo.Verification);
								
								switch (a) {
								case 2:
									if (uIo.HeadImg != null) {
										Picasso.with(UserInformationActivity.this)
												.load(HttpUrl.HttpURL + "/" + uIo.HeadImg)
												.transform(new CircleTransform()).error(R.drawable.denglu).into(img_user);
									}
//									tv_username.setText(uIo.UserName);
//									 tv_sex.setText(uIo.Sex);
//									tv_birthday.setText(uIo.Birthday);
//									tv_mailbox.setText(uIo.Email);
//									tv_id.setText(uIo.IdCard);
									break;

								case 1:
									if (uIo.HeadImg != null) {
										Picasso.with(UserInformationActivity.this)
												.load(HttpUrl.HttpURL + "/" + uIo.HeadImg)
												.transform(new CircleTransform()).error(R.drawable.denglu).into(img_user);
									}
									break;
								case 3:
									
//									tv_username.setText(uIo.UserName);
//									 tv_sex.setText(uIo.Sex);
//									tv_birthday.setText(uIo.Birthday);
//									tv_mailbox.setText(uIo.Email);
//									tv_id.setText(uIo.IdCard);
									break;
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
	public void getJson(String response) {
		Gson gson = new Gson();
		userInfo = gson.fromJson(response, UserInfo.class);
//		switch (a) {
//		case 2:
//			if (userInfo.HeadImg != null) {
//				Picasso.with(this)
//						.load(HttpUrl.HttpURL + "/" + userInfo.HeadImg)
//						.transform(new CircleTransform()).into(img_user);
//			}
//			tv_username.setText(userInfo.UserName);
//			 tv_sex.setText(userInfo.Sex);
//			tv_birthday.setText(userInfo.Birthday);
//			tv_mailbox.setText(userInfo.Email);
//			tv_id.setText(userInfo.IdCard);
//			break;
//
//		case 1:
//			if (userInfo.HeadImg != null) {
//				Picasso.with(this)
//						.load(HttpUrl.HttpURL + "/" + userInfo.HeadImg)
//						.error(R.drawable.denglu).into(img_user);
//			}
//			break;
//		case 3:
//			
//			tv_username.setText(userInfo.UserName);
//			 tv_sex.setText(userInfo.Sex);
//			tv_birthday.setText(userInfo.Birthday);
//			tv_mailbox.setText(userInfo.Email);
//			tv_id.setText(userInfo.IdCard);
//			break;
//		}
	}

	@Override
	public void getFiel() {

	}

	public void uploadMethod(final RequestParams params, final String uploadHost) {
		new HttpUtils().send(HttpRequest.HttpMethod.POST, uploadHost, params,
				new RequestCallBack<String>() {
					@Override
					public void onStart() {
						// msgTextview.setText("conn...");
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						if (dialog != null && dialog.isShowing()) {
							dialog.dismiss();
						}
						Toast.makeText(getApplicationContext(), "上传成功", 1000)
								.show();
						//getJson();
						setUserInfo();
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						if (dialog != null && dialog.isShowing()) {
							dialog.dismiss();
						}
						Toast.makeText(getApplicationContext(), "上传失败,请检查网络",
								1000).show();
						File file = new File(
								GetSdcardInfomation.PHOTO_PATH_NAME);
						file.delete();
						// getJson();
					}
				});
	}

	private void imageBrower(int position, String[] urls) {
		Intent intent = new Intent(UserInformationActivity.this,
				ImagePagerActivity.class);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		img_back.performClick();
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private void showDialog() {
		dialog = new ProgressDialog(this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("正在上传...");
		dialog.show();

	}

	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("cctv");
		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("cctv")) {
				getJson();
			}
		}

	};

	@Override
	public void onDestroy() {
		super.onStop();
		unregisterReceiver(mBroadcastReceiver);
	}

}
