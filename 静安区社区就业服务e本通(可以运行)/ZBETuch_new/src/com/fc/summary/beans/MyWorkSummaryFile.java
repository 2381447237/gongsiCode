package com.fc.summary.beans;

import android.graphics.Bitmap;

/**
 * 视频文件类
 * 
 * @author hxl
 * 
 */
public class MyWorkSummaryFile {
	public String fileName;// 文件名称
	public String fileSize;// 文件大小
	public String fileFormat;// 文件格式
	public Bitmap fileBitmap;// 文件缩略图
	public String filetime;// 视频文件播放时长
	public String filePath;// 视频文件路径

	public MyWorkSummaryFile() {
		super();
	}

	public MyWorkSummaryFile(String fileName, String fileSize,
			String fileFormat, Bitmap fileBitmap, String filetime,
			String filePath) {
		super();
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileFormat = fileFormat;
		this.fileBitmap = fileBitmap;
		this.filetime = filetime;
		this.filePath = filePath;
	}

	public String getFiletime() {
		return filetime;
	}

	public void setFiletime(String filetime) {
		this.filetime = filetime;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public Bitmap getFileBitmap() {
		return fileBitmap;
	}

	public void setFileBitmap(Bitmap fileBitmap) {
		this.fileBitmap = fileBitmap;
	}

	@Override
	public String toString() {
		return "MyWorkSummaryFile [fileName=" + fileName + ", fileSize="
				+ fileSize + ", fileFormat=" + fileFormat + ", fileBitmap="
				+ fileBitmap + "]";
	}

}
