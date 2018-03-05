package com.aladdin.catalog.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.aladdin.base.BaseVo;
import com.aladdin.model.entity.Model;
import com.aladdin.model.entity.ModelFunction;
import com.aladdin.model.entity.ModelMeta;

public class CatalogVo extends BaseVo{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2373320044300195955L;

	private String id;

    private String name;

    private byte type;
    
    private String exportType;

    private String parent;

    private Long priority;

    private Byte builtin;

    private String owner;
    
    private String createdBy;
    
    private Date createdDate;
    
    private String createdDateStr;

    private Byte status;

    private String extra;
    
    private Model model;
    
    private Map<String,Object> modelMetaMap;
    
    public Map<String, Object> getModelMetaMap() {
		return modelMetaMap;
	}

	public void setModelMetaMap(Map<String, Object> modelMetaMap) {
		this.modelMetaMap = modelMetaMap;
	}

	public List<ModelFunction> getModelFunctionList() {
		return modelFunctionList;
	}

	public void setModelFunctionList(List<ModelFunction> modelFunctionList) {
		this.modelFunctionList = modelFunctionList;
	}

	private List<ModelFunction> modelFunctionList;

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
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


	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public Byte getBuiltin() {
		return builtin;
	}

	public void setBuiltin(Byte builtin) {
		this.builtin = builtin;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
    
    
}
