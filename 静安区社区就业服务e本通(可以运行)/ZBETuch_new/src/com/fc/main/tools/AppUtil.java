package com.fc.main.tools;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;

import com.fc.main.beans.AppInfo;

public class AppUtil {
	private Context context;

	public AppUtil(Context context) {
		super();
		this.context = context;
	}

	public List<AppInfo> getAppInfo() {
		List<AppInfo> appInfos = new ArrayList<AppInfo>();
		List<PackageInfo> infos = context.getPackageManager()
				.getInstalledPackages(0);
		for (PackageInfo packageInfo : infos) {
			AppInfo appInfo = new AppInfo();
			appInfo.setAppName(packageInfo.applicationInfo.loadLabel(
					context.getPackageManager()).toString());
			appInfo.setPackageName(packageInfo.packageName);
			appInfo.setVersionCode(String.valueOf(packageInfo.versionCode));
			appInfo.setVersionName(packageInfo.versionName);
			appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(context
					.getPackageManager()));
			appInfos.add(appInfo);
		}
		return appInfos;

	}

}
