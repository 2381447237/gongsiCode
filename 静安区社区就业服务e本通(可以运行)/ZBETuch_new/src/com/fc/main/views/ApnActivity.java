package com.fc.main.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

public class ApnActivity extends Activity {

	private WifiManager wifiManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		checkNetworkType(this);
		Intent intent = new Intent(Settings.ACTION_APN_SETTINGS);
		startActivity(intent);
		this.finish();
	}

	/**
	 * 检测当前使用的网络类型是WIFI还是WAP
	 * 
	 * @param context
	 */
	private void checkNetworkType(Context context) {
		NetworkInfo networkInfo = ((ConnectivityManager) context
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if (networkInfo != null) {
			if ("wifi".equals(networkInfo.getTypeName().toLowerCase())) {
				wifiManager.setWifiEnabled(false);
			}
		}
	}

}
