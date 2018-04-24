package com.example.companytask;

import java.util.Map;

public class CompanyTask {
	private int taskId;
	private Map<String, Object> params;
	
	public static final int SEATSINFO=1;//��Һ�б�
	public static final int INFUSIONORDERSINFO=2;//��ȡ���˻�����Ϣ
	public static final int GETSTART=3;//��ȡ��Һ��Ϣ
	public static final int DRIPSPEED=4;//���� 
	public static final int TUBES=5;//���� 
	public static final int INFUSIONSTART=6;//��ʼ��Һ
	public static final int INFUSIONRESTART=7;//����
	public static final int INFUSIONPAUSE=8;//ֹͣ��ǰ��Һ 
	public static final int INFUSIONFINISH=9;//��ɵ�ǰ��Һ
	
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