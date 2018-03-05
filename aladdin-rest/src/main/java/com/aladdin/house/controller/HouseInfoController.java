package com.aladdin.house.controller;

import static com.aladdin.utils.BooleanUtils.OR;
import static com.aladdin.utils.BooleanUtils.isEmpty;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo3;
import com.aladdin.base.ErrorEnum;
import com.aladdin.house.HouseLayoutResult;
import com.aladdin.house.entity.HouseInfo;
import com.aladdin.house.service.HouseLayoutService;
import com.aladdin.house.vo.HouseInfoVO;
import com.aladdin.member.entity.Member;
import com.google.common.collect.Maps;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/api/housetypes")
public class HouseInfoController extends BaseController {

	private Logger log = Logger.getLogger(HouseInfoController.class);


	@Autowired
	private HouseLayoutService houseLayoutService;

	@ApiOperation(value = "查询我的户型", notes = "")
	@RequestMapping({"/search","/search/public"})
	public ResponseEntity<?> search(
			@ApiParam(value = "查询关键字", required = false) @RequestParam(required = false, defaultValue = "") String keyword,
			@ApiParam(value = "分页页码,默认第一页", required = true) @RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@ApiParam(value = "每页记录数,默认10条", required = true) @RequestParam(required = false, defaultValue = "10") Integer pageSize,
			@ApiParam(value = "查询面积范围[A,B],eg:0,10000", required = false) @RequestParam(required = false, defaultValue = "0,1000") String areaStr,
			@ApiParam(value = "查询状态,0未审核,1审核通过,2审核未通过;-1默认查询全部", required = false) @RequestParam(required = false,defaultValue="-1") Integer state, // 通过，未通过
			@ApiParam(value = "查询类别,200自己,其他公共", required = false) @RequestParam(required = false, defaultValue = "200") String status, // 我的户型，或其他,200表示我的户型
			@ApiParam(value = "查询户型类型", required = false) @RequestParam(required = false) String houseType, // 户型类型id
			@ApiParam(value = "城市查询", required = false) @RequestParam(required = false) String area, // 城市id
			@ApiParam(value = "城市查询等级", required = false) @RequestParam(required = false) String level, // 城市查询层级,省市县
			@ApiParam(value = "按创建日期排序规则,asc升序,desc降序;默认降序", required = false) @RequestParam(required = false, defaultValue = "desc") String timeSort,
			HttpServletRequest request) {
		try {
			Member m = getLoginUser(request);
			
			Map<String,Object> parms = Maps.newHashMap();
			
			parms.put("designerId", m.getId());
			
			// 组装面积查询参数
			float[] areas = null;
			if (!isEmpty(areaStr)) {
				areas = new float[2];
				String[] areaArr = areaStr.split(",");
				if (!isEmpty(areaArr) && areaArr.length == 1) {
					areas[0] = Float.parseFloat(areaArr[0]);
					areas[1] = Integer.MAX_VALUE;
				}
				if (!isEmpty(areaArr) && areaArr.length == 2) {
					areas[0] = Float.parseFloat(areaArr[0]);
					areas[1] = Float.parseFloat(areaArr[1]);
				}
				parms.put("areas_min", areas[0]);
				parms.put("areas_max", areas[1]);
			}
			//组装参数map
			if(keyword!=null&&!keyword.trim().equals("")){
				parms.put("keyword", keyword);
			}
			//有默认值
			parms.put("pageNo",pageNo);
			parms.put("pageSize",pageSize);
			parms.put("pageIndex",(pageNo - 1) * pageSize);
			
			parms.put("state", state);
			if("200".equals(status)){
				parms.put("status", 1);
			}else{
				parms.put("status", 0);
				parms.remove("designerId");
			}
			parms.put("timeSort", timeSort);
			//无默认值
			if(houseType!=null&&!"".equals(houseType.trim())){
				parms.put("houseType", houseType);
			}
			if(area!=null&&!"".equals(area.trim())){
				
				parms.put("area", area);
				
				if("sheng".equals(level)){
					parms.put("level", "sheng");
				}else if("shi".equals(level)){
					parms.put("level", "shi");
				}else{
					parms.put("level", "qu");
				}
			}else{
				parms.remove("level");
			}
			
			/**
			 * (String keyword, Integer start_index, Integer pageSize, float[] areas, String userid,
			Integer state, Integer status, String timeSort)
			 */
			/*HouseLayoutResult result = houseLayoutService.find(keyword, pageNo, pageSize, areas,
					 getLoginUser(request).getId(), state, 1,
					timeSort);*/
			HouseLayoutResult result = houseLayoutService.find(parms);
			return buildResponseEntity(ErrorEnum.SUCCESS, result);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return buildResponseEntity(ErrorEnum.SERVER_ERROR);
		}
	}
	
/*	@ApiOperation(value = "查询公共户型", notes = "")
	@RequestMapping("/search/public")
	public ResponseEntity<?> searchPublic(
			@ApiParam(value = "查询关键字", required = false) @RequestParam(required = false, defaultValue = "") String keyword,
			@ApiParam(value = "分页页码,默认第一页", required = true) @RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@ApiParam(value = "每页记录数,默认10条", required = true) @RequestParam(required = false, defaultValue = "10") Integer pageSize,
			@ApiParam(value = "查询面积范围[A,B],eg:0,100", required = false) @RequestParam(required = false, defaultValue = "0,1000") String areaStr,
			@ApiParam(value = "查询状态,0未审核,1审核通过,2审核未通过;-1默认查询全部", required = false) @RequestParam(required = false,defaultValue="-1") Integer state, // 通过，未通过
			@ApiParam(value = "按创建日期排序规则,asc升序,desc降序;默认降序", required = false) @RequestParam(required = false, defaultValue = "desc") String timeSort,
			HttpServletRequest request) {
		try {
			// 组装面积查询参数
			float[] areas = null;
			if (!isEmpty(areaStr)) {
				areas = new float[2];
				String[] areaArr = areaStr.split(",");
				if (!isEmpty(areaArr) && areaArr.length == 1) {
					areas[0] = Float.parseFloat(areaArr[0]);
					areas[1] = Integer.MAX_VALUE;
				}
				if (!isEmpty(areaArr) && areaArr.length == 2) {
					areas[0] = Float.parseFloat(areaArr[0]);
					areas[1] = Float.parseFloat(areaArr[1]);
				}
			}
			
			HouseLayoutResult result = houseLayoutService.find(keyword, pageNo, pageSize, areas,
					"", state, 0,
					timeSort);
			return buildResponseEntity(ErrorEnum.SUCCESS, null);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return buildResponseEntity(ErrorEnum.SERVER_ERROR);
		}
	}*/

	
	/**
	 * 获取数量信息
	 * @param req
	 * @return
	 */
	@RequestMapping("getCountInfo")
	public ResponseEntity<?> getCountByCondition(HttpServletRequest req){
		ErrorEnum errorEnum=ErrorEnum.SERVER_ERROR;
		Member user=getLoginUser(req);
		BaseVo3 baseVo3=new BaseVo3();
		if(user==null){
			errorEnum=ErrorEnum.NO_AUT_ERROR;
		}else{
			String userId=user.getId();
			Map<String, Object> conmap=houseLayoutService.selectCountByDesignerId(userId);
			if(conmap!=null){
				baseVo3.setData(conmap);
				errorEnum=ErrorEnum.SUCCESS;
			}			
		}
		ResponseEntity<?> responseEntity=buildResponseEntity(errorEnum, baseVo3);
	
		return responseEntity;
	}
	
