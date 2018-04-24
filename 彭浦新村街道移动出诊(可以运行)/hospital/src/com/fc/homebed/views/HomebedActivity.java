package com.fc.homebed.views;

import com.example.hospital.R;
import com.example.hospital.R.layout;
import com.fc.helper.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class HomebedActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homebed);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		 if (keyCode == KeyEvent.KEYCODE_BACK) {
	            exit();
	           
	            return true;
	        }
		return super.onKeyDown(keyCode, event);
	}
	
}
