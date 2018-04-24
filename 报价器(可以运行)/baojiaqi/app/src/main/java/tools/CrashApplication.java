package tools;

import android.app.Application;

import com.example.fc_android_bj_new.MainActivity;

public class CrashApplication extends Application{

	MainActivity activity;

	@Override
	public void onCreate(){
		super.onCreate();
		CrashHandler crashHandler = CrashHandler.getInstance();
		//crashHandler.init(getApplicationContext());
		crashHandler.init(MainActivity.instance);
	}
}
