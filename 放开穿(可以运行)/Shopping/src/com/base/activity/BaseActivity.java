package com.base.activity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
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
		//ActivityCollector.addActivity(this);
	}
	
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
	 @Override  
	    protected void onDestroy() {  
	        // TODO Auto-generated method stub  
	        super.onDestroy();  
	      //  ActivityCollector.finishAll();  
	    }  
	 
	 

}

