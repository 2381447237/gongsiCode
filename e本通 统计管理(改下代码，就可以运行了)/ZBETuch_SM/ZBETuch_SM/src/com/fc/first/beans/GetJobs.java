package com.fc.first.beans;

import java.io.Serializable;

public class GetJobs implements Serializable{
	private String Jobid;
	private String Comname;
	private String Jobname;
	private String Jobno;
	private String Eduname;
	private String Startage;
	private String Endage;
	private String Recruitnums;
	private String Modifydate;
	private String Startsalary;
	private String Endsalary;
	private String Max_row;
	
	public GetJobs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetJobs(String jobid, String comname, String jobname, String jobno,
			String eduname, String startage, String endage, String recruitnums,
			String modifydate, String startsalary, String endsalary,
			String max_row) {
		super();
		Jobid = jobid;
		Comname = comname;
		Jobname = jobname;
		Jobno = jobno;
		Eduname = eduname;
		Startage = startage;
		Endage = endage;
		Recruitnums = recruitnums;
		Modifydate = modifydate;
		Startsalary = startsalary;
		Endsalary = endsalary;
		Max_row = max_row;
	}

	public String getJobid() {
		return Jobid;
	}

	public void setJobid(String jobid) {
		Jobid = jobid;
	}

	public String getComname() {
		return Comname;
	}

	public void setComname(String comname) {
		Comname = comname;
	}

	public String getJobname() {
		return Jobname;
	}

	public void setJobname(String jobname) {
		Jobname = jobname;
	}

	public String getJobno() {
		return Jobno;
	}

	public void setJobno(String jobno) {
		Jobno = jobno;
	}

	public String getEduname() {
		return Eduname;
	}

	public void setEduname(String eduname) {
		Eduname = eduname;
	}

	public String getStartage() {
		return Startage;
	}

	public void setStartage(String startage) {
		Startage = startage;
	}

	public String getEndage() {
		return Endage;
	}

	public void setEndage(String endage) {
		Endage = endage;
	}

	public String getRecruitnums() {
		return Recruitnums;
	}

	public void setRecruitnums(String recruitnums) {
		Recruitnums = recruitnums;
	}

	public String getModifydate() {
		return Modifydate;
	}

	public void setModifydate(String modifydate) {
		Modifydate = modifydate;
	}

	public String getStartsalary() {
		return Startsalary;
	}

	public void setStartsalary(String startsalary) {
		Startsalary = startsalary;
	}

	public String getEndsalary() {
		return Endsalary;
	}

	public void setEndsalary(String endsalary) {
		Endsalary = endsalary;
	}

	public String getMax_row() {
		return Max_row;
	}

	public void setMax_row(String max_row) {
		Max_row = max_row;
	}

	@Override
	public String toString() {
		return "GetJobs [Jobid=" + Jobid + ", Comname=" + Comname
				+ ", Jobname=" + Jobname + ", Jobno=" + Jobno + ", Eduname="
				+ Eduname + ", Startage=" + Startage + ", Endage=" + Endage
				+ ", Recruitnums=" + Recruitnums + ", Modifydate=" + Modifydate
				+ ", Startsalary=" + Startsalary + ", Endsalary=" + Endsalary
				+ ", Max_row=" + Max_row + "]";
	}
		
}
