package com.aladdin.member.entity;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 电商会员
 * @author hyy
 *
 * 2016年10月21日
 */
@Table(name="shop_member")
public class Member implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final static String STATE_ACTIVE = "active";
	public final static String STATE_LOCKED = "locked";
	public static final String TYPE1="yyyy-MM-dd HH:mm:ss";
	
	@Id
	@Column(name="member_id")
	private String id;

	@Column(name="member_name")
	private String name;
	
	@Column(name="member_truename")
	private String truename;
	
	@Column(name = "member_email")
	private String email;
	
	@Column(name = "member_mobile")
	private String mobile;
	
	@Column(name="member_passwd")
	private String password;
	
	@Column(name = "member_sex")
	private Integer sexCode;
	
	@Transient
	private String sex;
	
	@Transient
	private Integer status = 1;
	
	@Column(name="member_avatar")
	private String avater;
	
	
	@Column(name = "member_birthday")
	private Long memberBirthday;
	
	@Transient
	private String locationIds;
	
	public String getLocationIds() {
		return locationIds;
	}


	public void setLocationIds(String locationIds) {
		this.locationIds = locationIds;
	}


	public Long getMemberBirthday() {
		return memberBirthday;
	}
	
	
	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public void setMemberBirthday(Long memberBirthday) {
		this.memberBirthday = memberBirthday;
		if(memberBirthday!=null){
			Timestamp timestamp=new Timestamp(memberBirthday);
			
			SimpleDateFormat df = new SimpleDateFormat(TYPE1);
			String res = df.format(timestamp);
			this.birthday=res;			
		}
	}

	@Transient
	private String birthday;
	
	@Column(name = "member_areainfo")
	private String location;
	
	
	@Column(name="is_buy")
	private Byte isBuy;
	
	@Column(name="is_del")
	private Byte isDel;
	
	@Transient
	private String state = STATE_ACTIVE;
	
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

	
//	private Integer role;
	


	public Integer getSexCode() {
		return sexCode;
	}

	public void setSexCode(Integer sexCode) {
		this.sexCode = sexCode;
		if(sexCode==1){
			sex="男";
		}else if(sexCode==2){
			sex="女";
		}else{
			sex="";
		}
	}

	public String getAvater() {
		return avater;
	}

	public void setAvater(String avater) {
		this.avater = avater;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Byte getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(Byte isBuy) {
		this.isBuy = isBuy;
	}

	public Byte getIsDel() {
		return isDel;
	}

	public void setIsDel(Byte isDel) {
		this.isDel = isDel;
	}
	
}
