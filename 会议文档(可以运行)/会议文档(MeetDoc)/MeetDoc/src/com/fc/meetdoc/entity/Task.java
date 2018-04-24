package com.fc.meetdoc.entity;

import java.util.Map;

public class Task {
	private int taskId;
	private Map<String, Object> params;
	/**
	 * 按列表发送文件是否存在请求
	 */
	public static final int ISFILEEXISTLIST=1;
	
	/**
	 * 回答本地是否有此文件
	 */
	public static final int ANSWERISFILEEXIST=2;
	
	/**
	 * 发送文件
	 */
	public static final int SENDFILE=3;
	
	/**
	 * 发送ip列表
	 */
	public static final int SENDIPLIST=4;
	
	/**
	 * 向所有人发送ip列表
	 */
	public static final int SENDIPLISTTOALL=5;
	
	/**
	 * 提示已经是最新列表
	 */
	public static final int REMAIND=6;
	
	/**
	 * 刷新ip列表
	 */
	public static final int REFRESHIPLIST=7;
	/**
	 * 刷新主界面文件列表
	 */
	public static final int REFRESHFILELIST=8;
	/**
	 * 主页面显示下载进度条
	 */
	public static final int MAINACTIVITY_SHOW_PROGRESSDIALOG = 9;	
	/**
	 * 主页面改变下载进度条
	 */
	public static final int MAINACTIVITY_RUN_PROGRESSDIALOG =10;
	
	/**
	 * 主界面清空文件
	 */
	public static final int MAINACTIVITY_DELETE_ALL_FILES=11;
	
	/**
	 * 发送文件列表
	 */
	public static final int SEND_FILE_LIST = 12;
	
	public Task(int taskId, Map<String, Object> params) {
		this.taskId = taskId;
		this.params = params;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
}
