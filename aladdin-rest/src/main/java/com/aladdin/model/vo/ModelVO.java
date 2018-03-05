package com.aladdin.model.vo;

import java.math.BigDecimal;
import java.util.List;
import com.aladdin.base.BaseVo;
import com.aladdin.model.entity.ModelMeta;

public class ModelVO extends BaseVo {

	private static final long serialVersionUID = 1L;
	
	private String id;

	private String name;

	private String abbreviation;

	private String brand;

	private String modelnumber;

	private Integer weight;

	private String unit;
	
	private String categoryId;

	private String style;

	private String color;

	private BigDecimal length;

	private BigDecimal width;

	private BigDecimal height;

	private String type;

	private String material;

	private Byte autoApply;

	private Double unitprice;

	private String description;

	private Byte comefrom;

	private Byte state;

	private Byte accessibility;

	private Byte status;

	private List<ModelMeta> metaDatas;
	
	private String thumb;

	private String skp;
	
	private String modelCode;
	
	private List<ModelSkpVo> skpvo;

	public String getId() {
		return id;
	}

	public String getThumb() {
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

	public void setId(String id) {
		this.id = id;
	}

	public void setCode(Integer code) {
		this.code = code;
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

	public Byte getAutoApply() {
		return autoApply;
	}

	public void setAutoApply(Byte autoApply) {
		this.autoApply = autoApply;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public Byte getComefrom() {
		return comefrom;
	}

	public void setComefrom(Byte comefrom) {
		this.comefrom = comefrom;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public Byte getAccessibility() {
		return accessibility;
	}

	public void setAccessibility(Byte accessibility) {
		this.accessibility = accessibility;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public List<ModelSkpVo> getSkpvo() {
		return skpvo;
	}

	public void setSkpvo(List<ModelSkpVo> skpvo) {
		this.skpvo = skpvo;
	}

}
