package com.aladdin.component.vo;

import java.util.List;

import com.aladdin.base.BaseVo;
import com.aladdin.component.entity.ProductItem;

public class ComponentVo extends BaseVo {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private Integer type;
	private long priority;
	private Integer builtin;
	private String owner;
	
	private String extra;
	private List<ProductItem> items;
	
	
	public List<ProductItem> getItems() {
		return items;
	}

	public void setItems(List<ProductItem> items) {
		this.items = items;
	}

	public String getExtra() {
		return extra;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public long getPriority() {
		return priority;
	}

	public void setPriority(long priority) {
		this.priority = priority;
	}

	public Integer getBuiltin() {
		return builtin;
	}

	public void setBuiltin(Integer builtin) {
		this.builtin = builtin;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
}
