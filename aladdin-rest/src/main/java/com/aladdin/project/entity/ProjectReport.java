package com.aladdin.project.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="a_project_report")
public class ProjectReport implements Serializable{
		
		/**
	 * 
	 */
	private static final long serialVersionUID = -7636231627598988388L;

		@Id
	    private String id;

	    private String name;

	    private String jsonfile;

	    private String template;
	    
	    @Column(name="project_id")
	    private String projectId;
	    
	    @Column(name="package_id")
	    private String packageId;

	    private Float total;
	    
	    @Column(name="created_date")
	    private Date createdDate;
	    
	    @Column(name="created_by")
	    private String createdBy;
	    
	    @Column(name="modified_date")
	    private Date modifiedDate;
	    
	    @Column(name="modified_by")
	    private String modifiedBy;

	    private Byte status;

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id == null ? null : id.trim();
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name == null ? null : name.trim();
	    }

	    public String getJsonfile() {
	        return jsonfile;
	    }

	    public void setJsonfile(String jsonfile) {
	        this.jsonfile = jsonfile == null ? null : jsonfile.trim();
	    }

	    public String getTemplate() {
	        return template;
	    }

	    public void setTemplate(String template) {
	        this.template = template == null ? null : template.trim();
	    }

	    public String getProjectId() {
	        return projectId;
	    }

	    public void setProjectId(String projectId) {
	        this.projectId = projectId == null ? null : projectId.trim();
	    }

	    public String getPackageId() {
	        return packageId;
	    }

	    public void setPackageId(String packageId) {
	        this.packageId = packageId == null ? null : packageId.trim();
	    }

	    public Float getTotal() {
	        return total;
	    }

	    public void setTotal(Float total) {
	        this.total = total;
	    }

	    public Date getCreatedDate() {
	        return createdDate;
	    }

	    public void setCreatedDate(Date createdDate) {
	        this.createdDate = createdDate;
	    }

	    public String getCreatedBy() {
	        return createdBy;
	    }

	    public void setCreatedBy(String createdBy) {
	        this.createdBy = createdBy == null ? null : createdBy.trim();
	    }

	    public Date getModifiedDate() {
	        return modifiedDate;
	    }

	    public void setModifiedDate(Date modifiedDate) {
	        this.modifiedDate = modifiedDate;
	    }

	    public String getModifiedBy() {
	        return modifiedBy;
	    }

	    public void setModifiedBy(String modifiedBy) {
	        this.modifiedBy = modifiedBy == null ? null : modifiedBy.trim();
	    }

	    public Byte getStatus() {
	        return status;
	    }

	    public void setStatus(Byte status) {
	        this.status = status;
	    }
}
