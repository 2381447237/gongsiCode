package com.fc.main.beans;

import java.util.Map;

public class PostMsgTask {
	private int taskId;
	private Map<String, Object> params;
	
	/**
	 * 全局推送信息
	 */
	public static final int ACTIVITY_GET_POSTMSG = 1;
	
	public PostMsgTask(int taskId, Map<String, Object> params) {
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
