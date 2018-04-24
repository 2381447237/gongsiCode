package com.fc.person.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TypeInquiry implements Serializable {
	private int id;
	private String name;

	public TypeInquiry() {
		super();
	}

	public TypeInquiry(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return name;
	}

	public List<TypeInquiry> findAll() {
		List<TypeInquiry> list = new ArrayList<TypeInquiry>();
		list.add(new TypeInquiry(0, "请选择"));
		list.add(new TypeInquiry(1, "全部"));
		list.add(new TypeInquiry(2, "失业"));
		list.add(new TypeInquiry(3, "无业"));
		list.add(new TypeInquiry(4, "启航"));
		return list;
	}
}
