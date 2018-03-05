package com.aladdin.series.entity;

import java.io.Serializable;
import java.util.Date;
import com.aladdin.base.BaseVo;

public class SeriesVo extends BaseVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2121895324951273590L;

	private String id;
	
	private String name;
	
	private String json;
	
	private Integer version;
	
	private Byte latest;
	
	
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

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Byte getLatest() {
		return latest;
	}

	public void setLatest(Byte latest) {
		this.latest = latest;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	private String createBy;
	
	private Date createTime;
	
	private String createTimeStr;
	
	private Byte status;
	
}
