package com.aladdin.packages.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aladdin.packages.entity.Packages;

import tk.mybatis.mapper.common.Mapper;

public interface PackagesMapper extends Mapper<Packages>{
	
	/**
	 * 条件查询列表(自定义)
	 * @param packages
	 * @param keyword
	 * @return
	 */
	List<Packages> selectListByCondition(
			@Param("packages")Packages packages,
			@Param("keyword")String keyword,
			@Param("start")Integer start,
			@Param("limit")Integer limit,
			@Param("minprice")Integer minprice,
			@Param("maxprice")Integer maxprice);
	
	/**
	 * 查询记录数（自定义）
	 * @param packages
	 * @param keyword
	 * @return
	 */
	Integer selectCountByCondition(@Param("packages")Packages packages,@Param("keyword")String keyword,@Param("minprice")Integer minprice,@Param("maxprice")Integer maxprice);
	
	
	/**
	 * 根据名称获取最大版本号
	 * @param name
	 * @return
	 */
	Integer selectMaxVersionByName(@Param("name") String name);
	
}
