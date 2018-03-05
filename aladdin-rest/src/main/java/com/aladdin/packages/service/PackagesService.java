package com.aladdin.packages.service;


import java.util.List;

import com.aladdin.base.Pager;
import com.aladdin.packages.entity.PackageZone;
import com.aladdin.packages.entity.Packages;

public interface PackagesService {
	
	/**
	 * 创建套餐（name，series不能为空）
	 * @param packages
	 * @param zonelist
	 * @param userId
	 * @return
	 */
	public boolean insertPackagesInfo(Packages packages,List<PackageZone> zonelist,String userId);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public Packages selectByPrimaryKey(String id);
	
	/**
	 * 删除套餐
	 * @param packages
	 * @return
	 */
	public boolean deletePackagesByKey(Packages packages);
	
	/**
	 * 条件查询套餐列表,全查（只查询最新的latest=1，可用的status=1套餐）
	 */
	public List<Packages> selectByCondition(Packages packages,String keyword,Integer minprice,Integer maxprice);
	
	/**
	 * 条件查询套餐列表,分页查询（只查询最新的latest=1，可用的status=1套餐）
	 */	
	public Pager<Packages> selectPageListByCondition(Packages packages,String keyword,Integer pageNum,Integer pageSize,Integer minprice,Integer maxprice);
	
}
