package entity;

import java.util.Map;

public class Task {
	private int taskId;
	private Map<String, Object> params;
	
	public static final int  TASK_SEND_GOOD=1;
	
	/**
	 * 底图
	 */
	public static final int MAINACTIVITY_UPDATE_IMG = 2;
	public static final int MAINACTIVITY_UPDATE_HAPPY = 3;
	public static final int MAINACTIVITY_UPDATE_WAV=4;
	public static final int SHOW_TOAST=5;
	public static final int SHOWTVACTIVITY_START=6;
	
	/**
	 * 接收文字
	 */
	public static final int SHOW_STR=7;
	
	/**
	 * 接收小图
	 */
	public static final int MAINACTIVITY_SHOW_PERSONIMG=8;
	
	/**
	 * 接收跑马灯
	 */
	public static final int MAINACTIVITY_SHOW_RUNSTR=9;
	
	/**
	 * 关闭跑马灯
	 */
	public static final int MAINACTIVITY_STOP_RUNSTR=10;
	
	/**
	 * 保存跑马灯
	 */
	public static final int MAINACTIVITY_SAVE_RUNSTR=11;
	
	/**
	 * 保存开机图
	 */
	public static final int MAINACTIVITY_SAVE_BG=12;
	
	/**
	 * 保存时间显示
	 */
	public static final int MAINACTIVITY_SAVE_SHOWTIME=13;
	
	public static final int SHOWTVACTIVITY_RE_STAR=15;
	
	public static final int FILELOAD=16;
	
	public static final int REAPLESE=17;

	
		
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
