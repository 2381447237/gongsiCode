package com.fc.main.beans;

import java.util.Map;

public class MainTask {
	private int taskId;
	private Map<String, Object> params;

	/**
	 * 得到单位性质
	 */
	public static final int ZAOPINQUERYACTIVITY_GETCBOCOMPROPERTY = 1;
	/**
	 * 得到文化程度
	 */
	public static final int ZAOPINQUERYACTIVITY_GET_CBOEDU = 2;
	/**
	 * 得到职业分类大类
	 */
	public static final int ZAOPINQUERYACTIVITY_GET_CBOZYFL = 3;
	/**
	 * 得到行业分类大类
	 */
	public static final int ZAOPINQUERYACTIVITY_GET_CBOINDUSTRYCLASS = 4;
	/**
	 * 得到工作性质
	 */
	public static final int ZAOPINQUERYACTIVITY_GET_CBOGZXZ = 5;

	/**
	 * 得到工作班时
	 */
	public static final int ZAOPINQUERYACTIVITY_GET_CBOGZBS = 6;

	/**
	 * 得到工作地区1
	 */
	public static final int ZAOPINQUERYACTIVITY_GET_CBOAREAINFO = 7;

	/**
	 * 得到职业分类小类
	 */
	public static final int ZAOPINQUERYACTIVITY_GET_CBOZYFLCHILD = 10;

	/**
	 * 得到工种类型
	 */
	public static final int ZAOPINQUERYACTIVITY_GET_CBOTYPEOFWORK = 11;
	/**
	 * 得到行业分类小类
	 */
	public static final int ZAOPINQUERYACTIVITY_GET_CBOINDUSTRYSMALL = 12;

	/**
	 * 政策问答界面得到政策类型
	 */
	public static final int MAINPOLICYACTIVITY_GET_POLICYTYPE = 13;

	/**
	 * 政策提问界面得到政策类型
	 */
	public static final int POLICYASKACTIVITY_GET_POLICYTYPE = 14;

	/**
	 * 文件下载界面得到政策类型
	 */
	public static final int FILEDOWNLOADACTIVITY_GET_POLICYTYPE = 15;

	/**
	 * 企业查询界面得到单位性质
	 */
	public static final int COMPANYQUERYACTIVITY_GET_COMPANYPROPERTY = 16;

	/**
	 * 企业查询界面得到联系人
	 */
	public static final int COMPANYQUERYACTIVITY_GET_CBOLINKMAN = 17;

	public MainTask(int taskId, Map<String, Object> params) {
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
