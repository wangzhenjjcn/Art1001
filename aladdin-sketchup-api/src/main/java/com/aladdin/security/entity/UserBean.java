package com.aladdin.security.entity;

import javax.persistence.Table;

@Table(name = "pre_user")
public class UserBean {

	private String id;
	private String name;
	private Integer status;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
