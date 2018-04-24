package com.example.thirdlevelactivity;

import java.util.ArrayList;
import java.util.List;

import com.example.adapters.ThirdlevelSizeAdapter;
import com.example.shopping.R;
import com.exmaple.infoclass.ThirdlevelSize;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

public class SizeActivity extends Activity implements OnClickListener{

	private ImageView iv_back;
	private ThirdlevelSizeAdapter tlsAdapter;
	private List<ThirdlevelSize> data=new ArrayList<ThirdlevelSize>();
	private ListView lv;
	private String sizes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_size);
		
		Intent intent=getIntent();
		sizes=intent.getStringExtra("sizes");
		initView();
		
	}

	private void initView(){
		
		iv_back=(ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);	
		lv=(ListView) findViewById(R.id.listview_activity_size);
		initData();
		tlsAdapter=new ThirdlevelSizeAdapter(data, this);
		lv.setAdapter(tlsAdapter);
	}
	
	private void initData(){	
		String b=",";
		
		int cnt = 0;
		int offset = 0;
		while ((offset = sizes.indexOf(b, offset)) != -1) {
			offset = offset + b.length();
			cnt++;
		}
		
		ThirdlevelSize tls=null;
		
		for (int i = 0; i < cnt+1; i++) {

			tls=new ThirdlevelSize();
			
			tls.sizeCategory="ÖÐ¹ú³ßÂë:"+sizes.split(b)[i];
			
			data.add(tls);
		}
		
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.iv_back:
			
			finish();
			
			break;

		default:
			break;
		}
		
	}

}
