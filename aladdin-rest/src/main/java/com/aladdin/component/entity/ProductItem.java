package com.aladdin.component.entity;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aladdin.catalog.service.CatalogService;
import com.aladdin.model.entity.Model;

/**
 * 配件产品项
 * 
 * @author daipengfei
 * 
 */
@Component
public class ProductItem implements Serializable {

	private static final long serialVersionUID = -5977440029138042518L;
	@Autowired
	CatalogService catalogService;
	
	private String catalogId = "";
	private String catalogName = "";
	private String formula = "";
	private String unit = "";
	private String thumb = "";
	private String guid = "";
	private String skp = "";
	private String isPackage = "";
	private String type = "accessory";
	private Model model;

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
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

	public String getIsPackage() {
		return isPackage;
	}

	public void setIsPackage(String isPackage) {
		this.isPackage = isPackage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
