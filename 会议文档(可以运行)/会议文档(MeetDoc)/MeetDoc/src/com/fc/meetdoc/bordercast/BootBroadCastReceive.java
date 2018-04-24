package com.fc.meetdoc.bordercast;


import com.fc.meetdoc.views.MainActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadCastReceive extends BroadcastReceiver {
	private final String startAction = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(startAction)) {
			Intent ootStartIntent = new Intent(context, MainActivity.class);
			ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(ootStartIntent);
		}
	}

}
