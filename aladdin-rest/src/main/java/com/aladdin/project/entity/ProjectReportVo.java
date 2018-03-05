package com.aladdin.project.entity;

import com.aladdin.base.BaseVo;

public class ProjectReportVo extends BaseVo{
	
	private static final long serialVersionUID = -2938521907641703020L;

	private String id;

    private String name;

    private String jsonfile;

    private String projectId;
    
    private String packageId;

    //private Float total;
    
    //private Date createdDate;
    
   // private String createdBy;
    
    //private Date modifiedDate;
    
    //private String modifiedBy;

    //private Byte status;

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

//    public String getTemplate() {
//        return template;
//    }
//
//    public void setTemplate(String template) {
//        this.template = template == null ? null : template.trim();
//    }

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

//    public Float getTotal() {
//        return total;
//    }
//
//    public void setTotal(Float total) {
//        this.total = total;
//    }
//
//    public Date getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(Date createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy == null ? null : createdBy.trim();
//    }
//
//    public Date getModifiedDate() {
//        return modifiedDate;
//    }
//
//    public void setModifiedDate(Date modifiedDate) {
//        this.modifiedDate = modifiedDate;
//    }
//
//    public String getModifiedBy() {
//        return modifiedBy;
//    }
//
//    public void setModifiedBy(String modifiedBy) {
//        this.modifiedBy = modifiedBy == null ? null : modifiedBy.trim();
//    }
//
//    public Byte getStatus() {
//        return status;
//    }
//
//    public void setStatus(Byte status) {
//        this.status = status;
//    }
}
