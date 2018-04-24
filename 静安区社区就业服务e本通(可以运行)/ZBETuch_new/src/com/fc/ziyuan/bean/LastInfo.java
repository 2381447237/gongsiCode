package com.fc.ziyuan.bean;

//http://web.youli.pw:89/Json/Get_Resource_Survey_Last.aspx?SFZ=310108198004026642

//[{"sfz":"310108198004026642","mqzk":"有劳动收入","dqyx":"无需意向分类","mark":null,"master_id":4}]
public class LastInfo {

	private String sfz;
	private String mqzk;
	private String dqyx;
	private String mark;
	private int master_id;
	public String getSfz() {
		return sfz;
	}
	public void setSfz(String sfz) {
		this.sfz = sfz;
	}
	public String getMqzk() {
		return mqzk;
	}
	public void setMqzk(String mqzk) {
		this.mqzk = mqzk;
	}
	public String getDqyx() {
		return dqyx;
	}
	public void setDqyx(String dqyx) {
		this.dqyx = dqyx;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public int getMaster_id() {
		return master_id;
	}
	public void setMaster_id(int master_id) {
		this.master_id = master_id;
	}
	
	
	
}
