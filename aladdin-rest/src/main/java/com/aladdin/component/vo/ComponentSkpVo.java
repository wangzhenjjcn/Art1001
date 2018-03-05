package com.aladdin.component.vo;

import java.util.List;

public class ComponentSkpVo {

	private String id;
	private String name;
	private boolean is_package= false;
	private String type = "component";
	private List<ComponentSkpVo2> children;
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
	public boolean isIs_package() {
		return is_package;
	}
	public void setIs_package(boolean is_package) {
		this.is_package = is_package;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<ComponentSkpVo2> getChildren() {
		return children;
	}
	public void setChildren(List<ComponentSkpVo2> children) {
		this.children = children;
	}

}
