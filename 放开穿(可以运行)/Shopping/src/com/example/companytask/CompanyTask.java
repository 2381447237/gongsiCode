package com.example.companytask;

import java.util.Map;

public class CompanyTask {
	private int taskId;
	private Map<String, Object> params;
	
	public static final int FIRSTPAGEACTIVITY_GET_STAFF=1;//分类页面信息
	public static final int SHOPACTIVITY_INFO1=6;
	public static final int SHOPACTIVITY_INFO2=24;
	public static final int SHOPACTIVITY_LEFTINFO=23;
	public static final int SHOPACTIVITY_SIZEINFO=25;
	public static final int SHOPACTIVITY_CHOOSEINFO=26;
	public static final int COLLECT=2;
	public static final int COLLECTSHOW=3;
	public static final int COLLECTCANCEL=4;
	public CompanyTask(int taskId,Map<String, Object> params) {
		super();
		this.taskId = taskId;
		this.params=params;
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
//sfdsfd