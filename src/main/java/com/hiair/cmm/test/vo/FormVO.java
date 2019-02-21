package com.hiair.cmm.test.vo;

import java.util.List;

public class FormVO {
	private String id;
	private String name;
	private List<?> testTags;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<?> getTestTags() {
		return testTags;
	}
	public void setTestTags(List<?> testTags) {
		this.testTags = testTags;
	}
	
	@Override
	public String toString() {
		return "FormVO [id=" + id + ", name=" + name + ", testTags=" + testTags + "]";
	}
	
}
