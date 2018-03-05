package com.aladdin.member.entity;

import com.aladdin.base.BaseVo;

public class MemberVo extends BaseVo{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -1252474817502010570L;

	private String id;

	private String name;
	
	private String email;
	
	private String mobile;
	
	private String sex;
	
	private String avater;
	
	private String birthday;
	
	private String location;
	
	private Byte isBuy;
	
	private String state;

	private String truename;
	private String locationIds;
	
	public String getLocationIds() {
		return locationIds;
	}

	public void setLocationIds(String locationIds) {
		this.locationIds = locationIds;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAvater() {
		return avater;
	}

	public void setAvater(String avater) {
		this.avater = avater;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Byte getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(Byte isBuy) {
		this.isBuy = isBuy;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
}
