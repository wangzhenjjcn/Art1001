package com.aladdin.model.vo;

import java.io.Serializable;

public class ModelCategorySkpVo2 implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private String guid;
	
	private String name;
	
	private ModelCategorySkpVo3 children;

	public String getGuid() {
		return guid;
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

	public ModelCategorySkpVo3 getChildren() {
		return children;
	}

	public void setChildren(ModelCategorySkpVo3 children) {
		this.children = children;
	}
	

}
