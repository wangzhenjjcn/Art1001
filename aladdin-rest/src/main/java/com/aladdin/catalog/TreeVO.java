package com.aladdin.catalog;

import java.io.Serializable;

import com.aladdin.model.entity.Model;

public class TreeVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String text;

	private boolean children;

	// custom Data
	private String a_attr;

	private String icon;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChildren() {
		return children;
	}

	public void setChildren(boolean children) {
		this.children = children;
	}

	public String getA_attr() {
		return a_attr;
	}

	public void setA_attr(String a_attr) {
		this.a_attr = a_attr;
	}

}
