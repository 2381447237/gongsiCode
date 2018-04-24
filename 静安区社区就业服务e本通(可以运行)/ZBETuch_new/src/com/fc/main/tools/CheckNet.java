package com.fc.main.tools;

import com.fc.first.views.LoadActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.TextView;

/**
 * 
 * @author hxl
 * 
 */
public class CheckNet {
	private static CheckNet instance = null;

	public static synchronized CheckNet getInstance() {
		if (instance == null) {
			instance = new CheckNet();
		}
		return instance;

	}

	/**
	 * 判断当前网络是否可用（通过网络管理器得到）
	 * 
	 * @param context
	 *            上下文
	 * @return 表示当前网络可用 false 表示当前网络不能用，需要先设置网络连接状态
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		} else {
			// 获得所有网络设备
			NetworkInfo[] netInfo = connectivityManager.getAllNetworkInfo();
			if (netInfo != null) {
				for (NetworkInfo networkInfo : netInfo) {
					if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 检查网络状态
	 * 
	 * @param context
	 */
	public static void checkNetwork(final LoadActivity context) {
		if (!isNetworkAvailable(context)) {

			TextView msg = new TextView(context);
			msg.setText("当前没有可以使用的网络，请先设置网络状态！");
			new AlertDialog.Builder(context)
					.setIcon(android.R.drawable.stat_sys_warning)
					.setTitle("网络状态提示")
					.setView(msg)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 跳转到系统的网络设置界面
									context.startActivity(new Intent(
											Settings.ACTION_WIRELESS_SETTINGS));
									context.finish();

								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									context.finish();
									System.exit(0);

								}
							}).create().show();
		}
	}
}
