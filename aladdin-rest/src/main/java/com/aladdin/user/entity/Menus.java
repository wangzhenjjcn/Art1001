package com.aladdin.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "menus")
public class Menus implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String name;

	@Column(name = "skp_id")
	private String skp_id;
	
	@Column(name = "img_url")
	private String img_url;
	
	@Column(name = "data_url")
	private String data_url;
	
	@Column(name = "status")
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSkp_id() {
		return skp_id;
	}

	public void setSkp_id(String skp_id) {
		this.skp_id = skp_id;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getData_url() {
		return data_url;
	}

	public void setData_url(String data_url) {
		this.data_url = data_url;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
