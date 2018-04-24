package com.fc.first.beans;

import java.io.Serializable;

public class GetNews implements Serializable {
	private String Id;
	private String Title;
	private String Doc;
	private String Create_Time;
	private String Create_Staff;
	private String RecordCount;

	public GetNews() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetNews(String id, String title, String doc, String create_Time,
			String create_Staff, String recordCount) {
		super();
		Id = id;
		Title = title;
		Doc = doc;
		Create_Time = create_Time;
		Create_Staff = create_Staff;
		RecordCount = recordCount;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDoc() {
		return Doc;
	}

	public void setDoc(String doc) {
		Doc = doc;
	}

	public String getCreate_Time() {
		return Create_Time;
	}

	public void setCreate_Time(String create_Time) {
		Create_Time = create_Time;
	}

	public String getCreate_Staff() {
		return Create_Staff;
	}

	public void setCreate_Staff(String create_Staff) {
		Create_Staff = create_Staff;
	}

	public String getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(String recordCount) {
		RecordCount = recordCount;
	}

	@Override
	public String toString() {
		return "GetNews [Id=" + Id + ", Title=" + Title + ", Doc=" + Doc
				+ ", Create_Time=" + Create_Time + ", Create_Staff="
				+ Create_Staff + ", RecordCount=" + RecordCount + "]";
	}

}
