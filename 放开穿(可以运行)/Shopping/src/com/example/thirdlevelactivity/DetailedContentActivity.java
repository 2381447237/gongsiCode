package com.example.thirdlevelactivity;

import com.example.shopping.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class DetailedContentActivity extends Activity implements OnClickListener{

private ImageView iv_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dcontent);
		
		iv_back=(ImageView) findViewById(R.id.iv_back_dcontent);
		iv_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.iv_back_dcontent:
			
			finish();
			
			break;

		default:
			break;
		}
		
	}
	
}
