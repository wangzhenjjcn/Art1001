package com.aladdin.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "a_models")
public class Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9147670596076940703L;

	@Id
	private String id;
	@Column(name = "code")
	private String modelCode;

	private String name;

	private String abbreviation;

	private String brand;

	private String modelnumber;

	private Integer weight;

	private String unit;
	@Column(name = "category_id")
	private String categoryId;
	
	@Column(name = "category_id1")
	private String categoryId1;
	
	@Column(name = "category_id2")
	private String categoryId2;

	private String style;

	private String color;

	private BigDecimal length;

	private BigDecimal width;

	private BigDecimal height;

	private String type;

	private String material;

	@Column(name = "auto_apply")
	private Integer autoApply;

	private Double unitprice;

	private String description;

	private Integer comefrom;

	private Integer state;

	private Integer accessibility;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	private Date modifiedDate;

	private Integer status;

	private String thumb;

	private String skp;

	@Transient
	private List<ModelMeta> metaDatas;

	public String getCategoryId1() {
		return categoryId1;
	}

	public void setCategoryId1(String categoryId1) {
		this.categoryId1 = categoryId1;
	}

	public String getCategoryId2() {
		return categoryId2;
	}

	public void setCategoryId2(String categoryId2) {
		this.categoryId2 = categoryId2;
	}

	@Transient
	private String mfs;// 模型功能id串
	
	
	
	

	public String getMfs() {
		return mfs;
	}

	public void setMfs(String mfs) {
		this.mfs = mfs;
	}

	public String getThumb() {
		if (this.thumb == null)
			this.thumb = "";
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getSkp() {
		return skp;
	}

	public void setSkp(String skp) {
		this.skp = skp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation == null ? null : abbreviation.trim();
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public List<ModelMeta> getMetaDatas() {
		return metaDatas;
	}

	public void setMetaDatas(List<ModelMeta> metaDatas) {
		this.metaDatas = metaDatas;
	}

	public String getModelnumber() {
		return modelnumber;
	}

	public void setModelnumber(String modelnumber) {
		this.modelnumber = modelnumber == null ? null : modelnumber.trim();
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId == null ? null : categoryId.trim();
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style == null ? null : style.trim();
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color == null ? null : color.trim();
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material == null ? null : material.trim();
	}

	public Integer getAutoApply() {
		return autoApply;
	}

	public void setAutoApply(Integer autoApply) {
		this.autoApply = autoApply;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public Integer getComefrom() {
		return comefrom;
	}

	public void setComefrom(Integer comefrom) {
		this.comefrom = comefrom;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getAccessibility() {
		return accessibility;
	}

	public void setAccessibility(Integer accessibility) {
		this.accessibility = accessibility;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

}