package com.aladdin.packages.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 套餐
 * @author hyy
 * 2016年10月10日
 */
@Table(name="a_packages")
@Entity
public class Packages implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7566018997686332221L;

	@Id
    private String id;

    private String name;

    private String style;

    private String area;

    private String unitprice;

	private String json;

    private String effect;
    
    @Column(name="created_by")
    private String createdBy;
    
    @Column(name="modified_date")
    private Date modifiedDate;

    private Byte status;
    
    @Column(name="created_date")
    private Date createdDate;
    
    @Column(name="modified_by")
    private String modifiedBy;

    private String skp;

    private String series;
    
    @Transient
    private String seriesJson;


	private Integer version;

    private Byte latest;


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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style == null ? null : style.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice == null ? null : unitprice.trim();
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json == null ? null : json.trim();
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect == null ? null : effect.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
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

    public String getSkp() {
        return skp;
    }

    public void setSkp(String skp) {
        this.skp = skp == null ? null : skp.trim();
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series == null ? null : series.trim();
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

	public String getSeriesJson() {
		return seriesJson;
	}

	public void setSeriesJson(String seriesJson) {
		this.seriesJson = seriesJson;
	}


}