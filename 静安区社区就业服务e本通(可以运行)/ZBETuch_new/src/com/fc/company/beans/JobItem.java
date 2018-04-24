package com.fc.company.beans;

import java.io.Serializable;

public class JobItem implements Serializable {
	private int jobid;
	private String comname;
	private String jobname;
	private String jobno;
	private String eduname;
	private String startage;
	private String endage;
	private String recruitnums;
	private String modifydate;
	private String startsalary;
	private String endsalary;
	private String max_row;

	public String getMax_row() {
		return max_row;
	}

	public void setMax_row(String max_row) {
		this.max_row = max_row;
	}

	public JobItem() {
	}

	public JobItem(int jobid, String comname, String jobname, String jobno,
			String eduname, String startage, String endage, String recruitnums,
			String modifydate, String startsalary, String endsalary,
			String max_row) {
		super();
		this.jobid = jobid;
		this.comname = comname;
		this.jobname = jobname;
		this.jobno = jobno;
		this.eduname = eduname;
		this.startage = startage;
		this.endage = endage;
		this.recruitnums = recruitnums;
		this.modifydate = modifydate;
		this.startsalary = startsalary;
		this.endsalary = endsalary;
		this.max_row = max_row;
	}

	public int getJobid() {
		return jobid;
	}

	public void setJobid(int jobid) {
		this.jobid = jobid;
	}

	public String getComname() {
		return comname;
	}

	public void setComname(String comname) {
		this.comname = comname;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getJobno() {
		return jobno;
	}

	public void setJobno(String jobno) {
		this.jobno = jobno;
	}

	public String getEduname() {
		return eduname;
	}

	public void setEduname(String eduname) {
		this.eduname = eduname;
	}

	public String getStartage() {
		return startage;
	}

	public void setStartage(String startage) {
		this.startage = startage;
	}

	public String getEndage() {
		return endage;
	}

	public void setEndage(String endage) {
		this.endage = endage;
	}

	public String getRecruitnums() {
		return recruitnums;
	}

	public void setRecruitnums(String recruitnums) {
		this.recruitnums = recruitnums;
	}

	public String getModifydate() {
		return modifydate;
	}

	public void setModifydate(String modifydate) {
		this.modifydate = modifydate;
	}

	public String getStartsalary() {
		return startsalary;
	}

	public void setStartsalary(String startsalary) {
		this.startsalary = startsalary;
	}

	public String getEndsalary() {
		return endsalary;
	}

	public void setEndsalary(String endsalary) {
		this.endsalary = endsalary;
	}

	@Override
	public String toString() {
		return "JobItem [jobid=" + jobid + ", comname=" + comname
				+ ", jobname=" + jobname + ", jobno=" + jobno + ", eduname="
				+ eduname + ", startage=" + startage + ", endage=" + endage
				+ ", recruitnums=" + recruitnums + ", modifydate=" + modifydate
				+ ", startsalary=" + startsalary + ", endsalary=" + endsalary
				+ ", max_row=" + max_row + "]";
	}

}
