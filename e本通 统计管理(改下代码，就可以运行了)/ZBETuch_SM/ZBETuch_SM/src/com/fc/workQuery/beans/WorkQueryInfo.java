package com.fc.workQuery.beans;

public class WorkQueryInfo {

	int StreetId;
	String StreetName;
	int CommitteeId;
	String CommitteeName;
	
	public int getStreetId() {
		return StreetId;
	}
	public void setStreetId(int streetId) {
		StreetId = streetId;
	}
	public String getStreetName() {
		return StreetName;
	}
	public void setStreetName(String streetName) {
		StreetName = streetName;
	}
	public int getCommitteeId() {
		return CommitteeId;
	}
	public void setCommitteeId(int committeeId) {
		CommitteeId = committeeId;
	}
	public String getCommitteeName() {
		return CommitteeName;
	}
	public void setCommitteeName(String committeeName) {
		CommitteeName = committeeName;
	}
	
	public WorkQueryInfo(int streetId, String streetName, int committeeId,
			String committeeName) {
		super();
		this.StreetId = streetId;
		this.StreetName = streetName;
		this.CommitteeId = committeeId;
		this.CommitteeName = committeeName;
	}
	
	public WorkQueryInfo() {
		super();
	}	

	@Override
	public String toString() {
		return "WorkQueryInfo [StreetId=" + StreetId + ", StreetName="
				+ StreetName + ", CommitteeId=" + CommitteeId
				+ ", CommitteeName=" + CommitteeName + "]";
	}
}
