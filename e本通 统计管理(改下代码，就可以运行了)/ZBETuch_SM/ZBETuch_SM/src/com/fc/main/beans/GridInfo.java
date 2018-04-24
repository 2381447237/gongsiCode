package com.fc.main.beans;

/**
 * 主界面信息类
 * 
 * @author hxl
 * 
 */
public class GridInfo {
	
	private int imgId;

	public GridInfo(int imgId) {
		super();
		
		this.imgId = imgId;
	}


	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	@Override
	public String toString() {
		return "GridInfo [imgId=" + imgId + "]";
	}
	
}
