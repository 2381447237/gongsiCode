package com.example.companytask;

import java.util.Map;

public class CompanyTask {
	private int taskId;
	private Map<String, Object> params;
	
	public static final int UNVISITEDINFO=1;//待诊
	public static final int DIAGONSISINFO=2;//根据输入码获取诊断
	public static final int DRUGSELECTION=3;//根据输入码获取收费项目
	public static final int FREQUENCYINFO=4;//频率
	public static final int USAGEINFO=5;//用法
	public static final int DIAGNOSIS=6;//保存诊断
	public static final int PRESCRIBE=7;//保存新下达处方
	public static final int VISITEDINFO=8;
	public static final int HOMEVISITPRESCRIBEINFO=9;
	public static final int DELETEHOMEVISITPRESCRIBEINFO=10;
	public static final int HOMEVISITPRESCRIPTIONACTIVITY=11;
	public static final int HOMEDIAGNOSIS=12;
	public static final int HOMEPRESCRIBE=13;
	public static final int HOMEDELETEINFO=14;
	public static final int VISITNUMINFO=15;
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