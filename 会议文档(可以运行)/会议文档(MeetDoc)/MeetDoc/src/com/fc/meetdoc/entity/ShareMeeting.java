package com.fc.meetdoc.entity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ShareMeeting {
private SharedPreferences shared;
private Editor editor;
private Context context;
	public ShareMeeting(Context context) {
		this.context=context;
		shared=context.getSharedPreferences("meetdoc", Context.MODE_PRIVATE);
	}
//
	public void putIP(String IP) {
		editor=shared.edit();
		editor.putString("IP", IP);
		editor.commit();
	}
	public String getIP(){
		return shared.getString("IP", null);
	}
	//判断SetIP是否处在关闭状态
	public void putState(boolean state) {
		editor=shared.edit();
		editor.putBoolean("state",state);
		editor.commit();
	}
	public boolean getState(){
		return shared.getBoolean("state", false);
	}
	//MainActivity回传时记录position
	public void putSelectIP(int selectIP) {
		editor=shared.edit();
		editor.putInt("selectIP", selectIP);
		editor.commit();
	}
	public int getSelectIP(){
		return shared.getInt("selectIP", 0);
	}
}
