package com.example.thirdlevelactivity;

import com.example.shopping.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

public class IdentityPhotoActivity extends Activity{

	private PhotoView photoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_identityphoto);
		
		photoView=(PhotoView) findViewById(R.id.pv_iphoto);
		photoView.setImageResource(R.drawable.logo);
		Intent intent=getIntent();
		if (TextUtils.equals(intent.getStringExtra("dPhoto"),"lfront")) {
			photoView.setImageResource(R.drawable.id1);
		} else if (TextUtils.equals(intent.getStringExtra("dPhoto"),
				"lback")) {
			photoView.setImageResource(R.drawable.id2);
		}
		photoView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//finish();
			}
		});
	}
	
}
