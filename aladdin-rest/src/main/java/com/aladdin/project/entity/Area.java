package com.aladdin.project.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="a_areas")
public class Area {
	
    private String code;

    private String name;

    private String latlng;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng == null ? null : latlng.trim();
    }
}
