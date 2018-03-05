package com.aladdin.component.vo;

import java.util.List;

import com.aladdin.base.BaseVo;
import com.aladdin.component.entity.Component;

public class ComponentCollectionVo extends BaseVo{
	
	private static final long serialVersionUID = 1L;
	
	private List<Component> components;

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}
	
}
