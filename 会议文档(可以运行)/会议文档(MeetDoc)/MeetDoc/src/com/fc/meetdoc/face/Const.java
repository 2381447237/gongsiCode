package com.fc.meetdoc.face;

import java.io.File;

import android.os.Environment;

public interface Const {
	/**
	 * 文件保存路径
	 */
	String SAVEPATH=Environment.getExternalStorageDirectory()+File.separator+"MEETDOCS";
	
	String SAVEAPPPATH = Environment.getExternalStorageDirectory()+File.separator+"MEETDOCUPDATE";
	/**
	 * 端口号
	 */
	int HOSTPORT=8878;
	
	/**
	 * 本项目包名
	 */
	String PACKAGE_NAME="com.fc.meetdoc";
	
	/**
	 * preferences的名称
	 */
	String PREFERENCES_NAME = "meetdoc";
	
	/**
	 * 保存在preference中最后打开文件名的key
	 */
	String KEY_CURRENT_OPEN_FILE ="Current_Open_File";
}
