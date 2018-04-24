package com.fc.personpolicy.beans;

import java.io.Serializable;

public class PolicyLevelItem implements Serializable {
	private int id;
	private String name;
	private int parent_id;
	private String stop;

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public PolicyLevelItem() {
	}

	public PolicyLevelItem(int id, String name, int parent_id) {
		this.id = id;
		this.name = name;
		this.parent_id = parent_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	@Override
	public String toString() {
		return "PolicyLevelItem [id=" + id + ", name=" + name + ", parent_id="
				+ parent_id + ", stop=" + stop + "]";
	}

}
