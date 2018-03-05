package com.aladdin.house.vo;

import com.aladdin.base.BaseVo;

public class HouseInfoVO extends BaseVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6859102674782744379L;

	private Integer id;
	
	private String communityName;
	
	private String name;
	
	private Integer houseTypeId;
	
	//private String attachmentId;
	
	private String housePicUri;
	
	private String uploadUri;

	private Float area;
	
	private String locationId;
	
	private String location;
	
	private Integer state;

	public Integer getId() {
		return id;
	}
	
	
	public String getUploadUri() {
		return uploadUri;
	}

	public void setUploadUri(String uploadUri) {
		this.uploadUri = uploadUri;
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
		this.houseTypeId = houseTypeId;
	}

/*	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}*/

	public Float getArea() {
		return area;
	}

	public void setArea(Float area) {
		this.area = area;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAttachmenturi() {
		return attachmenturi;
	}

	public void setAttachmenturi(String attachmenturi) {
		this.attachmenturi = attachmenturi;
	}


	public String getHousePicUri() {
		return housePicUri;
	}

	public void setHousePicUri(String housePicUri) {
		this.housePicUri = housePicUri;
	}


	private String attachmenturi;
}
