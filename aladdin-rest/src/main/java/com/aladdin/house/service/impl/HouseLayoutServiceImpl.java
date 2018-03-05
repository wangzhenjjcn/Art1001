package com.aladdin.house.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.house.HouseContants;
import com.aladdin.house.HouseLayoutResult;
import com.aladdin.house.entity.HouseInfo;
import com.aladdin.house.mapper.HouseInfoMapper;
import com.aladdin.house.service.HouseLayoutService;
import com.aladdin.utils.BooleanUtils;

import tk.mybatis.mapper.entity.Example;

@Service
public class HouseLayoutServiceImpl implements HouseLayoutService {

	@Autowired
	private HouseInfoMapper houseInfoMapper;

	@Override
	public HouseInfo findHouseById(Integer houseId) {
		HouseInfo house = new HouseInfo();
		house.setId(houseId);
		house.setIsDel(1);
		return houseInfoMapper.selectOne(house);
	}

	@Override
	public int deleteMyHouse(Integer houseId, String userId) {
		Example example = new Example(HouseInfo.class);
		if (houseId != null) {
			example.createCriteria().andEqualTo("id", houseId);
		}
		if (userId != null && !"".equals(userId)) {
			example.createCriteria().andEqualTo("designerId", userId);
		}
		HouseInfo house = new HouseInfo();
		house.setIsDel(0);
		return houseInfoMapper.updateByExampleSelective(house,example);
	}

	@Override
	public int saveHouse(HouseInfo house) {
		if(house==null||house.getDesignerId()==null){
			return 0;
		}
		house.setCreateTime(new Date());
		house.setState(HouseContants.verify_ok);//审核通过
		house.setStatus(HouseContants.status_own);
		return houseInfoMapper.insertSelective(house);
	}

	@Override
	public int updateHouse(HouseInfo house) {
		if(house==null||BooleanUtils.isEmpty(house.getId()))
			return 0;
		return houseInfoMapper.updateByPrimaryKeySelective(house);
	}

	/*@Override
	public HouseLayoutResult find(String keyword,Integer pageNo,
			Integer pageSize, float[] areas, String userid,Integer state,Integer status,String timeSort) {
		Integer start_index = (pageNo - 1) * pageSize;
		Integer count=houseInfoMapper.findHouseInfoCount(keyword, areas, userid, state, status);
		List<HouseInfo> list=new ArrayList<HouseInfo>();
		if(count==null){
			count=0;
		}
		if(count!=0){
			list =  houseInfoMapper.findHouseInfos(keyword,start_index,pageSize, areas, userid,state,status,timeSort);
		}
	 	HouseLayoutResult result = new HouseLayoutResult(keyword, count, list, pageNo,areas,state);
		return result;
	}*/
	
	@Override
	public HouseLayoutResult find(Map<String, Object> parms) {
		
		
		Integer count=houseInfoMapper.findHouseInfoCount(parms);
		List<HouseInfo> list=new ArrayList<HouseInfo>();
		if(count==null){
			count = 0;
		}
		if(count!=0){
			list = houseInfoMapper.findHouseInfos(parms);
		}
		Integer pageNo = 1;
		if(parms.get("pageNo")!=null){
			pageNo = Integer.parseInt(parms.get("pageNo").toString());
		}
		float[] areas = new float[]{0,1000};
		if(parms.get("areas")!=null){
			areas = (float[])parms.get("areas");
		}
		Integer state = 1;
		if(parms.get("state")!=null&&!parms.get("state").equals("")){
			state  = Integer.parseInt(parms.get("state")+"");
		}
		HouseLayoutResult result = new HouseLayoutResult(
				parms.get("keyword")+"", 
				count, list,
				pageNo,
				areas,
				state);
		return result;
	}

	@Override
	public Map<String, Object> selectCountByDesignerId(String designerId) {
		// TODO Auto-generated method stub

		HouseInfo info=new HouseInfo();
		info.setDesignerId(designerId);
		//info.setState(HouseContants.verify_init);
		Integer totalCount=houseInfoMapper.selectCount(info);
		info.setState(HouseContants.verify_ok);
		Integer verifyOkCount=houseInfoMapper.selectCount(info);
		info.setState(HouseContants.verify_no);
		Integer verifyNoCount=houseInfoMapper.selectCount(info);
		Map<String, Object> resMap=new HashMap<String,Object>();
		resMap.put("totalCount", totalCount);
		resMap.put("verifyOkCount", verifyOkCount);
		resMap.put("verifyNocount", verifyNoCount);
		
		return resMap;
	}

	@Override
	public int selectTotalCountByDesignerId(String designerId,Integer state) {
		// TODO Auto-generated method stub
		HouseInfo info=new HouseInfo();
		info.setDesignerId(designerId);
		if(state!=null && state != -1){
			info.setState(state);
		}
		Integer totalCount=houseInfoMapper.selectCount(info);
		return totalCount;
	}

	
	
}
