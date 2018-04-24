package com.fc.main.service;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class MenuService {
	public static List<Activity> menuActivity = new ArrayList<Activity>();
	public static List<Activity> menuinfoActivity = new ArrayList<Activity>();

	public static void addActivity(Activity activity) {
		for (int i = 0; i < menuActivity.size(); i++) {
			if (activity.getClass().getName()
					.equals(menuActivity.get(i).getClass().getName())) {
				menuActivity.remove(i);
			}
		}
		menuActivity.add(activity);
	}

	public static void exitMenuActivity() {
		for (Activity activity : menuActivity) {
			activity.finish();
		}
	}

	public static void addInfoActivity(Activity activity) {
		for (int i = 0; i < menuinfoActivity.size(); i++) {
			if (activity.getClass().getName()
					.equals(menuinfoActivity.get(i).getClass().getName())) {
				menuinfoActivity.remove(i);
			}
		}
		menuinfoActivity.add(activity);
	}

	public static void exitInfoMenuActivity() {
		for (Activity activity : menuinfoActivity) {
			activity.finish();
		}
	}

}
