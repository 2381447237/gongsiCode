package com.fc.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class BaseActivity extends Activity{
	
	private long exitTime = 0; 
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
	}
	 /**
	  * 隐藏输入框*/
	 public void DecorView(){
		 View view = getWindow().peekDecorView();
		  if (view != null) {
		     InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		      inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		    }
	 }
	 /**
	  *再按一次退出程序*/
	 public void exit() {
	        if ((System.currentTimeMillis() - exitTime) > 2000) {
	            Toast.makeText(getApplicationContext(), "再按一次退出程序",
	                    Toast.LENGTH_SHORT).show();
	            exitTime = System.currentTimeMillis();
	        } else {
	            finish();
	            System.exit(0);
	        }
	    }
}
