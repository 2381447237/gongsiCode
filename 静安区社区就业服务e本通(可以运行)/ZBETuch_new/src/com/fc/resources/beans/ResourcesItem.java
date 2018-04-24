package com.fc.resources.beans;

public class ResourcesItem {
	private int sum_value_man;// 男失业总数
	private int sum_value_woman;
	private String type;
	private String committee_id;// 居委ID
	private String name;// 显示名称
	private int order_id;
	private int all;// 失业总人数

	public ResourcesItem() {
	}

	public ResourcesItem(int sum_value_man, int sum_value_woman, String type,
			String committee_id, String name, int order_id, int all) {
		this.sum_value_man = sum_value_man;
		this.sum_value_woman = sum_value_woman;
		this.type = type;
		this.committee_id = committee_id;
		this.name = name;
		this.order_id = order_id;
		this.all = all;
	}

	public int getSum_value_man() {
		return sum_value_man;
	}

	public void setSum_value_man(int sum_value_man) {
		this.sum_value_man = sum_value_man;
	}

	public int getSum_value_woman() {
		return sum_value_woman;
	}

	public void setSum_value_woman(int sum_value_woman) {
		this.sum_value_woman = sum_value_woman;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCommittee_id() {
		return committee_id;
	}

	public void setCommittee_id(String committee_id) {
		this.committee_id = committee_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getAll() {
		return all;
	}

	public void setAll(int all) {
		this.all = all;
	}

	@Override
	public String toString() {
		return "ResourcesItem [sum_value_man=" + sum_value_man
				+ ", sum_value_woman=" + sum_value_woman + ", type=" + type
				+ ", committee_id=" + committee_id + ", name=" + name
				+ ", order_id=" + order_id + ", all=" + all + "]";
	}

}
