package com.youli.bedinfo;
import com.youli.bedinfo.fragment.FragmentChuangwei;
import com.youli.bedinfo.fragment.FragmentHuli;
import com.youli.bedinfo.fragment.FragmentJiankang;
import com.youli.bedinfo.fragment.FragmentTixing;
import com.youli.bedinfo.fragment.FragmentZizhu;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		FragmentManager fm=this.getSupportFragmentManager();
		FragmentTransaction ft=fm.beginTransaction();
		
		FragmentChuangwei fcw=new FragmentChuangwei();
		
		ft.add(R.id.fl,fcw);
		
		ft.commit();
		 
	}


	public void onGoTo(View v){
		
		FragmentManager fm=this.getSupportFragmentManager();
		
		FragmentTransaction ft=fm.beginTransaction();
		
		switch (v.getId()) {
		case R.id.rb_1:
			
			FragmentChuangwei fcw=new FragmentChuangwei();
			
			ft.replace(R.id.fl, fcw);
			
			ft.commit();
			
			break;

		case R.id.rb_2:
			
			FragmentHuli fhl=new FragmentHuli();
			
			ft.replace(R.id.fl,fhl);
			
			ft.addToBackStack(null);
			
			ft.commit();
			
			break;
			
		case R.id.rb_3:
			
			FragmentZizhu fzz=new FragmentZizhu();
			
			ft.replace(R.id.fl,fzz);
			
			ft.addToBackStack(null);
			
			ft.commit();
			
			break;
		case R.id.rb_4:
			
			FragmentTixing ftx=new FragmentTixing();
			
			ft.replace(R.id.fl,ftx);
			
			ft.addToBackStack(null);
			
			ft.commit();
			
			break;
		case R.id.rb_5:
			
			FragmentJiankang fjk=new FragmentJiankang();
			
			ft.replace(R.id.fl,fjk);
			
			ft.addToBackStack(null);
			
			ft.commit();
			
			break;
		default:
			break;
		}
		
	}
	long firstTime;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if (System.currentTimeMillis() - firstTime > 2000) {

				firstTime = System.currentTimeMillis();

				Toast.makeText(MainActivity.this, "请再按一次返回", 0).show();

			} else {

				android.os.Process.killProcess(android.os.Process.myPid());

			}

		}

		return true;
	}
}
