package com.example.shopping;

import java.lang.reflect.Type;
import java.util.LinkedList;

import okhttp3.Call;

import com.example.cusview.CusviewRadioGroup;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.MyAddressManage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MyOrderActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener,
		android.widget.RadioGroup.OnCheckedChangeListener,
		com.example.cusview.CusviewRadioGroup.OnCheckedChangeListener {

	private ImageView img_back, img_toAddressmanage;
	private ToggleButton mToggleButton;
	private LinearLayout ll_invoice, ll_personalInfo;
	private RadioGroup rg_invoiceType;
	private CusviewRadioGroup rg_payMode;
	private EditText et_info, et_remark;
	// private LinearLayout ll_bottom;
	private TextView tv_myordertotalprice;
	private String totalprice;
	private String userID;
//	private String personalInfoUrl = "http://www.youli.pw:85/Json/Get_User_Address_Info.aspx";
	private String personalInfoUrl = HttpUrl.HttpURL+"/Json/Get_User_Address_Info.aspx";

	private TextView tv_receiver, tv_phone, tv_goodsAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myorder);

		getUserId();
		
		img_toAddressmanage = (ImageView) findViewById(R.id.img_toAddressmanage);
		img_toAddressmanage.setOnClickListener(this);
		img_back = (ImageView) findViewById(R.id.img_back_myorder);
		img_back.setOnClickListener(this);
		mToggleButton = (ToggleButton) findViewById(R.id.mToggleButton);
		mToggleButton.setOnCheckedChangeListener(this);
		ll_invoice = (LinearLayout) findViewById(R.id.invoice_ll);
		ll_personalInfo = (LinearLayout) findViewById(R.id.ll_personalInfo);
		ll_personalInfo.setOnClickListener(this);
		rg_invoiceType = (RadioGroup) findViewById(R.id.rg_invoiceType);
		rg_invoiceType.setOnCheckedChangeListener(this);
		rg_payMode = (CusviewRadioGroup) findViewById(R.id.rg_payMode);
		rg_payMode
				.setOnCheckedChangeListener((com.example.cusview.CusviewRadioGroup.OnCheckedChangeListener) this);

		et_info = (EditText) findViewById(R.id.et_info);
		et_info.setOnClickListener(this);
		et_remark = (EditText) findViewById(R.id.et_remark);
		et_remark.setOnClickListener(this);

		tv_myordertotalprice = (TextView) findViewById(R.id.tv_myordertotalprice);

		Intent intent = getIntent();

		totalprice = intent.getStringExtra("totalprice");

		tv_myordertotalprice.setText(totalprice);

		tv_receiver = (TextView) findViewById(R.id.tv_receiver);
		tv_phone = (TextView) findViewById(R.id.tv_receiverPhone);
		tv_goodsAddress = (TextView) findViewById(R.id.tv_goodsAddress);

		// ll_bottom=(LinearLayout) findViewById(R.id.ll_order_bottom);

	}

	@Override
	protected void onResume() {
		super.onResume();
		
		getPersonalInfo();
	}

	private void getUserId() {

		SharedPreferences preferences = this.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");

	}

	private void getPersonalInfo() {
		
		OkHttpUtils.post().url(personalInfoUrl).addParams("AcctID", userID)
				.build().execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						runOnUiThread(new Runnable() {
							public void run() {

								Gson gson = new Gson();

								Type listType = new TypeToken<LinkedList<MyAddressManage>>() {
								}.getType();

								LinkedList<MyAddressManage> mAm = gson
										.fromJson(str, listType);

								for (MyAddressManage mAmanage : mAm) {

									if (mAmanage.IsDefault == true) {

										tv_receiver.setText(mAmanage.Name);
										tv_phone.setText(mAmanage.PhoneNumber
												.substring(0, 3)
												+ "****"
												+ mAmanage.PhoneNumber
														.substring(
																7,
																mAmanage.PhoneNumber
																		.length()));
										tv_goodsAddress
												.setText(mAmanage.Address);
									}

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
		switch (v.getId()) {

		case R.id.img_back_myorder:
			finish();
			break;

		case R.id.img_toAddressmanage:
			Intent intent;
			intent = new Intent(this, AddressManageActivity.class);
			startActivity(intent);
			break;

		case R.id.ll_personalInfo:
			if (tv_receiver.getText().toString()== null || tv_phone.getText().toString()== null
					|| tv_goodsAddress.getText().toString()== null) {
				Intent intent2;
				intent2 = new Intent(this, AddressManageActivity.class);
				startActivity(intent2);
			} else {
				showDialog();
			}
			break;

		case R.id.et_info:

			break;

		case R.id.et_remark:

			break;
		default:
			break;

		}
	}

	private void showDialog() {

		final AlertDialog dialog = new AlertDialog.Builder(this).create();

		View view = LayoutInflater.from(this).inflate(
				R.layout.newadd_dialog_layout, null);
		dialog.setView(view);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.newadd_dialog_layout);
		TextView tv_dialog = (TextView) window
				.findViewById(R.id.newadd_tv_dialog);
		tv_dialog.setText("您要更改个人信息吗？");
		Button btnOk = (Button) window.findViewById(R.id.newadd_btn_ok);
		Button btnUndo = (Button) window.findViewById(R.id.newadd_btn_undo);

		btnUndo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.dismiss();
				Intent intent;
				intent = new Intent(MyOrderActivity.this,
						AddressManageActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		switch (buttonView.getId()) {
		case R.id.mToggleButton:

			if (isChecked) {
				// 选中
				// Toast.makeText(MyOrderActivity.this,"选中",1).show();
				ll_invoice.setVisibility(View.VISIBLE);
			} else {
				// 未选中
				// Toast.makeText(MyOrderActivity.this,"未选中",1).show();
				ll_invoice.setVisibility(View.GONE);
			}

			break;

		default:
			break;
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (group.getId()) {
		case R.id.rg_invoiceType:

			if (checkedId == R.id.rb_personal) {
				// Toast.makeText(MyOrderActivity.this, "个人", 1).show();
			} else if (checkedId == R.id.rb_company) {
				// Toast.makeText(MyOrderActivity.this, "公司", 1).show();
			}

			break;
		default:
			break;
		}

	}

	@Override
	public void onCheckedChanged(CusviewRadioGroup group, int checkedId) {
		switch (group.getId()) {
		case R.id.rg_payMode:

			if (checkedId == R.id.rb_alipay) {
				// Toast.makeText(MyOrderActivity.this, "支付宝", 1).show();
			} else if (checkedId == R.id.rb_weChat) {
				// Toast.makeText(MyOrderActivity.this, "微信", 1).show();
			}

			break;
		default:
			break;
		}
	}

}
