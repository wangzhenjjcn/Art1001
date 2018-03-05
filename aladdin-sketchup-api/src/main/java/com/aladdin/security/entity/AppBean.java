package com.aladdin.security.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

@Table(name = "pre_app")
public class AppBean {
	@Id
	private String id;
	@NotNull(message = "项目名称不能为空")
	@Length(max = 20, message = "项目名称过长")
	private String name;
	private Integer status;
	@Pattern(regexp = "[a-zA-Z0-9]{6,20}",message="项目编码只能为6-20个字母或数字")
	private String code;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
