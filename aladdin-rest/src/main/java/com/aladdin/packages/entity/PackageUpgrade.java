package com.aladdin.packages.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 套餐升级
 * @author hyy
 * 2016年10月10日
 */
@Entity
@Table(name="a_package_upgrade")
public class PackageUpgrade implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6424850888576498773L;

	@Id
    private String id;
	
	@Column(name="package_name1")
    private String packageName1;

	@Column(name="package_name2")
    private String packageName2;

    private String zone;

    private Double differences;
    
    @Column(name="created_by")
    private String createdBy;
    
    @Column(name="created_date")
    private Date createdDate;

    @Column(name="modified_by")
    private String modifiedBy;

    @Column(name="modified_date")
    private Date modifiedDate;

    private Byte status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPackageName1() {
        return packageName1;
    }

    public void setPackageName1(String packageName1) {
        this.packageName1 = packageName1 == null ? null : packageName1.trim();
    }

    public String getPackageName2() {
        return packageName2;
    }

    public void setPackageName2(String packageName2) {
        this.packageName2 = packageName2 == null ? null : packageName2.trim();
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone == null ? null : zone.trim();
    }

    public Double getDifferences() {
        return differences;
    }

    public void setDifferences(Double differences) {
        this.differences = differences;
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
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy == null ? null : modifiedBy.trim();
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
	
}
