package com.aladdin.catalog.service;

import java.util.List;
import java.util.Map;

import com.aladdin.base.Pager;
import com.aladdin.catalog.entity.Catalog;
import com.aladdin.model.entity.Model;

public interface CatalogService {
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public Catalog selectByKey(String id);
	
	/**
	 * 分页查询（包括条件查询）
	 * @param map
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Pager<Catalog> selectPageList(Map<String, Object> map,Integer pageIndex,Integer pageSize);
	
	
	/**
	 * 查询列表（全查，条件查询）
	 * @param map
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Catalog> selectList(Map<String, Object> map);
	
	
	/**
	 * 添加型录
	 * @param model
	 * @param catalog
	 * @return
	 */
	public boolean saveInfo(Model model,Catalog catalog);
	
	/**
	 * 修改型录
	 * @param model
	 * @param catalog
	 * @return
	 */
	public boolean updateInfo(Model model,Catalog catalog);
	
	/**
	 * 删除型录信息（逻辑删除）
	 * @param id
	 * @return
	 */
	public boolean deleteInfo(String id);
	
	/**
	 * 获取子型录列表
	 * @param pid
	 * @return
	 */
	public List<Catalog> selectChilds(String pid);
	
	
	
}