	/**
	 * 获取总页数(用于分页，限于登录用户)
	 * @return
	 */
	@RequestMapping("/getTotlePage")
	public ResponseEntity<?> getTotalPage(HttpServletRequest request,
			@RequestParam(value="pageSize",defaultValue="12")Integer pageSize,
			@RequestParam(value="state",required=false)Integer state){
		
		ErrorEnum errorEnum=ErrorEnum.SERVER_ERROR;
		Member user=getLoginUser(request);
		BaseVo3 baseVo3=new BaseVo3();
		if(user==null){
			errorEnum=ErrorEnum.NO_AUT_ERROR;
		}else{
			errorEnum=ErrorEnum.SUCCESS;
			String userId=user.getId();
			int count=houseLayoutService.selectTotalCountByDesignerId(userId,state);
			Map<String, Object> map=new HashMap<String,Object>();
			int totalPage=1;
			if(count!=0){
				totalPage=(count+pageSize-1)/pageSize;
			}
			map.put("totalPage", totalPage);
			baseVo3.setData(map);
		}
		ResponseEntity<?> responseEntity=buildResponseEntity(errorEnum, baseVo3);
	
		return responseEntity;
	}



	/**
	 * @Description: 删除我的户型
	 */
	@RequestMapping(value = "/del/{id}")
	@ResponseBody
	public ResponseEntity<?> deleteMyHouse(@PathVariable("id") Integer houseId, HttpServletRequest request) {
		try {
			if (isEmpty(OR, houseId))
				return buildResponseEntity(ErrorEnum.PARAM_ERROR);

			HouseInfo house = houseLayoutService.findHouseById(houseId);

			if (house == null || !getLoginUser(request).getId().equals(house.getDesignerId()))
				return buildResponseEntity(ErrorEnum.PARAM_ERROR);

			houseLayoutService.deleteMyHouse(houseId, getLoginUser(request).getId());
			return buildResponseEntity(ErrorEnum.SUCCESS);

		} catch (Exception e) {
			log.error(e.getMessage());
			return buildResponseEntity(ErrorEnum.SERVER_ERROR);
		}
	}

