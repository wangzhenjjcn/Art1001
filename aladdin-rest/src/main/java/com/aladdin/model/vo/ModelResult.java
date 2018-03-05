package com.aladdin.model.vo;

import com.aladdin.base.Pager;
import com.aladdin.model.entity.Model;

public class ModelResult {
	private String query;//查询条件
	private String brand;//品牌
	private String category;//类别
	private String style;//风格
	private String color;//色系
	private String sortRule;//排序规则,asc,desc
	private Pager<Model> pager;
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSortRule() {
		return sortRule;
	}
	public void setSortRule(String sortRule) {
		this.sortRule = sortRule;
	}
	public Pager<Model> getPager() {
		return pager;
	}
	public void setPager(Pager<Model> pager) {
		this.pager = pager;
	}
}
