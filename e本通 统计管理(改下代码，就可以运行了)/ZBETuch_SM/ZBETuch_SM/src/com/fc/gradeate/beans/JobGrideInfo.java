package com.fc.gradeate.beans;

import java.io.Serializable;

public class JobGrideInfo implements Serializable {

	private int id;
	private int master_id;
	private String check_guide;
	private String guide_date;
	private String guide_name;
	private String guide_doc;
	private String check_recommend;
	private String recommend_date;
	private String recommend_com;
	private String recommend_job;
	private String create_date;
	private int creat_staff;
	private String update_date;
	private int update_staff;
	private int recordCount;

	public JobGrideInfo() {
		
	}

	public JobGrideInfo(int id, int master_id, String check_guide,
			String guide_date, String guide_name, String guide_doc,
			String check_recommend, String recommend_date,
			String recommend_com, String recommend_job, String create_date,
			int creat_staff, String update_date, int update_staff,
			int recordCount) {
		this.id = id;
		this.master_id = master_id;
		this.check_guide = check_guide;
		this.guide_date = guide_date;
		this.guide_name = guide_name;
		this.guide_doc = guide_doc;
		this.check_recommend = check_recommend;
		this.recommend_date = recommend_date;
		this.recommend_com = recommend_com;
		this.recommend_job = recommend_job;
		this.create_date = create_date;
		this.creat_staff = creat_staff;
		this.update_date = update_date;
		this.update_staff = update_staff;
		this.recordCount = recordCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMaster_id() {
		return master_id;
	}

	public void setMaster_id(int master_id) {
		this.master_id = master_id;
	}

	public String getCheck_guide() {
		return check_guide;
	}

	public void setCheck_guide(String check_guide) {
		this.check_guide = check_guide;
	}

	public String getGuide_date() {
		return guide_date;
	}

	public void setGuide_date(String guide_date) {
		this.guide_date = guide_date;
	}

	public String getGuide_name() {
		return guide_name;
	}

	public void setGuide_name(String guide_name) {
		this.guide_name = guide_name;
	}

	public String getGuide_doc() {
		return guide_doc;
	}

	public void setGuide_doc(String guide_doc) {
		this.guide_doc = guide_doc;
	}

	public String getCheck_recommend() {
		return check_recommend;
	}

	public void setCheck_recommend(String check_recommend) {
		this.check_recommend = check_recommend;
	}

	public String getRecommend_com() {
		return recommend_com;
	}

	public void setRecommend_com(String recommend_com) {
		this.recommend_com = recommend_com;
	}

	public String getRecommend_job() {
		return recommend_job;
	}

	public void setRecommend_job(String recommend_job) {
		this.recommend_job = recommend_job;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public int getCreat_staff() {
		return creat_staff;
	}

	public void setCreat_staff(int creat_staff) {
		this.creat_staff = creat_staff;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public int getUpdate_staff() {
		return update_staff;
	}

	public void setUpdate_staff(int update_staff) {
		this.update_staff = update_staff;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public String getRecommend_date() {
		return recommend_date;
	}

	public void setRecommend_date(String recommend_date) {
		this.recommend_date = recommend_date;
	}

	@Override
	public String toString() {
		return "JobGrideInfo [id=" + id + ", master_id=" + master_id
				+ ", check_guide=" + check_guide + ", guide_date=" + guide_date
				+ ", guide_name=" + guide_name + ", guide_doc=" + guide_doc
				+ ", check_recommend=" + check_recommend + ", recommend_date="
				+ recommend_date + ", recommend_com=" + recommend_com
				+ ", recommend_job=" + recommend_job + ", create_date="
				+ create_date + ", creat_staff=" + creat_staff
				+ ", update_date=" + update_date + ", update_staff="
				+ update_staff + ", recordCount=" + recordCount + "]";
	}

}
