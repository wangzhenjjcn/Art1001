package com.aladdin.series.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aladdin.series.entity.Series;

import tk.mybatis.mapper.common.Mapper;

public interface SeriesMapper extends Mapper<Series>{
	
	int selectCountByCondition(Series series);
	
	List<Series> selectListByCondition(@Param("series")Series series,@Param("start")Integer start,@Param("limit")Integer limit);
	
}
