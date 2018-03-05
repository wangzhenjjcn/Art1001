package com.aladdin.city.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="adcode")
public class City implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@Column(name="adcode")
	private String code;
	
	@Column(name="cname")
	private String name;
	
	@Column(name="padcode")
	private String parentId;
	
	@Column(name="arealevel")
	private String level;

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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
}
