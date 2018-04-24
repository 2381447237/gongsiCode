package com.example.hospitalapp;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.hospitalapp.R.id;
import com.example.hospitalapp.nonetwork.adapter.AnswerNonetWorkAdapter;
import com.example.hospitalapp.nonetwork.entity.AnswerNonetWorkContent;
import com.example.hospitalapp.sqlite.RecordNonetWorkContent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerNonetWorkActivity extends Activity {

	private List<AnswerNonetWorkContent> data = new ArrayList<AnswerNonetWorkContent>();
	public List<AnswerNonetWorkContent> answerlist = new ArrayList<AnswerNonetWorkContent>();
	private List<RecordNonetWorkContent> recordlist=new ArrayList<RecordNonetWorkContent>();
	private AnswerNonetWorkAdapter aAdapter;
	private AnswerListView lv;
	private String answerJson;
	private String[] temp = null;
    private LinearLayout ll_footer;
	private EditText et_name,et_phone;
    private RelativeLayout rl_header;
    private TextView tv_header,tv_header_title,tv_advise;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer_nonetwork);

		Intent intent=getIntent();
		
		lv = (AnswerListView) findViewById(R.id.lv_answer);
        lv.setVisibility(View.GONE);
		
		ll_footer=(LinearLayout) LayoutInflater.from(this).inflate(R.layout.footer_answer,null);
		
		lv.addFooterView(ll_footer);
		
		tv_advise=(TextView) findViewById(R.id.adviseTv);
		tv_advise.setVisibility(View.GONE);
		
		et_name=(EditText) ll_footer.findViewById(R.id.et_name_answer);
		et_phone=(EditText) ll_footer.findViewById(R.id.et_phone_answer);
		
		rl_header=(RelativeLayout) LayoutInflater.from(this).inflate(R.layout.header_answer,null);
		lv.addHeaderView(rl_header);
		
		tv_header_title=(TextView) rl_header.findViewById(R.id.header_title_tv);
		tv_header_title.setText(intent.getStringExtra("title_tv"));
		tv_header=(TextView) rl_header.findViewById(R.id.header_tv);
		
		if(!TextUtils.isEmpty(Html.fromHtml(intent.getStringExtra("header_tv")).toString().trim())){
			rl_header.setVisibility(View.VISIBLE);
			tv_header.setVisibility(View.VISIBLE);
			tv_header.setText(Html.fromHtml(intent.getStringExtra("header_tv")).toString().trim());
		}else{
			
			rl_header.setPadding(0,0,0,0);
			tv_header.setVisibility(View.GONE);
		}
		
		if (!TextUtils.isEmpty(FormList.downLoadData)) {

			temp = FormList.downLoadData.split("]");

			answerJson = (FormList.downLoadData.replace(temp[0] + "]", ""))
					.replace(temp[1] + "]", "");
			someNonetWorkAnswer(answerJson);
			lv.setVisibility(View.VISIBLE);
			tv_advise.setVisibility(View.GONE);
		}else{
			lv.setVisibility(View.GONE);
			tv_advise.setVisibility(View.VISIBLE);
		}

	
		answerlist = (List<AnswerNonetWorkContent>) intent.getSerializableExtra("answerlist");
		recordlist=(List<RecordNonetWorkContent>) intent.getSerializableExtra("recordlist");
		et_name.setText(recordlist.get(0).NAME);
		et_phone.setText(recordlist.get(0).PHONE);
	}

	private void someNonetWorkAnswer(final String myJson) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				Gson gson = new Gson();

				Type listType = new TypeToken<LinkedList<AnswerNonetWorkContent>>() {
				}.getType();

				LinkedList<AnswerNonetWorkContent> ac = gson.fromJson(myJson,
						listType);

				for (AnswerNonetWorkContent content : ac) {

					if (content.MASTERID == DetailedQuestionNonetWork.mId) {

						data.add(content);

					}
				}

				aAdapter = new AnswerNonetWorkAdapter(data,
						AnswerNonetWorkActivity.this);
				lv.setAdapter(aAdapter);

			}
		});

	}

	@Override
	protected void onResume() {

		// «ø÷∆∫·∆¡
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}

		super.onResume();
	}

}
