package com.fc.main.tools;

import android.app.Application;
import android.content.Intent;

public class CrashApplication extends Application {

	public static Application my_Application;
	public static Intent service;
	public static Intent service2;
	public static Intent service3;

	@Override
	public void onCreate() {
		super.onCreate();
		my_Application = (Application) getApplicationContext();
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
	}

	public Application getMy_Application() {
		return my_Application;
	}

	public void setMy_Application(Application my_Application) {
		this.my_Application = my_Application;
	}
}
