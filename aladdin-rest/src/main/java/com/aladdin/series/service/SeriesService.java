package com.aladdin.series.service;

import java.util.List;
import java.util.Map;

import com.aladdin.base.Pager;
import com.aladdin.series.entity.Series;

public interface SeriesService {
	
	/**
	 * 查询满足条件的记录数
	 * @param map
	 * @return
	 */
	//public Integer count(Map<String, Object> map);
	
	/**
	 * 添加规则
	 */
	public Integer create(Series series);
	

	public Pager<Series> find(Map<String, Object> map, Integer pageIndex,Integer pageSize);

	
	/**
	 * 条件查询，全查,根据名称模糊查询，创建人,id准确查询，只查询可用，并且是最新版本的记录
	 * @param map
	 * @return
	 */
	public List<Series> find(Map<String, Object> map);

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public Series find(String id);
	
	/**
	 * 逻辑删除
	 * @return
	 */
	public Integer remove(String id);

	
}
