package com.aladdin.security.entity;

import javax.persistence.Table;

@Table(name = "pre_app_user")
public class AppUserBean {

	private Integer id;
	private String appid;
	private String userid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
