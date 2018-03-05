package com.aladdin.component.vo;

import java.util.List;

import com.aladdin.component.entity.ProductItem;

public class ComponentSkpVo2 {

	private String id = "";//catalogId
	private String name = "";//catalogName
	private String formula = "";
	private String unit = "";
	private String thumb = "";
	private String guid = "";
	private String skp = "";
	private boolean isPackage = false;
	private String type = "accessory";
	
	private String brand;
	private String zone = "未知";
	private String product_mode = "";
	private String stage = "";
	private String project_category = "";
	private String unitprice_man = "";
	private String unitprice_material ="";
	private String transport_expenses = "";
	private String install_expenses = "";
	private String wastage_rate = "";
	private Double price;
	private String export_type = "";
	private String specification ="";
	
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getProduct_mode() {
		return product_mode;
	}
	public void setProduct_mode(String product_mode) {
		this.product_mode = product_mode;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getProject_category() {
		return project_category;
	}
	public void setProject_category(String project_category) {
		this.project_category = project_category;
	}
	public String getUnitprice_man() {
		return unitprice_man;
	}
	public void setUnitprice_man(String unitprice_man) {
		this.unitprice_man = unitprice_man;
	}
	public String getUnitprice_material() {
		return unitprice_material;
	}
	public void setUnitprice_material(String unitprice_material) {
		this.unitprice_material = unitprice_material;
	}
	public String getTransport_expenses() {
		return transport_expenses;
	}
	public void setTransport_expenses(String transport_expenses) {
		this.transport_expenses = transport_expenses;
	}
	public String getInstall_expenses() {
		return install_expenses;
	}
	public void setInstall_expenses(String install_expenses) {
		this.install_expenses = install_expenses;
	}
	public String getWastage_rate() {
		return wastage_rate;
	}
	public void setWastage_rate(String wastage_rate) {
		this.wastage_rate = wastage_rate;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getExport_type() {
		return export_type;
	}
	public void setExport_type(String export_type) {
		this.export_type = export_type;
	}
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
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getSkp() {
		return skp;
	}
	public void setSkp(String skp) {
		this.skp = skp;
	}
	public boolean getIsPackage() {
		return isPackage;
	}
	public void setIsPackage(boolean isPackage) {
		this.isPackage = isPackage;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
