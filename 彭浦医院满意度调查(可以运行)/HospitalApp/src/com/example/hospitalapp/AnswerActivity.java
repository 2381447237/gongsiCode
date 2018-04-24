package com.example.hospitalapp;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import okhttp3.Call;

import com.example.hospitalapp.adapter.AnswerAdapter;
import com.example.hospitalapp.entity.AnswerContent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class AnswerActivity extends Activity {

	// http://192.168.11.11:115/Json/Get_Investigator_Id.aspx?MasterId=2&InvestigatorId=74;

	private String answerUrl = MainActivity.httpStr + MainActivity.url
			+ "/Json/Get_Investigator_Id.aspx";
	private AnswerListView lv;
	private AnswerAdapter aAdapter;
	private List<AnswerContent> data = new ArrayList<AnswerContent>();
	private LinearLayout ll_footer;
	private EditText et_name, et_phone;
	private String nameStr, phoneStr;
	private RelativeLayout rl_header;
	private TextView tv_header, tv_header_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_answer);

		Intent intent = getIntent();

		nameStr = intent.getStringExtra("name");
		phoneStr = intent.getStringExtra("phone");

		lv = (AnswerListView) findViewById(R.id.lv_answer);
		ll_footer = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.answer_footer, null);
		lv.addFooterView(ll_footer);

		rl_header = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.answer_header, null);
		lv.addHeaderView(rl_header);

		tv_header_title = (TextView) rl_header
				.findViewById(R.id.header_title_tv);
		tv_header = (TextView) rl_header.findViewById(R.id.header_tv);

		tv_header_title.setText(intent.getStringExtra("title_tv"));

		if (!TextUtils
				.isEmpty(Html.fromHtml(intent.getStringExtra("header_tv"))
						.toString().trim())) {
			rl_header.setVisibility(View.VISIBLE);
			tv_header.setVisibility(View.VISIBLE);
			tv_header.setText(Html.fromHtml(intent.getStringExtra("header_tv"))
					.toString().trim());
		} else {

			rl_header.setPadding(0, 0, 0, 0);
			tv_header.setVisibility(View.GONE);
		}

		et_name = (EditText) ll_footer.findViewById(R.id.et_name_answer);
		et_phone = (EditText) ll_footer.findViewById(R.id.et_phone_answer);

		et_name.setText(nameStr);
		et_phone.setText(phoneStr);

		someData();
	}

	private void someData() {

		String myMIdStr = null;

		String InvestigatorIdStr = null;
		if (FormList.myMasterId != 0) {
			myMIdStr = String.valueOf(FormList.myMasterId);
		} else if (FormList.mynoNetMasterId != 0) {
			myMIdStr = String.valueOf(FormList.mynoNetMasterId);
		}

		InvestigatorIdStr = String.valueOf(DetailedQuestionNonetWork.mId);

//		Log.i("2016/11/23", "DetailedQuestionNonetWork.mId"
//				+ DetailedQuestionNonetWork.mId);
//
//		Log.i("2016/11/23", "=InvestigatorIdStr=" + InvestigatorIdStr);

		OkHttpUtils.post().url(answerUrl).addParams("MasterId", myMIdStr)
				.addParams("InvestigatorId", InvestigatorIdStr).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						runOnUiThread(new Runnable() {
							public void run() {

								Gson gson = new Gson();

								Type listType = new TypeToken<LinkedList<AnswerContent>>() {
								}.getType();

								LinkedList<AnswerContent> ac = gson.fromJson(
										str, listType);

								for (AnswerContent aContent : ac) {
									data.add(aContent);
								}

								aAdapter = new AnswerAdapter(data,
										AnswerActivity.this);
								lv.setAdapter(aAdapter);
							}
						});

					}

					@Override
					public void onError(Call arg0, Exception arg1) {

						Toast.makeText(AnswerActivity.this, "Çë¼ì²éÍøÂç", 0).show();

					}
				});

	}

	@Override
	protected void onResume() {
		// Ç¿ÖÆºáÆÁ
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}

		super.onResume();
	}

}
