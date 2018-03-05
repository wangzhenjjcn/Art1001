//package com.aladdin.user.entity;
//
//import java.io.Serializable;
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Table(name = "users")
//public class User implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	public final static String STATE_ACTIVE = "active";
//	public final static String STATE_LOCKED = "locked";
//
//	@Id
//	private String id;
//
//	@Column(name = "name")
//	private String name;
//	
//	@Column(name = "email")
//	private String email;
//	
//	@Column(name = "mobile")
//	private String mobile;
//	
//	@Column(name = "password")
//	private String password;
//	
//	@Column(name = "sex")
//	private String sex;
//	
//	@Column(name = "birthday")
//	private Date birthday;
//	
//	@Column(name = "location")
//	private String location;
//	
//	@Column(name = "state")
//	private String state = STATE_ACTIVE;
//	
//	@Column(name = "created_by")
//	private String created_by;
//	
//	@Column(name = "created_date")
//	private String created_date;
//	
//	@Column(name = "modified_by")
//	private String modified_by;
//	
//	@Column(name = "modified_date")
//	private String modified_date;
//	
//	@Column(name = "status")
//	private Integer status = 1;
//	
//	private Integer role;
//	
//	private String avater;
//
//	public Integer getRole() {
//		return role;
//	}
//
//	public void setRole(Integer role) {
//		this.role = role;
//	}
//
//	public String getAvater() {
//		return avater;
//	}
//
//	public void setAvater(String avater) {
//		this.avater = avater;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getMobile() {
//		return mobile;
//	}
//
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getSex() {
//		return sex;
//	}
//
//	public void setSex(String sex) {
//		this.sex = sex;
//	}
//
//	public Date getBirthday() {
//		return birthday;
//	}
//
//	public void setBirthday(Date birthday) {
//		this.birthday = birthday;
//	}
//
//	public String getLocation() {
//		return location;
//	}
//
//	public void setLocation(String location) {
//		this.location = location;
//	}
//
//	public String getState() {
//		return state;
//	}
//
//	public void setState(String state) {
//		this.state = state;
//	}
//
//	public String getCreated_by() {
//		return created_by;
//	}
//
//	public void setCreated_by(String created_by) {
//		this.created_by = created_by;
//	}
//
//	public String getCreated_date() {
//		return created_date;
//	}
//
//	public void setCreated_date(String created_date) {
//		this.created_date = created_date;
//	}
//
//	public String getModified_by() {
//		return modified_by;
//	}
//
//	public void setModified_by(String modified_by) {
//		this.modified_by = modified_by;
//	}
//
//	public String getModified_date() {
//		return modified_date;
//	}
//
//	public void setModified_date(String modified_date) {
//		this.modified_date = modified_date;
//	}
//
//	public Integer getStatus() {
//		return status;
//	}
//
//	public void setStatus(Integer status) {
//		this.status = status;
//	}
//
//}
