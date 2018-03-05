package com.aladdin.packages.entity;

import com.aladdin.base.BaseVo;

public class PackUpgVo extends BaseVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4568848709335143882L;

	private Packages packages;
	
	private String upgradesJson;//套餐升级 base64 编码

	public Packages getPackages() {
		return packages;
	}

	public void setPackages(Packages packages) {
		this.packages = packages;
	}

	public String getUpgradesJson() {
		return upgradesJson;
	}

	public void setUpgradesJson(String upgradesJson) {
		this.upgradesJson = upgradesJson;
	}
	
	
	
}
