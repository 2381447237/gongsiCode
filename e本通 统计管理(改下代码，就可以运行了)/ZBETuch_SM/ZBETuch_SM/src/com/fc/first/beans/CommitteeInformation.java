package com.fc.first.beans;

import java.util.ArrayList;
import java.util.List;

public class CommitteeInformation extends Base_Static_Data {
	private String committeeId;
	private String committeeName;
	private String streetId;
	public CommitteeInformation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CommitteeInformation(String committeeId, String committeeName,
			String streetId) {
		super();
		this.committeeId = committeeId;
		this.committeeName = committeeName;
		this.streetId = streetId;
	}
	

	public String getCommitteeId() {
		return committeeId;
	}

	public void setCommitteeId(String committeeId) {
		this.committeeId = committeeId;
	}

	public String getCommitteeName() {
		return committeeName;
	}

	public void setCommitteeName(String committeeName) {
		this.committeeName = committeeName;
	}

	public String getStreetId() {
		return streetId;
	}

	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}

	@Override
	public String toString() {
		return committeeName;
	}

	@Override
	public Boolean Check_This(String s) {
		
		return this.streetId == s;
	}
	
	public List<CommitteeInformation> getCommitteeList(){
		List<CommitteeInformation> committee_list = new ArrayList<CommitteeInformation>();
		committee_list.add(new  CommitteeInformation("201","安源居委","8001"));
		committee_list.add(new  CommitteeInformation("202", "长安西居委", "8001"));
		committee_list.add(new  CommitteeInformation("203", "地梨港居委", "8001"));
		committee_list.add(new  CommitteeInformation("204", "蕃瓜弄第一居委", "8001"));
		committee_list.add(new  CommitteeInformation("205", "合兴里居委", "8001"));
		committee_list.add(new  CommitteeInformation("206", "和泰居委", "8001"));
		committee_list.add(new  CommitteeInformation("207", "恒通居委", "8001"));
		committee_list.add(new  CommitteeInformation("208", "华丰居委", "8001"));
		committee_list.add(new  CommitteeInformation("209", "华康居委", "8001"));
		committee_list.add(new  CommitteeInformation("210", "华盛居委", "8001"));
		committee_list.add(new  CommitteeInformation("211", "华新居委", "8001"));
		committee_list.add(new  CommitteeInformation("212", "普善路第一居委", "8001"));
		committee_list.add(new  CommitteeInformation("213", "荣福村居委", "8001"));
		committee_list.add(new  CommitteeInformation("214", "三层楼居委", "8001"));
		committee_list.add(new  CommitteeInformation("215", "沈家宅居委", "8001"));
		committee_list.add(new  CommitteeInformation("216", "铁路新村第二居委", "8001"));
		committee_list.add(new  CommitteeInformation("217", "铁路新村第一居委", "8001"));
		committee_list.add(new  CommitteeInformation("218", "新桥居委", "8001"));
		committee_list.add(new  CommitteeInformation("219", "中兴坊居委", "8001"));
		committee_list.add(new  CommitteeInformation("220", "和泰居委会", "8001"));
		committee_list.add(new  CommitteeInformation("239", "不明", "8001"));
		committee_list.add(new  CommitteeInformation("30", "657居委", "8006"));
		committee_list.add(new  CommitteeInformation("31", "799居委", "8006"));
		committee_list.add(new  CommitteeInformation("32", "北高居委", "8006"));
		committee_list.add(new  CommitteeInformation("33", "北市场居委", "8006"));
		committee_list.add(new  CommitteeInformation("35", "北唐居委", "8006"));
		committee_list.add(new  CommitteeInformation("36", "长春居委", "8006"));
		committee_list.add(new  CommitteeInformation("37", "文昌居委","8006"));
		committee_list.add(new  CommitteeInformation("38","东德居委","8006"));
		committee_list.add(new  CommitteeInformation("39","甘肃居委","8006"));
		committee_list.add(new  CommitteeInformation("40","国庆居委","8006"));
		committee_list.add(new  CommitteeInformation("41", "华安居委","8006"));
		committee_list.add(new  CommitteeInformation("42", "均益居委", "8006"));
		committee_list.add(new  CommitteeInformation("43", "开封居委", "8006"));
		committee_list.add(new  CommitteeInformation("44", "来安居委", "8006"));
		committee_list.add(new  CommitteeInformation("45", "老泰居委", "8006"));
		committee_list.add(new  CommitteeInformation("46", "龙吉居委", "8006"));
		committee_list.add(new  CommitteeInformation("47", "蒙古居委", "8006"));
		committee_list.add(new  CommitteeInformation("49", "南高居委", "8006"));
		committee_list.add(new  CommitteeInformation("50", "华兴南林居委", "8006"));
		committee_list.add(new  CommitteeInformation("51", "南唐居委", "8006"));
		committee_list.add(new  CommitteeInformation("52", "南星居委", "8006"));
		committee_list.add(new  CommitteeInformation("53", "曲阜居委", "8006"));
		committee_list.add(new  CommitteeInformation("54", "三生居委", ""));
		committee_list.add(new  CommitteeInformation("55", "顺庆居委", "8006"));
		committee_list.add(new  CommitteeInformation("56", "天保居委", "8006"));
		committee_list.add(new  CommitteeInformation("57", "图南居委", "8006"));
		committee_list.add(new  CommitteeInformation("58", "卫星居委", ""));
		committee_list.add(new  CommitteeInformation("59", "新泰居委", "8006"));
		committee_list.add(new  CommitteeInformation("60", "颐福居委", "8006"));
		committee_list.add(new  CommitteeInformation("61", "永顺居委", "8006"));
		committee_list.add(new  CommitteeInformation("62", "鼎盛长春坊居委", ""));
		committee_list.add(new  CommitteeInformation("63", "宝庆居委", "8006"));
		committee_list.add(new  CommitteeInformation("64", "长康居委", "8006"));
		committee_list.add(new  CommitteeInformation("240", "不明", "8006"));
		
		return committee_list;
		
	}
}
