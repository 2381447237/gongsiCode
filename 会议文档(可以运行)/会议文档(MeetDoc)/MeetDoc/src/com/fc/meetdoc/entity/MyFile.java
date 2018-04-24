package com.fc.meetdoc.entity;

import java.io.Serializable;

public class MyFile implements Serializable, Comparable<MyFile> {
	private String name;
	private String absolutePath;
	private long lastModified;
	private boolean isShowCheckBox = false;
	private boolean isSelected = false;
	private long length;

	public MyFile() {
	}

	public MyFile(String name, String absolutePath, long lastModified,
			boolean isShowCheckBox, boolean isSelected, long length) {
		this.name = name;
		this.absolutePath = absolutePath;
		this.lastModified = lastModified;
		this.isShowCheckBox = isShowCheckBox;
		this.isSelected = isSelected;
		this.length = length;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public boolean isShowCheckBox() {
		return isShowCheckBox;
	}

	public void setShowCheckBox(boolean isShowCheckBox) {
		this.isShowCheckBox = isShowCheckBox;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "MyFile [name=" + name + ", absolutePath=" + absolutePath
				+ ", lastModified=" + lastModified + ", isShowCheckBox="
				+ isShowCheckBox + ", isSelected=" + isSelected + ", length="
				+ length + "]";
	}

	@Override
	public int compareTo(MyFile another) {
		if (this.lastModified == another.lastModified) {
			return 0;
		} else if (this.lastModified > another.lastModified) {
			return -1;
		} else {
			return 1;
		}
	}

}
