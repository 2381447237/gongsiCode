package com.fc.person.beans;

import java.io.Serializable;

public class AttentionItem implements Serializable {
	private int id;
	private String staff;
	private String sfz;
	private String createTime;
	private String name;
	private int recordCount;
	private String deletel;

	public AttentionItem() {
	}

	public AttentionItem(int id, String staff, String sfz, String createTime,
			String name, int recordCount, String deletel) {
		this.id = id;
		this.staff = staff;
		this.sfz = sfz;
		this.createTime = createTime;
		this.name = name;
		this.recordCount = recordCount;
		this.deletel = deletel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public String getSfz() {
		return sfz;
	}

	public void setSfz(String sfz) {
		this.sfz = sfz;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public String getDeletel() {
		return deletel;
	}

	public void setDeletel(String deletel) {
		this.deletel = deletel;
	}

	@Override
	public String toString() {
		return "AttentionItem [id=" + id + ", staff=" + staff + ", sfz=" + sfz
				+ ", createTime=" + createTime + ", name=" + name
				+ ", recordCount=" + recordCount + ", deletel=" + deletel + "]";
	}

}
