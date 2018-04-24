package com.example.thirdlevelactivity;

import com.example.shopping.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUsActivity extends Activity implements OnClickListener{

	private ImageView iv_back;
	private WebView wv;
	private String type;
	private TextView tv_aboutus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_aboutus);
		
		Intent intent=getIntent();
		type=intent.getStringExtra("type");
		
		tv_aboutus=(TextView) findViewById(R.id.tv_aboutus);
		
		iv_back=(ImageView) findViewById(R.id.img_aboutus_back);
		iv_back.setOnClickListener(this);
		wv=(WebView) findViewById(R.id.wv_aboutus);
		wv.setVerticalScrollBarEnabled(false);
		loadData();
		 
	}
	
	
	private void loadData(){
		
		if(TextUtils.equals("aboutus", type)){
			tv_aboutus.setText("关于我们");
			wv.loadUrl("file:///android_asset/aboutWo.html");
		}else if(TextUtils.equals("question", type)){
			tv_aboutus.setText("常见问题");
			wv.loadUrl("file:///android_asset/CommonIssue.html");
		}else if(TextUtils.equals("baozu", type)){
			tv_aboutus.setText("放开穿会员协议");
			wv.loadUrl("file:///android_asset/protocol.html");
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_aboutus_back:
			
			finish();
			
			break;

		default:
			break;
		}
	}

}
