package com.aladdin.catalog.entity;

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
@Table(name="a_catalog")
public class Catalog implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4908371891165306693L;

	@Id
    private String id;

    private String name;

    private Byte type;
    
    @Column(name="export_type")
    private String exportType;

    private String parent;

    private Long priority;

    private Byte builtin;

    private String owner;
    
    @Column(name="created_by")
    private String createdBy;
    
    @Column(name="created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Transient
    private String createdDateStr;

    private Byte status;

    private String extra;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType == null ? null : exportType.trim();
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent == null ? null : parent.trim();
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Byte getBuiltin() {
        return builtin;
    }

    public void setBuiltin(Byte builtin) {
        this.builtin = builtin;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        if(createdDate!=null){
        	this.createdDateStr=DateUtils.dateToString(createdDate);
        }
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getCreatedDateStr() {
		return createdDateStr;
	}

}
