package com.example.secondlevelactivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import com.alipay.sdk.app.PayTask;
import com.base.activity.BaseActivity;
import com.base.activity.PayResult;
import com.base.activity.SignUtils;
import com.example.httpurl.HttpUrl;
import com.example.shopping.R;
import com.example.shopping.R.layout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class PayActivity extends BaseActivity implements OnClickListener {
	// 商户PID
	public static final String PARTNER = "2088121469173196";
	// 商户收款账号
	public static final String SELLER = "youli_sh@126.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALVvQWg2hf3RSeUMFiBHe8jzKD1IAimUU65q4ubF2+BUsQPG0y511vNUS/BjeeDfSN57rXhvvLQ2KZTRb1pGHSb0L1pWqrxqgiErIfJawT+OuMqe1UkWEoxEV4X9BA/uYpaQ350o4Kx+rqhBhBkr+znKLtbFj65CVxg5heTPomORAgMBAAECgYA72REoQSP9z7WiWlRKHYpVhO/3FvOvp3/a/uMN4KJg407owgMTRke7SEksaIPhi4XL7dwQ6DrE70DUGCm1C3+9tYgvVn+lP8i0Ag7AYD7XgUXtsRfKudUoBNnzFUOdleDqya1tE4SLZ5PNhvlSWOv2nbSvNOjfzletlxZ63k+QgQJBANqdRVLIuZawKK5hirRTZ96eqi4JlOLgl1dyDppcjEEc77D9W/7zOB6waWD6ejyP3bJtd034yH5nnhoXjI+miwkCQQDUdkpd9wue9UkxeZWuEIfZDu+7+YjWzi2NvAs64XV/AucNEf4UYS/tRwNr0BzD6ILkG+HaKUGGvgCFLTDOiE5JAkBaSP1qVQ+glhwW+J3KH3AUVr69yKM+l7apHKe/RF2APq0XEWu+/T++HOlIbemxvVC38dGF2CslWTHIYNViNkvRAkEAhc4Bw+/kV7Xu5MPH6pnqlF79yIq9DVtIAS1efTSkxNrVHLwqIjea8Xp5wxqMIy150aNlt/mnlO5wcW8mf8wEUQJBAL1X1S03F9qiW84Wi92Wo2o1XLHbUBdG8t8c0E1JvLoy6+eLNxfuUpOmpU/oXZipMqjwvnTPogkPMSXPQAn3SC0=";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	private static final int SDK_PAY_FLAG = 1;

	private TextView tv_paytotalprice;
	private String totalprice;
	private ToggleButton mTogBtn;
	private View layout_fapiao, layout_address;
	private Button btn_pay;
	private String userID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		Intent intent = getIntent();
		totalprice = intent.getStringExtra("totalprice");
		SharedPreferences preferences = getSharedPreferences("user",
				Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");
		initView();
	}

	private void initView() {
		tv_paytotalprice = (TextView) findViewById(R.id.tv_paytotalprice);
		mTogBtn = (ToggleButton) findViewById(R.id.mTogBtn);
		layout_fapiao = findViewById(R.id.layout_fapiao);
		layout_address = findViewById(R.id.layout_address);
		btn_pay = (Button) findViewById(R.id.btn_pay);
		btn_pay.setOnClickListener(this);
		layout_address.setOnClickListener(this);
		layout_fapiao.setVisibility(View.GONE);
		tv_paytotalprice.setText(totalprice);
		mTogBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					layout_fapiao.setVisibility(View.VISIBLE);
				} else {
					layout_fapiao.setVisibility(View.GONE);
				}
			}
		});// 添加监听事件
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_address:
			Intent intent = new Intent(PayActivity.this, AddressActivity.class);
			startActivity(intent);
			break;

		case R.id.btn_pay:
			pay(v);
			break;
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT)
							.show();
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			default:
				break;
			}
		};
	};

	public void pay(View v) {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this)
					.setTitle("警告")
					.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									//
									finish();
								}
							}).show();
			return;
		}
		String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");

		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = sign(orderInfo);
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(PayActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	private String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + HttpUrl.HttpURL+"/json/Get_Pay_Treasure_Info.aspx?lsh="+userID+","+price
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
}
