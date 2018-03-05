package com.aladdin.packages.service;

import java.util.List;

import com.aladdin.packages.entity.PackageUpgrade;

public interface PackageUpgradeService {
	
	/**
	 * 条件查询,准确查询，全查,只包含可用的套餐升级
	 * @param packageUpgrade
	 * @return
	 */
	List<PackageUpgrade> selectByCondition(PackageUpgrade packageUpgrade);
	
}
