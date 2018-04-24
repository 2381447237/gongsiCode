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
	// �̻�PID
	public static final String PARTNER = "2088121469173196";
	// �̻��տ��˺�
	public static final String SELLER = "youli_sh@126.com";
	// �̻�˽Կ��pkcs8��ʽ
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALVvQWg2hf3RSeUMFiBHe8jzKD1IAimUU65q4ubF2+BUsQPG0y511vNUS/BjeeDfSN57rXhvvLQ2KZTRb1pGHSb0L1pWqrxqgiErIfJawT+OuMqe1UkWEoxEV4X9BA/uYpaQ350o4Kx+rqhBhBkr+znKLtbFj65CVxg5heTPomORAgMBAAECgYA72REoQSP9z7WiWlRKHYpVhO/3FvOvp3/a/uMN4KJg407owgMTRke7SEksaIPhi4XL7dwQ6DrE70DUGCm1C3+9tYgvVn+lP8i0Ag7AYD7XgUXtsRfKudUoBNnzFUOdleDqya1tE4SLZ5PNhvlSWOv2nbSvNOjfzletlxZ63k+QgQJBANqdRVLIuZawKK5hirRTZ96eqi4JlOLgl1dyDppcjEEc77D9W/7zOB6waWD6ejyP3bJtd034yH5nnhoXjI+miwkCQQDUdkpd9wue9UkxeZWuEIfZDu+7+YjWzi2NvAs64XV/AucNEf4UYS/tRwNr0BzD6ILkG+HaKUGGvgCFLTDOiE5JAkBaSP1qVQ+glhwW+J3KH3AUVr69yKM+l7apHKe/RF2APq0XEWu+/T++HOlIbemxvVC38dGF2CslWTHIYNViNkvRAkEAhc4Bw+/kV7Xu5MPH6pnqlF79yIq9DVtIAS1efTSkxNrVHLwqIjea8Xp5wxqMIy150aNlt/mnlO5wcW8mf8wEUQJBAL1X1S03F9qiW84Wi92Wo2o1XLHbUBdG8t8c0E1JvLoy6+eLNxfuUpOmpU/oXZipMqjwvnTPogkPMSXPQAn3SC0=";
	// ֧������Կ
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
		});// ��Ӽ����¼�
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
				 * ͬ�����صĽ��������õ�����˽�����֤����֤�Ĺ����뿴https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) �����̻������첽֪ͨ
				 */
				String resultInfo = payResult.getResult();// ͬ��������Ҫ��֤����Ϣ

				String resultStatus = payResult.getResultStatus();
				// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PayActivity.this, "֧���ɹ�", Toast.LENGTH_SHORT)
							.show();
				} else {
					// �ж�resultStatus Ϊ��"9000"��������֧��ʧ��
					// "8000"����֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayActivity.this, "֧�����ȷ����",
								Toast.LENGTH_SHORT).show();

					} else {
						// ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
						Toast.makeText(PayActivity.this, "֧��ʧ��",
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
					.setTitle("����")
					.setMessage("��Ҫ����PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									//
									finish();
								}
							}).show();
			return;
		}
		String orderInfo = getOrderInfo("���Ե���Ʒ", "�ò�����Ʒ����ϸ����", "0.01");

		/**
		 * �ر�ע�⣬�����ǩ���߼���Ҫ���ڷ���ˣ�����˽Կй¶�ڴ����У�
		 */
		String sign = sign(orderInfo);
		try {
			/**
			 * �����sign ��URL����
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * �����ķ���֧���������淶�Ķ�����Ϣ
		 */
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// ����PayTask ����
				PayTask alipay = new PayTask(PayActivity.this);
				// ����֧���ӿڣ���ȡ֧�����
				String result = alipay.pay(payInfo, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// �����첽����
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	private String getOrderInfo(String subject, String body, String price) {

		// ǩԼ���������ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// ǩԼ����֧�����˺�
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// �̻���վΨһ������
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// ��Ʒ����
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// ��Ʒ����
		orderInfo += "&body=" + "\"" + body + "\"";

		// ��Ʒ���
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// �������첽֪ͨҳ��·��
		orderInfo += "&notify_url=" + "\"" + HttpUrl.HttpURL+"/json/Get_Pay_Treasure_Info.aspx?lsh="+userID+","+price
				+ "\"";

		// ����ӿ����ƣ� �̶�ֵ
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// ֧�����ͣ� �̶�ֵ
		orderInfo += "&payment_type=\"1\"";

		// �������룬 �̶�ֵ
		orderInfo += "&_input_charset=\"utf-8\"";

		// ����δ����׵ĳ�ʱʱ��
		// Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
		// ȡֵ��Χ��1m��15d��
		// m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
		// �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_tokenΪ���������Ȩ��ȡ����alipay_open_id,���ϴ˲����û���ʹ����Ȩ���˻�����֧��
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
		orderInfo += "&return_url=\"m.alipay.com\"";

		// �������п�֧���������ô˲���������ǩ���� �̶�ֵ ����ҪǩԼ���������п����֧��������ʹ�ã�
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. �����̻������ţ���ֵ���̻���Ӧ����Ψһ�����Զ����ʽ�淶��
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
	 * sign the order info. �Զ�����Ϣ����ǩ��
	 * 
	 * @param content
	 *            ��ǩ��������Ϣ
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. ��ȡǩ����ʽ
	 * 
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
}
