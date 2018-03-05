package com.aladdin.model.vo;

import java.io.Serializable;

public class ModelSkpVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String guid;
	
	private String name;
	
	private String brand;
	
	private String thumb;
	
	private String skp;
	
	@SuppressWarnings("unused")
	private String zone;
	
	private String product_mode;
	
	private String unit;
	
	private String stage;
	
	private String project_category = "天棚工程";
	
	private String unitprice_man = "0.0";
	
	private String unitprice_material="0.0";
	
	private String transport_expenses="0.0";
	
	private String install_expenses="0.0";
	
	private String wastage_rate="0.0";
	
	private String specification="1";
	
	private String price="11.0";
	
	private String export_type="construction";
	
	private String model_type="normal";
	
	private String material_type = "none";
	
	private Boolean auto_apply = false;
	
	@SuppressWarnings("unused")
	private ModelCategorySkpVo categories_path;

	public String getGuid() {
		return guid;
	}

	public ModelCategorySkpVo getCategories_path() {
		return categories_path;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
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

	public String getZone() {
		return "未知";
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getExport_type() {
		return export_type;
	}

	public void setExport_type(String export_type) {
		this.export_type = export_type;
	}

	public String getModel_type() {
		return model_type;
	}

	public void setModel_type(String model_type) {
		this.model_type = model_type;
	}

	public Boolean getAuto_apply() {
		return auto_apply;
	}

	public void setAuto_apply(Boolean auto_apply) {
		this.auto_apply = auto_apply;
	}

/*	public ModelCategorySkpVo getCategories_path() {
		ModelCategorySkpVo vo1 = new ModelCategorySkpVo();
		vo1.setGuid("292");
		vo1.setName("厨房系统");
		
		ModelCategorySkpVo2 vo2 = new ModelCategorySkpVo2();
		vo2.setGuid("293");
		vo2.setName("整体橱柜");
		
		
		ModelCategorySkpVo3 vo3 = new ModelCategorySkpVo3();
		vo3.setGuid("296");
		vo3.setName("橱柜配件");
		
		vo2.setChildren(vo3);
		
		vo1.setChildren(vo2);
		
		return vo1;
	}*/

	public void setCategories_path(ModelCategorySkpVo categories_path) {
		this.categories_path = categories_path;
	}

	public String getMaterial_type() {
		return material_type;
	}

	public void setMaterial_type(String material_type) {
		this.material_type = material_type;
	}

}
