package com.aladdin.house.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aladdin.house.entity.HouseInfo;

import tk.mybatis.mapper.common.Mapper;

public interface HouseInfoMapper extends Mapper<HouseInfo> {

/*	List<HouseInfo> findHouseInfos(
			@Param("keyword")String keyword, 
			@Param("startIndex")Integer start_index,
			@Param("pageSize")Integer pageSize,
			@Param("areas")float[] areas, 
			@Param("designerId")String userid,
			@Param("state")Integer state,
			@Param("status")Integer status,
			@Param("timeSort") String timeSort);
	
	Integer findHouseInfoCount(			
			@Param("keyword")String keyword, 
			@Param("areas")float[] areas, 
			@Param("designerId")String userid,
			@Param("state")Integer state,
			@Param("status")Integer status
			);*/

	Integer findHouseInfoCount(@Param("params")Map<String, Object> parms);

	List<HouseInfo> findHouseInfos(@Param("params")Map<String, Object> parms);

}
