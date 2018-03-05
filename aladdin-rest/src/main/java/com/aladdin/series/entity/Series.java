package com.aladdin.series.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.aladdin.utils.DateUtils;

@Entity
@Table(name="a_series")
public class Series implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7766171548801464083L;

	@Id
	private String id;
	
	private String name;
	
	private String json;
	
	private Integer version;
	
	private Byte latest;
	
	@Column(name="create_by")
	private String createBy;
	
	@Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Transient
	private String createTimeStr;
	
	private Byte status;
	
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
		if(createTime!=null){
			this.createTimeStr=DateUtils.dateToString(createTime);
		}
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
	
	public String getCreateTimeStr() {
		return createTimeStr;
	}

	
}
