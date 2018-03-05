package com.aladdin.house.service;

import java.util.Map;

import com.aladdin.house.HouseLayoutResult;
import com.aladdin.house.entity.HouseInfo;

public interface HouseLayoutService {

	HouseInfo findHouseById(Integer houseId);

	int deleteMyHouse(Integer houseId, String memberIdByAccessToken);

	int saveHouse(HouseInfo house);

	int updateHouse(HouseInfo house);

	/*HouseLayoutResult find(String keyword, Integer start_index, Integer pageSize, float[] areas, String userid,
			Integer state, Integer status, String timeSort);*/
	
	Map<String, Object> selectCountByDesignerId(String designerId);
	
	int selectTotalCountByDesignerId(String designerId,Integer state);

	HouseLayoutResult find(Map<String, Object> parms);

}
