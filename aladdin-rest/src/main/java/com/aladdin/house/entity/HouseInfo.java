package com.aladdin.house.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "housestyle")
public class HouseInfo implements Serializable {

	private static final long serialVersionUID = 5950373433538249101L;
	private static String[] array = new String[]{"一居","二居","三居","四居","五居","其他"};
	@Id
	private Integer id;

	@Column(name = "community_name")
	private String communityName;

	@Column(name = "name")
	private String name;

	@Column(name = "house_type_id")
	private Integer houseTypeId;
	
	@Column(name = "attachment_uri")
	private String attachmenturi;//skp文件
	
	@Column(name="house_pic_uri")
	private String housePicUri;//png文件

	@Column(name="upload_uri")
	private String uploadUri;//用户上传的图片
	
	@Column(name = "designer_id")
	private String designerId;
	
	@Column(name = "area")
	private Float area;
	
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "location_id")
	private String locationId;
	
	@Column(name = "location_id1")
	private String locationId1;
	
	@Column(name = "location_id2")
	private String locationId2;
	
	private String location;
	
	private Integer state;
	
	private Integer status;
	
	@Column(name = "is_del")
	private Integer isDel;
	
	@Transient
	private String houseType;
	
	
	public String getLocationId1() {
		return locationId1;
	}

	public void setLocationId1(String locationId1) {
		this.locationId1 = locationId1;
	}

	public String getLocationId2() {
		return locationId2;
	}

	public void setLocationId2(String locationId2) {
		this.locationId2 = locationId2;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getHouseType() {
	
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getUploadUri() {
		return uploadUri;
	}

	public void setUploadUri(String uploadUri) {
		this.uploadUri = uploadUri;
	}
	
	
	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAttachmenturi() {
		return attachmenturi;
	}

	public void setAttachmenturi(String attachmenturi) {
		this.attachmenturi = attachmenturi;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(Integer houseTypeId) {
		if(houseTypeId!=null&&houseTypeId>0&&houseTypeId<=6){
			houseType = array[houseTypeId-1];
		}else{
			houseType = "";
		}
		this.houseTypeId = houseTypeId;
	}

	public String getDesignerId() {
		return designerId;
	}

	public void setDesignerId(String designerId) {
		this.designerId = designerId;
	}

	public Float getArea() {
		return area;
	}

	public void setArea(Float area) {
		this.area = area;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getHousePicUri() {
		return housePicUri;
	}

	public void setHousePicUri(String housePicUri) {
		this.housePicUri = housePicUri;
	}

}