	/**
	 * @Description: 添加户型基本信息
	 */
	@RequestMapping(value = "/addHouse")
	@ResponseBody
	public ResponseEntity<?> addHouse(
			//HouseInfo house,
			@RequestParam(value="communityName",required=false) String communityName,
			@RequestParam(value="name",required=false) String name,
			@RequestParam(value="houseTypeId",required=false) String houseTypeId,
			@RequestParam(value="attachmenturi",required=false) String attachmenturi,//skp文件
			@RequestParam(value="housePicUri",required=false) String housePicUri,//png文件
			@RequestParam(value="uploadUri",required=false) String uploadUri,
			@RequestParam(value="area",required=false) String area,
			@RequestParam(value="sheng",required=false) String locationId1,
			@RequestParam(value="shi",required=false) String locationId2,
			@RequestParam(value="qu",required=false) String locationId,
			@RequestParam(value="location",required=false) String location,
			HttpServletRequest request) {
		try {
			HouseInfo house=new HouseInfo();
			if(attachmenturi!=null){
				house.setAttachmenturi(attachmenturi);
			}
			if(area!=null){
				house.setArea(Float.parseFloat(area));
			}
			if(locationId!=null){
				house.setLocationId(locationId);
			}
			if(locationId1!=null){
				house.setLocationId1(locationId1);
			}
			if(locationId2!=null){
				house.setLocationId2(locationId2);
			}
			if(location!=null){
				house.setLocation(location);
			}
			if(name!=null){
				house.setName(name);
			}
			if(communityName!=null){
				house.setCommunityName(communityName);
			}
			if(houseTypeId!=null){
				house.setHouseTypeId(Integer.parseInt(houseTypeId));
			}
			if(housePicUri!=null){
				house.setHousePicUri(housePicUri);
			}
			if(uploadUri!=null){
				house.setUploadUri(uploadUri);
			}
			house.setIsDel(1);
			house.setDesignerId(getLoginUser(request).getId());
			houseLayoutService.saveHouse(house);

			return buildResponseEntity(ErrorEnum.SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return buildResponseEntity(ErrorEnum.SERVER_ERROR);
		}
	}
	
	
	

	@ApiOperation(value = "查询户型信息")
	@RequestMapping(value = "/get/{id}")
	public ResponseEntity<?> findHouse(@PathVariable("id") Integer houseId) {

		if (isEmpty(houseId))
			return buildResponseEntity(ErrorEnum.PARAM_ERROR);

		HouseInfo house = houseLayoutService.findHouseById(houseId);

		if (isEmpty(house))
			return buildResponseEntity(ErrorEnum.PARAM_ERROR);
		
		return buildResponseEntity(ErrorEnum.SUCCESS,(HouseInfoVO) po2vo(house, new HouseInfoVO()) );
	}

	/**
	 * @Description: 编辑户型基本信息
	 */
	@ApiOperation(value = "编辑户型")
	@RequestMapping(value = "/edit/{id}")
	@ResponseBody
	public ResponseEntity<?> editHouse(@PathVariable("id") Integer id, 
			@RequestParam(value="communityName",required=false) String communityName,
			@RequestParam(value="name",required=false) String name,
			@RequestParam(value="houseTypeId",required=false) String houseTypeId,
			@RequestParam(value="attachmenturi",required=false) String attachmenturi,
			@RequestParam(value="housePicUri",required=false) String housePicUri,
			@RequestParam(value="area",required=false) String area,
			@RequestParam(value="sheng",required=false) String locationId1,
			@RequestParam(value="shi",required=false) String locationId2,
			@RequestParam(value="qu",required=false) String locationId,
			@RequestParam(value="location",required=false) String location,
			@RequestParam(value="uploadUri",required=false) String uploadUri,
			HttpServletRequest request) {

		try {
			if (id == null || "".equals(id))
				return buildResponseEntity(ErrorEnum.PARAM_ERROR);

			HouseInfo persisthouse = houseLayoutService.findHouseById(id);

			if (persisthouse == null || !getLoginUser(request).getId().equals(persisthouse.getDesignerId()))
				return buildResponseEntity(ErrorEnum.NO_AUT_ERROR);
			
			HouseInfo house=new HouseInfo();
			if(attachmenturi!=null){
				house.setAttachmenturi(attachmenturi);
			}
			if(area!=null){
				house.setArea(Float.parseFloat(area));
			}
			if(locationId!=null){
				house.setLocationId(locationId);
			}
			if(locationId1!=null){
				house.setLocationId1(locationId1);
			}
			if(locationId2!=null){
				house.setLocationId2(locationId2);
			}
			if(location!=null){
				house.setLocation(location);
			}
			if(name!=null){
				house.setName(name);
			}
			if(communityName!=null){
				house.setCommunityName(communityName);
			}
			if(uploadUri!=null){
				house.setUploadUri(uploadUri);
			}
			if(houseTypeId!=null){
				house.setHouseTypeId(Integer.parseInt(houseTypeId));
			}
			if(housePicUri!=null){
				house.setHousePicUri(housePicUri);
			}
			
			house.setDesignerId(getLoginUser(request).getId());
			
			house.setId(persisthouse.getId());
			houseLayoutService.updateHouse(house);

			return buildResponseEntity(ErrorEnum.SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return buildResponseEntity(ErrorEnum.SERVER_ERROR);
		}
	}
}
