package com.example.hospitalapp.receiver;

import com.example.hospitalapp.FormList;
import com.example.hospitalapp.MainActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.View;

public class ConnectionChangeReceiver extends BroadcastReceiver{

	FormList myContext;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		myContext=(FormList) context;
				
		if(isNetworkAvailable(myContext)){
			myContext.srl.setVisibility(View.VISIBLE);
			myContext.lv.setVisibility(View.VISIBLE);
			myContext.noNetSrl.setVisibility(View.GONE);
			myContext.noNetlv.setVisibility(View.GONE);
		}else{
			myContext.srl.setVisibility(View.GONE);
			myContext.lv.setVisibility(View.GONE);
			
			if(!myContext.file.exists()){
				myContext.noNetSrl.setVisibility(View.GONE);
				myContext.noNetlv.setVisibility(View.GONE);
				myContext.tv_advise.setVisibility(View.VISIBLE);
			}else{			
				myContext.noNetSrl.setVisibility(View.VISIBLE);
				myContext.noNetlv.setVisibility(View.VISIBLE);
				myContext.tv_advise.setVisibility(View.GONE);
			}
			
		}
		
	}

	private boolean isNetworkAvailable(FormList activity){
		Context context = activity.getApplicationContext();

		// ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ���
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {

			return false;

		} else {

			// ��ȡNetworkInfo����
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0) {

				for (int i = 0; i < networkInfo.length; i++) {
					/*
					 * System.out.println(i + "===״̬===" +
					 * networkInfo[i].getState()); System.out.println(i +
					 * "===����===" + networkInfo[i].getTypeName());
					 */
					// �жϵ�ǰ����״̬�Ƿ�Ϊ����״̬
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						
						// �жϵ�ǰ��wifi��ip�ͷ�������ip�Ƿ���ͬ
						// //=======================================================================

						// ��ȡwifi����
						WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
						// �ж�wifi�Ƿ���
						if (!wifiManager.isWifiEnabled()) {
							wifiManager.setWifiEnabled(true);
						}

						WifiInfo wifiInfo = wifiManager.getConnectionInfo();
						int ipAddress = wifiInfo.getIpAddress();
						String ip = intToIp(ipAddress);
						//Toast.makeText(this, "��ǰipΪ" + ip, 0).show();
						// �жϵ�ǰ��wifi��ip�ͷ�������ip�Ƿ���ͬ
					//	if (!ip.equals("10.0.3.15")||!ip.equals("192.168.11.116")) {
                    //							if (!ip.equals("10.0.3.15")&&!ip.equals(MainActivity.myIpAddress)) {
                    //						
                    //							return false;
                    //						}

						// //=======================================================================
						
						return true;
					}
				}

			}

		}

		return false;
	}
	
	
	// �õ���ǰ��wifiIP��ַ
			private String intToIp(int i) {

				return (i & 0Xff) + "." + ((i >> 8) & 0Xff) + "." + ((i >> 16) & 0xff)
						+ "." + (i >> 24 & 0xff);

			}
	
}
