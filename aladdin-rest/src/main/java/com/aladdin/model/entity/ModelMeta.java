package com.aladdin.model.entity;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="a_models_metas")
public class ModelMeta {
	
	public ModelMeta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ModelMeta(String objectId, String key, String value) {
		super();
		this.objectId = objectId;
		this.key = key;
		this.value = value;
	}
	public ModelMeta(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Id
	private String id;
	
	@Column(name="object_id")
	private String objectId;
	
	@Column(name="meta_key")
	private String key;
	
	@Column(name="meta_value")
	private String value;
	
	@Column(name="meta_priority")
	private String priority;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
}
